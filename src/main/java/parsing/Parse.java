package parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    String url = "";
    String domain;
    String content;

    Parse(String url, String domain) {
        this.url = url;
        this.domain = domain;
    }

    private static final String[] EXTENSIONS_LIST = {"redirect", "php", "js",
            "#", ".jpg", ".jpeg", ".png", ".gif", ".pdf",
            ".php", "mailto:", "resolve?", "go?", "callto", "tel:", "t.me"};
    Set<String> setNames = new HashSet<>();

    public void getParse() {
        try {
            Document document = Jsoup.connect(url).timeout(0).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6)" +
                    " Gecko/20070725 Firefox/2.0.0.6").maxBodySize(0).get();
            Elements elements = document.body().getElementsByTag("a");
            for (Element element : elements) {
                String href = element.attr("href");
                if (!hrefValidate(href)) continue;
                Pattern pattern = Pattern.compile(domain + "[\\w,\\D]+/$"); // ссылка с доменом
                Pattern pattern2 = Pattern.compile("^/[\\w,\\D]+/$"); // ссылка без домена
                Matcher matcher = pattern.matcher(href);
                Matcher matcher2 = pattern2.matcher(href);
                if (matcher.matches()) {
                    setNames.add(href);
                }
                if (matcher2.matches()) {
                    href = domain.concat(href);
                    setNames.add(href);
                }

            }
            content = document.body().text();

            Lemmatizer lemmatizer = Lemmatizer.getInstance();

            Map<String,Integer> countLemmas =  lemmatizer.countLemmas(content);

            File fileHrefs = new File("hrefs.txt");  //проверка - потом убрать
            File fileWithContent = new File("body_file.txt");  //проверка - потом убрать
            FileWriter fileWriter = new FileWriter(fileHrefs, true);  //проверка - потом убрать
            FileWriter contentWriter = new FileWriter(fileWithContent, true);  //проверка - потом убрать

            contentWriter.write(content);  //проверка - потом убрать
            for (String s : setNames) {
                fileWriter.write(s + "\n");
            }
            contentWriter.write("\n");
            for (Map.Entry<String,Integer> entry:countLemmas.entrySet()){
                String line = entry.getKey() + " - " + entry.getValue();
                contentWriter.write(line + "\n");
            }
            fileWriter.flush();  //проверка - потом убрать
            contentWriter.flush(); //проверка - потом убрать
            fileWriter.close();  //проверка - потом убрать
            contentWriter.close(); //проверка - потом убрать
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    boolean hrefValidate(String href) {
        for (String e : EXTENSIONS_LIST) {
            if (href.contains(e)) return false;
        }
        return true;
    }
}
