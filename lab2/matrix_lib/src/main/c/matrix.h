#ifndef _MATRIX
#define _MATRIX

#ifdef __cplusplus
extern "C" {
#endif

void decomp(int n, double **a, double *cond, int *ipvt, double *work);

void solve(int n, double **a, double *b, int *ipvt);

#ifdef __cplusplus
}
#endif

#endif //_MATRIX