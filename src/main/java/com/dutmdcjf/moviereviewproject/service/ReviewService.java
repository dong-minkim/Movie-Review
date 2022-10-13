package com.dutmdcjf.moviereviewproject.service;

import com.dutmdcjf.moviereviewproject.dto.ReviewDTO;
import com.dutmdcjf.moviereviewproject.entity.Member;
import com.dutmdcjf.moviereviewproject.entity.Movie;
import com.dutmdcjf.moviereviewproject.entity.Review;

import java.util.List;

public interface ReviewService {
    Long register(ReviewDTO reviewDTO);

    List<ReviewDTO> getReviewList(Long mno);

    void modify(ReviewDTO reviewDTO);

    void remove(Long rno);

    default Review dtoToEntity(ReviewDTO reviewDTO){

        Review review = Review.builder()
                .rno(reviewDTO.getRno())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .movie(Movie.builder().mno(reviewDTO.getMno()).build())
                .member(Member.builder().memid(reviewDTO.getMemid()).build()).build();

        return review;
    }

    default ReviewDTO entityToDto(Review review){

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .rno(review.getRno())
                .grade(review.getGrade())
                .text(review.getText())
                .registDate(review.getRegistDate())
                .modifyDate(review.getModifyDate())
                .mno(review.getMovie().getMno())
                .memid(review.getMember().getMemid())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname()).build();

        return reviewDTO;
    }
}
