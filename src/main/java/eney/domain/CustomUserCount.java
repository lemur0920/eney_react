package eney.domain;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserCount {
    private int web_log;
    private int personal;
    private int corp;
    private int visit;
    private int api;
}
