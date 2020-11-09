package eney.domain;

public class BiCategoryVO {
    private Integer idx;
    private String category;
    private String sub_category;
    private Integer group_id;


    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    @Override
    public String toString() {
        return "BiCategoryVO{" +
                "idx=" + idx +
                ", category='" + category + '\'' +
                ", sub_category='" + sub_category + '\'' +
                ", group_id=" + group_id +
                '}';
    }
}
