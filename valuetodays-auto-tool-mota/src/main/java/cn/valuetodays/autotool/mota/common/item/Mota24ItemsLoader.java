package cn.valuetodays.autotool.mota.common.item;

import cn.valuetodays.autotool.common.utils.ClassPathResourceUtils;
import cn.valuetodays.autotool.common.utils.YamlUtils;

import java.util.List;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-07-10
 */
public class Mota24ItemsLoader {
    public final List<Mota24Item> loadItems() {
        String yamlString = ClassPathResourceUtils.getFileAsString("/mota/mota24/mota24-items.yaml");
        Mota24Items mota24Items = YamlUtils.toObject(yamlString, Mota24Items.class);
        return mota24Items.getItems();
    }
}
