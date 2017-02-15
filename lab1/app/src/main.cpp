#include <cmath>
#include <iostream>
#include <array>
#include <vector>
#include <limits>

#include "../../lib/src/spline_calculation.h"
#include "../../lib/src/lagrange_calculation.h"
#include "../../lib/src/quanc8_calculation.h"
#include "../../lib/src/util.h"

double fun(double x) {
  if (x == 0) {
    return sin(x) / std::numeric_limits<double>::denorm_min();
  }
  return sin(x) / x;
}

int main(int argc, char **argv) {
  const double LEFT = 2.0;
  const double RIGHT = 3.0;
  const double STEP = 1e-1;
  const int NUMBER_OF_TEST_POINTS = 10;

  std::vector<double> points = generatePoints(LEFT, RIGHT, STEP);
  std::vector<double> values = calculateQuanc8Values(fun, points);

  std::vector<double> testPoints = generateTestPoints(NUMBER_OF_TEST_POINTS, STEP);
  std::vector<double> lagrangeCalculatedValues = calculateLagrangeValues(points, values, testPoints);
  std::vector<double> splineCalculatedValues = calculateSplineValues(points, values, testPoints);
  std::vector<double> quanc8CalculatedValues = calculateQuanc8Values(fun, testPoints);

  std::cout << "x_k\tlagrange\tspline\t\tquanc8" << std::endl << std::endl;
  unsigned i = 0;
  for (double testPoint : testPoints) {
    std::cout << testPoint << "\t" << lagrangeCalculatedValues.at(i) << "\t\t" << splineCalculatedValues.at(i) <<
              "\t\t" << quanc8CalculatedValues.at(i) << std::endl;
    ++i;
  }
  return 0;
}