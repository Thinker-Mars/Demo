import org.apache.commons.cli.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 入口
 */
public class Entrance {

    public static void main(String[] args) throws ClassNotFoundException, ParseException {
        // create the parser
        CommandLineParser parser = new DefaultParser();
        Options options = DtOptions.generateOp();
        CommandLine line = parser.parse(options, args);
        try {
            if (line.hasOption("ant")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("ant", options);
            } else {
                Database database = dealArgs.getDatabase(parser, options, args);
                Connection conn = CreateConnection.create(database);
                conn.close();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
