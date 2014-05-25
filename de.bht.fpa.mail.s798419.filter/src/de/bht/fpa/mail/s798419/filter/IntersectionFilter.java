package de.bht.fpa.mail.s798419.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.FilterOperator;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class IntersectionFilter extends Filter {

  private final Collection<Filter> listOfFilters;

  public IntersectionFilter(Collection<Filter> filters) {
    this.listOfFilters = filters;
  }

  @Override
  public final Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();

    for (Filter filter : getFilters()) {
      messagesToFilter = filter.filter(messagesToFilter);
    }

    for (Message message : messagesToFilter) {
      filteredMessages.add(message);
    }

    return filteredMessages;
  }

  public final Collection<Filter> getFilters() {
    return this.listOfFilters;
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
