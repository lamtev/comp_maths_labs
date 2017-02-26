package com.lamtev.comp_maths_labs.lab2;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.apache.commons.lang3.mutable.MutableDouble;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//TODO migrate from JNA to JNR
class MatrixUtil {

    public static void decomp(double[][] matrix, MutableDouble cond, int[] iptv, double[] work) {
        final int ORDER = matrix.length;

        double[] data = getMatrixAsLinearArray(matrix, ORDER);

        Pointer condPtr = getCondAsPointer(cond);

        ForsytheMatrixLibrary.INSTANCE.decomp(ORDER, data, condPtr, iptv, work);
        updateMatrix(matrix, ORDER, data);
        cond.setValue(condPtr.getDouble(0));
        condPtr.clear(Double.BYTES);
    }

    public static void solve(double[][] matrix, double[] vector, int[] ipvt) {
        final int ORDER = matrix.length;
        double[] data = getMatrixAsLinearArray(matrix, ORDER);
        ForsytheMatrixLibrary.INSTANCE.solve(ORDER, data, vector, ipvt);
    }

    private static double[] getMatrixAsLinearArray(double[][] matrix, int ORDER) {
        double[] data = new double[ORDER * ORDER];
        int i = 0;
        for (double[] row : matrix) {
            for (double x : row) {
                data[i++] = x;
            }
        }
        return data;
    }

    private static Pointer getCondAsPointer(MutableDouble cond) {
        Pointer condPtr = new Memory(Double.BYTES);
        double[] condArr = new double[1];
        condArr[0] = cond.doubleValue();
        condPtr.write(0, condArr, 0, 1);
        return condPtr;
    }

    private static void updateMatrix(double[][] matrix, int ORDER, double[] data) {
        int k = 0;
        for (int i = 0; i < ORDER; ++i) {
            for (int j = 0; j < ORDER; ++j) {
                matrix[i][j] = data[k++];
            }
        }
    }

    private static final String FORSYTHE_MATRIX_LIBRARY_PREFIX = "libforsythe_matrix";

    private interface ForsytheMatrixLibrary extends Library {
        ForsytheMatrixLibrary INSTANCE = (ForsytheMatrixLibrary)
                Native.loadLibrary(pathToLib(), ForsytheMatrixLibrary.class);

        void decomp(int n, double[] a, Pointer cond, int[] ipvt, double[] work);

        void solve(int n, double[] a, double[] b, int[] ipvt);

    }

    private static String pathToLib() {
        Path start = Paths.get("").toAbsolutePath();
        try {
            return Files.walk(start)
                    .filter(MatrixUtil::isLibrary)
                    .findFirst().get().toAbsolutePath().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isLibrary(Path path) {
        return path.getFileName().toString().contains(FORSYTHE_MATRIX_LIBRARY_PREFIX);
    }

}