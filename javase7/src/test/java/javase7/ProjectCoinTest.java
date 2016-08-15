package javase7;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by pppurple on 2016/08/15.
 */
public class ProjectCoinTest {
    @Test
    public void swithで文字列が使用できる() throws Exception {
        String str = "bar";

        switch (str) {
            case "foo":
                str += "1";
                break;
            case "bar":
                str += "2";
                break;
            case "foobar":
                str += "3";
                break;
            default:
                str += "4";
        }
        assertThat(str).isEqualTo("bar2");
    }

    @Test
    public void 二進数リテラルが使用できる() throws Exception {
        int a = 0b0100;

        assertThat(a).isEqualTo(4);
    }

    @Test
    public void 数値リテラルで区切り文字が使用できる() throws Exception {
        int a = 1_000_000;
        int b = 0b0001_1000;

        assertThat(a).isEqualTo(1000000);
        assertThat(b).isEqualTo(24);
    }

    @Test
    public void 例外のマルチキャッチが可能() throws Exception {
        String message = "";
        try {
            Class clazz = Class.forName("Sample");
        } catch (ClassNotFoundException | ClassCastException e) {
            message = e.getMessage();
        }

        assertThat(message).isEqualTo("Sample");
    }

    @Test
    public void ダイヤモンド演算子が使用できる() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        assertThat(list).containsOnly("aaa", "bbb", "ccc");
    }

    @Test
    public void リソースが自動クローズされる() throws Exception {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/javase7/samplefile.txt"))) {
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertThat(sb.toString()).isEqualTo("abcdefg");
    }

    @Test
    public void 安全な可変長引数() throws Exception {
        List<String> list = new ArrayList<>();
        List actual = add(list, "aaa", "bbb", "ccc");

        String[] expected = {"aaa", "bbb", "ccc"};

        assertThat(actual).containsOnly(expected);
    }

    @SafeVarargs
    public static <T> List<T> add(List<T> list, T... t) {
        Arrays.stream(t).forEach(list::add);
        return list;
    }
}
