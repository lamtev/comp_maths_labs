package com.lamtev.comp_maths_labs.lab3.diffeqs_lib;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class DiffeqsUtil {

    public interface Fun {
        void perform(double t, double[] x, double[] dx);
    }

    private static final String FORSYTHE_RKF45_LIBRARY_PREFIX = "forsythe_rkf45";

    interface ForsytheRKF45Library extends Library {

        ForsytheRKF45Library INSTANCE = (ForsytheRKF45Library)
                Native.loadLibrary(pathToLib(), ForsytheRKF45Library.class);

        interface Fun extends Callback {
            void perform(double t, Pointer x, Pointer dx);
        }

        void rkf45(Fun fun, int n, double[] x, DoubleByReference t, DoubleByReference tOut,
                DoubleByReference relErr, DoubleByReference absErr,
                   IntByReference iFlag, double[] work, int[] iWork);

    }

    private static String pathToLib() {
        Path start = Paths.get("").toAbsolutePath();
        try {
            Optional<Path> path = Files.walk(start).parallel()
                    .filter(DiffeqsUtil::isLibrary)
                    .findFirst();
            if (path.isPresent()) {
                return path.get().toAbsolutePath().toString();
            }
            throw new FileNotFoundException(FORSYTHE_RKF45_LIBRARY_PREFIX + " shared lib not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean isLibrary(Path path) {
        return path.getFileName().toString().contains(FORSYTHE_RKF45_LIBRARY_PREFIX);
    }

}
