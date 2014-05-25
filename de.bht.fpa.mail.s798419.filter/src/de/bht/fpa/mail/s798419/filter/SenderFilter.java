package de.bht.fpa.mail.s798419.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class SenderFilter extends Filter {

  private final String input;
  private final FilterOperator type;

  public SenderFilter(String filteredString, FilterOperator filterOperator) {
    this.input = filteredString.toLowerCase();
    this.type = filterOperator;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();

    if (messagesToFilter != null) {
      for (Message message : messagesToFilter) {
        String senderEmail = message.getSender().getEmail().toLowerCase();
        String senderPersonal = message.getSender().getPersonal().toLowerCase();
        if (StringCompareHelper.matches(senderEmail, this.input, this.type)
            || StringCompareHelper.matches(senderPersonal, this.input, this.type)) {
          filteredMessages.add(message);
        }
      }
    }
    return filteredMessages;
  }

  @Override
  public String getInput() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public FilterOperator getType() {
    // TODO Auto-generated method stub
    return null;
  }
}
