package stepDef;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StepDef {

    private static WebDriver driver;

    @Пусть("открыт ресурс авито")
    public static void setDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/driver/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.avito.ru/");
    }
    @И("в выпадающем списке категорий выбрана {}")
    public static void selectCat(String cat) {
        try {
            System.out.println("Exept");
            Select catigories_button = new Select(driver.findElement(By.xpath("//*[@id=\"category\"]")));

            catigories_button.selectByVisibleText(cat);

        } catch (Exception e) {
            WebElement catigories_button = driver.findElement(By.xpath("//span//div/button"));
            //Select catigories_button = new Select(driver.findElement(By.xpath("//*[@id=\"category\"]")));
            catigories_button.click();
            //catigories_button.selectByVisibleText("Оргтехника и расходники");
            List<WebElement> catigories = driver.findElements(By.xpath("//span//div//li/a"));
            for (WebElement catigory : catigories) {
                if (catigory.getText().toLowerCase().equals("оргтехника и расходники")) {
                    catigory.click();
                    break;
                }
            }
        }
    }
    @И("в поле поиска введено значение {}")
    public static void insertItem(String item) {
        WebElement input_search_word = driver.findElement(By.xpath("//input[contains(@data-marker, 'search-form/suggest')]"));
        input_search_word.sendKeys(item);
        new Actions(driver)
                .keyDown(Keys.ENTER)
                .perform();
    }
    @Тогда("кликнуть по выпадающему списку региона")
    public static void regionList() {
        WebElement city = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div/div/div/div/span/span/div"));
        city.click();
    }

    @Тогда("в поле регион введено значение {}")
    public static void selectRegion(String region) {
        WebElement city_field = driver.findElement(By.xpath("//*[@id=\"app\"]//div/input"));
        city_field.click();
        city_field.sendKeys(region);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement first_elem = driver.findElement(By.xpath("//*[@id=\"app\"]/div//ul/li[1]/span/span"));
        wait.until(ExpectedConditions.textToBePresentInElement(first_elem, region));
        first_elem.click();
    }

    @И("нажата кнопка показать объявления")
    public static void showThings() {
        WebElement show_button = driver.findElement(By.xpath("//span/div//div/button"));
        show_button.click();
    }

    @И("активирован чекбокс только с доставкой")
    public static void checkbox() {
        WebElement check_box = driver.findElement(By.xpath("//label[@data-marker=\"delivery-filter\"]"));
        new Actions(driver)
                .keyDown(Keys.PAGE_DOWN)
                .perform();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(check_box));
        if (!check_box.isSelected()) {
            check_box.click();
        }
        WebElement show_all_button = driver.findElement(By.xpath("//button[contains(@data-marker, 'search-filters/submit-button')]"));
        show_all_button.click();
    }
    @И("в выпадающем списке сортировка выбрано значение {}")
    public static void sortList(String param) {
        Select price_selector = new Select(driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div/div/div/select")));
        price_selector.selectByVisibleText(param);
    }
    @И("в консоль выведено значение названия и цены {int} первых товаров")
    public static void sortList(int top) {
        List<WebElement> all_items = driver.findElements(By.xpath("//div[contains(@data-marker, 'item')]/div/div[contains(@class,'item-body')]"));
        all_items.stream().limit(top)
                .map(x -> x.findElement(By.xpath("./div[contains(@class, 'title')]"))
                        .getText()).forEach(System.out::println);
        all_items.stream().limit(top)
                .map(x -> x.findElement(By.xpath("./div[contains(@class, 'price')]"))
                        .getText()).forEach(System.out::println);
        driver.quit();

    }
}
