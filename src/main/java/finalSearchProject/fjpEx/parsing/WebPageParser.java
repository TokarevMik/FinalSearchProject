package finalSearchProject.fjpEx.parsing;

import finalSearchProject.fjpEx.model.Page;
import finalSearchProject.fjpEx.services.impl.LemmaService;
import finalSearchProject.fjpEx.services.LemmaServiceInterface;
import lombok.Getter;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
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


@Getter
public class WebPageParser extends RecursiveAction {
//public class WebPageParser extends RecursiveTask<Integer> {
    String url;
    String domain;
    String content;

    private final transient Repomock repomock;
    private final LemmaServiceInterface lemmaService = new LemmaService();

    public WebPageParser(String url, String domain ) throws IOException {
        this.url = url;
        this.domain = domain;
        repomock = Repomock.getInstance();
    }

    public static Set<String> isAlreadyRead = new CopyOnWriteArraySet<>();

    private static final String[] EXTENSIONS_LIST = {"redirect", "zip", "js",
            "#", ".jpg", ".jpeg", ".png", ".gif", ".pdf",
            ".php", "mailto:", "resolve?", "xml", "callto", "tel:", "t.me", "?", "bmp"};

    Set<String> setUrls = new HashSet<>();
    public void setUrlAndDomain(String url, String domain) {
        this.url = url;
        this.domain = domain;
    }

    protected void compute() {
        Integer counter = 1;
        Set<WebPageParser> taskList = new CopyOnWriteArraySet<>(); // список тасков
        try {
            Connection.Response response = new UrlConnectorImpl(url).getResponse();
            int statusCode = response.statusCode();
            if (statusCode < 400) {
                Document document = response.parse();
                content = document.body().text();
                Map<String, Integer> countLemmas = lemmaService.countLemmas(content);
//                Page page = new Page(content, url, domain, countLemmas);
                Page page = new Page();
                page.setContent(content);
                page.setPath(url);
                page.setDomain(domain);
                page.setCountLemmas(countLemmas);
                repomock.writeToFile(page);
                Elements elements = document.body().getElementsByTag("a");

                for (Element element : elements) {
                    String href = element.attr("href");
                    if (!hrefValidate(href)) {
                        continue;
                    }
                    if (href.startsWith(domain)) {
                        setUrls.add(href);
                    } else if (href.startsWith("/")) {
                        href = domain.concat(href);
                        setUrls.add(href);
                    }
                }

                for (String url : setUrls) {
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
            }
        } catch (HttpStatusException httpStatusException) {
            if (httpStatusException.getStatusCode() == 404) {
                System.err.println("Страница не найдена (404): " + url);
            } else {
                System.err.println("HTTP ошибка при доступе к URL: " + url + ", Статус код: " + httpStatusException.getStatusCode() + ", Сообщение: " + httpStatusException.getMessage());
                throw new RuntimeException(httpStatusException);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        for(WebPageParser webPageParser:taskList){
//            counter+=webPageParser.join();
//        }
//        return counter;
    }//end of compute


    boolean hrefValidate(String href) {
        for (String e : EXTENSIONS_LIST) {
            if (href.toLowerCase().contains(e)) return false;
        }
        return true;
    }
}
