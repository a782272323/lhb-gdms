package lhb.gdms.consumer.blog.mapper;

import lhb.gdms.commons.base.persistence.BaseMapper;
import lhb.gdms.commons.domain.entity.SysArticleCollectionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * @Description  
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/12/27 
 * @time 19:28
 */
@Repository
public interface SysArticleCollectionMapper{


    /**
     * 根据id获取全部列表信息
     * @param sysCollectionId
     * @return
     */
    List<Map<String, Object>> getListsById(@PathParam("sysCollectionId") Long sysCollectionId);

    /**
     * 单个删除
     * @param articleId
     * @param sysCollectionId
     * @return
     */
    Integer deleteOne(@Param("articleId") Long articleId,
                      @Param("sysCollectionId") Long sysCollectionId);
}
