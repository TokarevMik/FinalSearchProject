package finalSearchProject.fjpEx;
import finalSearchProject.fjpEx.services.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
@Component
class AppRunner implements CommandLineRunner {

    private final ParsingService parsingService;

    @Autowired
    public AppRunner(ParsingService parsingService) {
        this.parsingService = parsingService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Приложение запущено. Инициализируем парсинг из AppRunner...");
        try {
            parsingService.startParsing(); // Запускаем парсинг через ParsingService
        } catch (IOException e) {
            System.err.println("Ошибка при запуске парсинга: " + e.getMessage());
            e.printStackTrace(); // Важно обработать IOException
        }
    }
}

