package com.cynovo.kivvi.demo.function.outside.action.scan;

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
 * Created by eddard on 17-1-23.
 */

public class ActionScangun extends BaseAction {
    private String msg = "";
    private KivviDevice kvDev;

    public ActionScangun() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionScan)+"|"+GetString.getString(KivviApplication.getContext(),R.string.Scancode));
    }


    @Override
    public void myAction(String actionName) {
        kvDev = new KivviDevice();
        Log.v("scan"," P1 ");
        try {
            kvDev.Action("scangun.cmd.scangunscan", new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    int ret = kivviDeviceResp.ErrCode;
                    Log.v("scan"," P5 ret =  " + ret);

                    try {
                        Log.v("scan"," P6  " );
                        if (ret == KV.RET.OK) {

                            msg = "结果:" + kvDev.GetString(KV.KEY.BASIC.RESULT);
                            Log.v("scan"," P7  msg = " + msg );
                        } else {
                            Log.v("scan"," P8  " );
                            msg = "结果:unknown";
                        }
                        Log.v("scan"," P9  " );
                    }catch (KvException e){
                        Log.v("scan"," P10  " );
                        msg = e.getMessage();
                    }
                    Log.v("scan"," P11  msg = " + msg );
                    setMsg(msg);
                }
            });
            Log.v("scan"," P2 ");
        }catch (KvException e){
            msg = e.getMessage();
            Log.v("scan"," P3 ");
            setMsg(msg);
        }
        Log.v("scan"," P4 ");

    }
}
