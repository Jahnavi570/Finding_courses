package main_project;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class CourseraPage {
    WebDriver driver;

    @FindBy(linkText = "For Enterprise")
    WebElement forEnterprise;

    @FindBy(linkText = "For Universities")
    WebElement forUniversities;

    @FindBy(name = "FirstName")
    WebElement firstName;

    @FindBy(name = "LastName")
    WebElement lastName;

    @FindBy(name = "Email")
    WebElement email;

    @FindBy(name = "Phone")
    WebElement phone;

    @FindBy(name = "Institution_Type__c")
    WebElement institutionType;

    @FindBy(name = "Company")
    WebElement company;

    @FindBy(name = "Title")
    WebElement jobRole;

    @FindBy(name = "Department")
    WebElement department;

    @FindBy(name = "What_the_lead_asked_for_on_the_website__c")
    WebElement needsDescription;

    @FindBy(name = "Country")
    WebElement country;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitButton;

    @FindBy(xpath = "//div[contains(@class, 'mktoErrorMsg')]")
    WebElement errorMessage;

    
    @FindBy(id = "search-autocomplete-input")
    WebElement searchBox;

    @FindBy(className = "search-button")
    WebElement searchIcon;

    @FindBy(id = "cds-react-aria-:R4darconaj6kqikta:")
    WebElement englishCheckbox;

    @FindBy(css = "button[data-testid='megamenu-explore-button']")
    WebElement exploreButton;

    @FindBy(css = "a[data-track-component='megamenu_item'][href='/browse/language-learning']")
    WebElement languageLearningButton;

    @FindBy(css = "a[data-track-component='top_domain_skill'][href='/courses?query=teaching%20english']")
    WebElement teachingEnglish;

    @FindBy(xpath = "(//button[@data-test='expand-filter-items-button'])[2]")
    WebElement secondShowMoreButton;

    @FindBy(xpath = "//*[@id='checkbox-group']//div[contains(@class,'cds-checkboxAndRadio-labelText')]")
    List<WebElement> languageElements;

    @FindBy(css = "button[aria-label='Close'][type='button']")
    WebElement closeButton;

    @FindBy(xpath = "//div[text()='Level']/ancestor::h4/ancestor::div[1]")
    WebElement levels;

    public CourseraPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
