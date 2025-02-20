package finalSearchProject.fjpEx.parsing;

import java.util.concurrent.ForkJoinPool;

public class ThreadParser implements Runnable {
    WebPageParser webPageParser;

    public ThreadParser(WebPageParser webPageParser) {
        this.webPageParser = webPageParser;
    }

    @Override
    public void run() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(webPageParser);
    }
}

/*
* WebPageParser webPageParser;
    private final ForkJoinPool commonForkJoinPool; // <--- Общий ForkJoinPool

    public ThreadParser(WebPageParser webPageParser, ForkJoinPool forkJoinPool) { // <--- Принимаем ForkJoinPool в конструкторе
        this.webPageParser = webPageParser;
        this.commonForkJoinPool = forkJoinPool; // <--- Используем переданный ForkJoinPool
    }

    @Override
    public void run() {
        commonForkJoinPool.invoke(webPageParser); // <--- Используем общий ForkJoinPool для запуска задачи
    }
*
* */


