package com.example.demo.domain.post;

import reactor.core.publisher.Flux;

//DatabaseClient를 사용하는 Interface
public interface PostCustomRepository {

    Flux<Post> searchByKeyword(String keyword);

    Flux<PostUserSpecificInfo> findAllWithAuthor();

}
