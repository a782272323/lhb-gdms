package lhb.gdms.consumer.blog.domain.cover;

import lhb.gdms.commons.domain.entity.SysDraftEntity;
import lhb.gdms.consumer.blog.domain.vo.ArticleVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Description  表 sys_draft 属性转化接口
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/24
 * @time 21:06
 */
@Mapper(componentModel = "spring")
public interface SysDraftCover {

    SysDraftCover INSTANCE = Mappers.getMapper(SysDraftCover.class);

    /**
     * ArticleVO 转换
     */

    /**
     * entity 实体类属性拷贝给 vo
     * @param entity
     * @return
     */
    @Mappings({
            @Mapping(target = "id", source = "sysDraftId"),
            @Mapping(target = "title", source = "draftTitle"),
            @Mapping(target = "content", source = "draftContent"),
            @Mapping(target = "coverImgUrl", source = "draftImgUrl"),
            @Mapping(target = "key", source = "draftImgKey"),
            @Mapping(target = "sysUserId", source = "sysUserId"),
            @Mapping(target = "type", source = "draftType")
    })
    ArticleVO toCoverVO(SysDraftEntity entity);

    /**
     * vo 属性拷贝给 entity
     *
     * @param articleVO
     * @return
     */
    @Mappings({
            @Mapping(source = "id", target = "sysDraftId"),
            @Mapping(source = "title", target = "draftTitle"),
            @Mapping(source = "content", target = "draftContent"),
            @Mapping(source = "coverImgUrl", target = "draftImgUrl"),
            @Mapping(source = "key", target = "draftImgKey"),
            @Mapping(source = "sysUserId", target = "sysUserId"),
            @Mapping(source = "type", target = "draftType")
    })
    SysDraftEntity fromCoverEntity(ArticleVO articleVO);
}
