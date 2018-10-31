package com.cynovo.kivvi.demo.function.outside.action.basic;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import java.text.SimpleDateFormat;
import java.util.Date;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016-06-13.
 * 给金融模块设置时间
 */
public class ActionSetTime extends BaseAction{
    KivviDevice kvDev=null;

    public ActionSetTime() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionBasic)+"|"+GetString.getString(KivviApplication.getContext(),R.string.set_time)+"-2");
    }

    @Override
    public void myAction(String actionName) {

        kvDev = new KivviDevice();

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss"); //设置日期格式（the format of the data）
        String time = df.format(new Date());

        try {
            setMsg(GetString.getString(KivviApplication.getContext(),R.string.setting_time) +time);
            kvDev.Set(KV.KEY.TIME.REQUIRE.OPERATE, "SET");
            kvDev.Set(KV.KEY.TIME.REQUIRE.DATETIME, time);

            int ret = kvDev.Action(KV.CMD.TIME);
            if (ret == KV.RET.OK) {
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.finish_setting_time));
            } else {
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.fail_in_setting_time) +":" + ret +kvDev.GetString("result"));
            }
        }catch (KvException e){
            setMsg(e.getMessage());
        }
    }
}
