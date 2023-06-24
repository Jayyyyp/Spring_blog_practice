package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService{

    BlogRepository blogRepository;


    @Autowired  // 생성자 주입이 속도가 빠름
    public BlogServiceImpl(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog findById(long blogId) {
        return blogRepository.findById(blogId);
    }

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    @Transactional // 댓글과 블로그가 같이 삭제되어야 한다.(★원자성★)
    @Override
    public void deleteById(long blogId) {
        blogRepository.deleteById(blogId);
    }

    @Override
    public void update(Blog blog) {
        blogRepository.update(blog);
    }
}
