package com.njfsoft_utils.smsutil;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.telephony.SmsManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.njfsoft_utils.core.Base64Android;
import com.njfsoft_utils.core.StringUtils;

import com.njfsoft_utils.dbutil.UtilDbRecord;
import com.njfsoft_utils.dbutil.UtilSQLAdapter;



/**
 * Created by IntelliJ IDEA.
 * User: boss
 * Date: 06-04-2014
 * Time: 17:13
 * To change this template use File | Settings | File Templates.
 */
public class SmsUtils {
    ContentResolver cr;
    UtilSQLAdapter dbSQLA;
    private Context c;

    // set the KEY_REC_TYPES
    public int RTYPE_FORM_CREATED = 5;
    public int RTYPE_FORM_RECEIVED = 6;
    public int RTYPE_FORM_ANSWERED = 7;
    public int RTYPE_FORM_SENT = 8;
    public int RTYPE_FORM_ANSWER_RECEIVED = 9;
    public int RTYPE_FORMFIELD = 10;
    public int RTYPE_CANVAS_CREATED = 20;
    public int RTYPE_CANVAS_RECEIVED = 21;
    public int RTYPE_CANVAS_ANSWERED = 22;
    public int RTYPE_CANVAS_SENT = 23;
    public int RTYPE_CANVAS_ANSWER_RECEIVED = 24;
    public int RTYPE_CANVASFIELD = 29;
    public int RTYPE_CHECKERS = 30;
    public int RTYPE_CHALL_CREATED = 40;
    public int RTYPE_CHALL_RECEIVED = 41;
    public int RTYPE_CHALL_ANSWERED = 42;
    public int RTYPE_CHALL_SENT = 43;
    public int RTYPE_CHALL_ANSWER_RECEIVED = 44;
    public int RTYPE_CHALLFIELD = 49;
    int intCurrHID = 0;
    BroadcastReceiver deliverReceiver;
    BroadcastReceiver sentReceiver;
    IntentFilter sentPIFilter;
    IntentFilter delivPIFilter;
    String currMsgString = "";
    String currUniqueID;
    ServiceListener tmpServiceListener;
    int mMessageTotalParts;
    int mMessageSentCount;
    ArrayList<UtilDbRecord> arrMsgNums = new ArrayList<UtilDbRecord>();
    ProgressDialog progDialog;

    public SmsUtils(Context context, ServiceListener serviceListener) {
        this.c = context;
        cr = this.c.getContentResolver();
        tmpServiceListener = serviceListener;
        dbSQLA = new UtilSQLAdapter(this.c);
        progDialog = new ProgressDialog(this.c);
        progDialog.hide();
        progDialog.setMessage("Please wait");

    }


    /* SMS functions

    */

    public void sendSMS(final UtilDbRecord afnlSCrec) {
        int intMsgRType = afnlSCrec.getKeyRecType();
        String strMsgValA = afnlSCrec.getKeyRecValA();
        String strMsgValB = afnlSCrec.getKeyRecValB();
        String strMsgValC = afnlSCrec.getKeyRecValC();
        String strMsgDModfd = afnlSCrec.getKeyRecDateModified();

        System.out.println("flow:doMsgTest: Sending msg to " + strMsgValC + ":" + strMsgDModfd);
        System.out.println("flow:doMsgTest: Message is " + strMsgValB);
        // processSMS(intMsgRType, strMsgValB, strMsgDModfd + ";1234;Charlie");
        System.out.println("flow:doMsgTest: db record " + strMsgValA);
        // sendSMS(strMsgValC, strMsgValB);

        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(c, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(c, 0, new Intent(DELIVERED), 0);

        sentPIFilter = new IntentFilter(SENT);
        delivPIFilter = new IntentFilter(DELIVERED);
        deliverReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent arg1) {
//                System.out.println("smsResCode: " + getResultCode()   + " :: " + deliverReceiver.getResultExtras(false).toString());

                switch (getResultCode()) {

                    case Activity.RESULT_OK:
                        tmpServiceListener.onComplete("sent", afnlSCrec);
                        //   dbSQLA.doRecUpdateOrAdd(5, String.valueOf(currStoryID), tmpMessage, tmpUID);

                        // Toast.makeText(c, "SMS delivered",  Toast.LENGTH_SHORT).show();

                        break;
                    case Activity.RESULT_FIRST_USER:
                        tmpServiceListener.onComplete("failed", afnlSCrec);
                        // dbSQLA.doRecUpdateOrAdd(5, currUniqueID, tmpMessage, tmpUID);
                        //   Toast.makeText(c, "SMS Error",  Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        tmpServiceListener.onComplete("canceled", afnlSCrec);
                        //    Toast.makeText(c, "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
                try {
                    c.unregisterReceiver(sentReceiver);
                    c.unregisterReceiver(deliverReceiver);
                } catch (Exception ee) {
                    System.out.println("E:sendNextMessage:unregisterReceiver: " + ee.toString());
                }

//                System.out.println("dev:SmsUtils:deliverReceiver: " + getResultCode()   + " :: " + deliverReceiver.getResultExtras(false).toString());
                mMessageSentCount++;
                sendNextMessage();
            }

        };
        sentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        // Toast.makeText(c, "SMS sent", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // Toast.makeText(c, "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // Toast.makeText(c, "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // Toast.makeText(c, "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // Toast.makeText(c, "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }

                System.out.println("dev:SmsUtils:sentReceiver: " + getResultCode() + " :: " + sentReceiver.getResultExtras(false).toString());

            }
        };


        //---when the SMS has been sent---

        SmsManager sms = SmsManager.getDefault();
        // sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
        ArrayList<String> parts = sms.divideMessage(strMsgValB);
        int messageCount = parts.size();
        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>(messageCount);
        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>(messageCount);
        for (int j = 0; j < messageCount; j++) {
            sentIntents.add(PendingIntent.getBroadcast(c, 0,
                    new Intent(DELIVERED),
                    0));
        }

        try {
            c.registerReceiver(sentReceiver, sentPIFilter);
            c.registerReceiver(deliverReceiver, delivPIFilter);
        } catch (Exception ee) {
            System.out.println("registerReceiver ee: " + ee.toString());
        }

        //  progDialog.setMessage("Sending to: " + strMsgValC);
        sms.sendMultipartTextMessage(strMsgValC, null, parts, sentIntents, deliveryIntents);
    }


    public String getContactDisplayNameByNumber(String number) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String name = "?";

        ContentResolver contentResolver = c.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[]{BaseColumns._ID,
                ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name;
    }

    public void getSMSlist() {

        Cursor cursor = cr.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        cursor.moveToFirst();
        int msgCount = 0;
        do {
            // for(int idx=0;idx<5;idx++)
            for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                System.out.println("MDD: " + cursor.getColumnName(idx) + ":" + cursor.getString(idx));

                if ((cursor.getColumnName(idx).equals("body")) && cursor.getString(idx).startsWith("g://")) {
                    if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains("1")) {
                        String theFAdd = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                        String strTheAAdrs = theFAdd.substring(theFAdd.length() - 9, theFAdd.length()).trim();
                        System.out.println("theAddrss: " + strTheAAdrs);
                        System.out.println("theName: " + getContactDisplayNameByNumber(strTheAAdrs));
                    }

                }

            }
            cursor.moveToNext();
            msgCount++;
        } while (msgCount < 40);  // check just the last 20 messages

        try {
        } catch (Exception e) {
            System.out.println("getSMSlist: " + e);
        }
    }


    public UtilDbRecord getSMSDBrec(String strRKey, String strMsgArgs, String pNum, String pName, String pMsg) {
        UtilDbRecord retEpdbRec = new UtilDbRecord();
        if (pMsg.length() == 0) {
            pMsg = "*";
        }
        ArrayList<UtilDbRecord> lpDbRecord;

        int intRType = 20;
        String strEncdMsg = "noQvalue";
        String getChallQ = dbSQLA.KEY_REC_ID + "=?";
        String[] parseT = new String[]{strRKey};
        String strHtml = "";
        //  String str = strPhoneNums.replaceAll("\\D+", ""); // strip phoneNum of Non Digits

        String strDrecID = "noQvalue";
        String strDrecType = "noQvalue";
        String strDrecValA = "noQvalue";
        String strDrecValB = "noQvalue";
        String strDrecValC = "noQvalue";
        String strDdAdded = "noQvalue";
        String strDdModfd = "noQvalue";


        if (strRKey.equalsIgnoreCase("canvas")) {
            System.out.println("flow:setFormatSMS:isCanvas " + strRKey + " :: " + strMsgArgs + " :: " + pNum + " :: " + pName + " :: " + pMsg);
            intRType = 20;
            //   return;
        } else {

            lpDbRecord = dbSQLA.getUtilDbRecords(getChallQ, parseT, "1");
            if (lpDbRecord.size() <= 0) {
                System.out.println("db rec count: IS NULL: " + strRKey);
            } else {
                for (UtilDbRecord list : lpDbRecord) {
                    strDrecID = String.valueOf(list.getKeyRecID());
                    strDrecType = String.valueOf(list.getKeyRecType());
                    intRType = Integer.parseInt(strDrecType);
                    strDrecValA = list.getKeyRecValA();
                    strDrecValB = list.getKeyRecValB();
                    strDrecValC = list.getKeyRecValC();
                    strDdAdded = list.getKeyRecDateAdded();
                    strDdModfd = list.getKeyRecDateModified();

                    //  strHtml  += "db rec: " + strRecID + " :: "  + strRecType + " :: " + strRecValA + " :: " + strRecValB +  " :: " + strRecValC +  " :: " + strRecDateAdded +  " :: " + strRecDateModified + "<br>";
                    System.out.println("flow:getSMSDBrec: " + strDrecID + " :: " + strDrecType + " :: " + strDrecValA + " :: " + strDrecValB + " :: " + strDrecValC + " :: " + strDdAdded + " :: " + strDdModfd);
                }

            }

        }
        retEpdbRec.setKeyRecType(intRType);
        switch (intRType) {
            case 5:
                retEpdbRec.setKeyRecValA("8|" + strRKey + "|" + strDrecValB + "|" + pNum + "|" + pName + "::" + pMsg);

                String strGroups = "";
                String strFRec = "6" + "|" + strRKey + "|" + strDrecValA + "|";
                getChallQ = "rec_type=? and rec_val_a=?";
                parseT = new String[]{"10", strRKey};

                strHtml = "";
                lpDbRecord = null;
                lpDbRecord = dbSQLA.getUtilDbRecords(getChallQ, parseT, "noQvalue");
                if (lpDbRecord.size() <= 0) {
                    System.out.println("db rec count: IS NULL: " + strRKey);
                } else {
                    for (UtilDbRecord list : lpDbRecord) {
                        String strRecID = String.valueOf(list.getKeyRecID());
                        String strRecType = String.valueOf(list.getKeyRecType());

                        String strRecValA = list.getKeyRecValA();
                        String strRecValB = list.getKeyRecValB();
                        String strRecValC = list.getKeyRecValC();
                        String strRecDateAdded = list.getKeyRecDateAdded();
                        String strRecDateModified = list.getKeyRecDateModified();
                        strGroups += strRecValB + ";";
                        //  strHtml  += "db rec: " + strRecID + " :: "  + strRecType + " :: " + strRecValA + " :: " + strRecValB +  " :: " + strRecValC +  " :: " + strRecDateAdded +  " :: " + strRecDateModified + "<br>";
                    }

                }

                String strClnGrps = strGroups.substring(0, strGroups.length() - 1);
                strFRec += strClnGrps + "|" + pMsg;
                String strBase64 = null;
                try {
                    byte[] data = strFRec.getBytes("ISO-8859-1");
                    strBase64 = Base64Android.encodeToString(data, Base64Android.NO_WRAP);
                    System.out.println("b64: " + strBase64);
                    byte[] ddata = Base64Android.decode(strBase64, Base64Android.NO_WRAP);
                    String text = new String(ddata, "ISO-8859-1");
                    System.out.println("b64decoded: " + text);
                    retEpdbRec.setKeyRecValB("g://s.ms/form/" + strBase64);
                    retEpdbRec.setKeyRecValC(pNum);
                    retEpdbRec.setKeyRecDateModified(pName);

                } catch (Exception e) {
                    System.out.println("UnsupportedEncodingException: " + e.toString());  //To change body of catch statement use File | Settings | File Templates.
                }


                break;
            case 6:
                retEpdbRec.setKeyRecValA("7|" + strDrecValA + "|" + strDrecValB + "|" + strMsgArgs + "|" + pNum + ":" + pName + ":" + pMsg);
                strFRec = "9|" + strDrecValA + "|" + strDrecValB + "|" + strMsgArgs + "|" + pMsg;
                try {
                    byte[] data = strFRec.getBytes("ISO-8859-1");
                    strBase64 = Base64Android.encodeToString(data, Base64Android.NO_WRAP);
                    System.out.println("b64: " + strBase64);
                    byte[] ddata = Base64Android.decode(strBase64, Base64Android.NO_WRAP);
                    String text = new String(ddata, "ISO-8859-1");
                    System.out.println("b64decoded: " + text);
                    retEpdbRec.setKeyRecValB("g://s.ms/form/" + strBase64);
                    retEpdbRec.setKeyRecValC(pNum);
                    retEpdbRec.setKeyRecDateModified(pName);
                } catch (Exception e) {
                    System.out.println("UnsupportedEncodingException: " + e.toString());  //To change body of catch statement use File | Settings | File Templates.
                }
                break;

            case 20:  // canvas created and sent

			dbSQLA.close();
					dbSQLA.openToRead();
		dbSQLA.openToWrite();
                int intCnvsRadd = dbSQLA.doIntRecordAdd(intRType, strMsgArgs, pNum, pName, "noQvalue");
			dbSQLA.close();
                retEpdbRec.setKeyRecValA("23|" + intCnvsRadd + "|" + strDrecValB + "|" + strMsgArgs + "|" + pNum + ":" + pName + ":" + pMsg);

                //  strFRec = "21|" + intCnvsRadd + "|";

                strFRec = "21|" + strMsgArgs + "|" + intCnvsRadd + "|" + "Art Message" + "|" + intCnvsRadd;
                try {
                    byte[] data = strFRec.getBytes("ISO-8859-1");
                    strBase64 = Base64Android.encodeToString(data, Base64Android.NO_WRAP);
                    System.out.println("b64: " + strBase64);
                    byte[] ddata = Base64Android.decode(strBase64, Base64Android.NO_WRAP);
                    String text = new String(ddata, "ISO-8859-1");
                    System.out.println("b64decoded: " + text);


        String strRVs = "g://s.ms/art/" + strBase64;

			/* fix to send as normal text */

        if(strMsgArgs.indexOf(";") != -1) {
            StringTokenizer stringtokenizer = new StringTokenizer(strMsgArgs, ";");
            int imt = stringtokenizer.countTokens();
		String[] mTokes = new String[imt];
            for (int j = 0; j < imt; j++) {
                mTokes[j] = stringtokenizer.nextToken();
                // arrMsgNums[j] = as[j];
                System.out.println("mTokes: " + mTokes[j]);
            }
	      if(mTokes[0].equals("17")) {  // just a simple text message
        	if(strMsgArgs.indexOf(":") != -1) {
		String cleantxt = strMsgArgs.substring(strMsgArgs.indexOf(":") + 1, strMsgArgs.length());
		strRVs = cleantxt;
		}
		}
        } 


                    System.out.println("strRVs: " + strRVs);
                    
                    retEpdbRec.setKeyRecValB(strRVs);
                    //retEpdbRec.setKeyRecValB("g://s.ms/art/" + strFRec);
                    retEpdbRec.setKeyRecValC(pNum);
                    retEpdbRec.setKeyRecDateModified(pName);
                    tmpServiceListener.onComplete("Sending...", retEpdbRec);
                } catch (Exception e) {
                    System.out.println("UnsupportedEncodingException: " + e.toString());  //To change body of catch statement use File | Settings | File Templates.
                }
                break;

            case 40:
                retEpdbRec.setKeyRecValA("43|" + strRKey + "|" + strDrecValB + "|" + pNum + "|" + pName + "::" + pMsg);

                strGroups = "";
                strFRec = "41" + "|" + strRKey + "|" + strDrecValA + "|";
                getChallQ = "rec_type=? and rec_val_a=?";
                parseT = new String[]{"49", strRKey};

                strHtml = "";
                lpDbRecord = null;
                lpDbRecord = dbSQLA.getUtilDbRecords(getChallQ, parseT, "noQvalue");
                if (lpDbRecord.size() <= 0) {
                    System.out.println("db rec count: IS NULL: " + strRKey);
                } else {
                    for (UtilDbRecord list : lpDbRecord) {
                        String strRecID = String.valueOf(list.getKeyRecID());
                        String strRecType = String.valueOf(list.getKeyRecType());

                        String strRecValA = list.getKeyRecValA();
                        String strRecValB = list.getKeyRecValB();
                        String strRecValC = list.getKeyRecValC();
                        String strRecDateAdded = list.getKeyRecDateAdded();
                        String strRecDateModified = list.getKeyRecDateModified();
                        strGroups += strRecValB + ":" + strRecValC + ";";
                        //  strHtml  += "db rec: " + strRecID + " :: "  + strRecType + " :: " + strRecValA + " :: " + strRecValB +  " :: " + strRecValC +  " :: " + strRecDateAdded +  " :: " + strRecDateModified + "<br>";
                    }

                }

                strClnGrps = strGroups.substring(0, strGroups.length() - 1);
                strFRec += strClnGrps + "|" + pMsg;
                strBase64 = null;
                try {
                    byte[] data = strFRec.getBytes("ISO-8859-1");
                    strBase64 = Base64Android.encodeToString(data, Base64Android.NO_WRAP);
                    System.out.println("b64: " + strBase64);
                    byte[] ddata = Base64Android.decode(strBase64, Base64Android.NO_WRAP);
                    String text = new String(ddata, "ISO-8859-1");
                    System.out.println("b64decoded: " + text);
                    retEpdbRec.setKeyRecValB("g://s.ms/chlg/" + strBase64);
                    retEpdbRec.setKeyRecValC(pNum);
                    retEpdbRec.setKeyRecDateModified(pName);

                } catch (Exception e) {
                    System.out.println("UnsupportedEncodingException: " + e.toString());  //To change body of catch statement use File | Settings | File Templates.
                }


                break;


        }

        System.out.println("flow:getSMSDBrec[" + intRType + "]: " + retEpdbRec.getKeyRecType() + " :: " + retEpdbRec.getKeyRecValA() + " :: " + retEpdbRec.getKeyRecValB() + " :: " + retEpdbRec.getKeyRecValC() + " :: " + retEpdbRec.getKeyRecDateModified());
        return retEpdbRec;
    }





    public void setFormatSMS(String strRKey, String strMsgArgs, String strPhoneNums, String strMsg) {

        int intRType = 0;
        String strEncdMsg = "";
        String strDrecID = "noQvalue";
        String strDrecType = "noQvalue";
        String strDrecValA = "noQvalue";
        String strDrecValB = "noQvalue";
        String strDrecValC = "noQvalue";
        String strDdAdded = "noQvalue";
        String strDdModfd = "noQvalue";
        UtilDbRecord epDBRsendInfo = new UtilDbRecord();
        UtilDbRecord epDBRregInfo = new UtilDbRecord();
        arrMsgNums.clear();
        String fullNums = strPhoneNums.trim();
        if (fullNums.endsWith(",")) {
            String trimFullNumes = fullNums.substring(0, fullNums.length() - 1);
            fullNums = trimFullNumes;

        }
        String[] as = new String[0];
        if (fullNums.indexOf(",") != -1) {
            String[] pNums = StringUtils.readmessTokens(fullNums, ",");
            StringTokenizer stringtokenizer = new StringTokenizer(fullNums, ",");
            int i = stringtokenizer.countTokens();
            as = new String[i];
            for (int j = 0; j < i; j++) {
                as[j] = stringtokenizer.nextToken();
                // arrMsgNums[j] = as[j];
                System.out.println("tokeSMS: " + as[j]);
            }
        } else {
            as = new String[]{fullNums};
            // arrMsgNums = new String[](fullNums);
        }
        System.out.println("arrSMS: " + as[0] + " :: " + as.length);

        for (int j = 0; j < as.length; j++) {
            UtilDbRecord epDbRecord = new UtilDbRecord();
            String strPname = "noQvalue";
            String strPnum = "noQvalue";
            if (as[j].contains("<")) {
                String[] asStr = as[j].split("<");
                strPname = asStr[0].trim();
                strPnum = asStr[1].replace(">", "");
                System.out.println("arrSMSvals: " + strPname + " :: " + strPnum);
            } else {

                strPnum = as[j].replace(">", "");
                strPname = "noQvalue";

            }

            System.out.println("flow:setFormatSMS: " + strRKey + " :: " + strMsgArgs + " :: " + strPnum + " :: " + strMsg);
            arrMsgNums.add(getSMSDBrec(strRKey, strMsgArgs, strPnum, strPname, strMsg));
        }
        // progDialog.setMessage("Sending....");
        // progDialog.show();
        startSendMessages();


    }









    public void old_TODELETE_SetFormatSMS(String strRKey, String strMsgArgs, String strPhoneNums, String strMsg) {

        int intRType = 0;
        String strEncdMsg = "";
        String strDrecID = "noQvalue";
        String strDrecType = "noQvalue";
        String strDrecValA = "noQvalue";
        String strDrecValB = "noQvalue";
        String strDrecValC = "noQvalue";
        String strDdAdded = "noQvalue";
        String strDdModfd = "noQvalue";
        UtilDbRecord epDBRsendInfo = new UtilDbRecord();
        UtilDbRecord epDBRregInfo = new UtilDbRecord();
        arrMsgNums.clear();
        String fullNums = strPhoneNums.trim();
        if (fullNums.endsWith(",")) {
            String trimFullNumes = fullNums.substring(0, fullNums.length() - 1);
            fullNums = trimFullNumes;

        }
        String[] as = new String[0];
        if (fullNums.indexOf(",") != -1) {
            String[] pNums = StringUtils.readmessTokens(fullNums, ",");
            StringTokenizer stringtokenizer = new StringTokenizer(fullNums, ",");
            int i = stringtokenizer.countTokens();
            as = new String[i];
            for (int j = 0; j < i; j++) {
                as[j] = stringtokenizer.nextToken();
                // arrMsgNums[j] = as[j];
                System.out.println("tokeSMS: " + as[j]);
            }
        } else {
            as = new String[]{fullNums};
            // arrMsgNums = new String[](fullNums);
        }
        System.out.println("arrSMS: " + as[0] + " :: " + as.length);

        for (int j = 0; j < as.length; j++) {
            UtilDbRecord epDbRecord = new UtilDbRecord();
            String strPname = "noQvalue";
            String strPnum = "noQvalue";
            if (as[j].contains("<")) {
                String[] asStr = as[j].split("<");
                strPname = asStr[0].trim();
                strPnum = asStr[1].replaceAll("\\D+", "");
                System.out.println("arrSMSvals: " + strPname + " :: " + strPnum);
            } else {

                strPnum = as[j].replaceAll("\\D+", "");
                strPname = "noQvalue";

            }

            System.out.println("flow:setFormatSMS: " + strRKey + " :: " + strMsgArgs + " :: " + strPnum + " :: " + strMsg);
            arrMsgNums.add(getSMSDBrec(strRKey, strMsgArgs, strPnum, strPname, strMsg));
        }
        // progDialog.setMessage("Sending....");
        // progDialog.show();
        startSendMessages();


    }

    public boolean thereAreSmsToSend() {
        return mMessageSentCount < arrMsgNums.size();
    }

    public void sendNextMessage() {
        if (thereAreSmsToSend()) {
            sendSMS(arrMsgNums.get(mMessageSentCount));
            //   doMsgTest(arrMsgNums.get(mMessageSentCount));
        } else {
            try {
                // tmpServiceListener.onComplete("hahaha");
                //    progDialog.setMessage("All SMS have been sent");

                //  Toast.makeText(c, "All SMS have been sent", Toast.LENGTH_SHORT);
            } catch (Exception e) {
                System.out.println("doHideEPMainDlg" + e.toString());
            }


            // progDialog.hide();
        }
    }

    private void startSendMessages() {
        mMessageSentCount = 0;
        sendNextMessage();
    }

    public void doMsgTest(UtilDbRecord pnum) {
        int intMsgRType = pnum.getKeyRecType();
        String strMsgValA = pnum.getKeyRecValA();
        String strMsgValB = pnum.getKeyRecValB();
        String strMsgValC = pnum.getKeyRecValC();
        String strMsgDModfd = pnum.getKeyRecDateModified();

        System.out.println("flow:doMsgTest: Sending msg to " + strMsgValC + ":" + strMsgDModfd);
        System.out.println("flow:doMsgTest: Message is " + strMsgValB);
        // processSMS(intMsgRType, strMsgValB, strMsgDModfd + ";1234;Charlie");
        System.out.println("flow:doMsgTest: db record " + strMsgValA);
        //sendSMS(strMsgValC, strMsgValB);
        // mMessageSentCount++;
        // sendNextMessage();
    }


    public void getSmsDetails() {
	getSmsDetails(false);
    }

    public void getSmsDetails(boolean boolNDel) {
        String SMS_READ_COLUMN = "read";
        int count = 0;
        int intAddressCol = 2;
        int intBodyCol = 5;
	  int intDateCol = 4;
	  int intThreadIdCol = 5;
        String strCname = "noQvalue";
        String[] selectionArgs;
        // String []columns = {"body", "type", "address"};
	  if(boolNDel == true) {
        selectionArgs = new String[]{"1","g://" + "%"};
	  } else {
        selectionArgs = new String[]{String.valueOf(intCurrHID),"g://" + "%"};
	  }
        Cursor cursor = c.getContentResolver().query(Uri.parse("content://sms/inbox"), null, "_id >? and body LIKE ?", selectionArgs, null);

        if (cursor != null) {
            try {
                count = cursor.getCount();
                if (count > 0) {
			  intCurrHID = count;
                    cursor.moveToFirst();
                    String[] columns = cursor.getColumnNames();
                    for (int i = 0; i < columns.length; i++) {
                        if (columns[i].equals("address")) {
                            intAddressCol = i;
                        } else if (columns[i].equals("body")) {
                            intBodyCol = i;
                        } else if (columns[i].equals("date")) {
                            intDateCol = i;
                        } else if (columns[i].equals("thread_id")) {
                            intThreadIdCol = i;
                        }
                        System.out.println("columns " + i + ": " + columns[i] + ": " + cursor.getString(i));
                    }

                    do {
                        long messageId = cursor.getLong(0);
                        String strMsgId = Long.toString(messageId);
                        intCurrHID = Integer.parseInt(strMsgId);
                        String address = cursor.getString(intAddressCol);
                        String body = cursor.getString(intBodyCol);
                        String strDate = cursor.getString(intDateCol);
                        String strTID = cursor.getString(intThreadIdCol);
                        try {
                        strCname = getContactDisplayNameByNumber(address);
                        } catch (Exception e) {

                        }
                        String fPCstr = strCname + " <" + address + ">";
				String isRegd = dbSQLA.doGetDBRecords("rec_date_added=?", new String[]{strDate}, "1");
				if(isRegd.equals("noQvalue")){
                        processSMS(strDate, strTID, body, fPCstr);
				} else {
                        System.out.println("flow:getSmsDetails:AlreadyRecord: " + address + " :: " + body + " :: " + fPCstr);
				}				
                        System.out.println("flow:getSmsDetails: " + address + " :: " + body + " :: " + fPCstr + " :: " + String.valueOf(messageId));
				if(boolNDel == true) {
 				Uri uriSMSURI = Uri.parse("content://sms/" + strMsgId);
 				cr.delete(uriSMSURI, null, null);
				}


                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }

    }


    public void processSMSText(String strSMSTxt, String strSMSfrom, String strIMDate, String iMsgID) {
        System.out.println("flow:processSMSText:raw: " + strSMSTxt);

        String strTSMSTxt = strSMSTxt;
        String strRecType = "noQvalue";
        String strRecValA = "noQvalue";
        String strRecValB = "noQvalue";
        String strRecValC = "noQvalue";
        String strRecValD = "noQvalue";
        if (strTSMSTxt.contains("|")) {
            StringTokenizer stringtokenizer = new StringTokenizer(strTSMSTxt, "|");
            int i = stringtokenizer.countTokens();
            String d[] = new String[i];
            for (int j = 0; j < i; j++) {
                d[j] = stringtokenizer.nextToken();
                // arrMsgNums[j] = as[j];
                System.out.println("processSMSTextTokes: " + d[j]);
                switch (j) {
                    case 0:
                        strRecType = d[j];
                        break;
                    case 1:
                        strRecValA = d[j];
                        break;
                    case 2:
                        strRecValB = d[j];
                        break;
                    case 3:
                        strRecValC = d[j];
                        break;
                    case 4:
                        strRecValD = d[j];
                        break;
                    default:
                        strRecValD = "noQvalue";
                        break;
                }
            }

            if (strRecType.equals("41")) {  // challenge received
                long lastId = 1;
                dbSQLA.openToRead();
                dbSQLA.openToWrite();
                dbSQLA.insert(41, strRecValA, strRecValB, strRecValC, strRecValD);
                String query = "SELECT " + dbSQLA.KEY_REC_ID + " from " + dbSQLA.MYDATABASE_TABLE + " order by " + dbSQLA.KEY_REC_ID + " DESC limit 1";
                Cursor c = dbSQLA.sqLiteDatabase.rawQuery(query, null);
                if (c != null && c.moveToFirst()) {
                    lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
                    System.out.println("dev:processSMSText.lastId: " + lastId);
                }
                if (strRecValC.contains(";")) {
                    StringTokenizer astringtokenizer = new StringTokenizer(strRecValC, ";");
                    int ai = astringtokenizer.countTokens();
                    String ad[] = new String[ai];
                    for (int jj = 0; jj < ai; jj++) {
                        ad[jj] = astringtokenizer.nextToken();
                        if (ad[jj].contains(":")) {
                            String[] asStr = ad[jj].split(":");
                            long l = System.currentTimeMillis() / 1000;
                            String s = Long.toString(l);
                            int tStart = Integer.parseInt(s);
                            int dateMod = tStart + Integer.parseInt(asStr[1]);
                            dbSQLA.openToWrite();
                            dbSQLA.insert(49, String.valueOf(lastId), asStr[0], strRecValB, String.valueOf(tStart));
                            dbSQLA.close();
                        }
                    }
                    dbSQLA.close();
                }
            } else if (strRecType.equals("21")) {
                String strTPname = "noQvalue";
                String strTPnum = "noQvalue";
                if (strSMSfrom.contains("<")) {
                    String[] asStr = strSMSfrom.split("<");
                    strTPname = asStr[0].trim();
                    strTPnum = asStr[1].replace(">", "");
                    System.out.println("dev:processSMSText:21 " + strTPname + " :: " + strTPnum);
                }

	  ContentValues targs = new ContentValues();
	  targs.put("rec_date_modified", iMsgID);
		    dbSQLA.close();
                dbSQLA.openToWrite();
                dbSQLA.insert(Integer.parseInt(strRecType), strRecValA, strTPnum, strTPname, strIMDate, iMsgID);
		    dbSQLA.close();
                dbSQLA.openToWrite();
	          String tmpSU = dbSQLA.doJSUpdateRecord("theTable", targs, "rec_val_c=?", new String[]{strTPname});
		    dbSQLA.close();
            } else if (strRecType.equals("9")) {
                dbSQLA.openToRead();                
                dbSQLA.openToWrite();
                dbSQLA.insert(Integer.parseInt(strRecType), strRecValA, strRecValB, strRecValC, strRecValD + ";" + strSMSfrom);
                dbSQLA.close();
            } else {
            System.out.println("flow:processSMSText:default: " + strRecType + " :: " + strRecValA + " :: " + strRecValB + " :: " + strRecValC + " :: " + strRecValD + " :: " + strSMSfrom);
                dbSQLA.openToRead();
                dbSQLA.openToWrite();
                dbSQLA.insert(Integer.parseInt(strRecType), strRecValA, strRecValB, strRecValC, strRecValD + ";" + strSMSfrom);
                dbSQLA.close();
            }
            System.out.println("flow:processSMSText: " + strRecType + " :: " + strRecValA + " :: " + strRecValB + " :: " + strRecValC + " :: " + strRecValD + " :: " + strSMSfrom);

        }

    }

    public void processSMS(String strIMDate, String strSTID, String strMsgBody, String strFrom) {

        String[] params;
        String[] upEnc;
        String text = null;
        params = strMsgBody.split("/");
        if (params[4] != null) {
            String clnSMSBody = params[4];
                System.out.println("flow:processSMS:clnSMSBody: " + clnSMSBody);
            try {
                if (clnSMSBody.contains("|")) { // scenes
                    upEnc = clnSMSBody.split("|");
                    if (upEnc[1] != null) {
                        byte[] ddata = Base64Android.decode(upEnc[0], Base64Android.NO_WRAP);
                        text = new String(ddata, "ISO-8859-1");
                        text += upEnc[1] + "|" + strIMDate + "|" + strSTID;
                    }
                } else {

                    byte[] ddata = Base64Android.decode(clnSMSBody, Base64Android.NO_WRAP);

                    text = new String(ddata, "ISO-8859-1");
                System.out.println("flow:processSMS:clnSMSBodyA: " + text);
                }
 
                // Uri uriSMSURI = Uri.parse("content://sms/" + intSMSID);
                // ContentValues values = new ContentValues();
                // values.put("read", true);

                //   cr.update(Uri.parse("content://sms/inbox"), values, "_id=" + intSMSID, null);
                // cr.delete(uriSMSURI, null, null);
                System.out.println("flow:processSMS: " + text + " :: " + strFrom);
                processSMSText(text, strFrom, strIMDate, strSTID);


            } catch (UnsupportedEncodingException e) {
                System.out.println("processSMS: " + e.toString());  //To change body of catch statement use File | Settings | File Templates.
            }


        }
	
	}

 }