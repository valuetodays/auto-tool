package cn.valuetodays.autotool.mota.mota24;

import cn.valuetodays.autotool.mota.common.step.FullSteps;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-11
 */
@Slf4j
public class Mota24StepLoaderTest {

    @Test
    public void test() {
        List<FullSteps> fullSteps = new Mota24StepLoader().loadSteps();
        for (FullSteps fullStep : fullSteps) {
            log.info("fullStep={}", fullStep);
        }
    }

}
