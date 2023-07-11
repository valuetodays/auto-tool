package cn.valuetodays.autotool.mota.common;

import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-10
 */
@Data
public class MonsterFightData {
    private int atk;
    private int def;
    private int hp;
    private Special special = new Special();

    public void enableAtkBeforeHero() {
        special.atkBeforeHero = true;
    }

    public void disableAtkBeforeHero() {
        special.atkBeforeHero = false;
    }

    public void enableIgnoreHeroDef() {
        special.ignoreHeroDef = true;
    }

    public void disableIgnoreHeroDef() {
        special.ignoreHeroDef = true;
    }

    @Data
    public static class Special {
        // 固伤，必为先攻
        private int fixDamage;
        // 先攻
        private boolean atkBeforeHero;
        // 忽略主角防御
        private boolean ignoreHeroDef;
    }
}
