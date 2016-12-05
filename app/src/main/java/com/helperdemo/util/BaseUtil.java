package com.helperdemo.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by whm on 16/12/5.
 */

public class BaseUtil {

    public static List<Map<String,Object>> getAppList(Context context) {
        List<Map<String, Object>> listItems = new ArrayList<>();
        List<PackageInfo> packageinfo;

        // 获取系统内的所有程序信息
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        packageinfo = context.getPackageManager().getInstalledPackages(0);

        Collections.sort(packageinfo, new Comparator<PackageInfo>() {
            @Override
            public int compare(PackageInfo o1, PackageInfo o2) {
                return o1.packageName.compareTo(o2.packageName);
            }
        });

        int count = packageinfo.size();
        for(int i = 0; i < count; i++){

            PackageInfo pinfo = packageinfo.get(i);
            ApplicationInfo appInfo = pinfo.applicationInfo;
            if (appInfo.packageName.equals("com.helperdemo")) {
                continue;
            }

            if((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0)
            {
                //系统程序 忽略
            } else {
                //非系统程序
                Map<String, Object> map = new HashMap<>();
                map.put("app_logo", appInfo.loadIcon(context.getPackageManager()));
                map.put("app_name", appInfo.loadLabel(context.getPackageManager()));
                map.put("app_version_name", pinfo.versionName);
                map.put("package_name", appInfo.packageName);
                listItems.add(map);
            }
        }
        return listItems;
    }
}
