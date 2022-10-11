package com.dutmdcjf.moviereviewproject.service;

import com.dutmdcjf.moviereviewproject.dto.MovieDTO;
import com.dutmdcjf.moviereviewproject.dto.MovieImageDTO;
import com.dutmdcjf.moviereviewproject.dto.PageRequestDTO;
import com.dutmdcjf.moviereviewproject.dto.PageResultDTO;
import com.dutmdcjf.moviereviewproject.entity.Movie;
import com.dutmdcjf.moviereviewproject.entity.MovieImage;
import com.dutmdcjf.moviereviewproject.repository.MovieImageRepository;
import com.dutmdcjf.moviereviewproject.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    //Lombok의 @RequiredArgsConstructor를 통해 생성사 주입
    //생성자 주입이기 때문에 final도 가능능
    private final MovieRepository movieRepository;
    private final MovieImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {

        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(movieImage -> {
            imageRepository.save(movieImage);
        });

        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);

        System.out.println("getListPage result");
        System.out.println("===================");
        result.getContent().forEach(arr->{
            log.info(Arrays.toString(arr));
        });

        //Entity -> DTO
        Function<Object[], MovieDTO> function = (
                    arr -> entityToDTO(
                            (Movie) arr[0],
                            (List<MovieImage>) (Arrays.asList((MovieImage)arr[1])),
                            (Double) arr[2],
                            (Long) arr[3]
                    )
                );
        return new PageResultDTO<>(result,function);
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovie(mno);
        log.info(result);
        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImageList = new ArrayList<>();

        result.forEach(en -> {
            MovieImage movieImage = (MovieImage) en[1];
            movieImageList.add(movieImage);
        });

        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];


        return entityToDTO(movie, movieImageList, avg, reviewCnt);
    }
}
