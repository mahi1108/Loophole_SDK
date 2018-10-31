package com.cynovo.kivvi.demo.function.outside.action.pinpad.pin.DUKPT;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KivviDeviceResp;
import com.cynovo.kivvidevicessdk.KivviDeviceRespListener;
import com.cynovo.kivvidevicessdk.KvException;
import com.cynovo.kivvidevicessdk.Utils.F;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 * 输入密码
 */
public class ActionGetPin extends BaseAction {
    KivviDevice kvDev = null;

    Handler handler = new Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message message) {
            Log.i("message====",message.obj.toString());
            setMsg(message.obj.toString());
        }
    };

    public ActionGetPin(){
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.loadkey_pin)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.DUKPT)+"|"+ GetString.getString(KivviApplication.getContext(),R.string.input_password)+"-1");
    }

    @Override
    public void myAction(final String actionName) {
        kvDev = new KivviDevice();
        showDialog(false,kvDev);
        int ret = 0;

        try {
            //卡号【可选参数】，如刚执行过搜卡指令（Search），可不设该参数，系统默认使用搜到的卡号
            kvDev.Set(KV.KEY.PIN_PROC.REQUIRE.PAN, "4012345678909"); //ANSI X9.24-1-2009  Page 54 A.4 DUKPT Test Data Examples: Assumed Primary Account Number

            // 交易金额 支持以元为单位的格式，如："12.34"、"12.3"、"12"。对于查询余额之类没有金额的交易，可以不设这行，但不要设为null
            kvDev.Set(KV.KEY.PIN_PROC.REQUIRE.AMOUNT_AUTH, "1200.3");

            // 输入密码时所用的APPID，默认为1，请按自己的实际使用情况，如不输PIN，可不用设置
            kvDev.Set(KV.KEY.PIN_PROC.REQUIRE.KEY_APP_ID, PinAppId);

            // 超时时间，默认为60秒，单位为秒，最大不超过120秒
            kvDev.Set(KV.KEY.PIN_PROC.REQUIRE.TIMEOUT, 50);

            // 输入密码的最小长度，默认为4，有效值4-12
            kvDev.Set(KV.KEY.PIN_PROC.REQUIRE.PIN_MINLEN, 4);
            // 输入密码的最小长度，默认为12，有效值4-12
            kvDev.Set(KV.KEY.PIN_PROC.REQUIRE.PIN_MAXLEN, 8);

            //设置秘钥管理体系值
            kvDev.Set(KV.KEY.PIN_PROC.REQUIRE.KEY_MNG,"DUKPT");

            ret = kvDev.Action(KV.CMD.PIN_PROC, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    String message = "";
                    int respErrCode = kivviDeviceResp.ErrCode;

                    try {
                        if(respErrCode == KV.RET.OK){
                            byte[] pinblock = kvDev.GetByteArray(KV.KEY.PIN.PINBLOCK);
                            message = "PINBLOCK："+ F.ByteArrayToHexString(pinblock);
                        }else if(respErrCode == KV.RET.PROCESSING){
                            //todo
                        }else{
                            message = GetString.getString(KivviApplication.getContext(),R.string.execution_fail_error_code) + respErrCode + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT);
                        }
                    } catch (KvException e) {
                        e.printStackTrace();
                    }

                    Message msg = Message.obtain();
                    msg.obj = message;
                    // 发送这个消息到消息队列中
                    handler.sendMessage(msg);

                    unlock();
                }
            });
        } catch (KvException e) {
            e.printStackTrace();
        }

        if(ret == KV.RET.PROCESSING){
            lock();
            setMsg(GetString.getString(KivviApplication.getContext(),R.string.please_input_password));
        }else if(ret != KV.RET.OK){
            setMsg(actionName + GetString.getString(KivviApplication.getContext(),R.string.execution_fail));
        }
    }
}
