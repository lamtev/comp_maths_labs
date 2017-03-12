package com.lamtev.comp_maths_labs.lab3.diffeqs_lib;

import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableInt;

import static com.lamtev.comp_maths_labs.lab3.diffeqs_lib.DiffeqsUtil.ForsytheRKF45Library;
import static com.lamtev.comp_maths_labs.lab3.diffeqs_lib.DiffeqsUtil.Fun;

public class RKF45 {
    public static void rkf45(Fun fun,
                             int n, double[] X,
                             MutableDouble t, MutableDouble tOut,
                             MutableDouble relErr, MutableDouble absErr,
                             MutableInt iFlag, double[] work, int[] iWork) {
        ForsytheRKF45Library.Fun adaptedFun = adaptFun(fun, n);
        DoubleByReference tRef = new DoubleByReference(t.getValue());
        DoubleByReference tOutRef = new DoubleByReference(tOut.getValue());
        DoubleByReference relErrRef = new DoubleByReference(relErr.getValue());
        DoubleByReference absErrRef = new DoubleByReference(absErr.getValue());
        IntByReference iFlagRef = new IntByReference(iFlag.getValue());

        ForsytheRKF45Library.INSTANCE.rkf45(adaptedFun, n, X, tRef, tOutRef,
                relErrRef, absErrRef, iFlagRef, work, iWork);

        t.setValue(tRef.getValue());
        tOut.setValue(tOutRef.getValue());
        relErr.setValue(relErrRef.getValue());
        absErr.setValue(absErrRef.getValue());
        iFlag.setValue(iFlagRef.getValue());
    }

    private static ForsytheRKF45Library.Fun adaptFun(Fun fun, int n) {
        return (t, x, dx) -> {
            double[] dxArray = dx.getDoubleArray(0, n);
            double[] xArray = x.getDoubleArray(0, n);
            fun.perform(t, xArray, dxArray);
            dx.write(0, dxArray, 0, 2);
        };
    }

}
