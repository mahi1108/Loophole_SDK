package com.cynovo.kivvi.demo.function.outside.action.card;

import android.os.Handler;
import android.os.Message;

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
 * 交易
 */
public class ActionTransact_loadKey extends BaseAction {
    private KivviDevice kvDev =null;

    private String GetStrings(int id)
    {
        return GetString.getString(KivviApplication.getContext(), id);
    }

    public ActionTransact_loadKey() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionCard)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.LoadRelatedKey));
    }

    Handler handler = new Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message message) {
            setMsg(message.obj.toString());
        }
    };

    private void decommission(int appId){
        final KivviDevice kvDevPinpad = new KivviDevice();
        try {
            kvDevPinpad.Set(KV.KEY.PIN.KEY_APP_ID, appId);
            int ret = kvDevPinpad.Action(KV.CMD.KEY_DECOMMISSION);
            if(ret == KV.RET.OK){
                setMsg(GetStrings(R.string.decommission_ok));
            }else{
                String errMsg = GetStrings(R.string.decommission_fail) + ret + "\n";
                try {
                    errMsg += GetStrings(R.string.error_is) + kvDevPinpad.GetString(KV.KEY.BASIC.RESULT);
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
    private void loadKPK(String dbgKPK, int appId){
        int ret = 0;

        final KivviDevice kvDevPinpad = new KivviDevice();

        //String dbgKPK = "32323232323232323232323232323232";

        try {
            // 输入密码时所用的APPID，默认为1，请按自己的实际使用情况填
            kvDevPinpad.Set(KV.KEY.PIN.KEY_APP_ID, appId);

            //密钥类型，可选值有： "KPK"（传输密钥）、"MASTER_KEY"（主密钥）、"PIN_KEY"（工作密钥PIK）、"MAC_KEY"(工作密钥MAK)、"TD_KEY"(工作密钥)
            kvDevPinpad.Set(KV.KEY.PIN.KEY_TYPE, "KPK");

            //密钥数据格式，可选值有："plain"（明文）、"EnByKPK"（由KPK加密）、"EnByMK"(由主密钥加密)、"TR31"
            kvDevPinpad.Set(KV.KEY.PIN.KEY_FORMAT, "plain");

            kvDevPinpad.Set(KV.KEY.PIN.KEY_MANAGER, "DUKPT");

            kvDevPinpad.Set(KV.KEY.PIN.KEY_DATA, F.HexStringToByteArray(dbgKPK));

            ret = kvDevPinpad.Action(KV.CMD.PINPAD_LOAD_KEY);
            if(ret == KV.RET.OK){
                setMsg(GetStrings(R.string.success));
            }else{
                String errMsg = GetStrings(R.string.download_KPK_fail) + ret + "\n";
                try {
                    errMsg += GetStrings(R.string.error_is) + kvDevPinpad.GetString(KV.KEY.BASIC.RESULT);
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


    private void loadDukpt(int appid, String dbgDMK, String dbgKSN){
        final KivviDevice kvDevPinpad = new KivviDevice();

        try {
            // 输入密码时所用的APPID，请按自己的实际使用情况填
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_APP_ID, appid);

            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_MANAGER, "DUKPT");

            //密钥类型，可选值有： "KPK"（传输密钥）、"MASTER_KEY"（主密钥）、"PIN_KEY"（工作密钥PIK）、"MAC_KEY"(工作密钥MAK)、"TD_KEY"(工作密钥)
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_TYPE, "INIT_KEY");

            //密钥数据格式，可选值有："plain"（明文）、"EnByKPK"（由KPK加密）、"EnByMK"(由主密钥加密)、"TR31"
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_FORMAT, "EnByKPK");

            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KEY_DATA, F.HexStringToByteArray(dbgDMK));
            byte[] ksnbyte = F.HexStringToByteArray(dbgKSN);
            kvDevPinpad.Set(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KSN, ksnbyte);

            int ret = kvDevPinpad.Action(KV.CMD.PINPAD_LOAD_KEY);
            if(ret == KV.RET.OK){
                setMsg(GetStrings(R.string.success));
            }else{
                String errMsg = "加载密钥失败，" + ret + "\n";
                try {
                    errMsg += GetStrings(R.string.error_is) + kvDevPinpad.GetString(KV.KEY.BASIC.RESULT);
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

    private void getKSN(boolean update, int appId){
        final KivviDevice kvDevPinpad = new KivviDevice();

        try {
            // 输入密码时所用的APPID，默认为1，请按自己的实际使用情况填
            kvDevPinpad.Set(KV.KEY.PINPAD_KSN.REQUIRE.KEY_APP_ID, appId);
            kvDevPinpad.Set(KV.KEY.PINPAD_KSN.REQUIRE.OPERATE, update==true ? "UPDATE": "GET");

            int ret = kvDevPinpad.Action(KV.CMD.PINPAD_KSN);
            if(ret == KV.RET.OK){

                byte[] ksn = kvDevPinpad.GetByteArray(KV.KEY.PINPAD_LOAD_KEY.REQUIRE.KSN);
                setMsg("取KSN成功：" + F.ByteArrayToHexString(ksn));

            }else{
                String errMsg = "读取KSN失败" + ret + "\n";
                try {
                    errMsg += GetStrings(R.string.error_is) + kvDevPinpad.GetString(KV.KEY.BASIC.RESULT);
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

    public void PreTransactAction(int appId1, int appId2)
    {

        decommission(appId1);
        decommission(appId2);
        loadKPK( "57F83B7597E362A79BA2EC541A6129F4", appId1);
        loadDukpt(appId1, "44A0915D63D28B27E95C9B70CEB63F04", "000002016E365840" );

        loadKPK( "57F83B7597E362A79BA2EC541A6129F4", appId2);
        loadDukpt(appId2, "6AC7B406116C3B186D28AB211A753160", "000002016E365840" );

        getKSN(true, appId1);
        getKSN(true, appId2);
    }


    @Override
    public void myAction(final String actionName) {
        kvDev = new KivviDevice();
        setMsg("Downloading the related keys");

        int appId1 = 1;
        int appId2 = 2;

        PreTransactAction(appId1, appId2);

    }
    private void showmessage(String info){
        Message msg = Message.obtain();
        msg.obj =info;
        handler.sendMessage(msg);
    }
}
