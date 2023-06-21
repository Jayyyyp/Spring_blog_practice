package com.spring.blog.dto.reply;

import com.spring.blog.entity.Reply;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;

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

//    private void errorCheck(){
//        if(blogId == 0 || replyWriter == null || replyContent == null){
//            throw  new DataIntegrityViolationException("필드주입이 되어야합니다.");
//        }
//    }
}
