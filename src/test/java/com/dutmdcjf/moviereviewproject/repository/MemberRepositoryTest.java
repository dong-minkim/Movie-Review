package com.dutmdcjf.moviereviewproject.repository;

import com.dutmdcjf.moviereviewproject.entity.Member;
import com.dutmdcjf.moviereviewproject.entity.Movie;
import com.dutmdcjf.moviereviewproject.entity.MovieImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembersDummies(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .email("user"+i+"@gmail.com")
                    .pw("1234")
                    .nickname("usernn"+i).build();
            memberRepository.save(member);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMember(){
        //Member의 Review 삭제 -> Member 삭제
        Long memid = 100L;

        Member member = Member.builder()
                .memid(memid).build();
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(memid);
    }
}
