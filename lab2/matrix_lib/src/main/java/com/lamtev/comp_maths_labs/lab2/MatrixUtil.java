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

class MatrixUtil {

    static void decomp(double[][] matrix, MutableDouble cond, int[] iptv, double[] work) {
        final int DIMENSION = matrix.length;
        Pointer[] data = getMatrixAsPointers(matrix, DIMENSION);
        Pointer condPtr = getCondPointer(cond);

        ForsytheMatrixLibrary.INSTANCE.decomp(DIMENSION, data, condPtr, iptv, work);

        cond.setValue(condPtr.getDouble(0));
        updateMatrix(matrix, DIMENSION, data);
    }

    static void solve(double[][] matrix, double[] vector, int[] ipvt) {
        final int DIMENSION = matrix.length;
        Pointer[] data = getMatrixAsPointers(matrix, DIMENSION);
        ForsytheMatrixLibrary.INSTANCE.solve(DIMENSION, data, vector, ipvt);
        updateMatrix(matrix, DIMENSION, data);
    }

    private interface ForsytheMatrixLibrary extends Library {
        ForsytheMatrixLibrary INSTANCE = (ForsytheMatrixLibrary)
                Native.loadLibrary(pathToLib(), ForsytheMatrixLibrary.class);

        void decomp(int n, Pointer[] a, Pointer cond, int[] ipvt, double[] work);

        void solve(int n, Pointer[] a, double[] b, int[] ipvt);

    }

    private static Pointer getCondPointer(MutableDouble cond) {
        Pointer condPtr = new Memory(Double.BYTES);
        double[] condArr = new double[1];
        condArr[0] = cond.doubleValue();
        condPtr.write(0, condArr, 0, 1);
        return condPtr;
    }

    private static Pointer[] getMatrixAsPointers(double[][] matrix, int DIMENSION) {
        Pointer[] data = new Pointer[DIMENSION];
        for (int i = 0; i < DIMENSION; ++i) {
            data[i] = new Memory(DIMENSION * Double.BYTES);
            data[i].write(0, matrix[i], 0, DIMENSION);
        }
        return data;
    }

    private static void updateMatrix(double[][] matrix, int DIMENSION, Pointer[] data) {
        for (int i = 0; i < DIMENSION; ++i) {
            matrix[i] = data[i].getDoubleArray(0, DIMENSION);
        }
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

    private static final String FORSYTHE_MATRIX_LIBRARY = "libforsythe_matrix";

    private static boolean isLibrary(Path path) {
        return path.getFileName().toString().contains(FORSYTHE_MATRIX_LIBRARY);
    }

}