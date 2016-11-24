package lombok;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.experimental.Wither;

class FieldDefaultsExample {

    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class FieldLevelPrivate {
        String text = "ABC";
    }

    @FieldDefaults(level = AccessLevel.PUBLIC)
    public static class FieldLevelPublic {
        int num = 100;
    }

    @FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
    public static class FieldFinal {
        int count = 200;

        @NonFinal
        String memo = "memorandum";
    }
}
