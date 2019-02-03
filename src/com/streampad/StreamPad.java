package com.streampad;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Browser;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.*;
import android.widget.*;
import android.view.WindowManager;

import com.streampad.js_interfaces.JSI_StreamPad;
 
import com.njfsoft_utils.core.SpaceTokenizer;
import com.njfsoft_utils.core.Base64; 

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import com.jwetherell.quick_response_code.CaptureActivity;



import com.njfsoft_utils.core.Base64;
import com.njfsoft_utils.dbutil.UtilDbRecord;
import com.njfsoft_utils.dbutil.UtilSQLAdapter;

import com.njfsoft_utils.smsutil.SmsUtils;
import com.njfsoft_utils.smsutil.ServiceListener;

import com.njfsoft_utils.webviewutil.UtilWebView;
import com.njfsoft_utils.webviewutil.UtilWebDialog;
 



public class StreamPad extends Activity {

    private UtilWebView mWebView;
    private final Activity activity = this;
    private static Bundle currConfBundle;
 
 

    private SharedPreferences configSettings;
    private SharedPreferences.Editor configEditor;
    String currImgString;
    long currImgID;
    long oldImgID;
    public int currStoryID;
    public int currStoryType;
    private SlidingDrawer slidingDrawer;
    public ProgressDialog progressDialog;
    private MultiAutoCompleteTextView autoCompleteTextView;
    ArrayList aListHistUrls;
    ArrayList aListHistTitles;


    UtilWebDialog utilWDialog;
    public UtilWebDialog epMainDialog;
    public UtilSQLAdapter dbMSQLA;
    public UtilSQLAdapter challSQLiteAdapter;
    SmsUtils smsUtils;
    ServiceListener cServiceListener;

    public ProgressDialog dialog;
    private Handler mHandler;
    private LinearLayout lnrLyt_UWView;
 private final String strHomeUrl = "file:///android_asset/index.html";
//    private final String strHomeUrl = "file:///android_asset/flying_chop/index.html";

    private final String strSettingsSaved = "Settings saved.";
    static final int CAMERA_REQUEST = 3;
    static final int GALLERY_REQUEST = 4;
  














    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

 
         requestWindowFeature(Window.FEATURE_PROGRESS);
        configSettings = this.getPreferences(MODE_WORLD_WRITEABLE);
        System.out.println("StreamPad onCreate: ");
        CookieSyncManager.createInstance(activity);

        configEditor = configSettings.edit();
        currConfBundle = getConfBundle();
        int CURR_SCREEN_ORIENT = currConfBundle.getInt("confScreenOrient", 0);
 

        setContentView(R.layout.com_streampad_main);
 


        cServiceListener = new ServiceListener() {
            @Override
            public void onComplete(String s,  UtilDbRecord tmpASCpnum) {
	                try {
                int theLastID = dbMSQLA.getLastID();	  
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                mWebView.loadUrl("javascript:doSvcMsgComm('" + theLastID + "','" + s + "')");

                } catch (Exception e) {
        	    System.out.println("dev:ERROR:StreamPad:cServiceListener:onComplete: " + e);
                }
                System.out.println("flow:StreamPad:cServiceListener: " + s);
            }
        };

        smsUtils = new SmsUtils(this, cServiceListener);
        mHandler = new Handler();

        dbMSQLA = new UtilSQLAdapter(this);
        preparePagePopUps("blank.html", "noQvalue");
	  currStoryID = 0;
	  currStoryType = 50;
    }
 


    @Override
    public void onResume() {
        super.onResume();
        try {
            dialog.dismiss();
        }  catch(Exception e) {
            System.out.println("onResume: " + e.toString());
        }
        try {
 

	  if(mWebView == null) {
        mWebView = new UtilWebView(this, strHomeUrl);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.clearSslPreferences();
        WebView.enablePlatformNotifications();
        mWebView.addJavascriptInterface(new JSI_StreamPad(this), "app");
 

        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        boolean CURR_SHOW_WEB_IMAGES = Boolean.parseBoolean(currConfBundle.getString("confShowWebImgs"));
         mWebView.getSettings().setLoadsImagesAutomatically(CURR_SHOW_WEB_IMAGES);
 

        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
 

        registerForContextMenu(mWebView);




	   lnrLyt_UWView = (LinearLayout) StreamPad.this.findViewById(R.id.ll_uwv);
	   lnrLyt_UWView.addView(mWebView.getLayout());

         } else {
            System.out.println("onResume mWebView.notnull: ");	
	   }

        }  catch(Exception e) {
 
            System.out.println("onResume: " + e.toString());
        }
    }



    public void setPagePopUp(final String fnlPageUrl, final String fnlPageHtml) {

        try {
            this.runOnUiThread(new Runnable() {
                public void run() {
        utilWDialog.setPopPage(fnlPageUrl, fnlPageHtml);
                }
            });


        } catch (Exception e) {
            System.out.println("doStoryShare dialog" + e.toString());
        }

    }

 



    public void preparePagePopUps(String pageUrl, String pageHtml) {
        String fullUrl = "file:///android_asset/" + pageUrl;
        String newHTML = "";

        UtilWebDialog.UtilWDListener utilWDListener = new UtilWebDialog.UtilWDListener() {

            public void epMDcom(int cbType, String cbArgs, UtilWebDialog epmd) {
                final String fnlCbArgs;
                epmd.doDismiss();

			// handler.removeCallbacks(thrdTask);
			// handler.postDelayed(thrdTask, 0);
			// showDaToast(cbType + " : " + cbArgs);
            System.out.println("dev:SmsCanvas:epMainDListener: " + cbType + " : " + cbArgs);
                switch (cbType) {
                          case 5:
        try {

                        mWebView.loadUrl("javascript:getPageThread('" + cbType + "','" + cbArgs + "');");

//                    dwEdit.toggleStyle(3, cbArgs);

 

        } catch (Exception e) {
            System.out.println("dev:ERROR:setSFColorEdit:" + e.toString());
        }
                        break;
                    case 10: // load local url
				doLoadLclUrl(cbArgs);
                      //   setSFColorEdit(cbArgs);
                        break;
                    case 15:
                        mWebView.loadUrl("javascript:setSceneProps('" + String.valueOf(cbType) + "');");
                        break;
                    case 20:
                        if(cbArgs.contains(":")) {
                            String nStrTCB = cbArgs.replace(": ", "<");
                            nStrTCB += ">, ";
                            cbArgs = nStrTCB;
				    
                        }
 
                        mWebView.loadUrl("javascript:doDLgCUp();");
				break;
                    case 25:
				if(cbArgs.equals("19")) {
                       //  setSceneType("4");
				} else {
                        // setSceneType(cbArgs);
				}
                        mWebView.loadUrl("javascript:setSceneProps('" + cbArgs + "');");
                        break;
                    case 30:
				// doEPPicture(cbArgs);
                        break;
                    case 50:
				 if(cbArgs.contains(":")) {
		    		String[] asHStr = cbArgs.split(":");
				dbMSQLA.doUpdateRecord(Integer.parseInt(asHStr[0]), "rec_val_c=;" + asHStr[1]);				
 				// showDaToast(asHStr[0] + " :: " + asHStr[1]);
				mWebView.reload();
				}
                        break; 
                    case 101:
 				// showDaToast("epMDcom: " + cbType + " :: " + cbArgs);
				mWebView.loadUrl("javascript:doOrderSend('" + cbArgs +  "');");
                        break; 
                   case 200:
                        // showDaToast(cbArgs);
                        mWebView.loadUrl("javascript:doDLgCUp();");
                        break;

                    case 300:
                        System.out.println("dev:preparePagePopUps:defaultDlgClose[300]");
                        break;
                    case 500:
                       //  showDaToast(cbArgs);
				mWebView.loadUrl("javascript:getPageThread(" + cbType + "," + cbArgs +  ");");
                        break;
                    default:
                        mWebView.loadUrl("javascript:doDLgCUp();");
                        break;
                }
            }
        };
        utilWDialog = new UtilWebDialog(this, fullUrl, pageHtml, utilWDListener, new JSI_StreamPad(this), "app");
	 //  utilWDialog.addJSobj(new JSI_smsCanvas(this), "app_cnvs");
	  utilWDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }




 

 

    public void showDaToast(final String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
    }

    Bundle getConfBundle() {
        Bundle theConfBundle = new Bundle();
        theConfBundle.putString("confBrowBkgd", configSettings.getString("confBrowBkgd", "#800000"));
        theConfBundle.putString("confShowWebImgs", configSettings.getString("confShowWebImgs", "true"));
        theConfBundle.putInt("confScreenOrient", configSettings.getInt("confScreenOrient", 1));
        return theConfBundle;
    }
 


    public static String getConfValString(String theKey) {
        String strTheKey = "noQvalue";
        try {
            strTheKey = currConfBundle.getString(theKey);
        } catch (Exception err) {
            System.out.println("Error.getConValString: " + err);
        }
        return strTheKey;
    }

    public Integer getConfValInt(String theKey) {

        int strTheKey = 1234;
        try {
            strTheKey = currConfBundle.getInt(theKey);
        } catch (Exception err) {
            System.out.println("Error.getConValString: " + err);
        }
        return strTheKey;
    }


    public void putConfValString(String theKey, String theVal) {


        configEditor = configSettings.edit();
        configEditor.putString(theKey, theVal);
        configEditor.commit();
        currConfBundle = getConfBundle();
        showDaToast(strSettingsSaved);


    }

    public void putConfValInt(String theKey, Integer theVal) {

        configEditor = configSettings.edit();
        configEditor.putInt(theKey, theVal);
        configEditor.commit();
        currConfBundle = getConfBundle();

        showDaToast(strSettingsSaved);

    }



    public void doStreamPic(String fromType) {
        if (fromType.equals("camera")) {
            //   //  // startEpCamera();
            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(pictureIntent, CAMERA_REQUEST);
            // pictureIntent.setRequestedOrientation(1);
        } else {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, (MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
            startActivityForResult(pickPhoto, GALLERY_REQUEST);
        }
    }




    public String getCompdMsg(String strEdTxt) {
 
         String strPaa = strEdTxt.replace("!j", "-1;0");
        String strPab = strPaa.replace("!i", "9-1;0");
        String strPac = strPab.replace("!h", "|\n50;");
        String strPad = strPac.replace("!g", " 1lt;");
        String strPb = strPad.replace("!f", " 2kg;");
        String strPc = strPb.replace("!e", " 1kg;");
        String strPd = strPc.replace("!d", " de ");
        String strPe = strPd.replace("!c", " e ");
        String strPf = strPe.replace("!b", " o ");
        String strPg = strPf.replace("!a", " MyShop");
        String strPh = strPg.replace("!!", "<br>");
        return strPg;
 
	}


    public static String[] getStrArr(String s, String s1) {
        String s2 = s;
        String s3 = s1;
        StringTokenizer stringtokenizer = new StringTokenizer(s2, s3);
        int i = stringtokenizer.countTokens();
        String as[] = new String[i];
        for (int j = 0; j < i; j++)
            as[j] = stringtokenizer.nextToken();

        return as;
    }



    public String  procsData(String strRecValT)  {

		   String retStr = "noQvalue";
		  int innew = 0;
		  int currInt = 0;

		try {
		String strRecValC = getCompdMsg(strRecValT);
		// showDaToast(strRecValC);
            if(strRecValC.contains("|")) {
                    StringTokenizer astringtokenizer = new StringTokenizer(strRecValC, "|");
                    int ai = astringtokenizer.countTokens();
  			  String[] strFA = getStrArr(strRecValC, "|");
			  if(strFA[0].contains(";")) {
		    	String[] asHStr = strFA[0].split(";");				
			String[] parseT;
 			parseT = new String[]{asHStr[0], asHStr[1]};
                  String fstr = "50";


 			String getChallQ = "rec_type=? and rec_val_a=?";
			ArrayList<UtilDbRecord> lpDbRecord = dbMSQLA.getUtilDbRecords(getChallQ, parseT, "1");
        		if(lpDbRecord.size() >= 1) {
			   currInt = lpDbRecord.get(0).getKeyRecID();
			   innew = lpDbRecord.get(0).getKeyRecType();

	 
			        }  else {
			System.out.println("lpDbRecord.size() <= 0");
                    dbMSQLA.openToWrite();
			 currInt = dbMSQLA.doIntRecordAdd(Integer.parseInt(asHStr[0]), asHStr[1], asHStr[2], asHStr[3], asHStr[4]);
			 innew = Integer.parseInt(asHStr[0]);
				dbMSQLA.openToWrite();
                    for (int jj = 1; jj < ai; jj++) {
                        if (strFA[jj].contains(";")) {
                            String[] asStr = strFA[jj].split(";");
				    if((innew == 5) || (innew == 500)) { // folheto or recipe
				     fstr = asStr[3] + "-" + asStr[1];
				     asStr[0] = "50";
				     asStr[1] = String.valueOf(currInt);
				    } else {
				    fstr = asStr[3];
				    }
                            dbMSQLA.insert(Integer.parseInt(asStr[0]), asStr[1], asStr[2], fstr, asStr[4]);
                        }
                    }
			   dbMSQLA.close();
 

        }  // else lpDbRecord.size() greater 0 

	

			} // if ad[0].contains ;
       
                }  // strRecValC.contains |

        } catch (Exception e) {
		 e.printStackTrace();
            System.out.println("procsData" + e.toString());
		  return e.toString();
        }
		//  return String.valueOf(currInt);
		  return String.valueOf(innew) + "','" + String.valueOf(currInt);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {

        switch (requestCode) {
            case (11):
                if (resultCode == Activity.RESULT_OK) {

                    try {
				String strRecValC = data.getStringExtra("encdBmp").toString();
                        System.out.println("onActivityResult: " + strRecValC);	
  				String strResU = procsData(strRecValC);
				// showDaToast("resU:" + strResU);
				mWebView.loadUrl("javascript:getPageThread('" + procsData(strRecValC) + "');");
                       // doLoadLclUrl("sqldump.html");
                    } catch (Exception e) {
                        System.out.println("onActivityResult: " + e.toString());
                    }
                } else {
                    //gallery MODE CANCELLED
                }
                break;

            default: {
                System.out.println("Its default");
            }

        }



    }
  

	public void rinsert() {
    String d = "5;223;April Promo Leaflet;Produtos em campanha...;Dadded!h1;COD with Olive Oil 120gr;2.79-1.99-1;2!h2;Tuna In Olive Oil 120gr;1.59-0.79-1;1!h3;Tuna and TOMATE 125gr;1.14-0.8!i!h4;MAIONESE HELLMAN\'S 412gr;2.59-1.68-1;3!h5;White Sugar RAR 1KG;0.99-0.79-1;3!h6;CHOCOLATE P/ Cooking Chocolate 200gr;1.69-1.26-1;2!h7;Keep me Up Coffee 250gr;2.66-1.74-1;1!h8;Keep me Up Coffee 1KG;10.49-7.86-1;2!h9;CEVADA DELTA Q PURE 10CAPSULAS;2.29-1.9!i!h10;CEREAIS ESTRELITAS SABOR BOLACHA MARIA 270gr;1.99-0.99-1;1!h11;CHOCOLATE P/ CULINARIA 200gr;1.69-1.26-1;2";
     mWebView.loadUrl("javascript:getPageThread('" + procsData(d) + "');");
	}

    public void doPageLoad() {
        String abgclr = "#800000";
        try {
            abgclr = currConfBundle.getString("confBrowBkgd");
            mWebView.loadUrl("javascript:doPageLoad('" + abgclr + "','noQvalue','noQvalue');");
        } catch (Exception e) {
            System.out.println("doPageLoad" + e.toString());
        }
    }

 


    public static String getLocalUrl(String strLUrl) {
    return "file:///android_asset/" + strLUrl ;
    }
 

 
    public void doQRscan() {
        try {
            Intent toMain = new Intent(this, com.jwetherell.quick_response_code.CaptureActivity.class);
            // toMain.putExtra("encdBmp", currImgString);
            startActivityForResult(toMain, 11);
        } catch(Exception e) {
            System.out.println("doArtPadBitmap" + e.toString());
        }
    }


    public void doWaitDialog() {
        dialog = ProgressDialog.show(StreamPad.this, "","Pf Aguarde..", true, true);
    }


    public void doLoadLclUrl(final String theFnlLclUstr) {
        try {
            this.runOnUiThread(new Runnable() {
                public void run() {
        String fullUrl = "file:///android_asset/" + theFnlLclUstr;
        mWebView.loadUrl(fullUrl);
                }
            });

 
        } catch (Exception e) {
		 e.printStackTrace();
            System.out.println("doLoadLclUrl" + e.toString());
 
        }
    }

     public void setPageThread(int ptype, int pid, String purl) {
	try {
	currStoryID = pid;
	currStoryType = ptype;
	if(ptype == 50) {
	setPagePopUp(purl,"noQvalue");
	} else {
	doLoadLclUrl(purl);
	}
        } catch (Exception e) {
		 e.printStackTrace();
            System.out.println("setPageThread" + e.toString());
 
        }
    }

     public void getPageThread() {
 
	try {

       mWebView.loadUrl("javascript:getPageThread('" + currStoryType + "','" + currStoryID + "');");

	  } catch (Exception e) {
		 e.printStackTrace();
            System.out.println("setPageThread" + e.toString());
 
        }
    }
     public void getPopPageThread() {
 
	try {

       setPagePopUp("javascript:getPageThread('" + currStoryType + "','" + currStoryID + "');","noQvalue");

	  } catch (Exception e) {
		 e.printStackTrace();
            System.out.println("setPageThread" + e.toString());
 
        }
    }

     public void doPageReload() {
        mWebView.reload();
    }   
    public void runFormatSMS(String strRKey, String strMsgArgs, String strPhoneNums, String strMsg) {
		smsUtils.setFormatSMS("20", strMsgArgs, strPhoneNums, "noQvalue");
	}
    public void runSmsDetails() {
		smsUtils.getSmsDetails();
	}
	public void doRecInsert(int rt, String va, String vb, String vc, String da) {
	try {
		dbMSQLA.openToWrite();
		dbMSQLA.insert(rt, va, vb, vc, da);
		dbMSQLA.close();		
		
	  } catch (Exception e) {
		dbMSQLA.close();	
		 e.printStackTrace();
            System.out.println("doRecInsert" + e.toString());
 
        }
	}


	public void setOrderClean(String onum, String strOtitle, String strOttl, String strOtype) {

	try {

			String[] parseT;
			String chgstr = "1";
	            parseT = new String[]{"2","3","50"};
			int oid = 5;
 			String getChallQ = "rec_date_modified=? or rec_date_modified=? and rec_type=?";
 		 dbMSQLA.close();
		dbMSQLA.openToRead();
			ArrayList<UtilDbRecord> lpDbRecord = dbMSQLA.getUtilDbRecords(getChallQ, parseT, "300");
		 dbMSQLA.close();
        if (lpDbRecord.size() <= 0) {
            System.out.println("doOrderClean count: IS NULL");
        } else {
		dbMSQLA.openToWrite();
 
			oid = dbMSQLA.doIntRecordAdd(80, onum, strOtitle, strOttl, strOtype);

	 		 dbMSQLA.close();
            for (UtilDbRecord list : lpDbRecord) {
                String strRecID = String.valueOf(list.getKeyRecID());
                String strRecType = String.valueOf(list.getKeyRecType());
                String strRecValA = list.getKeyRecValA();
                String strRecValB = list.getKeyRecValB();
                String strRecValC = list.getKeyRecValC();
                String strRecDateAdded = list.getKeyRecDateAdded();
                String strRecDateModified = list.getKeyRecDateModified();
                System.out.println("flow:doSQLtest:record: " + strRecID + " :: " + strRecType + " :: " + strRecValA + " :: " + strRecValB + " :: " + strRecValC + " :: " + strRecDateAdded + " :: " + strRecDateModified);
		dbMSQLA.openToWrite();
 	
	    dbMSQLA.insert(81, String.valueOf(oid), strRecValB, strRecValC, strRecDateModified);
		 dbMSQLA.close();
            }

		dbMSQLA.openToRead();
		dbMSQLA.openToWrite();
		    dbMSQLA.doUpdateRecords("rec_date_modified=? and rec_type=?","2:50", "rec_date_modified=;1");
		 dbMSQLA.close();

		dbMSQLA.openToRead();
		dbMSQLA.openToWrite();
		    dbMSQLA.doUpdateRecords("rec_date_modified=? and rec_type=?","3:50", "rec_date_modified=;0");
		 dbMSQLA.close();

            	System.out.println("flow:setOrderClean:total: " + String.valueOf(lpDbRecord.size()));
       		mWebView.loadUrl("javascript:setSentConf()");

        }
	  } catch (Exception e) {
		dbMSQLA.close();	
		 e.printStackTrace();
            System.out.println("setOrderClean" + e.toString());
 
        }


	}
	
 


}
