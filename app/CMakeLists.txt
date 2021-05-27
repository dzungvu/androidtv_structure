# Sets the minimum version of CMake required to build the native
# library.
cmake_minimum_required(VERSION 3.10.2)

# Creates the project's shared lib: libnative-lib.so.
# The lib is loaded by this project's Java code in MainActivity.java:
#     System.loadLibrary("app-values");
# The lib name in both places must match.
add_library( app-values
             SHARED
             src/main/cpp/app-values.cpp )

find_library(log-lib
             log )

# Specifies a path to native header files.
include_directories(src/main/cpp/include/)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       app-values

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )