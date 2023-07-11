package cn.valuetodays.autotool.mota.mota24;

import cn.valuetodays.autotool.mota.common.Hero;
import cn.valuetodays.autotool.mota.common.Mota24GamePlayStepSimulator;
import cn.valuetodays.autotool.mota.common.item.Mota24Item;
import cn.valuetodays.autotool.mota.common.item.Mota24ItemsLoader;
import cn.valuetodays.autotool.mota.common.step.BranchSteps;
import cn.valuetodays.autotool.mota.common.step.FullSteps;
import cn.valuetodays.autotool.mota.common.step.SimpleSteps;
import cn.valuetodays.autotool.mota.common.step.StepType;
import cn.valuetodays.autotool.mota.common.step.Steps;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-10
 */
@Slf4j
public class Mota24GamePlayStepSimulatorTest {
    private static final Mota24ItemsLoader MOTA_24_ITEMS_LOADER = new Mota24ItemsLoader();

    @Test
    public void test() {
        List<FullSteps> fullStepList = new Mota24StepLoader().loadSteps();
        List<Steps> stepsList = fullStepList.stream()
            .map(e -> {
                StepType type = e.getType();
                if (StepType.SIMPLE == type) {
                    return SimpleSteps.with(e.getSteps());
                } else if (StepType.BRANCH == type) {
                    return BranchSteps.with(e.getBranchSteps());
                } else {
                    log.error("unknown type=" + type);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        List<Mota24Item> mota24Items = MOTA_24_ITEMS_LOADER.loadItems();
        Map<String, Mota24Item> nameAndItemMap = mota24Items.stream().collect(Collectors.toMap(Mota24Item::getName, e -> e));
        Hero hero = new Hero();
        Mota24GamePlayStepSimulator mota24GamePlayStepSimulator = new Mota24GamePlayStepSimulator();
        mota24GamePlayStepSimulator.simulate(hero, stepsList, nameAndItemMap);
    }

}
