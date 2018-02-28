package com.sunilrana.acontact.utils;

import android.os.Build;

/**
 * Created by sunilrana on 28/02/18.
 */

public class Utils {

    public static boolean isMarshmallowOrHigher() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false;
        } else {
            return true;
        }
    }
}
