package javase8;

import org.junit.Test;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class DateAndTimeApiTest {

    @Test
    public void localDataTime() {
        // 現在時刻
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        // 現在時刻(タイムゾーン指定)
        LocalDateTime nowTokyo = LocalDateTime.now(ZoneId.of("Asia/Tokyo"));
        System.out.println(nowTokyo);

        LocalDateTime nowNewYork = LocalDateTime.now(ZoneId.of("America/New_York"));
        System.out.println(nowNewYork);

        // 現在時刻(デフォルトタイムゾーンのシステムクロック)
        LocalDateTime nowSystem = LocalDateTime.now(Clock.systemDefaultZone());
        System.out.println(nowSystem);

        // 現在時刻(UTCタイムゾーンのシステムクロック)
        LocalDateTime nowUtc = LocalDateTime.now(Clock.systemUTC());
        System.out.println(nowUtc);
    }

    @Test
    public void of() {
        // 年月日 時分秒
        LocalDateTime dateTime = LocalDateTime.of(2016, 9, 25, 12, 55, 10);
        assertThat(dateTime.toString()).isEqualTo("2016-09-25T12:55:10");

        // 年月日 時分秒 (列挙型Month)
        LocalDateTime dateTime2 = LocalDateTime.of(2016, Month.SEPTEMBER, 25, 12, 55, 10);
        assertThat(dateTime2.toString()).isEqualTo("2016-09-25T12:55:10");

        // 年月日 時分
        LocalDateTime dateTime3 = LocalDateTime.of(2016, 9, 25, 12, 55);
        assertThat(dateTime3.toString()).isEqualTo("2016-09-25T12:55");

        // 年月日 時分 (列挙型Month)
        LocalDateTime dateTime4 = LocalDateTime.of(2016, Month.SEPTEMBER, 25, 12, 55);
        assertThat(dateTime4.toString()).isEqualTo("2016-09-25T12:55");

        // 年月日 時分秒ナノ秒
        LocalDateTime dateTime5 = LocalDateTime.of(2016, 9, 25, 12, 55, 10, 123_456_789);
        assertThat(dateTime5.toString()).isEqualTo("2016-09-25T12:55:10.123456789");

        // 年月日 時分秒ナノ秒 (列挙型Month)
        LocalDateTime dateTime6 = LocalDateTime.of(2016, Month.SEPTEMBER, 25, 12, 55, 10, 123_456_789);
        assertThat(dateTime6.toString()).isEqualTo("2016-09-25T12:55:10.123456789");

        // LocalDate + LocalTime
        LocalDate date = LocalDate.of(2016, 9, 25);
        LocalTime time = LocalTime.of(12, 55, 10);
        LocalDateTime dateTime7 = LocalDateTime.of(date, time);
        assertThat(dateTime7.toString()).isEqualTo("2016-09-25T12:55:10");
    }

    @Test
    public void from() {
        // LocalDateTimeから取得
        LocalDateTime from = LocalDateTime.of(2016, 9, 25, 12, 55, 10);
        LocalDateTime to = LocalDateTime.from(from);
        assertThat(to.toString()).isEqualTo("2016-09-25T12:55:10");

        // LocalDateから取得
        LocalDate fromDate = LocalDate.of(2016, 9, 25);
        LocalDate toDate = LocalDate.from(fromDate);
        assertThat(toDate.toString()).isEqualTo("2016-09-25");

        // LocalTimeから取得
        LocalTime fromTime = LocalTime.of(12, 55, 10);
        LocalTime toTime = LocalTime.from(fromTime);
        assertThat(toTime.toString()).isEqualTo("12:55:10");
    }

    @Test
    public void parse() {
        // DateTimeFormatter.ISO_LOCAL_DATE_TIME
        LocalDateTime dateTime = LocalDateTime.parse("2016-09-25T12:55:10");
        System.out.println(dateTime);

        // DateTimeFormatter.ISO_OFFSET_DATE_TIME
        LocalDateTime basicIso = LocalDateTime.parse("2016-09-25T12:55:10+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        System.out.println(basicIso);
    }

    @Test
    public void getXxx() {
        LocalDateTime dateTime = LocalDateTime.of(2016, 9, 25, 12, 55, 10, 123_456_789);

        // getYear
        int year = dateTime.getYear();
        assertThat(year).isEqualTo(2016);

        // getDayOfYear
        int dayOfYear = dateTime.getDayOfYear();
        assertThat(dayOfYear).isEqualTo(269);

        // getMonth
        Month month = dateTime.getMonth();
        assertThat(month).isEqualTo(Month.SEPTEMBER);

        // getMonthValue
        int monthValue = dateTime.getMonthValue();
        assertThat(monthValue).isEqualTo(9);

        // getDayOfMonth
        int dayOfMonth = dateTime.getDayOfMonth();
        assertThat(dayOfMonth).isEqualTo(25);

        // getDayOfWeek
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        assertThat(dayOfWeek).isEqualTo(DayOfWeek.SUNDAY);

        // getHour
        int hour = dateTime.getHour();
        assertThat(hour).isEqualTo(12);

        // getMinute
        int minute = dateTime.getMinute();
        assertThat(minute).isEqualTo(55);

        // getSecond
        int second = dateTime.getSecond();
        assertThat(second).isEqualTo(10);

        // getNano
        int nano = dateTime.getNano();
        assertThat(nano).isEqualTo(123_456_789);
    }

    @Test
    public void get() {
        LocalDateTime dateTime = LocalDateTime.of(2016, 9, 25, 20, 55, 10, 123_456_789);

        // 紀元
        int era = dateTime.get(ChronoField.ERA);
        assertThat(era).isEqualTo(1);

        // 年
        int year = dateTime.get(ChronoField.YEAR);
        assertThat(year).isEqualTo(2016);

        // 年の日
        int dayOfYear = dateTime.get(ChronoField.DAY_OF_YEAR);
        assertThat(dayOfYear).isEqualTo(269);

        // 月
        int month = dateTime.get(ChronoField.MONTH_OF_YEAR);
        assertThat(month).isEqualTo(9);

        // 日
        int dayOfMonth = dateTime.get(ChronoField.DAY_OF_MONTH);
        assertThat(dayOfMonth).isEqualTo(25);

        // 曜日
        int dayOfWeek = dateTime.get(ChronoField.DAY_OF_WEEK);
        assertThat(dayOfWeek).isEqualTo(7);

        // 時
        int hourOfDay = dateTime.get(ChronoField.HOUR_OF_DAY);
        assertThat(hourOfDay).isEqualTo(20);

        // 12時間表示の時間
        int hourOfAMPM = dateTime.get(ChronoField.HOUR_OF_AMPM);
        assertThat(hourOfAMPM).isEqualTo(8);

        // AMPM
        int ampmOfDay = dateTime.get(ChronoField.AMPM_OF_DAY);
        assertThat(ampmOfDay).isEqualTo(1);

        // 分
        int minuteOfHour = dateTime.get(ChronoField.MINUTE_OF_HOUR);
        assertThat(minuteOfHour).isEqualTo(55);

        // 一日のうちの分
        int minuteOfDay = dateTime.get(ChronoField.MINUTE_OF_DAY);
        assertThat(minuteOfDay).isEqualTo(1255);

        // 秒
        int secondOfMinute = dateTime.get(ChronoField.SECOND_OF_MINUTE);
        assertThat(secondOfMinute).isEqualTo(10);

        // ミリ秒
        int milliOfSecond = dateTime.get(ChronoField.MILLI_OF_SECOND);
        assertThat(milliOfSecond).isEqualTo(123);

        // マイクロ秒
        int microOfSecond = dateTime.get(ChronoField.MICRO_OF_SECOND);
        assertThat(microOfSecond).isEqualTo(123_456);

        // ナノ秒
        long nanoOfSecond = dateTime.getLong(ChronoField.NANO_OF_SECOND);
        assertThat(nanoOfSecond).isEqualTo(123_456_789);
    }

    @Test
    public void withXxx() {
        LocalDateTime dateTime = LocalDateTime.of(2016, 9, 25, 20, 55, 10, 123_456_789);

        // withYear
        LocalDateTime dateTimeWithYear = dateTime.withYear(2015);
        assertThat(dateTimeWithYear.getYear()).isEqualTo(2015);

        // withMonth
        LocalDateTime dateTimeWithMonth = dateTime.withMonth(3);
        assertThat(dateTimeWithMonth.getMonth()).isEqualTo(Month.MARCH);
        assertThat(dateTimeWithMonth.getMonthValue()).isEqualTo(3);

        // withDayOfMonth
        LocalDateTime dateTimeWithDayOfMonth = dateTime.withDayOfMonth(22);
        assertThat(dateTimeWithDayOfMonth.getDayOfMonth()).isEqualTo(22);

        // withDayOfYear
        LocalDateTime dateTimeWithDayOfYear = dateTime.withDayOfYear(222);
        assertThat(dateTimeWithDayOfYear.getDayOfYear()).isEqualTo(222);

        // withHour
        LocalDateTime dateTimeWithHour = dateTime.withHour(23);
        assertThat(dateTimeWithHour.getHour()).isEqualTo(23);

        // withMinute
        LocalDateTime dateTimeWithMinute = dateTime.withMinute(44);
        assertThat(dateTimeWithMinute.getMinute()).isEqualTo(44);

        // withSeconde
        LocalDateTime dateTimeWithSecond = dateTime.withSecond(55);
        assertThat(dateTimeWithSecond.getSecond()).isEqualTo(55);

        // withNano
        LocalDateTime dateTimeWithNano = dateTime.withNano(987_654_321);
        assertThat(dateTimeWithNano.getNano()).isEqualTo(987_654_321);
    }

    @Test
    public void with() {
        LocalDateTime dateTime = LocalDateTime.of(2016, 9, 25, 20, 55, 10, 123_456_789);

        // 紀元
        LocalDateTime era = dateTime.with(ChronoField.ERA, 1);
        assertThat(era.get(ChronoField.ERA)).isEqualTo(1);

        // 年
        LocalDateTime year = dateTime.with(ChronoField.YEAR, 2015);
        assertThat(year.getYear()).isEqualTo(2015);

        // 年の日
        LocalDateTime dayOfYear = dateTime.with(ChronoField.DAY_OF_YEAR, 250);
        assertThat(dayOfYear.getDayOfYear()).isEqualTo(250);

        // 月
        LocalDateTime monthOfYear = dateTime.with(ChronoField.MONTH_OF_YEAR, 8);
        assertThat(monthOfYear.getMonth()).isEqualTo(Month.AUGUST);

        // 日
        LocalDateTime dayOfMonth = dateTime.with(ChronoField.DAY_OF_MONTH, 22);
        assertThat(dayOfMonth.getDayOfMonth()).isEqualTo(22);

        // 曜日
        LocalDateTime dayOfWeek = dateTime.with(ChronoField.DAY_OF_WEEK, 6);
        assertThat(dayOfWeek.getDayOfWeek()).isEqualTo(DayOfWeek.SATURDAY);

        // 時
        LocalDateTime hourOfDay = dateTime.with(ChronoField.HOUR_OF_DAY, 23);
        assertThat(hourOfDay.getHour()).isEqualTo(23);

        // 12時間表示の時間
        LocalDateTime hourOfAMPM = dateTime.with(ChronoField.HOUR_OF_AMPM, 8);
        assertThat(hourOfAMPM.get(ChronoField.HOUR_OF_AMPM)).isEqualTo(8);

        // AMPM
        LocalDateTime ampmOfDay = dateTime.with(ChronoField.AMPM_OF_DAY, 0);
        assertThat(ampmOfDay.get(ChronoField.AMPM_OF_DAY)).isEqualTo(0);

        // 分
        LocalDateTime minuteOfHour = dateTime.with(ChronoField.MINUTE_OF_HOUR, 44);
        assertThat(minuteOfHour.getMinute()).isEqualTo(44);

        // 一日のうちの分
        LocalDateTime minuteOfDay = dateTime.with(ChronoField.MINUTE_OF_DAY, 1200);
        assertThat(minuteOfDay.get(ChronoField.MINUTE_OF_DAY)).isEqualTo(1200);

        // 秒
        LocalDateTime secondOfMinute = dateTime.with(ChronoField.SECOND_OF_MINUTE, 33);
        assertThat(secondOfMinute.getSecond()).isEqualTo(33);

        // ミリ秒
        LocalDateTime milliOfSecond = dateTime.with(ChronoField.MILLI_OF_SECOND, 234);
        assertThat(milliOfSecond.get(ChronoField.MILLI_OF_SECOND)).isEqualTo(234);

        // マイクロ秒
        LocalDateTime microOfSecond = dateTime.with(ChronoField.MICRO_OF_SECOND, 234_567);
        assertThat(microOfSecond.get(ChronoField.MICRO_OF_SECOND)).isEqualTo(234_567);

        // ナノ秒
        LocalDateTime nanoOfSecond = dateTime.with(ChronoField.NANO_OF_SECOND, 987_654_321);
        assertThat(nanoOfSecond.getNano()).isEqualTo(987_654_321);

        // LocalDate
        LocalDate date = LocalDate.of(2015, 12, 23);
        LocalDateTime dateTimeWithLocalDate = dateTime.with(date);
        assertThat(dateTimeWithLocalDate.toString()).isEqualTo("2015-12-23T20:55:10.123456789");

        // LocalTime
        LocalTime time = LocalTime.of(21, 43, 25);
        LocalDateTime dateTimeWithLocalTime = dateTime.with(time);
        assertThat(dateTimeWithLocalTime.toString()).isEqualTo("2016-09-25T21:43:25");

        // 月の第N曜日
        // 例）9月の第3金曜日
        LocalDateTime dayOfWeekInMonth = dateTime.with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.FRIDAY));
        assertThat(dayOfWeekInMonth.getDayOfMonth()).isEqualTo(16);

        // 月初日
        // 例）2016年9月の最初の日
        LocalDateTime firstDayOfMonth = dateTime.with(TemporalAdjusters.firstDayOfMonth());
        assertThat(firstDayOfMonth.getDayOfMonth()).isEqualTo(1);

        // 翌月の最初の日
        // 例）2016年10月の最初の日
        LocalDateTime firstDayOfNextMonth = dateTime.with(TemporalAdjusters.firstDayOfNextMonth());
        assertThat(firstDayOfNextMonth.getDayOfMonth()).isEqualTo(1);

        // 翌年の最初の日
        // 例）2017年の最初の日
        LocalDateTime firstDayOfYear = dateTime.with(TemporalAdjusters.firstDayOfNextYear());
        assertThat(firstDayOfYear.getDayOfMonth()).isEqualTo(1);

        // 月の中で指定した曜日の一番早い日
        // 例）2016年9月で最初の金曜日
        LocalDateTime firstInMonth = dateTime.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY));
        assertThat(firstInMonth.getDayOfMonth()).isEqualTo(2);

        // 月末日
        // 例）2016年9月の最後の日
        LocalDateTime lastDayOfMonth = dateTime.with(TemporalAdjusters.lastDayOfMonth());
        assertThat(lastDayOfMonth.getDayOfMonth()).isEqualTo(30);

        // 年末日
        // 例）2016年の最後の日
        LocalDateTime lastDayOfYear = dateTime.with(TemporalAdjusters.lastDayOfYear());
        assertThat(lastDayOfYear.getDayOfMonth()).isEqualTo(31);

        // 月の中で指定した曜日の一番遅い日
        // 例）2016年9月で最後の金曜日
        LocalDateTime lastInMonth = dateTime.with(TemporalAdjusters.lastInMonth(DayOfWeek.FRIDAY));
        assertThat(lastInMonth.getDayOfMonth()).isEqualTo(30);

        // 指定した曜日が次に出てくる最初の日
        // 例）2016年9月25日以降で一番最初の土曜日
        LocalDateTime next = dateTime.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        assertThat(next.getDayOfMonth()).isEqualTo(2);

        // 指定した曜日が次に出てくる最初の日(ただし同じ曜日の場合、その日を返す)
        // 例）2016年9月25日以降で一番最初の土曜日
        LocalDateTime nextOrSame = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        assertThat(nextOrSame.getDayOfMonth()).isEqualTo(25);

        // 指定した曜日が前に出てくる最初の日
        // 例）2016年9月25日以前で一番最初の金曜日
        LocalDateTime previous = dateTime.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        assertThat(previous.getDayOfMonth()).isEqualTo(18);

        // 指定した曜日が前に出てくる最初の日(ただし同じ曜日の場合、その日を返す)
        // 例）2016年9月25日以前で一番最初の金曜日
        LocalDateTime previousOrSame = dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        assertThat(previousOrSame.getDayOfMonth()).isEqualTo(25);
    }

    @Test
    public void calc() {
        LocalDateTime dateTime = LocalDateTime.of(2016, 9, 25, 20, 55, 10, 123_456_789);

        // 年の加算
        LocalDateTime plusYears = dateTime.plusYears(1L);
        assertThat(plusYears.getYear()).isEqualTo(2017);

        LocalDateTime plusYears2 = dateTime.plus(1L, ChronoUnit.YEARS);
        assertThat(plusYears2.getYear()).isEqualTo(2017);

        // 月の加算
        LocalDateTime plusMonths = dateTime.plusMonths(1L);
        assertThat(plusMonths.getMonth()).isEqualTo(Month.OCTOBER);

        LocalDateTime plusMonths2 = dateTime.plus(1L, ChronoUnit.MONTHS);
        assertThat(plusMonths2.getMonth()).isEqualTo(Month.OCTOBER);

        // 週の加算
        LocalDateTime plusWeeks = dateTime.plusWeeks(1L);
        assertThat(plusWeeks.getDayOfMonth()).isEqualTo(2);

        LocalDateTime plusWeeks2 = dateTime.plus(1L, ChronoUnit.WEEKS);
        assertThat(plusWeeks2.getDayOfMonth()).isEqualTo(2);

        // 日の加算
        LocalDateTime plusDays = dateTime.plusDays(1L);
        assertThat(plusDays.getDayOfMonth()).isEqualTo(26);

        LocalDateTime plusDays2 = dateTime.plus(1L, ChronoUnit.DAYS);
        assertThat(plusDays2.getDayOfMonth()).isEqualTo(26);

        // 時の加算
        LocalDateTime plusHours = dateTime.plusHours(1L);
        assertThat(plusHours.getHour()).isEqualTo(21);

        LocalDateTime plusHours2 = dateTime.plus(1L, ChronoUnit.HOURS);
        assertThat(plusHours2.getHour()).isEqualTo(21);

        // 分の加算
        LocalDateTime plusMinutes = dateTime.plusMinutes(1L);
        assertThat(plusMinutes.getMinute()).isEqualTo(56);

        LocalDateTime plusMinutes2 = dateTime.plus(1L, ChronoUnit.MINUTES);
        assertThat(plusMinutes2.getMinute()).isEqualTo(56);

        // 秒の加算
        LocalDateTime plusSeconds = dateTime.plusSeconds(1L);
        assertThat(plusSeconds.getSecond()).isEqualTo(11);

        LocalDateTime plusSeconds2 = dateTime.plus(1L, ChronoUnit.SECONDS);
        assertThat(plusSeconds2.getSecond()).isEqualTo(11);

        // ナノ秒の加算
        LocalDateTime plusNanos = dateTime.plusNanos(1L);
        assertThat(plusNanos.getNano()).isEqualTo(123456790);

        LocalDateTime plusNanos2 = dateTime.plus(1L, ChronoUnit.NANOS);
        assertThat(plusNanos2.getNano()).isEqualTo(123456790);

        // 年の減算
        LocalDateTime minusYears = dateTime.minusYears(1L);
        assertThat(minusYears.getYear()).isEqualTo(2015);

        LocalDateTime minusYears2 = dateTime.minus(1L, ChronoUnit.YEARS);
        assertThat(minusYears2.getYear()).isEqualTo(2015);

        // 月の減算
        LocalDateTime minusMonths = dateTime.minusMonths(1L);
        assertThat(minusMonths.getMonth()).isEqualTo(Month.AUGUST);

        LocalDateTime minusMonths2 = dateTime.minus(1L, ChronoUnit.MONTHS);
        assertThat(minusMonths2.getMonth()).isEqualTo(Month.AUGUST);

        // 週の減算
        LocalDateTime minusWeeks = dateTime.minusWeeks(1L);
        assertThat(minusWeeks.getDayOfMonth()).isEqualTo(18);

        LocalDateTime minusWeeks2 = dateTime.minus(1L, ChronoUnit.WEEKS);
        assertThat(minusWeeks2.getDayOfMonth()).isEqualTo(18);

        // 日の減算
        LocalDateTime minusDays = dateTime.minusDays(1L);
        assertThat(minusDays.getDayOfMonth()).isEqualTo(24);

        LocalDateTime minusDays2 = dateTime.minus(1L, ChronoUnit.DAYS);
        assertThat(minusDays2.getDayOfMonth()).isEqualTo(24);

        // 時の減算
        LocalDateTime minusHours = dateTime.minusHours(1L);
        assertThat(minusHours.getHour()).isEqualTo(19);

        LocalDateTime minusHours2 = dateTime.minus(1L, ChronoUnit.HOURS);
        assertThat(minusHours2.getHour()).isEqualTo(19);

        // 分の減算
        LocalDateTime minusMinutes = dateTime.minusMinutes(1L);
        assertThat(minusMinutes.getMinute()).isEqualTo(54);

        LocalDateTime minusMinutes2 = dateTime.minus(1L, ChronoUnit.MINUTES);
        assertThat(minusMinutes2.getMinute()).isEqualTo(54);

        // 秒の減算
        LocalDateTime minusSeconds = dateTime.minusSeconds(1L);
        assertThat(minusSeconds.getSecond()).isEqualTo(9);

        LocalDateTime minusSeconds2 = dateTime.minus(1L, ChronoUnit.SECONDS);
        assertThat(minusSeconds2.getSecond()).isEqualTo(9);

        // ナノ秒の減算
        LocalDateTime minusNanos = dateTime.minusNanos(1L);
        assertThat(minusNanos.getNano()).isEqualTo(123456788);

        LocalDateTime minusNanos2 = dateTime.minus(1L, ChronoUnit.NANOS);
        assertThat(minusNanos2.getNano()).isEqualTo(123456788);
    }

    @Test
    public void compare() {
        // isAfter
        LocalDateTime dateTime1 = LocalDateTime.of(2016, 9, 25, 20, 55, 10);
        LocalDateTime dateTime2 = LocalDateTime.of(2016, 9, 25, 20, 55, 11);
        assertThat(dateTime2.isAfter(dateTime1)).isEqualTo(true);

        // isBefore
        assertThat(dateTime1.isBefore(dateTime2)).isEqualTo(true);

        // isEqual
        LocalDateTime dateTime3 = dateTime1.plusSeconds(1L);
        assertThat(dateTime2.isEqual(dateTime3)).isEqualTo(true);
    }

    @Test
    public void DateTimeToDate() {
        // Datetime → Date
//        OffsetDateTime dateTime1 = OffsetDateTime.of(2016, 9, 25, 20, 55, 10, );

    }

    @Test
    public void temmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        String date = now.format(formatter) + " GMT";
        System.out.println(date);
    }
}
