package cn.valuetodays.autotool.mota.common.layout;

import lombok.Data;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-10
 */
@Data
public class Mota24Layout {
    private List<FloorInfo> layout;

    @Data
    public static class FloorLayout {
        private String item;
        private List<FloorLayout> children;
    }

    @Data
    public static class FloorInfo {
        private String foor;
        private List<FloorLayout> down;
    }
}
