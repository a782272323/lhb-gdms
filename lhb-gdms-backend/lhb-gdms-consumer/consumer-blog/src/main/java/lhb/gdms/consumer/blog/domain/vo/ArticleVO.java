package lhb.gdms.consumer.blog.domain.vo;

import lhb.gdms.commons.base.persistence.BaseDateEntity;

import java.io.PipedReader;
import java.io.Serializable;

/**
 * @Description  文章编辑前端参数，适用于写文章和草稿箱模块
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/24
 * @time 20:47
 */
public class ArticleVO extends BaseDateEntity {

    private static final long serialVersionUID = -90000010L;
    /**
     * id
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 文章类型
     */
    private String type;
    /**
     * 封面图片
     */
    private String coverImgUrl;
    /**
     * 封面图片七牛云key
     */
    private String key;
    /**
     * 用户id
     */
    private Long sysUserId;

    @Override
    public String toString() {
        return "ArticleVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", coverImgUrl='" + coverImgUrl + '\'' +
                ", key='" + key + '\'' +
                ", sysUserId=" + sysUserId +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }
}
