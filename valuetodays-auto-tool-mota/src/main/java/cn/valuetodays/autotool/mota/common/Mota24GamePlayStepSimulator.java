package cn.valuetodays.autotool.mota.common;

import cn.valuetodays.autotool.common.utils.JsonUtils;
import cn.valuetodays.autotool.mota.common.item.Mota24Item;
import cn.valuetodays.autotool.mota.common.step.BranchSteps;
import cn.valuetodays.autotool.mota.common.step.SimpleSteps;
import cn.valuetodays.autotool.mota.common.step.Step;
import cn.valuetodays.autotool.mota.common.step.Steps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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


    private boolean meetItems(Steps steps, Hero hero, Map<String, Mota24Item> nameAndItemMap) {
        if (steps instanceof BranchSteps) {
            return meetItem((BranchSteps) steps, hero, nameAndItemMap);
        } else if (steps instanceof SimpleSteps) {
            return meetItem((SimpleSteps) steps, hero, nameAndItemMap);
        } else {
            throw new RuntimeException("unknown steps: " + steps);
        }
    }

    private boolean meetItem(SimpleSteps simpleSteps, Hero hero, Map<String, Mota24Item> nameAndItemMap) {
        List<Step> steps = simpleSteps.getSteps();
        for (Step step : steps) {
            boolean b = meetItem(step, hero, nameAndItemMap);
            if (!b) {
                log.info("路线不可行");
                return b;
            }
        }
        return true;
    }

    private boolean meetItem(BranchSteps branchSteps, Hero hero, Map<String, Mota24Item> nameAndItemMap) {
        Map<String, SimpleSteps> map = branchSteps.getBranchSteps();
        Hero oldHeroObj = JsonUtils.fromJson(JsonUtils.toJson(hero), Hero.class);
        Map<String, Pair<Boolean, Hero>> branchHeroMap = new HashMap<>();
        for (Map.Entry<String, SimpleSteps> entry : map.entrySet()) {
            String key = entry.getKey();
            SimpleSteps value = entry.getValue();
            Hero tmpHeroObj = JsonUtils.fromJson(JsonUtils.toJson(oldHeroObj), Hero.class);
            log.info("开始分支 #{}", key);
            boolean b = meetItem(value, tmpHeroObj, nameAndItemMap);
            assert tmpHeroObj != null;
            log.info("分支结束 #{}, 可行={}, hero={}", key, tmpHeroObj.hp > 0, tmpHeroObj);
            branchHeroMap.put(key, Pair.of(b, tmpHeroObj));
        }
        for (Map.Entry<String, Pair<Boolean, Hero>> entry : branchHeroMap.entrySet()) {
            String key = entry.getKey();
            Pair<Boolean, Hero> pair = entry.getValue();
            Boolean left = pair.getLeft();
            Hero value = pair.getValue();
            log.info("分支 【{}】, 可行={}, hero={}", key, left, value);
        }
        List<Map.Entry<String, Pair<Boolean, Hero>>> sortedList = branchHeroMap.entrySet().stream()
            .filter(e -> BooleanUtils.isTrue(e.getValue().getLeft()))
            .sorted(Comparator.comparingInt(e -> e.getValue().getRight().hp))
            .collect(Collectors.toList());
        for (Map.Entry<String, Pair<Boolean, Hero>> stringPairEntry : sortedList) {
            log.info(" -> {}", stringPairEntry);
        }
        return true;
    }

    private boolean meetItem(Step step, Hero hero, Map<String, Mota24Item> nameAndItemMap) {
        String item = step.getItem();
        if (item.startsWith("RED_KEY#") || item.startsWith("红钥匙#")) {
            hero.redKeys++;
            log.info("获得红钥匙");
        } else if (item.startsWith("RED_DOOR#")) {
            if (hero.redKeys > 0) {
                hero.redKeys--;
                log.info("使用红钥匙开红门");
            } else {
                log.error("no redKeys");
                return false;
            }
        } else if (item.startsWith("YELLOW_KEY#") || item.startsWith("黄钥匙#")) {
            hero.yellowKeys++;
            log.info("获得黄钥匙");
        } else if (item.startsWith("YELLOW_DOOR#")) {
            if (hero.yellowKeys > 0) {
                hero.yellowKeys--;
                log.info("使用黄钥匙开黄门");
            } else {
                log.error("no yellowKey");
                return false;
            }
        } else if (item.startsWith("BLUE_KEY#") || item.startsWith("蓝钥匙#")) {
            hero.blueKeys++;
            log.info("获得蓝钥匙");
        } else if (item.startsWith("BLUE_DOOR#")) {
            if (hero.blueKeys > 0) {
                hero.blueKeys--;
                log.info("使用蓝钥匙开蓝门");
            } else {
                log.error("no blueKeys");
                return false;
            }
        } else if (item.equalsIgnoreCase("up")) {
            log.info("上楼");
            return true;
        } else if (item.startsWith("shop#")) {
//            #1#gold25#atk4
            String substring = item.substring("shop#".length());
            String goldAndAtk = substring.substring(substring.indexOf("#gold") + "#gold".length());
            String[] arr = StringUtils.split(goldAndAtk, "#");
            if (arr.length != 2) {
                log.error("商店消费配置有误");
                return false;
            }
            String goldString = arr[0];
            int costGold = Integer.parseInt(goldString);
            if (hero.gold < costGold) {
                log.error("金币不足 {}<{}", hero.gold, costGold);
                return false;
            }
            hero.gold -= costGold;
            String attrString = arr[1];
            String info = "增加了";
            if (StringUtils.contains(attrString, "hp")) {
                String hpString = StringUtils.replace(arr[1], "hp", "");
                info += (hpString + "hp");
                hero.hp += Integer.parseInt(hpString);
            } else if (StringUtils.contains(attrString, "atk")) {
                String atkString = StringUtils.replace(arr[1], "atk", "");
                info += (atkString + "攻击力");
                hero.atk += Integer.parseInt(atkString);
            } else if (StringUtils.contains(attrString, "dfd")) {
                String dfdString = StringUtils.replace(arr[1], "dfd", "");
                info += (dfdString + "防御力");
                hero.dfd += Integer.parseInt(dfdString);
            }
            log.info("花费了金币{}，{}", goldString, info);
        } else {
            if (!item.contains("#")) {
                log.error("missing # in name. item={}", item);
                return false;
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
                log.info("fight, damage={}", damage);
                if (Objects.isNull(damage)) {
                    log.error("can not fight with enemy={}", mota24Item);
                    return false;
                } else {
                    hero.hp -= damage.getMonsterToHero();
                    hero.gold += mota24Item.getGold();
                    hero.exp += mota24Item.getExp();
                }
            } else if (MotaGameItemEnums.Type.WEAPON == type) {
                hero.atk += mota24Item.getAttack();
                log.info("获得武器：{}", name);
            } else if (MotaGameItemEnums.Type.SHIELD == type) {
                hero.dfd += mota24Item.getDefend();
                log.info("获得盾牌：{}", name);
            } else if (MotaGameItemEnums.Type.ATK_DIAMOND == type) {
                hero.atk += mota24Item.getAttack();
                log.info("获得红宝石：{}", name);
            } else if (MotaGameItemEnums.Type.DFD_DIAMOND == type) {
                hero.dfd += mota24Item.getDefend();
                log.info("获得蓝宝石：{}", name);
            } else if (MotaGameItemEnums.Type.HP_BOTTLE == type) {
                hero.hp += mota24Item.getHp();
                log.info("获得血瓶：{}", name);
            } else {
                log.error("unknown item type={}", type);
            }
        }
        return true;
    }

}
