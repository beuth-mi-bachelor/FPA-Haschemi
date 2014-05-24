package de.bht.fpa.mail.s798419.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class SubjectFilter extends Filter {

  private final String input;
  private final FilterOperator type;

  public SubjectFilter(String filteredString, FilterOperator filterOperator) {
    this.input = filteredString.toLowerCase();
    this.type = filterOperator;
  }

  @Override
  public final Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> messages = new HashSet<Message>();

    if (messagesToFilter != null) {
      for (Message message : messagesToFilter) {
        if (StringCompareHelper.matches(message.getSubject().toLowerCase(), this.input, this.type)) {
          messages.add(message);
        }
      }
    }
    return messages;
  }
}
