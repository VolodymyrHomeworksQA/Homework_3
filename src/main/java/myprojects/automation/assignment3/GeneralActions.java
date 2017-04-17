package myprojects.automation.assignment3;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

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
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        // TODO implement logging in to Admin Panel
        driver.navigate().to( "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");
        WebElement loginInput = driver.findElement(By.cssSelector("#email"));
        WebElement passwordInput = driver.findElement(By.cssSelector("#passwd"));
        WebElement LoginButton = driver.findElement(By.name("submitLogin"));
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        LoginButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));

    }

    /**
     * Adds new category in Admin Panel.
     * @param categoryName
     */
    public void createCategory(String categoryName) {
        //driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(catalogueLink));

        WebElement catalogueLink = driver.findElement(this.catalogueLink);
        WebElement categoriesLink = driver.findElement(this.categoriesLink);

        Actions actions = new Actions(driver);

        actions.moveToElement(catalogueLink).moveToElement(categoriesLink).click().build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addCategoryButton));

        WebElement addCategoryButton = driver.findElement(this.addCategoryButton);
        addCategoryButton.click();

        WebElement nameField = driver.findElement(this.nameField);
        WebElement saveButton = driver.findElement(this.saveButton);

        nameField.sendKeys("NewCategory1");
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        boolean scrollResult = (boolean) executor.executeScript(
                "var scrollBefore = $(window).scrollTop();"
                        + "window.scrollTo(scrollBefore, document.body.scrollHeight);"
                        + "return $(window).scrollTop() > scrollBefore;");
        saveButton.click();
        WebElement createMessage = driver.findElement(this.createMessage);
        Assert.assertTrue("Регистрация не успешна", createMessage.isDisplayed());
    }

    public void checkNewCategory(String newCategoryName) {

        WebElement categoryFilterField = driver.findElement(this.categoryFilterField);
        categoryFilterField.sendKeys(newCategoryName);
        WebElement searchButton = driver.findElement(this.searchButton);
        searchButton.click();
        WebElement newCategory = driver.findElement(this.newCategory);
        Assert.assertFalse("Категория не найдена", newCategory.getText().equals("\n" +
                "\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+newCategoryName+"\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t\t")  );
    }

/*
    public void createCategoryJS() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(catalogueLink));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        WebElement categoriesLink = driver.findElement(this.categoriesLink);
        executor.executeScript("arguments[0].click()", categoriesLink);
        boolean scrollResult =(boolean) executor.executeScript("var scrollBefore = $(window).scrollTop();"
                                                                + "$window.scrollTo (scrollBefore, document.body.scrollHeight);"
                                                                + "return $(window).scrollTop > scrollBefore;");
}
*/

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        // TODO implement generic method to wait until page content is loaded
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        // wait.until(...);
        // ...
    }
}
