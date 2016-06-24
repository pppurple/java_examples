package junit.db;

import org.dbunit.DatabaseUnitException;
import org.dbunit.DatabaseUnitRuntimeException;
import org.dbunit.dataset.ITable;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by pppurple on 2016/06/25.
 */
public class ITableMatcher extends TypeSafeMatcher<ITable> {
    public static Matcher<ITable> tableOf(ITable expected) {
        return new ITableMatcher(expected);
    }

    private final ITable expected;
    String assertionMsg = null;

    ITableMatcher(ITable expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(ITable actual) {
        try {
            org.dbunit.Assertion.assertEquals(expected, actual);
        } catch (DatabaseUnitException e) {
            throw new DatabaseUnitRuntimeException(e);
        } catch (AssertionError e) {
            assertionMsg = e.getMessage();
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendValue(expected);
        if(assertionMsg == null) return;
        desc.appendText("\n >>> ").appendText(assertionMsg);
    }
}
