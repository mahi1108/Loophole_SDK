package com.cynovo.kivvi.demo.function.outside.action.storage.fileRW;

import android.os.Environment;
import android.os.Message;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KivviDeviceResp;
import com.cynovo.kivvidevicessdk.KivviDeviceRespListener;
import com.cynovo.kivvidevicessdk.KvException;

import java.io.File;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/1.
 */
public class ActionStoreFile2 extends BaseAction {
    KivviDevice kvDev;
    String msg="";
    public ActionStoreFile2() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionStorage)+"|"+GetString.getString(KivviApplication.getContext(),R.string.fileRW)+"|"+GetString.getString(KivviApplication.getContext(),R.string.file_store_2)+"-2");
    }
    private android.os.Handler handler = new android.os.Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message msg) {
            setMsg((String)msg.obj);
        }
    };
    @Override
    public void myAction(String actionName) {
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.file_inputting));
        new Thread(){
            public void run() {
                kvDev = new KivviDevice();
                try {
                    byte[] fileData = new byte[8000];
                    File sd = Environment.getExternalStorageDirectory();
                    String filePath = sd.getPath() +"/abc";
                    kvDev.Set(KV.KEY.STORE_FILE.REQUIRE.FILE_NAME,  "ActionStoreFile2");
                    kvDev.Set(KV.KEY.STORE_FILE.REQUIRE.FILE_PATH,  filePath);
                    int ret = kvDev.Action(KV.CMD.STORE_FILE, new KivviDeviceRespListener() {
                        @Override
                        public void onResponse(KivviDeviceResp kivviDeviceResp) {
                            try {
                                if (kivviDeviceResp.ErrCode == KV.RET.OK) {
                                    msg =GetString.getString(KivviApplication.getContext(),R.string.file_store_success);
                                } else {
                                    msg =GetString.getString(KivviApplication.getContext(),R.string.file_store_fail)+GetString.getString(KivviApplication.getContext(),R.string.error_is) + kivviDeviceResp.ErrCode + kvDev.GetString(KV.KEY.BASIC.RESULT);

                                }
                            }catch (KvException e){
                                msg =e.getMessage();
                            }
                            Message message = Message.obtain();
                            message.obj = msg;
                            handler.sendMessage(message);
                        }
                    });

                }catch (KvException e){
                    msg =e.getMessage();
                    Message message = Message.obtain();
                    message.obj = msg;
                    handler.sendMessage(message);
                }
            }
        }.start();

    }
}
