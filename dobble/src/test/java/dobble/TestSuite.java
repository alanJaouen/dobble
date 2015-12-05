package dobble;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestCarte.class,
	TestChronometer.class,
	TestJoueur.class,
	TestMode.class,
	TestStats.class,
	TestSymbole.class
})
public class TestSuite {

}