package com.example.stackwalker.reflection;

import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.stream.Collectors;

public class UseReflection {
    public void notUseShowReflectFrames(String str) {
        System.out.println(str);

        StackWalker stackWalker = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE);
        List<StackFrame> frames = stackWalker.walk(frame -> frame.collect(Collectors.toList()));
        frames.forEach(f -> System.out.println(f.getClassName() + "#" + f.getMethodName()));
    }

    public void useShowReflectFrames(String str) {
        System.out.println(str);

        StackWalker stackWalker = StackWalker.getInstance(Option.SHOW_REFLECT_FRAMES);
        List<StackFrame> frames = stackWalker.walk(frame -> frame.collect(Collectors.toList()));
        frames.forEach(f -> System.out.println(f.getClassName() + "#" + f.getMethodName()));
    }
}
