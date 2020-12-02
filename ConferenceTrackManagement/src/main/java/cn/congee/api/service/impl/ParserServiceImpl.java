package cn.congee.api.service.impl;

import cn.congee.api.entity.Talk;
import cn.congee.api.enums.TalkTypeEnum;
import cn.congee.api.exception.FileException;
import cn.congee.api.service.ParserService;
import cn.congee.api.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 报告解析实现类
 *
 * @Author: yang
 * @Date: 2020-12-02 20:51
 */
public class ParserServiceImpl implements ParserService<Talk, List<String>> {

    private static final Logger log = LoggerFactory.getLogger(ParserServiceImpl.class);

    private static final Pattern INPUT_PATTERN = Pattern.compile("^(.+)\\s(\\d+)?\\s?((min)|(lightning))$");

    /**
     *
     * @param dataList 解析数据
     * @return
     */
    @Override
    public List<Talk> execute(List<String> dataList) {
        List<Talk> talks = new ArrayList<>();

        dataList.forEach(lineStr -> {
            Matcher match = INPUT_PATTERN.matcher(lineStr);

            if (!match.find()) {
                if (log.isErrorEnabled()) {
                    log.error("【{}】格式错误", lineStr);
                }
                throw new FileException("【" + lineStr + "】格式错误");
            }

            String title = match.group(1);
            String talkType = match.group(3);
            TalkTypeEnum talkTypeEnum;
            if (TalkTypeEnum.MINUTES.getName().equalsIgnoreCase(talkType)) {
                talkTypeEnum = TalkTypeEnum.MINUTES;
            } else {
                talkTypeEnum = TalkTypeEnum.LIGHTNING;
            }

            String durationStr = match.group(2);
            int duration = StringUtils.isEmpty(durationStr) ? 1 : Integer.parseInt(durationStr);
            duration = duration * talkTypeEnum.getValue();

            Talk talk = new Talk(title, duration);
            talks.add(talk);
        });

        return talks;
    }


}
