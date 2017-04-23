#!/usr/bin/env bash

docker run -v $TRAVIS_BUILD_DIR/${LAB}:/${LAB}  lamtev/python:latest /bin/bash -c " cd ${LAB}/notebook && ipython nbconvert --to html car.ipynb "
docker run -v $TRAVIS_BUILD_DIR/${LAB}:/${LAB}  lamtev/latex:full /bin/bash -c " cd ${LAB}/report/latex && pdflatex ${LAB}.tex && pdflatex ${LAB}.tex "