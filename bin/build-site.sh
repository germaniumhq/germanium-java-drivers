#!/usr/bin/env bash

cd $(readlink -f $(dirname $(readlink -f "$0"))/..)
mvn -Dmaven.repo.local=$HOME/projects/germanium-java/m2repo/ clean install $@

