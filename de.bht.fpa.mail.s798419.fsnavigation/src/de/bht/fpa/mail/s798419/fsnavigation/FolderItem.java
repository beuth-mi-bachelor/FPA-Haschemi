package de.bht.fpa.mail.s798419.fsnavigation;

import java.io.File;
import java.io.FileFilter;

import org.eclipse.swt.graphics.Image;

public class FolderItem {

  private static final String ACCEPTED_FILE_EXTENSION = ".xml";

  private File item;

  private static final Image ICON_FOLDER = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
      "img/icon_folder.png").createImage();
  private static final Image ICON_FILE = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "img/icon_file.png")
      .createImage();

  private static final FileFilter DIRECTORIES_ONLY_FILTER = new FileFilter() {
    @Override
    public boolean accept(File item) {
      return item.isDirectory();
    }
  };

  public static final FileFilter XML_ONLY_FILTER = new FileFilter() {
    @Override
    public boolean accept(File item) {
      return !item.isDirectory() || item.getName().endsWith(ACCEPTED_FILE_EXTENSION);
    }
  };

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

  public boolean hasChildren() {
    if (this.item.isFile()) {
      return false;
    } else {
      File[] subItems = this.item.listFiles(DIRECTORIES_ONLY_FILTER);
      if (subItems != null) {
        return subItems.length != 0;
      } else {
        return false;
      }
    }
  }

  public File[] listSubElements() {

    return this.item.listFiles(DIRECTORIES_ONLY_FILTER);
  }

  @Override
  public String toString() {
    return this.item.getAbsolutePath();
  }

  public String displayName() {
    return this.item.getName();
  }

  public Image getImage() {
    if (this.item.isFile()) {
      return FolderItem.ICON_FILE;
    } else {
      return FolderItem.ICON_FOLDER;
    }
  }
}
