package de.bht.fpa.mail.s798419.fsnavigation;

import java.io.File;

public class FolderItem {

  private File item;
  
  public FolderItem(String item) {
    this.item = new File(item);
  }
  
  public FolderItem(File item) {
    this.item = item;
  }
  
  public FolderItem(Object item) {
    if (item instanceof File) {
      this.item = (File) item;
    }
  }
  
  
  public String getPath() {
    return this.item.getPath();
  }
  
  public boolean hasChildren()  {
    if (this.item.isFile()) {
      return false;
    } else {
      File[] subItems = this.item.listFiles();
      if (subItems != null) {
        return subItems.length != 0;
      } else {
        return false;
      }
    }
  }
  
  public File[] listSubElements() {
    return this.item.listFiles();
  }

  @Override
  public String toString() {
    return this.item.getName();
  }
  
}
