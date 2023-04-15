package com.example.poc.infra.interceptors;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ReadOnlyContext {
    private static final ThreadLocal<AtomicInteger> READ_ONLY_LEVEL = ThreadLocal.withInitial(() -> new AtomicInteger(0));

    private ReadOnlyContext() {
    }

    public static boolean isReadOnly() {
        return READ_ONLY_LEVEL.get()
                .get() > 0;
    }

    public static void enter() {
        READ_ONLY_LEVEL.get()
                .incrementAndGet();
    }

    public static void exit() {
        READ_ONLY_LEVEL.get()
                .decrementAndGet();

    }
}
