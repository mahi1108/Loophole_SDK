package com.cynovo.kivvi.demo.function.outside.action.mifare;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 */
public class ActionMifareIncrement extends BaseAction {

    public ActionMifareIncrement() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionMifare)+"|"+GetString.getString(KivviApplication.getContext(),R.string.MifareIncrement)+"-5");
    }

    @Override
    public void myAction(String actionName) {
        KivviDevice kvDev = new KivviDevice();

        try {
            kvDev.Set(KV.KEY.MIFARE.REQUIRE.OPERATE, "INCREASE");
            kvDev.Set(KV.KEY.MIFARE.REQUIRE.MIFARE_BLOCK, 9);
            kvDev.Set(KV.KEY.MIFARE.REQUIRE.MIFARE_NUM, 1);        //Increase:1

            int ret = kvDev.Action(KV.CMD.MIFARE);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.Increment_succeed));

            }else{
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
