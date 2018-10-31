package com.cynovo.kivvi.demo.function.outside.action.pinpad.loadKey.MKSK.MK_SK;

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
 * 加载主密钥
 */
public class ActionLoadMkey extends BaseAction {

    public ActionLoadMkey() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.LoadKey)+"|"+GetString.getString(KivviApplication.getContext(),R.string.MKSK)+"|"+GetString.getString(KivviApplication.getContext(),R.string.MK_SK)+"|"+GetString.getString(KivviApplication.getContext(),R.string.load_master_key)+"-2");

    }

    @Override
    public void myAction(String actionName) {
        int ret = 0;

        final KivviDevice kvDevPinpad = new KivviDevice();

        String dbgMK = "4B3ECBE10A5FD02203360D4691FC2F20";


        try {
            // 输入密码时所用的APPID，默认为1，请按自己的实际使用情况填
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_APP_ID, PinAppId);

            //密钥类型，可选值有： "KPK"（传输密钥）、"MASTER_KEY"（主密钥）、"PIN_KEY"（工作密钥PIK）、"MAC_KEY"(工作密钥MAK)、"TD_KEY"(工作密钥)
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_TYPE, "MASTER_KEY");

            //密钥数据格式，可选值有："plain"（明文）、"EnByKPK"（由KPK加密）、"EnByMK"(由主密钥加密)、"TR31"
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_FORMAT, "EnByKPK");

            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_DATA, F.HexStringToByteArray(dbgMK));

            ret = kvDevPinpad.Action(KV.CMD.PINPAD_LOAD_KEY);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.success));
            }else{
                String errMsg = GetString.getString(KivviApplication.getContext(),R.string.download_master_key_fail) + ret + "\n";
                try {
                    errMsg += GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDevPinpad.GetString(KV.KEY.BASIC.RESULT);
                } catch (KvException e) {
                    e.printStackTrace();
                } finally {
                    setMsg(errMsg);
                }
            }
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
