package com.spring.blog.controller;

import com.spring.blog.exception.NotFoundByReplyIdException;
import com.spring.blog.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ReplyResponseDTO>> findAllReplies(@PathVariable long blogId){

        List<ReplyResponseDTO> replies = replyService.findAllByBlogId(blogId);
        return ResponseEntity.ok().body(replies);
    }

    // replyId를 주소에 포함시켜 요청하면 해당 번호 댓글 정보를 json으로 리턴하는 메서드
    // 예시) /reply/5 -> replyId 변수에 5가 대입되도록 주소 설정 및 메서드 선언

    @GetMapping("/{replyId}")
    public ResponseEntity<?> findByReplyId(@PathVariable long replyId){
        ReplyResponseDTO replyResponseDTO = replyService.findByReplyId(replyId);

        if (replyResponseDTO == null) {
            try {
                throw new NotFoundByReplyIdException("없는 리플 번호를 조회했습니다.");
            } catch (NotFoundByReplyIdException e) {
                e.printStackTrace();
                return new ResponseEntity<>("찾는 댓글 번호가 없습니다.", HttpStatus.NOT_FOUND);
            }
        } return ResponseEntity.ok(replyResponseDTO);
        // return new ResponseEntity<ReplyFindByIdDTO>(replyFindByIdDTO, HttpStatus.OK);
    }

    @PostMapping("")
        // REST 컨트롤러는 데이터를 json으로 주고받기 때문에 @RequestBody를 이용해
        // json으로 들어온 데이터를 역직렬화 하도록 설정
    public ResponseEntity<String> insertReply(@RequestBody ReplyCreateResponseDTO replyCreateResponseDTO){
        replyService.save(replyCreateResponseDTO);

        return ResponseEntity.ok("댓글 등록이 잘되었네요, 허허");
    }

    // delete 방식으로 /reply/{댓글번호} 주소로 요청이 들어왔을때 실행되는 메서드 작성
    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable long replyId){
        replyService.deleteByReplyId(replyId);

        return ResponseEntity.ok("댓글이 잘 삭제되었습니다.");
    }

    // 수정로직은 put, patch 메서드로 "/reply/댓글번호" 주소로
    // ReplyUpdateRequestDTO를 requestBody로 받아 요청처리를 하게 만들기
    @RequestMapping(value = "/{replyId}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<String> updateReply(@PathVariable long replyId,
                                              @RequestBody ReplyUpdateRequestDTO replyUpdateRequestDTO){
        // json데이터에 replyId를 포함하는 대신 url에 포함시켰으므로, requestBody에 추가시켜줘야 함
        replyUpdateRequestDTO.setReplyId(replyId);
        replyService.update(replyUpdateRequestDTO);
        
        return ResponseEntity.ok("댓글 수정 완료되었습니다.");
    }
}
