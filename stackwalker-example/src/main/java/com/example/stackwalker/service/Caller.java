package com.example.stackwalker.service;

import com.example.stackwalker.foo.bar.FooBarService;
import com.example.stackwalker.reflection.UseReflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public static void skip(String str) {
        MyService.skipItself(str);
    }

    public static void filter(String str) {
        FooBarService.walk(str);
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

    public static void callNotUseShowReflectFrames(String str) throws NoSuchMethodException,
            IllegalAccessException, InstantiationException, InvocationTargetException {
        UseReflection clazz = UseReflection.class.getDeclaredConstructor().newInstance();
        Method method = UseReflection.class.getMethod("notUseShowReflectFrames", String.class);
        method.invoke(clazz, str);
    }

    public static void callUseShowReflectFrames(String str) throws NoSuchMethodException,
            IllegalAccessException, InstantiationException, InvocationTargetException {
        UseReflection clazz = UseReflection.class.getDeclaredConstructor().newInstance();
        Method method = UseReflection.class.getMethod("useShowReflectFrames", String.class);
        method.invoke(clazz, str);
    }
}
