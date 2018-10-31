package com.cynovo.kivvi.demo.function.outside.action.pinpad;


import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.KivviApplication;

import cynovo.com.sdktool.utils.BaseTips;
import cynovo.com.sdktool.utils.GetString;

/**
 * Created by Craftsman on 2016/11/15.
 */
public class Tips extends BaseTips {
    private static String tip;

    static{
        tip = "=====================================\n";
        tip += "POS"+ GetString.getString(KivviApplication.getContext(),R.string.pinpad)+GetString.getString(KivviApplication.getContext(),R.string.Interface)+"\n";
    }

    public Tips() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad),  tip);
    }
}
