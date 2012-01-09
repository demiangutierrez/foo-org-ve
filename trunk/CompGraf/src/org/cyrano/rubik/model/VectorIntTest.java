package org.cyrano.rubik.model;

import junit.framework.TestCase;

public class VectorIntTest extends TestCase {

  // --------------------------------------------------------------------------------

  public void testXcc() throws Exception {
    VectorInt v = new VectorInt(1, 1, 1);

    int[][] transform = //
    Transforms.getTransform(Axis.X_POS, Turn.CC);

    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(-1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(-1, v.y);
    assertEquals(-1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(-1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
  }

  // --------------------------------------------------------------------------------

  public void testXcw() throws Exception {
    VectorInt v = new VectorInt(1, 1, 1);

    int[][] transform = //
    Transforms.getTransform(Axis.X_POS, Turn.CW);

    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(-1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(-1, v.y);
    assertEquals(-1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(-1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
  }

  // --------------------------------------------------------------------------------

  public void testYcc() throws Exception {
    VectorInt v = new VectorInt(1, 1, 1);

    int[][] transform = //
    Transforms.getTransform(Axis.Y_POS, Turn.CC);

    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(-1, v.z);
    v.transform(transform);
    assertEquals(-1, v.x);
    assertEquals(+1, v.y);
    assertEquals(-1, v.z);
    v.transform(transform);
    assertEquals(-1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
  }

  // --------------------------------------------------------------------------------

  public void testYcw() throws Exception {
    VectorInt v = new VectorInt(1, 1, 1);

    int[][] transform = //
    Transforms.getTransform(Axis.Y_POS, Turn.CW);

    v.transform(transform);
    assertEquals(-1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(-1, v.x);
    assertEquals(+1, v.y);
    assertEquals(-1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(-1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
  }

  // --------------------------------------------------------------------------------

  public void testZcc() throws Exception {
    VectorInt v = new VectorInt(1, 1, 1);

    int[][] transform = //
    Transforms.getTransform(Axis.Z_POS, Turn.CC);

    v.transform(transform);
    assertEquals(-1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(-1, v.x);
    assertEquals(-1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(-1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
  }

  // --------------------------------------------------------------------------------

  public void testZcw() throws Exception {
    VectorInt v = new VectorInt(1, 1, 1);

    int[][] transform = //
    Transforms.getTransform(Axis.Z_POS, Turn.CW);

    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(-1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(-1, v.x);
    assertEquals(-1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(-1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
    v.transform(transform);
    assertEquals(+1, v.x);
    assertEquals(+1, v.y);
    assertEquals(+1, v.z);
  }
}
