import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.*;
import java.net.*;

public class grep_like {
    /**
     * Auxiliary function for recognizing whether argument is URL or path.
     * @return Scanner which reads object behind given path.
     */
    private static Scanner search_from(String file_path) throws IOException {
        Scanner sc;
        if (file_path.startsWith("http://") || file_path.startsWith("https://")){
            URL file = new URL(file_path);
            sc = new Scanner(file.openStream());
        }
        else {
            File file = new File(file_path);
            sc = new Scanner(file);
        }
        return sc;
    }

    /**
     * Function that uses console input for analysing file/web page content
     * Prints all the lines where given expressions where found
     */
    public static void search_from_words(String file_path) throws IOException {
        Scanner cin = new Scanner(System.in);
        System.out.println("Word or regex?");
        String objCase = cin.nextLine();
        System.out.print("Allow case insensitivity? Yes/No\n" );
        String sensitivity = cin.nextLine();
        if (sensitivity.equals("yes")||sensitivity.equals("Yes")){
            sensitivity = "sensitive";
        }
        else if (sensitivity.equals("No")||sensitivity.equals("no")){
            sensitivity = "insensitive";
        }
        System.out.print("Enter your request\n");
        String request = cin.nextLine();
        search_from_params(file_path, objCase, sensitivity, request);
    }

    /**
     * Function that performs analysis of file or web page using parameters
     * @param file_path - path to file or URL to web page
     * @param what_to_search - "word" or "regex"
     * @param case_sensitivity - "sensitive" or "insensetive"
     * @param request - word or regex you want to search for
     * Prints all the lines where given expression where found
     */
    public static void search_from_params(String file_path, String what_to_search, String case_sensitivity, String request) throws IOException {
        Scanner scanner = search_from(file_path);
        String regex;
        Pattern pattern;
        if (what_to_search.equals("word")){
            regex =  "\\b" + request.replace(",", "\\b|\\b") + "\\b";
        }
        else if (what_to_search.equals("regex")) {
            regex = request;
        }
        else {
            throw new IOException();
        }
        if (case_sensitivity.equals("sensitive")){
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
        else if (case_sensitivity.equals("insensitive")){
            pattern = Pattern.compile(regex);
        } else {
            throw new IOException();
        }
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()){
                System.out.print(line+"\n");
            }
        }
    }

    /**
     * Function reads file witch contains requests and performs search.
     * Each line of this file contains file path or url, type of request ("word" or "regex"),
     * case-sensitivity of search ("sensitive" or "insensitive"), request (word or regex you want to search).
     * @param file_path - path to file with requests
     * Prints path to file (or url) where search is performed and the resulr of the search.
     * @see grep_like#search_from_params(String, String, String, String)
     */
    public static void parse_from_file(String file_path) throws IOException {
        File file = new File(file_path);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            String[] params = scanner.nextLine().split(" ");
            System.out.print(params[0]+"\n");
            search_from_params(params[0], params[1], params[2], params[3]);
        }
    }
}
