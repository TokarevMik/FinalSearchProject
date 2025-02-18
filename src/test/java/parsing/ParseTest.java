package parsing;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParseTest {
    /*private Parse parse;
    final String url = "https://www.example.com";
    final String domain = "https://www.example.com";

    @BeforeEach
    void setUp() {
        parse = new Parse(url, domain);
    }

    @Test
    void testGetParse_ValidLinks() throws IOException {

        String html = "<html><body><a href=\"https://www.example.com/page1/\">" +
                "Link 1</a><a href=\"/page2/\">Link 2</a></body></html>";
        Document mockDocument = Jsoup.parse(html, domain);
        try (MockedStatic<Jsoup> jsoupMock = Mockito.mockStatic(Jsoup.class)) {
            Connection mockConnection = mock(Connection.class);
            jsoupMock.when(() -> Jsoup.connect(url)).thenReturn(mockConnection);
            when(mockConnection.timeout(0)).thenReturn(mockConnection);
            when(mockConnection.userAgent(anyString())).thenReturn(mockConnection);
            when(mockConnection.maxBodySize(0)).thenReturn(mockConnection);
            when(mockConnection.get()).thenReturn(mockDocument); // ВАЖНО: теперь get() возвращает Document

            parse.getParse();
            // Check if the correct links were added to the set
            Set<String> expectedLinks = new HashSet<>();
            expectedLinks.add("https://www.example.com/page1/");
            expectedLinks.add("https://www.example.com/page2/");  // Домен добавляется автоматически

            assertEquals(expectedLinks, parse.setNames);
            File file = new File("hrefs.txt");
            assertTrue(file.exists());

            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testValidHref() {
        assertTrue(parse.hrefValidate(("/books/collections/skazki-avtoportret-i-moskovskaya-poeziya/")));
    }

    @Test
    void testInvalidHrefTel() {
        assertFalse(parse.hrefValidate(("tel:84992548475")));
    }

    @Test
    void testInvalidHrefHash() {
        assertFalse(parse.hrefValidate(("#")));
        assertFalse(parse.hrefValidate(("/books/#recomend")));
    }

    @Test
    void testInvalidHrefTelegram() {
        assertFalse(parse.hrefValidate(("https://t.me/svetlovkamoscow")));
    }

    @Test
    void testInvalidHrefMail() {
        assertFalse(parse.hrefValidate(("mailto:svetlovka@culture.mos.ru")));

    }

    @Test
    void testInvalidHrefJs() {
        assertFalse(parse.hrefValidate(("mailto:svetlovka@culture.mos.ru")));

    }

    @Test
    void testInvalidHrefRedirect() {
        assertFalse(parse.hrefValidate(("https://www.test-url.com/redirect")));
        assertFalse(parse.hrefValidate(("/https://www.test-url.com/go?to=somewhere")));

    }

    @Test
    void testInvalidHrefJpeg() {
        assertFalse(parse.hrefValidate(("/image.jpg")));
        assertFalse(parse.hrefValidate(("/image.jpeg")));
    }*/

}