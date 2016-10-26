#!/usr/bin/env bash

GERMANIUM_DRIVERS_JAVA=$(readlink -f $(dirname $(readlink -f "$0"))/..)
GERMANIUM_DRIVERS=$(readlink -f $(dirname $(readlink -f "$0"))/../../germaniumdrivers/)

rm -fr $GERMANIUM_DRIVERS_JAVA/src/main/resources/binary
mkdir $GERMANIUM_DRIVERS_JAVA/src/main/resources/binary
cp -R $GERMANIUM_DRIVERS/germaniumdrivers/binary/* $GERMANIUM_DRIVERS_JAVA/src/main/resources/binary

