package de.bht.fpa.mail.s798419.filter;

import java.util.HashSet;
import java.util.Set;

import de.bht.fpa.mail.s000000.common.mail.model.Importance;
import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class ImportanceFilter extends Filter {

  private final Importance importance;

  public ImportanceFilter(final Importance importance) {
    this.importance = importance;
  }

  @Override
  public final Set<Message> filter(Iterable<Message> messagesToFilter) {
    Set<Message> filteredMessages = new HashSet<Message>();

    if (messagesToFilter != null) {
      for (Message message : messagesToFilter) {
        if (message.getImportance().equals(this.importance)) {
          filteredMessages.add(message);
        }
      }
    }
    return filteredMessages;
  }

}
