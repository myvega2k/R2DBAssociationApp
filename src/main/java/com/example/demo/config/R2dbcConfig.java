package com.example.demo.config;

import com.example.demo.domain.post.Post;
import com.example.demo.domain.post.PostRepository;
import com.example.demo.domain.post.converter.PostReadConverter;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableR2dbcAuditing
@Slf4j
public class R2dbcConfig {

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions(DatabaseClient databaseClient) {
        var dialect = DialectResolver.getDialect(databaseClient.getConnectionFactory());
        var converters = new ArrayList<>(dialect.getConverters());
        converters.addAll(R2dbcCustomConversions.STORE_CONVERTERS);

        return new R2dbcCustomConversions(
                CustomConversions.StoreConversions.of(dialect.getSimpleTypeHolder(), converters),
                getCustomConverters());
    }

    private List<Object> getCustomConverters() {
        return List.of(new PostReadConverter());
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    @Bean
    public CommandLineRunner insert_find(UserRepository userRepository, PostRepository postRepository){
        return (args) -> {
            Flux<User> userFlux = userRepository.saveAll(Arrays.asList(
                    new User("Jack", 20),
                    new User("Chloe", 30)
            ));
            userFlux.doOnComplete(() -> System.out.println("User saveAll ok "))
                    .subscribe();


            postRepository.saveAll(Arrays.asList(
                    new Post("Jack title1", "Jack content1",1L),
                    new Post("Jack title2", "Jack content2",1L),
                    new Post("Chloe title", "Chloe content",2L)
            )).doOnComplete(() -> System.out.println("Post saveAll ok "))
                    .subscribe();
        };
    }

}
