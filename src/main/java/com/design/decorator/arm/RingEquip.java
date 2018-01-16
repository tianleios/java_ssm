package com.design.decorator.arm;

/**
 * Created by tianlei on 2017/十二月/27.
 */
public class RingEquip implements IEquip {
    @Override
    public int caculateAttack() {
        return 5;
    }

    @Override
    public String description() {
        return "戒指";
    }
}
