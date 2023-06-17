package com.spring.blog.controller;

import com.spring.blog.entity.Blog;
import com.spring.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // 컨트롤러 어노테이션은
            // 1. 빈등록 2. url 매핑 처리기능을 함께
            // 가지고 있어 다른 어노테이션과 교환해서 쓸 수  X
@RequestMapping("/blog")
public class BlogController {

    // 컨트롤러 레이어는 서비스 레이어 직접 호출
   @Autowired
    BlogService blogService;
   @Autowired
   public BlogController(BlogService blogService){
       this.blogService = blogService;
   }

   // /blog/list 주소를 get방식으로 접속했을때
    @GetMapping("/list")
    public String list(Model model){
        List<Blog> blogList = blogService.findAll();
        model.addAttribute("blogList", blogList);

        return "blog/list";
    }

    @GetMapping("/detail/{blogId}")
    public String detail(Model model, @PathVariable long blogId){

        model.addAttribute("blog", blogService.findById(blogId));
       return "blog/detail";
    }
}
