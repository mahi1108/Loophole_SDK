package com.cynovo.kivvi.demo.function.outside.action.scan;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KivviDeviceResp;
import com.cynovo.kivvidevicessdk.KivviDeviceRespListener;
import com.cynovo.kivvidevicessdk.KvException;

import java.io.UnsupportedEncodingException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016-11-02.
 */

public class ScanAction extends BaseAction {
    private String msg = "";
    private KivviDevice kvDev;

    public ScanAction() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionScan)+"|"+GetString.getString(KivviApplication.getContext(),R.string.ScanQRcode));
    }


    @Override
    public void myAction(String actionName) {
        kvDev = new KivviDevice();
        try {
            kvDev.Action(KV.CMD.SCAN_CODE, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    int ret = kivviDeviceResp.ErrCode;
                    try {
                        if (ret == KV.RET.OK) {

                            byte[] bResult = kvDev.GetByteArray(KV.KEY.BASIC.DATA);
                            String result = null;
                            try {
                                result = new String(bResult, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                                msg= GetString.getString(KivviApplication.getContext(),R.string.Scanning_Result)+result;
                        } else {
                            if(ret == KV.RET.CANCELED){
                                msg =GetString.getString(KivviApplication.getContext(),R.string.Scanning_Failed)+GetString.getString(KivviApplication.getContext(),R.string.User_Cancel);
                            }
                            else if(ret == KV.RET.TIMEOUT){
                                msg =GetString.getString(KivviApplication.getContext(),R.string.Scanning_Failed)+GetString.getString(KivviApplication.getContext(),R.string.User_Cancel);
                            }
                            else{
                            msg =GetString.getString(KivviApplication.getContext(),R.string.Scanning_Failed) + kivviDeviceResp.ErrCode +"  "+kvDev.GetString(KV.KEY.BASIC.RESULT);
                            }
                        }
                    }catch (KvException e){
                        msg = e.getMessage();
                    }
                    setMsg(msg);
                }
            });
        }catch (KvException e){
            msg = e.getMessage();
            setMsg(msg);
        }

    }
}

