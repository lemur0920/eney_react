package eney.domain.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JoinReponse {

    private Boolean result = true;
    private String passwordError;
    private String userIdError;
    private String phoneNumError;
    private String emailError;
    private String certifyError;
    private String addressError;
    private String corporateError;

}
