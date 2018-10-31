package com.cynovo.kivvi.demo.function.outside.action.mifare;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;
import com.cynovo.kivvidevicessdk.Utils.F;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 */
public class ActionMifareRead extends BaseAction {

    public ActionMifareRead() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionMifare)+"|"+GetString.getString(KivviApplication.getContext(),R.string.MifareRead)+"-3");
    }

    @Override
    public void myAction(String actionName) {
        KivviDevice kvDev = new KivviDevice();

        try {
            kvDev.Set(KV.KEY.MIFARE.REQUIRE.OPERATE, "READ");
            kvDev.Set(KV.KEY.MIFARE.REQUIRE.MIFARE_BLOCK, 9);

            int ret = kvDev.Action(KV.CMD.MIFARE);
            if(ret == KV.RET.OK){
                byte[] data = kvDev.GetByteArray(KV.KEY.MIFARE.RESPONSE.DATA);
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.MifareRead_succeed)+ F.ByteArrayToHexString(data));
            }else{
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
