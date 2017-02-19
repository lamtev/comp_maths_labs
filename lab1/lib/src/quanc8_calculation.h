#ifndef LAB1_QUANC8_CALCULATION_H
#define LAB1_QUANC8_CALCULATION_H

#include <vector>

/** @file */

/**
 * Структура, содержащая информацию о работе QUANC8
 */
struct Quanc8Info {
  /**
   * Абсолютная погрешность, выполненная программно.
   */
  double errest;

  /**
   * Количество вычислений подынтегральной функции.
   */
  int nofun;

  /**
   * Целая часть: количество промежутков, принятых с нарушением контроля погрешности,
   * дробная часть: номер промежутка, на котором "застряли".
   */
  double flag;
};

/**
 * Считает значения определённых интегралов заданной функции от 0 до заданных
 * точек, используя стандартную функцию quanc8.
 * @param fun указатель на функцию, которую необходимо проинтегрировать.
 * @param points константная ссылка на вектор заданных точек, с индексацией от 0.
 * @param error относительная погрешность.
 * @param quanc8Info ссылка на вектор, в который будет записана информация о работе QUANC8.
 * @return Вектор значений определённых интегралов для заданных точек.
 */
const std::vector<double> calculateQuanc8Values(double (*fun)(double),
                                                const std::vector<double> &points,
                                                double error,
                                                std::vector<Quanc8Info> &quanc8Info);

#endif //LAB1_QUANC8_CALCULATION_H
