package com.google.gson.stream;

import com.google.gson.JsonElement;
import com.google.gson.internal.bind.JsonTreeWriter;
import java.io.IOException;
import java.io.StringWriter;
import junit.framework.TestCase;

public final class JsonWriterBoxedBooleanRegressionTest extends TestCase {

  public void testBoxedBooleans() throws IOException {
    StringWriter stringWriter = new StringWriter();
    JsonWriter jsonWriter = new JsonWriter(stringWriter);
    jsonWriter.beginArray();
    jsonWriter.value((Boolean) true);
    jsonWriter.value((Boolean) false);
    jsonWriter.value((Boolean) null);
    jsonWriter.endArray();
    assertEquals("[true,false,null]", stringWriter.toString());
  }

  public void testBoxedBooleansWithJsonTreeWriter() throws IOException {
    JsonTreeWriter writer = new JsonTreeWriter();
    writer.beginArray();
    writer.value((Boolean) true);
    writer.value((Boolean) false);
    writer.value((Boolean) null);
    writer.endArray();
    JsonElement result = writer.get();
    assertEquals("[true,false,null]", result.toString());
  }
}