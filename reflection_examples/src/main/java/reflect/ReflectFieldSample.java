package reflect;

/**
 * Created by pppurple on 2016/07/23.
 */
public class ReflectFieldSample {
    public String pub = "public";
    protected String pro = "protected";
    String def = "default";
    private String pri = "private";

    String name;

    public ReflectFieldSample() {
        this("no args");
    }

    public ReflectFieldSample(String name) {
        this.name = name;
    }
}
