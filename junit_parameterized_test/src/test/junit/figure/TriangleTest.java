package junit.figure;

import org.junit.Rule;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by pppurple on 2016/06/12.
 */
@RunWith(Enclosed.class)
public class TriangleTest {
    @RunWith(Theories.class)
    public static class 辺の長さ0なし {
        @DataPoints
        public static TriangleFixture[] TRI = {
                new TriangleFixture(3, 4, 5, 6),
                new TriangleFixture(4, 10, 12, 19),
                new TriangleFixture(3, 3, 3, 4)
        };

        @Theory
        public void area(TriangleFixture f) throws Exception {
            Triangle t = new Triangle(f.a, f.b, f.c);
            assertThat(t.area(), is(f.expected));
        }
    }

    @RunWith(Theories.class)
    public static class 辺の長さ0あり {
        @DataPoints
        public static TriangleFixture[] TRI = {
                new TriangleFixture(0, 4, 5, 6),
                new TriangleFixture(4, 0, 12, 19),
                new TriangleFixture(3, 3, 0, 4)
        };

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Theory
        public void area(TriangleFixture f) throws Exception {
            thrown.expect(ArithmeticException.class);
            Triangle t = new Triangle(f.a, f.b, f.c);
            t.area();
        }
    }

    static class TriangleFixture {
        int a;
        int b;
        int c;
        int expected;

        TriangleFixture(int a, int b, int c, int expected) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.expected = expected;
        }
    }
}