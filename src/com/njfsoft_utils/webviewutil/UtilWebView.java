package com.njfsoft_utils.webviewutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;




import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;


import android.content.Intent;
 
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.util.AttributeSet;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.*;
import android.widget.*;
import android.view.*; 



import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



// import com.streampad.utils.SpaceTokenizer;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import com.njfsoft_utils.core.SpaceTokenizer;
import com.streampad.R;



 
 
 

/**
 * Created by IntelliJ IDEA.
 * User: boss
 * Date: 20-10-2012
 * Time: 3:24
 * To change this template use File | Settings | File Templates.
 */
public class UtilWebView extends WebView {

    Context mContext;
    Activity spactivity;

    private View                                mCustomView;
    private FrameLayout                         mCustomViewContainer;
    private WebChromeClient.CustomViewCallback  mCustomViewCallback;
    private FrameLayout                         mContentView;

    private LinearLayout                        lnrLytUWView;
    private FrameLayout                         mBrowserFrameLayout;
    private FrameLayout                         mLayout;

   UtilWChromeClient mWebChromeClient;



    private ImageButton btnGoBack;
    private ImageButton btnGoFoward;
    private ImageButton btnRefresh;
    private ImageButton btnStop;
    private ImageButton btnSettings;
    private ImageButton slideButton;
    private Button btnGotoUrl;
    private ImageButton btnHome;
    SlidingDrawer slidingDrawer;
 
    private MultiAutoCompleteTextView autoCompleteTextView;
    ArrayList aListHistUrls;
    ArrayList aListHistTitles;
    RelativeLayout  panBtnsWebView;

    String strHomeUrl = "file:///android_asset/blank.html";

    static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);




    public UtilWebView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
	  spactivity = (Activity) mContext;
        setDaWebViewClient();
    }

    public UtilWebView(Context context, String tmpHomeUrl) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
	  spactivity = (Activity) mContext;
	  strHomeUrl = tmpHomeUrl;
        setDaWebViewClient();
    }

    public UtilWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
	  spactivity = (Activity) mContext;
        setDaWebViewClient();
    }


    public UtilWebView(Activity activity, Context context) {
        super(context);
        mContext = context;
        spactivity = activity;
        setDaWebViewClient();
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return true;
    }

 

  public void setDaWebViewClient() {


        setScrollBarStyle(SCROLLBARS_INSIDE_OVERLAY);
        setFocusable(true);
        setFocusableInTouchMode(true);
        // requestFocus(View.FOCUS_DOWN);

		mLayout = new FrameLayout(mContext);

        mBrowserFrameLayout = (FrameLayout) LayoutInflater.from(spactivity).inflate(R.layout.com_njfsoft_utils_utilwebview_main, null);
        lnrLytUWView = (LinearLayout) mBrowserFrameLayout.findViewById(R.id.uwv_main);





        btnGoBack = (ImageButton) mBrowserFrameLayout.findViewById(R.id.btn_go_back);
        btnGoFoward = (ImageButton) mBrowserFrameLayout.findViewById(R.id.btn_go_foward);
        btnRefresh = (ImageButton) mBrowserFrameLayout.findViewById(R.id.btn_refresh);
        btnStop = (ImageButton) mBrowserFrameLayout.findViewById(R.id.btn_stop);
        btnHome = (ImageButton) mBrowserFrameLayout.findViewById(R.id.btn_Home);
        btnSettings = (ImageButton) mBrowserFrameLayout.findViewById(R.id.btn_settings);
        btnGotoUrl = (Button) mBrowserFrameLayout.findViewById(R.id.btn_gotourl);
        String[] fullBlist;
        panBtnsWebView = (RelativeLayout) mBrowserFrameLayout.findViewById(R.id.rLayoutWVbtns);
 

        autoCompleteTextView = (MultiAutoCompleteTextView) mBrowserFrameLayout.findViewById(R.id.aCompTextView);
        autoCompleteTextView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        autoCompleteTextView.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (arg1 == EditorInfo.IME_ACTION_DONE) {
                    try {
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                        UtilWebView.this.loadUrl(autoCompleteTextView.getText().toString());
                        slidingDrawer.close();
                    } catch (Exception e) {
                       //  showDaToast(e.toString());
                    }
                    // search pressed and perform your functionality.
                }
                return false;
            }

        });
        // you can also prompt the user with a hint
        autoCompleteTextView.setHint("http://");
        autoCompleteTextView.setTokenizer(new SpaceTokenizer());
        btnGotoUrl = (Button) mBrowserFrameLayout.findViewById(R.id.btn_gotourl);
        slideButton = (ImageButton) mBrowserFrameLayout.findViewById(R.id.slideButton);
        slidingDrawer = (SlidingDrawer) mBrowserFrameLayout.findViewById(R.id.slidingDrawer);
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                System.out.println("slide drawer opened");
               //  setHistoryLinks();

            }
        });

        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                try {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                } catch (Exception e) {
                    System.out.println("setOnDrawerCloseListener: " + e.toString());
                }
                System.out.println("slide drawer closed");
            }
        });


        btnGoBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (UtilWebView.this.canGoBack()) {
                    if (UtilWebView.this.getUrl().equals("about:blank")) {
                        UtilWebView.this.loadUrl(strHomeUrl);
                    } else {
                        UtilWebView.this.goBack();

                    }


                }

            }
        });


        btnGoFoward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (UtilWebView.this.canGoForward()) {
                    if (UtilWebView.this.getUrl().equals("about:blank")) {
                        UtilWebView.this.loadUrl(strHomeUrl);
                    } else {
                        UtilWebView.this.goForward();

                    }

                }

            }
        });


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (UtilWebView.this.getUrl().equals("about:blank")) {
                    UtilWebView.this.loadUrl(strHomeUrl);
                } else {
                    UtilWebView.this.reload();
                }


            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UtilWebView.this.stopLoading();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // doLoadLclUrl("settings.html");
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    UtilWebView.this.loadUrl(strHomeUrl);
                } catch (Exception e) {
//                    showDaToast(e.toString());
                }


            }
        });
        btnGotoUrl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                    String strUrlString = autoCompleteTextView.getText().toString();
                    if (strUrlString.toLowerCase().startsWith("http")) {

                    } else {
                        strUrlString = "http://" + strUrlString;
                    }
                    UtilWebView.this.loadUrl(strUrlString);
                    slidingDrawer.close();
                } catch (Exception e) {
//                    showDaToast(e.toString());
                }


            }
        });



   



        UtilWebView.this.clearSslPreferences();
        UtilWebView.this.enablePlatformNotifications();
 
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        try {
            Method m = WebSettings.class.getMethod("setDomStorageEnabled", new Class[]{boolean.class});
            m.invoke(getSettings(), true);
        } catch (Exception e) {
           //  showDaToast(e.toString());
        }



 
	   setToggleWViewBtns(false,false);
        // loadUrl(strHomeUrl);




        WebSettings webSettings = getSettings();
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setUseWideViewPort(false);
        webSettings.setLoadWithOverviewMode(false);
	  // fix this showing images
       //  boolean CURR_SHOW_WEB_IMAGES = Boolean.parseBoolean(currConfBundle.getString("confShowWebImgs"));
       //  webSettings.setLoadsImagesAutomatically(CURR_SHOW_WEB_IMAGES);

        webSettings.setBuiltInZoomControls(false);
	  webSettings.setPluginsEnabled(true);
 
	  webSettings.setDomStorageEnabled(true);
	  webSettings.supportMultipleWindows();
        webSettings.setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
//         UtilWebView.this.registerForContextMenu(this);


      //  s.setSavePassword(true);

        addJavascriptInterface(new JSI_UWV(this), "app_uwv");
        // UtilWebView.this.setFocusable(false);
        // UtilWebView.this.setFocusableInTouchMode(false);
        // UtilWebView.this.requestFocus(View.FOCUS_DOWN);

        CookieSyncManager.createInstance(mContext);
	  mWebChromeClient = new UtilWChromeClient();
     UtilWebView.this.setWebChromeClient(mWebChromeClient);
 //   UtilWebView.this.setWebChromeClient(new WebChromeClient());
        UtilWebView.this.setWebViewClient(new UtilWViewClient());	 
       lnrLytUWView.addView(this);

       mLayout.addView(mBrowserFrameLayout, COVER_SCREEN_PARAMS);
        // addContentView(mLayout);
	// setHistoryLinks();
	UtilWebView.this.loadUrl(strHomeUrl);
   }




    public void setHomeUrl(String theHomeUrl) {
        strHomeUrl = theHomeUrl;
    }
    public FrameLayout getLayout() {
        return mLayout;
    }

    public boolean inCustomView() {
        return (mCustomView != null);
    }

    public void hideCustomView() {
        mWebChromeClient.onHideCustomView();
    }







	private class UtilWViewClient extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
           Toast.makeText(mContext, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
		if(url.indexOf("http") != -1) {
		autoCompleteTextView.setText(url);
		}
            System.out.print("onPageStarted1: " + url);
            // TODO Auto-generated method stub
            // Toast.makeText(mContext, "page started: " + url, Toast.LENGTH_LONG).show();
            CookieSyncManager.getInstance().sync();
          //  super.onPageStarted(view, url, favicon);
            
        }
        @Override
        public void onReceivedHttpAuthRequest(android.webkit.WebView view, android.webkit.HttpAuthHandler handler, java.lang.String host, java.lang.String realm)  {

            // TODO Auto-generated method stub
            // Toast.makeText(mContext, "page started: " + url, Toast.LENGTH_LONG).show();
            CookieSyncManager.getInstance().sync();
            System.out.print("onReceivedHttpAuthRequest: " + host);
          //    super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            //  showDaUrlToast(url);
            //  Toast.makeText(mContext, "page finished: " + url, Toast.LENGTH_LONG).show();
            // TODO Auto-generated method stub
            System.out.print("onPageFinished: " + url);
            CookieSyncManager.getInstance().sync();
          // super.onPageFinished(view, url);
            // pumpToUrlString();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String theUrl) {
            return false;
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError errer) {
            System.out.print("onReceivedSslError: " + errer);
            handler.proceed() ;
        }
 

   
     }








    private class UtilWChromeClient extends WebChromeClient {

        private Bitmap      mDefaultVideoPoster;
        private View        mVideoProgressView;

 

        @Override
        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            System.out.println("onConsoleMessage: " + message + " -- From line "
                    + String.valueOf(lineNumber) + " of "
                    + sourceID);
                    super.onConsoleMessage(message, lineNumber, sourceID);
        }
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                System.out.println("+-------------------------------");
                System.out.println("|WebChromeClient onJsAlert	" + message);
                System.out.println("+-------------------------------");
                result.confirm();
		    
		    Toast.makeText(mContext, "Alert... " + message, Toast.LENGTH_LONG).show();
                return true;
            }





        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
        {

                System.out.println("onShowCstmView: ");

            UtilWebView.this.setVisibility(View.GONE);

            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            mCustomViewContainer.addView(view);
            mCustomView = view;
            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
        }

        @Override
        public void onHideCustomView() {
            System.out.println("customview hideeeeeeeeeeeeeeeeeeeeeeeeeee");
            if (mCustomView == null)
                return;        

            // Hide the custom view.
            mCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            mCustomViewContainer.removeView(mCustomView);
            mCustomView = null;
            mCustomViewContainer.setVisibility(View.GONE);
            mCustomViewCallback.onCustomViewHidden();

            UtilWebView.this.setVisibility(View.VISIBLE);
            UtilWebView.this.goBack();
            //Log.i(LOGTAG, "set it to webVew");
        }


        @Override
        public View getVideoLoadingProgressView() {
            //Log.i(LOGTAG, "here in on getVideoLoadingPregressView");
                System.out.println("getVideoLoadingProgressView: ");

            if (mVideoProgressView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                mVideoProgressView = inflater.inflate(R.layout.com_njfsoft_utils_utilwebview_video_loading_progress, null);
            }
            return mVideoProgressView; 
        }

         @Override
         public void onReceivedTitle(WebView view, String title) {
            ((Activity) mContext).setTitle(title);
         }

         @Override
         public void onProgressChanged(WebView view, int newProgress) {
             ((Activity) mContext).getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress*100);
         }

         @Override
         public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
             callback.invoke(origin, true, false);
         }
 
    }



public void setToggleWVBtns() {


		spactivity.runOnUiThread(new Runnable() {
				                   public void run() {
				try {
        if(panBtnsWebView.isShown()) {
	  panBtnsWebView.setVisibility(View.GONE);

 	  } else {
	  panBtnsWebView.setVisibility(View.VISIBLE);
	  }

		} catch(Exception e) {
            System.out.println("dev:ERROR:setToggleWView: " + e);
		}
				                   }
			                   });

	}



public void setToggleWViewBtns(final boolean boolWBShow, final boolean boolSDShow) {


		spactivity.runOnUiThread(new Runnable() {
				                   public void run() {
				try {
        if(boolWBShow) {
	  panBtnsWebView.setVisibility(View.VISIBLE);
        if(boolSDShow) {
                    slidingDrawer.open();
        }
 	  } else {
	  panBtnsWebView.setVisibility(View.GONE);
	  }

		} catch(Exception e) {
            System.out.println("dev:ERROR:setSendSMSComm: " + e);
		}
				                   }
			                   });

	}










/*







 

*/







    public class CustomAdapater extends ArrayAdapter implements Filterable {
        private ArrayList<String> mData;
        private final ArrayList<String> orig;
        private final ArrayList<String> suggestions;
        final LayoutInflater inflater;

        @SuppressWarnings("unchecked")
        public CustomAdapater(Context context, ArrayList<String> al) {
            super(context, R.layout.com_njfsoft_utils_utilwebview_list_item, al);
            inflater = LayoutInflater.from(context);
            mData = al;
            this.add(mData);
            orig = (ArrayList<String>) mData.clone();
            this.suggestions = new ArrayList<String>();
        }

        @Override
        public int getCount() {
            return suggestions.size();
        }

        @Override
        public Object getItem(int position) {
            try {
                return suggestions.get(position);
            } catch (Exception e) {
                System.out.println("CustomAdapater getItem(): " + e.toString());
                return suggestions.get(suggestions.size());
            }
        }

        @Override
        public Filter getFilter() {
            Filter myFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        suggestions.clear();
                        for (String s : orig) {
                            if (s != null && s.contains(constraint))
                                suggestions.add(s);
                        }
                        filterResults.values = suggestions;
                        filterResults.count = suggestions.size();
                    }
                    return filterResults;
                }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence contraint, FilterResults results) {
                    mData = (ArrayList<String>) results.values;
                    notifyDataSetChanged();
                }
            };
            return myFilter;
        }
    }



    public ArrayList<String> getHistArrayList() {

        ArrayList<String> fullLstring = new ArrayList<String>();
        System.out.println("getCurrHistory new string:");

        int mCurCount = 0;
        String strLastUrl = "";
        String regex = "^(ww[a-zA-Z0-9-]{0,}\\.)";

        Cursor mCur = spactivity.managedQuery(Browser.BOOKMARKS_URI, Browser.HISTORY_PROJECTION, null, null, "URL");
        mCur.moveToFirst();
        if (mCur.moveToFirst() && mCur.getCount() > 0) {

            while (!mCur.isAfterLast()) {
                String strTmpTtl = mCur.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX);
                String strTmpUrl = mCur.getString(Browser.HISTORY_PROJECTION_URL_INDEX);

                try {

                    if (strTmpUrl.contains("?")) {
                        strTmpUrl = strTmpUrl.substring(0, strTmpUrl.indexOf("?"));
                    }


                    if ((strTmpTtl.length() < 1) || (strTmpTtl.toLowerCase().startsWith("http"))) {
                        URL myUrl = new URL(strTmpUrl);


                        strTmpTtl = myUrl.getHost();
                        System.out.println("strTmpTtl clean: " + strTmpTtl);
                        // strTmpTtl = strTmpUrl;
                    }

                    // System.out.println("strTmpTtl[" + mCurCount + "]: " + strTmpTtl);
                    // System.out.println("strTmpUrl[" + mCurCount + "]: " + strTmpUrl);
                    if (strTmpUrl.equals(strLastUrl)) {
                    // System.out.println("strTmpUrl Duplicat: " + strTmpUrl);
                    } else {
                        System.out.println("strTmpUrl clean: " + strTmpUrl);
                        strTmpUrl = strTmpUrl.replace(",", "");
                        fullLstring.add(strTmpUrl);


                    }

                } catch (Exception e) {
                    System.out.println("mcur: " + e.toString());
                }

                strLastUrl = strTmpUrl;
                mCurCount++;
                strTmpTtl = null;
                strTmpUrl = null;
                mCur.moveToNext();
            }


        }
        // System.out.println("fullLstring: " + fullLstring);
        return fullLstring;
    }


    void setHistoryLinks() {
        autoCompleteTextView.setAdapter(new CustomAdapater(spactivity, getHistArrayList()));
    }

 


}