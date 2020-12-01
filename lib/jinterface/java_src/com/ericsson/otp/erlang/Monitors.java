/*
 * %CopyrightBegin%
 *
 * Copyright Ericsson AB 2000-2016. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * %CopyrightEnd%
 */
package com.ericsson.otp.erlang;

// package scope
class Monitors {
    Monitor[] monitors;
    int count;

    Monitors() {
        this(10);
    }

    Monitors(final int initialSize) {
        monitors = new Monitor[initialSize];
        count = 0;
    }

    synchronized void addMonitor(final OtpErlangPid monitoring,
            final Object monitored, final OtpErlangRef ref) {
        if (find(monitoring, monitored, ref) == -1) {
            if (count >= monitors.length) {
                final Monitor[] tmp = new Monitor[count * 2];
                System.arraycopy(monitors, 0, tmp, 0, count);
                monitors = tmp;
            }
            monitors[count++] = new Monitor(monitoring, monitored, ref);
        }
    }

    synchronized void removeMonitor(final OtpErlangPid monitoring,
            final Object monitored, final OtpErlangRef ref) {
        int i;

        if ((i = find(monitoring, monitored, ref)) != -1) {
            count--;
            monitors[i] = monitors[count];
            monitors[count] = null;
        }
    }

    synchronized boolean exists(final OtpErlangPid monitoring,
            final Object monitored, final OtpErlangRef ref) {
        return find(monitoring, monitored, ref) != -1;
    }

    synchronized int find(final OtpErlangPid monitoring, final Object monitored, final OtpErlangRef ref) {
        for (int i = 0; i < count; i++) {
            if (monitors[i].equals(monitoring, monitored, ref)) {
                return i;
            }
        }
        return -1;
    }

    int count() {
        return count;
    }

    /* clears the monitor table, returns a copy */
    synchronized Monitor[] clearMonitors() {
        Monitor[] ret = null;
        if (count != 0) {
            ret = new Monitor[count];
            for (int i = 0; i < count; i++) {
                ret[i] = monitors[i];
                monitors[i] = null;
            }
            count = 0;
        }
        return ret;
    }

    /* returns a copy of the monitor table */
    synchronized Monitor[] monitor() {
        Monitor[] ret = null;
        if (count != 0) {
            ret = new Monitor[count];
            System.arraycopy(monitors, 0, ret, 0, count);
        }
        return ret;
    }
}
