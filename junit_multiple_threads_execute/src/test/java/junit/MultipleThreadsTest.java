package junit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class MultipleThreadsTest {
    private static final List<String> countries = Arrays.asList(
            "JP", "IS", "IE", "AZ", "AF", "US", "VI", "AS", "AE", "DZ",
            "AR", "AW", "AL", "AM", "AI", "AO", "AG", "AD", "YE", "GB", "IO",
            "VG", "IL", "IT", "IQ", "IR", "IN", "ID", "WF", "UG", "UA", "UZ");

    private static final List<String> languages = Arrays.asList(
            "ar", "an", "hy", "as", "av", "ae", "ay", "az", "ba", "bm",
            "eu", "be", "bn", "bh", "bi", "bs", "br", "bg", "my", "ca", "ch",
            "ce", "zh", "zh-CN", "zh-TW", "cu", "cv", "kw", "co", "cr", "cs",
            "da", "dv", "nl", "dz", "en", "en-US", "en-GB", "en-CA", "en-AU");

    @Test
    public void serialTest() {
        countries.forEach(country -> {
            languages.forEach(lang -> {
                // heavy test
                assertThat(targetTest(country, lang)).isTrue();
            });
        });
    }

    @Test
    public void concurrentTest() throws InterruptedException {
        List<ArgsExpectedPair<Locale, Boolean>> pairs = new ArrayList<>();
        countries.forEach(country -> {
            languages.forEach(lang -> {
                Locale locale = new Locale(country, lang);
                pairs.add(new ArgsExpectedPair<>(locale, true));
            });
        });

        Function<Locale, Boolean> targetTestWrapper = (locale) -> {
            return targetTest(locale.country, locale.getLanguage());
        };

        testConcurrent(targetTestWrapper, pairs, 10, 100);
    }

    // テスト対象メソッド
    // target test method
    private boolean targetTest(String country, String language) {
        try {
            // dummy heavy test
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test: country=" + country + " lang=" + language);

/*        if (country.equals("JP") && language.equals("be")) {
            return false;
        }*/
        return true;
    }

    @Data
    @AllArgsConstructor
    public static class Locale {
        private String country;
        private String language;
    }

    @Data
    @AllArgsConstructor
    public static class ArgsExpectedPair<A, B> {
        private A args;
        private B expected;
    }

    public static void testConcurrent(final Function<Locale, Boolean> targetTest, final List<ArgsExpectedPair<Locale , Boolean>> pairs,
                                      final int threadPoolSize, final int maxTimeoutSeconds) throws InterruptedException {
        final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
        final List<Boolean> testFails = Collections.synchronizedList(new ArrayList<>());
        final ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);
        try {
            final CountDownLatch allExecutorThreadsReady = new CountDownLatch(threadPoolSize);
            final CountDownLatch afterInitBlocker = new CountDownLatch(1);
            final CountDownLatch allDone = new CountDownLatch(pairs.size());
            for (final ArgsExpectedPair<Locale, Boolean> pair : pairs) {
                Locale args = pair.getArgs();
                Boolean expected = pair.getExpected();
                Runnable task = () -> {
                    Boolean result = targetTest.apply(args);
                    if (!expected.equals(result)) {
                        testFails.add(false);
                    }
                };

                threadPool.submit(() -> {
                    allExecutorThreadsReady.countDown();
                    try {
                        afterInitBlocker.await();
                        task.run();
                    } catch (final Throwable e) {
                        exceptions.add(e);
                    } finally {
                        allDone.countDown();
                    }
                });
            }

            // wait until all threads are ready
            assertTrue("Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent",
                    allExecutorThreadsReady.await(pairs.size() * 10, TimeUnit.MILLISECONDS));
            // start all test runners
            afterInitBlocker.countDown();
            assertTrue(" timeout! More than" + maxTimeoutSeconds + "seconds", allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));
        } finally {
            threadPool.shutdownNow();
        }
        assertThat(testFails).as("test failed").isEmpty();
        assertThat(exceptions).as("failed with exception(s)" + exceptions).isEmpty();
    }
}
