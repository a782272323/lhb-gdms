package lhb.gdms.consumer.blog.domain.cover;

import lhb.gdms.commons.domain.entity.SysArticleEntity;
import lhb.gdms.consumer.blog.domain.vo.ArticleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description  表 sys_article 属性转化接口
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/25
 * @time 16:03
 */
@Mapper(componentModel = "spring")
public interface SysArticleCover {

    SysArticleCover INSTANCE = Mappers.getMapper(SysArticleCover.class);

    /**
     * ArticleVO 转化
     */

    /**
     *  entity 实体类属性拷贝给 vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "id", source = "articleId"),
            @Mapping(target = "title", source = "articleTitle"),
            @Mapping(target = "content", source = "articleContent"),
            @Mapping(target = "coverImgUrl", source = "articleImgUrl"),
            @Mapping(target = "key", source = "articleImgKey"),
            @Mapping(target = "sysUserId", source = "sysUserId"),
            @Mapping(target = "type", source = "articleType"),
            @Mapping(target = "sum", source = "articleBrowseSum")
    })
    ArticleVO toCoverVO(SysArticleEntity entity);

    /**
     * vo 属性拷贝给 entity
     * @param vo
     * @return
     */
    @Mappings({
            @Mapping(source = "id", target = "articleId"),
            @Mapping(source = "title", target = "articleTitle"),
            @Mapping(source = "content", target = "articleContent"),
            @Mapping(source = "coverImgUrl", target = "articleImgUrl"),
            @Mapping(source = "key", target = "articleImgKey"),
            @Mapping(source = "sysUserId", target = "sysUserId"),
            @Mapping(source = "type", target = "articleType"),
            @Mapping(source = "sum", target = "articleBrowseSum")
    })
    SysArticleEntity fromCoverEntity(ArticleVO vo);
}
