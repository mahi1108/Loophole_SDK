package com.cynovo.kivvi.demo.function.outside.action.printer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvi.demo.base.BaseAction;
import cynovo.com.sdktool.utils.GetString;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

/**
 * Created by eddard on 16-6-20.
 */
public class ActionPrintPhoto extends BaseAction {
    public ActionPrintPhoto() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.picture)+"-3");
    }

    @Override
    public void myAction(String actionName) {
        final KivviDevice kvDev = new KivviDevice();
        Bitmap temp = BitmapFactory.decodeResource(KivviApplication.getContext().getResources(), R.drawable.strawberry);
        Bitmap photo = Bitmap.createScaledBitmap(temp, (temp.getWidth() / 2), (temp.getHeight() / 2), true);
        try {
            kvDev.Set(KV.KEY.BASIC.DATA, photo);
            kvDev.Action(KV.CMD.PRINTER_PHOTO);

            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 2);
            kvDev.Action(KV.CMD.PRINTER_FEED);
            setMsg(GetString.getString(KivviApplication.getContext(),R.string.print_success));
        } catch (KvException e) {
            setMsg(e.getMessage());
        }
    }
}