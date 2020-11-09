package eney.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OpenStackUserVO {
    private int idx;
    private String user_id;
    private String passwd;
    private String token;
    private String identity_url;
    private String compute_url;

}
