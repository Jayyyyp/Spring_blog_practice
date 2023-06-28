package com.spring.blog.entity;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Blog {
    private long blogId;
    private String writer;
    private String blogTitle;
    private String blogContent;
    private LocalDate publishedAt;
    private LocalDate updatedAt;
    private long blogCount;
}

