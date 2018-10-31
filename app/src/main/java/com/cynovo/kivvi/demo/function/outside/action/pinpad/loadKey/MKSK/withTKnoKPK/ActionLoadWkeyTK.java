package com.cynovo.kivvi.demo.function.outside.action.pinpad.loadKey.MKSK.withTKnoKPK;


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
 * 加载工作密钥
 */
public class ActionLoadWkeyTK extends BaseAction {
    public ActionLoadWkeyTK() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.LoadKey)+"|"+GetString.getString(KivviApplication.getContext(),R.string.MKSK)+"|"+GetString.getString(KivviApplication.getContext(),R.string.encrypt_by_TK_noKPK)+"|"+GetString.getString(KivviApplication.getContext(),R.string.load_work_keyTK)+"-2");
    }

    private int LoadWorkKey(int appId, String keyType, String key, String checkValue)
    {
        int ret = 0;
        final KivviDevice kvDevPinpad = new KivviDevice();
        String msgContent = new String();

        try {
            // 输入密码时所用的APPID，默认为1，请按自己的实际使用情况填
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_APP_ID, appId);

            //密钥类型，可选值有： "KPK"（传输密钥）、"MASTER_KEY"（主密钥）、"PIN_KEY"（工作密钥PIK）、"MAC_KEY"(工作密钥MAK)、"TD_KEY"(工作密钥)
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_TYPE, keyType);

            //密钥数据格式，可选值有："plain"（明文）、"EnByKPK"（由KPK加密）、"EnByMK"(由主密钥加密)、"TR31"
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_FORMAT, "EnByMK");

            //密钥数据
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_DATA, F.HexStringToByteArray(key));

            //密钥CheckValue 如果没有CheckValue，就不用设置下面这行
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_CHECK_VALUE, F.HexStringToByteArray(checkValue));

            ret = kvDevPinpad.Action(KV.CMD.PINPAD_LOAD_KEY);
            if(ret == KV.RET.OK){
                msgContent += GetString.getString(KivviApplication.getContext(),R.string.load) + keyType + GetString.getString(KivviApplication.getContext(),R.string.success) + "\n";
            }else{
                msgContent += GetString.getString(KivviApplication.getContext(),R.string.load) + keyType + GetString.getString(KivviApplication.getContext(),R.string.load_failure)+ret+"] ";
                try {
                    msgContent += GetString.getString(KivviApplication.getContext(),R.string.error) + kvDevPinpad.GetString(KV.KEY.BASIC.RESULT);
                } catch (KvException e) {
                    e.printStackTrace();
                } finally {
                    msgContent += "\n";
                }
            }
            setMsg(msgContent);
        } catch (KvException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public void myAction(String actionName) {
        LoadWorkKey(3, "PIN_KEY", "FDAC7B77ECAD14A5C6E11061A8BC9D5F", "52FBEA32");

        LoadWorkKey(3, "TD_KEY", "6FFD18913158B4BD82CBD7CE762C0F62", "C7AB586E");

        LoadWorkKey(3, "MAC_KEY", "A91CF415667DA4244E9323224D9524BB", "76BC1E41");
    }
}
