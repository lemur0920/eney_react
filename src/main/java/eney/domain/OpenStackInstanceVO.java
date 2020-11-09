package eney.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OpenStackInstanceVO {
    private int idx;
    private String instance_id;
    private int apply_idx;
    private String user_id;
    private String status;
}
