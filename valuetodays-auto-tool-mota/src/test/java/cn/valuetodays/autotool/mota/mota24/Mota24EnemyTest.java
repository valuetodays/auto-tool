package cn.valuetodays.autotool.mota.mota24;

import cn.valuetodays.autotool.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-09
 */
@Slf4j
public class Mota24EnemyTest {
    @Test
    public void test() {
        String json  = "var enemys_fcae963b_31c9_42b4_b48c_bb48d09f3f80 = \n"
            + "{\n"
            + "\t\"greenSlime\": {\"name\":\"绿头怪\",\"hp\":50,\"atk\":20,\"def\":1,\"money\":1,\"exp\":1,\"special\":0},\n"
            + "\t\"redSlime\": {\"name\":\"红头怪\",\"hp\":70,\"atk\":15,\"def\":2,\"money\":2,\"exp\":2,\"special\":0},\n"
            + "\t\"blackSlime\": {\"name\":\"青头怪\",\"hp\":200,\"atk\":35,\"def\":10,\"money\":5,\"exp\":5,\"special\":0},\n"
            + "\t\"slimelord\": {\"name\":\"怪王\",\"hp\":700,\"atk\":250,\"def\":125,\"money\":32,\"exp\":30,\"special\":0},\n"
            + "\t\"bat\": {\"name\":\"小蝙蝠\",\"hp\":100,\"atk\":20,\"def\":5,\"money\":3,\"exp\":3,\"special\":0},\n"
            + "\t\"bigBat\": {\"name\":\"大蝙蝠\",\"hp\":150,\"atk\":65,\"def\":30,\"money\":10,\"exp\":8,\"special\":0},\n"
            + "\t\"redBat\": {\"name\":\"红蝙蝠\",\"hp\":550,\"atk\":160,\"def\":90,\"money\":25,\"exp\":20,\"special\":0},\n"
            + "\t\"vampire\": {\"name\":\"冥灵魔王\",\"hp\":30000,\"atk\":1700,\"def\":1500,\"money\":250,\"exp\":220,\"special\":0},\n"
            + "\t\"vampire2\": {\"name\":\"冥灵魔王2\",\"hp\":45000,\"atk\":2550,\"def\":2250,\"money\":312,\"exp\":275,\"special\":0},\n"
            + "\t\"vampire3\": {\"name\":\"冥灵魔王3\",\"hp\":60000,\"atk\":3400,\"def\":3000,\"money\":390,\"exp\":343,\"special\":0},\n"
            + "\t\"skeleton\": {\"name\":\"骷髅人\",\"hp\":110,\"atk\":25,\"def\":5,\"money\":5,\"exp\":4,\"special\":0},\n"
            + "\t\"skeletonSoilder\": {\"name\":\"骷髅士兵\",\"hp\":150,\"atk\":40,\"def\":20,\"money\":8,\"exp\":6,\"special\":0},\n"
            + "\t\"skeletonCaptain\": {\"name\":\"骷髅队长\",\"hp\":400,\"atk\":90,\"def\":50,\"money\":15,\"exp\":12,\"special\":0},\n"
            + "\t\"ghostSkeleton\": {\"name\":\"冥队长\",\"hp\":2500,\"atk\":900,\"def\":850,\"money\":84,\"exp\":75,\"special\":0},\n"
            + "\t\"zombie\": {\"name\":\"兽面人\",\"hp\":300,\"atk\":75,\"def\":45,\"money\":13,\"exp\":10,\"special\":0},\n"
            + "\t\"zombieKnight\": {\"name\":\"兽面武士\",\"hp\":900,\"atk\":450,\"def\":330,\"money\":50,\"exp\":50,\"special\":0},\n"
            + "\t\"rock\": {\"name\":\"石头人\",\"hp\":500,\"atk\":115,\"def\":65,\"money\":15,\"exp\":15,\"special\":0},\n"
            + "\t\"slimeMan\": {\"name\":\"影子战士\",\"hp\":3100,\"atk\":1150,\"def\":1050,\"money\":92,\"exp\":80,\"special\":0},\n"
            + "\t\"bluePriest\": {\"name\":\"初级法师\",\"hp\":125,\"atk\":50,\"def\":25,\"money\":10,\"exp\":7,\"special\":0},\n"
            + "\t\"redPriest\": {\"name\":\"高级法师\",\"hp\":100,\"atk\":200,\"def\":110,\"money\":30,\"exp\":25,\"special\":0},\n"
            + "\t\"brownWizard\": {\"name\":\"麻衣法师\",\"hp\":250,\"atk\":120,\"def\":70,\"money\":20,\"exp\":17,\"special\":22,\"damage\":100},\n"
            + "\t\"redWizard\": {\"name\":\"红衣法师\",\"hp\":500,\"atk\":400,\"def\":260,\"money\":47,\"exp\":45,\"special\":22,\"damage\":300},\n"
            + "\t\"yellowGuard\": {\"name\":\"初级卫兵\",\"hp\":450,\"atk\":150,\"def\":90,\"money\":22,\"exp\":19,\"special\":0},\n"
            + "\t\"blueGuard\": {\"name\":\"中级卫兵\",\"hp\":1250,\"atk\":500,\"def\":400,\"money\":55,\"exp\":55,\"special\":0},\n"
            + "\t\"redGuard\": {\"name\":\"高级卫兵\",\"hp\":1500,\"atk\":560,\"def\":460,\"money\":60,\"exp\":60,\"special\":0},\n"
            + "\t\"swordsman\": {\"name\":\"双手剑士\",\"hp\":1200,\"atk\":620,\"def\":520,\"money\":65,\"exp\":75,\"special\":0},\n"
            + "\t\"soldier\": {\"name\":\"冥战士\",\"hp\":2000,\"atk\":680,\"def\":590,\"money\":70,\"exp\":65,\"special\":0},\n"
            + "\t\"yellowKnight\": {\"name\":\"金卫士\",\"hp\":850,\"atk\":350,\"def\":200,\"money\":45,\"exp\":40,\"special\":0},\n"
            + "\t\"redKnight\": {\"name\":\"金队长\",\"hp\":900,\"atk\":750,\"def\":650,\"money\":77,\"exp\":70,\"special\":0},\n"
            + "\t\"darkKnight\": {\"name\":\"灵武士\",\"hp\":1200,\"atk\":980,\"def\":900,\"money\":88,\"exp\":75,\"point\":0,\"special\":0},\n"
            + "\t\"blackKing\": {\"name\":\"黑衣魔王\",\"hp\":1000,\"atk\":500,\"def\":0,\"money\":1000,\"exp\":1000,\"point\":0,\"special\":0,\"notBomb\":true},\n"
            + "\t\"yellowKing\": {\"name\":\"黄衣魔王\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"greenKing\": {\"name\":\"青衣武士\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"blueKnight\": {\"name\":\"蓝骑士\",\"hp\":100,\"atk\":120,\"def\":0,\"money\":9,\"exp\":0,\"point\":0,\"special\":8},\n"
            + "\t\"goldSlime\": {\"name\":\"黄头怪\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"poisonSkeleton\": {\"name\":\"紫骷髅\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"poisonBat\": {\"name\":\"紫蝙蝠\",\"hp\":100,\"atk\":120,\"def\":0,\"money\":14,\"exp\":0,\"point\":0,\"special\":13},\n"
            + "\t\"steelRock\": {\"name\":\"铁面人\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"skeletonPriest\": {\"name\":\"骷髅法师\",\"hp\":100,\"atk\":100,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":18,\"value\":20},\n"
            + "\t\"skeletonKing\": {\"name\":\"骷髅王\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"skeletonWizard\": {\"name\":\"骷髅巫师\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"redSkeletonCaption\": {\"name\":\"骷髅武士\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"badHero\": {\"name\":\"迷失勇者\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"demon\": {\"name\":\"魔神武士\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"demonPriest\": {\"name\":\"魔神法师\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"goldHornSlime\": {\"name\":\"金角怪\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"redKing\": {\"name\":\"红衣魔王\",\"hp\":15000,\"atk\":1000,\"def\":1000,\"money\":100,\"exp\":100,\"point\":0,\"special\":0},\n"
            + "\t\"whiteKing\": {\"name\":\"白衣武士\",\"hp\":1300,\"atk\":300,\"def\":150,\"money\":40,\"exp\":35,\"point\":0,\"special\":11,\"value\":null,\"vampire\":0.25},\n"
            + "\t\"blackMagician\": {\"name\":\"灵法师\",\"hp\":1500,\"atk\":830,\"def\":730,\"money\":80,\"exp\":70,\"point\":0,\"special\":11,\"value\":null,\"add\":false,\"notBomb\":false,\"vampire\":0.3333333333333333},\n"
            + "\t\"silverSlime\": {\"name\":\"银头怪\",\"hp\":100,\"atk\":120,\"def\":0,\"money\":15,\"exp\":0,\"point\":0,\"special\":14},\n"
            + "\t\"swordEmperor\": {\"name\":\"剑圣\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"whiteHornSlime\": {\"name\":\"尖角怪\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"badPrincess\": {\"name\":\"痛苦魔女\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"badFairy\": {\"name\":\"黑暗仙子\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"grayPriest\": {\"name\":\"中级法师\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"redSwordsman\": {\"name\":\"剑王\",\"hp\":100,\"atk\":120,\"def\":0,\"money\":7,\"exp\":0,\"point\":0,\"special\":6,\"n\":8},\n"
            + "\t\"whiteGhost\": {\"name\":\"水银战士\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"poisonZombie\": {\"name\":\"绿兽人\",\"hp\":100,\"atk\":120,\"def\":0,\"money\":13,\"exp\":0,\"point\":0,\"special\":12},\n"
            + "\t\"magicDragon\": {\"name\":\"魔龙\",\"hp\":99999,\"atk\":9999,\"def\":5000,\"money\":0,\"exp\":0,\"special\":0,\"point\":0},\n"
            + "\t\"octopus\": {\"name\":\"血影\",\"hp\":99999,\"atk\":5000,\"def\":4000,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"darkFairy\": {\"name\":\"仙子\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"greenKnight\": {\"name\":\"强盾骑士\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"angel\": {\"name\":\"天使\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"elemental\": {\"name\":\"元素生物\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":0},\n"
            + "\t\"steelGuard\": {\"name\":\"铁守卫\",\"hp\":0,\"atk\":0,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":18,\"value\":20},\n"
            + "\t\"evilBat\": {\"name\":\"邪恶蝙蝠\",\"hp\":1000,\"atk\":1,\"def\":0,\"money\":0,\"exp\":0,\"point\":0,\"special\":[2,3]}\n"
            + "}";
        int beginIndex = json.indexOf("{");
        String mapStr = json.substring(beginIndex);
        log.info("mapStr={}", mapStr);
        Map<String, Object> map = JsonUtils.fromJson(mapStr);
        final String sqlTemplate = "INSERT INTO `eblog`.`ex_mota_game_item`"
            + "  (`floor`, `type`, `name`, `hp`, `attack`, `defend`, `gold`, `exp`) "
            + " VALUES ('1', 'ENEMY', '{{name}}', {{hp}}, {{atk}}, {{def}}, {{money}}, {{exp}}); \n";
        StringBuilder stringBuilder = new StringBuilder(1024);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Map<String, Object> info = (Map<String, Object>)entry.getValue();
            log.info("info={}", info);
            List<String> keys = Arrays.asList("name", "hp", "atk", "def", "money", "exp");
            String sqlToUse = sqlTemplate;
            for (String key : keys) {
                sqlToUse = StringUtils.replace(sqlToUse, "{{"+key+"}}", info.get(key).toString());
            }
            stringBuilder.append(sqlToUse);
        }

        log.info("\nsql: {}", stringBuilder);
    }

}
