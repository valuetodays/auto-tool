package cn.valuetodays.autotool.mota.common;

import lombok.Getter;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-08
 */
public final class MotaGameItemEnums {
    @Getter
    public enum Type {
        ENEMY("敌人"),
        YELLOW_KEY("黄钥匙"),
        YELLOW_DOOR("黄门"),
        RED_KEY("红钥匙"),
        RED_DOOR("红门"),
        BLUE_KEY("蓝钥匙"),
        BLUE_DOOR("蓝门"),
        ATK_DIAMOND("攻击宝石"),
        DFD_DIAMOND("防御宝石"),
        HP_BOTTLE("血瓶"),
        GOLD("金币"),
        WEAPON("武器"),
        SHIELD("盾牌");

        private final String title;

        Type(String title) {
            this.title = title;
        }

    }
}
