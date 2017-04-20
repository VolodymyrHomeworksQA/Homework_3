package myprojects.automation.assignment3;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private By catalogueLink = By.cssSelector("#subtab-AdminCatalog");
    private By categoriesLink = By.cssSelector("#subtab-AdminCategories");
    private By addCategoryButton = By.xpath("//a[@id='page-header-desc-category-new_category']");
    private By nameField = By.xpath("//input [@id='name_1']");
    private By saveButton = By.xpath("//button [@id='category_form_submit_btn']");
    private By createMessage = By.xpath("//div [@ class = 'alert alert-success']");
    private By categoryFilterField = By.xpath("//input [@ name = 'categoryFilter_name']");
    private By searchButton = By.xpath("//button [@ id = 'submitFilterButtoncategory']");
    private By newCategory = By.xpath(("//td [@ class = 'pointer'] [1]"));

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     *
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        // TODO implement logging in to Admin Panel
        driver.navigate().to("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");
        WebElement loginInput = driver.findElement(By.cssSelector("#email"));
        WebElement passwordInput = driver.findElement(By.cssSelector("#passwd"));
        WebElement LoginButton = driver.findElement(By.name("submitLogin"));
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        LoginButton.click();
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
    }

    /**
     * Adds new category in Admin Panel.
     *
     * @param categoryName
     */
    public void createCategory(String categoryName) {

        waitForContentLoad(catalogueLink);
        WebElement catalogueLink = driver.findElement(this.catalogueLink);
        WebElement categoriesLink = driver.findElement(this.categoriesLink);
        Actions actions = new Actions(driver);
        actions.moveToElement(catalogueLink).click(categoriesLink).build().perform();
        WebElement addCategoryButton = (WebElement) wait.until(elementToBeClickable(this.addCategoryButton));
        addCategoryButton.click();

        WebElement nameField = driver.findElement(this.nameField);
        WebElement saveButton = driver.findElement(this.saveButton);
        nameField.sendKeys("NewCategory1");
        scrollToTheBottomToThePage();
        saveButton.click();
        WebElement createMessage = driver.findElement(this.createMessage);
        Assert.assertTrue("Регистрация не успешна", createMessage.isDisplayed());
    }

    public void checkNewCategory(String newCategoryName) {

        WebElement categoryFilterField = driver.findElement(this.categoryFilterField);
        categoryFilterField.sendKeys(newCategoryName);
        WebElement searchButton = driver.findElement(this.searchButton);
        searchButton.click();
        try {
            driver.findElement(By.className("list-empty"));
            System.out.println(newCategoryName + " isn`t exist in Categories");
        } catch (NoSuchElementException e) {
            System.out.println(newCategoryName + " is exist in Categories");
        }
        //Alternative option
        /*
        WebElement newCategory = driver.findElement(this.newCategory);
        Assert.assertFalse("Категория не найдена", newCategory.getText().equals("\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+newCategoryName+"\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t\t")  );
         */
    }


    public void scrollToTheBottomToThePage() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        boolean scrollResult = (boolean) executor.executeScript(
                "var scrollBefore = $(window).scrollTop();"
                        + "window.scrollTo(scrollBefore, document.body.scrollHeight);"
                        + "return $(window).scrollTop() > scrollBefore;");
    }


    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));

        // TODO implement generic method to wait until page content is loaded

    }
}
