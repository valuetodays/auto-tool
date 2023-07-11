package cn.valuetodays.autotool.mota.common;

import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-10
 */
@Data
public class Damage {
    private int monsterToHero;
    private int heroToMonster;
    // 回合数
    private int turn;
    private int perDamage;
    private int heroPerDamage;
    private int initDamage;
}
