package lhb.gdms.commons.domain.vo;

import java.io.Serializable;

/**
 * @Description  分页，排序参数
 * @author Herbie Leung
 * @date 2020/7/2
 * @time 16:09
 */
public class QueryVO implements Serializable {


    private static final long serialVersionUID = -90018628L;
    /**
     * 当前页号
     */
    private Integer offSet;
    /**
     * 页面容量
     */
    private Integer limit;
    /**
     * 排序关键字段
     */
    private String keyWord;
    /**
     * 排序类型
     */
    private String orderBy;

    public Integer getOffSet() {
        return offSet;
    }

    public void setOffSet(Integer offSet) {
        this.offSet = offSet;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public String toString() {
        return "TableParams{" +
                "offSet=" + offSet +
                ", limit=" + limit +
                ", keyWord='" + keyWord + '\'' +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }
}
