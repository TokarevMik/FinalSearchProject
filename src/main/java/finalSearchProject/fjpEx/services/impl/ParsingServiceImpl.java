package finalSearchProject.fjpEx.services.impl;

import finalSearchProject.fjpEx.config.SitesList;
import finalSearchProject.fjpEx.model.Site;
import finalSearchProject.fjpEx.parsing.ThreadParser;
import finalSearchProject.fjpEx.parsing.UrlConnector;
import finalSearchProject.fjpEx.parsing.UrlConnectorImpl;
import finalSearchProject.fjpEx.parsing.WebPageParser;
import finalSearchProject.fjpEx.services.LemmaServiceInterface;
import finalSearchProject.fjpEx.services.ParsingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ParsingServiceImpl implements ParsingService {
    private final LemmaServiceInterface lemmaService;
    private final SitesList sites;
//    private final SiteRepo siteRepo;
//    private final PageRepo pageRepo;
//    private final SitesList sites;

    @Override
    public void startParsing() throws IOException, InterruptedException {
        Map<String, Thread> parsingThreads = new HashMap<>();
        for (Site site : sites.getSites()) {
            UrlConnector urlConnector = new UrlConnectorImpl(site.getUrl());
            if (urlConnector.codeResponse() == 200) {
                new Thread(new ThreadParser(new WebPageParser(site.getUrl(), site.getUrl()))).start();
            } else {
                System.out.printf("Адрес %s не отвечает %n", site.getUrl());
            }
        }
        System.out.println("Парсинг запущен в отдельных потоках...");
        for (Map.Entry<String, Thread> entry : parsingThreads.entrySet()) {
            entry.getValue().join();
            System.out.printf("Парсинг %s завершено", entry.getKey());
        }
    }
    //end startParsing
}
