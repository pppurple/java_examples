package junit.db;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.util.List;

import static junit.db.ITableMatcher.tableOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by pppurple on 2016/06/25.
 */
@RunWith(Enclosed.class)
public class ShopDaoTest {
    public static class shopに2件のitemがある場合 {
        @ClassRule
        public static H2DatabaseServer server = new H2UtDatabaseServer();
        @Rule
        public DbUnitTester tester = new ShopDaoDbUnitTester("fixtures.xml");

        ShopDao sut;

        @Before
        public void setUp() throws Exception {
            this.sut = new ShopDao();
        }

        @Test
        public void getListで3件取得できる() throws Exception {
            List<String> actual = sut.getList();
            assertThat(actual, is(notNullValue()));
            assertThat(actual.size(), is(3));
            assertThat(actual.get(0), is("apple"));
            assertThat(actual.get(1), is("orange"));
            assertThat(actual.get(2), is("banana"));
        }

        @Test
        public void insertで1件追加できる() throws Exception {
            sut.insert("pine", 330);
            ITable actual = tester.getConnection().createDataSet().getTable("shop");
            InputStream expectedIn = getClass().getResourceAsStream("expected.xml");
            ITable expected = new FlatXmlDataSetBuilder().build(expectedIn).getTable("shop");
            assertThat(actual, is(tableOf(expected)));
        }

    }

    public static class shopに0件のitemがある場合 {
        @ClassRule
        public static H2DatabaseServer server = new H2UtDatabaseServer();
        @Rule
        public DbUnitTester tester = new ShopDaoDbUnitTester("zero_fixtures.xml");

        ShopDao sut;

        @Before
        public void setUp() throws Exception {
            this.sut = new ShopDao();
        }

        @Test
        public void getListで0件取得できる() throws Exception {
            List<String> actual = sut.getList();
            assertThat(actual, is(notNullValue()));
            assertThat(actual.size(), is(0));
        }

        @Test
        public void insertで1件追加できる() throws Exception {
            sut.insert("apple", 100);
            ITable actual = tester.getConnection().createDataSet().getTable("shop");
            InputStream expectedIn = getClass().getResourceAsStream("zero_expected.xml");
            ITable expected = new FlatXmlDataSetBuilder().build(expectedIn).getTable("shop");
            assertThat(actual, is(tableOf(expected)));
        }
    }

    static class H2UtDatabaseServer extends H2DatabaseServer {
        public H2UtDatabaseServer() {
            super("h2", "db", "ut");
        }
    }

    static class ShopDaoDbUnitTester extends DbUnitTester {
        private final String fixture;
        public ShopDaoDbUnitTester(String fixture) {
            //super("org.h2.Driver", "jdbc:h2:tcp://localhost/db;SCHEMA=ut", "sa", "", "ut");
            super("org.h2.Driver", "jdbc:h2:~/db;SCHEMA=ut", "sa", "", "ut");
            this.fixture = fixture;
        }
        @Override
        protected void before() throws Exception {
            executeQuery("DROP TABLE IF EXISTS shop");
            executeQuery("create table shop(id int AUTO_INCREMENT primary key,"
                    + "name varchar(2000),"
                    + "price int)");
        }

        @Override
        protected IDataSet createDataSet() throws Exception {
            return new FlatXmlDataSetBuilder()
                    .build(getClass().getResourceAsStream(fixture));
        }
    }
}
