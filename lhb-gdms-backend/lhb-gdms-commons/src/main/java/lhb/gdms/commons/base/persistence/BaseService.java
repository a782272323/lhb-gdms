package lhb.gdms.commons.base.persistence;

import lhb.gdms.commons.domain.vo.QueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description  所有业务逻辑层的基类（Service）
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 19:31
 */
public interface BaseService<T extends BaseDateEntity> {

    /**
     * 查询列表全部信息,最好单表操作
     * @return
     */
    public List<T> select();

    /**
     * 查询列表全部信息
     * 分页
     * @return
     */
    public List<T> selectPageHelper(@Param("queryVO") QueryVO queryVO);

    /**
     * 查询列表全部信息的数据总和
     * 分页
     * @return
     */
    public Integer selectPageHelperCount();

    /**
     * 校验名字，昵称，用户名等等是否重复(添加数据时用)
     * @param name
     * @return 返回true 代表重复
     */
    public Boolean checkName(String name);

    /**
     * 校验名字，昵称，用户名等等是否重复(修改数据时用)
     * @param name
     * @param id
     * @return
     */
    public Boolean checkNameByUpdate(String name, Long id);

    /**
     * 根据实体类属性查询单条信息
     * @param t
     * @return
     */
    public T selectOneByKeyWord(T t);

    /**
     * 根据id查询单条数据
     * @param t
     * @return
     */
    public T selectOneById(T t);

    /**
     * 新增，适合单表操作
     * @param t
     * @return
     */
    public Integer insert(T t);

    /**
     * 编辑，适合单表操作
     * @param t
     * @return
     */
    public Integer update(T t);

    /**
     * 删除操作（删除单个且根据根据实体类属性）
     * @param t
     * @return
     */
    public Integer deleteOneByKeyWord(T t);

    /**
     * 批量删除
     * @param ids
     */
    public void deleteMulti(String[] ids);
}
