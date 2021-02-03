package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  输入框历史记录表 sys_input_history 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/1/22
 * @time 23:14
 */
public class SysInputHistoryEntity extends BaseDateEntity {
    private static final long serialVersionUID = -94695943L;
    /**
     * id
     */
    private Long sysInputHistoryId;
    /**
     * 输入框搜索内容
     */
    private String sysInputHistoryContent;
    /**
     * 用户id
     */
    private Long sysUserId;

    @Override
    public String toString() {
        return "SysInputHistoryEntity{" +
                "sysInputHistoryId=" + sysInputHistoryId +
                ", sysInputHistoryContent='" + sysInputHistoryContent + '\'' +
                ", sysUserId=" + sysUserId +
                '}';
    }

    public Long getSysInputHistoryId() {
        return sysInputHistoryId;
    }

    public void setSysInputHistoryId(Long sysInputHistoryId) {
        this.sysInputHistoryId = sysInputHistoryId;
    }

    public String getSysInputHistoryContent() {
        return sysInputHistoryContent;
    }

    public void setSysInputHistoryContent(String sysInputHistoryContent) {
        this.sysInputHistoryContent = sysInputHistoryContent;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }
}
