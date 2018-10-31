package com.cynovo.kivvi.demo.function.outside.action.mifare;

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
 * 搜Mifare卡
 */
public class ActionMifareSearch extends BaseAction {
    private KivviDevice kvDev = null;
    private int searchedCardType;
    private String message ="";
    private int ret =-1;
    private SpotsDialog dialog;

    public ActionMifareSearch() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionMifare)+"|"+GetString.getString(KivviApplication.getContext(),R.string.search_mifare_card)+"-1");
    }


    Handler handler = new Handler() {
        // 在Handler中获取消息，重写handleMessage()方法
        @Override
        public void handleMessage(Message message) {
            Log.i("message====",message.obj.toString());
            setMsg(message.obj.toString());
        }
    };


    @Override
    public void myAction(final String actionName) {
        kvDev = new KivviDevice();
        showDialog(true,kvDev);

        try {
            //搜索IC和NFC
            kvDev.Set(KV.KEY.CARD_SEARCH.REQUIRE.CARD_TYPE, "NFC");

            // 超时时间，默认为60秒，单位为秒，最大不超过120秒
            kvDev.Set(KV.KEY.CARD_SEARCH.REQUIRE.TIMEOUT, 50);

            int ret = kvDev.Action(KV.CMD.CARD_SEARCH, new KivviDeviceRespListener() {
                @Override
                public void onResponse(KivviDeviceResp kivviDeviceResp) {
                    int respErrCode = kivviDeviceResp.ErrCode;
                    Log.d("KvDevDEMO", "CardReader " + respErrCode);
                    unlock();
                    if (respErrCode == KV.RET.OK) {
                        try {
                            String cardType = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.CARD_TYPE);

                            if (cardType.equals("IC")) {
                                message = GetString.getString(KivviApplication.getContext(),R.string.is_IC_card);
                            } else if (cardType.equals("NFC")) {
                                String nfcType = kvDev.GetString(KV.KEY.CARD_SEARCH.RESPONSE.NFC_TYPE);
                                byte[] cardUid = kvDev.GetByteArray(KV.KEY.CARD_SEARCH.RESPONSE.NFC_UID);
                                if(cardUid != null) {
                                    String Uid= F.ByteArrayToHexString(cardUid);
                                    message=GetString.getString(KivviApplication.getContext(),R.string.is_NFC_card)+"(" + nfcType + ")"+"\n"+"CardUid:"+" "+Uid;
                                }
                                else {
                                    message = GetString.getString(KivviApplication.getContext(),R.string.is_NFC_card)+"(" + nfcType + ")";
                                }

                            } else {
                                message = GetString.getString(KivviApplication.getContext(),R.string.unknown_card_type);
                            }
                        } catch (KvException e) {
                            //e.printStackTrace();
                            message = GetString.getString(KivviApplication.getContext(),R.string.error_is) + e.getMessage();
                        } finally {
                        }
                    } else if (respErrCode == KV.RET.PROCESSING) {
                        message = GetString.getString(KivviApplication.getContext(),R.string.IC_or_NFC);
                    } else if (respErrCode == KV.RET.CANCELED) {
                        message = actionName + GetString.getString(KivviApplication.getContext(),R.string.terminated);
                    } else if (respErrCode == KV.RET.TIMEOUT) {
                        message = actionName;
                        message += GetString.getString(KivviApplication.getContext(),R.string.failed_of_timeout);
                    } else {
                        message = GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is) + respErrCode + "\n";

                        try {
                            message += GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT);
                        } catch (KvException e) {
                            e.printStackTrace();
                        } finally {
                        }
                        //                    _err = ERR_FAILED;
                        //                    handler.sendEmptyMessage(MSG_ACTION_FAILED);
                    }
                    Message msg = Message.obtain();
                    msg.obj = message;
                    // 发送这个消息到消息队列中
                    handler.sendMessage(msg);
                }
            });
            if (ret == KV.RET.PROCESSING) {
                message = GetString.getString(KivviApplication.getContext(),R.string.IC_or_NFC);
                setMsg(message);
                lock();
            } else if (ret != KV.RET.OK) {
                message = actionName;
                message += GetString.getString(KivviApplication.getContext(),R.string.error_is);
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
