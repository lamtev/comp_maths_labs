package com.lamtev.comp_maths_labs.lab3.diffeqs_lib;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;

import java.util.Arrays;

import static com.lamtev.comp_maths_labs.lab3.diffeqs_lib.DiffeqsUtil.*;
import static org.junit.Assert.assertEquals;

public class EulerCauchyTest {

    private static final int N = 2;

    private static final double[] INITIAL_CONDITIONS_1 = {1, -1};

    private static final double[] INITIAL_CONDITIONS_2 = {0.25, -0.0625};

    private static final Fun FUN = (t, X, DX) -> {
        DX[0] = 0 * X[0] + 1 * X[1];
        DX[1] = -(1 / (t * (t + 1))) * X[0] - (3 * t + 2) / (t * (t + 1)) * X[1];
    };

    @Test
    public void testEulerCauchy() {
        test(1, 2, 0.1, INITIAL_CONDITIONS_1);
        test(4, 5, 0.1, INITIAL_CONDITIONS_2);
    }

    private void test(double left, double right, double step, double[] initialConditions) {
        MutableDouble tRKF = new MutableDouble(1);
        MutableDouble tOut = new MutableDouble();
        MutableDouble relErr = new MutableDouble(0);
        MutableDouble absErr = new MutableDouble(0);
        MutableInt iFlag = new MutableInt(1);
        double[] work = new double[15];
        int[] iWork = new int[5];

        double[] xEulerCauchy = Arrays.copyOf(initialConditions, N);
        double[] xRKF = Arrays.copyOf(initialConditions, N);
        double tEulerCauchy = left;

        for (double i = left; i <= right + step; i += step) {
            tOut.setValue(i);
            RKF45.rkf45(FUN, N, xRKF, tRKF, tOut, relErr, absErr, iFlag, work, iWork);
            for (int j = 0; j < N; ++j) {
                assertEquals(xRKF[j], xEulerCauchy[j], 0.1);
            }
            EulerCauchy.eulerCauchy(FUN, N, tEulerCauchy, xEulerCauchy, step);
            tEulerCauchy += step;
        }
    }

}