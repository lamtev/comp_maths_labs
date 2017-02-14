#include <vector>

#include "../../std_lib/src/lagrange.h"

const std::vector<double> calculateLagrangeValues(std::vector<double> &points,
                                                  std::vector<double> &values,
                                                  const std::vector<double> &calculationPoints) {
  std::vector<double> calculatedValues = std::vector<double>();
  for (double point : calculationPoints) {
    double calculatedValue = lagrange(points.size(), points.data(), values.data(), point);
    calculatedValues.push_back(calculatedValue);
  }
  return calculatedValues;
}
