package parsing;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPageParser extends RecursiveAction {
    String url = "";
    String domain;
    String content;
    String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    private final transient Repomock repomock;

    WebPageParser(String url, String domain) throws IOException {
        this.url = url;
        this.domain = domain;
        repomock = Repomock.getInstance();
    }

    public static Set<String> isAlreadyRead = new CopyOnWriteArraySet<>();

    private static final String[] EXTENSIONS_LIST = {"redirect", "php", "js",
            "#", ".jpg", ".jpeg", ".png", ".gif", ".pdf",
            ".php", "mailto:", "resolve?", "go?", "callto", "tel:", "t.me","?"};

    Set<String> setNames = new HashSet<>();

    protected void compute() {
        try {
            Document document = Jsoup.connect(url).timeout(0)
                    .userAgent(USER_AGENT)
//                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
//                    .header("Accept-Language", "en-US,en;q=0.5")
//                    .header("Cache-Control", "max-age=0")
//                    .header("Connection", "keep-alive")
                    .maxBodySize(0).get();
            Elements elements = document.body().getElementsByTag("a");
            for (Element element : elements) {
                String href = element.attr("href");
                if (!hrefValidate(href)) {
                    continue;
                }
                if (href.startsWith(domain)) {
                    setNames.add(href);
                } else if (href.startsWith("/")) {
                    href = domain.concat(href);
                    setNames.add(href);
                }

            }
            content = document.body().text();

            Lemmatizer lemmatizer = Lemmatizer.getInstance();
            Map<String, Integer> countLemmas = lemmatizer.countLemmas(content);
            Page page = new Page(content, url, domain, countLemmas);
            repomock.writeToFile(page);

            Set<WebPageParser> taskList = new CopyOnWriteArraySet<>(); // список тасков
            for (String url : setNames) {
                if (!isAlreadyRead.contains(url)) {
                    isAlreadyRead.add(url);
                    WebPageParser webPageParserTask = new WebPageParser(url, domain);
                    webPageParserTask.fork();
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    taskList.add(webPageParserTask);
                }
            }
            taskList.forEach(ForkJoinTask::join);

        } catch (HttpStatusException httpStatusException) {
            if (httpStatusException.getStatusCode() == 404) {
                System.err.println("Страница не найдена (404): " + url);
                // Обработка 404 ошибки:
                //  - Просто пропустить эту ссылку и продолжить сканирование (как в примере ниже).
                //  - Записать URL в лог "битых ссылок" для дальнейшего анализа.
                //  - Выполнить другие действия, если необходимо.
            } else {
                // Обработка других HTTP ошибок (например, 500 Server Error, 403 Forbidden и т.д.), если нужно
                System.err.println("HTTP ошибка при доступе к URL: " + url + ", Статус код: " + httpStatusException.getStatusCode() + ", Сообщение: " + httpStatusException.getMessage());
                throw new RuntimeException(httpStatusException); // Переброс RuntimeException для других HTTP ошибок (или обработка по-другому)
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }//end of compute


    boolean hrefValidate(String href) {
        for (String e : EXTENSIONS_LIST) {
            if (href.toLowerCase().contains(e)) return false;
        }
        return true;
    }
}
