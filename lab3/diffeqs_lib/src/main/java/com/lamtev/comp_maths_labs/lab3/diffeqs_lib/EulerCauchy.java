package com.lamtev.comp_maths_labs.lab3.diffeqs_lib;

import static com.lamtev.comp_maths_labs.lab3.diffeqs_lib.DiffeqsUtil.Fun;

public class EulerCauchy {

    public static void eulerCauchy(Fun fun, int n, double t, double[] x, double h) {

        double[] dx = calculateDx(fun, n, t, x);
        double[] nextX = new double[n];

        euler(n, x, h, dx, nextX);

        double[] nextDx = calculateDx(fun, n, t, nextX);

        trapezoidalRule(n, x, h, dx, nextX, nextDx);

        System.arraycopy(nextX, 0, x, 0, n);

    }

    private static void euler(int n, double[] x, double h, double[] dx, double[] nextX) {
        for (int i = 0; i < n; ++i) {
            nextX[i] = x[i] + h * dx[i];
        }
    }

    private static void trapezoidalRule(int n, double[] x, double h,
                                        double[] dx, double[] nextX, double[] nextDx) {
        for (int i = 0; i < n; ++i) {
            nextX[i] = x[i] + h / 2 * (dx[i] + nextDx[i]);
        }
    }

    private static double[] calculateDx(Fun fun, int n, double t, double[] x) {
        double[] dx = new double[n];
        fun.perform(t, x, dx);
        return dx;
    }

}
