package ua.dou.Mimikria.resources;

import org.apache.http.client.CookieStore;

/**
 * User: David
 * Date: 30.03.13
 * Time: 19:26
 */
public class AppCookies {
    private static AppCookies appCookies = null;
    private static Object mutex = new Object();

    private CookieStore cookieStore;

    private AppCookies() { }

    public static AppCookies getInstance() {
        if (appCookies == null) {
            synchronized (mutex) {
                if (appCookies == null) {
                    appCookies = new AppCookies();
                }
            }
        }
        return appCookies;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
