package com.google.gson.functional;

import java.io.IOException;

import junit.framework.TestCase;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Regression test: a {@code @JsonAdapter} annotation on a primitive field must take
 * precedence over the default runtime-type adapter when serializing.
 */
public final class JsonAdapterPrimitiveFieldRegressionTest extends TestCase {

  public void testPrimitiveFieldAnnotationTakesPrecedenceOverDefault() {
    Gson gson = new Gson();
    String json = gson.toJson(new GadgetWithPrimitivePart(42));
    assertEquals("{\"part\":[\"42\"]}", json);
    GadgetWithPrimitivePart gadget = gson.fromJson(json, GadgetWithPrimitivePart.class);
    assertEquals(42, gadget.part);
  }

  private static final class GadgetWithPrimitivePart {
    @JsonAdapter(LongToStringArrayTypeAdapterFactory.class)
    final long part;

    private GadgetWithPrimitivePart(long part) {
      this.part = part;
    }
  }

  private static final class LongToStringArrayTypeAdapterFactory implements TypeAdapterFactory {
    static final TypeAdapter<Long> ADAPTER = new TypeAdapter<Long>() {
      @Override public void write(JsonWriter out, Long value) throws IOException {
        out.beginArray();
        out.value(value.toString());
        out.endArray();
      }
      @Override public Long read(JsonReader in) throws IOException {
        in.beginArray();
        long value = in.nextLong();
        in.endArray();
        return value;
      }
    };

    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
      Class<? super T> cls = type.getRawType();
      if (Long.class.isAssignableFrom(cls) || long.class.isAssignableFrom(cls)) {
        return (TypeAdapter<T>) ADAPTER;
      }
      throw new IllegalStateException("Non-long field of type " + type
          + " annotated with @JsonAdapter(LongToStringArrayTypeAdapterFactory.class)");
    }
  }
}