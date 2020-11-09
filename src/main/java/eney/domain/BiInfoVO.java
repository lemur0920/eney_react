package eney.domain;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BiInfoVO {
    private Integer idx;
    private Integer service_idx;
    private String user_id;
    private String account_id;
    private String tracking_id;
    private String view_id;
    private String table_id;
    private String reg_date;
    private String file_name;
    private String status;

    private List<MultipartFile> files;
    private List<FileVO> fileVO_list;

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getService_idx() {
        return service_idx;
    }

    public void setService_idx(Integer service_idx) {
        this.service_idx = service_idx;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getTracking_id() {
        return tracking_id;
    }

    public void setTracking_id(String tracking_id) {
        this.tracking_id = tracking_id;
    }

    public String getView_id() {
        return view_id;
    }

    public void setView_id(String view_id) {
        this.view_id = view_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<FileVO> getFileVO_list() {
        return fileVO_list;
    }

    public void setFileVO_list(List<FileVO> fileVO_list) {
        this.fileVO_list = fileVO_list;
    }

    @Override
    public String toString() {
        return "BiInfoVO{" +
                "idx=" + idx +
                ", service_idx=" + service_idx +
                ", user_id='" + user_id + '\'' +
                ", account_id='" + account_id + '\'' +
                ", tracking_id='" + tracking_id + '\'' +
                ", view_id='" + view_id + '\'' +
                ", table_id='" + table_id + '\'' +
                ", reg_date='" + reg_date + '\'' +
                ", file_name='" + file_name + '\'' +
                ", status='" + status + '\'' +
                ", files=" + files +
                ", fileVO_list=" + fileVO_list +
                '}';
    }
}
