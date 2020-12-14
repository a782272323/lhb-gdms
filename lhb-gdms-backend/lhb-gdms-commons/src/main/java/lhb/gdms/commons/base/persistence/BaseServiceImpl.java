package lhb.gdms.commons.base.persistence;

import com.github.pagehelper.PageHelper;
import lhb.gdms.commons.domain.vo.QueryVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description  所有业务逻辑层的实现类的抽象基类(ServiceImpl)
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/11/21
 * @time 19:34
 */
public class BaseServiceImpl<T extends BaseDateEntity, M extends BaseMapper<T>> implements BaseService<T>{

    /**
     * 注入mapper
     * 注意：这里只能用 autowired 不能用 resource
     */
    @Autowired
    protected M mapper;

    @Override
    public List<T> select() {
        return mapper.select();
    }

    /**
     * 查询列表全部信息
     * 分页
     * @return
     */
    @Override
    public List<T> selectPageHelper(@Param("queryVO") QueryVO queryVO) {
        PageHelper.startPage(queryVO.getLimit(), queryVO.getOffSet());
        return mapper.selectPageHelper(queryVO);
    }

    /**
     * 查询列表全部信息的数据总和
     * 分页
     * @return
     */
    @Override
    public Integer selectPageHelperCount() {
        return mapper.selectPageHelperCount();
    }

    /**
     * 校验名字，昵称，用户名等等是否重复
     * @param name
     * @return
     */
    @Override
    public Boolean checkName(String name) {
        return StringUtils.isNotBlank(mapper.checkName(name)) ? true : false;
    }

    /**
     * 根据实体类属性查询单条信息
     * @param t
     * @return
     */
    @Override
    public T selectOneByKeyWord(T t) {
        return mapper.selectOneByKeyWord(t);
    }

    /**
     * 根据id查询单条数据
     * @param t
     * @return
     */
    @Override
    public T selectOneById(T t) {
        return mapper.selectOneById(t);
    }

    /**
     * 新增，适合单表操作
     * @param t
     * @return
     */
    @Override
    public Integer insert(T t) {
        return mapper.insert(t);
    }

    /**
     * 编辑，适合单表操作
     * @param t
     * @return
     */
    @Override
    public Integer update(T t) {
        return mapper.update(t);
    }

    /**
     * 删除操作（删除单个且根据根据实体类属性）
     * @param t
     * @return
     */
    @Override
    public Integer deleteOneByKeyWord(T t) {
        return mapper.deleteOneByKeyWord(t);
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void deleteMulti(String[] ids) {
        mapper.deleteMulti(ids);
    }
}