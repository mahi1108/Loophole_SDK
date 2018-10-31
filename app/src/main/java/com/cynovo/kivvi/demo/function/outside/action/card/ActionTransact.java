package com.cynovo.kivvi.demo.function.outside.action.card;

import android.os.Handler;
import android.os.Message;

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
 * 交易
 */
public class ActionTransact extends BaseAction {
    private KivviDevice kvDev =null;
    private String message ="";
    int appId = 2;
    public ActionTransact() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionCard)+"|"+GetString.getString(KivviApplication.getContext(),R.string.direct_trade_old));
    }

    Handler handler = new Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message message) {
            setMsg(message.obj.toString());
        }
    };

    private String GetStrings(int id)
    {
        return GetString.getString(KivviApplication.getContext(), id);
    }

    private void Transact_old()
    {
        kvDev = new KivviDevice();
        setMsg(GetStrings(R.string.transacting) + "appId:" +appId);
        showDialog(true,kvDev);
        lock();
        try {
            //Transact 指令其实是Search、Pinprocess和EmvProc的组合，参数不再做重复说明，请参考Search、Pinprocess和EmvProc指令

            // 交易金额 支持以元为单位的格式，如："12.34"、"12.3"、"12"。对于查询余额之类没有金额的交易，可以不设这行，但不要设为null
            kvDev.Set(KV.KEY.TRANSACT.REQUIRE.AMOUNT_AUTH, "67.8");

            //交易类型(the type of the transaction)
            kvDev.Set(KV.KEY.TRANSACT.REQUIRE.TRANS_TYPE_CODE, 0x01);  // 消费

            //应用ID(the app ID)
            kvDev.Set(KV.KEY.TRANSACT.REQUIRE.KEY_APP_ID, appId);

            //完整流程
//            kvDev.Set(KV.OLD_KEY.TRANS_FLOW, 1);

            //是否允许FallBack (if allow fallback)
            kvDev.Set(KV.KEY.TRANSACT.REQUIRE.ALLOW_FALLBACK, 1);

            //强制联机标志(force online flag)
            kvDev.Set(KV.KEY.TRANSACT.REQUIRE.IS_FORCE_ONLINE, 1);

            // EMV 数据Tag标识，Tag之间用‘|’分隔，如没有特别要求，默认数据就符合直联规范（8583），可不以设置。
            kvDev.Set(KV.KEY.TRANSACT.REQUIRE.EMV_RDOL, "9F26|9F27|9F10|9F37|9F36|8E|95|9A|9F21|9B|9C|9F02|5F2A|82|9F0D|9F0E|9F0F|9F1A|9F1C|9F01|9F03|5F25|5F24|5F28|9F15|9F16|9F33|9F34|9F35|9F39|9F1E|84|9F09|9F41");

            int ret = kvDev.Action(KV.CMD.TRANSACT, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    int respErrCode = kivviDeviceResp.ErrCode;      //错误码
                    unlock();
                    if (respErrCode == KV.RET.OK) {
                        try {
                            String cardType = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_TYPE);
                            if (cardType.equalsIgnoreCase("MSR")) {
                                String track1 = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_TRACK, 1);
                                String track2 = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_TRACK, 2);
                                String track3 = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_TRACK, 3);

                                message = GetStrings(R.string.is_MSR_card)+ "\n"
                                        + "T1: " + track1 + "\n"
                                        + "T2: " + track2 + "\n"
                                        + "T3: " + track3 + "\n";
                                String pan = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_PAN);
                                if (pan != null)
                                    message += GetStrings(R.string.Account_is) + pan + "\n";

                                String expDate = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_EXP_DATE);
                                if (expDate != null)
                                    message += GetStrings(R.string.validity) + expDate + "\n";

                                boolean bWithIC = false;
                                if (kvDev.Has(KV.KEY.TRANSACT.RESPONSE.MSR_WITH_IC)) {
                                    bWithIC = (1 == kvDev.GetInt(KV.KEY.TRANSACT.RESPONSE.MSR_WITH_IC));
                                }

                                message += GetStrings(R.string.if_is_IC_card) + ((bWithIC) ? "yes" : "no") + "\n";

                                if(kvDev.Has(KV.KEY.PIN.PINBLOCK)){
                                    byte[] pinBlock = kvDev.GetByteArray(KV.KEY.PIN.PINBLOCK);
                                    message += "PINBLOCK: " + F.ByteArrayToHexString(pinBlock) + "\n";
                                }
                            } else if (cardType.equalsIgnoreCase("IC") || cardType.equalsIgnoreCase("NFC")) {

                                message =  ((cardType.equalsIgnoreCase("IC")) ? GetStrings(R.string.detected_IC_card) : GetStrings(R.string.is_NFC_card)) + "\n";

                                String pan = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_PAN);
                                String track2 = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_TRACK, 2);
                                String seqNo = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_SEQNO);
                                String expDate = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CARD_EXP_DATE);

                                String emvDataType = kvDev.GetString(KV.OLD_KEY.EMV_RESULT);
                                byte[] emvData = kvDev.GetByteArray(KV.OLD_KEY.EMV_DATA);

                                message += GetStrings(R.string.Account_is) + pan + "\n";
                                message += GetStrings(R.string.track2) + track2 + "\n";
                                message += GetStrings(R.string.order_number_is) + seqNo + "\n";
                                message += GetStrings(R.string.validity) + expDate + "\n";
                                if (kvDev.Has(KV.KEY.TRANSACT.RESPONSE.CVM_TYPE)) {
                                    String cvmType = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.CVM_TYPE);
                                    message += "CVM: " + cvmType + "\n";
                                }

                                if (kvDev.Has(KV.KEY.TRANSACT.RESPONSE.PINPAD_RESULT)) {
                                    String pinpadResult = kvDev.GetString(KV.KEY.TRANSACT.RESPONSE.PINPAD_RESULT);
                                    message += GetStrings(R.string.result_of_pinpad) + pinpadResult + "\n";
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

                                if (kvDev.Has(KV.KEY.PIN.PINBLOCK)) {
                                    byte[] pinBlock = kvDev.GetByteArray(KV.KEY.PIN.PINBLOCK);
                                    message += "PINBLOCK: " + F.ByteArrayToHexString(pinBlock) + "\n";
                                }
                                message += GetStrings(R.string.EMV_data_type) + emvDataType + "\n";
                                message += GetStrings(R.string.EMV_data) + F.ByteArrayToHexString(emvData) + "\n";
                            } else {
                                message = GetStrings(R.string.unknown_card_type);
                            }


                            Message msg = Message.obtain();
                            msg.obj =message;
                            handler.sendMessage(msg);
                        } catch (KvException e) {
                            e.printStackTrace();
                            Message msg = Message.obtain();
                            msg.obj =e.getMessage();
                            handler.sendMessage(msg);
                        }
                    }else{
                        try {
                            message = GetStrings(R.string.failed_error_code_is) + respErrCode;
                            message += GetStrings(R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT);

                            Message msg = Message.obtain();
                            msg.obj =message;
                            handler.sendMessage(msg);
                        } catch (KvException e) {
                            e.printStackTrace();
                            Message msg = Message.obtain();
                            msg.obj =e.getMessage();
                            handler.sendMessage(msg);
                        }
                    }
                }
            });
        }catch (KvException e){
            unlock();
            Message msg = Message.obtain();
            msg.obj =e.getMessage();
            handler.sendMessage(msg);
        }
    }


    @Override
    public void myAction(final String actionName) {
        Transact_old();
    }

}
