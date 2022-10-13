package com.dutmdcjf.moviereviewproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long rno;

    private int grade;

    private String text;

    private LocalDateTime registDate;
    private LocalDateTime modifyDate;

    //Movie
    private Long mno;

    //Member
    private Long memid;
    private String email;
    private String nickname;

}
