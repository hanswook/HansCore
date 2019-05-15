package com.mckj.tec_library.http;

import android.content.Context;

import com.mckj.tec_library.utils.EmptyUtils;
import io.reactivex.functions.Function;

public class HttpResultFunc<D> implements Function<HttpJSONResult<D>, D> {
    private static final String NET_STATE_SUCCESS = "100";
    // token异常
    private static final String NET_STATE_ERROR_TOKEN_ERROR = "991";
    // 业务异常
    private static final String NET_STATE_ERROR_WORK_ERROR = "303";
    // 系统异常
    private static final String NET_STATE_ERROR_ERROR_ERROR = "999";
    //info 100
    private Context context;
    // TODO rxJava2.0之后不支持发送null数据，暂时用提供默认数据的方法处理
    private D defaultData;

    public HttpResultFunc(Context context, D defaultData) {
        this.context = EmptyUtils.checkNotNull(context).getApplicationContext();
        this.defaultData = defaultData;
    }

    @Override
    public D apply(HttpJSONResult<D> result) throws Exception {
        processState(String.valueOf(result.getInfo()), EmptyUtils.checkStringNull(result.getMsg()));
        return getData(EmptyUtils.isEmpty(result.getObj()) ? null : result.getObj());
    }

    private D getData(D remoteData) {
        if (remoteData == null) {
            return this.defaultData;
        } else {
            return remoteData;
        }
    }

    private void processState(String state, String msg) throws ApiException {
        if (!NET_STATE_SUCCESS.equals(state)) {
            // 网络请求失败
            if (NET_STATE_ERROR_TOKEN_ERROR.equals(state)) {
                // Token失效 通知APP 清理用户数据 并跳转 登录页面
            }
            // 抛出网络请求异常
            //            if (AppUtil.isDebug()) {
            //            }
            throw new ApiException(String.valueOf(state), getHttStateString(state, msg));

        }
    }

    /**
     * 处理错误码
     *
     * @param state
     * @return
     */
    private String getHttStateString(String state, String msg) {
        /*switch (state) {
            case NET_STATE_ERROR_TOKEN_ERROR:
                return "登录失效";
            case NET_STATE_ERROR_WORK_ERROR:
                return msg;
            case NET_STATE_ERROR_ERROR_ERROR:
                return "服务器异常";
            default:
                return "服务器未知异常";
        }*/
        return msg;
    }

    /*private void processContacts(List<CacheUser> contacts) {
        ContactFinder contactFinder = null;
        try {
            contactFinder = ContactFinder.getInstance(context);
        } catch (Throwable t) {
            LogUtils.w(t.getMessage());
        }
        if (contactFinder == null) {
            LogUtils.w("contact initial failed!!!");
            return;
        }
        ContactFinder.getInstance(context).cacheContacts(contacts, new ContactFinder.CacheResult() {
            @Override
            public void onSuccess() {
                LogUtils.d("cache contacts success");
            }

            @Override
            public void onFailure(Throwable throwable) {
                LogUtils.d("cache contacts failure:" + throwable.getMessage());
            }
        });
    }*/


}
