package com.cynovo.kivvi.demo.function.outside.action.card;

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
 * EMV处理
 */
public class ActionEmvProc extends BaseAction {
    private int searchedCardType;
    private  boolean bNfcForceOnline = true; //true = EMV内核非接卡不提示输入PIN,  false = EMV内核非接卡自动判断是否输PIN
    String message = "";

    public ActionEmvProc() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionCard)+"|"+GetString.getString(KivviApplication.getContext(),R.string.EMV_process));
    }
    Handler handler = new Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message message) {
            setMsg(message.obj.toString());
        }
    };

    @Override
    public void myAction(final String actionName) {
        int ret = 0;
        final KivviDevice kvDev = new KivviDevice();
        showDialog(true,kvDev);
        lock();
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.EMV_processing));
        try {
            // 交易金额 支持以元为单位的格式，如："12.34"、"12.3"、"12"。对于查询余额之类没有金额的交易，可以不设这行，但不要设为null
            // the transaction amount ,support the yuan format, eg: "12.34" 、"12.3" 、"12",
            // there is no need to set this param when you queue the balance, but do not set is to null
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.AMOUNT_AUTH, "67.8");

            // EMV交易其他金额（外币），【可选参数】(the other amount of EMV transaction(foreign currency) [optional])
            //kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.AMOUNT_OTHER, "0.01");

            //交易类型(the type of transaction)
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.TRANS_TYPE_CODE, 0x01);    //消费

            //强制联机标志(the flag of force online)
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.IS_FORCE_ONLINE, 1);

            //完整流程还是简化流程(choose the complete process of streamline process)
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.TRANS_FLOW, 1);

            // 输入密码时所用的APPID，请按自己的实际使用情况
            // this param will be used when you enter pin, please use it according to your own situation
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.EMV_APP_ID, 1);
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.PIN_APP_ID, 2);

            // 超时时间，默认为60秒，单位为秒，最大不超过120秒(the value of timeout, default is 60 seconds, Maximum is 120 seconds)
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.TIMEOUT, 50);

            // 输入密码的最小长度，默认为4，有效值4-12(the minimum value of the password length, the default is 4, the range is 4 to 12)
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.PIN_LEN_MIN, 6);
            // 输入密码的最小长度，默认为12，有效值4-12(the maximum value of the password length, the default is 12)
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.PIN_LEN_MAX, 8);

            //密码键盘输PIN由应用控制，调用"pinpad.cmd.pinprocess"
            //kvDev.Set(KV.KEY.IS_USE_PINPAD, 0);

            // EMV 数据Tag标识，Tag之间用‘|’分隔，如没有特别要求，默认数据就符合直联规范（8583），可不以设置。
            kvDev.Set(KV.KEY.EMV_TRANS_REQUEST.REQUIRE.EMV_RDOL, "9F26|9F27|9F10|9F37|9F36|8E|95|9A|9F21|9B|9C|9F02|5F2A|82|9F0D|9F0E|9F0F|9F1A|9F1C|9F01|9F03|5F25|5F24|5F28|9F15|9F16|9F34|9F35|9F39|9F1E|84|9F09|9F41");

            ret = kvDev.Action(KV.CMD.EMV_TRANS_REQUEST, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    int respErrCode = kivviDeviceResp.ErrCode;
                    Log.i("====EMV处理",respErrCode+"");
                    unlock();
                    if (respErrCode == KV.RET.OK) {
                        try {
                            byte[] pinBlock = null;
                            String pan = kvDev.GetString(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.CARD_PAN);
                            String track2 = kvDev.GetString(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.CARD_TRACK,2);
                            String seqNo = kvDev.GetString(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.CARD_SEQNO);
                            String expDate = kvDev.GetString(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.CARD_EXP_DATE);

                            if(kvDev.Has(KV.KEY.PIN.PINBLOCK)) {
                                pinBlock = kvDev.GetByteArray(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.PINBLOCK);
                            }
                            String emvDataType = kvDev.GetString(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.EMV_RESULT);
                            byte[] emvData = kvDev.GetByteArray(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.EMV_DATA);

                            message = GetString.getString(KivviApplication.getContext(),R.string.Account_is) + pan + "\n";
                            message += GetString.getString(KivviApplication.getContext(),R.string.track2_is) + track2 + "\n";
                            message += GetString.getString(KivviApplication.getContext(),R.string.order_number_is) + seqNo + "\n";
                            message += GetString.getString(KivviApplication.getContext(),R.string.validity) +  expDate + "\n";
                            if(kvDev.Has(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.CVM_TYPE)){
                                String cvmType = kvDev.GetString(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.CVM_TYPE);
                                message += "CVM: " + cvmType + "\n";
                            }

                            if (kvDev.Has(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.PINPAD_RESULT)) {
                                String pinpadResult = kvDev.GetString(KV.KEY.EMV_TRANS_REQUEST.RESPONSE.PINPAD_RESULT);
                                message += GetString.getString(KivviApplication.getContext(),R.string.result_of_pinpad) + pinpadResult + "\n";
                                switch (pinpadResult) {
                                    case "OK":
                                        break;
                                    case "FAIL":
                                        break;
                                    case "USER CANCEL":
                                        break;
                                    case "TIMEOUT":
                                        break;
                                    case "NO PIN":
                                        break;
                                }
                            }

                            if(pinBlock != null) {
                                message += "PINBLOCK: " + F.ByteArrayToHexString(pinBlock) + "\n";
                            }
                            message += GetString.getString(KivviApplication.getContext(),R.string.EMV_data_type_is) + emvDataType + "\n";
                            if(emvData != null)
                                message += GetString.getString(KivviApplication.getContext(),R.string.EMV_data_is) + F.ByteArrayToHexString(emvData) + "\n";
                            Log.d("emvData",""+emvData);
                        } catch (KvException e) {
                            e.printStackTrace();
                            message =e.getMessage();
                        }
                    }else if(respErrCode == KV.RET.PROCESSING){

                    }else {
                        try {
                            message= GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is) + respErrCode +GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT);
                        } catch (KvException e) {
                            e.printStackTrace();
                            message =e.getMessage();
                        }
                    }
                    Message msg = Message.obtain();
                    msg.obj =message;
                    handler.sendMessage(msg);
                }
            });
        } catch (KvException e) {
            unlock();
            e.printStackTrace();
            setMsg(e.getMessage());
        }
    }
}
