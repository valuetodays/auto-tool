package cn.valuetodays.autotool.mota.common.item;

import cn.valuetodays.autotool.mota.common.MotaGameItemEnums;
import lombok.Data;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-10
 */
@Data
public class Mota24Item {
    private Long id;
    private MotaGameItemEnums.Type type;
    private String name;
    private Integer hp;
    private Integer attack;
    private Integer defend;
    private Integer gold;
    private Integer exp;
}
