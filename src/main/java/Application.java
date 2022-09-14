import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src/main/driver/chromedriver/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.get("https://www.avito.ru/");

        try {
            System.out.println("Exept");
            Select catigories_button = new Select(driver.findElement(By.xpath("//*[@id=\"category\"]")));

            catigories_button.selectByVisibleText("Оргтехника и расходники");

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

        WebElement input_search_word = driver.findElement(By.xpath("//input[contains(@data-marker, 'search-form/suggest')]"));
        input_search_word.sendKeys("Принтер");
        new Actions(driver)
                .keyDown(Keys.ENTER)
                .perform();

        WebElement city = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div/div/div/div/span/span/div"));
        city.click();

        WebElement city_field = driver.findElement(By.xpath("//*[@id=\"app\"]//div/input"));
        city_field.click();
        city_field.sendKeys("Владивосток");

        WebElement first_elem = driver.findElement(By.xpath("//*[@id=\"app\"]/div//ul/li[1]/span/span"));
        wait.until(ExpectedConditions.textToBePresentInElement(first_elem, "Владивосток"));
        first_elem.click();

        WebElement show_button = driver.findElement(By.xpath("//span/div//div/button"));
        show_button.click();

        WebElement check_box = driver.findElement(By.xpath("//form/div/div/div/div/div/div/div/label/span"));
        if (!check_box.isSelected()) {
            check_box.click();
        }

        WebElement show_all_button = driver.findElement(By.xpath("//button[contains(@data-marker, 'search-filters/submit-button')]"));
        show_all_button.click();

        Select price_selector = new Select(driver.findElement(By.xpath("//*[@id=\"app\"]/div/div/div/div/div/div/select")));
        price_selector.selectByVisibleText("Дороже");

        List<WebElement> all_items = driver.findElements(By.xpath("//div[contains(@data-marker, 'item')]/div/div[contains(@class,'item-body')]"));
        all_items.stream().limit(3)
                .map(x -> x.findElement(By.xpath("./div[contains(@class, 'title')]"))
                        .getText()).forEach(System.out::println);
        all_items.stream().limit(3)
                .map(x -> x.findElement(By.xpath("./div[contains(@class, 'price')]"))
                        .getText()).forEach(System.out::println);
        driver.quit();

    }
}
