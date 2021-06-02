package kroppeb.stareval.parser;

import kroppeb.stareval.exception.ParseException;
import kroppeb.stareval.token.ExpressionToken;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParserTest {
	private static ExpressionToken parse(String string) throws ParseException {
		return Parser.parse(string, IrisOptions.options);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/shouldBeAbleToBeParsed.csv", delimiter = ';')
	void checkIfValidExpressionsParse(String input) throws ParseException {
		parse(input);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/shouldNotBeAbleToBeParsed.csv", delimiter = ';')
	void checkIfInvalidExpressionsDontParse(String input) {
		try {
			parse(input);
		} catch (ParseException parseException) {
			parseException.printStackTrace();
			return;
		} catch (RuntimeException runtimeException) {
			fail("Unexpected exception has been thrown", runtimeException);
			return;
		}
		fail("No exception has been thrown");
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/fullyEquivalent.csv", delimiter = ';')
	void checkOrderOfOperationsParse(String input1, String input2) throws ParseException {
		ExpressionToken exp1 = parse(input1);
		ExpressionToken exp2 = parse(input2);
		assertEquals(exp1.simplify().toString(), exp2.simplify().toString());
	}
}
