package junit;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
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
    public void useExecutorTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<Void>> tasks = new ArrayList<>();
        try {
            countries.forEach(country -> {
                languages.forEach(lang -> {
                    Callable<Void> task = () -> {
                        assertThat(targetTest(country, lang)).isTrue();
                        return null;
                    };
                    tasks.add(task);
                });
            });
            executorService.invokeAll(tasks);
        } finally {
            executorService.shutdown();
        }
    }

    @Test
    public void concurrentTest() throws Exception {
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

        testConcurrent(targetTestWrapper, pairs, 10, 10);
    }

    // テスト対象メソッド
    // target test method
    private boolean targetTest(String country, String language) {
        try {
            // dummy heavy task
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test: country=" + country + " lang=" + language);

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

    public static <T, U> void testConcurrent(final Function<T, U> targetTest, final List<ArgsExpectedPair<T, U>> pairs,
                                       final int threadPoolSize, final int maxTimeoutSeconds) throws InterruptedException {
        // failed test cases
        final List<ArgsExpectedPair<T, U>> testFails = Collections.synchronizedList(new ArrayList<>());
        // failed with an exception test cases
        final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());

        final ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolSize);
        try {
            final CountDownLatch allDone = new CountDownLatch(pairs.size());
            for (final ArgsExpectedPair<T, U> pair : pairs) {
                T args = pair.getArgs();
                U expected = pair.getExpected();
                Runnable task = () -> {
                    U result = targetTest.apply(args);
                    if (!expected.equals(result)) {
                        testFails.add(pair);
                    }
                };

                threadPool.submit(() -> {
                    try {
                        task.run();
                    } catch (final Throwable e) {
                        exceptions.add(e);
                    } finally {
                        allDone.countDown();
                    }
                });
            }

            assertThat(allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS))
                    .as(" timeout! More than " + maxTimeoutSeconds + "seconds.").isTrue();
        } finally {
            threadPool.shutdownNow();
        }
        assertThat(testFails).as("test failed.").isEmpty();
        assertThat(exceptions).as("failed with exception(s)." + exceptions).isEmpty();
    }
}
