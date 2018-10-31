package com.cynovo.kivvi.demo.function.outside.action.storage.fileRW;


import android.os.Message;
import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KivviDeviceResp;
import com.cynovo.kivvidevicessdk.KivviDeviceRespListener;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/1.
 */
public class ActionCheckStoreFile extends BaseAction {
    KivviDevice kvDev;
    String msg="";
    public ActionCheckStoreFile() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionStorage)+"|"+GetString.getString(KivviApplication.getContext(),R.string.fileRW)+"|"+GetString.getString(KivviApplication.getContext(),R.string.check_store_file)+"-4");
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
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.StoreFile)+GetString.getString(KivviApplication.getContext(),R.string.Date_Inputting));
        new Thread(){
            public void run() {
                kvDev = new KivviDevice();
                try {
                    byte[] fileData = new byte[8000];
                    for(int i=0; i<fileData.length; i++)
                    {
                        fileData[i]= 0x23;
                    }

                    kvDev.Set(KV.KEY.STORE_FILE.REQUIRE.FILE_NAME,  "ActionStoreCheckFile");
                    kvDev.Set(KV.KEY.STORE_FILE.REQUIRE.FILE_DATA,  fileData);
                    int ret = kvDev.Action(KV.CMD.STORE_FILE, new KivviDeviceRespListener() {
                        @Override
                        public void onResponse(KivviDeviceResp kivviDeviceResp) {
                            Log.i("===========file store",kivviDeviceResp.ErrCode+"");
                            try {
                                if (kivviDeviceResp.ErrCode == KV.RET.OK) {
                                    msg =GetString.getString(KivviApplication.getContext(),R.string.Store_Success);
                                } else {
                                    msg =GetString.getString(KivviApplication.getContext(),R.string.Store_Fail)+","+GetString.getString(KivviApplication.getContext(),R.string.error_is) + kivviDeviceResp.ErrCode + kvDev.GetString(KV.KEY.BASIC.RESULT);
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
