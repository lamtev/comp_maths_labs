#ifndef MATRIX_H
#define MATRIX_H

#ifdef __cplusplus
extern "C" {
#endif

void decomp(int n, double *a, double *cond, int *ipvt, double *work);

void solve(int n, double *a, double *b, int *ipvt);

#ifdef __cplusplus
}
#endif

#endif //MATRIX_H