package junit.randoms;

import java.util.List;

/**
 * Created by pppurple on 2016/06/21.
 */
public class Randoms {
    RandomNumberGenerator generator = new RandomNumberGeneratorImpl();

    public <T> T choice(List<T> options) {
        if (options.size() == 0) return null;
        int idx = generator.nextInt() % options.size();
        return options.get(idx);
    }
}
