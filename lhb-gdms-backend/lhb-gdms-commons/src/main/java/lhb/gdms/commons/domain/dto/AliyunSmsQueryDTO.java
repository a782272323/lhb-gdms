package lhb.gdms.commons.domain.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  阿里云短信查询实体
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/10
 * @time 12:25
 */
public class AliyunSmsQueryDTO implements Serializable {


    private static final long serialVersionUID = -90017313L;
    private String bizId;
    private String phone;
    private Date sendDate;
    private Long pageSize;
    private Long currentPage;

    @Override
    public String toString() {
        return "AliyunSmsQueryDTO{" +
                "bizId='" + bizId + '\'' +
                ", phone='" + phone + '\'' +
                ", sendDate=" + sendDate +
                ", pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                '}';
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }
}
