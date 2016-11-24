package lombok;

import lombok.experimental.Accessors;

public class AccessorsExample {
    @Accessors(chain = true)
    public static class AccessorsChain {
        @Setter
        String bar;

        void printBar() {
            System.out.println("|" + this.bar + "|");
        }
    }

    @Accessors(fluent = true)
    public static class AccessorsFluent {
        @Getter
        @Setter
        private String foo = "abc";
    }

    public static class AccessorsPrefix {
        @Getter
        @Setter
        @Accessors(prefix = "pre")
        String preZoo;
    }

}
