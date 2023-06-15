package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        String writer = "추가된사람";
        String blogContent = "추가된본문";
        String blogTitle = "추가된제목";

        Blog blog = Blog.builder()
                .blogTitle(blogTitle)
                .writer(writer)
                .blogContent(blogContent)
                .build();
        int blogId = 3;

        blogService.save(blog);

        assertEquals(writer, blogService.findAll().get(blogId).getWriter());
        assertEquals(blogContent, blogService.findAll().get(blogId).getBlogContent());
        assertEquals(blogTitle, blogService.findAll().get(blogId).getBlogTitle());
        assertEquals(4, blogService.findAll().size());
    }

    @Test
    @Transactional
    public void deleteByIdTest(){
        long blogId = 2;

        blogService.deleteById(blogId);

        assertEquals(2, blogService.findAll().size());
        assertNull(blogService.findById(blogId));
    }

    @Test
    @Transactional
    public void updateTest(){
        String blogContent = "업데이트된본문";
        String blogTitle = "업데이트된제목";
        long blogId = 2;

        Blog blog = Blog.builder()
                .blogId(blogId)
                .blogContent(blogContent)
                .blogTitle(blogTitle)
                .build();

        blogService.update(blog);

        assertEquals(blogId, blogService.findById(blogId).getBlogId());
        assertEquals(blogTitle, blogService.findById(blogId).getBlogTitle());
        assertEquals(blogContent, blogService.findById(blogId).getBlogContent());
    }
}
