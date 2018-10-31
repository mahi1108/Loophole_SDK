package com.cynovo.kivvi.demo.function.outside.action.storage.fileRW;

import android.os.Message;

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
public class ActionReadFile extends BaseAction {
    KivviDevice kvDev;
    String msg="";

    public ActionReadFile() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionStorage)+"|"+GetString.getString(KivviApplication.getContext(),R.string.fileRW)+"|"+GetString.getString(KivviApplication.getContext(),R.string.file_read)+"-3");
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
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.file_reading));
        kvDev = new KivviDevice();
        try {
            byte[] fileData = new byte[8000];

            kvDev.Set(KV.KEY.READ_FILE.REQUIRE.FILE_NAME,  "ActionStoreFile");
            int ret = kvDev.Action(KV.CMD.READ_FILE, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    try {
                        if (kivviDeviceResp.ErrCode == KV.RET.OK) {
                            byte[] fileData = kvDev.GetByteArray(KV.KEY.READ_FILE.RESPONSE.FILE_DATA);
                            msg =GetString.getString(KivviApplication.getContext(),R.string.Read_Success)+GetString.getString(KivviApplication.getContext(),R.string.Length_is) + fileData.length;

                        } else {
                            msg =GetString.getString(KivviApplication.getContext(),R.string.Read_Error)+","+GetString.getString(KivviApplication.getContext(),R.string.error_is) + kivviDeviceResp.ErrCode + kvDev.GetString(KV.KEY.BASIC.RESULT);

                        }
                    }catch (KvException e){
                        msg = e.getMessage();
                    }
                    Message message = Message.obtain();
                    message.obj = msg;
                    handler.sendMessage(message);
                }
            });
        }catch (KvException e){
            msg = e.getMessage();
            Message message = Message.obtain();
            message.obj = msg;
            handler.sendMessage(message);
        }

    }
}
