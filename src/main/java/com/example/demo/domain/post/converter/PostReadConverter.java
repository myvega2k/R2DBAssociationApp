package com.example.demo.domain.post.converter;

import com.example.demo.domain.post.Post;
import com.example.demo.domain.user.User;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDateTime;

@ReadingConverter
public class PostReadConverter implements Converter<Row, Post> {

    @Override
    public Post convert(Row source) {

        System.out.println("====> PostReadConverter source = " + source);
        User user = null;
        Post post = null;

        try {
            user = User.builder()
                    .id((Long) source.get("authorId"))
                    .name((String) source.get("authorName"))
                    .age((Integer) source.get("authorAge"))
                    .build();
            post = Post.builder()
                    .id((Long) source.get("postId"))
                    .title((String) source.get("postTitle"))
                    .content((String) source.get("postContent"))
                    .createdAt((LocalDateTime) source.get("postCreatedAt"))
                    .authorId(((Long) source.get("authorId")))
                    .author(user)
                    .build();
        }catch (IllegalArgumentException e) {
            System.out.println("PostReadConverter.convert = " + e.getMessage());
            post = Post.builder()
                    .id((Long) source.get("id"))
                    .title((String) source.get("title"))
                    .content((String) source.get("content"))
                    .createdAt((LocalDateTime) source.get("created_at"))
                    .updatedAt((LocalDateTime) source.get("updated_at"))
                    .build();
        }
        return post;

//        try {
//            user = User.builder()
//                    .id((Long) source.get("id"))
//                    .name((String) source.get("name"))
//                    .age((Integer) source.get("age"))
//                    .build();
//            post = Post.builder()
//                    .id((Long) source.get("id"))
//                    .title((String) source.get("title"))
//                    .content((String) source.get("content"))
//                    .createdAt((LocalDateTime) source.get("created_at"))
//                    .authorId(((Long) source.get("author_id")))
//                    .author(user)
//                    .build();
//        }catch (IllegalArgumentException e) {
////            post = Post.builder()
////                    .id((Long) source.get("id"))
////                    .title((String) source.get("title"))
////                    .content((String) source.get("content"))
////                    .createdAt((LocalDateTime) source.get("created_at"))
////                    .updatedAt((LocalDateTime) source.get("updated_at"))
////                    .build();
//        }
//        return post;
    }

}
