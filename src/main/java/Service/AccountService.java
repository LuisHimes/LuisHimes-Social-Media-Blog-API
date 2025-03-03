package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
    
    public Account registerAccount(Account account){
        if(account.getUsername()!= null && account.getUsername().length() > 0){
            if(account.getPassword().length() > 3){
                List<Account> rs = accountDAO.getAllAccounts();
                for(int i = 0; i < rs.size(); i++){
                    if(rs.get(i).getUsername().equals(account.getUsername())){
                        return null;
                    }
                }
                return accountDAO.insertAccount(account);
            }
        }
        return null;
    }

    public Account login(Account account){
        List<Account> rs = accountDAO.getAllAccounts();
        for(int i = 0; i < rs.size(); i++){
            if(rs.get(i).getUsername().equals(account.getUsername()) && rs.get(i).getPassword().equals(account.getPassword())){
                return rs.get(i);
            }
        }
        return null;
    }
}
