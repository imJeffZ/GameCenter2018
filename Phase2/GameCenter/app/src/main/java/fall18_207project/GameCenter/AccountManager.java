package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***
 * Manages all accounts. Emails are unique, but userNames are not.
 */
public class AccountManager implements Serializable {

    private Map<String, Account> accountMap = new HashMap<>();
    private GlobalScoreBoard globalScoreBoard;
    // TODO: Maybe put AccountManager and GlobalScoreBoard together to become a MainSystem class. We can try singleton on it.

    public AccountManager() {
        accountMap = new HashMap<>();
        globalScoreBoard = new GlobalScoreBoard();
    }

    public GlobalScoreBoard getGlobalScoreBoard() {
        return globalScoreBoard;
    }

    public ArrayList<Account> getAccountList() {
        return new ArrayList<Account>(accountMap.values());
    }

    // TODO: Change the parameters to (Account new Account)
    boolean addAccount(String email, String userName, String password) {
        if (accountMap.containsKey(email)) {
            return false;
        }
        accountMap.put(email, new Account(email, userName, password));
        return true;
    }

    Account getAccount(String email) {
        return accountMap.get(email);
    }

    boolean removeAccount(String email) {
        return accountMap.remove(email) != null;
    }

    boolean validate(String email, String password) {
        Account targetAccount = getAccount(email);
        if (targetAccount != null) {
            return targetAccount.getPassword().equals(password);
        }
        return false;
    }
}
