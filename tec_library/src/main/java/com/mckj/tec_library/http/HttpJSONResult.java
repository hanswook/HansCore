package com.mckj.tec_library.http;

public class HttpJSONResult<T> {


    /**
     * info : 100
     * msg :
     * obj : {"id":1,"userId":1,"all_Integral":200,"effective_Integral":200,"createTime":"2018-08-23 10:11:09.0","updateTime":"2018-08-23 10:11:13.0"}
     */

    private int info;
    private String msg;
    private T obj;

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
