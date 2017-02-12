#lib

set(
        LIB_SOURCE_FILES src/lagrange.cpp
        src/lagrange.h src/quanc8.cpp src/quanc8.h
        src/SPLINES.CPP src/SPLINES.H
)
add_library(lib STATIC ${LIB_SOURCE_FILES})