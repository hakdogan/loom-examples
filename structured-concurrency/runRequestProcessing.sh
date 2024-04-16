#!/usr/bin/env bash

#make sure the util and structured-concurrency modules are compiled before running this script

java --enable-preview --source 22 \
-cp ../util/target/classes/:./target/classes \
./src/main/java/org/jugistanbul/concurrency/structured/RequestProcessing.java &
for i in 1 2 3 4 5
do
   curl -GET localhost:8080
   sleep 1
done