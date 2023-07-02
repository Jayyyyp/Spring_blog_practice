package com.spring.blog.dto.reply;

import com.spring.blog.entity.Reply;
import lombok.*;


@Getter @ToString @Setter
@AllArgsConstructor @NoArgsConstructor @Builder
public class ReplyUpdateDTO {
    private long replyId;
    private String replyWriter;
    private String replyContent;

    public ReplyUpdateDTO(Reply reply){
        this.replyId = reply.getReplyId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
