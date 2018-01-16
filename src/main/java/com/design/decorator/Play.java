package com.design.decorator;

import com.design.decorator.arm.ArmEquip;
import com.design.decorator.arm.IEquip;

/**
 * Created by tianlei on 2017/十二月/27.
 */
public class Play {

    public static void  main(String[] args) {

        ArmEquip armEquip = new ArmEquip();

        // 一个镶嵌1颗黄宝石，1颗蓝宝石的靴子
        IEquip equip = new YellowGemDecorator(new BlueGemDecorator(armEquip));

        System.out.println("攻击力  : " + equip.caculateAttack());
        System.out.println("描述 :" + equip.description());
    }

}
