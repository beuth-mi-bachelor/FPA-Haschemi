package de.bht.fpa.mail.s798419.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class UnionFilter extends Filter {

  private final Collection<Filter> listOfFilters;

  public UnionFilter(final ArrayList<Filter> filters) {
    this.listOfFilters = filters;
  }

  @Override
  public final Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();

    for (Filter filter : this.getFilters()) {
      Set<Message> currentFilteredMessages = filter.filter(messagesToFilter);

      for (Message message : currentFilteredMessages) {
        filteredMessages.add(message);
      }

    }

    return filteredMessages;
  }

  public final Collection<Filter> getFilters() {
    return this.listOfFilters;
  }

  public final void addFilter(Filter newfilter) {
    this.listOfFilters.add(newfilter);
  }
}
