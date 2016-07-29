package reflect;

/**
 * Created by pppurple on 2016/07/26.
 */
public class ReflectConstructorExample {
    private String name;
    private int age;
    private int num;

    public ReflectConstructorExample() {

    }

    protected ReflectConstructorExample(String name) {
        this.name = name;
    }

    ReflectConstructorExample(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private ReflectConstructorExample(int num) {
        this.num = num;
    }
}
