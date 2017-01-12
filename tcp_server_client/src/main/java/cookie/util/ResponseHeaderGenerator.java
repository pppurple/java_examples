package cookie.util;

import java.io.IOException;
import java.io.OutputStream;

public interface ResponseHeaderGenerator {
    void generate(OutputStream output) throws IOException;
}
