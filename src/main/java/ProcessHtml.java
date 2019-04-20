import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ProcessHtml {

    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }


    private static boolean hasDBMSname(Element element) {
        String nodename = element.nodeName();

        return nodename.equals("a") && element.parentNode().nodeName().equals("th")
                && element.parentNode().attributes().hasKey("class")
                && element.parentNode().attributes().get("class").equals("pad-l");
    }

    public static void main(String[] args) throws Exception {

        String htmlText = getText("https://db-engines.com/en/ranking");

        Document document = Jsoup.parse(htmlText);
        Elements allElements =
                document.getAllElements();
        for (Element element : allElements) {
            String text = element.ownText();
            if (hasDBMSname(element)) {
                System.out.println("1. " + text);
            }
        }

       // System.out.println(getText("https://db-engines.com/en/ranking"));


    }
}
