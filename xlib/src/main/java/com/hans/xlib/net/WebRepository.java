package com.hans.xlib.net;

/**
 * @date: 2019-12-06 10:29
 * @author: drq
 * @description null
 */
public class WebRepository {

    private WebDownloadService webDownloadService;

    private static WebRepository sInstace;

    /**
     * 创建单例
     */
    public static WebRepository getInstance() {
        if (sInstace == null) {
            synchronized (WebRepository.class) {
                sInstace = new WebRepository();
            }
        }
        return sInstace;
    }

    private WebRepository() {
        init();
    }

    public WebDownloadService getWebDownloadService() {
        return webDownloadService;
    }

    private void init() {
        webDownloadService = RetrofitHelper.getInstance().create(WebDownloadService.class);
    }
}
