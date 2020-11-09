package eney.domain.payload;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PasswordRequest {

    private String userId;
    private String currentPw;
    private String newPw;
    private String newPwCheck;

}