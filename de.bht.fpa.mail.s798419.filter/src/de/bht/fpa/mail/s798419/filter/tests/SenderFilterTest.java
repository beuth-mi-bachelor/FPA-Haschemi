package de.bht.fpa.mail.s798419.filter.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s798419.filter.SenderFilter;

public class SenderFilterTest {

  private static final String FILTERED_STRING = "heidi";
  private static final int NUMBER_OF_SENDER_MESSAGES = 50;

  private final List<Message> data = new RandomTestDataProvider(SenderFilterTest.NUMBER_OF_SENDER_MESSAGES)
      .getMessages();

  private SenderFilter senderFilter;

  @Test
  public void senderStartsWithAndContainsString() {
    Set<Message> filteredMessages = senderFilter.filter(data);

    for (Message message : filteredMessages) {
      assertTrue(StringCompareHelper.matches(message.getSender().getPersonal(), FILTERED_STRING,
          FilterOperator.STARTS_WITH)
          && StringCompareHelper.matches(message.getSender().getPersonal(), FILTERED_STRING, FilterOperator.CONTAINS));
    }
  }

  @Test
  public void numberOfMessagesIsNotEqualToFilteredMessages() {
    senderFilter = new SenderFilter(FILTERED_STRING, FilterOperator.STARTS_WITH);
    Set<Message> filteredMessages = senderFilter.filter(data);
    assertFalse(NUMBER_OF_SENDER_MESSAGES == filteredMessages.size());
  }

  @Test
  public void doesNotStartAndEndsWithSameString() {
    Set<Message> filteredMessages = senderFilter.filter(data);

    for (Message message : filteredMessages) {
      assertFalse(StringCompareHelper.matches(message.getSender().getPersonal(), FILTERED_STRING,
          FilterOperator.STARTS_WITH)
          && StringCompareHelper.matches(message.getSender().getPersonal(), FILTERED_STRING, FilterOperator.ENDS_WITH));
    }
  }

}
