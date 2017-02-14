#ifndef LAB1_UTIL_H
#define LAB1_UTIL_H

#include <vector>

/** @file */

/**
 * Генерирует вектор точек по границам отрезка и длине шага.
 * @param left левая граница отрезка.
 * @param right правая граница отрезка.
 * @param step шаг.
 * @return Вектор точек.
 */
const std::vector<double> generatePoints(double left, double right, double step);

/**
 * Генерирует вектор точек, предназначенных для сравнения в них значений интерполяционных полиномов.
 * @param pointsNumber число точек.
 * @param step шаг.
 * @return Вектор точек.
 */
const std::vector<double> generateTestPoints(int pointsNumber, double step);

#endif //LAB1_UTIL_H
