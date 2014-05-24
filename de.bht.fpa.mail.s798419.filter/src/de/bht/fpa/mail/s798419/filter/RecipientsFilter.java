package de.bht.fpa.mail.s798419.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;
import de.bht.fpa.mail.s000000.common.mail.model.Recipient;

public class RecipientsFilter extends Filter {

  private final String input;
  private final FilterOperator type;

  public RecipientsFilter(String filteredString, FilterOperator filterOperator) {
    this.input = filteredString.toLowerCase();
    this.type = filterOperator;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();

    if (messagesToFilter != null) {
      for (Message message : messagesToFilter) {
        for (Recipient recipient : message.getRecipients()) {
          String recipientEmail = recipient.getEmail().toLowerCase();
          String recipientPersonal = recipient.getPersonal().toLowerCase();

          if (StringCompareHelper.matches(recipientEmail, this.input, this.type)
              || StringCompareHelper.matches(recipientPersonal, this.input, this.type)) {
            filteredMessages.add(message);
          }
        }
      }
    }
    return filteredMessages;
  }
}
