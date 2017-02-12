#app

set(APP_SOURCE_FILES src/main.cpp)

add_executable(app ${APP_SOURCE_FILES})

target_link_libraries(app lib)