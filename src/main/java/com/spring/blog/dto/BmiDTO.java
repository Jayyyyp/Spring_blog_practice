package com.spring.blog.dto;

import lombok.*;

@Getter@Setter@ToString
@AllArgsConstructor@NoArgsConstructor
@Builder
public class BmiDTO {
    private double height;
    private double weight;
}
