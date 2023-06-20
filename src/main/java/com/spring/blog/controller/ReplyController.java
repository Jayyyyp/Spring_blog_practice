package com.spring.blog.controller;

import com.spring.blog.dto.reply.ReplyFindByIdDTO;
import com.spring.blog.entity.Reply;
import com.spring.blog.service.ReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    // 컨트롤러는 서비스를 호출한다.
    ReplyService replyService;

    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    // 글 번호에 맞는 전체 댓글을 가져오는 메서드
    @GetMapping("/{blogId}/all")
    public ResponseEntity<List<ReplyFindByIdDTO>> findAllReplies(@PathVariable long blogId){

        List<ReplyFindByIdDTO> replies = replyService.findAllByBlogId(blogId);
        return ResponseEntity.ok().body(replies);
    }
}
