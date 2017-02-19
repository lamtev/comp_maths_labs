#include <vector>
#include <limits>
#include <iostream>
#include <iomanip>

#include "../../std_lib/src/quanc8.h"
#include "quanc8_calculation.h"

const std::vector<double> calculateQuanc8Values(double (*fun)(double),
                                                const std::vector<double> &points,
                                                double error,
                                                std::vector<Quanc8Info> &quanc8Info) {
  std::vector<double> values = std::vector<double>();
  quanc8Info = std::vector<Quanc8Info>();

  for (auto point : points) {
    double value;
    double errest;
    int nofun;
    double flag;

    quanc8(fun, 0, point, error, 0, &value, &errest, &nofun, &flag);

    values.push_back(value);
    quanc8Info.push_back(Quanc8Info{errest, nofun, flag});
  }
  return values;
}
