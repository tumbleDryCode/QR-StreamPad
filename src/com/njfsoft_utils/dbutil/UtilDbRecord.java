package com.njfsoft_utils.dbutil;

/**
 * Created by IntelliJ IDEA.
 * User: boss
 * Date: 16-03-2014
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
public class UtilDbRecord {
    String tagValA;

    public UtilDbRecord() {
    }

    int keyRecID;
    int keyRecType;
    String keyRecValA;
    String keyRecValB;
    String keyRecValC;
    String keyRecDateAdded;
    String keyRecDateModified;

    public int getKeyRecID() {
        return keyRecID;
    }

    public int getKeyRecType() {
        return keyRecType;
    }

    public String getKeyRecValA() {
        return keyRecValA;
    }

    public String getKeyRecValB() {
        return keyRecValB;
    }

    public String getKeyRecValC() {
        return keyRecValC;
    }

    public String getKeyRecDateAdded() {
        return keyRecDateAdded;
    }

    public String getKeyRecDateModified() {
        return keyRecDateModified;
    }

    public void setKeyRecID(int theVal) {
        this.keyRecID = theVal;
    }

    public void setKeyRecType(int theVal) {
        this.keyRecType = theVal;
    }

    public void setKeyRecValA(String theVal) {
        this.keyRecValA = theVal;
    }

    public void setKeyRecValB(String theVal) {
        this.keyRecValB = theVal;
    }

    public void setKeyRecValC(String theVal) {
        this.keyRecValC = theVal;
    }

    public void setKeyRecDateAdded(String theVal) {
        this.keyRecDateAdded = theVal;
    }

    public void setKeyRecDateModified(String theVal) {
        this.keyRecDateModified = theVal;
    }

    @Override
    public String toString() {
        return keyRecValB;
    }
}
