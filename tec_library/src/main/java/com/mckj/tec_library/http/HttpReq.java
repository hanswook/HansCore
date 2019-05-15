package com.mckj.tec_library.http;




public class HttpReq<T extends Object> {
    private String version ;
    private String signatureMethod;
    private String timestamp;
    private String params;
    private String signature;
    private String format;
    private String appKey;
    private String token;

   /* public HttpReq(Object jsonObject) {
        this.version = AppUtil.getVersionName(BaseApplication.getInstance().getContext());
        this.signatureMethod = "md5";
        this.timestamp = System.currentTimeMillis() + "";
        this.params = new Gson().toJson(jsonObject);
        this.format = "json";
        //后台给的appkey
        this.appKey = "cee736bb992d42e59fd9c87f348b22f2";
        this.token = EmptyUtils.isEmpty(AppState.getInstance().getToken()) ? "" : AppState.getInstance().getToken();
        StringBuilder sb = new StringBuilder();
        sb.append(this.params);
        sb.append("appKey=");
        sb.append(this.appKey);
        sb.append("format=");
        sb.append(this.format);
        sb.append("timestamp=");
        sb.append(this.timestamp);
        sb.append("version=");
        sb.append(this.version);
        sb.append("signatureMethod=");
        sb.append(this.signatureMethod);
        sb.append(AppState.key);
        this.signature = MD5Utils.md5(sb.toString());
    }

    public HttpReq(Map jsonObject) {
        this.version = AppUtil.getVersionName(BaseApplication.getInstance().getContext());
        this.signatureMethod = "md5";
        this.timestamp = System.currentTimeMillis() + "";
        this.params = new Gson().toJson(jsonObject);
        this.format = "json";
        //后台给的appkey
        this.appKey = "cee736bb992d42e59fd9c87f348b22f2";
        this.token = EmptyUtils.isEmpty(AppState.getInstance().getToken()) ? "" : AppState.getInstance().getToken();
        StringBuilder sb = new StringBuilder();
        sb.append(this.params);
        sb.append("appKey=");
        sb.append(this.appKey);
        sb.append("format=");
        sb.append(this.format);
        sb.append("timestamp=");
        sb.append(this.timestamp);
        sb.append("version=");
        sb.append(this.version);
        sb.append("signatureMethod=");
        sb.append(this.signatureMethod);
        sb.append(AppState.key);
        this.signature = MD5Utils.md5(sb.toString());
    }*/

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSignatureMethod() {
        return signatureMethod;
    }

    public void setSignatureMethod(String signatureMethod) {
        this.signatureMethod = signatureMethod;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
