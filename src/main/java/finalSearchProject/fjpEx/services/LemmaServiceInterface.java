package finalSearchProject.fjpEx.services;

import java.io.IOException;
import java.util.Map;

public interface LemmaServiceInterface {
//    static LemmaServiceInterface getInstance() throws IOException {
//        throw new UnsupportedOperationException("getInstance method should be implemented in the implementing class");
//    }

    Map<String, Integer> countLemmas(String content);
}
