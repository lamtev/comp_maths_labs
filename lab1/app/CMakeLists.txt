#app

set(APP_SOURCE_FILES src/main.cpp src/help.cpp src/help.h)

add_executable(app ${APP_SOURCE_FILES})

target_link_libraries(app lib)