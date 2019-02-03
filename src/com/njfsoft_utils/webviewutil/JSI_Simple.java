package com.njfsoft_utils.webviewutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
 
 
/**
 * Created by IntelliJ IDEA.
 * User: boss
 * Date: 25-11-2012
 * Time: 0:20
 * To change this template use File | Settings | File Templates.
 */


public class JSI_Simple  {

    Activity main;
    public JSI_Simple(Activity amain) {
    main = amain;

    }
    public void doTest(String s) {
    System.out.println("jsi doTest: " + s);

    // main.doTest(s);
    }
}
