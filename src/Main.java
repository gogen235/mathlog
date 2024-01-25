import parser.CantorNormalForm;
import parser.ExpressionParser;
import parser.StringSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(new File(args[0]))) {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    StandardCharsets.UTF_8
            ))) {
                while (scan.hasNextLine()) {
                    String[] str = scan.nextLine().split("=");
                    CantorNormalForm first = ExpressionParser.parse(new StringSource(str[0])).evaluate();
                    CantorNormalForm second = ExpressionParser.parse(new StringSource(str[1])).evaluate();
                    if (first.equals(second)) {
                        writer.write("Равны");
                    } else {
                        writer.write("Не равны");
                    }
                    writer.newLine();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Output file writer error: " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist: " + e.getMessage());
        }
    }
}
