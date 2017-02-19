#include <cmath>
#include <iostream>
#include <vector>

#include "../../lib/src/spline_calculation.h"
#include "../../lib/src/lagrange_calculation.h"
#include "../../lib/src/quanc8_calculation.h"
#include "../../lib/src/util.h"
#include "help.h"

double sinXDivX(double x) {
  /// Функция sin(x)/x не определена в точке 0.
  /// Поэтому доопределим её в этой точке до 1.
  if (x == 0) {
    return 1;
  }
  return sin(x) / x;
}

int main(int argc, char **argv) {
  const double LEFT = 2.0;
  const double RIGHT = 3.0;
  const double STEP = 1e-1;
  const int NUMBER_OF_TEST_POINTS = 10;
  const std::vector<double> ABSOLUTE_ERRORS{1e-7, 1e-13, 1e-19, 1e-20};

  for (auto error : ABSOLUTE_ERRORS) {
    std::vector<double> points = generatePoints(LEFT, RIGHT, STEP);
    std::vector<Quanc8Info> valuesCalculationInfo;
    std::vector<double> values = calculateQuanc8Values(sinXDivX,
                                                       points,
                                                       error,
                                                       valuesCalculationInfo);

    std::vector<double> testPoints = generateTestPoints(NUMBER_OF_TEST_POINTS, STEP);
    std::vector<double> lagrangeCalculatedValues = calculateLagrangeValues(points,
                                                                           values,
                                                                           testPoints);
    std::vector<double> splineCalculatedValues = calculateSplineValues(points,
                                                                       values,
                                                                       testPoints);
    std::vector<Quanc8Info> exactValuesCalculationInfo;
    std::vector<double> exactValues = calculateQuanc8Values(sinXDivX,
                                                            testPoints,
                                                            error,
                                                            exactValuesCalculationInfo);

    printDelimiter();
    std::cout << "error: " << error << std::endl;
    std::cout << std::endl;

    printTableDefinedFunctionValuesCalculation();
    printQuanc8Info(valuesCalculationInfo);

    printExactValuesCalculation();
    printQuanc8Info(exactValuesCalculationInfo);

    printTable(testPoints, lagrangeCalculatedValues, splineCalculatedValues, exactValues);
    printDelimiter();
    std::cout << std::endl;
  }
  return 0;
}