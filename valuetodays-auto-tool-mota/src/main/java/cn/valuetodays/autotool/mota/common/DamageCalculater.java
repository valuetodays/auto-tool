package cn.valuetodays.autotool.mota.common;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-10
 */
public final class DamageCalculater {
    private DamageCalculater() {
        throw new IllegalStateException("util class");
    }

    public static Damage calculateDamage(Hero hero, MonsterFightData monster) {
        // 战前造成的额外伤害（可被魔防抵消）
        int init_damage = 0;

        int heroHp = hero.hp;
        int heroAtk = hero.atk;
        int heroDef = hero.dfd;

        int monsterHp = monster.getHp();
        int monsterAtk = monster.getAtk();
        int monsterDef = monster.getDef();

        final int fixDamage = monster.getSpecial().getFixDamage();
        int fixDamageToUse = fixDamage;
        if (fixDamageToUse <= 0) {
            fixDamageToUse = 0;
        }
        int heroDefToUse = heroDef;
        if (monster.getSpecial().isIgnoreHeroDef()) {
            heroDefToUse = 0;
        }
        // 每回合怪物对勇士造成的战斗伤害
        int per_damage = monsterAtk - heroDefToUse;
        // 战斗伤害不能为负值
        per_damage = Math.max(per_damage, 0);


        // 2连击 & 3连击 & N连击
//        if (core.hasSpecial(mon_special, 4)) per_damage *= 2;

        if (monster.getSpecial().isAtkBeforeHero()) {
            init_damage += per_damage;
        }


        // 勇士每回合对怪物造成的伤害
        int hero_per_damage = Math.max(heroAtk - monsterDef, 0);
        // 如果没有破防，则不可战斗
        if (hero_per_damage <= 0) {
            return null;
        }

        // 勇士的攻击回合数；为怪物生命除以每回合伤害向上取整
        int turn = (monsterHp / hero_per_damage) + (monsterHp % hero_per_damage > 0 ? 1 : 0);

        // 最终伤害：初始伤害 + 怪物对勇士造成的伤害
        int damageValue = fixDamageToUse + init_damage + (turn - 1) * per_damage;

        Damage damageObj = new Damage();
        damageObj.setMonsterToHero(damageValue);
        damageObj.setTurn(turn);
        damageObj.setPerDamage(per_damage);
        damageObj.setHeroPerDamage(hero_per_damage);
        damageObj.setInitDamage(init_damage);
        return damageObj;
    }
}
