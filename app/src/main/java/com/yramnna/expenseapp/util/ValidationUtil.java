package com.yramnna.expenseapp.util;

import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Rufo on 8/3/2016.
 */
public class ValidationUtil {

    private static final String EMPTY_STRING = "";
    private static final String ERROR_EMPTY = "%1$s must not be empty!";
    private static final String ERROR_INVALID = "%1$s is invalid!";

    public static boolean validateIsEmpty(EditText editText) {
        boolean isEmpty = StringUtils.isEmpty(editText.getText());
        if (isEmpty) {
            editText.setError(String.format(ERROR_EMPTY, "PassCode"));
        }

        return isEmpty;
    }

    public static boolean validateIsEqual(EditText editText, String valueToCompare) {
        boolean isEqual = StringUtils.equals(editText.getText(), valueToCompare);
        if (!isEqual) {
            editText.setError(String.format(ERROR_INVALID, "PassCode"));
            editText.setText(EMPTY_STRING);
        }

        return isEqual;
    }
}
