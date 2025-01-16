package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;


@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Runnable {
    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    private int exitCode = 0;

    @Override
    public void run() {
        try {
            String resultData = Differ.generate(filepath1, filepath2, format);
            System.out.println(resultData);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            exitCode = 1;
        }
    }

    public int getExitCode() {
        return exitCode;
    }

    public static void main(String[] args) {
        App app = new App();
        new CommandLine(app).execute(args);
        System.exit(app.getExitCode());
    }
}
