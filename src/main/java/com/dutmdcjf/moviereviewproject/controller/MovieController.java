package com.dutmdcjf.moviereviewproject.controller;

import com.dutmdcjf.moviereviewproject.dto.MovieDTO;
import com.dutmdcjf.moviereviewproject.dto.PageRequestDTO;
import com.dutmdcjf.moviereviewproject.dto.PageResultDTO;
import com.dutmdcjf.moviereviewproject.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes){
        System.out.println("register");
        System.out.println(movieDTO);
        log.info(movieDTO);

        Long mno = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("msg", mno);

        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        System.out.println("list 단계");
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    @GetMapping("/read")
    public void read(long mno, @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model){
        MovieDTO movieDTO = movieService.getMovie(mno);

        model.addAttribute("dto", movieDTO);
    }
}
