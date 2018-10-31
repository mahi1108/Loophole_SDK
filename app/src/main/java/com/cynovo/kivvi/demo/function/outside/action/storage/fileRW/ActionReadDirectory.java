package com.cynovo.kivvi.demo.function.outside.action.storage.fileRW;

import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by joycewu on 2017-10-26.
 * 读取文件夹中的A00000000350文件
 */

public class ActionReadDirectory extends BaseAction {
    KivviDevice kvDev;
    String msg="";

    public ActionReadDirectory() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionStorage)+"|"+GetString.getString(KivviApplication.getContext(),R.string.fileRW)+"|"+GetString.getString(KivviApplication.getContext(),R.string.directory_read)+"-8");
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

            kvDev.Set(KV.KEY.READ_FILE.REQUIRE.FILE_NAME,  "A00000000350.cak");
            int ret = kvDev.Action(KV.CMD.READ_FILE, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    try {
                        if (kivviDeviceResp.ErrCode == KV.RET.OK) {
                            byte[] fileData = kvDev.GetByteArray(KV.KEY.READ_FILE.RESPONSE.FILE_DATA);
                           File file = null;
                            String  path = Environment.getExternalStorageDirectory().getPath()+"/KernelTest/";
                            try {
                                file = new File(path);
                                if (!file.exists()) {
                                    file.mkdir();
                                }
                                File cak = new File(path + "A00000000350.cak");
                                if(!cak.exists()){
                                    cak.createNewFile();
                                }
                                FileOutputStream fileOutputStream = new FileOutputStream(cak);
                                fileOutputStream.write(fileData);
                                fileOutputStream.close();
                            } catch (Exception e) {
                                Log.i("directory====:", e+"");
                            }
                            msg =GetString.getString(KivviApplication.getContext(),R.string.Read_Success)+"   A00000000350     "+GetString.getString(KivviApplication.getContext(),R.string.Length_is) + fileData.length;
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

