package finalSearchProject.fjpEx.parsing;

//import finalSearchProject.fjpEx.services.LemmaService;

import finalSearchProject.fjpEx.model.Page;
import finalSearchProject.fjpEx.services.LemmaServiceInterface;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired; // Импорт аннотации @Autowired

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;


@Component
public class WebPageParser extends RecursiveAction {
    String url = "";
    String domain;
    String content;

    private final transient Repomock repomock;
    private final LemmaServiceInterface lemmaService;
    private final ApplicationContext applicationContext;

    @Autowired
    public WebPageParser(LemmaServiceInterface lemmaService, ApplicationContext applicationContext, String url, String domain) throws IOException {
        this.lemmaService = lemmaService;
        this.applicationContext = applicationContext;
        this.url = url;
        this.domain = domain;
        repomock = Repomock.getInstance();
    }

    public static Set<String> isAlreadyRead = new CopyOnWriteArraySet<>();

    private static final String[] EXTENSIONS_LIST = {"redirect", "zip", "js",
            "#", ".jpg", ".jpeg", ".png", ".gif", ".pdf",
            ".php", "mailto:", "resolve?", "xml", "callto", "tel:", "t.me", "?", "bmp"};

    Set<String> setUrls = new HashSet<>();

    protected void compute() {
        try {
            Connection.Response response = new UrlConnectorImpl(url).getResponse();
            int statusCode = response.statusCode();
            if (statusCode < 400) {
                Document document = response.parse();
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
                content = document.body().text();
                Map<String, Integer> countLemmas = lemmaService.countLemmas(content);
//                Page page = new Page(content, url, domain, countLemmas);
                Page page = new Page();
                page.setContent(content);
                page.setPath(url);
                page.setDomain(domain);
                page.setCountLemmas(countLemmas);
                repomock.writeToFile(page);

                Set<WebPageParser> taskList = new CopyOnWriteArraySet<>(); // список тасков
                for (String url : setUrls) {
                    if (!isAlreadyRead.contains(url)) {
                        isAlreadyRead.add(url);
                        WebPageParser webPageParserTask = applicationContext.getBean(WebPageParser.class, lemmaService, url, domain);
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

    }//end of compute


    boolean hrefValidate(String href) {
        for (String e : EXTENSIONS_LIST) {
            if (href.toLowerCase().contains(e)) return false;
        }
        return true;
    }
}
