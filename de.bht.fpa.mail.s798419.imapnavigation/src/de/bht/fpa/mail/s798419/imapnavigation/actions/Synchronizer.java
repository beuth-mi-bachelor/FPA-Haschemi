package de.bht.fpa.mail.s798419.imapnavigation.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import de.bht.fpa.mail.s000000.common.mail.model.Account;

import de.bht.fpa.mail.s000000.common.mail.imapsync.ImapHelper;
import de.bht.fpa.mail.s000000.common.mail.imapsync.SynchronizationException;
import de.bht.fpa.mail.s798419.imapnavigation.AccountList;
import de.bht.fpa.mail.s798419.imapnavigation.ImapNavigationView;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class Synchronizer extends Job implements IWorkbenchWindowActionDelegate {
  
  private IWorkbenchWindow window;
  private Account currentAccount = new Account();
  private ImapNavigationView view;
  private Job job;
  
  /**
   * The constructor.
   */
  public Synchronizer() {
    super("Job");
    
  }

  /**
   * The action has been activated. The argument of the method represents the
   * 'real' action sitting in the workbench UI.
   * 
   * @see IWorkbenchWindowActionDelegate#run
   */
  @Override
  public void run(IAction action) {
    AccountList.createAccount("BHT-FPA-Testaccount", "imap.gmail.com", 
    "bhtfpa@googlemail.com", "B-BgxkT_anr2bubbyTLM");
    
    job = new Job("Getting Account") {
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        currentAccount = ImapHelper.getAccount("BHT-FPA-Testaccount");
        return Status.OK_STATUS;
      }
    };
    
    job.setUser(true);
    job.schedule();   
    
    Job.getJobManager().addJobChangeListener(listener);
    
    /*
    try {
      ImapHelper.syncAllFoldersToAccount(account, null);
    } catch (SynchronizationException e) {
      throw new RuntimeException("Exception not handled in code", e);
    }

    */
  }

  /**
   * Selection in the workbench has been changed. We can change the state of the
   * 'real' action here if we want, but this can only happen after the delegate
   * has been created.
   * 
   * @see IWorkbenchWindowActionDelegate#selectionChanged
   */
  @Override
  public void selectionChanged(IAction action, ISelection selection) {
  }
  
  private IJobChangeListener listener = new IJobChangeListener() {

    @Override
    public void aboutToRun(IJobChangeEvent event) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void awake(IJobChangeEvent event) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void done(IJobChangeEvent event) {
      if (event.getJob().getName().equals("Getting Account") && event.getJob().getResult().isOK()) {
        syncFolders();
      }
      if (event.getJob().getName().equals("Getting Folders") && event.getJob().getResult().isOK()) {
        addAccountToList();
      }
    }

    @Override
    public void running(IJobChangeEvent event) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void scheduled(IJobChangeEvent event) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void sleeping(IJobChangeEvent event) {
      // TODO Auto-generated method stub
      
    }
    
  };

  /**
   * We can use this method to dispose of any system resources we previously
   * allocated.
   * 
   * @see IWorkbenchWindowActionDelegate#dispose
   */
  @Override
  public void dispose() {
  }
  
  public void syncFolders() {
    Display.getDefault().asyncExec(new Runnable() {
      public void run() {
        Job syncFolders = new Job("Getting Folders") {
          @Override
          protected IStatus run(IProgressMonitor monitor) {
            try {
              ImapHelper.syncAllFoldersToAccount(currentAccount, monitor);
            } catch (SynchronizationException e) {
              throw new RuntimeException("Exception not handled in code", e);
            }
            return Status.OK_STATUS;
          }
        };
        
        syncFolders.setUser(true);
        syncFolders.schedule();  
        
      }
   });
  }
  
  public void addAccountToList() {   
    final AccountList existingAccounts = (AccountList) view.getModel();
    existingAccounts.add(currentAccount);
    Display.getDefault().asyncExec(new Runnable() {
      public void run() {
        view.changeModel(existingAccounts);
      }
   });
  }

  /**
   * We will cache window object in order to be able to provide parent shell for
   * the message dialog.
   * 
   * @see IWorkbenchWindowActionDelegate#init
   */
  @Override
  public void init(IWorkbenchWindow window) {
    this.window = window;
    IWorkbenchPage page = this.window.getActivePage();
    view = (ImapNavigationView) page.findView(ImapNavigationView.ID);
  }

  @Override
  protected IStatus run(IProgressMonitor monitor) {
    // TODO Auto-generated method stub
    return null;
  }
}