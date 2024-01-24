package com.example.credit_card; /**
 * Created by ajaymahadevan on {19/12/23}.
 */
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class DeepLinkHandler {

	public static void openLink(Context context, String link) {
		if (!isDeepLink(context, link)) {
			if (isAppInstalled(context, getPackageNameFromDeepLink(link))) {
				openDeepLinkInApp(context, link);
			} else {
				openDeepLinkInBrowser(context, link);
			}
		} else {
			openLinkInBrowser(context, link);
		}
	}

	private static boolean isDeepLink(Context context, String link) {
		// Add your logic to determine if the link is a deep link
		// For example, you might check for a specific scheme or host
		// For simplicity, this example considers any link starting with "http" as a deep link.
		return link.startsWith("http");
	}

	private static String getPackageNameFromDeepLink(String link) {
		// Extract the package name from the deep link if needed
		// Modify this based on your deep link structure
		// For simplicity, this example extracts the host as the package name
		Uri uri = Uri.parse(link);
		return uri.getHost();
	}

	private static boolean isAppInstalled(Context context, String packageName) {
		try {
			context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

	private static void openDeepLinkInApp(Context context, String link) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
		context.startActivity(intent);
	}

	private static void openDeepLinkInBrowser(Context context, String link) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
		context.startActivity(intent);
	}

	private static void openLinkInBrowser(Context context, String link) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
		context.startActivity(intent);
	}
}

