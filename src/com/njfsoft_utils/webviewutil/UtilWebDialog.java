package com.njfsoft_utils.webviewutil;


import android.app.*;
import android.app.Dialog;
import android.content.*;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import android.view.WindowManager;
import com.njfsoft_utils.webviewutil.JSI_MainDialog;
import com.njfsoft_utils.webviewutil.UtilWebView;

 
import java.util.regex.Pattern;

 

public class UtilWebDialog extends Dialog {

    public interface UtilWDListener {
        void epMDcom(int cbType, String cbArgs, UtilWebDialog epmd);
    }

    UtilWDListener utilWDListener;
    static final FrameLayout.LayoutParams MAINFILL = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
    String strHTML = "noQvalue";
    private String mUrl;
    private DialogListener utilMWDListener;
    private UtilWebView epMainWebView;
    Button btnClose;
    Context contxt;
    Object objJSinterface = new JSI_MainDialog(this);
    String strAppName = "epmapp";
    private FrameLayout mContent;
    private ImageView mCrossImage;
    String strAppData;
    public UtilWebDialog(Context context, String url, DialogListener listener) {
        super(context, android.R.style.Theme_Light_NoTitleBar);
        mUrl = url;
        utilMWDListener = listener;
        contxt = context;
    }

 

    public UtilWebDialog(Context context, String url, UtilWDListener theMainDListener) {
        super(context, android.R.style.Theme_Light_NoTitleBar);
        mUrl = url;
        utilWDListener = theMainDListener;
        contxt = context;
    }



    public UtilWebDialog(Context context, String url, String htmlStr, Object theJsIobj, String theAppName) {
        super(context, android.R.style.Theme_Light_NoTitleBar);
        contxt = context;
        objJSinterface = theJsIobj;
        strAppName = theAppName;
        mUrl = url;
        strHTML = htmlStr;
        contxt = context;
        // epMainHbook = theHbook;
    }


    public UtilWebDialog(Context context, String url, String htmlStr, UtilWDListener theMainDListener, Object theJsIobj, String theAppName) {
        super(context, android.R.style.Theme_Light_NoTitleBar);
        contxt = context;
        objJSinterface = theJsIobj;
        strAppName = theAppName;
        mUrl = url;
        strHTML = htmlStr;
        contxt = context;
        utilWDListener = theMainDListener;
        UtilWDListener l = new UtilWDListener() {
            public void epMDcom(int cbType, String cbArgs, UtilWebDialog epmd) {
                utilWDListener.epMDcom(cbType, cbArgs, epmd);
                dismiss();
            }
        };
        // epMainHbook = theHbook;
    }



  


    public void setPopPage(String theUrl, String theHtml) {
        String fullUrl = "";
        if (theUrl.startsWith("java")) {
            fullUrl = theUrl;
        } else {
            fullUrl = "file:///android_asset/" + theUrl;
        }

        if (theHtml.equals("noQvalue")) {
            System.out.println("setPopPage: " + fullUrl + " :: " + theHtml);
            show();
            epMainWebView.loadUrl(fullUrl);
        } else {
            show();
            strAppData = theHtml;		
            epMainWebView.loadUrl(fullUrl);
        }

    }

    public void doHide() {

        String blankUrl = "file:///android_asset/blank.html";
        //  epMainWebView.loadUrl(blankUrl);
        this.hide();
    }

    public void doDismiss() {

        String blankUrl = "file:///android_asset/blank.html";
        //  epMainWebView.loadUrl(blankUrl);
        this.dismiss();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

	  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //  setContentView(R.layout.ep_main_dialog);


     int id = contxt.getResources().getIdentifier("translucent_black", "color", contxt.getPackageName());
		
       this.getWindow().setBackgroundDrawableResource(id);
	//  	this.getWindow().setBackgroundDrawable(new ColorDrawable(97101040));
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                String blankUrl = "file:///android_asset/blank.html";
                epMainWebView.loadUrl(blankUrl);
                setEpMDcom(3000, "noQvalue");

            }
        });


        this.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                String blankUrl = "file:///android_asset/blank.html";
                epMainWebView.loadUrl(blankUrl);
                setEpMDcom(3000, "noQvalue");
            }
        });

        mContent = new FrameLayout(getContext());

        createCrossImage();


        int crossWidth = mCrossImage.getDrawable().getIntrinsicWidth();
        setUpWebView(crossWidth / 2);


 


         
        RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        lay.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        // mContent.setLayoutParams(MAINFILL);
        mContent.addView(mCrossImage, lay);
        addContentView(mContent, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));


    }


    private void setUpWebView(int margin) {
        LinearLayout webViewContainer = new LinearLayout(getContext());
        LinearLayout webViewInner = new LinearLayout(getContext());

	//  int id = getResourceIdByName(contxt.getPackageName(), "drawable", "rounded_corners");
		
      int id = contxt.getResources().getIdentifier("rounded_corners", "drawable", contxt.getPackageName());
		
      Drawable crossDrawable = contxt.getResources().getDrawable(id);
      webViewInner.setBackgroundDrawable(crossDrawable);
        epMainWebView = new UtilWebView(contxt);


       //  epMainWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

       //  epMainWebView.setWebViewClient(new EPMainDlgWVClient());


       //  epMainWebView.getSettings().setJavaScriptEnabled(true);
        epMainWebView.addJavascriptInterface(objJSinterface, strAppName);

        epMainWebView.addJavascriptInterface(new JSI_MainDialog(this), "app_dlg");
	  // epMainWebView.setToggleWViewBtns(false, false);
        // webViewInner.setLayoutParams(MAINFILL);
        // epMainWebView.setLayoutParams(MAINFILL);

        webViewInner.setPadding(2, 2, 2, 2);
        webViewContainer.setPadding(margin, margin, margin, margin);
        webViewContainer.addView(webViewInner);
        webViewInner.addView(epMainWebView.getLayout());
        mContent.addView(webViewContainer);

        if (strHTML.equals("noQvalue")) {
            epMainWebView.loadUrl(mUrl);
        } else {
            epMainWebView.loadDataWithBaseURL(mUrl, strHTML, "text/html", "utf-8", "");
        }

    }


    private void createCrossImage() {
        mCrossImage = new ImageView(getContext());
        // Dismiss the dialog when user click on the 'x'
        mCrossImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDismiss();
            }
        });
 
       int id = contxt.getResources().getIdentifier("close", "drawable", getContext().getPackageName());
		
        Drawable crossDrawable = contxt.getResources().getDrawable(id);

 
        mCrossImage.setImageDrawable(crossDrawable);
        /* 'x' should not be visible while webview is loading
         * make it visible only after webview has fully loaded
        */
        // mCrossImage.setVisibility(View.INVISIBLE);
    }




    private class EPMainDlgWVClient extends WebViewClient {

        @Override
        public void onReceivedError(WebView mWebView, int errorCode, String description, String failingUrl) {
            System.out.println("onReceivedError: " + description);
            if (failingUrl.startsWith("file://") && failingUrl.contains("?")) {
                mWebView.stopLoading();
                System.out.print("shouldOverrideUrlLoading:oride " + failingUrl);
                String[] temp;
                temp = failingUrl.split(Pattern.quote("?"));
                // epMainHbook.setCurrPageVars(temp[1]);
                mWebView.loadUrl(temp[0]);

            } else {
                super.onReceivedError(mWebView, errorCode, description, failingUrl);
            }
        }


        @Override
        public void onPageStarted(WebView mWebView, String url, Bitmap favicon) {
            System.out.print("onPopPageStarted: " + url);
            // TODO Auto-generated method stub
            // Toast.makeText(mContext, "page started: " + url, Toast.LENGTH_LONG).show();
            super.onPageStarted(mWebView, url, favicon);
        }

        @Override
        public void onPageFinished(WebView mWebView, String url) {
            // TODO Auto-generated method stub
            System.out.print("onPopPageFinished: " + url);

            super.onPageFinished(mWebView, url);

        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView mWebView, String url) {
            if (url.startsWith("file://") && url.contains("?")) {
                System.out.print("shouldOverrideUrlLoading:oride " + url);
                String[] temp;
                temp = url.split(Pattern.quote("?"));
 		    // epMainHbook.setCurrPageVars(temp[1]);
                mWebView.loadUrl(temp[0]);
                return true;
            } else {
                return false;
            }
        }


    }


    public static interface DialogListener {

        /**
         * Called when a dialog completes.
         * <p/>
         * Executed by the thread that initiated the dialog.
         *
         * @param values Key-value string pairs extracted from the response.
         */
        public void onComplete(Bundle values);

        /**
         * Called when a Facebook responds to a dialog with an error.
         * <p/>
         * Executed by the thread that initiated the dialog.
         */

        public void onError(DialogError e);

        /**
         * Called when a dialog is canceled by the user.
         * <p/>
         * Executed by the thread that initiated the dialog.
         */
        public void onCancel();

    }

    public void setEpMDcom(int cbType, String cbArgs) {
        utilWDListener.epMDcom(cbType, cbArgs, this);
    }



public static int getResourceIdByName(String packageName, String className, String name) {
    Class r = null;
    int id = 0;
    try {
        r = Class.forName(packageName + ".R");

        Class[] classes = r.getClasses();
        Class desireClass = null;

        for (int i = 0; i < classes.length; i++) {
            if (classes[i].getName().split("\\$")[1].equals(className)) {
                desireClass = classes[i];

                break;
            }
        }

        if (desireClass != null) {
            id = desireClass.getField(name).getInt(desireClass);
        }

    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (IllegalArgumentException e) {
        e.printStackTrace();
    } catch (SecurityException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (NoSuchFieldException e) {
        e.printStackTrace();
    }

    return id;
}


}
