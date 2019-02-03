package com.njfsoft_utils.widgetutil;

/**
 * Created by IntelliJ IDEA.
 * User: boss
 * Date: 27-04-2014
 * Time: 0:55
 * To change this template use File | Settings | File Templates.
 */

public interface DWETListener {


    int DWET_SET_TOGGLE_VIEW = 10;
    int DWET_GET_CONTACTS = 11;
    int DWET_GET_HELP = 12;
    int DWET_GET_TXT_CLR = 13;
    int DWET_GET_TXT_SIZE = 14;
    int DWET_RELOAD_MSGS = 15;
    int DWET_DO_MSG_SEND = 16;
    int DWET_CHECK_SCENE_KEYS = 17;

    public void onComplete(int iAct, String s);
}
