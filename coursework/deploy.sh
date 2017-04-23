#!/usr/bin/env bash
{
	git clone -b gh-pages https://github.com/lamtev/comp_maths_labs.git
	cp notebook/car.html comp_maths_labs/index.html
	cd comp_maths_labs
	git add index.html
	git commit -m "update index.html"
	git push origin gh-pages
	Username: "lamtev"
	Password: $GITHUB_DEPLOY_TOKEN
} &> /dev/null
