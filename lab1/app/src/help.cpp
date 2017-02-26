#include <vector>
#include <iostream>
#include <iomanip>

#include "../../lib/src/quanc8_calculation.h"

void printTable(const std::vector<double> &points,
                const std::vector<double> &lagrangeValues,
                const std::vector<double> &splineValues,
                const std::vector<double> &exactValues) {
  std::cout << "x_k\tlagrange\t\t\tspline\t\t\t\texact value by QUANC8"
            << std::endl << std::endl;
  unsigned i = 0;
  for (auto point : points) {
    std::cout << std::setprecision(3) << point << "\t"
              << std::setprecision(17) << lagrangeValues.at(i) << "\t\t"
              << std::setprecision(17) << splineValues.at(i) << "\t\t"
              << std::setprecision(17) << exactValues.at(i) << std::endl;
    ++i;
  }
}

void printDelimiter() {
  std::cout << "##################################################"
      "###############################################" << std::endl;
}

void printTableDefinedFunctionValuesCalculation() {
  std::cout << "Table-defined function values\ncalculation by QUANC8" << std::endl;
}

void printExactValuesCalculation() {
  std::cout << "Exact values calculation by QUANC8" << std::endl;
}

void printQuanc8Info(const std::vector<Quanc8Info> &quanc8Info) {
  std::cout << std::endl;
  std::cout << "ERREST\t" << "NOFUN\t" << "FLAG" << std::endl;
  std::cout << std::endl;
  for (auto info : quanc8Info) {
    std::cout << std::setprecision(1) << info.errest << "\t" << info.nofun
              << "\t" << std::setprecision(10) << info.flag << std::endl;
  }
  std::cout << std::endl;
}