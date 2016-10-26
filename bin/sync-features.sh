#!/usr/bin/env bash

GERMANIUM_DRIVERS_JAVA=$(readlink -f $(dirname $(readlink -f "$0"))/..)
GERMANIUM_DRIVERS=$(readlink -f $(dirname $(readlink -f "$0"))/../../germaniumdrivers/)

rm -fr $GERMANIUM_DRIVERS_JAVA/src/test/resources/features/
mkdir $GERMANIUM_DRIVERS_JAVA/src/test/resources/features
cp $GERMANIUM_DRIVERS/features/*.feature $GERMANIUM_DRIVERS_JAVA/src/test/resources/features/
