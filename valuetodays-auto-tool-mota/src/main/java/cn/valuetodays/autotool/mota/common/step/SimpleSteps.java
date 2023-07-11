package cn.valuetodays.autotool.mota.common.step;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SimpleSteps extends Steps {
    private String floor;
    private List<Step> steps;


    public static SimpleSteps with(Step ...steps) {
        return with(Arrays.asList(steps));
    }
    public static SimpleSteps with(List<Step> steps) {
        SimpleSteps steps1 = new SimpleSteps();
        steps1.setSteps(steps);
        return steps1;
    }
}
