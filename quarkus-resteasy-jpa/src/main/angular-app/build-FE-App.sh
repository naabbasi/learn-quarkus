#!/usr/bin/env bash

rm -vfr ../resources/META-INF/resources/*

ng build --prod --base-href=.

mkdir -p ../resources/META-INF/resources/app

mv dist/angular-app/* ../resources/META-INF/resources/app/
