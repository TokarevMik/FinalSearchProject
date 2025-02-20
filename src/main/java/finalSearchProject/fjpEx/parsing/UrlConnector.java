package finalSearchProject.fjpEx.parsing;

import org.jsoup.Connection;

public interface UrlConnector {
    Connection.Response getResponse();
    int codeResponse();
}
