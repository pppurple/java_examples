package com.example.stackwalker;

import com.example.stackwalker.service.Caller;
import com.example.stackwalker.service.MyService;

public class Main {
    public static void main(String[] args) {
        // call directly
        MyService.whoIsCallingMe("directly");

        // call via Caller
        Caller.callWhoIsCallingMe("via caller");

        // call via Caller
        Caller.callWalking("walking");

        // call via nested class
        Caller.call("call!!!");

        // skip
        Caller.skip("skip");

        // filter
        Caller.filter("filter");
    }
}
