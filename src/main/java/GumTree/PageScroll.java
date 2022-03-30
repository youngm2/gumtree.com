package GumTree;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Random;

public class PageScroll {
    public static void scrollToTop(WebDriver driver) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0,0)");
    }

    public static void scrollToBottom(WebDriver driver) {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static void scrollToElementTop(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToElementBottom(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(false);", element);
    }


    public static class RandomGenerator {

            public static int GenerateRandomNumber(int upperLimit) {
                Random rand = new Random();
                int upperbound = upperLimit;
                //generate random values from 1-6
                int int_random = rand.nextInt(upperbound) + 1;
                System.out.println("Random integer value from 1 to " + (upperbound) + " : "+ int_random);
                return (int_random);
            }
    }
}
