package runner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features="@target//failedScenario.txt",
				glue= {"stepDefs"},
				monochrome=true,
				//dryRun=true, //By Default dryRun is False
				plugin= {"pretty"})
public class RerunRunner extends AbstractTestNGCucumberTests {
  
}
