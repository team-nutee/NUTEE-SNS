package kr.nutee.nuteebackend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.nutee.nuteebackend.DTO.Request.*;
import kr.nutee.nuteebackend.Enum.ErrorCode;
import kr.nutee.nuteebackend.Exception.GlobalExceptionHandler;
import kr.nutee.nuteebackend.Exception.NotAllowedException;
import kr.nutee.nuteebackend.Interceptor.HttpInterceptor;
import kr.nutee.nuteebackend.Service.MemberService;
import kr.nutee.nuteebackend.Service.PostService;
import kr.nutee.nuteebackend.Service.Util;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/sns/post",consumes = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final GlobalExceptionHandler globalExceptionHandler;
    private final Util util;

    @GetMapping(path = "/test")
    public void test() {
        throw new NotAllowedException("접근 권한이 없는 유저입니다.", ErrorCode.ACCEPT_DENIED);
    }

    /*
        즐겨찾기 게시판 불러오기
     */
    @GetMapping(path = "/preference")
    public ResponseEntity<Object> getPreferencePosts(
            HttpServletRequest request,
            @RequestParam("lastId") int lastId,
            @RequestParam("limit") int limit
    ){
        Long memberId = util.getTokenMemberId(request);
        System.out.println(memberId);
        return new ResponseEntity<>(postService.getPreferencePosts((long)lastId,limit,memberId), HttpStatus.OK);
    }

    /*
        카테고리 게시판 불러오기
     */
    @GetMapping(path = "")
    public ResponseEntity<Object> getCategoryPosts(
            @RequestParam("category") String category,
            @RequestParam("lastId") int lastId,
            @RequestParam("limit") int limit
    ){
        return new ResponseEntity<>(postService.getCategoryPosts((long)lastId,limit,category), HttpStatus.OK);
    }

    /*
        글 작성
     */
    @PostMapping(path = "")
    public ResponseEntity<Object> createPost(
            HttpServletRequest request,
            @RequestBody @Valid CreatePostRequest body
    ){
        Long id = util.getTokenMemberId(request);
        return new ResponseEntity<>(postService.createPost(id,body), HttpStatus.OK);
    }

    /*
        글 읽기
     */
    @GetMapping(path = "/{postId}")
    public ResponseEntity<Object> getPost(
            HttpServletRequest request,
            @PathVariable String postId
    ){
        Long memberId = util.getTokenMemberId(request);
        return new ResponseEntity<>(postService.getPost(Long.parseLong(postId),memberId), HttpStatus.OK);
    }

    /*
        글 수정
     */
    @PatchMapping(path = "/{postId}")
    public ResponseEntity<Object> updatePost(
            HttpServletRequest request,
            @PathVariable String postId,
            @RequestBody @Valid UpdatePostRequest body
    ){
        Long memberId = util.getTokenMemberId(request);
        return new ResponseEntity<>(postService.updatePost(memberId,Long.parseLong(postId), body), HttpStatus.OK);
    }

    /*
        글 삭제
     */
    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<Object> deletePost(
            @PathVariable String postId
    ){
        return new ResponseEntity<>(postService.deletePost(Long.parseLong(postId)),HttpStatus.OK);
    }

    /*
        글 신고
     */
    @PostMapping(path = "/{postId}/report")
    public ResponseEntity<Object> reportPost(
            @PathVariable String postId,
            HttpServletRequest request,
            @RequestBody @Valid ReportRequest body
            ){
        Long memberId = util.getTokenMemberId(request);
        return new ResponseEntity<>(postService.reportPost(Long.parseLong(postId),memberId,body.getContent()),HttpStatus.OK);
    }

    /*
        댓글목록 읽기
     */
    @GetMapping(path = "/{postId}/comments")
    public ResponseEntity<Object> getComments(
            @PathVariable String postId,
            @RequestParam("lastId") Long lastId,
            @RequestParam("limit") int limit

    ){
        return new ResponseEntity<>(postService.getComments(Long.parseLong(postId)),HttpStatus.OK);
    }

    /*
        댓글 작성
     */
    @PostMapping(path = "/{postId}/comment")
    public ResponseEntity<Object> createComment(
            @PathVariable String postId,
            HttpServletRequest request,
            @RequestBody @Valid CommentRequest body
    ){
        Long memberId = util.getTokenMemberId(request);

        return new ResponseEntity<>(
                postService.createComment(memberId,Long.parseLong(postId),body.getContent()),HttpStatus.OK
        );
    }

    /*
        댓글 수정
     */
    @PatchMapping(path = "/{postId}/comment/{commentId}")
    public ResponseEntity<Object> updateComment(
            @PathVariable String postId,
            @PathVariable String commentId,
            HttpServletRequest request,
            @RequestBody @Valid CommentRequest body
    ){
        Long memberId = util.getTokenMemberId(request);
        return new ResponseEntity<>(
                postService.updateComment(memberId,Long.parseLong(commentId),body.getContent()),HttpStatus.OK
        );
    }

    /*
        답글 생성
     */
    @PostMapping(path = "/{postId}/comment/{parentId}")
    public ResponseEntity<Object> createReComment(
            @PathVariable String postId,
            @PathVariable String parentId,
            HttpServletRequest request,
            @RequestBody @Valid CommentRequest body
    ){
        Long memberId = util.getTokenMemberId(request);
        return new ResponseEntity<>(
                postService.createReComment(memberId,Long.parseLong(parentId),Long.parseLong(postId),body.getContent()),HttpStatus.OK
        );
    }

    /*
        댓글 삭제
     */
    @DeleteMapping(path = "/{postId}/comment/{commentId}")
    public ResponseEntity<Object> deleteComment(
            @PathVariable String postId,
            @PathVariable String commentId,
            HttpServletRequest request
    ){
        Long memberId = util.getTokenMemberId(request);
        return new ResponseEntity<>(
                postService.deleteComment(memberId,Long.parseLong(commentId),Long.parseLong(postId)),HttpStatus.OK
        );
    }

    @PostMapping(path = "/{postId}/like")
    public ResponseEntity<Object> likePost(
            @PathVariable String postId,
            HttpServletRequest request
    ){
        Long memberId = util.getTokenMemberId(request);
        return new ResponseEntity<>(
                postService.likePost(Long.parseLong(postId),memberId),HttpStatus.OK
        );
    }

    @DeleteMapping(path = "/{postId}/like")
    public ResponseEntity<Object> unlikePost(
            @PathVariable String postId,
            HttpServletRequest request
    ){
        Long memberId = util.getTokenMemberId(request);
        return new ResponseEntity<>(
                postService.unlikePost(Long.parseLong(postId),memberId),HttpStatus.OK
        );
    }


    @PostMapping(path = "/{postId}/retweet")
    public ResponseEntity<Object> retweetPost(
            @PathVariable String postId,
            HttpServletRequest request,
            @RequestBody @Valid RetweetRequest body
    ){
        Long memberId = util.getTokenMemberId(request);
        return new ResponseEntity<>(
                postService.createRetweet(Long.parseLong(postId),memberId,body),HttpStatus.OK
        );
    }

}

