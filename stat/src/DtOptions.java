import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class DtOptions {

    public static Options generateOp() {
        final Options options = new Options();
        options.addOption(new Option("ant", "ant", false, "command help"));
        options.addOption(new Option("t", "type", true, "database type"));
        options.addOption(new Option("l", "url", true, "database url"));
        options.addOption(new Option("u", "username", true, "database username"));
        options.addOption(new Option("p", "password", true, "database password"));
        return options;
    }
}
