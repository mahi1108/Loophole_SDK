package com.cynovo.kivvi.demo.function.outside.action.pinpad.mac.DUKPT;

import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;
import com.cynovo.kivvidevicessdk.Utils.F;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016-06-13.
 * 计算MAC
 */
public class ActionCaculateMac extends BaseAction {
    public ActionCaculateMac() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad) + "|" + GetString.getString(KivviApplication.getContext(),R.string.Loadkey_mac) + "|" + GetString.getString(KivviApplication.getContext(),R.string.DUKPT) + "|" + GetString.getString(KivviApplication.getContext(),R.string.calculation_MAC) + "-2");
    }

    @Override
    public void myAction(String actionName) {
        int ret = 0;
        String[] macType = {"X99", "ECB", "Unionpay_ECB", "RICOM"};
        String outMacData = null;
        byte[] outMacDataByte = null;
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.calculating_MAC));

        final KivviDevice kvDev = new KivviDevice();

        try {

            for (int str = 0; str < macType.length; str++) {
                // 输入密码时所用的APPID，默认为1，请按自己的实际使用情况
                kvDev.Set(KV.KEY.PINPAD_CALCULATE_MAC.REQUIRE.KEY_APP_ID, PinAppId);

                //对应设置秘钥管理体系值
                kvDev.Set(KV.KEY.PINPAD_CALCULATE_MAC.REQUIRE.KEY_MANAGER, "DUKPT");

                kvDev.Set(KV.KEY.PINPAD_CALCULATE_MAC.REQUIRE.DATA, F.HexStringToByteArray("4012345678909D987")); //ANSI X9.24-1-2009  Page 54 A.4 DUKPT Test Data Examples: MAC and Data Encryption Input (ASCII)
                Log.d("str", "macType" + macType[str]);

                //Mac 类型, 可选值为："X99"、"ECB"、"Unionpay_ECB"（银联标准算法）、"RICOM"
                kvDev.Set(KV.KEY.PINPAD_CALCULATE_MAC.REQUIRE.MAC_TYPE, macType[str]);
                ret = kvDev.Action(KV.CMD.PINPAD_CALCULATE_MAC);

                if (ret == KV.RET.OK) {
                    byte[] mac = kvDev.GetByteArray(KV.KEY.PINPAD_CALCULATE_MAC.RESPONSE.MAC);

                    setMsg(GetString.getString(KivviApplication.getContext(),R.string.MAC_type_is) + macType[str] + "," + GetString.getString(KivviApplication.getContext(),R.string.MAC_is) + "\n" + F.ByteArrayToHexString(mac));
                    Log.d("" + macType[str], "" + F.ByteArrayToHexString(mac));
                } else {
                    setMsg(GetString.getString(KivviApplication.getContext(),R.string.calculate_MAC_fail) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT));
                }
            }

        } catch (KvException e) {
            e.printStackTrace();
        }
    }
}
