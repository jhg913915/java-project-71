package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.util.Map;
import java.util.concurrent.Callable;

import static hexlet.code.Differ.parseFile;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    @Parameters(index = "0", description = "path to first file")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format;

    @Override
    public Integer call() throws Exception {
        try {
            System.out.println("First file parsed contents:");
            Map<String, Object> file1Data = parseFile(filepath1);
            System.out.println(file1Data);

            System.out.println("\nSecond file parsed contents:");
            Map<String, Object> file2Data = parseFile(filepath2);
            System.out.println(file2Data);

            return 0;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 1;
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
