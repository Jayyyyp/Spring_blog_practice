package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // DROP 테이블 시 필요
public class BlogRepositoryTest {

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach // 각 테스트 전 공통적으로 실행할 코드 저장하는곳
    public void setBlogTable(){
        blogRepository.createBlogTable();
        blogRepository.insertTestData();
    }

    @Test
    public void findAllTest(){
        // given
        int blogId = 1;
        // when
        List<Blog> blogList = blogRepository.findAll();

        //then
        // 2번째 객체의 ID번호는 2번일 것이다.
        assertEquals(2, blogList.get(blogId).getBlogId());
    }

    @Test
    public void findByIdTest(){
        // given : 조회할 ID
        long blogId = 2;
        String writer = "2번유저";
        String blogTitle = "2번제목";

        // when : 레포지토리에서 단일행 Blog를 얻어와 저장
        Blog blog = blogRepository.findById(blogId);

        // then : 해당 객체의 writer 멤버변수는 "2번 유저"이고, blogTitle은 "2번제목",
        //          blogId = 2
        assertEquals(blogTitle, blog.getBlogTitle());
        assertEquals(blogId, blog.getBlogId());
    }

    @Test
    public void saveTest(){
        String writer = "4번유저";
        String blogTitle = "4번제목";
        String blogContent = "4번본문";

        Blog blog = Blog.builder()
                .writer(writer)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();
        int blogId = 3;

        blogRepository.save(blog);
        List<Blog>blogList = blogRepository.findAll();

        assertEquals(writer, blogList.get(blogId).getWriter());
        assertEquals(blogTitle, blogList.get(blogId).getBlogTitle());
        assertEquals(blogContent, blogList.get(blogId).getBlogContent());
    }

    @Test
    public void deleteByIdTest(){
        long blogId = 2;

       blogRepository.deleteById(blogId);

       assertEquals(2, blogRepository.findAll().size());
       assertNull(blogRepository.findById(blogId));
    }

    @Test
    public void updateTest(){
        String blogContent = "수정한본문";
        String blogTitle = "수정한제목";
        long blogId = 2;

        blogRepository.findById(blogId);
        Blog blog = Blog.builder()
                .blogId(blogId)
                .blogContent(blogContent)
                .blogTitle(blogTitle)
                .build();

        blogRepository.update(blog);

        assertEquals(blogContent, blog.getBlogContent());
        assertEquals(blogTitle, blog.getBlogTitle());

    }
    @AfterEach
    public void dropBlogTable(){
        blogRepository.dropBlogTable();
    }
}

