package com.syntex_error.testing.chat.finale;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.sample.videochat.java.BuildConfig;
import com.quickblox.sample.videochat.java.R;
import com.syntex_error.testing.chat.finale.util.QBResRequestExecutor;

import io.fabric.sdk.android.Fabric;

public class App extends Application {
    //App credentials
    private static final String APPLICATION_ID = "81229";
    private static final String AUTH_KEY = "9JgYE7ZndNUKWrV";
    private static final String AUTH_SECRET = "H-dcQ9ctXP9PXuW";
    private static final String ACCOUNT_KEY = "beSyAvnyeyxaswSMJyfY";

    public static final String USER_DEFAULT_PASSWORD = "quickblox";

    private static App instance;
    private QBResRequestExecutor qbResRequestExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initFabric();
        checkAppCredentials();
        initCredentials();
    }

    private void initFabric() {
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }

    private void checkAppCredentials() {
        if (APPLICATION_ID.isEmpty() || AUTH_KEY.isEmpty() || AUTH_SECRET.isEmpty() || ACCOUNT_KEY.isEmpty()) {
            throw new AssertionError(getString(R.string.error_credentials_empty));
        }
    }

    private void initCredentials() {
        QBSettings.getInstance().init(getApplicationContext(), APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

        // Uncomment and put your Api and Chat servers endpoints if you want to point the sample
        // against your own server.
        //
        // QBSettings.getInstance().setEndpoints("https://your_api_endpoint.com", "your_chat_endpoint", ServiceZone.PRODUCTION);
        // QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);
    }

    public synchronized QBResRequestExecutor getQbResRequestExecutor() {
        return qbResRequestExecutor == null
                ? qbResRequestExecutor = new QBResRequestExecutor()
                : qbResRequestExecutor;
    }

    public static App getInstance() {
        return instance;
    }
}