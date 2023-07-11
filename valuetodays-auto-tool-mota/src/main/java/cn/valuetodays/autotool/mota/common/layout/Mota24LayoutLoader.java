package cn.valuetodays.autotool.mota.common.layout;

import cn.valuetodays.autotool.common.utils.ClassPathResourceUtils;
import cn.valuetodays.autotool.common.utils.YamlUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-10
 */
@Slf4j
public class Mota24LayoutLoader {

    public final Mota24Layout loadLayout() {
        String path = "/mota/mota24/mota24-layout.yaml";
        String yamlString = ClassPathResourceUtils.getFileAsString(path);
        Mota24Layout mota24Layout = YamlUtils.toObject(yamlString, Mota24Layout.class);
        log.info("mota24Layout={}", mota24Layout);
        return mota24Layout;
    }

}
