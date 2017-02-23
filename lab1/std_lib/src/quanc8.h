#ifndef QUANC8_H
#define QUANC8_H

#ifdef __cplusplus
extern "C" {
#endif

void quanc8(double (*FUN)(double), double A, double B, double ABSERR,
            double RELERR, double *RESULT, double *ERREST, int *NOFUN, double *FLAG);

#ifdef __cplusplus
}
#endif

#endif //QUANC8_H