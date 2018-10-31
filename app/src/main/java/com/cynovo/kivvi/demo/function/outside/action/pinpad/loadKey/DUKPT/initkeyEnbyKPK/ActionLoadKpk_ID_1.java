package com.cynovo.kivvi.demo.function.outside.action.pinpad.loadKey.DUKPT.initkeyEnbyKPK;


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
 * 加载传输密钥
 */
public class ActionLoadKpk_ID_1 extends BaseAction {

    public ActionLoadKpk_ID_1() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.LoadKey)+"|"+GetString.getString(KivviApplication.getContext(),R.string.DUKPT)+"|"+GetString.getString(KivviApplication.getContext(),R.string.initkeyenbykpk)+"|"+GetString.getString(KivviApplication.getContext(),R.string.load_kpk)+"(appId1)"+"-6");
    }

    @Override
    public void myAction(String actionName) {
        int ret = 0;

        final KivviDevice kvDevPinpad = new KivviDevice();

        String dbgKPK = "57F83B7597E362A79BA2EC541A6129F4";
        try {
            // 输入密码时所用的APPID，请按自己的实际使用情况填
            // the pin appId
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_APP_ID, PinAppId_1);

            //密钥类型，可选值有： "KPK"（传输密钥）、"MASTER_KEY"（主密钥）、"PIN_KEY"（工作密钥PIK）、"MAC_KEY"(工作密钥MAK)、"TD_KEY"(工作密钥)
            //the key type, optional value: "KPK", "MASTER_KEY", "PIN_KEY", "MAC_KEY", "TD_KEY"
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_TYPE, "KPK");

            //密钥数据格式，可选值有："plain"（明文）、"EnByKPK"（由KPK加密）、"EnByMK"(由主密钥加密)、"TR31"
            //The key format, optional value: "plain" ,"EnByKPK", "EnByMK", "TR31"
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_FORMAT, "plain");

            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_MANAGER, "DUKPT");

            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_DATA, F.HexStringToByteArray(dbgKPK));

            ret = kvDevPinpad.Action(KV.CMD.PINPAD_LOAD_KEY);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.success));
            }else{
                String errMsg = "(AppID = 1)" + GetString.getString(KivviApplication.getContext(),R.string.download_KPK_fail) + ret + "\n";
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
