#ifndef LAB1_HELP_H
#define LAB1_HELP_H

#include <vector>

void printTable(const std::vector<double> &points,
                const std::vector<double> &lagrangeValues,
                const std::vector<double> &splineValues,
                const std::vector<double> &exactValues);

void printDelimiter();

void printTableDefinedFunctionValuesCalculation();

void printExactValuesCalculation();

void printQuanc8Info(const std::vector<Quanc8Info> &quanc8Info);

#endif //LAB1_HELP_H
