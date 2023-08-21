#!/usr/bin/env bash

#make sure the YieldExecution class is compiled before running this script

java -cp ../util/target/classes/:./target/classes/ \
--add-modules jdk.incubator.concurrent \
--add-exports java.base/jdk.internal.vm=ALL-UNNAMED \
--enable-preview \
org.jugistanbul.structuredconcurrency.RequestProcessing &
for i in 1 2 3 4 5
do
   curl -s -o -GET localhost:8080
done