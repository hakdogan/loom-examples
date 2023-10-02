#!/usr/bin/env bash

#make sure the YieldExecution class is compiled before running this script

java --add-exports java.base/jdk.internal.vm=ALL-UNNAMED \
src/main/java/org/jugistanbul/virtualthread/continuation/YieldExecution.java
