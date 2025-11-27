import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class SearchResultTest extends BaseTest {
    SearchResultsPage Searchpage;
    String expectedText;


    @BeforeClass
    public void MainSteps() throws InterruptedException {
        Searchpage = new SearchResultsPage(driver);
        Searchpage.selectRomeAsLocation();
        Searchpage.selectAirbnbDates();
        Searchpage.selectTwoAdultsAndOneChild();
        Searchpage.ClickSearch();
        Searchpage.ClickOnGotIt();
    }
    @Test
    public void Assertheader(){
        Assert.assertTrue(Searchpage.getHeaderText().contains("Rome"));
    }

    @Test
    public void Getguest() {

        expectedText = "3 guests";
        String ActualResult = Searchpage.GetGuestResult();
        Assert.assertEquals(ActualResult, expectedText, " In Correct guests Count");
    }


    @Test
    public void GetHomeinRome() {

        expectedText = "Homes in Rome";
        String ActualResult = Searchpage.GetRomeLocator();
        Assert.assertEquals(ActualResult, expectedText, " The city is not rome");
    }

    @Test
    public void VerifyAtLeastOnePropertyHasThreeBeds() {

        SearchResultsPage bed = new SearchResultsPage(driver);
        int targetBeds = 3;
        boolean isThreeBedsFound = bed.hasAtLeastOneCardWithThreeBeds(targetBeds);
        Assert.assertTrue(isThreeBedsFound, " there is no 3 beds");
    }


}