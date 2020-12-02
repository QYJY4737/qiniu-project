package cn.congee.api.service;

import java.util.List;

/**
 * 解析器
 *
 * @Author: yang
 * @Date: 2020-12-02 20:50
 */
public interface ParserService<T1,T2> {

    /**
     * 解析执行
     * @param data 解析数据
     * @return
     */
    List<T1> execute(T2 data);

}
