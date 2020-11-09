package eney.domain.payload;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String userId;
    private String username;
    private String epoint;
    private String role;
    private String accessToken;
    private String tokenType = "Bearer";
    private String email;

}
