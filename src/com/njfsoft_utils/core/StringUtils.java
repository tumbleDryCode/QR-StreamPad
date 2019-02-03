package com.njfsoft_utils.core;

import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: boss
 * Date: 13-04-2014
 * Time: 19:38
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    public static String[] readmessTokens(String s, String s1)
    {
        String s2 = s;
        String s3 = s1;
        StringTokenizer stringtokenizer = new StringTokenizer(s2, s3);
        int i = stringtokenizer.countTokens();
        String as[] = new String[i];
        for(int j = 0; j < i; j++)
            as[j] = stringtokenizer.nextToken();

        return as;
    }


    public String replaceString(String s, String s1, String s2)
    {
        String s3 = s;
        if(s3 != null && s3.length() > 0)
        {
            int i = 0;
            do
            {
                int j = s3.indexOf(s1, i);
                if(j == -1)
                    break;
                s3 = s3.substring(0, j) + s2 + s3.substring(j + s1.length());
                i = j + s2.length();
            } while(true);
        }
        return s3;
    }



}
