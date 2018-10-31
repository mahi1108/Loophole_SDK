package com.cynovo.kivvi.demo.function.outside.action.storage;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvi.demo.base.BaseAction;
import cynovo.com.sdktool.utils.GetString;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.File;

/**
 * Created by cynovo on 2016/6/1.
 */
public class ActionStoreLogo extends BaseAction {
    String errMsg = "";
    private android.os.Handler handler = new android.os.Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message msg) {
            int number =msg.getData().getInt("number", -1);
            String msgContent ="";
            if(msg.what == KV.RET.OK){
                msgContent += GetString.getString(KivviApplication.getContext(),R.string.picture_load_ok)  + GetString.getString(KivviApplication.getContext(),R.string.success);
            }else{
                cancelDialog();
                msgContent += GetString.getString(KivviApplication.getContext(),R.string.picture_load_ok)  + GetString.getString(KivviApplication.getContext(),R.string.failure);
            }
            setMsg(msgContent);
        }
    };

    public ActionStoreLogo() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionStorage)+"|"+GetString.getString(KivviApplication.getContext(),R.string.preserve_Logo)+"-2");
    }
    @Override
    public void myAction(String actionName){
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.file_store)+GetString.getString(KivviApplication.getContext(),R.string.save_logo));
        new Thread() {
            public void run() {
                KivviDevice kvDev = new KivviDevice();
                try {
             //       File sd = Environment.getExternalStorageDirectory();
                    String filePath = Environment.getExternalStorageDirectory().getPath()+"/logo.sbin";


                    kvDev.Set(KV.KEY.STORE_BITMAP.REQUIRE.TYPE, "LOGO");
                    kvDev.Set(KV.KEY.STORE_BITMAP.REQUIRE.FILE_PATH, filePath);

                    int ret = kvDev.Action(KV.CMD.STORE_BITMAP);
                    if(ret == KV.RET.OK) {
                        errMsg =GetString.getString(KivviApplication.getContext(),R.string.preserve_Logo_success);
                    }else {
                        errMsg =GetString.getString(KivviApplication.getContext(),R.string.preserve_Loge_fail)+GetString.getString(KivviApplication.getContext(),R.string.error_is) + ret + " | " + kvDev.GetString(KV.KEY.BASIC.RESULT);
                    }
                }catch (KvException e){
                    errMsg= e.getMessage();
                }
                Message message = Message.obtain();
                message.obj = errMsg;
                handler.sendMessage(message);
            }
        }.start();
    }
}
