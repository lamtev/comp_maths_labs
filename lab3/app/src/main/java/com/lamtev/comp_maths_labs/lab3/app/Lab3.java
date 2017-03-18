package com.lamtev.comp_maths_labs.lab3.app;

import com.lamtev.comp_maths_labs.lab3.diffeqs_lib.DiffeqsUtil.Fun;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableInt;

import java.io.FileWriter;
import java.io.IOException;
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
    private static int POINTS_NUMBER = (int) ((RIGHT - LEFT) / STEP) + 1;

    private static final Fun FUN = (t, x, dx) -> {
        dx[0] = 0 * x[0] + 1 * x[1];
        dx[1] = -(1 / (t * (t + 1))) * x[0] - (3 * t + 2) / (t * (t + 1)) * x[1];
    };

    public static void main(String[] args) {
        double[][] systemExactValues = solveSystemExactly();
        printTable(systemExactValues, "Exact solution");
        double[][] systemRkf45Values = solveSystemByRkf45();
        printTable(systemRkf45Values, "RKF45");
        double[][] systemEulerCauchyValues = solveSystemByEulerCauchy();
        printTable(systemEulerCauchyValues, "Euler-Cauchy");
        makeCsvs(systemExactValues, systemRkf45Values, systemEulerCauchyValues);
    }

    private static double[][] solveSystemExactly() {
        double x[] = new double[N];
        double[][] values = new double[POINTS_NUMBER][N];
        int i = 0;
        for (double t = LEFT; t <= RIGHT + STEP; t += STEP) {
            calculateExactValues(x, t);
            values[i++] = Arrays.copyOf(x, N);
        }
        return values;
    }

    private static double[][] solveSystemByRkf45() {
        double x[] = Arrays.copyOf(INITIAL_CONDITIONS, N);
        MutableDouble t = new MutableDouble(1);
        MutableDouble tOut = new MutableDouble();
        MutableDouble relErr = new MutableDouble(1e-13);
        MutableDouble absErr = new MutableDouble(1e-13);
        MutableInt iFlag = new MutableInt(1);
        double[] work = new double[15];
        int[] iWork = new int[5];
        double[][] values = new double[POINTS_NUMBER][N];
        int i = 0;
        for (double tout = LEFT; tout <= RIGHT + STEP; tout += STEP) {
            tOut.setValue(tout);
            rkf45(FUN, N, x, t, tOut, relErr, absErr, iFlag, work, iWork);
            values[i++] = Arrays.copyOf(x, N);
        }
        return values;
    }

    private static double[][] solveSystemByEulerCauchy() {
        double x[] = Arrays.copyOf(INITIAL_CONDITIONS, N);
        double[][] values = new double[POINTS_NUMBER][N];
        int i = 0;
        values[i++] = INITIAL_CONDITIONS;
        for (double t = LEFT; t <= RIGHT; t += STEP) {
            eulerCauchy(FUN, N, t, x, STEP);
            values[i++] = Arrays.copyOf(x, N);
        }
        return values;
    }

    private static void calculateExactValues(double[] x, double t) {
        x[0] = 1 / t;
        x[1] = -1 / (t * t);
    }

    private static void printTable(double[][] values, String tableName) {
        System.out.println(tableName);
        printTAndX();
        double t = 1.0;
        for (int j = 0; j < POINTS_NUMBER; ++j) {
            printTAndXValues(t, values[j]);
            t += STEP;
        }
    }

    private static void printTAndX() {
        System.out.println("t\t\tx = (x1, x2)");
    }

    private static void printTAndXValues(double t, double[] x) {
        System.out.format(Locale.US, "%1.1f\t\t", t);
        System.out.format(Locale.US, "(%1.12f, ", x[0]);
        System.out.format(Locale.US, "%1.12f)\n", x[1]);
    }

    private static void makeCsvs(double[][] exactValues, double[][] rkf45Values,
                                 double[][] eulerCauchyValues) {
        double[] points = getPoints();
        double[] exacts = getFirstEquationValues(exactValues);
        makeCsv("exactValues", points, exacts);
        double[] rkf45s = getFirstEquationValues(rkf45Values);
        makeCsv("rkf45Values", points, rkf45s);
        double[] eulerCauchys = getFirstEquationValues(eulerCauchyValues);
        makeCsv("eulerCauchyValues", points, eulerCauchys);
        double[] rkf45Epsilons = getEpsilons(exacts, rkf45s);
        makeCsv("rkf45Epsilons", points, rkf45Epsilons);
        double[] eulerCauchyEpsilons = getEpsilons(exacts, eulerCauchys);
        makeCsv("eulerCauchyEpsilons", points, eulerCauchyEpsilons);
    }

    private static double[] getPoints() {
        double[] points = new double[POINTS_NUMBER];
        double t = LEFT;
        for (int i = 0; i < POINTS_NUMBER; ++i) {
            points[i] = t;
            t += STEP;
        }
        return points;
    }

    private static double[] getFirstEquationValues(double[][] systemValues) {
        double[] exacts = new double[POINTS_NUMBER];
        int i = 0;
        for (double[] row : systemValues) {
            exacts[i++] = row[0];
        }
        return exacts;
    }

    private static double[] getEpsilons(double[] exactValues, double[] methodValues) {
        double[] rkf45Epsilons = new double[POINTS_NUMBER];
        for (int i = 0; i < POINTS_NUMBER; ++i) {
            rkf45Epsilons[i] = Math.abs(exactValues[i] - methodValues[i]);
        }
        return rkf45Epsilons;
    }

    private static void makeCsv(String csvName, double[] points, double[] values) {
        try (FileWriter fileWriter = new FileWriter(csvName + ".csv")) {
            fileWriter.write("t, " + csvName.substring(0, csvName.lastIndexOf("s")));
            fileWriter.write(System.getProperty("line.separator"));
            for (int i = 0; i < POINTS_NUMBER; ++i) {
                fileWriter.write(String.format(Locale.US, "%1.1f", points[i]) + ", "
                        + String.format(Locale.US, "%1.12f", values[i]));
                fileWriter.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
