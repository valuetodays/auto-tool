package cn.valuetodays.autotool.mota.common;

import cn.valuetodays.autotool.common.utils.JsonUtils;
import cn.valuetodays.autotool.mota.common.item.Mota24Item;
import cn.valuetodays.autotool.mota.common.step.BranchSteps;
import cn.valuetodays.autotool.mota.common.step.SimpleSteps;
import cn.valuetodays.autotool.mota.common.step.Step;
import cn.valuetodays.autotool.mota.common.step.Steps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-11
 */
@Slf4j
public class Mota24GamePlayStepSimulator {
    public void simulate(Hero hero, List<Steps> stepsList, Map<String, Mota24Item> nameAndItemMap) {
        for (Steps steps : stepsList) {
            meetItems(steps, hero, nameAndItemMap);
        }
        printHero(hero);
    }

    private void printHero(Hero hero) {
        log.info("hero: {}", hero);
    }


    private void meetItems(Steps steps, Hero hero, Map<String, Mota24Item> nameAndItemMap) {
        if (steps instanceof BranchSteps) {
            meetItem((BranchSteps) steps, hero, nameAndItemMap);
        } else if (steps instanceof SimpleSteps) {
            meetItem((SimpleSteps) steps, hero, nameAndItemMap);
        } else {
            throw new RuntimeException("unknown steps: " + steps);
        }
    }

    private void meetItem(SimpleSteps simpleSteps, Hero hero, Map<String, Mota24Item> nameAndItemMap) {
        List<Step> steps = simpleSteps.getSteps();
        for (Step step : steps) {
            meetItem(step, hero, nameAndItemMap);
        }
    }

    private void meetItem(BranchSteps branchSteps, Hero hero, Map<String, Mota24Item> nameAndItemMap) {
        Map<String, SimpleSteps> map = branchSteps.getBranchSteps();
        Hero oldHeroObj = JsonUtils.fromJson(JsonUtils.toJson(hero), Hero.class);
        for (Map.Entry<String, SimpleSteps> entry : map.entrySet()) {
            String key = entry.getKey();
            SimpleSteps value = entry.getValue();
            Hero tmpHeroObj = JsonUtils.fromJson(JsonUtils.toJson(oldHeroObj), Hero.class);
            log.info("开始分支 #{}", key);
            meetItem(value, tmpHeroObj, nameAndItemMap);
            log.info("分支结束 #{}, 可行={}, hero={}", key, tmpHeroObj.hp > 0, tmpHeroObj);
        }
    }

    private void meetItem(Step step, Hero hero, Map<String, Mota24Item> nameAndItemMap) {
        String item = step.getItem();
        if (item.startsWith("RED_KEY#") || item.startsWith("红钥匙#")) {
            hero.redKeys++;
        } else if (item.startsWith("RED_DOOR#")) {
            if (hero.redKeys > 0) {
                hero.redKeys--;
            } else {
                log.error("no redKeys");
            }
        } else if (item.startsWith("YELLOW_KEY#") || item.startsWith("黄钥匙#")) {
            hero.yellowKeys++;
        } else if (item.startsWith("YELLOW_DOOR#")) {
            if (hero.yellowKeys > 0) {
                hero.yellowKeys--;
            } else {
                log.error("no yellowKey");
            }
        } else if (item.startsWith("BLUE_KEY#") || item.startsWith("蓝钥匙#")) {
            hero.blueKeys++;
        } else if (item.startsWith("BLUE_DOOR#")) {
            if (hero.blueKeys > 0) {
                hero.blueKeys--;
            } else {
                log.error("no blueKeys");
            }
        } else if (item.equalsIgnoreCase("up")) {
            // nothing
        } else {
            if (!item.contains("#")) {
                log.error("missing # in name. item={}", item);
                return;
            }
            String name = item.substring(0, item.indexOf("#"));
            Mota24Item mota24Item = nameAndItemMap.get(name);
            if (Objects.isNull(mota24Item)) {
                log.error("no item with name={}", name);
            }
            MotaGameItemEnums.Type type = mota24Item.getType();
            if (MotaGameItemEnums.Type.ENEMY == type) {
                MonsterFightData monsterFightData = new MonsterFightData();
                monsterFightData.setHp(mota24Item.getHp());
                monsterFightData.setAtk(mota24Item.getAttack());
                monsterFightData.setDef(mota24Item.getDefend());
                Damage damage = DamageCalculater.calculateDamage(hero, monsterFightData);
                log.info("damage={}", damage);
                if (Objects.isNull(damage)) {
                    log.error("can not fight with enemy={}", mota24Item);
                } else {
                    hero.hp -= damage.getMonsterToHero();
                }
            } else if (MotaGameItemEnums.Type.WEAPON == type) {
                hero.atk += mota24Item.getAttack();
            } else if (MotaGameItemEnums.Type.SHIELD == type) {
                hero.dfd += mota24Item.getDefend();
            } else if (MotaGameItemEnums.Type.ATK_DIAMOND == type) {
                hero.atk += mota24Item.getAttack();
            } else if (MotaGameItemEnums.Type.DFD_DIAMOND == type) {
                hero.dfd += mota24Item.getDefend();
            } else if (MotaGameItemEnums.Type.HP_BOTTLE == type) {
                hero.hp += mota24Item.getHp();
            } else {
                log.error("unknown item type={}", type);
            }
        }
    }

}
