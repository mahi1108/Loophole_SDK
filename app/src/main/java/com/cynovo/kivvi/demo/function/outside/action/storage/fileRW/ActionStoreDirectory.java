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

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by joycewu on 2017-10-26.
 * 存储文件夹
 */

public class ActionStoreDirectory extends BaseAction {
    KivviDevice kvDev;
    String msg="";
    int count = 0;
    private File[] files;
    private String path;

    public ActionStoreDirectory() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionStorage)+"|"+GetString.getString(KivviApplication.getContext(),R.string.fileRW)+"|"+GetString.getString(KivviApplication.getContext(),R.string.directory_store)+"-7");
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
        count=0;
        path = Environment.getExternalStorageDirectory().getPath()+"/KernelTest/";
        File file = new File(path);
        if(file.exists()){
            files = file.listFiles();
            if(files.length>0){
                storeFile();
            }else{
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.file_not_exist));
            }
        }else{
            setMsg(GetString.getString(KivviApplication.getContext(),R.string.file_not_exist));
        }
    }

     private void storeFile(){
         kvDev = new KivviDevice();
         try {
             String fileName = files[count].getName();
             kvDev.Set(KV.KEY.STORE_FILE.REQUIRE.FILE_NAME,  files[count].getName());
             Log.i("====Demo","count==="+count+"name==="+files[count].getName());
             kvDev.Set(KV.KEY.STORE_FILE.REQUIRE.PREFIX, "emv"); //when you store the emv scripts, you must add this params.
             kvDev.Set(KV.KEY.STORE_FILE.REQUIRE.FILE_PATH,  path+files[count].getName());
             int ret = kvDev.Action(KV.CMD.STORE_FILE, new KivviDeviceRespListener() {
                 @Override
                 public void onResponse(KivviDeviceResp kivviDeviceResp) {
                     Log.i("===========file store",kivviDeviceResp.ErrCode+"===count===="+count);
                     try {
                         if (kivviDeviceResp.ErrCode == KV.RET.OK) {
                             count++;
                             //count没有读取完
                             if(count<files.length){
                                    storeFile();
                             }else {
                                 msg =GetString.getString(KivviApplication.getContext(),R.string.file_store_success);
                                 Message message = Message.obtain();
                                 message.obj = msg;
                                 handler.sendMessage(message);
                                 for (int i = 0; i <files.length ; i++) {
                                     files[i].delete();
                                 }
                             }
                         } else {
                             msg =GetString.getString(KivviApplication.getContext(),R.string.file_store_fail)+GetString.getString(KivviApplication.getContext(),R.string.error_is) + kivviDeviceResp.ErrCode + kvDev.GetString(KV.KEY.BASIC.RESULT);
                             Message message = Message.obtain();
                             message.obj = msg;
                             handler.sendMessage(message);
                         }
                     }catch (KvException e){
                         msg =e.getMessage();
                         Message message = Message.obtain();
                         message.obj = msg;
                         handler.sendMessage(message);
                     }

                 }
             });
         }catch (KvException e){
             msg =e.getMessage();
             Message message = Message.obtain();
             message.obj = msg;
             handler.sendMessage(message);
         }
     }

}

