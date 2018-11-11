package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***
 * Manages all accounts. Emails are unique, but userNames are not.
 */
public class AccountManager implements Serializable {

    static Map<String, Account> accountMap = new HashMap<>();

    public AccountManager() {
        accountMap = new HashMap<>();
    }

    private ArrayList<Account> getaccountList() {
        return new ArrayList<Account>(AccountManager.accountMap.values());
    }

    boolean addAccount(String email, String userName, String password) {
        if (AccountManager.accountMap.containsKey(email)) {
            return false;
        }
        AccountManager.accountMap.put(email, new Account(email, userName, password));
        return true;
    }

    Account getAccount(String email) {
        return AccountManager.accountMap.get(email);
    }

    boolean removeAccount(String email) {
        return AccountManager.accountMap.remove(email) != null;
    }

    boolean validate(String email, String password) {
        Account targetAccount = getAccount(email);
        if (targetAccount != null) {
            return targetAccount.getPassword().equals(password);
        }
        return false;
    }
}
