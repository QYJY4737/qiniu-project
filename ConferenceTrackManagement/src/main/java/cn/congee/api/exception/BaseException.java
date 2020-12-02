package cn.congee.api.exception;

/**
 * 异常基类
 *
 * @Author: yang
 * @Date: 2020-12-02 20:46
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
