package it.corley.analytics.daily;

import java.util.Properties;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.DailyUpload;
import com.google.api.services.analytics.model.DailyUpload.RecentChanges;
import com.google.api.services.analytics.model.DailyUploads;

/**
 * 
 * @author Walter Dal Mut
 *
 */
public class MyList extends AuthenticationAbstract {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			properties.load(MyUpload.class.getResourceAsStream("/analytics-info.properties"));

			Analytics analytics = initializeAnalytics();

			DailyUploads dailyUploads = analytics
					.management()
					.dailyUploads()
					.list(properties.getProperty("accountId"), properties.getProperty("webPropertyId"),
							properties.getProperty("customDataSourceId"), "2012-09-01",
							"2012-12-30").execute();

			for (DailyUpload dailyUpload : dailyUploads.getItems()) {
				System.out.println("Account ID: " + dailyUpload.getAccountId());
				System.out.println("Web Property ID: "
						+ dailyUpload.getWebPropertyId());
				System.out.println("Custom Data Source ID: "
						+ dailyUpload.getCustomDataSourceId());
				System.out.println("Date: " + dailyUpload.getDate());
				System.out.println("Append Count:"
						+ dailyUpload.getAppendCount());
				System.out.println("Created Time:"
						+ dailyUpload.getCreatedTime());
				System.out.println("Modified Time:"
						+ dailyUpload.getModifiedTime());

				System.out.println("Recent Changes:");
				for (RecentChanges recentChanges : dailyUpload
						.getRecentChanges()) {
					System.out.println(" Change Type: "
							+ recentChanges.getChange());
					System.out.println(" Change Time: "
							+ recentChanges.getTime());
				}
			}
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
}
