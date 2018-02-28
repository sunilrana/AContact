
package com.sunilrana.acontact.permission;

import android.app.Activity;
import android.content.pm.PackageManager;

import com.sunilrana.acontact.utils.Utils;


class ActivityPermissionActionImpl implements PermissionAction {

    private Activity activity;

    public ActivityPermissionActionImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean hasSelfPermission(String permission) {
        if (!Utils.isMarshmallowOrHigher()) {
            return true;
        }
        return activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestPermission(String permission, int requestCode) {
        activity.requestPermissions(new String[] { permission }, requestCode);
    }

/*    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return activity.shouldShowRequestPermissionRationale(permission);
    }*/
}
