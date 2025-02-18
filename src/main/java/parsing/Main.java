package parsing;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class Main {
    public static void main(String[] args) throws IOException {
//        http://www.playback.ru/ -?
        //https://volochek.life/ +
        //http://radiomv.ru --
        //https://ipfran.ru -?
        //https://dimonvideo.ru-?
        //https://nikoartgallery.com
        //https://et-cetera.ru/mobile --
        //https://www.lutherancathedral.ru
        //https://dombulgakova.ru  +
        //https://www.svetlovka.ru
        String url = "https://dimonvideo.ru";
        // Смотреть ForkJoinNew Basic
        Set<String> setNames = new HashSet<>();
        /*Parse root = new Parse(url, url);
        root.getParse();*/
        WebPageParser root = null;
        try {
            root = new WebPageParser(url, url);
        } catch (IOException e) {
            System.err.println("Ошибка при создании WebPageParser task: " + e.getMessage());
            e.printStackTrace();
            return; // Завершаем выполнение программы в случае ошибки
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(root);
        forkJoinPool.shutdown();

    }


}
