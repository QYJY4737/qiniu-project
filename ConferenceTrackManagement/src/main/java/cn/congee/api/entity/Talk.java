package cn.congee.api.entity;

import cn.congee.api.constant.Constant;

import java.util.Date;

/**
 * 报告实体
 *
 * @Author: yang
 * @Date: 2020-12-02 20:41
 */
public class Talk extends BaseEntity {

    // 报告标题
    private String title;

    // 时长，单位：分钟
    private Integer duration;

    // 开始时间
    private Date startTime;

    // 结束时间
    private Date endTime;

    public Talk() {
    }

    public Talk(String title, Integer duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return title + " " + (duration == 5 ? Constant.LIGHTNING : duration + Constant.MIN);
    }

}
