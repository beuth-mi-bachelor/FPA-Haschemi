package de.bht.fpa.mail.s798419.filter.parser;

import java.util.List;

import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s798419.filter.Filter;

public class FilterParserTest {

  public static final int NUMBER_OF_GENERATED_MESSAGES = 50;
  private static final List<Message> DATA = new RandomTestDataProvider(NUMBER_OF_GENERATED_MESSAGES).getMessages();

  public static void main(String[] args) {
    String toParse = "Union(Sender('stulle', contains), Recipient('asdf', containsNot))";

    FilterParser parser = new FilterParser();

    Filter filter = parser.parseCommand(toParse);

    System.out.println(filter.filter(FilterParserTest.DATA));

  }

}
