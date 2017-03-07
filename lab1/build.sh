#!/usr/bin/env bash

docker run -v $TRAVIS_BUILD_DIR/${LAB}:/${LAB} lamtev/cxx /bin/bash -c " cd ${LAB} && pwd && ls && mkdir build && cd build && cmake -DCMAKE_BUILD_TYPE=Debug .. && cmake --build . "
docker run -v $TRAVIS_BUILD_DIR/${LAB}:/${LAB}  lamtev/latex:full /bin/bash -c " cd ${LAB}/report/latex && pdflatex ${LAB}.tex && pdflatex ${LAB}.tex "
docker run -v $TRAVIS_BUILD_DIR/${LAB}:/${LAB} lamtev/cxx /bin/bash -c " cd ${LAB}/report/doxygen && doxygen config.doxygen "
docker run -v $TRAVIS_BUILD_DIR/${LAB}:/${LAB}  lamtev/latex:full /bin/bash -c " cd ${LAB}/report/doxygen/latex && pdflatex refman.tex && makeindex refman.idx && pdflatex refman.tex && makeindex refman.idx && pdflatex refman.tex "