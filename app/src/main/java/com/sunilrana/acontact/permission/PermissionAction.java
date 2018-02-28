
package com.sunilrana.acontact.permission;


public interface PermissionAction {

    boolean hasSelfPermission(String permission);

    void requestPermission(String permission, int requestCode);

  //  boolean shouldShowRequestPermissionRationale(String permission);
}
