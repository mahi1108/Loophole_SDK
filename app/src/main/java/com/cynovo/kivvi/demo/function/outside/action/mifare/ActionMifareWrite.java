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
 * Created by cynovo on 2016-06-12.
 */
public class ActionMifareWrite extends BaseAction {

    public ActionMifareWrite() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionMifare)+"|"+GetString.getString(KivviApplication.getContext(),R.string.MifareWrite)+"-4");
    }

    @Override
    public void myAction(String actionName) {
        KivviDevice kvDev = new KivviDevice();

        try {
            kvDev.Set(KV.KEY.MIFARE.REQUIRE.OPERATE, "WRITE");
            kvDev.Set(KV.KEY.MIFARE.REQUIRE.MIFARE_BLOCK, 9);
            kvDev.Set(KV.KEY.MIFARE.REQUIRE.DATA, F.HexStringToByteArray("00000000FFFFFFFF0000000009F609F6")); //84D612007829EDFF84D6120011EE11EE

            int ret = kvDev.Action(KV.CMD.MIFARE);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.MifareWrite_Succeed));
            }else{
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
