package com.lamtev.comp_maths_labs.lab2;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import org.apache.commons.lang3.mutable.MutableDouble;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class MatrixUtil {

    public static void decomp(double[][] matrix, MutableDouble cond, int[] iptv, double[] work) {
        final int ORDER = matrix.length;

        double[] data = getMatrixAsLinearArray(matrix, ORDER);

        Pointer condPtr = getCondAsPointer(cond);

        ForsytheMatrixLibrary.INSTANCE.decomp(ORDER, data, condPtr, iptv, work);
        updateMatrix(matrix, ORDER, data);
        cond.setValue(condPtr.getDouble(0));
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
        Pointer condPtr = Memory.allocate(
                Runtime.getRuntime(ForsytheMatrixLibrary.INSTANCE),
                Double.BYTES
        );
        double[] condArr = new double[1];
        condArr[0] = cond.doubleValue();
        condPtr.put(0, condArr, 0, 1);
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

    public interface ForsytheMatrixLibrary {

        ForsytheMatrixLibrary INSTANCE = LibraryLoader.create(
                ForsytheMatrixLibrary.class).load(pathToLib()
        );

        void decomp(int n, double[] a, Pointer cond, int[] ipvt, double[] work);

        void solve(int n, double[] a, double[] b, int[] ipvt);

    }

    private static String pathToLib() {
        Path start = Paths.get("").toAbsolutePath();
        try {
            Optional<Path> path = Files.walk(start).parallel()
                    .filter(MatrixUtil::isLibrary)
                    .findFirst();
            if (path.isPresent()) {
                return path.get().toAbsolutePath().toString();
            }
            throw new FileNotFoundException(FORSYTHE_MATRIX_LIBRARY_PREFIX + " shared lib not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isLibrary(Path path) {
        return path.getFileName().toString().contains(FORSYTHE_MATRIX_LIBRARY_PREFIX);
    }

}