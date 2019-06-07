package com.example.stackwalker.service;


import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.stream.Collectors;

public class MyService {
    public static void whoIsCallingMe(String str) {
        System.out.println(str);

        StackWalker stackWalker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
        System.out.println(stackWalker.getCallerClass());
    }

    public static void walking(String str) {
        System.out.println(str);

        StackWalker stackWalker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);

        List<StackFrame> frames = stackWalker.walk(frame -> frame.collect(Collectors.toList()));
        frames.forEach(f -> System.out.println(f.getDeclaringClass()));
    }
}
