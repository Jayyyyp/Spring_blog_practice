package com.spring.blog.dto.reply;

import com.spring.blog.entity.Reply;
import lombok.*;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder
public class ReplyUpdateRequestDTO {
    private long replyId;
    private String replyWriter;
    private String replyContent;

    public ReplyUpdateRequestDTO(Reply reply){
        this.replyId = reply.getReplyId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
