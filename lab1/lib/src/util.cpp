#include <vector>

const std::vector<double> generatePoints(double left, double right, double step) {
  std::vector<double> points = std::vector<double>();
  double x = left;
  while (x - right < step) {
    points.push_back(x);
    x += step;
  }
  return points;
}

const std::vector<double> generateTestPoints(int pointsNumber, double step) {
  std::vector<double> testPoints = std::vector<double>();
  for (int i = 1; i <= pointsNumber; ++i) {
    double testPoint = (i - 0.5) * step + 2;
    testPoints.push_back(testPoint);
  }
  return testPoints;
}
