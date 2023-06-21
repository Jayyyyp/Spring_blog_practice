package com.spring.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.blog.dto.reply.ReplyInsertDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        ReplyInsertDTO replyInsertDTO = new ReplyInsertDTO(blogId, replyWriter, replyContent);

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


}