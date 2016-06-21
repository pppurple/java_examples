package junit.spy;

import java.util.logging.Logger;

/**
 * Created by pppurple on 2016/06/21.
 */
public class SpyExample {
    Logger logger = Logger.getLogger(SpyExample.class.getName());

    public void doSomething() {
        logger.info("doSomething");
    }
}
