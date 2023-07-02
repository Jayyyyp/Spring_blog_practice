package com.spring.blog.dto.reply;

import com.spring.blog.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter@ToString@Setter
@AllArgsConstructor@NoArgsConstructor@Builder
public class ReplyFindByIdDTO {
    private long replyId;
    private String replyWriter;
    private String replyContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;

    // DTO는 entity 객체를 이용해 생성될 수 있어야 하나, 반대는 성립하지 않는다
    // entity는 DTO의 내부 구조를 알 필요가 없다
    public ReplyFindByIdDTO(Reply reply){
        this.replyId = reply.getReplyId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
        this.publishedAt = reply.getPublishedAt();
        this.updatedAt = reply.getUpdatedAt();
    }
}
