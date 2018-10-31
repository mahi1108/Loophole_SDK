package com.cynovo.kivvi.demo.function.outside.action.printer;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by eddard on 16-6-20.
 */
public class ActionPrintCheckpaper extends BaseAction {
    public ActionPrintCheckpaper() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.low_paper_detection)+"-1");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDev = new KivviDevice();
        try {
            int ret = kvDev.Action(KV.CMD.PRINTER_PAPER_OUT);
            if(ret== KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.normal));
            }
            if(ret != KV.RET.OK){
                setMsg(kvDev.GetString(KV.KEY.BASIC.RESULT));
            }


        } catch (KvException e) {
            setMsg(e.getMessage());
        }
    }
}
