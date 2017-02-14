#ifndef LAB1_SPLINE_CALCULATION_H
#define LAB1_SPLINE_CALCULATION_H

#include <vector>

/** @file */

/**
 * Строит интерполирующий Сплайн-полином для функции, заданной таблично и
 * вычисляет значения полученного полинома в заданных точках.
 * @param points вектор точек таблично заданной функции, для которой строится полином, с индексацией от 0.
 * @param values вектор значений таблично заданной функции, для которой строится полином, с индексацией от 0.
 * @param calculationPoints вектор точек, в которых вычисляются значения Сплайн-полинома.
 * @return Вектор значений интерполяционного Сплайн-полинома в заданных точках.
 */
const std::vector<double> calculateSplineValues(const std::vector<double> &points,
                                                const std::vector<double> &values,
                                                const std::vector<double> &calculationPoints);

#endif //LAB1_SPLINE_CALCULATION_H
