package com.yramnna.expenseapp.core;

import android.app.Application;
import android.content.Context;

import com.yramnna.expenseapp.db.ExpenseDbReader;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * Created by Rufo on 7/31/2016.
 */
public class ExpenseApplication extends Application {

    private static ExpenseApplication sInstance;
    private static boolean activityVisible;
    private static char[] defaultValue = Character.toChars(0x0039);

    private Properties properties;
    private final String filename = "application.properties";

    private Context currentContext;
    private ExpenseDbReader dbReader;


    public synchronized static ExpenseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        dbReader = ExpenseDbReader.getInstance(getBaseContext());
    }

    public Context getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    public ExpenseDbReader getDbReader() {
        return dbReader;
    }

    public void setDbReader(ExpenseDbReader dbReader) {
        this.dbReader = dbReader;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    public String getDefault() {
        return StringUtils.repeat(String.valueOf(defaultValue), "", 6);
    }
}
