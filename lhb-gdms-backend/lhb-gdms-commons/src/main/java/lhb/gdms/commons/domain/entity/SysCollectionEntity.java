package lhb.gdms.commons.domain.entity;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

/**
 * @Description  收藏集表 sys_collection 实体类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/22
 * @time 01:35
 */
public class SysCollectionEntity extends BaseDateEntity {

    private static final long serialVersionUID = -90000083L;
    /**
     * id
     */
    private Long sysCollectionId;
    /**
     * 收藏集名称
     */
    private String sysCollectionName;
    /**
     * 用户id
     */
    private Long sysUserId;

    @Override
    public String toString() {
        return "SysCollectionEntity{" +
                "sysCollectionId=" + sysCollectionId +
                ", sysCollectionName='" + sysCollectionName + '\'' +
                ", sysUserId=" + sysUserId +
                '}';
    }

    public Long getSysCollectionId() {
        return sysCollectionId;
    }

    public void setSysCollectionId(Long sysCollectionId) {
        this.sysCollectionId = sysCollectionId;
    }

    public String getSysCollectionName() {
        return sysCollectionName;
    }

    public void setSysCollectionName(String sysCollectionName) {
        this.sysCollectionName = sysCollectionName;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }
}
