/*
 * %CopyrightBegin%
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
class Monitor {
    private final OtpErlangPid monitoring;
    private final Object monitored;
    private final OtpErlangRef ref;
    private int hashCodeValue = 0;

    public Monitor(final OtpErlangPid monitoring, final Object monitored, final OtpErlangRef ref) {
        if (monitoring == null) {
            throw new IllegalArgumentException("monitoring cannot be null");
        }
        if (monitored == null) {
            throw new IllegalArgumentException("monitored cannot be null");
        }
        if (ref == null) {
            throw new IllegalArgumentException("ref cannot be null");
        }
        this.monitoring = monitoring;
        this.monitored = monitored;
        this.ref = ref;
    }

    public OtpErlangPid monitoring() {
        return monitoring;
    }

    public Object monitored() {
        return monitored;
    }

    public OtpErlangRef ref() {
        return ref;
    }

    public boolean equals(final OtpErlangPid aMonitoring, final Object aMonitored, final OtpErlangRef aRef) {
        return monitoring.equals(aMonitoring) && monitored.equals(aMonitored) && ref.equals(aRef);
    }

    @Override
    public int hashCode() {
        if (hashCodeValue == 0) {
            final OtpErlangObject.Hash hash = new OtpErlangObject.Hash(5);
            hash.combine(monitoring.hashCode() + monitored.hashCode() + ref.hashCode());
            hashCodeValue = hash.valueOf();
        }
        return hashCodeValue;
    }
}
