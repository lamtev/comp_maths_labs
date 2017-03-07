package com.lamtev.comp_maths_labs.lab2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatrixTest {

    @Test
    public void testNormAsMaximumAbsoluteColumnSum() {
        assertEquals(4, MATRIX1.columns());
    }

    @Test
    public void testNormAsMaximumAbsoluteRowSum() {
        assertEquals(4, MATRIX1.rows());
    }

    @Test
    public void testDeterminant() {
        assertEquals(0, MATRIX1.determinant(), ERROR);
        assertEquals(1, Matrix.Identity(3).determinant(), ERROR);
        assertEquals(-9331, MATRIX2.determinant(), ERROR);
    }

    @Test
    public void testPlus() {
        Matrix actual = MATRIX1.plus(MATRIX2);
        Matrix expected = new Matrix(new double[][]{
                {6, -1, 4, 12},
                {11, 27, 6, 25},
                {22, 14, 11, 10},
                {12, 12, 12, 11}
        });
        assertEquals(expected, actual);
    }

    @Test
    public void testMinus() {
        assertEquals(Matrix.Zero(4), MATRIX1.minus(MATRIX1));
    }

    @Test
    public void testMultiply() {
        assertEquals(Matrix.Identity(5), Matrix.Identity(5).multiply(Matrix.Identity(5)));

        Matrix actual = MATRIX1.multiply(MATRIX2);
        Matrix expected = new Matrix(new double[][]{
                {52, 43, -13, 16},
                {144, 123, -25, 88},
                {236, 203, -37, 160},
                {328, 283, -49, 232}
        });
        assertEquals(expected, actual);
    }

    @Test
    public void testTransposed() {
        assertEquals(MATRIX1, MATRIX1.transposed().transposed());
        assertEquals(MATRIX2, MATRIX2.transposed().transposed());
        assertEquals(MATRIX3, MATRIX3.transposed().transposed());
    }

    @Test
    public void testInverted() {
        assertTrue(MATRIX2.equals(MATRIX2.inverted().inverted(), ERROR));
        assertTrue(MATRIX3.equals(MATRIX3.inverted().inverted(), ERROR));

        assertEquals(Matrix.Identity(5), Matrix.Identity(5).inverted());

        assertTrue(Matrix.Identity(4).equals(MATRIX2.inverted().multiply(MATRIX2), ERROR));

        assertEquals(Matrix.Identity(3), MATRIX3.multiply(MATRIX3.inverted()));

    }

    private final static double ERROR = 1e-12;

    private final static Matrix MATRIX1 = new Matrix(
            new double[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 16}
            }
    );

    private final static Matrix MATRIX2 = new Matrix(
            new double[][]{
                    {5, -3, 1, 8},
                    {6, 21, -1, 17},
                    {13, 4, 0, -2},
                    {-1, -2, -3, -5}
            }
    );

    private final static Matrix MATRIX3 = new Matrix(
            new double[][]{
                    {1, 2, 3},
                    {3, 2, 1},
                    {4, 5, 7}
            }
    );


}