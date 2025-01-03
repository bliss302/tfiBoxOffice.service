package com.movies.tfi.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @Id
    private long id;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
