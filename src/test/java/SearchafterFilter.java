import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class SearchafterFilter extends BaseTest {
    SearchResultsPage Searchpage;
    int targetBedrooms;

    @BeforeClass
    public void MainSteps() {
        Searchpage = new SearchResultsPage(driver);
        Searchpage.selectRomeAsLocation();
        Searchpage.selectTwoAdultsAndOneChild();
        Searchpage.ClickSearch();
        Searchpage.ClickOnGotIt();
        Searchpage.ClickFilterButton();
        Searchpage.setBedroomsFilter(5);
        Searchpage.clickShowMoreAmenities();
        Searchpage.ClickPoolButton();
        Searchpage.ClickShowResultsButton();

    }


    @Test
    public void VerifyAtLeastOnePropertyHasFiveBedRooms() {
        Searchpage.hasAtLeastOneCardWithFiveBedRooms(targetBedrooms);
        SearchResultsPage bedroom = new SearchResultsPage(driver);
        targetBedrooms = 5;
        boolean isFiveBedRoomsFound = bedroom.hasAtLeastOneCardWithFiveBedRooms(targetBedrooms);
        Assert.assertTrue(isFiveBedRoomsFound,  " there is no 5 bedrooms available");

    }

    @Test
    public void VerifythePoolFacility() {

        Searchpage.openFirstCardDetailsInNewTab();
        Searchpage.switchToNewTab();
        Searchpage.ClickClosePopup();
        Searchpage.ClickShowFacilities();
        Searchpage.FindPoolintheParkingandfacilitiessection();
        WebElement poolElement = Searchpage.FindPoolintheParkingandfacilitiessection();
        String actualtext = poolElement.getText();
        Assert.assertTrue(
                actualtext.toLowerCase().contains("pool"),
                "Word Not Foud" + actualtext
        );
    }
}




