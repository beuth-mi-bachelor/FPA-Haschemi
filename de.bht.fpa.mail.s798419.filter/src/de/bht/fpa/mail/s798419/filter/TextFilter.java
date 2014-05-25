package de.bht.fpa.mail.s798419.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.filter.StringCompareHelper;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class TextFilter extends Filter {

  private final String input;
  private final FilterOperator type;

  public TextFilter(String filteredString, FilterOperator filterOperator) {
    this.input = filteredString.toLowerCase();
    this.type = filterOperator;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();

    if (messagesToFilter != null) {
      for (Message message : messagesToFilter) {

        if (StringCompareHelper.matches(message.getText().toLowerCase(), this.input, this.type)) {
          filteredMessages.add(message);
        }
      }
    }
    return filteredMessages;
  }

  public String getInput() {
    return this.input;
  }

  public FilterOperator getType() {
    return this.type;
  }

}
