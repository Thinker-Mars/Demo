import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {

    /**
     * 建立数据库连接
     * @param database
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection create(Database database) throws ClassNotFoundException, SQLException {

        String driverClassName = getDriveClass(database.getType());
        Class.forName(driverClassName);
        Connection connection = DriverManager.getConnection(database.getUrl(), database.getUsername(), database.getPassword());
        return connection;
    }

    /**
     * 根据数据库类型获取驱动类名
     * @param type
     * @return
     */
    public static String getDriveClass(String type){
        String result = "";

        switch (type) {
            case DT.DictDatabaseType.MYSQL://Mysql
                result = "com.mysql.jdbc.Driver";
                break;

            case DT.DictDatabaseType.ORACLE://Oracle
                result = "oracle.jdbc.OracleDriver";
                break;

            case DT.DictDatabaseType.DB2://db2
                result = "com.ibm.db2.jcc.DB2Driver";
                break;

            case DT.DictDatabaseType.SQL_SERVER://sql Server
                result = "com.microsoft.sqlserver.jdbc.SQLserverDriver";
                break;

            default:
                break;
        }

        return result;
    }


}
