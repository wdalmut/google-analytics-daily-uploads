package it.corley.analytics.daily;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.Analytics.Management.DailyUploads.Upload;
import com.google.api.services.analytics.model.DailyUploadAppend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MyUpload extends AuthenticationAbstract {

	public static void main(String[] args) {
		try {
			Properties properties = new Properties();
			properties.load(MyUpload.class.getResourceAsStream("/analytics-info.properties"));
			
			Analytics analytics = initializeAnalytics();

			File file = new File("cpc.csv");
			InputStreamContent mediaContent = new InputStreamContent(
					"application/octet-stream", new FileInputStream(file));
			mediaContent.setLength(file.length());

			Upload upload = analytics
					.management()
					.dailyUploads()
					.upload(properties.getProperty("accountId"), properties.getProperty("webPropertyId"),
							properties.getProperty("customDataSourceId"), "2012-12-29", 1, "cost",
							mediaContent);

			upload.setReset(true);
			DailyUploadAppend append = upload.execute();

			System.out.println("Account ID:" + append.getAccountId());
			System.out.println("Web Property ID:" + append.getWebPropertyId());
			System.out.println("Custom Data Source ID:"
					+ append.getCustomDataSourceId());
			System.out.println("Date:" + append.getDate());
			System.out.println("Append Number:" + append.getAppendNumber());
			System.out
					.println("Next Append Link:" + append.getNextAppendLink());

		} catch (GoogleJsonResponseException e) {
			e.printStackTrace();
			System.err.println("There was a service error: "
					+ e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		} catch (Exception exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
}
