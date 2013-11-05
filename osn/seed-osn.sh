#!/bin/sh

ant clean build
pushd .
cd build/deploy
java -jar osn-test.jar seed_properties.xml
popd
