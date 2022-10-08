package com.dutmdcjf.moviereviewproject.repository;

import com.dutmdcjf.moviereviewproject.entity.Member;
import com.dutmdcjf.moviereviewproject.entity.Movie;
import com.dutmdcjf.moviereviewproject.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"})
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review r where r.member = :member")
    void deleteByMember(Member member);
}
