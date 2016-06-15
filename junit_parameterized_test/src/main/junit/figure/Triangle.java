package junit.figure;

/**
 * Created by pppurple on 2016/06/12.
 */
public class Triangle {
    private final int a;
    private final int b;
    private final int c;

    public Triangle(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    // ヘロンの公式で面積を求める。
    // 簡単のため、面積は四捨五入してintで返す。
    public int area() {
        if (a == 0 || b == 0 || c == 0) throw new ArithmeticException("zero side");
        double s = (a + b + c) / 2.0;
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
        return (int)Math.round(area);
    }
}
