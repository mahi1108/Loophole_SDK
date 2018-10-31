
package com.cynovo.kivvi.demo.function.outside.action.printer;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvi.demo.base.BaseAction;
import cynovo.com.sdktool.utils.GetString;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by hai on 2016/6/18.
 */
public class ActionPrintText extends BaseAction {
    public ActionPrintText() {
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.text)+"-2");
    }

    @Override
    public void myAction(String actionName) {
        copyAssets("fangsongti.ttf", "/mnt/sdcard/fangsongti.ttf");
        copyAssets("kaiti.ttf", "/mnt/sdcard/kaiti.ttf");
        final KivviDevice kvDev = new KivviDevice();
        try {
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.DATA, "_+-*_+-*_+-*_+-*_+-*_+-*_+-*");
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.DATA, GetString.getString(KivviApplication.getContext(),R.string.strange));
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.DATA, GetString.getString(KivviApplication.getContext(),R.string.strange2));
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.DATA, GetString.getString(KivviApplication.getContext(),R.string.default_font) + "\n1234567890-=qwertyuiop[]asdfghjkl;'zxcvbnm,./   1234567789\nもういいよ！君と関");
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_LINEGAP, 6);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 2);
            kvDev.Action(KV.CMD.PRINTER_FEED);

            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.DATA, GetString.getString(KivviApplication.getContext(),R.string.font_font));
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_SIZE, 48);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 2);
            kvDev.Action(KV.CMD.PRINTER_FEED);

            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.DATA, GetString.getString(KivviApplication.getContext(),R.string.common_font));
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_FONTTYPE, android.graphics.Typeface.NORMAL);
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            kvDev.Set(KV.KEY.BASIC.DATA, GetString.getString(KivviApplication.getContext(),R.string.weight_font));
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_FONTTYPE, android.graphics.Typeface.BOLD);
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            kvDev.Set(KV.KEY.BASIC.DATA, GetString.getString(KivviApplication.getContext(),R.string.tilt_font));
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_FONTTYPE, android.graphics.Typeface.ITALIC);
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            kvDev.Set(KV.KEY.BASIC.DATA, GetString.getString(KivviApplication.getContext(),R.string.bold_font_tilt));
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_FONTTYPE, android.graphics.Typeface.BOLD_ITALIC);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 2);
            kvDev.Action(KV.CMD.PRINTER_FEED);

            kvDev.Set(KV.KEY.BASIC.DATA, GetString.getString(KivviApplication.getContext(),R.string.set_left_limit));
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_LEFTMARGIN, 120);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.BASIC.DATA,
                    GetString.getString(KivviApplication.getContext(),R.string.set_right_limit));
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_RIGHTMARGIN, 60);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.BASIC.DATA,
                    GetString.getString(KivviApplication.getContext(),R.string.set_row_spacing) + "\n1234567890-=qwertyuiop[]asdfghjkl;'zxcvbnm,./   1234567789\nもういいよ！君と関");
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_LINEGAP, 22);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 2);
            kvDev.Action(KV.CMD.PRINTER_FEED);

            kvDev.Set(KV.KEY.BASIC.DATA, GetString.getString(KivviApplication.getContext(),R.string.set_font_font_GB18030));
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TYPEFACE, "/mnt/sdcard/fangsongti.ttf");
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.BASIC.DATA, GetString.getString(KivviApplication.getContext(),R.string.set_font_italics_GB18030));
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TYPEFACE, "/mnt/sdcard/kaiti.ttf");
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.BASIC.DATA, "测试单行长度：32字节");
            kvDev.Action(KV.CMD.PRINTER_TEXT);
            kvDev.Set(KV.KEY.BASIC.DATA, "0123456789ABCDEFGHIJKLMNOPQRSTUV");
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            //TODO: print_textposition属性在字库打印时无效，通过空格实现定位，单行32字节
            kvDev.Set(KV.KEY.BASIC.DATA, "居中");
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TEXTPOSITION, 1);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.BASIC.DATA, "居左");
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TEXTPOSITION, 0);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.BASIC.DATA, "居右");
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TEXTPOSITION, 2);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.BASIC.DATA, "倍宽");
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TEXTWEIGHT, 2);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.BASIC.DATA, "倍高");
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TEXTHEIGHT, 2);
            kvDev.Action(KV.CMD.PRINTER_TEXT);


            kvDev.Set(KV.KEY.BASIC.DATA, "倍宽倍高");
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TEXTWEIGHT, 2);
            kvDev.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TEXTHEIGHT, 2);
            kvDev.Action(KV.CMD.PRINTER_TEXT);

            kvDev.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, 2);
            kvDev.Action(KV.CMD.PRINTER_FEED);
            setMsg(GetString.getString(KivviApplication.getContext(),R.string.print_success));
        } catch (KvException e) {
            setMsg(e.getMessage());
        }
    }

    private void copyAssets(String filename, String dest) {
        try {
            if (new File(dest).exists()) {
                return;
            }

            File outFile = new File(dest);
            File pdir = new File(outFile.getParent());
            if (!pdir.exists())
                pdir.mkdirs();
            InputStream is = KivviApplication.getContext().getAssets().open(filename);
            OutputStream os = new FileOutputStream(outFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (FileNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}