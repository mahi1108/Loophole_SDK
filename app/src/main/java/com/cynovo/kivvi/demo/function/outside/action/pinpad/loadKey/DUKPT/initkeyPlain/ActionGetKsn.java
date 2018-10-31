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
public class ActionGetKsn extends BaseAction {

    public ActionGetKsn() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.LoadKey)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.DUKPT)+"|"+GetString.getString(KivviApplication.getContext(),R.string.initkeyPlain)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.Read_KSN)+"-2");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDevPinpad = new KivviDevice();

        try {
            // 输入密码时所用的APPID，默认为1，请按自己的实际使用情况填
            kvDevPinpad.Set(KV.KEY.PINPAD_KSN.REQUIRE.KEY_APP_ID, PinAppId);
            kvDevPinpad.Set(KV.KEY.PINPAD_KSN.REQUIRE.OPERATE, "GET");

            int ret = kvDevPinpad.Action(KV.CMD.PINPAD_KSN);
            if(ret == KV.RET.OK){
                byte[] ksn = kvDevPinpad.GetByteArray(KV.KEY.PINPAD_KSN.RESPONSE.KSN);

                setMsg("取KSN成功：" + F.ByteArrayToHexString(ksn));
            }else{
                String errMsg = "读取KSN失败" + ret + "\n";
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
