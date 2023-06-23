package com.spring.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blog.dto.reply.ReplyResponseDTO;
import com.spring.blog.dto.reply.ReplyCreateResponseDTO;
import com.spring.blog.dto.reply.ReplyUpdateRequestDTO;
import com.spring.blog.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc  // MVC테스트는 브라우저를 켜야 원래 테스트가 가능하므로 브라우저를 대체할 객체를 만들어 수행
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper; // 데이터 직렬화에 사용하는 객체

    @Autowired
    private ReplyRepository replyRepository; // 임시적으로 ReplyRepository 생성
    // 레포지토리 레이어의 메서드는 쿼리문을 하나만 호출하는 것이 보장되지만
    // 서비스 레이어의 메서드는 추후에 쿼리문을 두 개 이상 호출할 수도 있고,
    // 그런 변경이 생겼을 때 테스트 코드도 같이 수정할 가능성이 생김

    // 컨트롤러를 테스트해야 하는데 컨트롤러는 서버에 url만 입력하면 동작하므로 컨트롤러를 따로 생성하지 않는다.
    // 각 테스트 전에 설정하기
    @BeforeEach
    public void setMockMvc(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    @Transactional
    @DisplayName("2번 글에 대한 댓글을 조회했을 때, 0번째 요소의 replyWriter는 댓글 쓴사람, replyId는 1")
    void findAllReplies() throws Exception {
        // given : fixture저장
        String replyWriter = "댓글쓴사람";
        long replyId = 1;
        String url = "/reply/2/all";

        // when : 해당 주소로 접속 후 json 데이터 리턴받아 저장하기, ResultActions 형 자료로 json 저장하기
                                     // fetch(url, {method:'get'}).then(res => res.json()); 에 대응하는 코드
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // then : 리턴받은 json 목록의 0번째 요소의 replyWriter와 replyId가 예상과 일치하는 지 확인
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter)) // $ -> 전체 데이터를 의미
                .andExpect(jsonPath("$[0].replyId").value(replyId));
    }

    @Test
    @Transactional
    @DisplayName("replyId 2번 조회시 얻어진 json 객체의 replyWriter는 챱챱맨, replyId는 2번")
    public void findByReplyIdTest() throws Exception{

        long replyId = 2;
        String replyWriter = "챱챱맨";
        String url = "/reply/2";

        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("replyWriter").value(replyWriter))
                .andExpect(jsonPath("replyId").value(replyId));
    }

    @Test
    @Transactional
    @DisplayName("blogId 1번 데이터 삽입 테스트")
    public void insertReplyTest() throws Exception{
        // given
        long blogId = 1;
        String replyWriter = "수정된사람";
        String replyContent = "수정된본문";
        String url = "/reply";
        String url2 = "/reply/1/all";
        ReplyCreateResponseDTO replyInsertDTO = new ReplyCreateResponseDTO(blogId, replyWriter, replyContent);

        // 데이터 직렬화
        final String requestBody = objectMapper.writeValueAsString(replyInsertDTO);

        // when : 직렬화된 데이터를 이용해 post방식으로 url에 요청
        mockMvc.perform(post(url) // reply 주소에 post방식으로 요청을 넣고
                .contentType(MediaType.APPLICATION_JSON) // 전달자료는 json이며
                .content(requestBody)); // 위에서 직렬화한 requestBody 변수를 전달할 것이다.

        // then : 위에서 blogId로 지정한  1번글의 전체 데이터를 가져와서
        //         픽스처와 replyWriter, replyContent가 일치하는 지 확인
        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter))
                .andExpect(jsonPath("$[0].replyContent").value(replyContent));
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 3번을 삭제시, 글번호 2번의 댓글수는 3개, 그리고 단일댓글 조회시 null")
    public void deleteByReplyTest() throws Exception{
        // given
        long replyId = 3;
        long blogId = 2;
        String url = "http://localhost:8080/reply/3";

        // when : 삭제 수행
        mockMvc.perform(delete(url).accept(MediaType.TEXT_PLAIN));
        // .accept는 리턴 데이터가 있는 경우에 해당 데이터를 어떤 형식으로 받아올지 기술

        // then : repository를 이용해 전체 데이터를 가져온 후, 개수 비교 및 삭제한 3번댓글은 null이 리턴되는지 확인
        List<ReplyResponseDTO> resultList = replyRepository.findAllByBlogId(blogId);
        assertEquals(3, resultList.size());
        ReplyResponseDTO result = replyRepository.findByReplyId(replyId);
        assertNull(result);
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 4번 replyWriter를 깃플로우로, replyContent를 깃깃으로 바꾼뒤 조회해보기")
    public void updateReplyTest() throws Exception{
        // given
        long replyId = 4;
        String replyWriter = "깃플로우";
        String replyContent = "깃깃";
        String url = "/reply/4"; // 4번댓글에 대한 수정요청 넣기
        ReplyUpdateRequestDTO replyUpdateRequestDTO = ReplyUpdateRequestDTO.builder()
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        // 데이터 직렬화
        final String requestBody = objectMapper.writeValueAsString(replyUpdateRequestDTO);

        // when
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("replyWriter").value(replyWriter))
                .andExpect(jsonPath("replyContent").value(replyContent));
    }


}