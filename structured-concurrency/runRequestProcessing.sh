#!/usr/bin/env bash

#make sure the util and structured-concurrency modules are compiled before running this script

java -cp ../util/target/classes/:./target/classes \
--enable-preview \
--source=22 \
src/main/java/org/jugistanbul/structuredconcurrency/RequestProcessing.java &
for i in 1 2 3 4 5
do
   curl -GET localhost:8080
done