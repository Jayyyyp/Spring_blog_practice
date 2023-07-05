package com.spring.blog.controller;

import com.spring.blog.entity.Blog;
import com.spring.blog.exception.NotFoundBlogException;
import com.spring.blog.service.BlogService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // 컨트롤러 어노테이션은
            // 1. 빈등록 2. url 매핑 처리기능을 함께
            // 가지고 있어 다른 어노테이션과 교환해서 쓸 수  X
@RequestMapping("/blog")
@Log4j2
public class BlogController {

    // 컨트롤러 레이어는 서비스 레이어 직접 호출한다.
   @Autowired
    BlogService blogService;
   @Autowired
   public BlogController(BlogService blogService){
       this.blogService = blogService;
   }

   // /blog/list 주소를 get방식으로 접속했을때의 경우
    @GetMapping("/list")
    public String list(Model model){
        List<Blog> blogList = blogService.findAll();
        model.addAttribute("blogList", blogList);

        return "blog/list";
    }

    @GetMapping("/detail/{blogId}")
    public String detail(Model model, @PathVariable long blogId){
       Blog blog = blogService.findById(blogId);
       if(blog == null){
           try {
               throw new NotFoundBlogException("없는 blogId로 조회했습니다. 조회번호" + blogId);
           }catch (NotFoundBlogException e) {
               return "blog/NotFoundBlogIdExceptionResultPage";
           }
       }
        model.addAttribute("blog", blog);
       return "blog/detail";
    }

    @GetMapping("/insert")
    public String insert(){
       return "blog/blog-form";
    }

    @PostMapping("/insert")
    public String insert(Blog blog){
       blogService.save(blog);

        return "redirect:/blog/list";
    }

    @PostMapping("/delete")
    public String delete(long blogId){
       blogService.deleteById(blogId);
       log.info(blogId);

       return "redirect:/blog/list";
    }

    @PostMapping("/updateform")
    public String update(long blogId, Model model){
       Blog blog = blogService.findById(blogId);
       model.addAttribute(blog);
       return "blog/blog-update-form";
    }

    @PostMapping("/update")
    public String update(Blog blog){
       blogService.update(blog);
       return"redirect:/blog/detail/" + blog.getBlogId();
    }

}
