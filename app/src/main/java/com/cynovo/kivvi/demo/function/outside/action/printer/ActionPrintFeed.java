package com.cynovo.kivvi.demo.function.outside.action.printer;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by eddard on 16-10-13.
 */
public class ActionPrintFeed  extends BaseAction {
    public ActionPrintFeed() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.feed)+"-6");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDev = new KivviDevice();
        int ret = 0;
        try{

            kvDev.Set(KV.KEY.BASIC.DATA, "---------------------");
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 10);
            kvDev.Action(KV.CMD.PRINTER_FEED);

            kvDev.Set(KV.KEY.BASIC.DATA, "-----------走纸10行");
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            setMsg(GetString.getString(KivviApplication.getContext(),R.string.Feed_10_Lines));

            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 100);
            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_FEEDUNIT, 1);
            kvDev.Action(KV.CMD.PRINTER_FEED);

            kvDev.Set(KV.KEY.BASIC.DATA, "-----------走纸100个象数点");
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            setMsg(GetString.getString(KivviApplication.getContext(),R.string.Feed_100_Pixels));

        }catch (KvException e){
            setMsg(e.getMessage());
        }
    }

}