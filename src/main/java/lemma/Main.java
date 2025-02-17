package lemma;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import parsing.Lemmatizer;

import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        LuceneMorphology luceneMorph = new RussianLuceneMorphology();

        String str = "ежедневно с 14 до 19 (посещение до договоренности) +7 \n" +
                "(985) 998 97 95 +7 (499) 253 86 07 дизайн и разработка - Addeo.ru Фото: Борис Сысоев";
        Lemmatizer lemmatizer = Lemmatizer.getInstance();
        Map<String, Integer> integerMap = lemmatizer.countLemmas(str);
        integerMap.forEach((k,v)->System.out.println(k + " - " + v));
    }


}
