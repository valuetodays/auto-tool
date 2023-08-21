package cn.valuetodays.autotool.mine;

import cn.valuetodays.autotool.mine.impl.ClassicMine;

import java.io.IOException;

/**
 * .
 *
 * @author lei.liu
 * @since 2023-08-20
 */
public class Main {
    public static void main(String[] args) throws IOException {
        IMine delegate = new ClassicMine();
        AutoClickMine autoClickMine = new AutoClickMine(delegate);
        autoClickMine.doAutoClick();
    }
}
