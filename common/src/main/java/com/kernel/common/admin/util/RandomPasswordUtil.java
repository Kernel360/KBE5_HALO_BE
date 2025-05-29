package com.kernel.common.admin.util;

import java.security.SecureRandom;

/**
 * 랜덤 비밀번호 생성 유틸리티 클래스
 * 이 클래스는 보안적으로 안전한 랜덤 비밀번호를 생성하는 기능을 제공합니다.
 * 비밀번호는 최소 8자 이상이어야 하며, 대문자, 소문자, 숫자, 특수 문자를 포함합니다.
 */
public class RandomPasswordUtil {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 랜덤 비밀번호 생성
     * @param length
     * @return
     */
    public static String generatePassword(int length) {
        if (length < 8) {   // 임의로 정한 정책, 팀과 상의 필요
            throw new IllegalArgumentException("비밀번호 최소 길이는 8자 이상이어야 합니다.");
        }

        StringBuilder password = new StringBuilder(length);

        // 각 종류의 문자를 최소 하나씩 추가
        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));
        password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
        password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.charAt(RANDOM.nextInt(SPECIAL_CHARACTERS.length())));

        // 나머지 문자는 모든 문자에서 무작위로 선택
        for (int i = 4; i < length; i++) {
            password.append(ALL_CHARACTERS.charAt(RANDOM.nextInt(ALL_CHARACTERS.length())));
        }

        return shuffleString(password.toString());
    }

    /**
     * 문자열을 무작위로 섞는 메소드
     * @param input
     * @return
     */
    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);  // Spring Security에서 제공하는 SecureRandom을 사용하여 무작위 인덱스 생성
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }
}

