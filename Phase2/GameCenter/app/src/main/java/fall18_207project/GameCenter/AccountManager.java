package fall18_207project.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountManager implements Serializable {

    static Map<String, Account> accountMap = new HashMap<>();

    public AccountManager() {
        accountMap = new HashMap<>();
    }

    private ArrayList<Account> getaccountList() {
        return new ArrayList<Account>(AccountManager.accountMap.values());
    }

    boolean addAccount(String userName, String password) {
        if (AccountManager.accountMap.containsKey(userName)) {
            return false;
        }
        AccountManager.accountMap.put(userName, new Account(userName, password));
        return true;
    }

    Account getAccount(String userName) {
        return AccountManager.accountMap.get(userName);
    }

    boolean removeAccount(String userName) {
        return AccountManager.accountMap.remove(userName) != null;
    }
}
