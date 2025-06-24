package com.kernel.evaluation.repository.review;


import com.kernel.evaluation.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReviewRepository extends JpaRepository<Review, Long>, CustomCustomerReviewRepository {

}
