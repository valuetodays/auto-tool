package cn.valuetodays.autotool.mota.common.step;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BranchSteps extends Steps {
    private Map<String, SimpleSteps> branchSteps;

    public BranchSteps() {
        super();
    }

    public static BranchSteps with(Map<String, SimpleSteps> branchSteps) {
        BranchSteps bs = new BranchSteps();
        bs.setBranchSteps(branchSteps);
        return bs;
    }
}
