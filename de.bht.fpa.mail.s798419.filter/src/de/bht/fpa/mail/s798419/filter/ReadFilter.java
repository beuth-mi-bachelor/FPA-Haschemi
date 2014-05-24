package de.bht.fpa.mail.s798419.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class ReadFilter extends Filter {

  private final boolean read;

  public ReadFilter(boolean isRead) {
    this.read = isRead;
  }

  @Override
  public Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();

    if (messagesToFilter != null) {
      for (Message message : messagesToFilter) {
        if (message.isRead().equals(this.read)) {
          filteredMessages.add(message);
        }
      }
    }

    return filteredMessages;
  }

}
