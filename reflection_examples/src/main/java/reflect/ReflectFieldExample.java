package reflect;

/**
 * Created by pppurple on 2016/07/23.
 */
public class ReflectFieldExample {
    public String pub = "public";
    protected String pro = "protected";
    String def = "default";
    private String pri = "private";

    String name;

    public ReflectFieldExample() {
        this("no args");
    }

    public ReflectFieldExample(String name) {
        this.name = name;
    }
}
