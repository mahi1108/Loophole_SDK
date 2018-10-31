package com.cynovo.kivvi.demo.function.outside.action.printer;

import android.graphics.Typeface;

import com.cynovo.kivvi.demo.R;
import com.cynovo.kivvi.demo.base.BaseAction;
import com.cynovo.kivvi.demo.base.KV;
import com.cynovo.kivvi.demo.base.KivviApplication;
import com.cynovo.kivvidevicessdk.KivviDevice;
import com.cynovo.kivvidevicessdk.KvException;

import cynovo.com.sdktool.utils.GetString;

/**
 * Created by eddard on 16-9-29.
 */
public class ActionPrintReceipt extends BaseAction {
    public ActionPrintReceipt(){
        super(GetString.getString(KivviApplication.getContext(),R.string.actionPrint)+"|"+GetString.getString(KivviApplication.getContext(),R.string.receipt)+"-7");
    }

    protected String defFont = "/mnt/sdcard/fangsongti.ttf";
    protected int    defLineGap  = 8;
    public static final int NORMAL      = Typeface.NORMAL;
    public static final int BOLD        = Typeface.BOLD;
    public static final int ITALIC      = Typeface.ITALIC;
    public static final int BOLD_ITALIC = Typeface.BOLD_ITALIC;

    @Override
    public void myAction(String actionName) {
        PrintOne(GetString.getString(KivviApplication.getContext(),R.string.UnionPay_POS_SalesSlip));
        PrintOne(GetString.getString(KivviApplication.getContext(),R.string.UnionPay_POS_SalesSlip));
    }

    private int PrintOne(String title)  {
        int ret = 0;

        KivviDevice kvDevPrinter = new KivviDevice();

        PrintText(kvDevPrinter, "   "+title,  BOLD, 36);   //todo... 字库打印，单行32字节，此行居中需10个空格
        PrintText(kvDevPrinter, "______________________________");
        PrintText(kvDevPrinter, GetString.getString(KivviApplication.getContext(),R.string.CommercialTenant_Number)+":");

        PrintText(kvDevPrinter, GetString.getString(KivviApplication.getContext(),R.string.card_number));
        PrintText(kvDevPrinter, " " + "622521******8628   S", BOLD, 32);

        PrintText(kvDevPrinter, "______________________________");
        String temp = "";
        temp += GetString.getString(KivviApplication.getContext(),R.string.IssuingBank_Number)+"0001";
        temp += "  ";
        temp += GetString.getString(KivviApplication.getContext(),R.string.AcquiringBank_Number)+"0002";
        PrintText(kvDevPrinter, temp);


        PrintText(kvDevPrinter, GetString.getString(KivviApplication.getContext(),R.string.TransactionType));
        PrintText(kvDevPrinter, "    消费(SALE)", BOLD, 32);


        temp = "";
        temp += GetString.getString(KivviApplication.getContext(),R.string.BatchNumber)+"000001";
        temp += "  ";
        temp += GetString.getString(KivviApplication.getContext(),R.string.Voucher_Number)+"000013" ;
        PrintText(kvDevPrinter, temp);

        PrintText(kvDevPrinter, GetString.getString(KivviApplication.getContext(),R.string.Authorization_Code)+"654321");

        PrintText(kvDevPrinter, GetString.getString(KivviApplication.getContext(),R.string.Reference_Number)+"123456000013");

        PrintText(kvDevPrinter, GetString.getString(KivviApplication.getContext(),R.string.Data_Time)+ "2016/06/12 16:30:59");


        PrintText(kvDevPrinter, GetString.getString(KivviApplication.getContext(),R.string.Amount));
        PrintText(kvDevPrinter, "    RMB 0.01", BOLD, 32);

        PrintText(kvDevPrinter, GetString.getString(KivviApplication.getContext(),R.string.Remark));
        PrintFeed(kvDevPrinter,1);
        PrintText(kvDevPrinter, "持卡人签名：");
        PrintText(kvDevPrinter, "CARD HOLDER SIGNATURE", NORMAL, 20);
        PrintFeed(kvDevPrinter,2);
        PrintText(kvDevPrinter, "______________________________");
        PrintText(kvDevPrinter, "本人确认以上交易，同意将其记入本卡账户", NORMAL, 19);
        PrintText(kvDevPrinter, "I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICE", NORMAL, 12);

        PrintFeed(kvDevPrinter,4);

        return ret;
    }

    //文本打印
    protected int PrintText(KivviDevice Kvde, String  data) {
        return PrintText(Kvde, data, null, null, null, null, null, null);
    }

    protected int PrintText(KivviDevice Kvde, String  data, Integer fontType,   Integer fontSize)  {
        return PrintText(Kvde, data, null, fontType, fontSize, null, null, null);
    }

    protected int PrintText(KivviDevice Kvde, String  data,
                            String  font,     Integer fontType,   Integer fontSize,
                            Integer lineGap,  Integer marginLeft, Integer marginRight) {
        int ret = -1;

            //1. 参数检查
            if (data == null) {
                return -1;
            }

            try {
                //2. 缺纸检查
                ret = Kvde.Action(KV.CMD.PRINTER_PAPER_OUT);
                if(ret != KV.RET.OK){
                    return -1;
                }

                //3. 参数设置
                if(font != null) {
                    Kvde.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TYPEFACE, font);
                }else{
                    Kvde.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_TYPEFACE, defFont);
                }

                if(fontType != null) {
                    Kvde.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_FONTTYPE, fontType);
                }

                if(fontSize != null) {
                    Kvde.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_SIZE, fontSize);
                }

                if(lineGap != null) {
                    Kvde.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_LINEGAP, lineGap);
                }else{
                    Kvde.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_LINEGAP, defLineGap);
                }

                if(marginLeft != null) {
                    Kvde.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_LEFTMARGIN, marginLeft);
                }

                if(marginRight != null) {
                    Kvde.Set(KV.KEY.PRINTER_TEXT.REQUIRE.PRINT_RIGHTMARGIN, marginRight);
                }

                Kvde.Set(KV.KEY.BASIC.DATA, data);

                //4. 打印
                ret = Kvde.Action(KV.CMD.PRINTER_TEXT);
                if(ret == KV.RET.OK){
                    ret = 0;
                }
            } catch (KvException e) {
                e.printStackTrace();
            }

        return ret;
    }

    protected int PrintFeed (KivviDevice Kvde, int lines) {
        int ret = -1;
            //1. 参数检查
            if (lines <= 0)
            {
                return ret;
            }

//            KivviDevice kvDevPrinter = new KivviDevice();

            try {
                //2. 缺纸检查
                ret = Kvde.Action(KV.CMD.PRINTER_PAPER_OUT);
                if(ret != KV.RET.OK){
                    return 0;
                }

                //3. 参数设置
                Kvde.Set(KV.KEY.PRINTER_FEED.REQUIRE.PRINT_LINE, lines);

                //4. 打印
                ret = Kvde.Action(KV.CMD.PRINTER_FEED);
                if(ret == KV.RET.OK){
                    ret = 0;
                }
            } catch (KvException e) {
                e.printStackTrace();
            }

        return ret;
    }
}
