package com.njfsoft_utils.smsutil;

/**
 * Created by IntelliJ IDEA.
 * User: boss
 * Date: 27-04-2014
 * Time: 0:55
 * To change this template use File | Settings | File Templates.
 */
import com.njfsoft_utils.dbutil.UtilDbRecord;

public interface ServiceListener {
    public void onComplete(String s, UtilDbRecord fnlEPpnum);
}
