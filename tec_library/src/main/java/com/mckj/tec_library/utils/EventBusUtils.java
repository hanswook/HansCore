package com.mckj.tec_library.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Desc:
 * Creator ling
 * Date:   2018/2/7 0007 15:53
 */

public class EventBusUtils {

    public static boolean isRegistered(Object subscriber) {
        return EventBus.getDefault().isRegistered(subscriber);
    }

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unRegister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

}
