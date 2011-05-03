package net.thucydides.core.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.thucydides.core.annotations.At;
import net.thucydides.core.junit.rules.SaveWebdriverSystemPropertiesRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.ElementNotDisplayedException;
import org.openqa.selenium.support.ui.Select;

public class WhenManagingAPageObject {

    @Mock
    WebDriver driver;

    @Mock
    Select mockSelect;


    @Mock
    WebElement mockButton;
    
    @Rule
    public MethodRule saveSystemProperties = new SaveWebdriverSystemPropertiesRule();

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    class BasicPageObject extends PageObject {
        
        protected WebElement button;

        public BasicPageObject(WebDriver driver) {
            super(driver);
        }

        protected WebElement getButton() {
            return mockButton;
        }
        
        @Override
        protected Select findSelectFor(WebElement dropdownList) {
            return mockSelect;
        }
    }

    @Test
    public void the_page_gets_the_title_from_the_web_page() {

        when(driver.getTitle()).thenReturn("Google Search Page");
        BasicPageObject page = new BasicPageObject(driver);

        assertThat(page.getTitle(), is("Google Search Page"));
    }

    @Test
    public void page_will_wait_for_rendered_element_if_it_is_already_present() {

        RenderedWebElement renderedElement = mock(RenderedWebElement.class);
        List<WebElement> renderedElements = new ArrayList<WebElement>();
        renderedElements.add(renderedElement);

        when(driver.findElement(any(By.class))).thenReturn(renderedElement);
        when(driver.findElements(any(By.class))).thenReturn(renderedElements);

        when(renderedElement.isDisplayed()).thenReturn(true);

        BasicPageObject page = new BasicPageObject(driver);
        page.waitForRenderedElements(By.id("whatever"));
    }

    @Test
    public void page_will_wait_for_rendered_element_to_disappear() {

        List<WebElement> emptyList = Arrays.asList();
        when(driver.findElements(any(By.class))).thenReturn(emptyList);

        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(100);
        page.waitForRenderedElementsToDisappear(By.id("whatever"));
    }

    @Test
    public void page_will_wait_for_rendered_element_if_it_is_not_already_present() {

        RenderedWebElement renderedElement = mock(RenderedWebElement.class);
        List<WebElement> renderedElements = new ArrayList<WebElement>();
        renderedElements.add(renderedElement);

        when(driver.findElement(any(By.class))).thenReturn(renderedElement);
        when(driver.findElements(any(By.class))).thenReturn(renderedElements);
        when(renderedElement.isDisplayed()).thenReturn(false).thenReturn(false).thenReturn(true);

        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(200);
        page.waitForRenderedElements(By.id("whatever"));
    }

    @Test
    public void page_will_wait_for_text_to_appear_if_requested() {

        BasicPageObject page = new BasicPageObject(driver);
        WebElement textBlock = mock(WebElement.class);

        List<WebElement> emptyList = Arrays.asList();
        List<WebElement> listWithElements = Arrays.asList(textBlock);

        when(driver.findElements(any(By.class))).thenReturn(emptyList).thenReturn(listWithElements);

        page.waitForTextToAppear("hi there");
    }

    @Test
    public void page_will_wait_for_text_to_appear_in_element_if_requested() {

        BasicPageObject page = new BasicPageObject(driver);
        WebElement textBlock = mock(WebElement.class);
        WebElement searchedBlock = mock(WebElement.class);

        List<WebElement> emptyList = Arrays.asList();
        List<WebElement> listWithElements = Arrays.asList(textBlock);

        when(searchedBlock.findElements(any(By.class))).thenReturn(emptyList).thenReturn(listWithElements);

        page.waitForTextToAppear(searchedBlock,"hi there");
    }

    @Test
    public void page_will_wait_for_text_to_appear_in_an_element_if_requested() {

        BasicPageObject page = new BasicPageObject(driver);
        WebElement textBlock = mock(WebElement.class);
        WebElement searchedBlock = mock(WebElement.class);

        List<WebElement> emptyList = Arrays.asList();
        List<WebElement> listWithElements = Arrays.asList(textBlock);

        when(searchedBlock.findElements(any(By.class))).thenReturn(emptyList).thenReturn(listWithElements);

        page.waitForAnyTextToAppear(searchedBlock, "hi there");
    }

    @Test(expected=ElementNotDisplayedException.class)
    public void page_will_fail_if_single_text_fails_to_appear_in_an_element_if_requested() {

        BasicPageObject page = new BasicPageObject(driver);
        WebElement searchedBlock = mock(WebElement.class);

        List<WebElement> emptyList = Arrays.asList();

        when(searchedBlock.findElements(any(By.class))).thenReturn(emptyList);
        page.setWaitForTimeout(200);
        page.waitForAnyTextToAppear(searchedBlock, "hi there");
    }

    @Test(expected=ElementNotDisplayedException.class)
    public void page_will_fail_if_text_fails_to_appear_in_an_element_if_requested() {

        BasicPageObject page = new BasicPageObject(driver);
        WebElement searchedBlock = mock(WebElement.class);

        List<WebElement> emptyList = Arrays.asList();

        when(searchedBlock.findElements(any(By.class))).thenReturn(emptyList);

        page.setWaitForTimeout(200);
        page.waitForAnyTextToAppear(searchedBlock, "hi there");
    }

    @Test
    public void page_will_wait_for_text_to_disappear_if_requested() {

        BasicPageObject page = new BasicPageObject(driver);
        WebElement textBlock = mock(WebElement.class);

        List<WebElement> emptyList = Arrays.asList();
        List<WebElement> listWithElements = Arrays.asList(textBlock);

        when(driver.findElements(any(By.class))).thenReturn(listWithElements).thenReturn(emptyList);

        page.waitForTextToDisappear("hi there");
    }

    @Test(expected=NoSuchElementException.class)
    public void should_contain_text_should_throw_an_assertion_if_text_is_not_visible() {
        BasicPageObject page = new BasicPageObject(driver);
        List<WebElement> emptyList = Arrays.asList();
        when(driver.findElements(any(By.class))).thenReturn(emptyList);

        page.shouldContainText("hi there");
    }

    @Test
    public void should_contain_text_should_do_nothing_if_text_is_present() {
        WebElement textBlock = mock(WebElement.class);
        BasicPageObject page = new BasicPageObject(driver);
        List<WebElement> emptyList = Arrays.asList(textBlock);
        when(driver.findElements(any(By.class))).thenReturn(emptyList);

        page.shouldContainText("hi there");
    }

    @Test
    public void entering_a_value_in_a_field_will_clear_it_first() {
        WebElement field = mock(WebElement.class);
        BasicPageObject page = new BasicPageObject(driver);

        page.typeInto(field, "some value");

        verify(field).clear();
        verify(field).sendKeys("some value");
    }

    @Test
    public void picking_a_value_in_a_dropdown_picks_by_visible_text() {
        WebElement field = mock(WebElement.class);
        BasicPageObject page = new BasicPageObject(driver);

        page.selectFromDropdown(field, "Visible label");

        verify(mockSelect).selectByVisibleText("Visible label");
    }

    @Test(expected=NoSuchElementException.class)
    public void page_will_throw_exception_if_waiting_for_rendered_element_does_not_exist() {

        when(driver.findElement(any(By.class))).thenThrow(new NoSuchElementException("No such element"));

        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(200);
        page.waitForRenderedElements(By.id("whatever"));
    }


    @Test(expected=ElementNotDisplayedException.class)
    public void page_will_throw_exception_if_waiting_for_rendered_element_is_not_visible() {

        RenderedWebElement renderedElement = mock(RenderedWebElement.class);
        when(driver.findElement(any(By.class))).thenReturn(renderedElement);
        when(renderedElement.isDisplayed()).thenReturn(false);

        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(200);
        page.waitForRenderedElements(By.id("whatever"));
    }


    @Test
    public void page_will_succeed_for_any_of_several_rendered_elements() {

        RenderedWebElement renderedElement = mock(RenderedWebElement.class);
        elementIsRendered(renderedElement, By.id("element1"));
        noElementIsRendered(By.id("element2"));

        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(200);
        page.waitForAnyRenderedElementOf(By.id("element1"), By.id("element2"));
    }

    @Test(expected=ElementNotDisplayedException.class)
    public void page_will_fail_for_any_of_several_rendered_elements_if_element_is_displayed_but_not_rendered() {

        RenderedWebElement renderedElement = mock(RenderedWebElement.class);
        elementIsDisplayedButNotRendered(renderedElement, By.id("element1"));
        noElementIsRendered(By.id("element2"));

        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(200);
        page.waitForAnyRenderedElementOf(By.id("element1"), By.id("element2"));
    }



    @Test
    public void page_will_wait_for_any_of_several_rendered_elements() {

        RenderedWebElement renderedElement = mock(RenderedWebElement.class);
        elementIsRenderedWithDelay(renderedElement, By.id("element1"));
        noElementIsRendered(By.id("element2"));

        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(200);
        page.waitForAnyRenderedElementOf(By.id("element1"), By.id("element2"));
    }


    @Test(expected = ElementNotDisplayedException.class)
    public void page_will_fail_if_none_of_the_several_rendered_elements_are_present() {

        noElementIsRendered(By.id("element1"));
        noElementIsRendered(By.id("element2"));


        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(200);
        page.waitForAnyRenderedElementOf(By.id("element1"), By.id("element2"));
    }


    @Test
    public void page_can_wait_for_an_element_to_disappear() {

        RenderedWebElement renderedElement = mock(RenderedWebElement.class);
        elementDisappearsAfterADelay(renderedElement, By.id("element1"));
        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(200);
        page.waitForRenderedElementsToDisappear(By.id("element1"));
    }

    @Test
    public void page_can_wait_for_an_element_to_disappear_if_element_is_not_initially_displayed() {

        noElementIsRendered(By.id("element1"));
        BasicPageObject page = new BasicPageObject(driver);
        page.setWaitForTimeout(200);
        page.waitForRenderedElementsToDisappear(By.id("element1"));
    }


    private void noElementIsRendered(By criteria) {
        List<WebElement> emptyList = Arrays.asList();
        when(driver.findElement(criteria)).thenThrow(new NoSuchElementException("No such element"));
        when(driver.findElements(criteria)).thenReturn(emptyList);
    }

    private void elementIsRendered(RenderedWebElement renderedElement, By criteria) {
        when(renderedElement.isDisplayed()).thenReturn(true);
        List<WebElement> listWithRenderedElement = Arrays.asList((WebElement) renderedElement);
        when(driver.findElement(criteria)).thenReturn(renderedElement);
        when(driver.findElements(criteria)).thenReturn(listWithRenderedElement);
    }

    private void elementIsDisplayedButNotRendered(RenderedWebElement renderedElement, By criteria) {
        when(renderedElement.isDisplayed()).thenReturn(false);
        List<WebElement> listWithRenderedElement = Arrays.asList((WebElement) renderedElement);
        when(driver.findElement(criteria)).thenReturn(renderedElement);
        when(driver.findElements(criteria)).thenReturn(listWithRenderedElement);
    }

    private void elementIsRenderedWithDelay(RenderedWebElement renderedElement, By criteria) {
        List<WebElement> emptyList = Arrays.asList();

        when(renderedElement.isDisplayed()).thenReturn(false).thenReturn(true);
        List<WebElement> listWithRenderedElement = Arrays.asList((WebElement) renderedElement);
        when(driver.findElement(criteria)).thenThrow(new NoSuchElementException("No such element"))
                                          .thenReturn(renderedElement);
        when(driver.findElements(criteria)).thenReturn(emptyList)
                                           .thenReturn(listWithRenderedElement);
    }


    private void elementDisappearsAfterADelay(RenderedWebElement renderedElement, By criteria) {
        List<WebElement> emptyList = Arrays.asList();

        when(renderedElement.isDisplayed()).thenReturn(true).thenReturn(false);
        List<WebElement> listWithRenderedElement = Arrays.asList((WebElement) renderedElement);
        when(driver.findElement(criteria)).thenReturn(renderedElement)
                                           .thenThrow(new NoSuchElementException("No such element"));
        when(driver.findElements(criteria)).thenReturn(listWithRenderedElement)
                                           .thenReturn(emptyList);
    }


    @Test
    public void page_object_should_know_when_a_field_is_visible() {
        BasicPageObject page = new BasicPageObject(driver);
        RenderedWebElement field = mock(RenderedWebElement.class);

        page.userCanSee(field);

        verify(field).isDisplayed();

    }

    @Test(expected=AssertionError.class)
    public void should_be_visible_should_throw_an_assertion_if_element_is_not_visible() {
        BasicPageObject page = new BasicPageObject(driver);
        RenderedWebElement field = mock(RenderedWebElement.class);
        when(field.isDisplayed()).thenReturn(false);

        page.shouldBeVisible(field);
    }

    @Test
    public void should_be_visible_should_do_nothing_if_element_is_visible() {
        BasicPageObject page = new BasicPageObject(driver);
        RenderedWebElement field = mock(RenderedWebElement.class);
        when(field.isDisplayed()).thenReturn(true);

        page.shouldBeVisible(field);
    }

    @Test
    public void when_clicking_on_something_should_retry_if_it_fails_once() {
        BasicPageObject page = new BasicPageObject(driver);

        doThrow(new WebDriverException()).doNothing().when(mockButton).click();

        page.clickOn(page.getButton());

        verify(mockButton,times(2)).click();
    }

    @Test(expected = WebDriverException.class)
    public void when_clicking_on_something_should_throw_exception_if_it_fails_twice() {
        BasicPageObject page = new BasicPageObject(driver);

        doThrow(new WebDriverException()).when(mockButton).click();

        page.clickOn(page.getButton());
    }

    @Test
    public void the_page_should_initially_open_at_the_systemwide_default_url() {

        System.setProperty("webdriver.base.url","http://www.google.com");

        BasicPageObject page = new BasicPageObject(driver);

        Pages pages = new Pages(driver);
        pages.start();

        verify(driver).get("http://www.google.com");
        System.setProperty("webdriver.base.url","");
    }


    @Test
    public void the_start_url_for_a_page_can_be_overridden_by_the_system_default_url() {
        BasicPageObject page = new BasicPageObject(driver);
        PageConfiguration.getCurrentConfiguration().setDefaultBaseUrl("http://www.google.com");

        Pages pages = new Pages(driver);
        pages.setDefaultBaseUrl("http://www.google.co.nz");
        pages.start();

        verify(driver).get("http://www.google.com");
    }

}
