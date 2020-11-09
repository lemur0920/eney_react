package eney.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OpenStackNetworkVO {
    private int idx;
    private String network_name;
    private String network_uuid;
}
