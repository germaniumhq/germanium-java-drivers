#!/usr/bin/env bash

PROJECT_FOLDER=$(readlink -f "$(dirname "$0")/..")
cd $PROJECT_FOLDER

bin/download-drivers.sh

