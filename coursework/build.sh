#!/usr/bin/env bash

docker run -v $TRAVIS_BUILD_DIR/${LAB}:/${LAB}  lamtev/latex:full /bin/bash -c " cd ${LAB}/report/latex && pdflatex ${LAB}.tex && pdflatex ${LAB}.tex "