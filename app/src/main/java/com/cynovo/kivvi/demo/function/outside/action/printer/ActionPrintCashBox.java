package com.cynovo.kivvi.demo.function.outside.action.printer;

import android.util.Log;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by eddard on 17-9-8.
 */

public class ActionPrintCashBox extends BaseAction {

    public ActionPrintCashBox(){
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.Busy)+"-12");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDev = new KivviDevice();

        try {
            int ret = kvDev.Action("printer.cmd.cashbox");
            Log.v("eddard","printer.cmd.cashbox ret = " + ret);
        } catch (KvException e) {
            e.printStackTrace();
            Log.v("eddard",e.toString());
        }

    }
}
