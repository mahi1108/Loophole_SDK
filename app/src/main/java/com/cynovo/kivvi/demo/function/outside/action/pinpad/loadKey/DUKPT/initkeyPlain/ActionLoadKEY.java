package com.cynovo.kivvi.demo.function.outside.action.pinpad.loadKey.DUKPT.initkeyPlain;

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
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.LoadKey)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.DUKPT)+"|"+GetString.getString(KivviApplication.getContext(),R.string.initkeyPlain)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.Load_init_key)+"-1");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDevPinpad = new KivviDevice();

        String dbgDMK = "6AC292FAA1315B4D858AB3A3D7D5933A";  //ANSI X9.24-1-2009  Page 54 A.4 DUKPT Test Data Examples: Initially Loaded PIN Entry Device Key
        String dbgKSN = "FFFF9876543210E00000";  //ANSI X9.24-1-2009  Page 54 A.4 DUKPT Test Data Examples: Initially Loaded Key Serial Number (KSN)
        try {
            // 输入密码时所用的APPID，默认为1，请按自己的实际使用情况填
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_APP_ID, PinAppId);

            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_MANAGER, "DUKPT");

            //密钥类型，可选值有： "KPK"（传输密钥）、"MASTER_KEY"（主密钥）、"PIN_KEY"（工作密钥PIK）、"MAC_KEY"(工作密钥MAK)、"TD_KEY"(工作密钥)
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_TYPE, "INIT_KEY");

            //密钥数据格式，可选值有："plain"（明文）、"EnByKPK"（由KPK加密）这里为明文
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_FORMAT, "plain");

            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_DATA, F.HexStringToByteArray(dbgDMK));
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KSN, F.HexStringToByteArray(dbgKSN));

            int ret = kvDevPinpad.Action(KV.CMD.PINPAD_LOAD_KEY);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.success));
            }else{
                String errMsg = "加载密钥失败，" + ret + "\n";
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
