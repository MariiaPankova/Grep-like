import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class html_parse {
    public static void main(String[] args) throws IOException {
        String url = "https://ru.wikipedia.org/wiki/%D0%9A%D1%83%D0%BB%D1%8C%D1%82%D1%83%D1%80%D0%BD%D1%8B%D0%B9_%D1%81%D0%B8%D0%BD%D0%B4%D1%80%D0%BE%D0%BC";
        //Document doc = Jsoup.connect(url).get();
        //Document dc = Jsoup.parse(doc.toString());
        //Elements link = dc.select("a");
        //System.out.print(doc);
        //System.out.print(dc.body().getElementsContainingOwnText("синдром"));
        getDyTag(url, "title");
        //Elements head = doc.select("синдром");
        //System.out.print(head);
    }
    public static void getDyTag(String url, String tag){
        String doc = null;
        try{
            doc = Jsoup.connect(url).get().toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.print(doc);
        Pattern start = Pattern.compile(String.join("", "<"+tag+".*>"));
        Pattern end = Pattern.compile(String.join("","<\\/"+tag+">"));
        Matcher matcher1 = start.matcher(doc);
        Matcher matcher2 = end.matcher(doc);
        while (matcher1.find() && matcher2.find()){
            System.out.print(doc.substring(matcher1.start(), matcher2.end())+"\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n\n");
        }
    }
}
