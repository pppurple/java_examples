package lombok;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UtilityExample {
    int MAGIC_NUMBER = 10;

    public int doubleNum(int num) {
        return num + num;
    }
}
