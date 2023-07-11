package cn.valuetodays.autotool.mota.mota24;

import cn.valuetodays.autotool.mota.common.Damage;
import cn.valuetodays.autotool.mota.common.DamageCalculater;
import cn.valuetodays.autotool.mota.common.Hero;
import cn.valuetodays.autotool.mota.common.MonsterFightData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-04-14 18:33
 */
@Slf4j
public class DamageCalculaterTest {

    @Test
    public void aa() {
        MonsterFightData md = new MonsterFightData();
        md.setHp(1094);
        md.setAtk(251);
        md.setDef(703);
//        md.enableAtkBeforeHero();
        md.disableAtkBeforeHero();
        md.enableIgnoreHeroDef();

        Hero hero = new Hero();
        hero.hp = (12330);
        hero.atk = (937);
        hero.dfd = (1106);
        Damage damage = DamageCalculater.calculateDamage(hero, md);
        log.info("damage: {}", damage);
    }

}
