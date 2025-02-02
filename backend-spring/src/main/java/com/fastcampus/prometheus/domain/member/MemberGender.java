package com.fastcampus.prometheus.domain.member;

import lombok.Getter;

@Getter
public enum MemberGender {
        MALE("M", "남성"),
        FEMALE("F", "여성");

    // Getter
    // DB에 저장할 코드 (ex: M, F)
        private final String code;

        // UI에서 보여줄 라벨 (ex: 남성, 여성)
        private final String label;

        MemberGender(String code, String label) {
            this.code = code;
            this.label = label;
        }

        // dbCode 값으로 Enum 찾아오기
        public static MemberGender fromDbCode(String code) {
            for (MemberGender gender : values()) {
                if (gender.code.equalsIgnoreCase(code)) {
                    return gender;
                }
            }
            throw new IllegalArgumentException("Unknown code: " + code);
        }

        // ✅ 문자열을 Enum으로 변환 (대소문자 무시)
        public static MemberGender fromString(String value) {
            for (MemberGender gender : MemberGender.values()) {
                if (gender.name().equalsIgnoreCase(value)) {
                    return gender;
                }
            }
            throw new IllegalArgumentException("Invalid gender value: " + value);
        }
    }

