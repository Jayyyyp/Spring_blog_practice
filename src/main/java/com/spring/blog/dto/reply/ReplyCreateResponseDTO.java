package com.spring.blog.dto.reply;

import com.spring.blog.entity.Reply;
import lombok.*;

@Setter@Getter@ToString
@AllArgsConstructor@NoArgsConstructor
@Builder
public class ReplyCreateResponseDTO {
    private long blogId;
    private String replyWriter;
    private String replyContent;

    public ReplyCreateResponseDTO(Reply reply){
        this.blogId = reply.getBlogId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }

//    private void errorCheck(){
//        if(blogId == 0 || replyWriter == null || replyContent == null){
//            throw  new DataIntegrityViolationException("필드주입이 되어야합니다.");
//        }
//    }
}
