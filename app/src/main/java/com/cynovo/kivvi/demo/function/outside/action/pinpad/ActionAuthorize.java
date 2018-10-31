package com.cynovo.kivvi.demo.function.outside.action.pinpad;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KivviApplication;

import cynovo.com.sdktool.utils.GetString;


/**
 * Created by cynovo on 2016/6/2.
 * authorize
 */
public class ActionAuthorize extends BaseAction {

    public ActionAuthorize() {
        super("pinpad|CMD_AUTHORIZE");
    }

    @Override
    public void myAction(String actionName) {
//        Snackbar.make(textView, "功能尚未完善", Snackbar.LENGTH_LONG).setAction("知道了", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        }).show();
        setMsg(actionName+ GetString.getString(KivviApplication.getContext(),R.string.developing));
    }
}
