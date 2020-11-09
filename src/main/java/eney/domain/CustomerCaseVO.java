package eney.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCaseVO extends PageVO{
    private int rownum;
    private int idx;
    private String content;
    private String clients;
    private String link;
    private String project;
    private String description;
    private String tumbnail_file;
    private Date release_date;
    private int type_code;

    private MultipartFile tumbnail;


    private int code;
    private String code_name;
}
