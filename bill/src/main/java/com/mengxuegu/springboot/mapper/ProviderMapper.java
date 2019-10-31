package com.mengxuegu.springboot.mapper;

import com.mengxuegu.springboot.entities.Provider;


import java.util.List;

/**
 * @Auther: 梦学谷
 */
//@Mapper 或 @MapperScan("com.mengxuegu.springboot.mapper")
public interface ProviderMapper {

    /**
     * 查询指定名字的所有provider或者全部的provider
     * @param provider 里面可能会有匹配的名字
     * @return 装有provider的list对象
     */
    List<Provider> getProviders(Provider provider);

    /**
     * 根据id查询一个provider
     * @param pid provider的id
     * @return  返回一个provider
     */
    Provider getProviderByPid(Integer pid);

    /**
     * 增添一个provider
     * @param provider  要增添的provider
     * @return  返回增添的结果
     */
    int addProvider(Provider provider);

    /**
     * 根据provider的id删除一个provider
     * @param pid   provider的id
     * @return  返回删除的结果
     */
    int deleteProviderByPid(Integer pid);

    /**
     * 更新provider
     * @param provider  要更新的provider
     * @return  返回更新的结果
     */
    int updateProvider(Provider provider);

}
