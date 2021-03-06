package reflect;

/**
 * Created by pppurple on 2016/07/18.
 */
public class Circle {
    private int radius;

    public Circle() {
        this(10);
    }

    public Circle(int radius) {
        this.radius = radius;
    }

    public int area() {
        return area(radius);
    }

    public int area(int r) {
        return (int) (r * r * Math.PI);
    }

    public int publicMethod() {
        return 1;
    }

    protected int protectedMethod() {
        return 2;
    }

    int defaultMethod() {
        return 3;
    }

    private int privateMethod() {
        return 4;
    }

    public static int staticMethod() {
        return 11;
    }

    @Override
    public String toString() {
        return "radius : " + radius;
    }
}
