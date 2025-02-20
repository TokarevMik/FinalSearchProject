package finalSearchProject.fjpEx;
import finalSearchProject.fjpEx.services.ParsingService;
import finalSearchProject.fjpEx.services.impl.ParsingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
    @Component // Помечаем ApplicationRunner как Spring Component
    class AppRunner implements CommandLineRunner { // Используем CommandLineRunner для запуска кода после старта приложения

        private final ParsingService parsingService; // Внедряем ParsingService

        @Autowired // Инъекция ParsingService через конструктор
        public AppRunner(ParsingService parsingService) {
            this.parsingService = parsingService;
        }

        @Override
        public void run(String... args) throws Exception {
            System.out.println("Приложение запущено. Инициализируем парсинг из AppRunner...");
            parsingService.startParsing(); // Вызываем метод startParsing из ParsingServiceImpl для запуска парсинга
        }
}
