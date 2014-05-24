package de.bht.fpa.mail.s798419.filter;

import java.util.Set;

import de.bht.fpa.mail.s000000.common.filter.IFilter;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public abstract class Filter implements IFilter {

  @Override
  public abstract Set<Message> filter(Iterable<Message> messagesToFilter);
}
