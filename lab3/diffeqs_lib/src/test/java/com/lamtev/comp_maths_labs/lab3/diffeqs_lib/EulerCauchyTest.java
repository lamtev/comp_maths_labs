package com.lamtev.comp_maths_labs.lab3.diffeqs_lib;

import org.junit.Test;

import java.util.Arrays;

import static com.lamtev.comp_maths_labs.lab3.diffeqs_lib.EulerCauchy.eulerCauchy;
import static com.lamtev.comp_maths_labs.lab3.diffeqs_lib.TestUtil.*;
import static org.junit.Assert.assertEquals;

public class EulerCauchyTest {

    @Test
    public void testEulerCauchy() {
        double x[] = Arrays.copyOf(INITIAL_CONDITIONS, N);
        double exactX[] = new double[N];
        for (double t = (double) 1; t <= (double) 2; t += 0.1) {
            exactX[0] = exactY(t);
            exactX[1] = exactDY(t);
            for (int i = 0; i < N; ++i) {
                assertEquals(exactX[i], x[i], 0.1);
            }
            eulerCauchy(FUN, N, t, x, 0.1);
        }
    }

}