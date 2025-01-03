package com.movies.tfi.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAuthResponse {
    private String accessToken;

    @Builder.Default
    private String tokenType = "Bearer";
}
