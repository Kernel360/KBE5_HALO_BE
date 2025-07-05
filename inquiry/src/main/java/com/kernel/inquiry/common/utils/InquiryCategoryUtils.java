package com.kernel.inquiry.common.utils;

import com.kernel.inquiry.common.enums.AuthorType;
import com.kernel.inquiry.common.enums.CustomerInquiryCategory;
import com.kernel.inquiry.common.enums.ManagerInquiryCategory;

import java.util.Arrays;

public class InquiryCategoryUtils {

    /**
     * 문의사항 카테고리 Label 불러오기 (영->한)
     * @param categoryName 카테고리 Name
     * @param authorType 작성자 타입
     */
    public static String getCategoryLabel(String categoryName, AuthorType authorType) {

        if(categoryName == null || authorType == null) return null;

        try {
            return switch (authorType) {
                case CUSTOMER -> CustomerInquiryCategory.valueOf(categoryName).getLabel();
                case MANAGER -> ManagerInquiryCategory.valueOf(categoryName).getLabel();
                default -> categoryName;
            };
        } catch (IllegalArgumentException e) {
            return categoryName; // 잘못된 enum name이 들어온 경우 fallback
        }
    }

    /**
     * 문의사항 카테고리 Name 불러오기(한 -> 영)
     * @param label 카테고리 Label
     * @param authorType 작성자 타입
     */
    public static String getCategoryName(String label, AuthorType authorType) {

        if(label == null || authorType == null) return null;

        return switch (authorType){
            case CUSTOMER -> Arrays.stream(CustomerInquiryCategory.values())
                    .filter(e -> e.getLabel().equals(label))
                    .map(Enum::name)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객 문의 카테고리 입니다."));
            case MANAGER -> Arrays.stream(ManagerInquiryCategory.values())
                    .filter(e -> e.getLabel().equals(label))
                    .map(Enum::name)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 매니저 문의 카테고리 입니다."));
        };
    }
}
