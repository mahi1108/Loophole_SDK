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
public class ActionCheckReadFile extends BaseAction {
    KivviDevice kvDev;
    String msg="";

    public ActionCheckReadFile() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionStorage)+"|"+GetString.getString(KivviApplication.getContext(),R.string.fileRW)+"|"+GetString.getString(KivviApplication.getContext(),R.string.check_read_file)+"-5");
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
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.Reading_CheckFile));
        kvDev = new KivviDevice();
        try {

            byte[] fileData = new byte[8000];

            kvDev.Set(KV.KEY.READ_FILE.REQUIRE.FILE_NAME,  "ActionStoreCheckFile");
            int ret = kvDev.Action(KV.CMD.READ_FILE, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    try {
                        int i;
                        int ReadOKFlag=0;
                        if (kivviDeviceResp.ErrCode == KV.RET.OK) {
                            byte[] fileData = kvDev.GetByteArray(KV.KEY.READ_FILE.RESPONSE.FILE_DATA);
                            for(i=0; i<fileData.length; i++)
                            {
                                if(fileData[i] != 0x23)
                                {
                                    ReadOKFlag=1;
                                    break;
                                }
                            }
                            if(ReadOKFlag == 1)
                            {
                                msg =GetString.getString(KivviApplication.getContext(),R.string.ReadCheck_Fail) + fileData[i-3]+fileData[i-2]+fileData[i-1];
                            }else
                            {
                                msg =GetString.getString(KivviApplication.getContext(),R.string.ReadCheck_Success)+GetString.getString(KivviApplication.getContext(),R.string.Length_is) + fileData.length;
                            }

                        } else {
                            msg =GetString.getString(KivviApplication.getContext(),R.string.ReadCheck_Error)+","+GetString.getString(KivviApplication.getContext(),R.string.error_is) + kivviDeviceResp.ErrCode + kvDev.GetString(KV.KEY.BASIC.RESULT);
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
