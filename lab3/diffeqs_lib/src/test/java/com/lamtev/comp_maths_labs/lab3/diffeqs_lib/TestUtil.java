package com.lamtev.comp_maths_labs.lab3.diffeqs_lib;


public class TestUtil {

    static final int N = 2;

    static final double[] INITIAL_CONDITIONS = {1, -1};

    static final DiffeqsUtil.Fun FUN = (t, X, DX) -> {
        DX[0] = 0 * X[0] + 1 * X[1];
        DX[1] = -(1 / (t * (t + 1))) * X[0] - (3 * t + 2) / (t * (t + 1)) * X[1];
    };

    static double exactY(double t) {
        return 1 / t;
    }

    static double exactDY(double t) {
        return -1 / (t * t);
    }

}
