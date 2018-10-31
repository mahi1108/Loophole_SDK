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
 * EMV处理
 */
public class ActionEmvIssuerProc extends BaseAction {

    public ActionEmvIssuerProc() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionCard)+"|"+GetString.getString(KivviApplication.getContext(),R.string.EMV_acquirer_data_processing_old));
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
        final KivviDevice kvDev = new KivviDevice();
        lock();
        try {
//          byte[] dbgIssuerData = new byte[]{(byte)0x91,(byte)0x01,(byte)0x33,(byte)0x71,(byte)0x01,(byte)0x44,(byte)0x72,(byte)0x01,(byte)0x66};
//          String dbgIssuerData = "9F260822A81E6B3D6FF3CA9F101307010103A02012010A010000000000308E89E69F3704D31901C59F36020162950500000480009A0316041282027C009F1A0201569F3303E0E9C09F1E085041365F49464D209F2701809C01009F02060000000001005F2A0201569F03060000000000009F34030203009F3501228408A0000003330101019F090200209F4104000000047211860F04DA9F790A000000000000E359E09C";
            String rdolApprove = "DFAE06|9F26|9F10|9F37|9F36|95|9A|82|9F1A|9F33|9F1E|9F27|9C|9F02|5F2A|9F03|9F34|9F35|84|9F09|9F41|DF31";
            String rdolDecline = "9F26|9F10";
            String IssueDataStr = "910A99887766554433220011720B86098418000004D876B77B8A023030";

            kvDev.Set(KV.KEY.EMV_ISSUER_PROC.REQUIRE.EMV_APP_ID, 1);
            kvDev.Set(KV.KEY.EMV_ISSUER_PROC.REQUIRE.EMV_ISSUER_DATA, F.HexStringToByteArray(IssueDataStr));
            kvDev.Set("rdolApprove", rdolApprove);
            kvDev.Set("rdolDecline", rdolDecline);
            kvDev.Set("Mactaglist", "DFAE06|9F26|9F10|9F37|9F36");

            unlock();
            int ret = kvDev.Action(KV.CMD.EMV_ISSUER_PROC);
            if(ret == KV.RET.OK){
                String emvDataType = kvDev.GetString(KV.KEY.EMV_ISSUER_PROC.RESPONSE.EMV_RESULT);
                byte[] emvApproveData = kvDev.GetByteArray(KV.KEY.EMV_ISSUER_PROC.RESPONSE.RDOL_APPROVE_DATA);
                byte[] emvDeclineData = kvDev.GetByteArray(KV.KEY.EMV_ISSUER_PROC.RESPONSE.RDOL_DECLINE_DATA);

                String msg = GetString.getString(KivviApplication.getContext(),R.string.data_type)+":" + emvDataType + "\n";
                if(emvApproveData != null)
                    msg += "emvApproveData"+":" + F.ByteArrayToHexString(emvApproveData) + "\n";
                if(emvDeclineData != null)
                    msg += "emvDeclineData"+":" + F.ByteArrayToHexString(emvDeclineData) + "\n";
                setMsg(msg);
            }else if(ret == KV.RET.PROCESSING)
            {

            }else{
                String msg = GetString.getString(KivviApplication.getContext(),R.string.failed_error_code_is) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT);

                setMsg(msg);
            }
        } catch (KvException e) {
            unlock();
            e.printStackTrace();
            setMsg(e.getMessage());
        }
    }
}
