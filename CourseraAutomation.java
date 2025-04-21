package main_project;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CourseraAutomation extends BaseTest {
    private Workbook workbook;
    private Sheet sheet;
    private int rowCount;
    private CourseraPage courseraPage;
    private ExcelReader excelReader;

    @BeforeClass
    public void initialize() {
        workbook = new XSSFWorkbook();
        rowCount = 0;
        courseraPage = new CourseraPage(driver);
        PageFactory.initElements(driver, courseraPage);
        excelReader = new ExcelReader();
    }

    @Test(priority = 1)
    public void searchWebDevelopmentCourses() {
        driver.get("https://www.coursera.org");
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(courseraPage.searchBox));
        searchBox.sendKeys("web development for beginners");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", courseraPage.searchIcon);
        js.executeScript("arguments[0].click();", courseraPage.englishCheckbox);
        List<WebElement> courses = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id='searchResults']//li")));
        for (int i = 0; i < 2; i++) {
            WebElement course = courses.get(i);
            String courseName = course.findElement(By.cssSelector(".cds-CommonCard-title.css-6ecy9b")).getText();
            String courseRating = course.findElement(By.cssSelector("span.css-6ecy9b")).getText();
            String courseHours = course.findElement(By.cssSelector("div.cds-CommonCard-metadata > p.css-vac8rf")).getText();
            System.out.println(courseName);
            System.out.println(courseRating);
            System.out.println(courseHours);
        }
    }
    
    @Test(priority = 2)
    public void extractLanguageLearningLevels() {
        driver.get("https://www.coursera.org");
        WebElement exploreButton = wait.until(ExpectedConditions.elementToBeClickable(courseraPage.exploreButton));
        exploreButton.click();
        WebElement languageLearningButton = wait.until(ExpectedConditions.elementToBeClickable(courseraPage.languageLearningButton));
        languageLearningButton.click();
        WebElement teachingEnglish = wait.until(ExpectedConditions.elementToBeClickable(courseraPage.teachingEnglish));
        teachingEnglish.click();
        WebElement secondShowMoreButton = wait.until(ExpectedConditions.elementToBeClickable(courseraPage.secondShowMoreButton));
        secondShowMoreButton.click();
        List<WebElement> languageElements = courseraPage.languageElements;
        for (WebElement element : languageElements) {
            System.out.println(element.getText());
        }
        courseraPage.closeButton.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", courseraPage.levels);
        for (int i = 1; i < 5; i++) {
            String text = driver.findElement(By.xpath("//div[text()='Level']/ancestor::h4/following::div/div[" + i + "]/label/div/span/span")).getText();
            System.out.println(text);
        }
    }

    @Test(priority = 3)
    public void validateEnterpriseForm() throws IOException {
        String filePath = System.getProperty("user.dir") + "/inputData.xlsx";
        Map<String, String> data = excelReader.readExcelData(filePath);
        driver.get("https://www.coursera.org");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", courseraPage.forEnterprise);
        js.executeScript("arguments[0].click();", courseraPage.forUniversities);
        WebElement form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mktoForm_1512")));
        courseraPage.firstName.sendKeys(data.get("FirstName"));
        courseraPage.lastName.sendKeys(data.get("LastName"));
        courseraPage.email.sendKeys(data.get("Email"));
        courseraPage.phone.sendKeys(data.get("Phone"));
        Select institutionType = new Select(courseraPage.institutionType);
        institutionType.selectByVisibleText(data.get("InstitutionType"));
        courseraPage.company.sendKeys(data.get("Company"));
        Select jobRole = new Select(courseraPage.jobRole);
        jobRole.selectByVisibleText(data.get("JobRole"));
        Select dept = new Select(courseraPage.department);
        dept.selectByVisibleText(data.get("Department"));
        Select needsDescription = new Select(courseraPage.needsDescription);
        needsDescription.selectByVisibleText(data.get("NeedsDescription"));
        Select country = new Select(courseraPage.country);
        country.selectByVisibleText(data.get("Country"));
        courseraPage.submitButton.click();
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'mktoErrorMsg')]")));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("screen.png");
        FileUtils.copyFile(screenshot, destFile);
        System.out.println("Error screenshot captured");
        Assert.assertFalse(errorMessage.getText().contains("Please enter a valid email address"), "Error message validation failed.");
    }
}
