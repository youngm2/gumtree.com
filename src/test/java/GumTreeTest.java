import GumTree.PageScroll;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GumTreeTest {

@Test
public void GumtreeHomePageSearchTest() throws InterruptedException {

    WebDriverManager.chromedriver().setup();
    WebDriver driver = new ChromeDriver();
    driver.get("https://www.gumtree.com.au");
    Thread.sleep(3000);

    String CATEGORY = "Electronics & Computer";
    String SEARCH_TEXT = "Sennheiser Headphones";
    String LOCATION_SEARCH = "Sydney Region, NSW";

    // click on I understand Button
    WebElement iUnderstandButton = driver.findElement(By.xpath("//*[@id='modal-body-0']/div/div/div/a[contains(@class, 'warning__button-hide')]"));
    webElementClick(iUnderstandButton,driver);

    // Enter Search Text
    WebElement searchTextBox = driver.findElement(By.xpath("//*[@id='search-query']"));
    setTextToWebElement(searchTextBox,SEARCH_TEXT);

    // Click on Category drop down list
    WebElement catDDList = driver.findElement(By.xpath("//*[@id='categoryId-wrp']"));
    System.out.println("clickable category list");
    webElementClick(catDDList, driver);

    // Select a specific category
    WebElement categorySelected = driver.findElement(By.xpath("//*[@id='categoryId-wrp-option-20045']"));
    webElementClick(categorySelected, driver);

    // Select Location
    WebElement locationTextBox = driver.findElement(By.xpath("//*[@id='search-area']"));
    setTextToWebElement(locationTextBox,LOCATION_SEARCH);

    // Select Radius
    WebElement radiusDDList = driver.findElement(By.xpath("//*[@id='srch-radius-input']"));
    webElementClick(radiusDDList, driver);
    WebElement radiusSelected = driver.findElement(By.xpath("//*[@id='srch-radius-wrp-option-20']"));
    webElementClick(radiusSelected, driver);

    // Click on Search button
    WebElement searchButton = driver.findElement(By.xpath("//*[@id='search-form-form']/ul/li[contains(@class, 'search-submit')]/button"));
    webElementClick(searchButton,driver);

    // Click on any random Ad details
    WebDriverWait wait = new WebDriverWait(driver, 60);
    driver.manage().window().maximize();
    int randomNumber = PageScroll.RandomGenerator.GenerateRandomNumber(10);

    JavascriptExecutor js = (JavascriptExecutor) driver;
    // js.executeScript("window.scrollBy(0,700)", "");

    Thread.sleep(3000);
    WebElement selectAdDetail = driver.findElement(By.xpath("//*/a[INDEX][contains(@id, 'user-ad-1')]/div/div/img".replace("INDEX", String.valueOf(randomNumber))));
    PageScroll.scrollToElementBottom(driver, selectAdDetail);
    webElementMouseOver(selectAdDetail, driver);
    wait.until(ExpectedConditions.visibilityOf(selectAdDetail));
    webElementClick(selectAdDetail,driver);

    Thread.sleep(5000);

    //Verify Ad ID available
    WebElement adIDNumber = driver.findElement(By.xpath("//*[@id='breadcrumbs__desktop-sentinel']/div/div/span[contains(@class,'breadcrumbs__summary')]"));
    PageScroll.scrollToTop(driver);
    wait.until(ExpectedConditions.visibilityOf(adIDNumber));
    webElementIsDisplayed(adIDNumber);
    String ad = adIDNumber.getText();

    System.out.print ("ad id =" + ad);

    String adStrNew = ad.replaceAll("[^0-9]", "");

    Assert.assertNotNull(adStrNew.getBytes(),"No Ad ID available");
    Assert.assertTrue(isNumeric(adStrNew), "Ad ID not numeric");
    //js.executeScript("window.scrollBy(0,1000)", "");

    // similarAdsLabel
    WebElement similarAdsLabel = driver.findElement(By.xpath("//*[@id='react-root']//div[text()='Similar Ads']"));
    webElementMouseOver(similarAdsLabel,driver);
    Assert.assertTrue(webElementIsDisplayed(similarAdsLabel),"Similar Ads not found");

    //similarAdsLabelFirstItem = driver.findElement(By.xpath("//*[@class='view-item-page__item-and-related']/div[5]//li[1]/a[contains(@id,'user-ad')]/div//img"));
    WebElement similarAdsLabelFirstItem = driver.findElement(By.xpath("//*[@class='view-item-page__item-and-related']/div[5]//li[1]/a[contains(@id,'user-ad')]/div//img"));
    Assert.assertTrue(webElementIsDisplayed(similarAdsLabelFirstItem),"Similar Ads 1st Item not found");

    driver.quit();
}
    public boolean webElementIsDisplayed(WebElement webElement) {
        boolean elementIsDisplayed = false;
        for(int retry = 0; retry < 5; retry++) {
            try {
                elementIsDisplayed = webElement.isDisplayed();
                break;
            } catch (Exception e) {
                System.out.println("element not displaying... retry " + (retry + 1));
                System.out.println("e: " + e);
            }
        }
        return elementIsDisplayed;
    }

    public void webElementClick(WebElement webElement, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        for(int retry = 0; retry < 5; retry++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(webElement));
                webElement.click();
                break;
            } catch (Exception e) {
                System.out.println("click retry " + (retry + 1));
                System.out.println("e: " + e);
            }
        }
    }

    protected void webElementMouseOver(WebElement webElement, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
        Actions action = new Actions(driver);
        action.moveToElement(webElement).build().perform();
    }

    public void setTextToWebElement(WebElement webElement, String text) {
        for(int retry = 0; retry < 5; retry++) {
            try {
                webElement.clear();
                webElement.sendKeys(text);
                break;
            } catch (Exception e) {
                System.out.println("e: " + e);
            }
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
