package lhb.gdms.commons.base.persistence;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description  带创建时间和更新时间字段的mapper的基类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 19:25
 */
public abstract class BaseDateEntity implements Serializable {

    private static final long serialVersionUID = -90013736L;

    // todo 日期格式化失效问题还未解决，暂时交给前端处理
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 更新时间
     */
    private Date updated;

    @Override
    public String toString() {
        return "BaseDateEntity{" +
                "created=" + created +
                ", updated=" + updated +
                '}';
    }


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
