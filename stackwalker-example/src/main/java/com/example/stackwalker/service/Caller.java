package com.example.stackwalker.service;

public class Caller {
    public static void callWhoIsCallingMe(String str) {
        MyService.whoIsCallingMe(str);
    }

    public static void callWalking(String str) {
        MyService.walking(str);
    }

    public static void call(String str) {
        NestedCaller.callFromNestedClass(str);
    }

    public static class NestedCaller {
        static void callFromNestedClass(String str) {
            MoreNestedCaller.callFromMoreNestedClass(str);
        }
    }

    public static class MoreNestedCaller {
        static void callFromMoreNestedClass(String str) {
            MyService.walking(str);
        }
    }
}
