package javase8;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pppurple on 2016/08/08.
 */
public class DefaultMethodTest {

    interface PrintWord {
        default String print(String word) {
            return "[" + word + "]";
        }
    }

    @Test
    public void デフォルトメソッド使用() throws Exception {
        PrintWord p = new PrintWord() {
        };
        String actual = p.print("abc");
        String expected = "[abc]";
        assertThat(actual, is(expected));
    }

    interface AbstractPrintWord extends PrintWord {
        @Override
        String print(String word);
    }

    @Test
    public void 再抽象化のメソッド実装() throws Exception {
        PrintWord p = new AbstractPrintWord() {
            @Override
            public String print(String word) {
                return "[[[" + word + "]]]";
            }
        };

        String actual = p.print("abc");
        String expected = "[[[abc]]]";
        assertThat(actual, is(expected));
    }

    interface MorePrintWord extends PrintWord {
        @Override
        default String print(String word) {
            return "[[[[[" + word + "]]]]]";
        }
    }

    @Test
    public void デフォルトメソッドのオーバーライドの確認() throws Exception {
        PrintWord p = new MorePrintWord() {};
        String actual = p.print("abc");
        String expected = "[[[[[abc]]]]]";
        assertThat(actual, is(expected));
    }

    interface Root {
        String method(String str);
    }

    interface implA extends Root {
        @Override
        default String method(String str) {
            return "A " + str;
        }
    }

    interface implB extends Root {
        @Override
        default String method(String str) {
            return "B " + str;
        }
    }

    class ImplAandB implements implA, implB {
        @Override
        public String method(String str) {
            return "A and B " + str;
        }
    }

    @Test
    public void インターフェースの多重継承() throws Exception {
        ImplAandB ab = new ImplAandB();
        String actual = ab.method("ab");
        String expected = "A and B ab";

        assertThat(actual, is(expected));
    }


}
