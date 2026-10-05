package com.google.debugging.sourcemap;

import com.google.debugging.sourcemap.proto.Mapping.OriginalMapping;

import junit.framework.TestCase;

/**
 * Regression test: the V3 consumer must return 1-based line and column
 * numbers for the original mapping, even though the V3 format stores
 * them 0-based.
 */
public class SourceMapConsumerV3OffsetTest extends TestCase {

  public void testOriginalMappingIsOneBased() throws Exception {
    String sourceMap = "{\n"
        + "\"version\":3,\n"
        + "\"file\":\"out.js\",\n"
        + "\"lineCount\":1,\n"
        + "\"mappings\":\"AAAA\",\n"
        + "\"sources\":[\"a.js\"],\n"
        + "\"names\":[]\n"
        + "}\n";

    SourceMapConsumerV3 consumer = new SourceMapConsumerV3();
    consumer.parse(sourceMap);

    // Query is 1-based: first line, first column of the generated output.
    OriginalMapping mapping = consumer.getMappingForLine(1, 1);

    assertNotNull(mapping);
    assertEquals("a.js", mapping.getOriginalFile());
    // The segment "AAAA" encodes 0-based source line 0 and column 0,
    // which must be reported as line 1, column 1.
    assertEquals(1, mapping.getLineNumber());
    assertEquals(1, mapping.getColumnPosition());
  }
}