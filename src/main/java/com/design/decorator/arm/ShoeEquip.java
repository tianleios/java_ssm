package com.design.decorator.arm;

/**
 * Created by tianlei on 2017/十二月/27.
 */
public class ShoeEquip implements IEquip {

    @Override
    public int caculateAttack() {
        return 5;
    }

    /**
     * 装备的描述
     *
     * @return s
     */
    @Override
    public String description() {
        return "鞋子";
    }
}
