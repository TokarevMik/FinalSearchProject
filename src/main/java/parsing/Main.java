package parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
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
        String url = "https://nikoartgallery.com";
        // Смотреть ForkJoinNew Basic
        Set<String> setNames = new HashSet<>();
        Parse root = new Parse(url, url);
        root.getParse();

    }


}
