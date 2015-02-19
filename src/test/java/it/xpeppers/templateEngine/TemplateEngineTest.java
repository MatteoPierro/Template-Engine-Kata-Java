package it.xpeppers.templateEngine;

import static org.junit.Assert.*;
import it.xpeppers.templateEngine.exception.MissingValueException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TemplateEngineTest {

	private Map<String, String> mapOfVariables;

	@Before
	public void initTest() {
		mapOfVariables = new HashMap<String, String>();
	}

	private void check(String expectedResult, String stringToEvaluate)
			throws Exception {
		assertEquals(expectedResult,
				new TemplateEngine().evaluate(stringToEvaluate, mapOfVariables));
	}

	@Test
	public void testSingleVariableExpression() throws Exception {
		mapOfVariables.put("name", "Cenk");
		check("Hello Cenk", "Hello {$name}");
	}

	@Test
	public void testMultipleVariablesExpression() throws Exception {
		mapOfVariables.put("firstName", "Cenk");
		mapOfVariables.put("lastName", "Civici");
		check("Hello Cenk Civici", "Hello {$firstName} {$lastName}");
	}

	@Test(expected = MissingValueException.class)
	public void testVariableDoesNotExist() throws Exception {
		check("", "Hello {$firstName}");
	}

	@Test
	public void testComplexCases() throws Exception {
		mapOfVariables.put("name", "Cenk");
		check("Hello ${Cenk}", "Hello ${{$name}}");
	}

}
