package parsing;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Page {
    String content;
    String path;
    String domain;
    Map<String, Integer> countLemmas = new HashMap<>();

    public Page(String content, String path, String domain, Map<String, Integer> countLemmas) {
        this.content = content;
        this.path = path;
        this.domain = domain;
        this.countLemmas = countLemmas;
    }

    public String getContent() {
        return content;
    }


    public String getPath() {
        return path;
    }

    public String getDomain() {
        return domain;
    }


    public Map<String, Integer> getCountLemmas() {
        return countLemmas;
    }

}
