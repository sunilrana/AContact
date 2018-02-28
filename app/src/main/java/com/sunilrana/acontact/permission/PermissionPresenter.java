
package com.sunilrana.acontact.permission;

import android.content.pm.PackageManager;


public class PermissionPresenter {

    private PermissionAction permissionAction;
    private PermissionCallbacks permissionCallbacks;

    public PermissionPresenter(PermissionAction permissionAction, PermissionCallbacks permissionCallbacks) {
        this.permissionAction = permissionAction;
        this.permissionCallbacks = permissionCallbacks;
    }

    public void requestReadContactsPermission() {
        checkAndRequestPermission(Action.READ_CONTACTS);
    }
/*
    public void requestReadContactsPermissionAfterRationale() {
        requestPermission(Action.READ_CONTACTS);
    }

    public void requestWriteExternalStorangePermission() {
        checkAndRequestPermission(Action.SAVE_IMAGE);
    }

    public void requestWriteExternalStorangePermissionAfterRationale() {
        requestPermission(Action.SAVE_IMAGE);
    }

    public void requestSendSMS() {
        checkAndRequestPermission(Action.SEND_SMS);
    }

    public void requestSendSMSAfterRationale() {
        requestPermission(Action.SEND_SMS);
    }*/

    private void checkAndRequestPermission(Action action) {
        if (permissionAction.hasSelfPermission(action.getPermission())) {
            permissionCallbacks.permissionAccepted(action.getCode());
        } else {
            permissionAction.requestPermission(action.getPermission(), action.getCode());
        }
    }

    private void requestPermission(Action action) {
        permissionAction.requestPermission(action.getPermission(), action.getCode());
    }

    public void checkGrantedPermission(int[] grantResults, int requestCode) {
        if (verifyGrantedPermission(grantResults)) {
            permissionCallbacks.permissionAccepted(requestCode);
        } else {
            permissionCallbacks.permissionDenied(requestCode);
        }
    }

    private boolean verifyGrantedPermission(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public interface PermissionCallbacks {
        void permissionAccepted(@Action.ActionCode int actionCode);

        void permissionDenied(@Action.ActionCode int actionCode);

    }
}
