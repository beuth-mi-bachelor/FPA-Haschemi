package de.bht.fpa.mail.s798419.filter.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s798419.filter.Filter;
import de.bht.fpa.mail.s798419.filter.ReadFilter;
import de.bht.fpa.mail.s798419.filter.RecipientsFilter;
import de.bht.fpa.mail.s798419.filter.SenderFilter;
import de.bht.fpa.mail.s798419.filter.UnionFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.testdata.RandomTestDataProvider;

public class FilterTestCase {

  public static final int NUMBER_OF_MESSAGES = 50;

  public static void main(String[] args) {
    System.out.println("*** Read-Filter ***");
    List<Message> data = new RandomTestDataProvider(FilterTestCase.NUMBER_OF_MESSAGES).getMessages();
    Filter readfilterNotRead = new ReadFilter(false);
    System.out.println("unread: " + readfilterNotRead.filter(data).size());

    System.out.println("*** Union-Filter ***");
    Filter sender = new SenderFilter("stulle_heidi@hotmai", FilterOperator.CONTAINS);
    System.out.println("sender: " + sender.filter(data).size());
    Filter recipient = new RecipientsFilter("stulle_lola@hotmail.de", FilterOperator.IS);
    System.out.println("recipient: " + recipient.filter(data).size());
    Collection<Filter> filterList = new ArrayList<Filter>();
    filterList.add(sender);
    filterList.add(recipient);
    Filter union = new UnionFilter(filterList);
    System.out.println("union(sender + recipient): " + union.filter(data).size());

    System.out.println("*** Intersection-Filter ***");
    Filter senderName = new SenderFilter("heidi", FilterOperator.STARTS_WITH);
    System.out.println("senderName: " + senderName.filter(data).size());
    Filter read = new ReadFilter(true);
    System.out.println("read: " + read.filter(data).size());
    Collection<Filter> intersectionFilterList = new ArrayList<Filter>();
    intersectionFilterList.add(sender);
    intersectionFilterList.add(recipient);
    Filter intersection = new UnionFilter(intersectionFilterList);
    System.out.println("union(sender + recipient): " + intersection.filter(data).size());

  }
}
