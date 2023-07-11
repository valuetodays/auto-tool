package cn.valuetodays.autotool.mota.common.step;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-11
 */
@Data
public class FullSteps {
    private String title;
    private StepType type;
    private String floor;
    private List<Step> steps;
    private LinkedHashMap<String, SimpleSteps> branchSteps;

    public FullSteps() {
    }
}
