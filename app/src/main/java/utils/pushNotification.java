//package utils;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.util.Log;
//
//import com.google.android.gms.common.ConnectionResult;
//
//import static com.google.android.gms.common.GooglePlayServicesUtil.getErrorDialog;
//import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;
//import static com.google.android.gms.common.GooglePlayServicesUtil.isUserRecoverableError;
//
///**
// * Created by User on 9/5/2015.
// */
//public class PushNotification {
//
//
//    private boolean checkPlayServices() {
//        int resultCode = isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (isUserRecoverableError(resultCode)) {
//                getErrorDialog(resultCode, this,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Log.i(TAG, "This device is not supported.");
//            }
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Gets the current registration ID for application on GCM service.
//     * <p>
//     * If result is empty, the app needs to register.
//     *
//     * @return registration ID, or empty string if there is no existing
//     *         registration ID.
//     */
//    private String getRegistrationId(Context context) {
//        final SharedPreferences prefs = getGCMPreferences(context);
//        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
//        if (registrationId.isEmpty()) {
//            Log.i(TAG, "Registration not found.");
//            return "";
//        }
//        // Check if app was updated; if so, it must clear the registration ID
//        // since the existing registration ID is not guaranteed to work with
//        // the new app version.
//        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
//        int currentVersion = getAppVersion(context);
//        if (registeredVersion != currentVersion) {
//            Log.i(TAG, "App version changed.");
//            return "";
//        }
//        return registrationId;
//    }
//
//    /**
//     * @return Application's version code from the {@code PackageManager}.
//     */
//    private static int getAppVersion(Context context) {
//        try {
//            PackageInfo packageInfo = context.getPackageManager()
//                    .getPackageInfo(context.getPackageName(), 0);
//            return packageInfo.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            // should never happen
//            throw new RuntimeException("Could not get package name: " + e);
//        }
//    }
//    /**
//     * @return Application's {@code SharedPreferences}.
//     */
//    private SharedPreferences getGCMPreferences(Context context) {
//        // This sample app persists the registration ID in shared preferences, but
//        // how you store the registration ID in your app is up to you.
//        return getSharedPreferences(MainActivity.class.getSimpleName(),
//                Context.MODE_PRIVATE);
//    }
//
//}
