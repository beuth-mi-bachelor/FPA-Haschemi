package de.bht.fpa.mail.s798419.fsnavigation;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class NavigationView extends ViewPart {
  public static final String ID = "de.bht.fpa.mail.s798419.fsnavigation.NavigationView";
  private TreeViewer viewer;
  private IMemento memento;

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
    this.restoreState();
  }

  private void restoreState() {
    IMemento selectionsMomento = this.memento.getChild("startPath");
    if (selectionsMomento != null) {
      this.viewer.setInput(new FolderTree(selectionsMomento.getID()));
    }
  }

  public void changeModel(Object m) {
    viewer.setInput(m);
  }

  /**
   * We will set up a model to initialize tree hierarchy.
   */
  private Object createModel() {
    return new FolderTree();
  }

  /**
   * Passing the focus request to the viewer's control.
   */
  @Override
  public void setFocus() {
    viewer.getControl().setFocus();
  }

  @Override
  public void saveState(final IMemento memento) {
    String currentFolder = this.viewer.getInput().toString();
    this.memento = memento.createChild("startPath", currentFolder);
  }

}
