package parsing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Repomock {
    private static volatile Repomock instance;
    private final FileWriter fileWriterB;
    private final FileWriter fileWriterH;

    private Repomock() throws IOException {
        fileWriterB = new FileWriter("body_file.txt", true);
        fileWriterH = new FileWriter("hrefs.txt", true);
    }
    public static Repomock getInstance() throws IOException {
        if (instance == null) { // Первая проверка (без блокировки)
            synchronized (Repomock.class) {
                if (instance == null) { // Вторая проверка (с блокировкой)
                    instance = new Repomock();
                }
            }
        }
        return instance;
    }

    public synchronized void writeToFile(Page page) throws IOException {
        fileWriterB.write(page.getContent() + "\n");
        fileWriterB.write("+++++++++++++" + "\n");
        fileWriterB.flush(); // Сброс буфера для записи в файл сразу
        fileWriterH.write(page.getPath() + "*************" + "\n");
        page.getCountLemmas().forEach((k,v)-> {
            try {
                fileWriterH.write(k + " " + v + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        fileWriterH.flush();
    }

    public void close() throws IOException {
        fileWriterB.close();
        fileWriterH.close();
    }


}
