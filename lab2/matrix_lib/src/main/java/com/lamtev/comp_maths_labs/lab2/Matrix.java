package com.lamtev.comp_maths_labs.lab2;

import org.apache.commons.lang3.mutable.MutableDouble;

import java.util.Arrays;

public final class Matrix {

    private final double[][] matrix;
    private final double normAsMaximumAbsoluteColumnSum;
    private final double normAsMaximumAbsoluteRowSum;

    public static Matrix ZeroMatrix(int diagonal) {
        return new Matrix(new double[diagonal][diagonal]);
    }

    public static Matrix IdentityMatrix(int diagonal) {
        final double[][] matrix = new double[diagonal][diagonal];
        for (int i = 0; i < diagonal; ++i) {
            matrix[i][i] = 1;
        }
        return new Matrix(matrix);
    }

    public Matrix(final double[][] matrix) {
        verifyConsistency(matrix);
        this.matrix = Arrays.copyOf(matrix, matrix.length);
        normAsMaximumAbsoluteRowSum = calculateNormAsMaximumAbsoluteRowSum();
        normAsMaximumAbsoluteColumnSum = calculateNormAsMaximumAbsoluteColumnSum();
    }

    public int rowsNumber() {
        return matrix.length;
    }

    public int columnsNumber() {
        return matrix[0].length;
    }

    public boolean isSquare() {
        return rowsNumber() == columnsNumber();
    }

    public boolean isSingular() {
        verifyMatrixIsSquare("Only square matrix can be singular");
        return determinant() == 0;
    }

    public double determinant() {
        verifyMatrixIsSquare("Determinant calculation is able only for square matrix");
        double[][] lu = Arrays.copyOf(matrix, rowsNumber());
        MatrixUtil.decomp(lu, new MutableDouble(), new int[rowsNumber()], new double[rowsNumber()]);
        double determinant = 1;
        for (int i = 0; i < rowsNumber(); ++i) {
            determinant *= lu[i][i];
        }
        return determinant;
    }

    public double normAsMaximumAbsoluteColumnSum() {
        return normAsMaximumAbsoluteColumnSum;
    }

    public double normAsMaximumAbsoluteRowSum() {
        return normAsMaximumAbsoluteRowSum;
    }

    public Matrix plus(final Matrix matrix) {

        final double[][] sum = new double[rowsNumber()][columnsNumber()];
        for (int i = 0; i < rowsNumber(); ++i) {
            for (int j = 0; j < columnsNumber(); ++j) {
                sum[i][j] = this.matrix[i][j] + matrix.matrix[i][j];
            }
        }
        return new Matrix(sum);
    }

    public Matrix minus(final Matrix matrix) {
        verifyDimensionsAreTheSame(matrix);
        final double[][] difference = new double[rowsNumber()][columnsNumber()];
        for (int i = 0; i < rowsNumber(); ++i) {
            for (int j = 0; j < columnsNumber(); ++j) {
                difference[i][j] = this.matrix[i][j] - matrix.matrix[i][j];
            }
        }
        return new Matrix(difference);
    }

    public Matrix multiply(final Matrix matrix) {
        if (columnsNumber() != matrix.rowsNumber()) {
            throw new UnsupportedOperationException("Matrices are not agreed");
        }
        final double[][] composition = new double[rowsNumber()][matrix.columnsNumber()];
        for (int i = 0; i < rowsNumber(); ++i) {
            for (int j = 0; j < matrix.columnsNumber(); ++j) {
                for (int k = 0; k < columnsNumber(); ++k) {
                    composition[i][j] += this.matrix[i][k] * matrix.matrix[k][j];
                }
            }
        }
        return new Matrix(composition);
    }

    public Matrix invert() {
        verifyMatrixIsSquare("Inversion is able only for square matrices");
        final int diagonal = rowsNumber();
        double[][] inverted = new double[diagonal][diagonal];
        double[] work = new double[diagonal];
        int[] indexes = new int[diagonal];
        double[] vector = new double[diagonal];
        for (int i = 0; i < diagonal; i++) {
            double[][] lu = Arrays.copyOf(matrix, diagonal);
            vector[i] = 1.0;
            MatrixUtil.decomp(lu, new MutableDouble(), indexes, work);
            MatrixUtil.solve(lu, vector, indexes);
            for (int j = 0; j < diagonal; j++) {
                inverted[j][i] = vector[j];
            }
            vector[i] = 0;
        }
        return new Matrix(inverted);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix matrix1 = (Matrix) o;

        return Arrays.deepEquals(matrix, matrix1.matrix);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(matrix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (double[] row : matrix) {
            for (double elem : row) {
                sb.append(elem).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void verifyConsistency(final double[][] matrix) {
        if (matrix == null) {
            throw new RuntimeException("Matrix can't be null");
        }
        for (double[] row : matrix) {
            if (row == null) {
                throw new RuntimeException("Row can't be null");
            }
            int firstRowLength = matrix[0].length;
            if (row.length != firstRowLength) {
                throw new RuntimeException("Rows must have the same length");
            }
        }
    }

    private void verifyMatrixIsSquare(String message) {
        if (!this.isSquare()) {
            throw new UnsupportedOperationException(message);
        }
    }

    private void verifyDimensionsAreTheSame(Matrix matrix) {
        if (rowsNumber() != matrix.rowsNumber() ||
                columnsNumber() != matrix.columnsNumber()) {
            throw new UnsupportedOperationException("Matrices must have the same dimensions");
        }
    }

    private double calculateNormAsMaximumAbsoluteRowSum() {
        double max = -1;
        for (int i = 0; i < rowsNumber(); ++i) {
            double sum = 0;
            for (int j = 0; j < columnsNumber(); ++j) {
                sum += Math.abs(matrix[i][j]);
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

    private double calculateNormAsMaximumAbsoluteColumnSum() {
        double max = -1;
        for (int j = 0; j < columnsNumber(); ++j) {
            double sum = 0;
            for (int i = 0; i < rowsNumber(); ++i) {
                sum += Math.abs(matrix[i][j]);
            }
            if (sum > max) {
                max = sum;
            }
        }

        return max;
    }

}
