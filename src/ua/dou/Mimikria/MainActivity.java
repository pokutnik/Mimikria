package ua.dou.Mimikria;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.GoogleAuthUtil;

import java.net.Authenticator;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    private EmailFinder emailFinder;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        emailFinder = new EmailFinder(this);
        TextView serviceInfo = (TextView) findViewById(R.id.service_info);
        serviceInfo.setText(emailFinder.getEmail());

    }
}
