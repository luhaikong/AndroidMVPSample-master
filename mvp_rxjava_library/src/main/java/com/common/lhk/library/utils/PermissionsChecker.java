package com.common.lhk.library.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by MyPC on 2018/5/24.
 * 权限检查工具类
 */

public class PermissionsChecker {

    /**
     * 判断权限集合
     */
    public static boolean lacksPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(context,permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限
     * @param context
     * @param permission
     * @return
     */
    private static boolean lacksPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }
}
