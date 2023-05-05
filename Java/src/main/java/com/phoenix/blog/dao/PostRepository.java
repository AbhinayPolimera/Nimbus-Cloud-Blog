package com.phoenix.blog.dao;

import com.phoenix.blog.model.Post;
import com.phoenix.blog.model.Subreddit;
import com.phoenix.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
