#!/usr/bin/env bash

set -e

rm -rf build

./gradlew -q clean check install --stacktrace

cd functional-test-app

./gradlew -q clean check

cd ..

