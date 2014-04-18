package de.bht.fpa.mail.s798419.fsnavigation;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class ViewLabelProvider extends LabelProvider {
  @Override
  public String getText(Object element) {
    FolderItem itemToBeDisplayed = new FolderItem(element);
    return super.getText(itemToBeDisplayed);
  }

  @Override
  public Image getImage(Object element) {
    return super.getImage(element);
  }
}
