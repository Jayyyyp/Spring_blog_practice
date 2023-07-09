package com.spring.blog.controller;

import com.spring.blog.dto.reply.ReplyFindByIdDTO;
import com.spring.blog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    // 글번호에 맞는 전체 댓글을 가져오는 메서드
    // 어떤 자원에 접근할 것인지만 URI에 명시(메서드가 행동을 결정함)
    @GetMapping("/{blogId}/all")
    // rest서버는 응답시 응답코드와 응답 객체를 넘기기 때문에 ResponseEntity<자료형>을 리턴
    public ResponseEntity<List<ReplyFindByIdDTO>> findAllReplies(@PathVariable long blogId){ // 서비스에서 리플목록들고오기

        List<ReplyFindByIdDTO> replies = replyService.findAllByBlogId(blogId);

        return ResponseEntity
                .ok()                // 200 코드, 상태 코드와 body에 전송할 데이터를 같이 작성할 수도 있음
                 .body(replies);     // 리플 목록
        // detail : line 114
    }
}
