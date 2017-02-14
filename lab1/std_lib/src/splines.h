#ifndef LAB1_SPLINES_H
#define LAB1_SPLINES_H

#ifdef __cplusplus
extern "C" {
#endif

void spline(int n, double *x, double *y, double *b, double *c, double *d);
double seval(int n, double *u, double *x, double *y, double *b, double *c, double *d);

#ifdef __cplusplus
}
#endif

#endif //LAB1_SPLINES_H