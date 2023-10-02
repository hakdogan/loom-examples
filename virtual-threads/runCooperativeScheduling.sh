#!/usr/bin/env bash

#make sure the util module is compiled before running this script

java -cp ../util/target/classes/ \
-Djdk.virtualThreadScheduler.parallelism=1 \
-Djdk.virtualThreadScheduler.maxPoolSize=1 \
-Djdk.virtualThreadScheduler.minRunnable=1 \
src/main/java/org/jugistanbul/virtualthread/scheduler/CooperativeScheduling.java
