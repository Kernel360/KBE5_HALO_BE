package com.kernel.common.customer.entity;

import com.kernel.common.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "inquiry_category")
@Getter
@SuperBuilder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InquiryCategory extends BaseEntity {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    // 카테고리 명
    @Column(nullable = false, length =  50)
    private String categoryName;

}
