package junit.spy;

import java.util.logging.Logger;

/**
 * Created by pppurple on 2016/06/21.
 */
public class SpyLogger extends Logger {
    final Logger base;
    final StringBuilder log = new StringBuilder();

    public SpyLogger(Logger base) {
        super(base.getName(), base.getResourceBundleName());
        this.base = base;
    }

    @Override
    public void info(String msg) {
        log.append(msg);
        base.info(msg);
    }
}
