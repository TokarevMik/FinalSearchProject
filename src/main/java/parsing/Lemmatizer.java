package parsing;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.*;

public class Lemmatizer {
    //    String content;
    private final LuceneMorphology morphology;
    private static final String[] particlesNames = new String[]{"МЕЖД", "ПРЕДЛ", "СОЮЗ"};

    public static Lemmatizer getInstance() throws IOException {
        LuceneMorphology morphology = new RussianLuceneMorphology();
        return new Lemmatizer(morphology);
    }

    private Lemmatizer(LuceneMorphology morphology) {
        this.morphology = morphology;
    }

    private Lemmatizer() {
        throw new RuntimeException("Disallow construct");
    }


    public Map<String, Integer> countLemmas(String content) {
        String[] allWords = getArrayOfWords(content);
        Map<String, Integer> lemmas = new HashMap<>();
        for (String word : allWords) {
            if (word.isBlank()) {
                continue;
            }
            List<String> wordBaseForms = morphology.getMorphInfo(word);
            if (anyWordBaseBelongToParticle(wordBaseForms)) {
                continue;
            }
            List<String> normalForms = morphology.getNormalForms(word);
            if (normalForms.isEmpty()) {
                continue;
            }
            String normalWord = normalForms.get(0);
            if (lemmas.containsKey(normalWord)) {
                lemmas.put(normalWord, lemmas.get(normalWord) + 1);
            } else {
                lemmas.put(normalWord, 1);
            }
        }
        return lemmas;
    }

    private String[] getArrayOfWords(String content) {
        content = content.toLowerCase(Locale.ROOT);
        return content.replaceAll("([^а-я\\s])", " ")
                .replaceAll("[А-ЯЁ]\\.", "") //убирает инициалы - спорное решение
                .trim()
                .split("\\s+");
    }

    private boolean anyWordBaseBelongToParticle(List<String> wordBaseForms) {
        return wordBaseForms.stream().anyMatch(this::hasParticleProperty);
    }

    private boolean hasParticleProperty(String wordBase) {
        for (String property : particlesNames) {
            if (wordBase.toUpperCase().contains(property)) {
                return true;
            }
        }
        return false;
    }
}
