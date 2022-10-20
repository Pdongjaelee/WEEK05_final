package com.sparta.post03.repository;

import com.sparta.post03.entity.PostLike;
import com.sparta.post03.entity.Member;
import com.sparta.post03.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByPostAndMember(Post post, Member member);
}
