#lib

set(
        LIB_SOURCE_FILES

        src/util.h
        src/util.cpp
        src/spline_calculation.h
        src/spline_calculation.cpp
        src/quanc8_calculation.h
        src/quanc8_calculation.cpp
        src/lagrange_calculation.h
        src/lagrange_calculation.cpp
)

add_library(lib STATIC ${LIB_SOURCE_FILES})

target_link_libraries(lib std_lib)