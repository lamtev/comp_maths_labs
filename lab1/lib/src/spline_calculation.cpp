#include <vector>

#include "../../std_lib/src/splines.h"

const std::vector<double> shiftRight(const std::vector<double> &array);

const std::vector<double> calculateSplineValues(const std::vector<double> &points,
                                                const std::vector<double> &values,
                                                const std::vector<double> &calculationPoints) {
  std::vector<double> splinePoints = shiftRight(points);
  std::vector<double> splineValues = shiftRight(values);
  double *x = splinePoints.data();
  double *y = splineValues.data();
  double b[points.size()];
  double c[points.size()];
  double d[points.size()];
  spline(points.size(), x, y, b, c, d);
  std::vector<double> calculatedValues = std::vector<double>();
  for (auto point : calculationPoints) {
    double calculatedValue = seval(points.size(), &point, x, y, b, c, d);
    calculatedValues.push_back(calculatedValue);
  }
  return calculatedValues;
}

const std::vector<double> shiftRight(const std::vector<double> &array) {
  std::vector<double> shiftedArray = std::vector<double>();
  shiftedArray.push_back(0);
  shiftedArray.insert(shiftedArray.cend(), array.cbegin(), array.cend());
  return shiftedArray;
}