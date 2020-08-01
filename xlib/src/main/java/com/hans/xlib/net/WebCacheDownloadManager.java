//package com.hans.xlib.net;
//
//import android.content.Context;
//import android.os.Build;
//import android.webkit.MimeTypeMap;
//import android.webkit.WebResourceResponse;
//
//
//import com.hans.xlib.utils.LogUtils;
//import com.hans.xlib.utils.MD5Util;
//import com.hans.xlib.utils.StringUtils;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import io.paperdb.Paper;
//import okhttp3.Headers;
//import okhttp3.ResponseBody;
//import retrofit2.Response;
//
//import static android.webkit.MimeTypeMap.getFileExtensionFromUrl;
//
///**
// * @date: 2019-12-04 16:44
// * @author: drq
// * @description WebCache缓存管理类
// * 通过拦截资源下载，存到本地，然后遇到重复请求时直接读取本地资源来节省流量
// */
//public class WebCacheDownloadManager {
//
//    /**
//     * 是否开启webcache模式。后期希望通过接口进行更改。
//     * 此模式默认关闭，可由后台控制进行开关。
//     */
//    private static boolean enableWebCache = false;
//
//    public static void setEnableWebCache(boolean enableWebCache) {
//        WebCacheDownloadManager.enableWebCache = enableWebCache;
//    }
//
//    public static boolean isEnableWebCache() {
//        return enableWebCache;
//    }
//
//    private static WebCacheDownloadManager sInstace;
//
//    /**
//     * 创建单例
//     */
//    public static WebCacheDownloadManager getInstance() {
//        if (sInstace == null) {
//            synchronized (WebCacheDownloadManager.class) {
//                sInstace = new WebCacheDownloadManager();
//            }
//        }
//        return sInstace;
//    }
//
//
//    public String getCacheFile(Context context, String url) {
//        String cachedPath = Paper.book(PaperName.PAPER_NAME_RES_CACHE).read(MD5Util.getMD5(url), "");
//        if (StringUtils.isInvalid(cachedPath)) {
//            return null;
//        }
//        return cachedPath;
//    }
//
//
//    public File writeResponseToDisk(String path, InputStream is) {
//        return writeFileFromIS(new File(path), is);
//    }
//
//
//    private int sBufferSize = 8192;
//
//    /**
//     * 将输入流写入文件
//     *
//     * @param file
//     * @param is
//     * @return
//     */
//    private File writeFileFromIS(File file, InputStream is) {
//        if (is == null) {
//            return null;
//        }
//        //创建文件
//        if (!file.exists()) {
//            File parentFile = file.getParentFile();
//            if (!parentFile.exists())
//                parentFile.mkdir();
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        OutputStream os = null;
//        try {
//            os = new BufferedOutputStream(new FileOutputStream(file));
//            byte[] data = new byte[sBufferSize];
//            int len;
//            while ((len = is.read(data, 0, sBufferSize)) != -1) {
//                os.write(data, 0, len);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            if (file.exists()) {
//                file.delete();
//            }
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                if (os != null) {
//                    os.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return file;
//    }
//
//    /**
//     * 将网络资源缓存，并返回WebResourceResponse给webview
//     * <p>
//     * 1、check mimetype
//     * 2、链接资源进行判断（要判断是否cdn刷新了资源。如果contenttype、contentlenght、lastModified、etag均未变化，则认为没有变化）
//     * 3、如果存在，直接取本地资源io流返回/如果不存在，使用链接同步下载资源，下载结束后返回。
//     * 4、api21以上，判断是否存在header。目前已知等有些web vr效果需要使用header。
//     *
//     * @param context
//     * @param url
//     * @return
//     */
//    public WebResourceResponse getWebResourceResponse(Context context, String url) {
//        if (!enableWebCache) {
//            return null;
//        }
//        if (context == null) {
//            return null;
//        }
//        String mimeTypeFromUrl = getMimeTypeFromUrl(url);
//        mimeTypeFromUrl = StringUtils.trimToEmpty(mimeTypeFromUrl);
//
//        String contentType = "";
//        String contentLength = "";
//        String lastModified = "";
//        String etag = "";
//
//        Map<String, String> headersMap = new HashMap<>();
//        try {
//            Response<ResponseBody> execute = WebRepository
//                    .getInstance()
//                    .getWebDownloadService()
//                    .download(url)
//                    .execute();
//
//            Headers headers = execute.headers();
//            contentType = StringUtils.trimToEmpty(headers.get("content-type"));
//            contentLength = StringUtils.trimToEmpty(execute.headers().get("content-length"));
//            lastModified = StringUtils.trimToEmpty(execute.headers().get("last-modified"));
//            etag = StringUtils.trimToEmpty(execute.headers().get("etag"));
//            if (StringUtils.isEmpty(mimeTypeFromUrl)) {
//                mimeTypeFromUrl = contentType;
//            }
//            if (!isMimeType(mimeTypeFromUrl)) {
//                mimeTypeFromUrl = "";
//            }
//            if (isMimeType(mimeTypeFromUrl) && isImageMimeType(mimeTypeFromUrl)) {
//                LogUtils.v("mimeTypeFromUrl:"+mimeTypeFromUrl);
//                File externalCacheDir = context.getExternalCacheDir();
//                if (externalCacheDir == null) {
//                    return null;
//                }
//                String path = context.getExternalCacheDir().getAbsolutePath() + File.separator + "webCacheFolder" + File.separator + MD5Util.getMD5(url + contentType + contentLength + lastModified + etag);
//                File now = new File(path);
//                if (!now.getParentFile().exists()) {
//                    now.getParentFile().mkdir();
//                }
//                WebResourceResponse webResourceResponse = null;
//                if (now.exists()) {
//                    LogUtils.v("now.exists");
//                } else {
//                    LogUtils.v("now.exists 不存在");
//                    ResponseBody body = execute.body();
//                    if (body == null) {
//                        return null;
//                    }
//                    InputStream in = body.byteStream();
//                    now = writeResponseToDisk(path, in);
//                }
//
//                if (now == null) {
//                    LogUtils.v("web缓存管理器，文件未下载成功，使用web本身加载");
//                    return null;
//                }
//                Paper.book(PaperName.PAPER_NAME_RES_CACHE).write(MD5Util.getMD5(url), path);
//                webResourceResponse = new WebResourceResponse(mimeTypeFromUrl, "", new FileInputStream(now));
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Map<String, List<String>> headerFields = headers.toMultimap();
//                    for (String key : headerFields.keySet()) {
//                        List<String> strings = headerFields.get(key);
//                        if (strings == null) {
//                            strings = new ArrayList<>();
//                        }
//                        StringBuilder sb = new StringBuilder();
//                        for (String value : strings) {
//                            sb.append(value);
//                            if (strings.indexOf(value) < strings.size() - 1) {
//                                sb.append(",");
//                            }
//                        }
//                        headersMap.put(key, sb.toString());
//                    }
//                    webResourceResponse.setResponseHeaders(headersMap);
//                }
//                return webResourceResponse;
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    private String getMimeTypeFromUrl(String url) {
//        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(getFileExtensionFromUrl(url));
//    }
//
//    private boolean isMimeType(String mimeType) {
//        return MimeTypeMap.getSingleton().hasMimeType(mimeType);
//    }
//
//    private boolean isImageMimeType(String mimeType) {
//        return mimeType.startsWith("image/");
//    }
//}
