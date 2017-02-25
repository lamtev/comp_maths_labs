package com.lamtev.comp_maths_labs.lab2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatrixTest {

    private Matrix generateMatrix1() {
        double[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };
        return new Matrix(matrix);
    }

    private Matrix generateMatrix2() {
        double[][] matrix = {
                {5, -3, 1, 8},
                {6, 21, -1, 17},
                {13, 4, 0, -2},
                {-1, -2, -3, -5}
        };
        return new Matrix(matrix);
    }

    @Test
    public void testNormAsMaximumAbsoluteColumnSum() {
        assertEquals(4, generateMatrix1().columnsNumber());
    }

    @Test
    public void testNormAsMaximumAbsoluteRowSum() {
        assertEquals(4, generateMatrix1().rowsNumber());
    }

    @Test
    public void testDeterminant() {
        assertEquals(0, generateMatrix1().determinant(), 0.001);
        assertEquals(1, Matrix.IdentityMatrix(3).determinant(), 0.001);
    }

    @Test
    public void testPlus() {
        Matrix actual = generateMatrix1().plus(generateMatrix2());
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
        assertEquals(Matrix.ZeroMatrix(4), generateMatrix1().minus(generateMatrix1()));
    }

    @Test
    public void testMultiply() {
        assertEquals(Matrix.IdentityMatrix(5), Matrix.IdentityMatrix(5).multiply(Matrix.IdentityMatrix(5)));
        Matrix actual = generateMatrix1().multiply(generateMatrix2());
        Matrix expected = new Matrix(new double[][]{
                {52, 43, -13, 16},
                {144, 123, -25, 88},
                {236, 203, -37, 160},
                {328, 283, -49, 232}
        });
        assertEquals(expected, actual);
    }

    @Test
    public void testInvert() {
        assertEquals(Matrix.IdentityMatrix(5), Matrix.IdentityMatrix(5).invert());
    }

}