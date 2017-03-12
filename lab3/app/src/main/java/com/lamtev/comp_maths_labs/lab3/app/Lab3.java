package com.lamtev.comp_maths_labs.lab3.app;

import com.lamtev.comp_maths_labs.lab3.diffeqs_lib.DiffeqsUtil.Fun;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.Arrays;
import java.util.Locale;

import static com.lamtev.comp_maths_labs.lab3.diffeqs_lib.EulerCauchy.eulerCauchy;
import static com.lamtev.comp_maths_labs.lab3.diffeqs_lib.RKF45.rkf45;

public class Lab3 {

    private static final int N = 2;

    private static final double[] INITIAL_CONDITIONS = {1, -1};

    private static final double LEFT = 1;

    private static final double RIGHT = 2;

    private static final double STEP = 0.1;

    private static final Fun FUN = (t, X, DX) -> {
        DX[0] = 0 * X[0] + 1 * X[1];
        DX[1] = -(1 / (t * (t + 1))) * X[0] - (3 * t + 2) / (t * (t + 1)) * X[1];
    };

    public static void main(String[] args) {
        rkf45Demo();
        System.out.println();
        eulerCauchyDemo();
    }

    private static void eulerCauchyDemo() {
        double x[] = Arrays.copyOf(INITIAL_CONDITIONS, N);
        System.out.println("Euler-Cauchy");
        printTAndX();
        printTAndXValues(LEFT, x);
        for (double t = LEFT; t <= RIGHT; t += STEP) {
            eulerCauchy(FUN, N, t, x, STEP);
            printTAndXValues(t + STEP, x);
        }
    }

    private static void rkf45Demo() {
        double x[] = Arrays.copyOf(INITIAL_CONDITIONS, N);
        MutableDouble t = new MutableDouble(1);
        MutableDouble tOut = new MutableDouble();
        MutableDouble relErr = new MutableDouble(0);
        MutableDouble absErr = new MutableDouble(0);
        MutableInt iFlag = new MutableInt(1);
        double[] work = new double[15];
        int[] iWork = new int[5];

        System.out.println("RKF45");
        printTAndX();
        for (double tout = LEFT; tout <= RIGHT + STEP; tout += STEP) {
            tOut.setValue(tout);
            rkf45(FUN, N, x, t, tOut, relErr, absErr, iFlag, work, iWork);
            printTAndXValues(t.getValue(), x);
        }
    }

    private static void printTAndX() {
        System.out.println("t\t\tx");
    }

    private static void printTAndXValues(double t, double[] x) {
        System.out.format(Locale.US, "%1.1f\t\t", t);
        System.out.format(Locale.US, "(%1.5f, ", x[0]);
        System.out.format(Locale.US, "%1.5f)\n", x[1]);
    }


}
