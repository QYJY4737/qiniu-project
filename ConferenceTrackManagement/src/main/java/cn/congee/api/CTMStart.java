package cn.congee.api;

import cn.congee.api.constant.Constant;
import cn.congee.api.entity.Conference;
import cn.congee.api.service.ManagerService;
import cn.congee.api.service.impl.ManagerServiceImpl;

/**
 * ctm入口
 *
 * @Author: yang
 * @Date: 2020-12-02 20:56
 */
public class CTMStart {

    public static void main (String[] agrs) {
        ManagerService manager = new ManagerServiceImpl();
        Conference conference = manager.readData(Constant.INPUT_FILE_NAME, true);

        int talkIndex = 0;
        for (int trackNo = 0;trackNo< conference.getTrackCount(); trackNo++) {
            talkIndex = manager.schedule(trackNo, conference, talkIndex);
        }

        manager.displaySchedule();
    }

}
