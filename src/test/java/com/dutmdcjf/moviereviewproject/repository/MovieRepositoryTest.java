package com.dutmdcjf.moviereviewproject.repository;

import com.dutmdcjf.moviereviewproject.entity.Movie;
import com.dutmdcjf.moviereviewproject.entity.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieImageRepository movieImageRepository;

    @Test
    public void insertMoviesDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Movie movie = Movie.builder().title(i + " Movie")
                    .build();

            movieRepository.save(movie);

            int movieImageCnt = (int)(Math.random() * 5) + 1;
            for(int j=0;j<movieImageCnt;j++){
                MovieImage movieImage = MovieImage.builder()
                        .imgName(movie.getTitle()+"_"+j+".jpg")
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .build();

                movieImageRepository.save(movieImage);
            }
        });
    }

    @Test
    public void testListPage(){
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "mno");

        Page<Object[]> result = movieRepository.getListPage(pageable);

        for(Object[] objects : result.getContent()){
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void testGetMovie(){
        List<Object[]> result = movieRepository.getMovie(4L);

        System.out.println(result);

        for(Object[] objects : result){
            System.out.println(Arrays.toString(objects));
        }
    }
}
