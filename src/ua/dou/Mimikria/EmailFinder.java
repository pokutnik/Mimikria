package ua.dou.Mimikria;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import java.util.regex.Pattern;

/**
 * User: David
 * Date: 30.03.13
 * Time: 15:52
 */
public class EmailFinder {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String possibleEmail;
    private Context context;

    public EmailFinder(Context context) {
        this.context = context;
    }

    public String getEmail() {
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Account[] accounts = AccountManager.get(context).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;
                break;
            }
        }

        return possibleEmail;
    }
}
