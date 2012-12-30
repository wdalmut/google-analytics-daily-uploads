package it.corley.analytics.daily;

import java.io.File;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.AnalyticsScopes;

public abstract class AuthenticationAbstract {

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	/**
	 * Performs all necessary setup steps for running requests against the API.
	 * 
	 * @return An initialized Analytics service object.
	 * 
	 * @throws Exception
	 *             if an issue occurs with OAuth2Native authorize.
	 */
	protected static Analytics initializeAnalytics() throws Exception {
		// Authorization.
		Credential credential = authorize();

		// Set up and return Google Analytics API client.
		return new Analytics.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(
						"Google-Analytics-Hello-Analytics-API-Sample").build();
	}

	/** Authorizes the installed application to access user's protected data. */
	private static Credential authorize() throws Exception {
		// load client secrets
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY,
				MyDelete.class.getResourceAsStream("/client_secrets.json"));
		if (clientSecrets.getDetails().getClientId().startsWith("Enter")
				|| clientSecrets.getDetails().getClientSecret()
						.startsWith("Enter ")) {
			System.out
					.println("Enter Client ID and Secret from https://code.google.com/apis/console/?api=analytics "
							+ "into analytics-cmdline-sample/src/main/resources/client_secrets.json");
			System.exit(1);
		}
		// set up file credential store
		FileCredentialStore credentialStore = new FileCredentialStore(
				new File(System.getProperty("user.home"),
						".credentials/analytics.json"), JSON_FACTORY);
		// set up authorization code flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
				Collections.singleton(AnalyticsScopes.ANALYTICS_READONLY))
				.setCredentialStore(credentialStore).build();
		// authorize
		return new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
	}
}
