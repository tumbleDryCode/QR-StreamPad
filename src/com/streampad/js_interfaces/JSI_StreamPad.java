package com.streampad.js_interfaces;

import com.streampad.StreamPad;
import com.njfsoft_utils.dbutil.UtilDbRecord;
import java.util.ArrayList;


/**
 * Created by IntelliJ IDEA.
 * User: boss
 * Date: 16-07-2013
 * Time: 10:24
 * To change this template use File | Settings | File Templates.
 */
public class JSI_StreamPad extends StreamPad {
    final StreamPad streamPad;
    public JSI_StreamPad(StreamPad theStreamPad) {
        streamPad = theStreamPad;

    }

    /*
   * functions for setting and getting config values
    */
    public void setConfValString(String theString, String theVal) {
        streamPad.putConfValString(theString, theVal);
    }
    public void setConfValInt(String theString, String theVal) {
        streamPad.putConfValInt(theString, Integer.parseInt(theVal));
    }

    public String fetchConfValString(String theString) {
        String confStr = streamPad.getConfValString(theString);
        return confStr;
    }
    public String fetchConfValInt(String theString) {
        String confStr = String.valueOf(streamPad.getConfValInt(theString));
        return confStr;
    }



    public void setStreamPic(String fromType) {
        streamPad.doStreamPic(fromType);
    }

    public void getUrlSlider() {
      //   streamPad.doUrlSlider();
    }

    /* Sends configuration for the loaded page */
    public void setPageLoad() {
        streamPad.doPageLoad();
    }

    public void doPagePopUp(String theUrl, String theHTML) {
        // showPagePopUp(theUrl, "noQvalue");
        streamPad.setPagePopUp(theUrl, theHTML);
    }
     public void doPageReload() {
        streamPad.doPageReload();
    }   
 
 
    public void setQRscan() {
        streamPad.doQRscan();
    }





    public void doJSIRecUpdate(int recid, String cvals)  {
     streamPad.dbMSQLA.doUpdateRecord(recid, cvals);
	}



    public String getSQLdump() {
        // String strHtml = "<table width=\"100%\" style=\"border: 1px solid #EEE:\">";
		String strHtml = "";
        String strBgColor = "#CECECE";
        ArrayList<UtilDbRecord> lpDbRecord = null;
        String[] parseT = new String[]{"0"};
        lpDbRecord = streamPad.dbMSQLA.doGetDBRecordsArr("_id>?", parseT, "1000");
        for (UtilDbRecord list : lpDbRecord) {

            String strRecID = String.valueOf(list.getKeyRecID());
            String strRecType = String.valueOf(list.getKeyRecType());
            String strRecValA = list.getKeyRecValA();
            String strRecValB = list.getKeyRecValB();
            String strRecValC = list.getKeyRecValC();
            String strRecDateAdded = list.getKeyRecDateAdded();
            String strRecDateModified = list.getKeyRecDateModified();

            strHtml += "<tr><td>" + strRecID + "</td><td>" + strRecType + "</td><td>" + strRecValA + "</td><td>" + strRecValB + "</td><td>" + strRecValC + "</td><td>" + strRecDateAdded + "</td><td>" + strRecDateModified + "</td></tr>";


            boolean isBGColor = strBgColor.equals("#CECECE");
            strBgColor = isBGColor ? "#DFDFDF" : "#CECECE";

        }


        // strHtml += "</table>";

          System.out.println(strHtml);
        return strHtml;
    }


    public String getRecordDelete(String theRID) {
        String retStr = "noQvalue";
        retStr = streamPad.dbMSQLA.doStrRecDelete(theRID);
        return retStr;
    }


 

	public String getJSEpDBrecords(String strIsDistinct, String strTable, String strColumns, String qstring, String qargs, String strGrpBy, String strHaving, String strOrderBy, String strQlimit) {
				String[] parseT;
                        if (qargs.contains(":")) {
                           parseT = qargs.split(":");
				} else {
			         parseT = new String[]{qargs};
				}
      return streamPad.dbMSQLA.setJSDBrecords(false, null, null, qstring, parseT, strGrpBy, strHaving, strOrderBy, strQlimit).toString();
	}

	public void getAllRecordsDelete() {
      streamPad.dbMSQLA.doAllRecordsDelete();
	}

	public void doRecInsert(int rt, String va, String vb, String vc, String da) {
      streamPad.doRecInsert(rt, va, vb, vc, da);
	}

    public void getALocalUrl(String strLUrl) {
	streamPad.doLoadLclUrl(strLUrl);
    // return "file:///android_asset/" + strLUrl ;
    }

     public void setPageThread(int ptype, int pid, String purl) {
	streamPad.setPageThread(ptype, pid, purl);
    // return "file:///android_asset/" + strLUrl ;
    }

     public void getPageThread() {
	streamPad.getPageThread();
    // return "file:///android_asset/" + strLUrl ;
    }
     public void getFormatSMS(String strRKey, String strMsgArgs, String strPhoneNums, String strMsg) {
	streamPad.runFormatSMS(strRKey, strMsgArgs, strPhoneNums, strMsg);
    // return "file:///android_asset/" + strLUrl ;
    }

	public void getSmsDetails() {
	streamPad.runSmsDetails();
	}

	public void rinsert() {
	streamPad.rinsert();
	}


public void getPopPageThread() {
streamPad.getPopPageThread();
}
 


public void getOrderClean(String onum, String strOtitle, String strOttl, String strOtype) {
streamPad.setOrderClean(onum,strOtitle,strOttl,strOtype);
}


}