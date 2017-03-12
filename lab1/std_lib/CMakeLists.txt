#std_lib

set(
        STD_LIB_SOURCE_FILES

        src/lagrange.c src/lagrange.h
        src/quanc8.c src/quanc8.h
        src/splines.c src/splines.h
)

add_library(std_lib STATIC ${STD_LIB_SOURCE_FILES})