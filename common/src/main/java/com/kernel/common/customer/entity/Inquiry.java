package com.kernel.common.customer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "inquiry")
@Getter
@NoArgsConstructor
//@SuperBuilder
@ToString
public class Inquiry // extends BaseEntity {
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    private Long authorId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
/*
    public void delete(String status) {
        this.status = status;
    }
    */
}
