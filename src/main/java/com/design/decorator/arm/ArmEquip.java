package com.design.decorator.arm;

/**
 * 武器攻击力 10
 * Created by tianlei on 2017/十二月/27.
 */
public class ArmEquip implements IEquip {

    @Override
    public int caculateAttack() {
        return 10;
    }

    @Override
    public String description() {
        return "武器";
    }
}
