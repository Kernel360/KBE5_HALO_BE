package com.kernel.admin.domain.entity;

import com.kernel.admin.common.enums.BoardType;
import com.kernel.admin.service.dto.request.BoardCreateReqDto;
import com.kernel.global.domain.entity.BaseEntity;
import com.kernel.global.domain.entity.File;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "board")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    // 보드 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    // 파일 Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private File file;

    // 타입
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardType boardType;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = true, length = 5000)
    private String content;

    // 삭제 여부
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    // 조회 수
    @Column(nullable = false)
    @Builder.Default
    private Long views = 0L;

    // 조회 수 업데이트
    public void updateViews() {
        this.views++;
    }

    // 데이터 수정
    public void updateBoard(BoardCreateReqDto requestDto) {

        // boardType 수정
        if(requestDto.getBoardType() != null && this.boardType != requestDto.getBoardType()) {
            this.boardType = requestDto.getBoardType();
        }

        // 제목 수정
        if(requestDto.getTitle() != null && !this.title.equals(requestDto.getTitle())) {
            this.title = requestDto.getTitle();
        }

        // 내용 수정
        if(requestDto.getContent() != null && !this.content.equals(requestDto.getContent())) {
            this.content = requestDto.getContent();
        }
    }

    // 삭제
    public void delete(){
        this.isDeleted = true;
    }
}