package lombok;

import lombok.experimental.Wither;

@Getter
public class WitherExample {
    @Wither
    private final String name;
    @Wither
    private final int age;

    public WitherExample(String name, int age) {
        if (name == null) throw new NullPointerException();
        this.name = name;
        this.age = age;
    }
}
