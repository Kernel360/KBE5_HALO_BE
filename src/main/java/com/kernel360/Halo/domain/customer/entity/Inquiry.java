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


    private Long authorId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    public void updateContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

/*
    public void updateStatus(String status) {
        this.status = status;
    }
    */

}
