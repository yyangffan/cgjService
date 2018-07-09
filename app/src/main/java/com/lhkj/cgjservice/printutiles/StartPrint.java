package com.lhkj.cgjservice.printutiles;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by user on 2018/1/2.
 */

public class StartPrint {
    private int record;
    private String[] mStrings = new String[]{"CP437", "CP850", "CP860", "CP863", "CP865", "CP857", "CP737", "CP928", "Windows-1252", "CP866", "CP852", "CP858", "CP874", "Windows-775", "CP855", "CP862", "CP864", "GB18030", "BIG5", "KSC5601", "utf-8"};

    private StartPrint() {
        record = 17;

    }

    public static StartPrint getInstance(Context context) {
//        if (BluetoothUtil.connectBlueTooth(context)) {
//            Toast.makeText(context, "蓝牙连接成功", Toast.LENGTH_SHORT).show();
//        }else {
//
//        }
        return SingetonHolder.instance;
    }

    private static class SingetonHolder {
        private static StartPrint instance = new StartPrint();
    }
    public  void  printByBluTooth(String content) {
        try {
//                BluetoothUtil.sendData(ESCUtil.boldOn());
            BluetoothUtil.sendData(ESCUtil.boldOff());
//                BluetoothUtil.sendData(ESCUtil.underlineWithOneDotWidthOn());
            BluetoothUtil.sendData(ESCUtil.underlineOff());

            if (record < 17) {
                BluetoothUtil.sendData(ESCUtil.singleByte());
                BluetoothUtil.sendData(ESCUtil.setCodeSystemSingle(codeParse(record)));
            } else {
                BluetoothUtil.sendData(ESCUtil.singleByteOff());
                BluetoothUtil.sendData(ESCUtil.setCodeSystem(codeParse(record)));
            }

            BluetoothUtil.sendData(content.getBytes(mStrings[record]));
            BluetoothUtil.sendData(ESCUtil.nextLine(3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte codeParse(int value) {
        byte res = 0x00;
        switch (value) {
            case 0:
                res = 0x00;
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                res = (byte) (value + 1);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                res = (byte) (value + 8);
                break;
            case 12:
                res = 21;
                break;
            case 13:
                res = 33;
                break;
            case 14:
                res = 34;
                break;
            case 15:
                res = 36;
                break;
            case 16:
                res = 37;
                break;
            case 17:
            case 18:
            case 19:
                res = (byte) (value - 17);
                break;
            case 20:
                res = (byte) 0xff;
                break;
        }
        return (byte) res;
    }



}
