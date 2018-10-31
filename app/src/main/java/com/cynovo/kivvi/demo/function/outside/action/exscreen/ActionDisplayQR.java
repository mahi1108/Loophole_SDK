package com.cynovo.kivvi.demo.function.outside.action.exscreen;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import java.io.UnsupportedEncodingException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 * 显示二维码
 */
public class ActionDisplayQR extends BaseAction {
    public ActionDisplayQR() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionExscreen)+"|"+GetString.getString(KivviApplication.getContext(),R.string.display_QRcode)+"-2");
    }


    @Override
    public void myAction(String actionName) {
        KivviDevice kvDev = new KivviDevice();

        String dbgData = "1234-ABCD哈娃";

        try {
            kvDev.Set(KV.KEY.DISPLAY_QR_CODE.REQUIRE.DATA, dbgData.getBytes("utf-8"));
            kvDev.Set(KV.KEY.DISPLAY_QR_CODE.REQUIRE.PAYTYPE, "ALIPAY");
            int ret = kvDev.Action(KV.CMD.DISPLAY_QR_CODE);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.display_ok));
            }else {
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
