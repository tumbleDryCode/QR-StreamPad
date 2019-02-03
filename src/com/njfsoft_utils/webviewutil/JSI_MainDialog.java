package com.njfsoft_utils.webviewutil;

import com.njfsoft_utils.webviewutil.UtilWebDialog;

/**
 * Created by IntelliJ IDEA.
 * User: boss
 * Date: 09-04-2014
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public class JSI_MainDialog  {
    UtilWebDialog tepMainDialog;
    public JSI_MainDialog(UtilWebDialog theUtilWebDialog) {
        tepMainDialog = theUtilWebDialog;
        
    }
    public void getEpMDcom(String theInt, String strArgs) {
        tepMainDialog.setEpMDcom(Integer.parseInt(theInt), strArgs);
     }   
}
