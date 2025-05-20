package com.kernel360.Halo.domain.customer.entity;

import com.kernel360.Halo.domain.global.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor
@SuperBuilder
public class Inquiry extends BaseEntity {

    private Long categoryId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

}
