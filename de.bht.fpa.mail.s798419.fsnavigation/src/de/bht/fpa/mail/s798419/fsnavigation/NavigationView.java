package de.bht.fpa.mail.s798419.fsnavigation;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.bind.JAXB;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import de.bht.fpa.mail.s000000.common.mail.model.Message;

public class NavigationView extends ViewPart {

  public static final String ID = "de.bht.fpa.mail.s798419.fsnavigation.NavigationView";
  private TreeViewer viewer;
  private IMemento memento;

  public static final String GUI_STATE = "lastState";
  public static final String START_PATH = "startPath";
  public static final String ALL_PATHS = "allPaths";

  private String history = "";

  @Override
  public void init(final IViewSite site, final IMemento memento) throws PartInitException {
    init(site);
    this.memento = memento;
  }

  /**
   * This is a callback that will allow us to create the viewer and initialize
   * it.
   */
  @Override
  public void createPartControl(Composite parent) {
    viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    viewer.setContentProvider(new ViewContentProvider());
    viewer.setLabelProvider(new ViewLabelProvider());
    viewer.setInput(createModel());
    viewer.addSelectionChangedListener(new ISelectionChangedListener() {
      @Override
      public void selectionChanged(SelectionChangedEvent event) {
        if (event.getSelection().isEmpty()) {
          return;
        }
        if (event.getSelection() instanceof IStructuredSelection) {
          IStructuredSelection selection = (IStructuredSelection) event.getSelection();
          for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
            Object item = iterator.next();
            if (item instanceof File) {
              File selectedFile = (File) item;
              if (selectedFile.isDirectory()) {
                File[] allXmlFiles = selectedFile.listFiles(FolderItem.XML_ONLY_FILTER);
                System.out.println("selected directory: " + selectedFile.getAbsolutePath());
                System.out.println("number of mails: : " + allXmlFiles.length);
                for (File file : allXmlFiles) {
                  Message message = JAXB.unmarshal(file, Message.class);
                  System.out.println(message);
                }
              } else {
                Message message = JAXB.unmarshal(selectedFile, Message.class);
                System.out.println(message);
              }
            }
          }

        }
      }
    });
    this.restoreState();
  }

  public String[] getHistory() {
    return this.history.split(";");
  }

  public void addToHistory(String item) {
    if (!Arrays.asList(this.getHistory()).contains(item)) {
      this.history += ";" + item;
    }
  }

  private void restoreState() {
    if (this.memento == null) {
      return;
    }
    IMemento selectionsMomento = this.memento.getChild(NavigationView.GUI_STATE);
    if (selectionsMomento != null) {
      this.history = selectionsMomento.getString(NavigationView.ALL_PATHS);
      String startFolder = selectionsMomento.getString(NavigationView.START_PATH);
      if (startFolder != null) {
        this.viewer.setInput(new FolderTree(startFolder));
      }
    }
  }

  public void changeModel(Object m) {
    viewer.setInput(m);
  }

  private Object createModel() {
    return new FolderTree();
  }

  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  @Override
  public void saveState(final IMemento memento) {
    String currentFolder = this.viewer.getInput().toString();
    IMemento remember = memento.createChild(NavigationView.GUI_STATE);

    remember.putString(NavigationView.ALL_PATHS, this.history);

    remember.putString(NavigationView.START_PATH, currentFolder);
  }
}
