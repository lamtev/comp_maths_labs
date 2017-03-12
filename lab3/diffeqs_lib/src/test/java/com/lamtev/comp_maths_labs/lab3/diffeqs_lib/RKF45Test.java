package com.lamtev.comp_maths_labs.lab3.diffeqs_lib;

import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;

import java.util.Arrays;

import static com.lamtev.comp_maths_labs.lab3.diffeqs_lib.TestUtil.*;
import static org.junit.Assert.assertEquals;

public class RKF45Test {

    @Test
    public void testRKF45() {
        test(1, 2, 0.1, INITIAL_CONDITIONS);
    }

    private void test(double left, double right, double step, double[] initialConditions) {
        MutableDouble t = new MutableDouble(1);
        MutableDouble tOut = new MutableDouble();
        MutableDouble relErr = new MutableDouble(0.00001);
        MutableDouble absErr = new MutableDouble(0.00001);
        MutableInt iFlag = new MutableInt(1);
        double[] work = new double[15];
        int[] iWork = new int[5];

        double[] xRKF = Arrays.copyOf(initialConditions, N);

        double[] exactX = new double[N];

        for (double i = left; i <= right + step; i += step) {
            exactX[0] = exactY(i);
            exactX[1] = exactDY(i);
            tOut.setValue(i);
            RKF45.rkf45(FUN, N, xRKF, t, tOut, relErr, absErr, iFlag, work, iWork);
            for (int j = 0; j < N; ++j) {
                assertEquals(xRKF[j], exactX[j], 0.00001);
            }

        }
    }


}