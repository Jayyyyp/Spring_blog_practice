package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BlogServiceTest {

    @Autowired
    BlogService blogService;

    @Test
    @Transactional // 테스트 결과가 커밋하지않음
    public void findAllTest(){

        List<Blog>blogList = blogService.findAll();

        assertEquals(3, blogList.size());
    }

    @Test
    @Transactional
    public void findByIdTest(){
        long blogId = 2;

        Blog blog = blogService.findById(blogId);

        assertEquals(2, blog.getBlogId());
    }

    @Test
    @Transactional
    public void saveTest(){

        String writer = "수정된사람";
        String blogContent = "수정된본문";
        long blogId = 2;

        Blog blog = Blog.builder()
                .blogId(blogId)
                .writer(writer)
                .blogContent(blogContent)
                .build();


        blogService.save(blog);

        assertEquals(blogId, blog.getBlogId());
        assertEquals(writer, blog.getWriter());
        assertEquals(blogContent, blog.getBlogContent());
    }
}
