package com.cynovo.kivvi.demo.function.outside.action.printer;

import android.os.Handler;
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
 * Created by eddard on 17-4-17.
 */

public class ActionPrintBusy extends BaseAction {

    public ActionPrintBusy(){
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.Busy)+"-10");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            setMsg(message.obj.toString());
        }
    };

    private void showMessage(String info){
        Message msg = new Message();
        msg.obj= info;
        handler.sendMessage(msg);
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDev = new KivviDevice();
        try{
            kvDev.Action(KV.CMD.PRINTER_BUSY, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    try {
                        int ret = kvDev.GetInt("ret");
                        if(ret != KV.RET.OK){
                            showMessage(kvDev.GetString(KV.KEY.BASIC.RESULT));
                        }
                        else{
                            showMessage(GetString.getString(KivviApplication.getContext(),R.string.print_over));
                        }
                    } catch (KvException e) {
                        e.printStackTrace();
                    }
                }
            });

        }catch (KvException e){
            setMsg(e.getMessage());
        }
    }
}