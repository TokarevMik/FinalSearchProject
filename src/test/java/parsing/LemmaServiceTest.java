package parsing;

import finalSearchProject.fjpEx.services.LemmaService;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LemmaServiceTest {
    /*private LuceneMorphology morphology;
    private LemmaService lemmaService;

    @BeforeEach
    void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        // Мокаем LuceneMorphology
        morphology = Mockito.mock(RussianLuceneMorphology.class);

        // Создаём экземпляр Lemmatizer
//        lemmaService = LemmaService.getInstance();

        // Подменяем morphology через рефлексию
        Field field = LemmaService.class.getDeclaredField("morphology");
        field.setAccessible(true);
        field.set(lemmaService, morphology);
    }

    @Test
    void testCountLemmas_NormalText() {
        String text = "Кошка гуляет по парку";
        when(morphology.getMorphInfo("кошка")).thenReturn(List.of("кошка СУЩ"));
        when(morphology.getNormalForms("кошка")).thenReturn(List.of("кошка"));

        when(morphology.getMorphInfo("гуляет")).thenReturn(List.of("гулять ГЛАГОЛ"));
        when(morphology.getNormalForms("гуляет")).thenReturn(List.of("гулять"));

        when(morphology.getMorphInfo("по")).thenReturn(List.of("по ПРЕДЛ"));
        when(morphology.getNormalForms("по")).thenReturn(List.of("по"));

        when(morphology.getMorphInfo("парку")).thenReturn(List.of("парк СУЩ"));
        when(morphology.getNormalForms("парку")).thenReturn(List.of("парк"));

        Map<String, Integer> lemmas = lemmaService.countLemmas(text);

        assertNotNull(lemmas);
        assertEquals(3, lemmas.size());
        assertEquals(1, lemmas.get("кошка"));
        assertEquals(1, lemmas.get("гулять"));
        assertEquals(1, lemmas.get("парк"));
    }

    @Test
    void testCountLemmas_RepeatedWords() {
        String text = "кошка кошка кошка";
        when(morphology.getMorphInfo("кошка")).thenReturn(List.of("кошка СУЩ"));
        when(morphology.getNormalForms("кошка")).thenReturn(List.of("кошка"));

        Map<String, Integer> lemmas = lemmaService.countLemmas(text);

        assertNotNull(lemmas);
        assertEquals(1, lemmas.size());
        assertEquals(3, lemmas.get("кошка"));
    }

    @Test
    void testCountLemmas_WithPrepositionsAndParticles() {
        String text = "кошка и собака в доме";
        when(morphology.getMorphInfo("кошка")).thenReturn(List.of("кошка СУЩ"));
        when(morphology.getNormalForms("кошка")).thenReturn(List.of("кошка"));

        when(morphology.getMorphInfo("и")).thenReturn(List.of("и СОЮЗ"));

        when(morphology.getMorphInfo("собака")).thenReturn(List.of("собака СУЩ"));
        when(morphology.getNormalForms("собака")).thenReturn(List.of("собака"));

        when(morphology.getMorphInfo("в")).thenReturn(List.of("в ПРЕДЛ"));

        when(morphology.getMorphInfo("доме")).thenReturn(List.of("дом СУЩ"));
        when(morphology.getNormalForms("доме")).thenReturn(List.of("дом"));

        Map<String, Integer> lemmas = lemmaService.countLemmas(text);

        assertNotNull(lemmas);
        assertEquals(3, lemmas.size());
        assertEquals(1, lemmas.get("кошка"));
        assertEquals(1, lemmas.get("собака"));
        assertEquals(1, lemmas.get("дом"));
    }

    @Test
    void testCountLemmas_EmptyText() {
        String text = "";
        Map<String, Integer> lemmas = lemmaService.countLemmas(text);

        assertNotNull(lemmas);
        assertTrue(lemmas.isEmpty());
    }*/

}
