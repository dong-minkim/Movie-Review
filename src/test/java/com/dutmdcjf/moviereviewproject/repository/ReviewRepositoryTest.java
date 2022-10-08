package com.dutmdcjf.moviereviewproject.repository;

import com.dutmdcjf.moviereviewproject.entity.Member;
import com.dutmdcjf.moviereviewproject.entity.Movie;
import com.dutmdcjf.moviereviewproject.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void insertReviewsDummies(){
        IntStream.rangeClosed(1,200).forEach(i->{
            Long mno = (long)(Math.random()*100)+1;
            Long memid = (long)(Math.random()*100)+1;

            Member member = Member.builder()
                    .memid(memid).build();

            Review review = Review.builder()
                    .grade((int)(Math.random()*5)+1)
                    .text(mno +" 영화 추천합니다")
                    .movie(Movie.builder().mno(mno).build())
                    .member(member).build();

            reviewRepository.save(review);
        });
    }

    @Test
    public void testGetOneMovieReview() {
        Movie movie = Movie.builder().mno(4L).build();

        List<Review> reviewList = reviewRepository.findByMovie(movie);

        for(Review review : reviewList){
            System.out.println(review.getRno());
            System.out.println(review.getGrade());
            System.out.println(review.getText());
            System.out.println(review.getMember());
        }
    }

}
