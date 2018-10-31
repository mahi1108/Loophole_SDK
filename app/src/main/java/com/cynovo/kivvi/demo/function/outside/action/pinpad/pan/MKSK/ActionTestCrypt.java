package com.cynovo.kivvi.demo.function.outside.action.pinpad.pan.MKSK;

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
 * 加解密测试
 */
public class ActionTestCrypt extends BaseAction {

    public ActionTestCrypt() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.loadkey_pan)+"|"+GetString.getString(KivviApplication.getContext(),R.string.MKSK)+"|"+GetString.getString(KivviApplication.getContext(),R.string.encryption_decryption_test)+"-1");
    }

    private byte[] TestCrypt(String opt, byte[] src){
        byte[] byteResult = null;
        String msg = "";

        final KivviDevice kvDev = new KivviDevice();

        try {
            kvDev.Set(KV.KEY.PINPAD_CRYPT.REQUIRE.OPERATE, opt);
            kvDev.Set(KV.KEY.PINPAD_CRYPT.REQUIRE.CRYPT_ALGORITHM, "DES_ECB");
            kvDev.Set(KV.KEY.PINPAD_CRYPT.REQUIRE.APPID, PinAppId);
            kvDev.Set(KV.KEY.PINPAD_CRYPT.REQUIRE.DATA, src);

            int ret = kvDev.Action(KV.CMD.PINPAD_CRYPT);
            if(ret == KV.RET.OK){
                byteResult = kvDev.GetByteArray(KV.KEY.PINPAD_CRYPT.RESPONSE.CAL_RESULT);

                setMsg(opt + GetString.getString(KivviApplication.getContext(),R.string.result)+ F.ByteArrayToHexString(byteResult));
            }else{
                setMsg(opt + GetString.getString(KivviApplication.getContext(),R.string.execution_fail_error_code) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT));
            }
        } catch (KvException e) {
            e.printStackTrace();
        }

        return byteResult;
    }

    @Override
    public void myAction(String actionName) {
        int ret = -1;

        final KivviDevice kvDev = new KivviDevice();

        String sourcePlain = "85236981478523698521478963258741";

        setMsg(GetString.getString(KivviApplication.getContext(),R.string.express) + sourcePlain);
        //转为十六进制的byte[]类型
        byte[] bytePlain = F.HexStringToByteArray(sourcePlain);
        byte[] byteEncrypted = TestCrypt("ENCRYPT", bytePlain);
        if(byteEncrypted != null){
            TestCrypt("DECRYPT", byteEncrypted);
        }
    }
}
