package com.example.stackwalker;

import com.example.stackwalker.service.Caller;
import com.example.stackwalker.service.MyService;

public class Main {
    public static void main(String[] args) throws Exception {
        // call directly
        MyService.whoIsCallingMe("directly");
        System.out.println("-------------------------");

        // call via Caller
        Caller.callWhoIsCallingMe("via caller");
        System.out.println("-------------------------");

        // walking
        Caller.callWalking("walking");
        System.out.println("-------------------------");

        // call via nested class
        Caller.call("nested");
        System.out.println("-------------------------");

        // skip
        Caller.skip("skip");
        System.out.println("-------------------------");

        // filter
        Caller.filter("filter");
        System.out.println("-------------------------");

        // not use reflect frame
        Caller.callNotUseShowReflectFrames("not use reflect frame");
        System.out.println("-------------------------");

        // use reflect frame
        Caller.callUseShowReflectFrames("use reflect frame");
    }
}
