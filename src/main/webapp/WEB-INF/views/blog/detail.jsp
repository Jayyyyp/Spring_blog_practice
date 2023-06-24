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
        <!-- Modal 자리 -->
        <div class="modal fade" id="replyUpdateModal" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">댓글 수정하기</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        작성자 : <input type="text" class="form-control" id="modalReplyWriter"><br>
                        댓글내용 : <input type="text" class="form-control" id="modalReplyContent">
                        <input type="hidden" id="modalReplyId" value="">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal" id="replyUpdateBtn">수정하기</button>
                    </div>
                </div>
            </div>
        </div>

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
            <div class="row">
                <!--비동기 form의 경우 목적지로 이동하지 않고 페이지 내에서 처리가 되므로
                    action을 가지지 않는다. 그리고 제출 버튼도 제출기능을 막고 fetch 요청만 넣는다.--->
                    <div class="col-2">
                        <input type="text" class="form-control" id="replyWriter" name="replyWriter">
                    </div>
                    <div class="col-6">
                        <input type="text" class="form-control" id="replyContent" name="replyContent">
                    </div>
                    <div class="col-2">
                        <button class="btn btn-primary" id="replySubmit">댓글쓰기</button>
                    </div>
            </div>
        </div>
    </div> <!--container-->
</body>
</html>