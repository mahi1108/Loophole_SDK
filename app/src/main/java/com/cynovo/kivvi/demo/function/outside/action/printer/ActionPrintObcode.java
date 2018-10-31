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
public class ActionPrintObcode extends BaseAction {
    public ActionPrintObcode(){
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.OBcode)+"-4");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDev = new KivviDevice();
        try{
            kvDev.Set(KV.KEY.BASIC.DATA, "09876543211234567890"); //打印内容
            kvDev.Action(KV.CMD.PRINTER_OBCODE);
            kvDev.Set(KV.KEY.BASIC.DATA, "09876543211234567890");
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 4);
            kvDev.Action(KV.CMD.PRINTER_FEED);

            kvDev.Set(KV.KEY.BASIC.DATA, "12345ABCDEabcd"); //打印内容
            kvDev.Action(KV.CMD.PRINTER_OBCODE);
            kvDev.Set(KV.KEY.BASIC.DATA, "12345ABCDEabcd");
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 4);
            kvDev.Action(KV.CMD.PRINTER_FEED);

            kvDev.Set(KV.KEY.BASIC.DATA, "1234ABCDabcd"); //打印内容
            kvDev.Action(KV.CMD.PRINTER_OBCODE);
            kvDev.Set(KV.KEY.BASIC.DATA, "1234ABCDabcd");
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 4);
            kvDev.Action(KV.CMD.PRINTER_FEED);
            setMsg(GetString.getString(KivviApplication.getContext(),R.string.print_success));
        }catch (KvException e){
            setMsg(e.getMessage());
        }
    }
}
