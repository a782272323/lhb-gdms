package lhb.gdms.consumer.admin.domain.vo;

import java.io.Serializable;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2021/2/4
 * @time 11:54
 */
public class BlogOverviewSearchVO implements Serializable {
    private static final long serialVersionUID = -90000122L;
    private String sysUserNickname;
    private String articleTitle;
    private String labelName;

    @Override
    public String toString() {
        return "BlogOverviewSearchVO{" +
                "sysUserNickname='" + sysUserNickname + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", labelName='" + labelName + '\'' +
                '}';
    }

    public String getSysUserNickname() {
        return sysUserNickname;
    }

    public void setSysUserNickname(String sysUserNickname) {
        this.sysUserNickname = sysUserNickname;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
