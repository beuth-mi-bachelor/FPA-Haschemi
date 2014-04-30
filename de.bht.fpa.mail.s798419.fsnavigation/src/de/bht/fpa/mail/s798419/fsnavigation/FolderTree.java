package de.bht.fpa.mail.s798419.fsnavigation;

import org.eclipse.swt.graphics.Image;
import java.io.File;

public class FolderTree {

  public static final String HOME_DIRECTORY = System.getProperty("user.home");
  private FolderItem item;

  public FolderTree() {
    this.item = new FolderItem(FolderTree.HOME_DIRECTORY);
  }

  public FolderTree(String pathname) {
    this.item = new FolderItem(pathname);
  }

  public FolderTree(Object file) {
    if (file instanceof File) {
      this.item = new FolderItem((File) file);
    } else if (file instanceof FolderItem) {
      this.item = (FolderItem) file;
    } else if (file instanceof FolderTree) {
      this.item = ((FolderTree) file).item;
    }
  }

  public Object[] listAllSubElements() {
    return this.item.listSubElements();
  }

  public boolean hasSubElements() {
    return this.item.hasChildren();
  }

  @Override
  public String toString() {
    return this.item.toString();
  }
  
  public String displayName() {
    return this.item.displayName();
  }

  public Image getImage() {
    return this.item.getImage();
  }

}
