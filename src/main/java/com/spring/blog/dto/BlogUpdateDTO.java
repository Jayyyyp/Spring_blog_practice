package com.spring.blog.dto;

import com.spring.blog.entity.Blog;
import lombok.*;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder
public class BlogUpdateDTO {
    private long blogId;
    private String writer;
    private String blogTitle;
    private String blogContent;

    BlogUpdateDTO(Blog blog){
        this.blogId = blog.getBlogId();
        this.writer = blog.getWriter();
        this.blogTitle = blog.getBlogContent();
        this.blogContent = blog.getBlogContent();
    }
}
