package cn.valuetodays.autotool.mota.mota24;

import cn.valuetodays.autotool.common.utils.ClassPathResourceUtils;
import cn.valuetodays.autotool.common.utils.YamlUtils;
import cn.valuetodays.autotool.mota.common.step.FullSteps;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-10
 */
public class Mota24StepLoader {
    public final List<FullSteps> loadSteps() {
        String path = "/mota/mota24/mota24-step.yaml";
        String yamlString = ClassPathResourceUtils.getFileAsString(path);
        Mota24StepList mota24StepList = YamlUtils.toObject(yamlString, Mota24StepList.class);
        return mota24StepList.getStepList();
    }
}
