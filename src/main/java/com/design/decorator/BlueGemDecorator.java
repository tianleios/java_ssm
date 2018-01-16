package com.design.decorator;

import com.design.decorator.arm.IEquip;

/**
 * Created by tianlei on 2017/十二月/27.
 */
public class BlueGemDecorator implements IEquipDecorator {

    private IEquip equip;

    public BlueGemDecorator(IEquip equip) {
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

        return equip.description() + "+ 蓝宝石";
    }
}
