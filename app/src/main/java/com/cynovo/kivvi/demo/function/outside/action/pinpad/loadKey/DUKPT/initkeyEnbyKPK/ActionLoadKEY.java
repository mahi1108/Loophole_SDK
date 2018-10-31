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
 * authorize
 */
public class ActionLoadKEY extends BaseAction {
    public static boolean DUKPTloaded = false;

    public ActionLoadKEY() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.LoadKey)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.DUKPT)+"|"+GetString.getString(KivviApplication.getContext(),R.string.initkeyenbykpk)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.Load_init_key)+"-2");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDevPinpad = new KivviDevice();

//          String dbgDMK = "E2EBA521A03142E557F57081B6EFDA35";
//          String dbgKSN = "FFFFF33AE04CB30";
        String dbgDMK = "3FEB69AD37E628F7C3091FB4CF2734C8";
        String dbgKSN = "FFFFF43AE04CB3000001";
//        String dbgDMK = "6AC7B406116C3B186D28AB211A753160";
//        String dbgKSN = "000002016E36584";
        try {
            // 输入密码时所用的APPID，默认为1，请按自己的实际使用情况填
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_APP_ID, PinAppId);

            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_MANAGER, "DUKPT");

            //密钥类型，可选值有： "KPK"（传输密钥）、"MASTER_KEY"（主密钥）、"PIN_KEY"（工作密钥PIK）、"MAC_KEY"(工作密钥MAK)、"TD_KEY"(工作密钥)
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_TYPE, "INIT_KEY");

            //密钥数据格式，可选值有："plain"（明文）、"EnByKPK"（由KPK加密）这里为KPK加密
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_FORMAT, "EnByKPK");
//            kvDevPinpad.Set(KV.KEY.KEY_FORMAT, "plain");

            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_DATA, F.HexStringToByteArray(dbgDMK));
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KSN, F.HexStringToByteArray(dbgKSN));

            int ret = kvDevPinpad.Action(KV.CMD.PINPAD_LOAD_KEY);
            if(ret == KV.RET.OK){
                setMsg("(AppID = 2)" + GetString.getString(KivviApplication.getContext(),R.string.success));
            }else{
                String errMsg = "(AppID = 2)加载密钥失败，" + ret + "\n";
                try {
                    errMsg += GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDevPinpad.GetString(KV.KEY.BASIC.RESULT);
                } catch (KvException e) {
                    e.printStackTrace();
                } finally {
                    setMsg(errMsg);
                }
            }
            DUKPTloaded = true;
        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
