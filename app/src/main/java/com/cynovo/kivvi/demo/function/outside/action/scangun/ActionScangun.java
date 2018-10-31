package com.cynovo.kivvi.demo.function.outside.action.scangun;

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
 * Created by eddard on 17-2-13.
 */

public class ActionScangun extends BaseAction {

    public ActionScangun() {
        super(GetString.getString(KivviApplication.getContext(),R.string.scangun)+"|"+GetString.getString(KivviApplication.getContext(),R.string.scangunscan)+"-1");
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
    public void myAction(final String actionName) {
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.start_scangun));
        final KivviDevice kvDev = new KivviDevice();
        try {
            int ret = kvDev.Action("scangun.cmd.scangunscan",new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp)
                {
                    try {
                        showMessage(kvDev.GetString(KV.KEY.BASIC.RESULT));
                    } catch (KvException e) {
                        e.printStackTrace();
                    }
                }
            });
            if(ret == KV.RET.PROCESSING){
                showMessage("processing...");
            }
            else{
                showMessage(kvDev.GetString(KV.KEY.BASIC.RESULT));
            }

        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}