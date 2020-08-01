package com.hans.xlib.net;

/**
 * @date: 2019-12-26 17:29
 * @author: drq
 * @description null
 */
public interface DownloadCallback {
    void success(String path);

    void failed();
}
