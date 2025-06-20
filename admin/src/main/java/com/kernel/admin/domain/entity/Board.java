package com.kernel.admin.domain.entity;

import com.kernel.admin.domain.enums.BoardType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "board")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//extends BaseEntity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private Long file_Id;
    private Boolean is_Deleted;
    private Long views;

    public void update(String title, String content, Long fileId, Long updatedBy) {
        this.title = title;
        this.content = content;
        this.file_Id = fileId;
    }
    public void Deleted(Boolean Deleted) {
            this.is_Deleted = Deleted;
        }
}