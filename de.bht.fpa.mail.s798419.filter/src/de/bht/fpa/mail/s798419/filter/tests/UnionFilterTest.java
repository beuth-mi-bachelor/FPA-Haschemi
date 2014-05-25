package de.bht.fpa.mail.s798419.filter.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;
import de.bht.fpa.mail.s798419.filter.Filter;
import de.bht.fpa.mail.s798419.filter.ReadFilter;
import de.bht.fpa.mail.s798419.filter.SubjectFilter;
import de.bht.fpa.mail.s798419.filter.UnionFilter;

public class UnionFilterTest {

  private static final String FILTERED_STRING = "beuth";
  private static final int NUMBER_OF_SENDER_MESSAGES = 50;

  private final List<Message> data = new RandomTestDataProvider(UnionFilterTest.NUMBER_OF_SENDER_MESSAGES)
      .getMessages();

  private Filter subject;
  private Filter read;

  @Test
  public void unionFilterIsWorking() {
    subject = new SubjectFilter(FILTERED_STRING, FilterOperator.STARTS_WITH);
    read = new ReadFilter(false);

    Set<Message> subjectMsg = subject.filter(data);
    Set<Message> readMsg = read.filter(data);

    Collection<Filter> filterCollection = new ArrayList<Filter>();
    filterCollection.add(subject);
    filterCollection.add(read);

    Set<Message> singleConcat = new HashSet<Message>();
    singleConcat.addAll(readMsg);
    singleConcat.addAll(subjectMsg);

    Filter union = new UnionFilter(filterCollection);

    assertTrue(singleConcat.size() == union.filter(data).size());

  }

}
