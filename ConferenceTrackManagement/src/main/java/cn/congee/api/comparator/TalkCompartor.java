package cn.congee.api.comparator;

import cn.congee.api.entity.Talk;

import java.util.Comparator;

/**
 * Talk比较器
 *
 * @Author: yang
 * @Date: 2020-12-02 20:44
 */
public class TalkCompartor implements Comparator<Talk> {

    /**
     * 降序排序
     *
     * @param talk1
     * @param talk2
     * @return
     */
    @Override
    public int compare(Talk talk1, Talk talk2) {
        if (talk1.getDuration() < talk2.getDuration()) {
            return 1;
        } else {
            return -1;
        }
    }

}
