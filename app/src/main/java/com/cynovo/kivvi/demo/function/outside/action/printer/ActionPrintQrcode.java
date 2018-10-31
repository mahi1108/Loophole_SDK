package com.cynovo.kivvi.demo.function.outside.action.printer;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by hai on 2016/6/18.
 */
public class ActionPrintQrcode extends BaseAction {
        public ActionPrintQrcode(){
            super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.QRcode)+"-5");
        }

        @Override
        public void myAction(String actionName) {
            final KivviDevice kvDev = new KivviDevice();
            try{
                kvDev.Set(KV.KEY.BASIC.DATA, GetString.getString(KivviApplication.getContext(),R.string.default_font) + "\n1234567890-=qwもういいよ！君と関");
                kvDev.Action(KV.CMD.PRINTER_QRCODE);
                kvDev.Set(KV.KEY.BASIC.DATA,  "\n1234567890-=qwもういいよ！君と関");
                kvDev.Action(KV.CMD.PRINTER_TEXT);

                kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 2);
                kvDev.Action(KV.CMD.PRINTER_FEED);
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.print_success));
            }catch (KvException e){
                setMsg(e.getMessage());
            }
        }
}
