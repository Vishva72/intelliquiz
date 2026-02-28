package org.bits.IntelliQuiz.Util;

import java.util.UUID;

public final class PasswordGenerator {
    private PasswordGenerator() {}

    public static String generate() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
