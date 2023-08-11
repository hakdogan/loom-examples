#!/usr/bin/env bash

# make sure the virtualThreadPinnedEvent.jfr file is created before running this script

jfr print --events jdk.VirtualThreadPinned virtualThreadPinnedEvent.jfr