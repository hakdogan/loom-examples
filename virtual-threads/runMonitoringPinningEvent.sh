#!/usr/bin/env bash

#make sure the util module is compiled before running this script

java --enable-preview --source 22 \
-cp ../util/target/classes/ \
-Djdk.tracePinnedThreads=short \
src/main/java/org/jugistanbul/virtualthread/monitor/MonitoringPinningEvent.java
