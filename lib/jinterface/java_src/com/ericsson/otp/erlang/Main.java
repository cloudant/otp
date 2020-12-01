package com.ericsson.otp.erlang;

public class Main {

    public static void main(String[] args) throws Exception {
        final OtpNode node = new OtpNode("cloujeau@127.0.0.1", "monster");
        final OtpMbox mbox = node.createMbox("main");

        while (true) {
            final OtpMsg msg = mbox.receiveMsg();
            System.out.println(msg);
        }
    }

}
