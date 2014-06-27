package de.bht.fpa.mail.s798419.imapnavigation;

import java.util.ArrayList;
import java.util.Collection;
import de.bht.fpa.mail.s000000.common.mail.model.Account;

public class AccountList {
  
  private Collection<Account> accounts;
  
  public AccountList() {
    this.accounts = new ArrayList<Account>();
  }
  
  public AccountList(final Collection<Account> accounts) {
    this.accounts = accounts;
  }

  public Collection<Account> getAccounts() {
    return accounts;
  }

  public void setAccounts(Collection<Account> accounts) {
    this.accounts = accounts;
  } 
  
  public void addAll(Collection<Account> accounts) {
    this.accounts.addAll(accounts);
  }
  
  public void addAll(Account... accounts) {
    for (Account account : accounts) {
      this.add(account);
    }
  }
  
  public void add(Account account) {
    this.accounts.add(account);
  } 
  
}