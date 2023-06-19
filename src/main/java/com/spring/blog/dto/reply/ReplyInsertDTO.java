package com.spring.blog.dto.reply;

import com.spring.blog.entity.Reply;
import lombok.*;

@Setter@Getter@ToString
@AllArgsConstructor@NoArgsConstructor
@Builder
public class ReplyInsertDTO {
    private long blogId;
    private String replyWriter;
    private String replyContent;

    public ReplyInsertDTO(Reply reply){
        this.blogId = reply.getBlogId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
