package com.design.decorator;

import com.design.decorator.arm.IEquip;

/**
 * Created by tianlei on 2017/十二月/27.
 */
public class YellowGemDecorator implements IEquipDecorator {

    private IEquip equip;

    public YellowGemDecorator(IEquip equip) {
        this.equip = equip;
    }

    @Override
    public int caculateAttack() {
        return equip.caculateAttack() + 5;
    }

    /**
     * 装备的描述
     *
     * @return s
     */
    @Override
    public String description() {

        return equip.description() + "+ 黄宝石";
    }
}
