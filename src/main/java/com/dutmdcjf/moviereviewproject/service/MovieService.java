package com.dutmdcjf.moviereviewproject.service;

import com.dutmdcjf.moviereviewproject.dto.MovieDTO;
import com.dutmdcjf.moviereviewproject.dto.MovieImageDTO;
import com.dutmdcjf.moviereviewproject.dto.PageRequestDTO;
import com.dutmdcjf.moviereviewproject.dto.PageResultDTO;
import com.dutmdcjf.moviereviewproject.entity.Movie;
import com.dutmdcjf.moviereviewproject.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {

    //등록
    Long register(MovieDTO movieDTO);

    //list
    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO);


    //Entity -> DTO (목록 처리)
    default MovieDTO entityToDTO(Movie movie, List<MovieImage> movieImageList, Double avg, Long reviewCnt){

        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .registDate(movie.getRegistDate())
                .modifyDate(movie.getModifyDate()).build();

        List<MovieImageDTO> movieImageDTOList =
                movieImageList.stream().map( movieImage -> {
                   return MovieImageDTO.builder()
                           .imgName(movieImage.getImgName())
                           .path(movieImage.getPath())
                           .uuid(movieImage.getUuid()).build();
                }).collect(Collectors.toList());

        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());

        return movieDTO;
    }

    //JAP는 Entity 처리하므로 MovieDTO -> Movie Entity로 바꿔야 함
    //이때 Movie 객체뿐 아니라 MovieImage 객체들도 같이 처리되기 때문에 Map 타입을 이용해서 반환
    default Map<String, Object> dtoToEntity(MovieDTO movieDTO){
        Map<String, Object> entityMap = new HashMap<>();

        //movieDTO -> movie Entity
        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();

        entityMap.put("movie",movie);

        //movieImageDTO -> movieImage Entity
        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
        if(imageDTOList != null && imageDTOList.size() > 0){
            List<MovieImage> movieImageList = imageDTOList.stream().map(
                    movieImageDTO -> {
                        MovieImage movieImage = MovieImage.builder()
                                .path(movieImageDTO.getPath())
                                .imgName(movieImageDTO.getImgName())
                                .uuid(movieImageDTO.getUuid())
                                .movie(movie).build();
                        return movieImage;
                    }).collect(Collectors.toList());
            entityMap.put("imgList",movieImageList);
        }
        return entityMap;
    }
}
