#include <vector>

#include "../../std_lib/src/lagrange.h"

const std::vector<double> calculateLagrangeValues(const std::vector<double> &points,
                                                  const std::vector<double> &values,
                                                  const std::vector<double> &calculationPoints) {
  std::vector<double> calculatedValues = std::vector<double>();
  for (auto point : calculationPoints) {
    double x[points.size()];
    std::copy(points.cbegin(), points.cend(), x);
    double y[values.size()];
    std::copy(values.cbegin(), values.cend(), y);
    double calculatedValue = lagrange(points.size(), x, y, point);
    calculatedValues.push_back(calculatedValue);
  }
  return calculatedValues;
}
