import org.apache.commons.cli.*;

public class dealArgs {

    public static Database getDatabase(CommandLineParser parser, Options options, String[] args) throws ParseException {

        Database database = new Database();
        CommandLine line = parser.parse(options, args);

        if (line.hasOption("t")) {//数据库类型
            database.setType(line.getOptionValue("t"));
        }
        if (line.hasOption("l")) {//url
            database.setUrl(line.getOptionValue("l"));
        }
        if (line.hasOption("u")) {//用户名
            database.setUsername(line.getOptionValue("u"));
        }
        if (line.hasOption("p")) {//密码
            database.setPassword(line.getOptionValue("p"));
        }
        return database;

    }
}
