import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchResultsPage extends Pages {
    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }


    //Weblocators

    By Wherelocator = By.cssSelector("input[data-testid=\"structured-search-input-field-query\"]");
    By Destinationlocator = By.cssSelector("[data-testid='option-0']");
    By Whenlocator =By.xpath("//div[text()='When']");
    By Headertextlocator =By.cssSelector("span[data-testid=\"stays-page-heading\"]\n");
    By Wholocator = By.xpath("//div[text()='Add guests']");
    By increaseAdultsButton = By.cssSelector("[data-testid='stepper-adults-increase-button']");
    By increaseChildrenButton = By.cssSelector("[data-testid='stepper-children-increase-button']");
    By searchButton = By.xpath("//div[text()='Search']");
    By gotItButton = By.xpath("//button[text()='Got it']");
    By guestsCountLocatorAssertion = By.xpath("//div[contains(text(), 'guest')]");
    By resultsTitleLocator = By.xpath("//div[contains(text(), 'Homes in Rome')]");
    By filtersButton = By.cssSelector("[data-testid='category-bar-filter-button']");
    By increaseMinBedroomsButton = By.cssSelector("[data-testid='stepper-filter-item-min_bedrooms-stepper-increase-button']");
    By showMoreAmenitiesButton = By.cssSelector("[aria-label='Show more amenities']");
    By amenitiesTitle = By.cssSelector("#filter-section-heading-id-FILTER_SECTION_CONTAINER\\:MORE_FILTERS_AMENITIES_WITH_SUBCATEGORIES");
    By ShowPlacesButton = By.xpath("//a[starts-with(text(), 'Show') and contains(text(), 'places')]");
    By propertyfirstCardLink = By.xpath("//a[contains(@href, 'adults=2') and contains(@href, 'children=1')]");
    By closeButton = By.xpath("//button[@aria-label='Close']");
    private static By PropertyAllContainers = By.cssSelector("div[data-testid=\"card-container\"]");
    By FeaturesTitleLocator = By.xpath("//h3[text()='Features']");
    By PoolButtonlocator = By.xpath("//button[.//span[text()='Pool']]");
    By ShowALL60ButtonLocator = By.xpath("//button[contains(., 'Show all') and contains(., 'amenities')]");
    By WhatthisplaceoffersTitleLocator =By.xpath("//h2[text()='What this place offers']");
    By POOLFACILITIESIDlocator = By.xpath("//div[starts-with(@id, 'pdp_v3_parking_facilities_7_')]");
    By Parkingandfacilitiestitlelocator=By.xpath("//h2[text()='Parking and facilities']");




    public void selectRomeAsLocation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(Wherelocator));
        WebElement whereInput = driver.findElement(Wherelocator);
        whereInput.sendKeys("Rome, Italy");
        wait.until(ExpectedConditions.elementToBeClickable(Destinationlocator));
        WebElement romeSuggestion = driver.findElement(Destinationlocator);
        romeSuggestion.click();
    }


    public void selectAirbnbDates() throws InterruptedException {

        LocalDate checkInDate = LocalDate.now().plusDays(7);
        LocalDate checkOutDate = checkInDate.plusDays(7);

        DateTimeFormatter airbnbFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String checkInString = checkInDate.format(airbnbFormat);
        String checkOutString = checkOutDate.format(airbnbFormat);

        By checkInLocator = By.xpath("//button[@data-state--date-string='" + checkInString + "']");
        By checkOutLocator = By.xpath("//button[@data-state--date-string='" + checkOutString + "']");


        driver.findElement(Whenlocator).click();


        while (driver.findElements(checkInLocator).isEmpty()) {
            driver.findElement(By.xpath("//button[@aria-label='Next']")).click();
            Thread.sleep(300);
        }

        driver.findElement(checkInLocator).click();


        driver.findElement(checkOutLocator).click();
    }


    public void selectTwoAdultsAndOneChild() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(Wholocator)).click();
        wait.until(ExpectedConditions.elementToBeClickable(increaseAdultsButton));
        WebElement adultsIncreaseButtonElement = driver.findElement(increaseAdultsButton);
        adultsIncreaseButtonElement.click();
        adultsIncreaseButtonElement.click();
        WebElement childrenIncreaseButtonElement = driver.findElement(increaseChildrenButton);
        childrenIncreaseButtonElement.click();
    }

    public void ClickSearch() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        driver.findElement(searchButton).click();
    }


    public void ClickOnGotIt() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(gotItButton));
        driver.findElement(gotItButton).click();
    }

    public  String getHeaderText()
    {
        return driver.findElement(Headertextlocator).getText();
    }

    public String GetGuestResult() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(guestsCountLocatorAssertion));
        return driver.findElement(guestsCountLocatorAssertion).getText();
    }

    public String GetRomeLocator() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTitleLocator));
        return driver.findElement(resultsTitleLocator).getText();
    }

    //For Assert 3 beds
    public boolean hasAtLeastOneCardWithThreeBeds(int targetBeds) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(PropertyAllContainers));
            System.out.println(" Success ");

        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println(" fail ");
            return false;
        }

        List<WebElement> listings = driver.findElements(PropertyAllContainers);

        if (listings.isEmpty()) {
            System.out.println(" No Properties available");
            return false;
        }


        for (int i = 0; i < listings.size(); i++) {
            WebElement listing = listings.get(i);

            try {

                String fullTextFromCard = listing.getText().trim();
                String bedsOnlyText = fullTextFromCard.replaceAll("bedroom|bedrooms", "");

                System.out.println(" valid text " + (i + 1) + ": \n---" + fullTextFromCard + "---");
                Pattern pattern = Pattern.compile("(\\d+)\\s+(bed|beds)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(bedsOnlyText);
                int actualBeds = 0;

                if (matcher.find()) {
                    actualBeds = Integer.parseInt(matcher.group(1));


                    if (actualBeds >= targetBeds) {
                        System.out.println( actualBeds + "  NumBed " + (i + 1) + ").");
                        return true;
                    }
                }


            } catch (Exception e) {

                System.err.println(" unexpected error " + (i + 1) + ": " + e.getMessage());
                continue;
            }
        }

        System.out.println(" unexpected error " + targetBeds  );
        return false;
    }


    public void ClickFilterButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(filtersButton));
        driver.findElement(filtersButton).click();
    }


    private final By BEDROOMS_TITLE =
            By.xpath("//div[text()='Bedrooms']");

    public void setBedroomsFilter(int count) {
        WebElement bedroomsTitleElement = driver.findElement(BEDROOMS_TITLE);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bedroomsTitleElement);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(increaseMinBedroomsButton));
        WebElement increaseButton = driver.findElement(increaseMinBedroomsButton);

        for (int i = 0; i < count; i++) {
            increaseButton.click();
        }
    }
    public void clickShowMoreAmenities() {

        WebElement amenitiesTitleELement = driver.findElement(amenitiesTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", amenitiesTitleELement);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(showMoreAmenitiesButton ));
        driver.findElement(showMoreAmenitiesButton ).click();
    }
    public void ClickPoolButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(FeaturesTitleLocator));
        WebElement featurestitleElement = driver.findElement(FeaturesTitleLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", featurestitleElement);
        wait.until(ExpectedConditions.elementToBeClickable(PoolButtonlocator));
        driver.findElement(PoolButtonlocator).click();
    }

    public void ClickShowResultsButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(ShowPlacesButton));
        driver.findElement(ShowPlacesButton).click();
    }


    //For 5 Bedrooms Assertion
    public boolean hasAtLeastOneCardWithFiveBedRooms(int targetBedrooms) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(PropertyAllContainers));
            System.out.println(" Success");

        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println(" failure ");
            return false;
        }

        List<WebElement> listings = driver.findElements(PropertyAllContainers);

        if (listings.isEmpty()) {
            System.out.println(" Failure ");
            return false;
        }


        for (int i = 0; i < listings.size(); i++) {
            WebElement listing = listings.get(i);

            try {

                String fullTextFromCard = listing.getText().trim();
                String bedroomsOnlyText = fullTextFromCard.replaceAll("beds|bed", "");

                System.out.println("Actual text " + (i + 1) + ": \n---" + fullTextFromCard + "---");
                Pattern pattern = Pattern.compile("(\\d+)\\s+(room|rooms)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(bedroomsOnlyText);
                int actualBedrooms = 0;

                if (matcher.find()) {
                    actualBedrooms = Integer.parseInt(matcher.group(1));


                    if (actualBedrooms >= targetBedrooms) {
                        System.out.println(  actualBedrooms + "  Bedroom num " + (i + 1) + ").");
                        return true;
                    }
                }


            } catch (Exception e) {

                System.err.println("unexpected error " + (i + 1) + ": " + e.getMessage());
                continue;
            }
        }

        System.out.println(" 5 Bedrooms not available " + targetBedrooms );
        return false;


    }
    public void openFirstCardDetailsInNewTab() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(propertyfirstCardLink));
        WebElement cardElement = driver.findElement(propertyfirstCardLink);
        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL)
                .click(cardElement)
                .keyUp(Keys.CONTROL)
                .build()
                .perform();

        switchToNewTab();
    }

    public void switchToNewTab() {
        Set<String> windowHandles = driver.getWindowHandles();
        String newWindowHandle = (String) windowHandles.toArray()[windowHandles.size() - 1];
        driver.switchTo().window(newWindowHandle);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("rooms"));
    }
    public void ClickClosePopup() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        driver.findElement(closeButton).click();

    }
   public void ClickShowFacilities(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(WhatthisplaceoffersTitleLocator));
        WebElement ShowFacilities = driver.findElement(WhatthisplaceoffersTitleLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ShowFacilities);
        wait.until(ExpectedConditions.elementToBeClickable(ShowALL60ButtonLocator));
        driver.findElement(ShowALL60ButtonLocator).click();
    }
    public WebElement FindPoolintheParkingandfacilitiessection() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement parkingSection = wait.until(ExpectedConditions.presenceOfElementLocated(Parkingandfacilitiestitlelocator));
        js.executeScript("arguments[0].scrollIntoView(true)", parkingSection);
        wait.until(ExpectedConditions.visibilityOfElementLocated(POOLFACILITIESIDlocator));
        WebElement poolFacilitytext = driver.findElement(POOLFACILITIESIDlocator);
        System.out.println("Get pool text");
        return poolFacilitytext;

    }


    }






















