package com.example.algorithm2;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generic fuzz harness (Optimized for Defects4J compatibility).
 */
public class Algorithm2Test {

    private static final int MAX_DEPTH = 3;
    private static final int CALLS_PER_INPUT = 8;

    private static final Class<?> TARGET = loadTarget();
    private static final List<Object> FACTORIES = new ArrayList<Object>();
    private static final List<Method> METHODS = new ArrayList<Method>();
    private static final Set<String> IGNORED = parseIgnored();
    private static final boolean FAIL_ON_LINKAGE = Boolean.getBoolean("fuzz.failOnLinkage");
    private static boolean linkageLogged = false;

    static {
        collect();
    }

    private static final class Skip extends RuntimeException {
        private static final long serialVersionUID = 1L;
        Skip() {
            super(null, null, false, false);
        }
    }

    // ------------------------------------------------------------------
    // Setup & Fallback Class Loader
    // ------------------------------------------------------------------

    private static Class<?> loadTarget() {
        String name = System.getProperty("targetClass");
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("targetClass property is not set");
        }
        
        // รวบรวมรายชื่อคลาสทางเลือกกรณีที่ Defects4J มีการเปลี่ยนชื่อแพ็กเกจข้ามเวอร์ชัน (เช่น math3 -> math)
        List<String> candidates = new ArrayList<String>();
        candidates.add(name);
        
        if (name.contains("math3")) {
            candidates.add(name.replace("math3", "math"));
        } else if (name.contains(".math.")) {
            candidates.add(name.replace(".math.", ".math3."));
        }
        
        if (name.contains("lang3")) {
            candidates.add(name.replace("lang3", "lang"));
        } else if (name.contains(".lang.")) {
            candidates.add(name.replace(".lang.", ".lang3."));
        }

        if (name.contains("collections4")) {
            candidates.add(name.replace("collections4", "collections"));
        } else if (name.contains(".collections.")) {
            candidates.add(name.replace(".collections.", ".collections4."));
        }

        ClassNotFoundException lastEx = null;
        for (String candidate : candidates) {
            try {
                Class<?> cls = Class.forName(candidate);
                if (!candidate.equals(name)) {
                    System.err.println("[harness] Fallback target loaded: " + candidate + " (original was " + name + ")");
                }
                return cls;
            } catch (ClassNotFoundException e) {
                lastEx = e;
            }
        }
        throw new IllegalStateException("Cannot load target class: " + name, lastEx);
    }

    private static Set<String> parseIgnored() {
        Set<String> s = new HashSet<String>();
        // เพิ่มตัวกรองพื้นฐานสำหรับ False Positive ทั่วไป
        s.add("org.apache.commons.jxpath.JXPathInvalidSyntaxException");
        s.add("java.lang.ClassCastException");
        
        String v = System.getProperty("fuzz.ignore", "");
        for (String part : v.split(",")) {
            if (!part.trim().isEmpty()) {
                s.add(part.trim());
            }
        }
        return s;
    }

    private static void collect() {
        if (!Modifier.isAbstract(TARGET.getModifiers()) && !TARGET.isInterface()) {
            for (Constructor<?> c : TARGET.getConstructors()) {
                if (canBuildAll(c.getParameterTypes(), 0)) {
                    FACTORIES.add(c);
                }
            }
            if (FACTORIES.isEmpty()) {
                for (Constructor<?> c : TARGET.getDeclaredConstructors()) {
                    if (!c.isSynthetic() && canBuildAll(c.getParameterTypes(), 0) && makeAccessible(c)) {
                        FACTORIES.add(c);
                    }
                }
            }
        }
        for (Method m : TARGET.getMethods()) {
            if (Modifier.isStatic(m.getModifiers())
                    && TARGET.isAssignableFrom(m.getReturnType())
                    && canBuildAll(m.getParameterTypes(), 0)) {
                FACTORIES.add(m);
            }
        }
        for (Field f : TARGET.getFields()) {
            if (Modifier.isStatic(f.getModifiers()) && TARGET.isAssignableFrom(f.getType())) {
                FACTORIES.add(f);
            }
        }

        for (Method m : TARGET.getMethods()) {
            if (m.getDeclaringClass() == Object.class || m.isSynthetic() || m.isBridge()) {
                continue;
            }
            boolean isStatic = Modifier.isStatic(m.getModifiers());
            if (!isStatic && FACTORIES.isEmpty()) {
                continue;
            }
            if (!canBuildAll(m.getParameterTypes(), 0)) {
                continue;
            }
            makeAccessible(m);
            METHODS.add(m);
        }
        System.err.println("[harness] target=" + TARGET.getName()
                + " factories=" + FACTORIES.size() + " methods=" + METHODS.size());
        if (METHODS.isEmpty() && !Boolean.getBoolean("fuzz.allowEmpty")) {
            throw new IllegalStateException("no fuzzable methods in " + TARGET.getName());
        }
    }

    private static boolean makeAccessible(java.lang.reflect.AccessibleObject o) {
        try {
            o.setAccessible(true);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static Object instance(Object factory, FuzzedDataProvider d) {
        try {
            if (factory instanceof Constructor) {
                return construct((Constructor<?>) factory, d, 0);
            }
            Object r;
            if (factory instanceof Method) {
                Method fm = (Method) factory;
                r = fm.invoke(null, makeAll(fm.getParameterTypes(), fm.getGenericParameterTypes(), d, 1));
            } else {
                r = ((Field) factory).get(null);
            }
            if (r == null) {
                throw new Skip();
            }
            return r;
        } catch (InvocationTargetException e) {
            throw new Skip();
        } catch (IllegalAccessException e) {
            throw new Skip();
        } catch (IllegalArgumentException e) {
            throw new Skip();
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
        // Each Jazzer input exercises several API methods. This improves method
        // reachability without extending the per-project fuzzing time budget.
        for (int call = 0; call < CALLS_PER_INPUT; call++) {
            Method m = METHODS.get(data.consumeInt(0, METHODS.size() - 1));
            Object self = null;
            Object[] args;
            try {
                if (!Modifier.isStatic(m.getModifiers())) {
                    self = instance(FACTORIES.get(data.consumeInt(0, FACTORIES.size() - 1)), data);
                }
                args = makeAll(m.getParameterTypes(), m.getGenericParameterTypes(), data, 0);
            } catch (Skip s) {
                continue;
            }
            try {
                m.invoke(self, args);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (isExpected(m, cause)) {
                    continue;
                }
                if (cause instanceof LinkageError && !FAIL_ON_LINKAGE) {
                    if (!linkageLogged) {
                        linkageLogged = true;
                        System.err.println("[harness] ignoring LinkageError: " + cause);
                    }
                    continue;
                }
                throw cause;
            } catch (IllegalAccessException e) {
                // A method may be inaccessible due to module or security policy.
            } catch (IllegalArgumentException e) {
                // Skip signatures that cannot be invoked on this library version.
            }
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
                || t == Object.class || t == Class.class
                || java.io.InputStream.class.isAssignableFrom(t)
                || java.io.OutputStream.class.isAssignableFrom(t)
                || java.io.Reader.class.isAssignableFrom(t)
                || java.io.Writer.class.isAssignableFrom(t)) {
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
        
        // Bias toward common boundary values while retaining wider values for
        // size, offset, and numeric range branches.
        if (t == int.class || t == Integer.class) return consumeInt(d);
        if (t == long.class || t == Long.class) return (long) consumeInt(d);
        
        if (t == float.class || t == Float.class) return d.consumeFloat();
        if (t == double.class || t == Double.class) return d.consumeDouble();
        if (t == String.class || t == CharSequence.class || t == Object.class) return d.consumeString(32);
        if (t == Class.class) return Object.class;

        // รองรับ Abstract Stream / Reader / Writer เพื่อแก้ปัญหา NO_METHODS ของ Compress
        if (java.io.InputStream.class.isAssignableFrom(t)) {
            return new ByteArrayInputStream(d.consumeBytes(32));
        }
        if (java.io.OutputStream.class.isAssignableFrom(t)) {
            return new ByteArrayOutputStream();
        }
        if (java.io.Reader.class.isAssignableFrom(t)) {
            return new StringReader(d.consumeString(32));
        }
        if (java.io.Writer.class.isAssignableFrom(t)) {
            return new StringWriter();
        }

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

    private static int consumeInt(FuzzedDataProvider d) {
        switch (d.consumeInt(0, 7)) {
            case 0: return 0;
            case 1: return 1;
            case 2: return -1;
            default: return d.consumeInt(-1024, 1024);
        }
    }

    private static Object construct(Constructor<?> c, FuzzedDataProvider d, int depth) {
        Object[] args = makeAll(c.getParameterTypes(), c.getGenericParameterTypes(), d, depth + 1);
        try {
            return c.newInstance(args);
        } catch (InvocationTargetException e) {
            throw new Skip();
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
