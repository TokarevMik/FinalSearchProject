package finalSearchProject.fjpEx.parsing;

import lombok.AllArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

@AllArgsConstructor
public class UrlConnectorImpl implements UrlConnector {
    String url;
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    @Override
    public Connection.Response getResponse() {
        try {
            return Jsoup.connect(url).timeout(0)
                    .userAgent(USER_AGENT)
                    .referrer("http://www.google.com")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Cache-Control", "max-age=0")
                    .header("Connection", "keep-alive")
                    .maxBodySize(0)
                    .followRedirects(false)
                    .execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int codeResponse(){
        return getResponse().statusCode();
    }
}
