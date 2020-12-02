package cn.congee.api.common;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: yang
 * @Date: 2020-12-02 18:17
 */
@Data
@ToString
public class BaseResult<T> {

    /**
     * 响应中的数据
     */
    private T data;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应业务状态
     */
    private Integer code;

    public BaseResult() {
    }

    public BaseResult(T data) {
        this.data = data;
        msg = "success";
        code = 200;
    }

    public BaseResult(Integer code) {
        String msg;
        switch (code){
            case 200:
                msg = "success";
                break;
            case 400:
                msg="bad request";
                break;
            case 401:
                msg="401 Unauthorized";
                break;
            case 403:
                msg="403 Forbidden";
                break;
            case 404:
                msg = "404 Not Found";
                break;
            case 500:
                msg=" Internal Server Error ";
                break;
            default:
                msg = "fail";
                break;
        }
        this.msg = msg;
        this.code = code;
    }
}