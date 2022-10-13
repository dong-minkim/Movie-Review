package com.dutmdcjf.moviereviewproject.service;

import com.dutmdcjf.moviereviewproject.dto.ReviewDTO;
import com.dutmdcjf.moviereviewproject.entity.Movie;
import com.dutmdcjf.moviereviewproject.entity.Review;
import com.dutmdcjf.moviereviewproject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;


    @Override
    public Long register(ReviewDTO reviewDTO) {

        Review review = dtoToEntity(reviewDTO);

        reviewRepository.save(review);

        return review.getRno();
    }

    @Override
    public List<ReviewDTO> getReviewList(Long mno) {

        Movie movie = Movie.builder().mno(mno).build();

        List<Review> result = reviewRepository.findByMovie(movie);

        return result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {

        //Optional: NPE 방지 (null 방지)
        Optional<Review> result = reviewRepository.findById(reviewDTO.getRno());
        System.out.println(result);
        if(result.isPresent()){
            Review review = result.get();
            review.setGrade(reviewDTO.getGrade());
            review.setText(reviewDTO.getText());
            reviewRepository.save(review);
        }
    }

    @Override
    public void remove(Long rno) {
        reviewRepository.deleteById(rno);
    }
}
