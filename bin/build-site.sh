#!/usr/bin/env bash

cd $(readlink -f $(dirname $(readlink -f "$0"))/..)
mvn -Dmaven.repo.local=$HOME/projects/germanium-maven-site/public/ clean install $@

