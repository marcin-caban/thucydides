package samples;

import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.UserStoryCode;
import net.thucydides.junit.runners.ThucydidesRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ThucydidesRunner.class)
@UserStoryCode("US01")
public class TestSampleTestForAnotherUserStoryScenario {

    @Steps
    public SampleScenarioSteps steps;
        
    @Test
    public void happy_day_scenario() {
		steps.stepThatSucceeds();
		steps.anotherStepThatSucceeds();
        steps.stepThatCallsNestedSteps();
        steps.stepThree("a");
        steps.stepFour("c");
    }    

    @Test
    public void another_successful_scenario() {
		steps.stepThatSucceeds();
		steps.anotherStepThatSucceeds();
        steps.anotherGroupOfSteps();
        steps.stepFour("c");
    }    
	
}
