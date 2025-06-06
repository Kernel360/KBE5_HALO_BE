package com.kernel.common.reservation.entity;

import com.kernel.common.global.entity.BaseEntity;
import com.kernel.common.global.enums.AuthorType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "review",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_review_reservation_author_type",
                        columnNames = {"reservation_id", "author_id", "author_type"}
                )
        }
)
@Getter
@SuperBuilder
@NoArgsConstructor
public class Review extends BaseEntity {

    // 리뷰ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    // 예약
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    // 작성자 타입
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AuthorType authorType;

    // 작성자ID
    @Column(nullable = false)
    private Long authorId;

    // 리뷰 평점
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;

    // 리뷰 내용
    @Column(nullable = false, length = 600)
    private String content;

    //리뷰 수정
    public void update(Integer reqRating, String reqContent){

        // 리뷰 평점
        if(reqRating!= null && !reqRating.equals(this.rating)){
            this.rating = reqRating;
        }

        // 리뷰 내용
        if(reqContent != null && !reqContent.equals(this.content)){
            this.content = reqContent;
        }
    }

}
