package cn.congee.api.test;

import cn.congee.api.comparator.TalkCompartor;
import cn.congee.api.entity.Talk;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Author: yang
 * @Date: 2020-12-02 20:59
 */
public class TalkCompartotTest {

    private List<Talk> talkList = new ArrayList<>();

    @Before
    public void generateTalks() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Talk talk = new Talk();
            talk.setTitle("tile " + (i + 1));
            talk.setDuration(random.nextInt(60));

            talkList.add(talk);
        }
    }

    /**
     * 测试talkList的降序排序
     */
    @Test
    public void talkSort() {
        Collections.sort(talkList, new TalkCompartor());
        talkList.forEach(talk -> System.out.println(talk));
    }

}
