package de.bht.fpa.mail.s798419.fsnavigation;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ViewContentProvider implements ITreeContentProvider {

  /**
   * This method is called when the user expands a tree node in the View. The
   * parameter of the method is the selected item, and the method is expected to
   * return the direct children of the item.
   * 
   * @param parentElement
   *          the expanded element in the tree, for which the framework requests
   *          the children
   */
  @Override
  public Object[] getChildren(Object parentElement) {
    FolderTree newItem = new FolderTree(parentElement);
    Object[] listOfAllChildren = newItem.listAllSubElements();
    return listOfAllChildren;
  }

  /**
   * This method is called when the user expands a tree node in the View. The
   * framework asks is the given element has any children. The parameter of the
   * method is a tree item, and the method is expected to return
   * <code>true></code> if the item has children, or <code>false</code> if it
   * has no children.
   * 
   * @param element
   *          a tree item, for which the framework wants to know if it has
   *          children
   */
  @Override
  public boolean hasChildren(Object element) {
      FolderTree elements = new FolderTree(element);
      return elements.hasSubElements();
  }

  // ==========================================================================
  // In our simple tree, you don't need to change any of the following
  // methods.
  // ==========================================================================

  @Override
  public Object[] getElements(Object parent) {
    return getChildren(parent);
  }

  @Override
  public void inputChanged(Viewer v, Object oldInput, Object newInput) {
  }

  @Override
  public void dispose() {
  }

  @Override
  public Object getParent(Object element) {
    return null;
  }
}
