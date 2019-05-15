package com.mckj.tec_library.http;

/**
 * @title: 网络请求异常
 * @author: yaolei
 * @date: 2018/10/9
 * @update: 2018/10/9
 * @describe: TODO
 **/
public class ApiException extends Exception {
    private String state;

    public ApiException(String state, String message) {
        super(message);
        this.state = state;
    }

    /**
     * 网络状态码
     *
     * @return 991 token 失效 303 业务异常 999 系统异常
     */
    public String getState() {
        return state;
    }
}
