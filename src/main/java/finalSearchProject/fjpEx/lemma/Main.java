package finalSearchProject.fjpEx.lemma;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;

public class Main {
    private static final String[] EXTENSIONS_LIST = {"redirect", "php", "js",
            "#", ".jpg", ".jpeg", ".png", ".gif", ".pdf",
            ".php", "mailto:", "resolve?", "callto", "tel:", "t.me", "?","javascript"};
    public static void main(String[] args) throws IOException {
        LuceneMorphology luceneMorph = new RussianLuceneMorphology();

        String st ="https://dombulgakova.ru/?method=ical&id=5905";

        /*String str = "ежедневно с 14 до 19 (посещение до договоренности) +7 \n" +
                "(985) 998 97 95 +7 (499) 253 86 07 дизайн и разработка - Addeo.ru Фото: Борис Сысоев";
        Lemmatizer lemmatizer = Lemmatizer.getInstance();
        Map<String, Integer> integerMap = lemmatizer.countLemmas(str);
        integerMap.forEach((k,v)->System.out.println(k + " - " + v));*/
        System.out.println(hrefValidate(st));
    }
    static boolean hrefValidate(String href) {
        for (String e : EXTENSIONS_LIST) {
            if (href.toLowerCase().contains(e)) return false;
        }
        return true;
    }

}
