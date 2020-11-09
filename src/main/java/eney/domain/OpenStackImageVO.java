package eney.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OpenStackImageVO {
    private int idx;
    private String image_name;
    private String image_id;
}
