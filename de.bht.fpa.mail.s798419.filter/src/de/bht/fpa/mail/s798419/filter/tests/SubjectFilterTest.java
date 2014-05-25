package de.bht.fpa.mail.s798419.filter.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s798419.filter.Filter;
import de.bht.fpa.mail.s798419.filter.SubjectFilter;

public class SubjectFilterTest {

  private static final String FILTERED_STRING = "beuth";
  private static final int NUMBER_OF_SENDER_MESSAGES = 50;

  private final List<Message> data = new RandomTestDataProvider(SubjectFilterTest.NUMBER_OF_SENDER_MESSAGES)
      .getMessages();

  private Filter subjectFilter1;
  private Filter subjectFilter2;

  @Test
  public void subjectStartsWithAndContainsString() {
    subjectFilter1 = new SubjectFilter(FILTERED_STRING, FilterOperator.STARTS_WITH);
    subjectFilter2 = new SubjectFilter(FILTERED_STRING, FilterOperator.ENDS_WITH);

    Set<Message> filteredMessages1 = subjectFilter1.filter(data);
    Set<Message> filteredMessages2 = subjectFilter2.filter(data);
    assertFalse(filteredMessages1.size() == filteredMessages2.size());

  }

  @Test
  public void numberOfMessagesIsNotEqualToFilteredMessages() {
    subjectFilter1 = new SubjectFilter(FILTERED_STRING, FilterOperator.STARTS_WITH);
    Set<Message> filteredMessages = subjectFilter1.filter(data);
    assertFalse(NUMBER_OF_SENDER_MESSAGES == filteredMessages.size());
  }

  @Test
  public void doesNotStartAndEndsWithSameString() {
    subjectFilter1 = new SubjectFilter(FILTERED_STRING, FilterOperator.STARTS_WITH);
    subjectFilter2 = new SubjectFilter(FILTERED_STRING, FilterOperator.CONTAINS);

    Set<Message> filteredMessages1 = subjectFilter1.filter(data);
    Set<Message> filteredMessages2 = subjectFilter2.filter(data);

    assertTrue(filteredMessages1.size() == filteredMessages2.size());

    for (int i = 0; i < filteredMessages1.size(); i++) {
      assertTrue(filteredMessages2.containsAll(filteredMessages1));
    }
  }

}
