package de.bht.fpa.mail.s798419.imapnavigation.actions;

import java.util.Properties;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.sun.mail.smtp.SMTPTransport;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NewMailExecutionListener implements IExecutionListener {

  @Override
  public void notHandled(String commandId, NotHandledException exception) {

  }

  @Override
  public void postExecuteFailure(String commandId, ExecutionException exception) {

  }

  @Override
  public void postExecuteSuccess(String commandId, Object returnValue) {
    if (commandId.equals("de.bht.fpa.mail.s798419.imapnavigation.actions.newMail")) {
      if (returnValue instanceof NewMailDialog && returnValue != null) {
        NewMailDialog dialog = (NewMailDialog) returnValue;
        ArrayList<String> values = (ArrayList<String>) dialog.getValues();
        if (values == null) {
          return;
        }

        Properties props = System.getProperties();
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        
        
        try {
          msg.setFrom(new InternetAddress("micha@test.de"));
          msg.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse(values.get(0), false));
          msg.setSubject(values.get(1));
          msg.setText(values.get(2));
          msg.setHeader("X-Mailer", "FPA-Mailer");
          msg.setSentDate(new Date());
          final SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
          final Message msgTemp = msg;
          Job job = new Job("Sending Email") {
            @Override
            protected IStatus run(IProgressMonitor monitor) {
              try {
                t.connect("smtp.gmail.com", "bhtfpa@googlemail.com", "B-BgxkT_anr2bubbyTLM");
                t.sendMessage(msgTemp, msgTemp.getAllRecipients());
                t.close();
              } catch (Exception e) {
                System.err.println(e.getMessage());
              }
              return Status.OK_STATUS;
            }
          };
          
          job.setUser(true);
          job.schedule();
          
          
        } catch (Exception aaa) {
          System.err.println(aaa.getMessage());
        }
        
      }
    }
  }

  @Override
  public void preExecute(String commandId, ExecutionEvent event) {

  }

}
