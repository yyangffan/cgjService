package com.lhkj.cgjservice.printutiles;

import com.lhkj.cgjservice.reponse.SumPrintResponse;
import com.lhkj.cgjservice.reponse.YhqAginReponse;

/*进行打印的工具类*/

public class PrintUtil {
    private static PrintUtil printUtil;

    private PrintUtil() {
    }

    public static PrintUtil getInstance() {
        if (printUtil == null) {
            synchronized (PrintUtil.class) {
                if (printUtil == null) {
                    printUtil = new PrintUtil();
                }
            }
        }
        return printUtil;
    }

    /*优惠券打印*/
    public void PrintYhq(YhqAginReponse bean) {
        AidlUtil.getInstance().printText(getPrintContent(bean), 24, false, false);/*Aidl打印*/
    }

    private String getPrintContent(YhqAginReponse bean) {
        String couponName = (String) bean.getOrder().getName();
        StringBuilder builder = new StringBuilder();
        builder.append("      油品惠兑换凭证");
        builder.append("\n兑换日期:" + bean.getDate());
        builder.append("\n账号:" + bean.getUser().getMobile());
        builder.append("\n车牌号:" + bean.getUser().getCarNumber());
        builder.append("\n客户姓名:" + bean.getUser().getNickname());
        builder.append("\n\n员工编号:" + bean.getStaff().getNumber());
        builder.append("\n员工姓名:" + bean.getStaff().getName());
        builder.append("\n账号:" + bean.getStaff().getAccount());
        builder.append("\n优惠券名称" + "  数量" + " 单价" + " 金额\n");
        builder.append(couponName + "\n");
        builder.append("             " + "1" + "  " + bean.getOrder().getMoney() + "  " + bean.getOrder().getMoney());
//        builder.append("\n\n\n\n兑换商品使用现金:" + "200.00  " + "使用积分:" + "200");
        builder.append("\n客户签名:\n\n\n\n\n\n\n");
        return builder.toString();
    }

    /*商品打印*/
    public void PrintGoods(SumPrintResponse bean) {
        AidlUtil.getInstance().printText(getPrintContent(bean), 24, false, false);/*Aidl打印*/
    }

    private String getPrintContent(SumPrintResponse bean) {
        String couponName = (String) bean.getOrder().getGoodsName();
        if (couponName != null) {
        } else {
            couponName = "                    ";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("      油品惠兑换凭证");
        builder.append("\n兑换日期:" + bean.getOrder().getConfirmTime());
        builder.append("\n账号:" + bean.getUser().getMobile());
        builder.append("\n车牌号:" + bean.getUser().getCar_number());
        builder.append("\n客户姓名:" + bean.getUser().getNickname());
        builder.append("\n\n员工编号:" + bean.getStaff().getNumber());
        builder.append("\n员工姓名:" + bean.getStaff().getName());
        builder.append("\n账号:" + bean.getStaff().getAccount());
        builder.append("\n商品名称" + "  数量" + " 单价" + " 金额\n");
        builder.append(couponName + "\n");
        builder.append("           1  " + bean.getOrder().getMoney() + "  " + bean.getOrder().getMoney());
        builder.append("\n\n\n\n" + "兑换商品\n" + "使用现金:" + bean.getOrder().getMoney() + "\n使用积分:" + bean.getOrder().getJifen());
        builder.append("\n客户签名:\n\n\n\n\n\n\n");
        return builder.toString();
    }


}
