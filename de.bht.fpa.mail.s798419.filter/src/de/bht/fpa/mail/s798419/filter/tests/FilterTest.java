package de.bht.fpa.mail.s798419.filter.tests;

import java.util.Collection;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s798419.filter.Filter;
import de.bht.fpa.mail.s798419.filter.TextFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;

public class FilterTest {

  public static final int NUMBER_OF_MESSAGES = 50;

  private static final RandomTestDataProvider DATA = new RandomTestDataProvider(FilterTest.NUMBER_OF_MESSAGES);

  public static final Filter TEST_TEXT_FILTER = new TextFilter("ReAdY", FilterOperator.CONTAINS);

  public static void executeTextTest() {
    System.out.println("msg before: " + FilterTest.DATA.getMessages().size());
    Collection<Message> callbackText = TEST_TEXT_FILTER.filter(FilterTest.DATA.getMessages());
    System.out.println(callbackText);
    System.out.println("msg after: " + callbackText.size());

  }

  public static void main(String[] args) {
    FilterTest.executeTextTest();
  }

}
