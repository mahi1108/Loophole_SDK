package com.cynovo.kivvi.demo.function.outside.action.card;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvi.demo.ui.view.progressdialog.SpotsDialog;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KivviDeviceResp;
import com.cynovo.kivvidevicessdk.KivviDeviceRespListener;
import com.cynovo.kivvidevicessdk.KvException;
import com.cynovo.kivvidevicessdk.Utils.F;

import cynovo.com.sdktool.utils.GetString;
import cynovo.com.sdktool.utils.ResUtil;

/**
 * Created by cynovo on 2016/6/2.
 * 搜接触卡
 */
public class ActionSearch extends BaseAction {
    private KivviDevice kvDev =null;
    private String message ="";

    private String GetStrings(int id)
    {
        return GetString.getString(KivviApplication.getContext(), id);
    }

    public ActionSearch() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionCard)+"|" + GetString.getString(KivviApplication.getContext(),R.string.search_card) );
    }

    Handler handler = new Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message message) {
            Log.i("message====",message.obj.toString());
            setMsg(message.obj.toString());
        }
    };

    private void OutputMessage(String message){
        Message msg = Message.obtain();
        msg.obj = message;
        // 发送这个消息到消息队列中
        handler.sendMessage(msg);
    }

    @Override
    public void myAction(final String actionName) {
        kvDev = new KivviDevice();
        showDialog(true,kvDev);

        try {
            // 期望搜索的卡类型，有效值为 "MSR"、"IC"、"NFC" 自由组合，用'|'分隔，默认为MSR|IC|NFC，三卡同时搜
            kvDev.Set(KV.KEY.CARD_SEARCH.REQUIRE.CARD_TYPE, "MSR|IC|NFC");

            // 超时时间，默认为60秒，单位为秒，最大不超过120秒
            //the timeout value ,default is 60 seconds, the maximum is 120 seconds
            kvDev.Set(KV.KEY.CARD_SEARCH.REQUIRE.TIMEOUT, 50);

            //是否允许FallBack
            //if allow fallback
            kvDev.Set(KV.KEY.CARD_SEARCH.REQUIRE.ALLOW_FALLBACK, 1);

            // 如果磁道数据需要加密，需设置密钥的APPID，否则不用设置
            // If you want to encrypt track data, you need to set the appId.
            kvDev.Set(KV.KEY.CARD_SEARCH.REQUIRE.KEY_APP_ID, 2);

            //mint定制。设置IC卡最大反插次数
            //maximum number of IC card search
            kvDev.Set(KV.KEY.CARD_SEARCH.REQUIRE.IC_SEARCH_MAXNUM, 3);

            int ret = kvDev.Action(KV.CMD.CARD_SEARCH, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    int respErrCode = kivviDeviceResp.ErrCode;
                    Log.d("KvDevDEMO", "CardReader " + respErrCode);
                    unlock();
                    if (respErrCode == KV.RET.OK) {
                        try {
                            String cardType = kvDev.GetString(KV.KEY.CARD_SEARCH.REQUIRE.CARD_TYPE);

                            if (cardType.equalsIgnoreCase("MSR")) {
                                String track1 = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.CARD_TRACK, 1);
                                String track2 = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.CARD_TRACK, 2);
                                String track3 = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.CARD_TRACK, 3);

                                message = GetStrings(R.string.is_MSR_card) + "\n"
                                        + "T1: " + track1 + "\n"
                                        + "T2: " + track2 + "\n"
                                        + "T3: " + track3 + "\n";

                                String pan = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.CARD_PAN);
                                if (pan != null)
                                    message += GetStrings(R.string.Account_is) + pan + "\n";

                                String expDate = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.CARD_EXP_DATE);
                                if (expDate != null)
                                    message += GetStrings(R.string.validity) + expDate + "\n";


                                String serviceCode = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.CARD_SERVICE_CODE);
                                if (serviceCode != null)
                                    message += GetStrings(R.string.ServiceCode) + serviceCode + "\n";

                                boolean bWithIC = false;
                                if (kvDev.Has(KV.KEY.CARD_SEARCH.RESPONSE.MSR_WITH_IC)) {
                                    bWithIC = (1 == kvDev.GetInt(KV.KEY.CARD_SEARCH.RESPONSE.MSR_WITH_IC));
                                }

                                message += GetStrings(R.string.if_is_IC_card)+":" + ((bWithIC) ? "yes" : "no") + "\n";
                            } else if (cardType.equals("IC")) {
                                int isICcard = kvDev.GetInt("isICCard");
                                if(isICcard == 1) {
                                    message = GetStrings(R.string.is_IC_card);
                                }else{
                                    message = "检测到卡片，卡片不是IC芯片卡（或插反）";
                                }

                            } else if (cardType.equals("NFC")) {
                                String nfcType = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.NFC_TYPE);
                                byte[] cardUid = kvDev.GetByteArray(KV.KEY.CARD_SEARCH.RESPONSE.NFC_UID);
                                if(cardUid != null) {
                                    String Uid= F.ByteArrayToHexString(cardUid);
                                    message= GetStrings(R.string.is_NFC_card)+"(" + nfcType + ")"+"\n"+"CardUid:"+" "+Uid;
                                }
                                else {
                                    message = GetStrings(R.string.is_NFC_card)+"(" + nfcType + ")";
                                }

                            } else {
                                message = GetStrings(R.string.unknown_card_type);
                            }
                        } catch (KvException e) {
                            //e.printStackTrace();
                            message = GetStrings(R.string.error_is) + e.getMessage();
                        } finally {
                        }
                    } else if (respErrCode == KV.RET.PROCESSING) {
                        message = GetStrings(R.string.IC_MSR_or_NFC);
                    } else if (respErrCode == KV.RET.CANCELED) {
                        message = actionName + GetStrings(R.string.terminated);
                    } else if (respErrCode == KV.RET.TIMEOUT) {
                        message = actionName;
                        message += GetStrings(R.string.failed_of_timeout);
                    } else {
                        message = GetStrings(R.string.failed_error_code_is) + respErrCode + "\n";

                        try {
                            String track1Error = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.TRACK1_ERROR);
                            String track2Error = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.TRACK2_ERROR);
                            String track3Error = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.TRACK3_ERROR);
                            message += "\n"
                                    + "Track1 Error: " + track1Error + "\n"
                                    + "Track2 Error: " + track2Error + "\n"
                                    + "Track3 Error: " + track3Error + "\n";
                        } catch (KvException e) {
                            e.printStackTrace();
                        }

                        try {
                            message += GetStrings(R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT);
                        } catch (KvException e) {
                            e.printStackTrace();
                        } finally {
                        }
                    }
                    OutputMessage(message);
                }
            });
            if (ret == KV.RET.PROCESSING) {
                message = GetStrings(R.string.IC_MSR_or_NFC);
                setMsg(message);
                lock();
            } else if (ret != KV.RET.OK) {
                message = actionName;
                message += GetStrings(R.string.error_is);
                message += ResUtil.errReason(ret);
                setMsg(message);
                unlock();
            }
        }catch (KvException e){
            setMsg(e.getMessage());
            unlock();
        }
    }
}
