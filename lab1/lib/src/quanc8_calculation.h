#ifndef LAB1_QUANC8_CALCULATION_H
#define LAB1_QUANC8_CALCULATION_H

#include <vector>

/** @file */

/**
 * Считает значения определённых интегралов заданной функции от 0 до заданных
 * точек, используя стандартную функцию quanc8.
 * @param fun указатель на функцию, которую необходимо проинтегрировать.
 * @param points вектор заданных точек.
 * @return Вектор значений определённых интегралов для заданных точек.
 */
const std::vector<double> calculateQuanc8Values(double (*fun)(double), const std::vector<double> &points);

#endif //LAB1_QUANC8_CALCULATION_H
