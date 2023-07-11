package cn.valuetodays.autotool.mota.common.step;

import lombok.Data;

import java.util.List;
import java.util.Map;

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
    private Map<String, SimpleSteps> branchSteps;

    public FullSteps() {
    }
}
