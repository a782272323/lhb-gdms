package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysArticleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/25
 * @time 17:16
 */
@Repository
public interface SysArticleMapper extends BaseMapper<SysArticleEntity> {

    /**
     * 根据id查看单个文章详情
     * @param articleId
     * @return
     */
    List<SysArticleEntity> getArticleListById(@Param("articleId") Long articleId);

    /**
     * 新增且返回主键id
     * @param entity
     * @return
     */
    Integer insertData(@Param("entity") SysArticleEntity entity);

    /**
     * 查询文章相关详情用于收藏集模块
     * @param articleId
     * @return
     */
    List<Map<String, Object>> getArticlesDetailsToCollection(@Param("articleId") Long articleId);
}
