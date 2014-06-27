package de.bht.fpa.mail.s798419.imapnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.bht.fpa.mail.s000000.common.mail.model.Account;
import de.bht.fpa.mail.s000000.common.mail.model.Folder;

public class ImapLabelProvider extends LabelProvider {

  private static final Image ICON_FOLDER = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "img/icon_folder.png").createImage();
  private static final Image ICON_ACCOUNT = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, 
      "img/icon_mail.png").createImage();
  
  @Override
  public String getText(Object element) {
    if (element instanceof Folder) {
      Folder folder = (Folder) element;
      return folder.getFullName();
    } else if (element instanceof Account) {
      Account account = (Account) element;
      return account.getName();
    }
    return element.toString();
  }

  @Override
  public Image getImage(Object element) {
    if (element instanceof Folder) {
      return ICON_FOLDER;
    } else if (element instanceof Account) {
      return ICON_ACCOUNT;
    }
    return null;
  }
}
