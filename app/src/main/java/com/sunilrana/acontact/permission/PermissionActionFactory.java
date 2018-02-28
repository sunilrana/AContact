
package com.sunilrana.acontact.permission;

import android.app.Activity;


public class PermissionActionFactory {

    public static final int ACTIVITY_IMPL = 1;
    public static final int SUPPORT_IMPL = 2;

    private Activity activity;

    public PermissionActionFactory(Activity activity) {
        this.activity = activity;
    }

    public PermissionAction getPermissionAction(int type) {
        if (type == SUPPORT_IMPL) {
            return new SupportPermissionActionImpl(activity);
        } else {
            return new ActivityPermissionActionImpl(activity);
        }
    }
}
