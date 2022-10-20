package com.sparta.post03.service;

import com.sparta.post03.dto.response.ResponseDto;
import com.sparta.post03.entity.PostLike;
import com.sparta.post03.entity.Member;
import com.sparta.post03.entity.Post;
import com.sparta.post03.exception.HeartException.LikeIsAlreadyExistException;
import com.sparta.post03.exception.MemberException.PostIdNotFoundException;
import com.sparta.post03.exception.PostException.RefreshTokenNotFoundException;
import com.sparta.post03.exception.PostException.TokenInvalidException;
import com.sparta.post03.jwt.provider.JwtProvider;
import com.sparta.post03.repository.PostLikeRepository;
import com.sparta.post03.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository heartRepository;
    private final JwtProvider jwtProvider;
    private final PostRepository postRepository;

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!jwtProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return jwtProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Transactional
    public ResponseEntity<?> pushPostLike(Long postId, HttpServletRequest request) {
        if(null == request.getHeader("Authorization")){
            throw new RefreshTokenNotFoundException();
        }
        if(null == request.getHeader("Refresh-Token")){
            throw new RefreshTokenNotFoundException();
        }
        Member member = validateMember(request);

        if(null == member){
            throw new TokenInvalidException();
        }
        Post post = isPresentPost(postId);
        if(post == null ){
            throw new PostIdNotFoundException();
        }
        Optional<PostLike> findPostLike = heartRepository.findByPostAndMember(post, member);
        findPostLike.ifPresentOrElse(
// 좋아요 있을경우 삭제
                postLike -> {
                    heartRepository.delete(postLike);
                    post.discountLike(postLike);
                    throw new LikeIsAlreadyExistException();
                },
//좋아요 없을 경우 좋아요 추가
                () -> {
                    PostLike heart = PostLike.builder()
                            .post(post)
                            .member(member)
                            .build();
                    heartRepository.save(heart);
                });
        return ResponseEntity.ok(ResponseDto.success(true));
    }
}
