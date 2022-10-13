package com.dutmdcjf.moviereviewproject.controller;

import com.dutmdcjf.moviereviewproject.dto.ReviewDTO;
import com.dutmdcjf.moviereviewproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{mno}/list")
    public ResponseEntity<List<ReviewDTO>> getReviewList(@PathVariable("mno") Long mno){
        List<ReviewDTO> reviewDTOList = reviewService.getReviewList(mno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{mno}")
    public ResponseEntity<Long> registerReview(@RequestBody ReviewDTO reviewDTO){
        Long rno = reviewService.register(reviewDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @PutMapping("/{mno}/{rno}")
    public ResponseEntity<Long> modifyReview(@PathVariable("rno") Long rno, @RequestBody ReviewDTO reviewDTO){
        System.out.println("Modify Review Controller");

        reviewService.modify(reviewDTO);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }

    @DeleteMapping("/{mno}/{rno}")
    public ResponseEntity<Long> removeReview(@PathVariable("rno") Long rno){
        reviewService.remove(rno);

        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
}
