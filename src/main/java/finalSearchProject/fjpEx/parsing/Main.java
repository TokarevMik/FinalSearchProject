package finalSearchProject.fjpEx.parsing;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
//        http://www.playback.ru/ -?
        //https://volochek.life/ +
        //http://radiomv.ru --
        //https://ipfran.ru -?
        //https://dimonvideo.ru-? //использовать для проверки остановки поиска
        //https://nikoartgallery.com
        //https://et-cetera.ru/mobile --
        //https://www.lutherancathedral.ru
        //https://dombulgakova.ru  +
        //https://www.svetlovka.ru
        Set<String> setNames = new HashSet<>();
        String url1 = "https://www.svetlovka.ru";
        String url2 = "https://www.lutherancathedral.ru";
        String url3 = "http://radiomv.ru";
        List<String> urlList = new ArrayList<>();
        urlList.add(url1);
        urlList.add(url2);
        urlList.add(url3);
//        try {
//            for(String url:urlList){
//                UrlConnector urlConnector = new UrlConnectorImpl(url);
//                if(urlConnector.codeResponse()==200){
////                    new Thread(new ThreadParser(new WebPageParser(url,url))).start();
//                } else {
//                    System.out.printf("Адрес %s не отвечает %n",url);
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Ошибка при создании WebPageParser task: " + e.getMessage());
//            e.printStackTrace();
//        }
    }


}
