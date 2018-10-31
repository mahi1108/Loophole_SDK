package com.cynovo.kivvi.demo.base;

import android.content.Context;
import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import dalvik.system.DexFile;

/**
 * Created by cynovo on 2016/6/3.
 * 在这个类里去初始化所有的action类
 */
public class InitAllFunctions {
    public static String packageName = KivviApplication.getInstance().getPackageName()+".function.";
   public static void init(Context context){
       if(Build.VERSION.SDK_INT >=23){
           initAction_SDK23(context);
       }
       else {
           initAction(context);
       }

   }

    private static void initAction(Context context) {
        try {
            DexFile df = new DexFile(context.getPackageCodePath());
            for (Enumeration<String> iter = df.entries(); iter
                    .hasMoreElements(); ) {
                String s = iter.nextElement();
                if (s.startsWith(packageName)) {
                    try {
                        Class<?> clazz = Class.forName(s);
                        try {
                            clazz.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BaseAction.executeMenuRelation();
    }

    private static void initAction_SDK23(Context context){
        try {
            String baseapkpath= context.getPackageCodePath();
            String dir =baseapkpath.substring(0, baseapkpath.lastIndexOf("/"));
            File dirfile = new File(dir);
            String[] filelist = dirfile.list();
            for (String filename:filelist) {
                if(filename.endsWith(".apk")){
                    DexFile df = new DexFile(dir+File.separator + filename);
                    for (Enumeration<String> iter = df.entries(); iter.hasMoreElements(); ) {
                        String s = iter.nextElement();
                        if (s.startsWith(packageName)) {
                            try {
                                Class<?> clazz = Class.forName(s);
                                try {
                                    clazz.newInstance();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BaseAction.executeMenuRelation();
    }

}
