package lhb.gdms.commons.base.persistence;


import lhb.gdms.commons.domain.vo.QueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description  所有数据访问层的基类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 19:21
 */
public interface BaseMapper<T extends BaseDateEntity> {

    /**
     * 查询列表全部信息,最好单表操作
     * 不分页
     * @return
     */
    List<T> select();

    /**
     * 查询列表全部信息
     * 分页
     * @param queryVO
     * @return
     */
    List<T> selectPageHelper(@Param("queryVO")QueryVO queryVO);

    /**
     * 查询列表全部信息的数据总和
     * 分页
     * @return
     */
    Integer selectPageHelperCount();

    /**
     * 校验名字，昵称，用户名等等是否重复(添加数据时用)
     * @param name
     * @return
     */
    String checkName(@Param("name") String name);

    /**
     * 校验名字，昵称，用户名等等是否重复(修改数据时用)
     * @param name
     * @param id
     * @return
     */
    String checkNameByUpdate(@Param("name") String name, @Param("id") Long id);

    /**
     * 根据实体类属性查询单条信息
     * @param t
     * @return
     */
    T selectOneByKeyWord(@Param("entity") T t);

    /**
     * 根据id查询单条数据
     * @param t
     * @return
     */
    T selectOneById(@Param("entity") T t);

    /**
     * 新增，适合单表操作
     * @param t
     * @return
     */
    Integer insert(@Param("entity") T t);

    /**
     * 编辑，适合单表操作
     * @param t
     * @return
     */
    Integer update(@Param("entity") T t);

    /**
     * 删除操作（删除单个且根据根据实体类属性）
     * @param t
     * @return
     */
    Integer deleteOneByKeyWord(@Param("entity") T t);

    /**
     * 通过主键id实现单个删除操作
     * @param id
     * @return
     */
    Integer deleteOneById(@Param("id") Long id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteMulti(@Param("ids") String[] ids);
}
