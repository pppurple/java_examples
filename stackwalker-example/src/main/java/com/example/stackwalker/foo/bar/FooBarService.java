package com.example.stackwalker.foo.bar;

import java.lang.StackWalker.Option;
import java.util.stream.Collectors;

public class FooBarService {
    public static void walk(String str) {
        System.out.println(str);

        StackWalker stackWalker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
        stackWalker.walk(frame -> frame.collect(Collectors.toList()))
                .stream()
                .filter(frame -> !frame.getClassName().contains("service"))
                .forEach(f -> System.out.println(f.getDeclaringClass()));
    }
}
