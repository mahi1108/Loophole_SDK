package com.cynovo.kivvi.demo.base;

/**
 * 创建一个新版本的KV文件
 */
public class KV {
    //用来处理所有共用的信息
    //错误码
    public class RET {
        public static final int OK                  = (0x00);   // 成功
        public static final int DATA_ERROR          = (0x10);   // 数据错误
        public static final int CMD_ERROR           = (0x11);   // 指令错误
        public static final int PARAM_ERROR         = (0x12);   // 参数错误
        public static final int FAILED              = (0x20);   // 指令执行失败
        public static final int BUSY                = (0x21);   // 正在处理其他指令
        public static final int PROCESSING          = (0x22);   // 异步处理中...
        public static final int TIMEOUT             = (0x23);   // 指令执行(等待)超时
        public static final int CANCELED            = (0x24);   // 用户取消
        public static final int EMV_FAILED          = (0x30);   // EMV错误
        public static final int COMMUNICATE_ERROR   = (0x40);   // 通信失败
        public static final int DEVICE_ERROR        = (0x50);   // 设备异常
        public static final int OPEN_FAILED         = (0x51);   // 设备打开失败
    }

    public class EVENT_RET{
        public static final int PINPAD              = (0xB1);  //密码键盘输入(enter pin)
        public static final int ACCOUNTSELECT       = (0xB2);  //Account Type Selection
        public static final int AIDSELECT           = (0xB3);  //AID Selection
        public static final int AIDLIST             = (0xB4);  //App List Return
        public static final int CARD_DETECTED       = (0xB5);  //Card Detected event
        public static final int CONFIRM_UI          = (0xB6);  //The confirm UI event
        public static final int CARD_FALLBACK       = (0xB7);  //The Fall back event
        public static final int WRONGCARD_INFO      = (0xB8);  //The Fall back event
    }

    // 行为指令
    public class CMD {
        //basic
        //public static final String HANDSHAKE          = "basic.cmd.handshake";
        public static final String STOP_ACTION          = "basic.cmd.stop";
        public static final String TIME                 = "basic.cmd.time";                     //读取或设置时间
        public static final String GET_VERSION          = "basic.cmd.version";                  //读取金融模块和SDK版本信息
        public static final String SYSTEM               = "basic.cmd.system";                   //系统级操作，如重启模块
        public static final String IS_BUSY              = "basic.cmd.isbusy";                   //固件是否处于BUSY状态
        public static final String LED_SWITCH           = "basic.cmd.led";                      //LED开关
        public static final String LOG_STATUE           = "basic.cmd.log";                      //LED开关
        public static final String CLOSE_SCREEN         = "basic.cmd.closescreen";

        //card
        public static final String CARD_SEARCH          = "card.cmd.search";             //提示并等待用户搜卡
        public static final String TRANSACT             = "card.cmd.transact";           //银行卡交易请求，提示用户刷(插/挥)卡和输密码
        public static final String TRANSACT_MINT        = "card.cmd.transactmint";       //银行卡交易请求，提示用户刷(插/挥)卡和输密码
        public static final String DETECT_IC_SLOT       = "card.cmd.detect_ic_slot";     //检测IC卡槽是否有卡
        public static final String EMV_TRANS_REQUEST    = "card.cmd.emv_process";        //EMV交易请求
        public static final String EMV_ISSUER_PROC      = "card.cmd.issuer_process";     //EMV交易的发卡行数据处理
        public static final String EMV_ISSUER_PROC_MINT = "card.cmd.onlineresp_mint";    //EMV交易的发卡行数据处理mint

        public static final String MIFARE               = "card.cmd.mifare";             //Mifare卡的认证、读写等
        public static final String APDU                 = "card.cmd.apdu";               //APDU交互操作



        //pinpad
        public static final String PINPAD_MSG_CONFIRM   = "pinpad.cmd.confirm";          //余额等敏感信息确认
        public static final String PINPAD_CRYPT         = "pinpad.cmd.crypto";           //测试加解密功能
        public static final String PINPAD_LOAD_KEY      = "pinpad.cmd.loadkey";          //加载KPK、主密钥、工作密钥
        public static final String PINPAD_CALCULATE_MAC = "pinpad.cmd.mac";              //计算MAC
        public static final String PIN_PROC             = "pinpad.cmd.pinprocess";       //提示并等待用户输入密码
        public static final String GET_RANDOM           = "pinpad.cmd.random";           //生成随机数
        public static final String PINPAD_KSN           = "pinpad.cmd.ksn";              //KSN相关操作
        public static final String KEY_DECOMMISSION     = "pinpad.cmd.decommission";     //销毁密钥

        public static final String DIGITAL_SIGN         = "pinpad.mpr.handwrite";        //手写数字签名

        //exscreen
        public static final String DISPLAY_IDLE         = "exscreen.cmd.idle";          //小屏显示待机Logo
        public static final String DISPLAY_QR_CODE      = "exscreen.cmd.qrcode";        //小屏显示二维码
        public static final String DISPLAY_CUSTOM_MSG   = "exscreen.cmd.custom_msg";    //小屏显示用户自定义信息
        public static final String DISPLAY_BITMAPS      = "exscreen.cmd.bitmap";        //小屏图片轮播

        //storage
        public static final String STORE_FILE           = "storage.mpw.write_file";     //存储文件
        public static final String READ_FILE            = "storage.mpr.read_file";      //读取文件
        public static final String DELETE_FILE          = "storage.cmd.delete_file";    //删除文件
        public static final String LIST_FILES           = "storage.cmd.list_files";     //列举文件
        public static final String STORE_BITMAP         = "storage.mpw.load_bitmap";    //保存位图或logo



        //printer
        public static final String PRINTER_PAPER_OUT    = "printer.cmd.paperout";       //打印机缺纸检测
        public static final String PRINTER_OBCODE       = "printer.cmd.obcode";         //打印一维码
        public static final String PRINTER_PHOTO        = "printer.cmd.photo";          //打印图片
        public static final String PRINTER_QRCODE       = "printer.cmd.qrcode";         //打印二维码
        public static final String PRINTER_TEXT         = "printer.cmd.text";           //打印文字
        public static final String PRINTER_FEED         = "printer.cmd.feed";           //打印机走纸
        public static final String PRINTER_BUSY         = "printer.cmd.busy";           //打印机忙
        public static final String PRINTER_UPDATE       = "printer.cmd.updateprinter";  //升级打印机固件
        public static final String PRINTER_VERSION      = "printer.cmd.printerversion";

        //scan
        public static final String SCAN_CODE            = "scan.cmd.scancode";          //扫码

        //old cmd
//        public static final String EMV_READ_RECORD      = "card.cmd.emv_readrecord";
//        public static final String EMV_BALANCE          = "emv.cmd.balance";             //芯片卡脱机读取卡片余额
//        public static final String EMV_LOG              = "emv.cmd.log";                 //芯片卡读取交易记录
//        public static final String PINPAD_TP            = "pinpad.cmd.tp";
//        public static final String STORE_EMV_PARAMS     = "storage.cmd.load_emvparams"; //加载EMV参数
//        public static final String EMV_PARAM_SET        = "emv.cmd.paramset";
//        public static final String INIT_EMV_PARAMS      = "emv.cmd.paramsinit";         //初始化EMV参数
    }

    public class OLD_KEY{
        //旧交易相关的key
        public static final String AMOUNT_AUTH          = "amount";                     //(old)   new: amountAuth//交易金额
        public static final String EMV_RDOL             = "reqTagList";                 // EMV 数据Tag标识，Tag之间用‘|’分隔，如没有特别要求，默认数据就符合直联规范（8583），可以不设置。
        public static final String EMV_RESULT           = "emvDataType";
        public static final String MSR_PIN_PROC         = "msrPinProc";                 //搜到磁条卡是否进行输密?0\1
        public static final String EMV_DATA             = "emvData";                   //TLV,EMV数据（55域）
        public static final String TRANS_FLOW           = "transFlow";                  //交易流程  有效值: "1"完整流程  "2"简化流程  "3"qPBOC流程
    }

    // 行为参数名
    public class KEY{

        public class BASIC{
            //通用，基础
            public static final String RESULT               = "result";                     //错误描述
            public static final String DATA                 = "data";
        }

//=====================基础指令(basic action)=========================//
        public class IS_BUSY{
            public class RESPONSE{
                public static final String IS_BUSY              = "isbusy";                     //busy 态
            }
        }

        public class LED_SWITCH{
            public class REQUIRE{
                public static final String LED                  = "led";
                public static final String OPERATE              = "operate";                    //操作 有效值为："ON"/"OFF"/"BLINK"
                public static final String DELAY                = "delay";
                public static final String SPEED                = "speed";
            }
        }

        public class LOG_STATUE{
            public class REQUIRE{
                public static  final String LOGRECORD           = "logrecord";                  //日志记录开关
                public static  final String LOGSTATUE           = "logstatue";
            }
        }


        public class SYSTEM{
            public class REQUIRE{
                public static final String OPERATE              = "operate";                    //操作 有效值为："update"/"reboot"
                public static final String FW_PATH              = "fwPath";                     //sbin文件存储路径,默认为SD卡根目录
                public static final String KVDEV_PLATFORM       = "KivviDevicePlatform";        //KivviDevice平台
            }

        }

        public class TIME{
            public class REQUIRE{
                public static final String OPERATE              = "operate";                    //操作 有效值为："GET"/"SET"
                public static final String DATETIME             = "datetime";                   //仅"SET"时需要
            }
            public class RESPONSE{
                public static final String DATETIME             = "datetime";
            }

        }

        public class GET_VERSION{
            public class REQUIRE{}
            public class RESPONSE{
                public static final String KIVVI_HW_VERSION     = "hwVer";                      //金融模块硬件版本号
                public static final String FW_VERSION           = "fwVer";                      //软件版本号
                public static final String CHIP_VERSION         = "chipVer";                    //芯片版本号
                public static final String KIVVI_SERIAL_NO      = "serialNo";                   //金融模块序列号
                public static final String KVDEV_SECURITY       = "security";                   //安全触发寄存器

                //主设备信息：
                public static final String KVDEV_PLATFORM       = "KivviDevicePlatform";        //KivviDevice平台
                public static final String SERIAL_NO            = "KivviDeviceSN";              //主设备序列号
                public static final String HW_VERSION           = "KivviDeviceHardwareVersion"; //主设备硬件版本号
                public static final String SDK_VERSION          = "SdkVerison";                 //SDK版本
                public static final String KVDEV_VERSION        = "KivviDeviceVerison";         //KivviDevice版本
            }

        }

//=====================交易相关(Transact action)=========================//
        public class EMV_ISSUER_PROC{
            public class REQUIRE{
                public static final String EMV_ISSUER_DATA      = "issuerData";                 //发卡行数据
                public static final String EMV_RDOL_APPROVE     = "rdolApprove";
                public static final String EMV_RDOL_DECLINE     = "rdolDecline";
                public static final String EMV_CHECKMAC_DATA    = "CheckMacData";
                public static final String KEY_MANAGER          = "keyMng";                     //密钥管理体系
                public static final String EMV_APP_ID           = "appId";                      //
                public static final String MAC_TYPE             = "macType";                    // MAC类别, 有效值:"X99"、"ECB"、"Unionpay_ECB"、"RICOM"
                public static final String MAC_RES              = "macRes";                     //值为："MACK_RESP"时，密钥为MAC加密密钥2，不输或其他值时为MAC加密密钥
            }
            public class RESPONSE{
                public static final String EMV_RESULT           = "emvResult";                   //值为："Online Declined","Online Approved","TERMINATED","UNKNOWN"
                public static final String RDOL_APPROVE_DATA    = "rdolApproveData";
                public static final String RDOL_DECLINE_DATA    = "rdolDeclineData";
                public static final String MAC_RESULT           = "macResult";
            }
        }

        public class EMV_ISSUER_PROC_MINT{
            public class REQUIRE{
                public static final String EMV_ISSUER_DATA      = "issuerData";                 //发卡行数据
                public static final String EMV_RDOL_APPROVE     = "rdolApprove";
                public static final String EMV_RDOL_DECLINE     = "rdolDecline";
                public static final String EMV_CHECKMAC_DATA    = "CheckMacData";
                public static final String KEY_MANAGER          = "keyMng";                     //密钥管理体系
                public static final String EMV_APP_ID           = "appId";                      //
                public static final String MAC_TYPE             = "macType";                    // MAC类别, 有效值:"X99"、"ECB"、"Unionpay_ECB"、"RICOM"
                public static final String MAC_RES              = "macRes";                     //值为："MACK_RESP"时，密钥为MAC加密密钥2，不输或其他值时为MAC加密密钥
            }
            public class RESPONSE{
                public static final String EMV_RESULT           = "emvResult";                   //值为："Online Declined","Online Approved","TERMINATED","UNKNOWN"
                public static final String RDOL_APPROVE_DATA    = "rdolApproveData";
                public static final String RDOL_DECLINE_DATA    = "rdolDeclineData";
                public static final String MAC_RESULT           = "macResult";
            }
        }

        public class EMV_TRANS_REQUEST{
            public class REQUIRE{
                public static final String AMOUNT_AUTH          = "amountAuth";                 //交易金额
                public static final String AMOUNT               = "amount";                     //(old)   new: amountAuth//交易金额
                public static final String AMOUNT_OTHER         = "amountOther";                //其他金额
                public static final String ACCOUNT_TYPE         = "accountType";                //账户类型：Format: 00-Default-unspecified(默认选择)；10-Savings； 20-Cheque/debit；30-Credit

                public static final String TRANS_DATE_TIME      = "transDateTime";              //交易日期时间：Format: "YYMMDDHHMMSS"
                public static final String TRANS_CURR_EXP       = "transCurrExp";               //交易货币指数 Format: 01
                public static final String TRANS_CURR_CODE      = "transCurrCode";              //交易货币代码 Format: 0840
                public static final String TRANS_CATE_CODE      = "transCateCode";              //交易类别代码 Format: 01
                public static final String TRANS_TYPE_CODE      = "transType";                  //交易类型码   见直联规范(8583)Page99 消息域说明, 如，0x22为"消费"

                public static final String BALANCE_READBEFORE_GAC   = "balanceReadBeforeGAC";   //读非接余额在GAC前（PayPass使用）
                public static final String BALANCE_READAFTER_GAC    = "balanceReadAfterGAC";    //读非接余额在GAC前（PayPass使用）
                public static final String MERCHANT_CUSTOM_DATA     = "merchantCustomData";      //商户定义数据（PayPass使用）

                public static final String KEY_MANAGER          = "keyMng";                     //密钥管理体系
                public static final String TRACK_MODE           = "trackMode";
                public static final String PIN_FORMAT           = "pinFormat";
                public static final String EMV_APP_ID           = "appId";
                public static final String PIN_APP_ID           = "appId2";
                public static final String TIMEOUT              = "timeout";                    //超时时间，单位秒
                public static final String PIN_LEN_MIN          = "pinMinLen";                  //瀵嗙爜鏈€灏忛暱搴?
                public static final String PIN_LEN_MAX          = "pinMaxLen";
                public static final String IS_FORCE_ONLINE      = "isForceOnline";
                public static final String KEYPAD_MODE          = "keypadmode";
                public static final String MAC_TAGLIST          = "Mactaglist";

                public static final String MAC_TYPE             = "macType";                    // MAC类别, 有效值:"X99"、"ECB"、"Unionpay_ECB"、"RICOM"
                public static final String MAC_RES              = "macRes";                      //值为："MACK_RESP"时，密钥为MAC加密密钥2，不输或其他值时为MAC加密密钥
                public static final String PINBLOCK             = "pinBlock";

                public static final String TRANS_FLOW           = "transFlow";                  //交易流程  有效值: "1"完整流程  "2"简化流程  "3"qPBOC流程
                public static final String EMV_RDOL             = "rdol";
            }
            public class RESPONSE{
                public static final String CARD_PAN             = "pan";                        //主帐号
                public static final String TRACK_MODE           = "trackMode";                  //有效值: "plain"(明文)、"entype0"(加密方式0)
                public static final String CARD_SEQNO           = "seqNo";                      //IC卡序列号
                public static final String CARD_EXP_DATE        = "expDate";                    //仅当cardType为”MSR”时存在,有效期
                public static final String PINBLOCK             = "pinBlock";
                public static final String EMV_RESULT           = "emvResult";
                public static final String EMV_DATA             = "rdolData";                   //TLV,EMV数据（55域）
                public static final String CVM_TYPE             = "cvmType";                    //有效值: "ONLINE_PIN" 联机PIN、 "SIGN"签名
                public static final String PINPAD_RESULT        = "pinpadResult";               //输PIN结果 "OK"已输PIN  "FAIL"输PIN失败  "USER CANCEL"用户取消  "TIMEOUT"超时  "NO PIN"没有输PIN，直接按确认
                public static final String CARD_TRACK           = "track";                      // 磁道数据
            }
        }

        public class DETECT_IC_SLOT{
            public class REQUIRE{
                public static final String IS_ICCARD            = "isICCard";
            }
            public class RESPONSE{
                public static final String ICCARD_INSLOT        = "cardInSlot";                 //0:无卡  1:有卡
                public static final String IS_ICCARD            = "isICCard";                   //仅当有卡时存在 0:错误卡  1:IC卡
            }
        }

        public class CARD_SEARCH{
            public class REQUIRE{
                public static final String CARD_TYPE            = "cardType";                   //卡类别 有效值: "MSR"、"IC"、"NFC"
                public static final String TRACK_MODE           = "trackMode";
                public static final String KEY_MANAGER          = "keyMng";                     //密钥管理体系
                public static final String KEY_APP_ID           = "appId";                      //仅当磁道加密时需要,默认0x01
                public static final String TIMEOUT              = "timeout";                    //超时时间，单位秒
                public static final String ALLOW_FALLBACK       = "allowFallback";              //是否允许降级交易
                public static final String IC_SEARCH_MAXNUM     = "IcSearchMaxNum";
                public static final String AMOUNT_AUTH          = "amountAuth";                 //交易金额

                public static final String TRACK_DATA           = "trackData";
            }
            public class RESPONSE{
                public static final String CARD_TYPE            = "cardType";                   //卡类别 有效值: "MSR"、"IC"、"NFC"
                public static final String NFC_TYPE             = "nfcType";                    //响应:“typeA”、”typeB” 、”M1”
                public static final String IS_ICCARD            = "isICCard";                   //有效值：1 是IC卡且上电成功  0 非IC卡或插入错误
                public static final String CARD_TRACK           = "track";                      // 磁道数据

                public static final String CARD_PAN             = "pan";                        //主帐号
                public static final String CARD_EXP_DATE        = "expDate";                    //仅当cardType为”MSR”时存在,有效期
                public static final String CARD_SERVICE_CODE    = "serviceCode";                //仅当cardType为”MSR”时存在,服务代码
                public static final String MSR_WITH_IC          = "withIC";                     //磁条卡带IC标记
                public static final String IS_FALL_BACK          = "isfallback";                //是否是降级交易 1是降级交易  0非降级交易
                public static final String NFC_UID              = "cardUid";                    //NFC卡UID
                public static final String TRACK1_ERROR         = "track1Error";                //磁道1错误码
                public static final String TRACK2_ERROR         = "track2Error";                //磁道2错误码
                public static final String TRACK3_ERROR         = "track3Error";                //磁道3错误码

            }
        }

        public class TRANSACT_MINT{
            public class REQUIRE{
                public static final String EMV_APP_ID           = "appId";                      //EMV参数APP ID
                public static final String AMOUNT_AUTH          = "amountAuth";                 //交易金额
                public static final String AMOUNT_OTHER         = "amountOther";                //其他金额
                public static final String TRANS_DATE_TIME      = "transDateTime";              //交易日期时间：Format: "YYMMDDHHMMSS"
                public static final String TRANS_CURR_EXP       = "transCurrExp";
                public static final String TRANS_CURR_CODE      = "transCurrCode";
                public static final String TRANS_TYPE_CODE      = "transType";                  //交易类型码   见直联规范(8583)Page99 消息域说明, 如，0x22为"消费"

                public static final String EMV_RDOL             = "rdol";
                public static final String MAC_TAGLIST          = "Mactaglist";
                public static final String CARD_TYPE            = "cardType";                   //卡类别 有效值: "MSR"、"IC"、"NFC"
                public static final String KEY_MANAGER          = "keyMng";                     //密钥管理体系
                public static final String PIN_FORMAT           = "pinFormat";
                public static final String TRACK_MODE           = "trackMode";

                public static final String PIN_APP_ID           = "appId2";
                public static final String TIMEOUT              = "timeout";                    //超时时间，单位秒
                public static final String PIN_LEN_MIN          = "pinMinLen";                  //瀵嗙爜鏈€灏忛暱搴?
                public static final String PIN_LEN_MAX          = "pinMaxLen";
                public static final String ALLOW_FALLBACK       = "allowFallback";              //是否允许降级交易
                public static final String IS_FORCE_ONLINE      = "isForceOnline";
                public static final String IC_SEARCH_MAXNUM     = "IcSearchMaxNum";

                public static final String TRACK_DATA           = "trackData";
                public static final String PINBLOCK             = "pinBlock";
                public static final String MAC_TYPE             = "macType";                    // MAC类别, 有效值:"X99"、"ECB"、"Unionpay_ECB"、"RICOM"
                public static final String MAC_RES              = "macRes";                     //值为："MACK_RESP"时，密钥为MAC加密密钥2，不输或其他值时为MAC加密密钥

                //事件传参

                public static final String EVENT_FLOW              = "EventFlow";

            }
            public class RESPONSE{
                public static final String CARD_TYPE            = "cardType";                   //卡类别 有效值: "MSR"、"IC"、"NFC"
                public static final String CARD_TRACK           = "track";                      // 磁道数据
                public static final String CARD_RDOLDATA        = "rdolData";                   // 磁道数据

                public static final String CARD_PAN             = "pan";                        //主帐号
                public static final String CARD_EXP_DATE        = "expDate";                    //仅当cardType为”MSR”时存在,有效期
                public static final String CARD_SEQNO           = "seqNo";                      //IC卡序列号
                public static final String CARD_SERVICE_CODE    = "serviceCode";                //仅当cardType为”MSR”时存在,服务代码
                public static final String MSR_WITH_IC          = "withIC";                     //磁条卡带IC标记
                public static final String PINBLOCK             = "pinBlock";

                public static final String CVM_TYPE             = "cvmType";                    //有效值: "ONLINE_PIN" 联机PIN、 "SIGN"签名
                public static final String EMV_DATA_TYPE        = "emvResult";                  //"emvDataType"; //EMV鏁版嵁绫诲瀷 鏈夋晥鍊? "REQUEST" 璇锋眰銆?CONFIRM" 纭銆?REVERSAL" 鍐叉
                public static final String EMV_DATA             = "emvData";                    //TLV,EMV数据（55域）
                public static final String PINPAD_RESULT        = "pinpadResult";               //输PIN结果 "OK"已输PIN  "FAIL"输PIN失败  "USER CANCEL"用户取消  "TIMEOUT"超时  "NO PIN"没有输PIN，直接按确认

                public static final String ACCOUNT_TYPE         = "accountType";
                public static final String CARD_AID             = "aid";
                public static final String COUNTRY_CODE         = "countryCode";
                public static final String BUTTON_ID            = "ButtonId";
            }
        }

        public class TRANSACT{
            public class REQUIRE{
                public static final String KEY_APP_ID           = "appId";                      //EMV参数APP ID
                public static final String AMOUNT_AUTH          = "amountAuth";                 //交易金额
                public static final String AMOUNT_OTHER         = "amountOther";                //其他金额
                public static final String TRANS_DATE_TIME      = "transDateTime";              //交易日期时间：Format: "YYMMDDHHMMSS"
                public static final String TRANS_CURR_EXP       = "transCurrExp";
                public static final String TRANS_CURR_CODE      = "transCurrCode";
                public static final String TRANS_TYPE_CODE      = "transType";                  //交易类型码   见直联规范(8583)Page99 消息域说明, 如，0x22为"消费"

                public static final String EMV_RDOL             = "rdol";
                public static final String MAC_TAGLIST          = "Mactaglist";
                public static final String CARD_TYPE            = "cardType";                   //卡类别 有效值: "MSR"、"IC"、"NFC"
                public static final String KEY_MANAGER          = "keyMng";                     //密钥管理体系
                public static final String PIN_FORMAT           = "pinFormat";
                public static final String TRACK_MODE           = "trackMode";

                public static final String PIN_APP_ID           = "appId2";
                public static final String TIMEOUT              = "timeout";                    //超时时间，单位秒
                public static final String PIN_LEN_MIN          = "pinMinLen";                  //瀵嗙爜鏈€灏忛暱搴?
                public static final String PIN_LEN_MAX          = "pinMaxLen";
                public static final String ALLOW_FALLBACK       = "allowFallback";              //是否允许降级交易
                public static final String IS_FORCE_ONLINE      = "isForceOnline";
                public static final String IC_SEARCH_MAXNUM     = "IcSearchMaxNum";

                public static final String TRACK_DATA           = "trackData";
                public static final String PINBLOCK             = "pinBlock";
                public static final String MAC_TYPE             = "macType";                    // MAC类别, 有效值:"X99"、"ECB"、"Unionpay_ECB"、"RICOM"
                public static final String MAC_RES              = "macRes";                     //值为："MACK_RESP"时，密钥为MAC加密密钥2，不输或其他值时为MAC加密密钥


            }
            public class RESPONSE{
                public static final String CARD_TYPE            = "cardType";                   //卡类别 有效值: "MSR"、"IC"、"NFC"
                public static final String CARD_TRACK           = "track";                      // 磁道数据
                public static final String CARD_RDOLDATA        = "rdolData";                   // 磁道数据

                public static final String CARD_PAN             = "pan";                        //主帐号
                public static final String CARD_EXP_DATE        = "expDate";                    //仅当cardType为”MSR”时存在,有效期
                public static final String CARD_SEQNO           = "seqNo";                      //IC卡序列号
                public static final String CARD_SERVICE_CODE    = "serviceCode";                //仅当cardType为”MSR”时存在,服务代码
                public static final String MSR_WITH_IC          = "withIC";                     //磁条卡带IC标记
                public static final String PINBLOCK             = "pinBlock";

                public static final String CVM_TYPE             = "cvmType";                    //有效值: "ONLINE_PIN" 联机PIN、 "SIGN"签名
                public static final String EMV_DATA_TYPE        = "emvResult";                  //"emvDataType"; //EMV鏁版嵁绫诲瀷 鏈夋晥鍊? "REQUEST" 璇锋眰銆?CONFIRM" 纭銆?REVERSAL" 鍐叉
                public static final String EMV_DATA             = "emvData";                    //TLV,EMV数据（55域）
                public static final String PINPAD_RESULT        = "pinpadResult";               //输PIN结果 "OK"已输PIN  "FAIL"输PIN失败  "USER CANCEL"用户取消  "TIMEOUT"超时  "NO PIN"没有输PIN，直接按确认

            }
        }
//=========================卡(CARD)=====================//
        public class APDU{
            public class REQUIRE{
                    public static final String CARD_TYPE  = "cardType";
                    public static final String OPERATE    = "operate";
                    public static final String DATA       = "data";
                    public static final String TIMEOUT    = "timeout";
            }
            public class RESPONSE{
                public static final String DATA  = "data";

            }
        }

        public class MIFARE{
            public class REQUIRE{
                public static final String OPERATE              = "operate";
                public static final String MIFARE_BLOCK         = "block";
                public static final String MIFARE_AUTH_KEY      = "authKey";
                public static final String MIFARE_AUTH_KEY_TYPE = "authKeyType";
                public static final String MIFARE_NUM           = "num";
                public static final String DATA                 = "data";
            }
            public class RESPONSE{
                public static final String DATA  = "data";
            }
        }

//=======================小屏幕(EXSCREEN)======================//
        /*
        * cmd: "exscreen.cmd.qrcode";        //小屏显示二维码 (display the QR code)
        */
        public class DISPLAY_QR_CODE{
            public class REQUIRE{
                public static final String DATA              = "data";
                public static final String PAYTYPE           = "payType";               //有效值："ALIPA","WEPAY",默认为一般QR
                public static final String MESSAGE           = "Msgdata";
            }
        }

        /*
        * cmd: "exscreen.cmd.custom_msg";    //小屏显示用户自定义信息 (display the customer message)
        */
        public class DISPLAY_CUSTOM_MSG{
            public class REQUIRE{
                public static final String DISPLAYTYPE          = "DisType";
                public static final String TRANS_RESPONSE       = "TransRes";
                public static final String FONT_SIZE            = "fontsize";
                public static final String FONT_COLOR           = "fontcolor";
                public static final String BK_COLOR             = "bkcolor";
                public static final String MESSAGE              = "msg";
                public static final String TIMEOUT              = "timeout";
                public static final String DATA                 = "data";
            }
        }

        /*
        * "exscreen.cmd.bitmap";        //小屏图片轮播 (pictures carousel)
        * */
        public class DISPLAY_BITMAPS{
            public class REQUIRE{
                public static final String CAROUSEL_INTERVAL    = "interval";          //轮播时间间隔, 单位 秒
                public static final String CAROUSEL_BEGIN_IDX   = "beginIdx";          //轮播起始图片索引
                public static final String CAROUSEL_BITMAP_NUM  = "palyNum";           //轮播图片的数量
            }

        }

//==============================密码键盘(pinpad)==========================//

        //密码键盘相关
        public class PIN{
            public static final String PIN_APP_ID           = "appId2";
            public static final String KEY_APP_ID           = "appId";                      //仅当磁道加密时需要,默认0x01
            public static final String KEY_MANAGER          = "keyMng";                     //密钥管理体系
            public static final String PIN_LEN_MIN          = "pinMinLen";                  //密码最小长度
            public static final String PIN_LEN_MAX          = "pinMaxLen";                  //密码最大长度
            public static final String PINBLOCK             = "pinBlock";
            public static final String KEY_TYPE             = "keyType";                    //密钥类别      有效值: "KPK"、"MASTER_KEY"、"PIN_KEY"、"MAC_KEY"、"TD_KEY"
            public static final String KEY_FORMAT           = "format";                     //密钥格式。    有效值: "plain"(明文)、"EnByKPK"(KPK加密)、"EnByMK"(主密钥加密)、"TR31"
            public static final String KEY_DATA             = "key";                        //密钥数据
        }

        public class PIN_PROC{
            public class REQUIRE{
                public static final String KEY_APP_ID   = "appId";          //M
                public static final String PIN_APP_ID   = "appId2";         //C
                public static final String PAN          = "pan"   ;         //C
                public static final String AMOUNT_AUTH  = "amountAuth";
                public static final String KEY_MNG      = "keyMng";
                public static final String PIN_FORMAT   = "pinFormat";      //PINBLOCK格式  有效值: "FORMAT0", "FORMAT1", "FORMAT3","PLAIN_TEXT"
                public static final String TIMEOUT      = "timeout";
                public static final String PIN_MINLEN   = "pinMinLen";
                public static final String PIN_MAXLEN   = "pinMaxLen";
                public static final String PINPAD_MODE  = "keypadmode";
            }

            public  class RESPONSE{
                public static final String PINPAD_RESULT = "pinpadResult";
                public static final String PIN_BLOCK     = "pinBlock";
            }
        }

        public class PINPAD_MSG_CONFIRM{
            public class REQUIRE{
                public static final String DATA_TYPE    = "dataType";
                public static final String MSG_LINE1    = "data";
                public static final String MSG_LINE2    = "data1";
                public static final String MSG_LINE3    = "data2";
                public static final String MAXLEN       = "maxlen";
                public static final String TIMEOUT      = "timeout";
                public static final String PINPAD_MODE  = "pinpadmode";
            }

            public  class RESPONSE{

                public static final String NUMBER       = "number";
            }
        }

        public class PINPAD_LOAD_KEY{
            public class REQUIRE{
                public static final String KEY_APP_ID        = "appId";
                public static final String KEY_MANAGER       = "keyMng";
                public static final String KEY_TYPE          = "keyType";
                public static final String KEY_FORMAT        = "format";
                public static final String KEY_DATA          = "key";
                public static final String KEY_CHECK_VALUE   = "checkValue";
                public static final String KSN               = "ksn";
                public static final String CERT_DATA         = "certData";
                public static final String SIGN_DATA         = "keysignData";
            }

        }

        public class KEY_DECOMMISSION{
            public class REQUIRE{
                public static final String APPID    = "appId";
            }

            public  class RESPONSE{
                public static final String KEY_STATE  = "keyState"; //判断密钥是否存在，1：存在 0：不存在
                                                                    //To determine if a key exists(1: exist 0: not exist)
            }
        }

        public class PINPAD_CRYPT{
            public class REQUIRE{
                public static final String OPERATE    = "operate";
                public static final String CRYPT_ALGORITHM  = "algorithm";
                public static final String PADDING    = "padding";
                public static final String KEY_MNG    = "keyMng";
                public static final String APPID      = "appId";
                public static final String KEY_TYPE   = "keyType";
                public static final String KEY        = "key";
                public static final String DATA       = "data";
            }

            public  class RESPONSE{
                public static final String CAL_RESULT = "data";
            }
        }

        public class GET_RANDOM{
            public class REQUIRE{
                public static final String REQ_LENGTH   = "length";                     //随机数长度
            }
            public  class RESPONSE {
                public static final String DATA         = "data";
            }
        }

        public class PINPAD_KSN{
            public class REQUIRE{
                public static final String OPERATE       = "operate";
                public static final String KEY_APP_ID    = "appId";
            }
            public  class RESPONSE {
                public static final String KSN           = "ksn";
            }
        }

        public class PINPAD_CALCULATE_MAC{
            public class REQUIRE{
                public static final String KEY_APP_ID   = "appId";
                public static final String MAC_TYPE     = "macType";
                public static final String KEY_MANAGER  = "keyMng";
                public static final String MAC_RES      = "macRes";
                public static final String DATA         = "data";
            }

            public  class RESPONSE{
                public static final String MAC          = "mac";
            }
        }

        public class DIGITAL_SIGN{

            public  class RESPONSE{
                public static final String FILE_DATA    = "fileData";
            }
        }
        
        //=====================文件存储(File storage)=========================//
        public class STORE_FILE{
            public class REQUIRE{
                public static final String PREFIX    = "prefix";
                public static final String FILE_NAME = "fileName";
                public static final String FILE_LEN  = "fileLen";
                public static final String FILE_PATH = "filePath";
                public static final String FILE_DATA = "fileData";
            }
        }

        public class READ_FILE{
            public class REQUIRE{
                public static final String PREFIX = "prefix";
                public static final String FILE_NAME = "fileName";
            }
            public  class RESPONSE{
                public static final String FILE_LEN  = "fileLen";
                public static final String FILE_DATA            = "fileData";
            }
        }

        public class DELETE_FILE{
            public class REQUIRE{
                public static final String PREFIX = "prefix";
                public static final String FILE_NAME = "fileName";
            }
        }

        public class LIST_FILES{
            public class REQUIRE{
                public static final String PREFIX = "prefix";
                public static final String OFFSET = "offset";
            }
            public  class RESPONSE{
                public static final String FILE_NAME  = "fileNames";
                public static final String FILE_NUM   = "fileNum";
            }
        }

        public class STORE_BITMAP {
            public class REQUIRE {
                public static final String TYPE      = "type";      //有效值: "NORMAL" 常规图   "LOGO" logoc
                public static final String INDEX     = "index";     //图片索引，仅type为"NORMAL"时需要
                public static final String FILE_LEN  = "fileLen";
                public static final String FILE_PATH = "filePath";
                public static final String FILE_DATA = "fileData";
            }
        }

//=========================打印机(Printer)==========================//

        public class PRINTER_TEXT{
            public class REQUIRE {
                public static final String PRINT_TEXTPOSITION   = "position";                   //文字位置 int
                public static final String PRINT_TEXTHEIGHT     = "textheight";
                public static final String PRINT_TYPEFACE       = "typeface";                   //设置自定义字体，导入字体文件路径，类型string
                public static final String PRINT_RIGHTMARGIN    = "rightmargin";                //页右边间距
                public static final String PRINT_LEFTMARGIN     = "leftmargin";                 //页左边间距
                public static final String PRINT_LINEGAP        = "linegap";                    //行间距 有效值：int类型 大于0即可
                public static final String PRINT_SIZE           = "size";                       //字体大小 大于0即可
                public static final String PRINT_FONTTYPE       = "fonttype";                   //字体类型 四个参数：“normal” "bold" ""
                public static final String PRINT_TEXTWEIGHT     = "textweight";
                public static final String DATA                 = "data";
            }
        }

        public class PRINTER_FEED{
            public class REQUIRE {
                public static final String PRINT_FEEDUNIT       = "unit";
                public static final String PRINT_LINE           = "lines";                      //走纸 几行
            }
        }


    }
}
