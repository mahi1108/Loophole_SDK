package com.cynovo.kivvi.demo.function.outside.action.pinpad.loadKey.FTKEY;


import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KivviApplication;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/2.
 * authorize
 */
public class ActionLoadKEY extends BaseAction {

    public ActionLoadKEY() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPinpad)+"|"+GetString.getString(KivviApplication.getContext(),R.string.LoadKey)+"|"+GetString.getString(KivviApplication.getContext(),R.string.FTKEY)+"|"+GetString.getString(KivviApplication.getContext(),R.string.load_work_key));

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
