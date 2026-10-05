package com.example.algorithm2;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generic fuzz harness. For the class given by -DtargetClass it:
 *   1) collects every public method (static AND instance),
 *   2) builds a fresh instance with fuzzed constructor arguments when needed,
 *   3) calls the method with fuzzed arguments.
 *
 * Optional system properties:
 *   -Dfuzz.ignore=java.lang.IllegalArgumentException,...  exceptions treated as "expected"
 *   -Dfuzz.allowEmpty=true                                 do not fail when no method is fuzzable
 */
public class Algorithm2Test {

    private static final int MAX_DEPTH = 3;

    private static final Class<?> TARGET = loadTarget();
    private static final List<Constructor<?>> CTORS = new ArrayList<Constructor<?>>();
    private static final List<Method> METHODS = new ArrayList<Method>();
    private static final Set<String> IGNORED = parseIgnored();

    static {
        collect();
    }

    /** Thrown while preparing inputs; the iteration is skipped (not a finding). */
    private static final class Skip extends RuntimeException {
        private static final long serialVersionUID = 1L;

        Skip() {
            super(null, null, false, false);
        }
    }

    // ------------------------------------------------------------------
    // Setup
    // ------------------------------------------------------------------

    private static Class<?> loadTarget() {
        String name = System.getProperty("targetClass");
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("targetClass property is not set");
        }
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot load " + name, e);
        }
    }

    private static Set<String> parseIgnored() {
        Set<String> s = new HashSet<String>();
        String v = System.getProperty("fuzz.ignore", "");
        for (String part : v.split(",")) {
            if (!part.trim().isEmpty()) {
                s.add(part.trim());
            }
        }
        return s;
    }

    private static void collect() {
        boolean instantiable = !Modifier.isAbstract(TARGET.getModifiers()) && !TARGET.isInterface();
        if (instantiable) {
            for (Constructor<?> c : TARGET.getConstructors()) {
                if (canBuildAll(c.getParameterTypes(), 0)) {
                    CTORS.add(c);
                }
            }
        }
        for (Method m : TARGET.getMethods()) {
            if (m.getDeclaringClass() == Object.class || m.isSynthetic() || m.isBridge()) {
                continue;
            }
            boolean isStatic = Modifier.isStatic(m.getModifiers());
            if (!isStatic && CTORS.isEmpty()) {
                continue;
            }
            if (!canBuildAll(m.getParameterTypes(), 0)) {
                continue;
            }
            try {
                m.setAccessible(true);
            } catch (RuntimeException ignored) {
                // keep going; invoke may still work for public members
            }
            METHODS.add(m);
        }
        System.err.println("[harness] target=" + TARGET.getName()
                + " ctors=" + CTORS.size() + " methods=" + METHODS.size());
        if (METHODS.isEmpty() && !Boolean.getBoolean("fuzz.allowEmpty")) {
            throw new IllegalStateException("no fuzzable methods in " + TARGET.getName());
        }
    }

    // ------------------------------------------------------------------
    // The fuzz target
    // ------------------------------------------------------------------

    @FuzzTest(maxDuration = "3s")
    void testParameterizedFuzzer(FuzzedDataProvider data) throws Throwable {
        if (METHODS.isEmpty()) {
            return;
        }
        Method m = METHODS.get(data.consumeInt(0, METHODS.size() - 1));
        Object self = null;
        Object[] args;
        try {
            if (!Modifier.isStatic(m.getModifiers())) {
                Constructor<?> c = CTORS.get(data.consumeInt(0, CTORS.size() - 1));
                self = construct(c, data, 0);
            }
            args = makeAll(m.getParameterTypes(), m.getGenericParameterTypes(), data, 0);
        } catch (Skip s) {
            return;
        }
        try {
            m.invoke(self, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (isExpected(m, cause)) {
                return;
            }
            throw cause; // let Jazzer see the real exception
        } catch (IllegalAccessException e) {
            // not callable from the harness; ignore
        } catch (IllegalArgumentException e) {
            // argument mismatch in the harness itself; ignore
        }
    }

    private static boolean isExpected(Method m, Throwable t) {
        for (Class<?> declared : m.getExceptionTypes()) {
            if (declared.isInstance(t)) {
                return true;
            }
        }
        for (Class<?> c = t.getClass(); c != null; c = c.getSuperclass()) {
            if (IGNORED.contains(c.getName())) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Input construction
    // ------------------------------------------------------------------

    private static boolean canBuildAll(Class<?>[] types, int depth) {
        for (Class<?> t : types) {
            if (!canBuild(t, depth)) {
                return false;
            }
        }
        return true;
    }

    private static boolean canBuild(Class<?> t, int depth) {
        if (t.isPrimitive() || isWrapper(t) || t == String.class || t == CharSequence.class
                || t == Object.class || t == Class.class) {
            return true;
        }
        if (t.isEnum()) {
            return t.getEnumConstants().length > 0;
        }
        if (t.isArray()) {
            return canBuild(t.getComponentType(), depth);
        }
        if (t.isInterface()) {
            return true;
        }
        if (depth >= MAX_DEPTH || Modifier.isAbstract(t.getModifiers())) {
            return false;
        }
        for (Constructor<?> c : t.getConstructors()) {
            if (canBuildAll(c.getParameterTypes(), depth + 1)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isWrapper(Class<?> t) {
        return t == Boolean.class || t == Byte.class || t == Short.class || t == Character.class
                || t == Integer.class || t == Long.class || t == Float.class || t == Double.class;
    }

    private static Object[] makeAll(Class<?>[] types, Type[] generic, FuzzedDataProvider d, int depth) {
        Object[] out = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            out[i] = make(types[i], generic == null ? null : generic[i], d, depth);
        }
        return out;
    }

    private static Object make(Class<?> t, Type gt, FuzzedDataProvider d, int depth) {
        if (t == boolean.class || t == Boolean.class) return d.consumeBoolean();
        if (t == byte.class || t == Byte.class) return d.consumeByte();
        if (t == short.class || t == Short.class) return d.consumeShort();
        if (t == char.class || t == Character.class) return d.consumeChar();
        if (t == int.class || t == Integer.class) return d.consumeInt();
        if (t == long.class || t == Long.class) return d.consumeLong();
        if (t == float.class || t == Float.class) return d.consumeFloat();
        if (t == double.class || t == Double.class) return d.consumeDouble();
        if (t == String.class || t == CharSequence.class || t == Object.class) return d.consumeString(32);
        if (t == Class.class) return Object.class;

        if (t.isEnum()) {
            Object[] k = t.getEnumConstants();
            return k[d.consumeInt(0, k.length - 1)];
        }
        if (t.isArray()) {
            if (t == byte[].class) {
                return d.consumeBytes(32);
            }
            Class<?> comp = t.getComponentType();
            int n = d.consumeInt(0, 3);
            Object arr = Array.newInstance(comp, n);
            for (int i = 0; i < n; i++) {
                Array.set(arr, i, make(comp, null, d, depth + 1));
            }
            return arr;
        }

        if (t == List.class || t == Collection.class || t == Iterable.class) {
            return fill(new ArrayList<Object>(), typeArg(gt, 0), d, depth);
        }
        if (t == Set.class) {
            return fill(new LinkedHashSet<Object>(), typeArg(gt, 0), d, depth);
        }
        if (t == Map.class) {
            Map<Object, Object> map = new LinkedHashMap<Object, Object>();
            int n = d.consumeInt(0, 3);
            Class<?> kt = typeArg(gt, 0);
            Class<?> vt = typeArg(gt, 1);
            for (int i = 0; i < n; i++) {
                map.put(make(kt, null, d, depth + 1), make(vt, null, d, depth + 1));
            }
            return map;
        }

        if (t.isInterface()) {
            return proxyOf(t);
        }

        // concrete class: pick a buildable public constructor
        if (depth >= MAX_DEPTH) {
            throw new Skip();
        }
        List<Constructor<?>> usable = new ArrayList<Constructor<?>>();
        for (Constructor<?> c : t.getConstructors()) {
            if (canBuildAll(c.getParameterTypes(), depth + 1)) {
                usable.add(c);
            }
        }
        if (usable.isEmpty()) {
            throw new Skip();
        }
        return construct(usable.get(d.consumeInt(0, usable.size() - 1)), d, depth + 1);
    }

    private static Object construct(Constructor<?> c, FuzzedDataProvider d, int depth) {
        Object[] args = makeAll(c.getParameterTypes(), c.getGenericParameterTypes(), d, depth + 1);
        try {
            return c.newInstance(args);
        } catch (InvocationTargetException e) {
            throw new Skip();   // constructor rejected the input: not a finding
        } catch (InstantiationException e) {
            throw new Skip();
        } catch (IllegalAccessException e) {
            throw new Skip();
        } catch (IllegalArgumentException e) {
            throw new Skip();
        }
    }

    private static Collection<Object> fill(Collection<Object> out, Class<?> elem, FuzzedDataProvider d, int depth) {
        int n = d.consumeInt(0, 3);
        for (int i = 0; i < n; i++) {
            out.add(make(elem, null, d, depth + 1));
        }
        return out;
    }

    private static Class<?> typeArg(Type gt, int index) {
        if (gt instanceof ParameterizedType) {
            Type[] a = ((ParameterizedType) gt).getActualTypeArguments();
            if (index < a.length) {
                if (a[index] instanceof Class) {
                    return (Class<?>) a[index];
                }
                if (a[index] instanceof ParameterizedType) {
                    Type raw = ((ParameterizedType) a[index]).getRawType();
                    if (raw instanceof Class) {
                        return (Class<?>) raw;
                    }
                }
            }
        }
        return String.class;
    }

    private static Object proxyOf(final Class<?> iface) {
        return Proxy.newProxyInstance(Algorithm2Test.class.getClassLoader(), new Class<?>[] {iface},
                (proxy, m, args) -> {
                    String n = m.getName();
                    int pc = m.getParameterTypes().length;
                    if (n.equals("equals") && pc == 1) return proxy == args[0];
                    if (n.equals("hashCode") && pc == 0) return System.identityHashCode(proxy);
                    if (n.equals("toString") && pc == 0) return "proxy:" + iface.getSimpleName();
                    return defaultValue(m.getReturnType());
                });
    }

    private static Object defaultValue(Class<?> r) {
        if (r == boolean.class) return Boolean.FALSE;
        if (r == byte.class) return (byte) 0;
        if (r == short.class) return (short) 0;
        if (r == char.class) return (char) 0;
        if (r == int.class) return 0;
        if (r == long.class) return 0L;
        if (r == float.class) return 0f;
        if (r == double.class) return 0d;
        return null;
    }
}