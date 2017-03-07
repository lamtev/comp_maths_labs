package com.lamtev.comp_maths_labs.lab2.app;

import com.lamtev.comp_maths_labs.lab2.matrix_lib.Matrix;

import static java.lang.StrictMath.*;

public final class Lab2 {

    public static void main(String[] args) {
        doLab2();
    }

    private static void doLab2() {
        final int N = 5;
        final double[] EPSILONS = {
                1e-1, 1e-2, 1e-3, 1e-4, 1e-5, 1e-6,
                1e-7, 1e-8, 1e-9, 1e-10, 1e-11, 1e-12,
                1e-13, 1e-14, 1e-15, 1e-16, 1e-64, 0
        };

        for (double epsilon : EPSILONS) {

            Matrix A = generateMatrix(N, epsilon);
            Matrix A_INVERTED = A.inverted();
            Matrix R = A_INVERTED.multiply(A).minus(Matrix.Identity(5));

            printResult(epsilon, A, R);
        }
    }

    private static Matrix generateMatrix(int n, double epsilon) {
        double[][] matrixArray = new double[n][n];

        for (int j = 0; j < n; ++j) {
            matrixArray[0][j] = 1;
        }

        for (int i = 1; i < n; ++i) {
            for (int k = 0; k < n - 1; ++k) {
                double x_k = (1 + cos((k + 1))) / (pow(sin((k + 1)), 2));
                matrixArray[i][k] = pow(x_k, i);
            }
        }

        for (int i = 1; i < n; ++i) {
            double x_k = (1 + cos(1)) / (pow(sin(1 + epsilon), 2));
            matrixArray[i][n - 1] = pow(x_k, i);
        }

        return new Matrix(matrixArray);
    }

    private static void printResult(double epsilon, Matrix a, Matrix r) {
        System.out.println(
                "epsilon = " + epsilon + "\n" +
                        "||R|| = " + r.normAsMaximumAbsoluteColumnSum()
        );
        System.out.println("Matrix A:");
        System.out.println(a);
        System.out.println("Matrix R = A\u207B\u00B9A:");
        System.out.println(r);
        System.out.println();
    }

}
