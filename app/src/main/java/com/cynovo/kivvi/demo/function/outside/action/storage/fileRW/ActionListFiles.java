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
 * Created by Administrator on 2017/11/14.
 */
public class ActionListFiles extends BaseAction{

    KivviDevice kvDev;
     String msg="";

    public ActionListFiles() {
        super(GetString.getString(KivviApplication.getContext(), R.string.actionStorage)+"|"
                  + GetString.getString(KivviApplication.getContext(), R.string.fileRW)+"|"
                  + GetString.getString(KivviApplication.getContext(), R.string.list_files)+"-9");
    }
    private android.os.Handler handler = new android.os.Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message msg) {
            setMsg((String)msg.obj);
        }
    };
        @Override
        public void myAction (String actionName){
            setMsg("List the file Names, Please wait ...");
            kvDev = new KivviDevice();
            try {
                kvDev.Set(KV.KEY.LIST_FILES.REQUIRE.OFFSET, 0);  //the offset can be 0 to 7,  different offset represents different store areas.
                kvDev.Action(KV.CMD.LIST_FILES, new KivviDeviceRespListener() {
                    @Override
                    public void onResponse(KivviDeviceResp kivviDeviceResp) {
                        String fileInfo = "";
                        Message message = new Message();
                        try {
                            if (kivviDeviceResp.ErrCode == KV.RET.OK) {
                                String fileNames = kvDev.GetString(KV.KEY.LIST_FILES.RESPONSE.FILE_NAME);
                                fileInfo+="File names： "+fileNames + "\n";

                                int fileNum = kvDev.GetInt(KV.KEY.LIST_FILES.RESPONSE.FILE_NUM);
                                fileInfo+= "File Number：   "+fileNum + "\n";

                                message.obj =fileInfo+ " List files' name Success";
                                handler.sendMessage(message);
                            } else {
                                message.obj =  "List files' name failed ！errorCode：" + kivviDeviceResp.ErrCode + "error description：" + kvDev.GetString(KV.KEY.BASIC.RESULT);
                                handler.sendMessage(message);
                                }
                        } catch (KvException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (KvException e) {
                e.printStackTrace();
            }

        }
    }



