package junit.randoms;

import java.util.Random;

/**
 * Created by pppurple on 2016/06/21.
 */
public class RandomNumberGeneratorImpl implements RandomNumberGenerator {
    private final Random rand = new Random();

    @Override
    public int nextInt(){
        return rand.nextInt();
    }
}
