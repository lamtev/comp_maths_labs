#include <vector>
#include <limits>

#include "../../std_lib/src/quanc8.h"

const std::vector<double> calculateQuanc8Values(double (*fun)(double), const std::vector<double> &points) {
  std::vector<double> values = std::vector<double>();
  for (double point : points) {
    double errest;
    int nofun;
    double flag;
    double value;
    quanc8(fun, 0, point, std::numeric_limits<double>::denorm_min(), 0, &value, &errest, &nofun, &flag);
    values.push_back(value);
  }
  return values;
}
