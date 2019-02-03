<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
android:orientation="horizontal" android:layout_width="fill_parent"
android:layout_height="fill_parent" android:background="@color/background">
<ImageView android:layout_width="25px" android:layout_height="25px"
    android:src="@drawable/icon"></ImageView>
<TextView android:id="@+id/customtitlebar"
    android:layout_width="wrap_content" android:layout_height="fill_parent"
    android:text="" android:textColor="@color/result_text" android:textStyle="bold"
    android:background="@color/background" android:padding="3px" />
</LinearLayout>