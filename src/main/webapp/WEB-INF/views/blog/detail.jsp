<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          crossorigin="anonymous">
    <style>
        div{
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <div class="container text-center">
        <h1>${blog.blogId}번 유저의 정보</h1>
        <div class="row first-row">
            <div class="col-1">
                글번호
            </div>
            <div class="col-1">
                ${blog.blogId}
            </div>
            <div class="col-1">
                글제목
            </div>
            <div class="col-4">
                ${blog.blogTitle}
            </div>
            <div class="col-1">
                작성자
            </div>
            <div class="col-2">
                ${blog.writer}
            </div>
            <div class="col-1">
                조회수
            </div>
            <div class="col-1">
                ${blog.blogCount}
            </div>
        </div> <!--.first-row-->
        <div class="row second-row">
            <div class="col-1">
                작성일
            </div>
            <div class="col-5">
               ${blog.publishedAt}
            </div>
            <div class="col-1">
                수정일
            </div>
            <div class="col-5">
                ${blog.updatedAt}
            </div>
        </div> <!--.row second-->
        <div class="row third-row">
            <div class="col-1">
                본문
            </div>
            <div class="col-11">
                ${blog.blogContent}
            </div>
        </div> <!--.third-row-->
        <div class="row fourth-row">
            <div class="col-1">
                <a href="/blog/list"><button class="btn btn-secondary">목록으로</button> </a>
            </div>
            <div class="col-1">
                <form action="/blog/delete" method="POST">
                    <input type="hidden" name="blogId" value="${blog.blogId}">
                    <input type="submit" value="삭제" class="btn btn-warning">
                </form>
            </div>
            <div class="col-1">
                <form action="/blog/updateform" method="POST">
                    <input type="hidden" name="blogId" value="${blog.blogId}">
                    <input type="submit" value="수정하기" class="btn btn-info">
                </form>
            </div>
        </div> <!--.fourth-row-->
        <div class="row">
            <div id="replies">

            </div>
        </div>
    </div> <!--container-->
    <script>
        // 글 구성에 필요한 글번호를 자바스크립트 변수에 저장
        let blogId = "${blog.blogId}";

        // blogId를 받아 전체 데이터를 JS 내부로 가져오는 함수
        function getAllReplies(Id){
            // <%--jsp와 js 모두 ${변수명} 문법을 공유하고, 이 중 .jsp파일에서는
            // ${}의 해석을 jsp식으로 먼저 하기 때문에, 해당 ${}가 백틱 내부에서 쓰이는 경우,
            // \${} 형식으로 \ 를 추가로 왼쪽에 붙여 jsp용으로 작성한것이 아님을 명시--%>
            let url = `http://localhost:8080/reply/\${Id}/all`;
            let str = ""; // 받아온 json을 표현할 html 코드를 저장할 문자열 str 선언
            fetch(url, {method:'get'}) // get방식으로 위 주소에 요청넣기
                .then((res) => res.json()) // 응답받은 요소중 json만 뽑기
                .then(replies => { // 뽑아온 json으로 처리작업하기
                    console.log(replies);
                    // for(reply of replies){
                    //     console.log(reply);
                    //     console.log("=============");
                    //     str += `<h3>글쓴이: \${reply.replyWriter}, 댓글내용: \${reply.replyContent}</h3>`;
                    // }

                    // .map()을 이용한 간결한 반복문 처리
                    replies.map((reply, i) =>{
                        // 첫 파라미터 : 반복 대상자료
                        // 두 번째 파라미터 : 순번
                        str += `<h3>\${i + 1}번째 댓글 || 글쓴이: \${reply.replyWriter},
                                댓글내용: \${reply.replyContent}</h3>`;
                    });

                    console.log(str); // 저장된 태그 확인
                    // #replies 요소를 변수에 저장하기
                    const $replies = document.getElementById('replies');
                    // 지정된 #replies의 innerHTML에 str을 대입하여 실제 화면에 출력되게하기
                    $replies.innerHTML = str;
                });
        }
        // 함수 호출
        getAllReplies(blogId);
    </script>
</body>
</html>