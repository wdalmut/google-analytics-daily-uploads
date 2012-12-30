package it.corley.analytics.daily;

import java.util.Properties;

import com.google.api.services.analytics.Analytics;

/**
 * @author walter.dalmut@gmail.com (Walter Dal Mut)
 * 
 */
public class MyDelete extends AuthenticationAbstract {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			properties.load(MyUpload.class.getResourceAsStream("/analytics-info.properties"));
			
			Analytics analytics = initializeAnalytics();

			analytics
					.management()
					.dailyUploads()
					.delete(properties.getProperty("accountId"), properties.getProperty("webPropertyId"),
					properties.getProperty("customDataSourceId"), "2012-12-29", "cost")
					.execute();

		} catch (Exception exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
}
