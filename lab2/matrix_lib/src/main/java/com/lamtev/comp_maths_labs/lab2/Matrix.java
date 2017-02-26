package com.lamtev.comp_maths_labs.lab2;

import org.apache.commons.lang3.mutable.MutableDouble;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;

public final class Matrix {

    private final double[][] matrix;
    private final double normAsMaximumAbsoluteColumnSum;
    private final double normAsMaximumAbsoluteRowSum;

    public static Matrix Zero(int order) {
        return new Matrix(new double[order][order]);
    }

    public static Matrix Identity(int order) {
        final double[][] matrix = new double[order][order];
        for (int i = 0; i < order; ++i) {
            matrix[i][i] = 1;
        }
        return new Matrix(matrix);
    }

    public Matrix(final double[][] matrix) {
        verifyConsistency(matrix);
        this.matrix = copyOf(matrix);
        normAsMaximumAbsoluteRowSum = calculateNormAsMaximumAbsoluteRowSum();
        normAsMaximumAbsoluteColumnSum = calculateNormAsMaximumAbsoluteColumnSum();
    }

    public int rows() {
        return matrix.length;
    }

    public int columns() {
        return matrix[0].length;
    }

    public boolean isSquare() {
        return rows() == columns();
    }

    public boolean isSingular() {
        verifyMatrixIsSquare("Only square matrix can be singular");
        return determinant() == 0;
    }

    public double determinant() {
        verifyMatrixIsSquare("Determinant calculation is able only for square matrix");
        double[][] lu = copyOf(matrix);
        MatrixUtil.decomp(
                lu,
                new MutableDouble(),
                new int[rows()],
                new double[rows()]
        );
        double determinant = 1;
        for (int i = 0; i < rows(); ++i) {
            determinant *= lu[i][i];
        }
        return determinant;
    }

    public double cond() {
        MutableDouble cond = new MutableDouble();
        MatrixUtil.decomp(
                copyOf(matrix),
                cond,
                new int[rows()],
                new double[rows()]
        );
        return cond.getValue();
    }

    public double normAsMaximumAbsoluteColumnSum() {
        return normAsMaximumAbsoluteColumnSum;
    }

    public double normAsMaximumAbsoluteRowSum() {
        return normAsMaximumAbsoluteRowSum;
    }

    public Matrix plus(final Matrix matrix) {

        final double[][] sum = new double[rows()][columns()];
        for (int i = 0; i < rows(); ++i) {
            for (int j = 0; j < columns(); ++j) {
                sum[i][j] = this.matrix[i][j] + matrix.matrix[i][j];
            }
        }
        return new Matrix(sum);
    }

    public Matrix minus(final Matrix matrix) {
        verifyDimensionsAreTheSame(matrix);
        final double[][] difference = new double[rows()][columns()];
        for (int i = 0; i < rows(); ++i) {
            for (int j = 0; j < columns(); ++j) {
                difference[i][j] = this.matrix[i][j] - matrix.matrix[i][j];
            }
        }
        return new Matrix(difference);
    }

    public Matrix multiply(final Matrix matrix) {
        if (columns() != matrix.rows()) {
            throw new UnsupportedOperationException("Matrices are not agreed");
        }
        final double[][] composition = new double[rows()][matrix.columns()];
        for (int i = 0; i < rows(); ++i) {
            for (int j = 0; j < matrix.columns(); ++j) {
                for (int k = 0; k < columns(); ++k) {
                    composition[i][j] += this.matrix[i][k] * matrix.matrix[k][j];
                }
            }
        }
        return new Matrix(composition);
    }

    public Matrix transposed() {
        double[][] matrixArray = new double[columns()][rows()];
        for (int i = 0; i < columns(); ++i) {
            for (int j = 0; j < rows(); ++j) {
                matrixArray[i][j] = matrix[j][i];
            }
        }
        return new Matrix(matrixArray);
    }

    public Matrix inverted() {
        verifyMatrixIsSquare("Inversion is able for square matrices only");
        verifyMatrixIsNonsingular();
        final int ORDER = rows();

        double[][] lu = copyOf(matrix);
        double[][] inverted = new double[ORDER][ORDER];
        double[] work = new double[ORDER];
        int[] ipvt = new int[ORDER];

        MatrixUtil.decomp(lu, new MutableDouble(), ipvt, work);

        for (int i = 0; i < ORDER; ++i) {

            double[] vector = new double[ORDER];
            vector[i] = 1;
            MatrixUtil.solve(copyOf(lu), vector, ipvt);

            for (int j = 0; j < ORDER; ++j) {
                inverted[j][i] = vector[j];
            }

        }

        return new Matrix(inverted);
    }

    public boolean equals(Matrix matrix, double delta) {
        if (this == matrix) return true;
        if (matrix == null) return false;
        if (rows() != matrix.rows() || columns() != matrix.columns()) return false;
        for (int i = 0; i < rows(); ++i) {
            for (int j = 0; j < columns(); ++j) {
                if (this.matrix[i][j] - matrix.matrix[i][j] >= delta) return false;
            }
        }
        return true;
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
        Locale.setDefault(new Locale("en_US"));
        DecimalFormat decimalFormat = new DecimalFormat("0.#####E0");
        StringBuilder sb = new StringBuilder();
        int maxLen = 0;
        for (double[] row : matrix) {
            for (Double elem : row) {
                int currLen = (
                        elem.isNaN() || elem.isInfinite() ?
                        elem.toString() :
                        decimalFormat.format(elem.doubleValue())
                ).length();

                if (currLen > maxLen) {
                    maxLen = currLen;
                }
            }
        }
        for (double[] row : matrix) {
            for (Double elem : row) {
                String el = elem.isNaN() || elem.isInfinite() ?
                        elem.toString() :
                        decimalFormat.format(elem.doubleValue());
                sb.append(el);
                for (int i = 0; i < maxLen - el.length() + 2; ++i) {
                    sb.append(" ");
                }
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

    private double calculateNormAsMaximumAbsoluteRowSum() {
        double max = -1;
        for (int i = 0; i < rows(); ++i) {
            double sum = 0;
            for (int j = 0; j < columns(); ++j) {
                sum += Math.abs(matrix[i][j]);
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max == -1 ? Double.NaN : max;
    }

    private double calculateNormAsMaximumAbsoluteColumnSum() {
        double max = -1;
        for (int j = 0; j < columns(); ++j) {
            double sum = 0;
            for (int i = 0; i < rows(); ++i) {
                sum += Math.abs(matrix[i][j]);
            }
            if (sum > max) {
                max = sum;
            }
        }

        return max == -1 ? Double.NaN : max;
    }

    private void verifyMatrixIsSquare(String message) {
        if (!this.isSquare()) {
            throw new UnsupportedOperationException(message);
        }
    }

    private void verifyDimensionsAreTheSame(Matrix matrix) {
        if (rows() != matrix.rows() ||
                columns() != matrix.columns()) {
            throw new UnsupportedOperationException("Matrices must have the same dimensions");
        }
    }

    private void verifyMatrixIsNonsingular() {
        if (this.isSingular()) {
            throw new UnsupportedOperationException("Inversion is able for nonsingular matrices only");
        }
    }

    private double[][] copyOf(double[][] matrix) {
        double[][] newMatrix = new double[matrix.length][];
        for (int i = 0; i < matrix.length; ++i) {
            newMatrix[i] = new double[matrix[i].length];
            System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix[i].length);
        }
        return newMatrix;
    }

}
