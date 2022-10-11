package com.dutmdcjf.moviereviewproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private Long mno;
    private String title;
    private LocalDateTime registDate;
    private LocalDateTime modifyDate;

    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();

    private double avg;
    private int reviewCnt;

}
