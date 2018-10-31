package com.cynovo.kivvi.demo.function.outside.action.storage.fileRW;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by cynovo on 2016/6/1.
 */
public class ActionDeleteFile extends BaseAction {
    KivviDevice kvDev;

    public ActionDeleteFile() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionStorage)+"|"+GetString.getString(KivviApplication.getContext(),R.string.fileRW)+"|"+GetString.getString(KivviApplication.getContext(),R.string.delete_file)+"-6");
    }

    @Override
    public void myAction(String actionName) {
        setMsg(GetString.getString(KivviApplication.getContext(),R.string.file_store_delete_file));
        kvDev = new KivviDevice();
        try {
            kvDev.Set(KV.KEY.DELETE_FILE.REQUIRE.FILE_NAME,  "ActionStoreFile");

            int ret = kvDev.Action(KV.CMD.DELETE_FILE);
            if(ret == KV.RET.OK){
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.delete_file_success));
            }else{
                setMsg(GetString.getString(KivviApplication.getContext(),R.string.delete_file_fail_error_code) + ret + GetString.getString(KivviApplication.getContext(),R.string.error_is) + kvDev.GetString(KV.KEY.BASIC.RESULT));
            }
        }catch (KvException e){
            setMsg(e.getMessage());
        }
    }
}
