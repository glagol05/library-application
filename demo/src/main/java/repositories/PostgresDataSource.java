package repositories;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

public class PostgresDataSource {

    private static PGSimpleDataSource ds;

    public static DataSource getDataSource() {
        if (ds == null) {
            ds = new PGSimpleDataSource();
            ds.setServerNames(new String[]{"localhost"});
            ds.setPortNumbers(new int[]{5432});
            ds.setDatabaseName("transactiondb");
            ds.setUser("user");
            ds.setPassword("password");
        }
        return ds;
    }
}
