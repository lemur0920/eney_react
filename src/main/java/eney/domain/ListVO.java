package eney.domain;

import java.util.List;


public class ListVO {
    private List list;
    private Pagination pagination;

    public ListVO() {
    }

    public ListVO(List list, Pagination pagination) {
        this.list = list;
        this.pagination = pagination;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "ListVO{" +
                "list=" + list +
                ", pagination=" + pagination +
                '}';
    }
}
