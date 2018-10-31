package com.cynovo.kivvi.demo.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import java.util.ArrayList;

import cynovo.com.sdktool.utils.ACache;
import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/5/30.
 */
public class KivviApplication extends Application {
    private static final String TAG = KivviApplication.class.getName();
    private static Context sContext;
    private static KivviApplication mInstance = null; // 实例化对象
    public static ArrayList<String> msgData = new ArrayList<String>(); //用一個全局的數組保存操作命令信息
    public static ACache mCache;
    private static KivviDevice mDevice=null;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        mInstance = this;
        mCache = ACache.get(this);
        initDevice();
        InitAllFunctions.init(sContext);
    }

    public static KivviApplication getInstance() {
        return mInstance;
    }

    public static Context getContext() {
        return sContext;
    }

    public void initDevice(){
        //开记底层Log
        String msg ="";
        KivviDevice kvDev = new KivviDevice();
        try {
            int ret = kvDev.Action(KV.CMD.GET_VERSION);
            if (ret == KV.RET.OK) {
                String platform = kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.KVDEV_PLATFORM);
                msg = GetString.getString(KivviApplication.getContext(),R.string.main_devices_info);
                msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.serial_number) + kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.SERIAL_NO);
                msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.hardware_version) + kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.HW_VERSION);
                msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.SDK_version) + kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.SDK_VERSION);
                try {
                    msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.app_version) + getVersionName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.KivviDevice_version) + kvDev.GetEnvironment(KV.KEY.GET_VERSION.RESPONSE.KVDEV_VERSION);
                msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.KivviDevice_platform) + platform;
                if (platform.equals("PAR6")) {
                    msg += "\n" + "\n" + GetString.getString(KivviApplication.getContext(),R.string.financial_module_info);
                    msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.serial_number) + kvDev.GetString(KV.KEY.GET_VERSION.RESPONSE.KIVVI_SERIAL_NO);
                    msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.hardware_version) + kvDev.GetString(KV.KEY.GET_VERSION.RESPONSE.KIVVI_HW_VERSION);
                    msg += "\n" + GetString.getString(KivviApplication.getContext(),R.string.firmware_version) + kvDev.GetString(KV.KEY.GET_VERSION.RESPONSE.FW_VERSION);
                }
            }else {
                msg = GetString.getString(KivviApplication.getContext(),R.string.fail_to_connect_to_pos) + ret +kvDev.GetString(KV.KEY.BASIC.RESULT);
            }
        }catch (KvException e){
            msgData.add(e.getMessage());
        }
        msgData.add(msg);
    }

    private String getVersionName() throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }
}
