package com.sma.mobile.android.TWApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import com.sma.mobile.android.TWApi.HttpUtils.HttpVerbs;

public class HttpRequest {

	StringBuilder sb = null;

	public HttpRequest() {
		sb = new StringBuilder();
	}

	public String doRequest(String baseURL, HttpVerbs method,
			boolean authRequired, Map<String, String> credentials,
			Map<String, String> data) {

		sb.setLength(0);
		BufferedReader rd = null;
		try {
			URL url = null;
			sb.append(baseURL);

			// add GET params to url
			if (!data.isEmpty() && method == HttpVerbs.GET) {
				sb.append("?");
				for (String key : data.keySet()) {
					sb.append(key);
					sb.append("=");
					sb.append(data.get(key));
					sb.append("&");
				}
			}

			url = new URL(sb.toString());
			sb.setLength(0);

			if (authRequired)
				setAuthentication(credentials.get("user"), credentials
						.get("password"));

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod(method.toString());
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setReadTimeout(10000);

			if (!data.isEmpty() && method == HttpVerbs.POST) {
				for (String key : data.keySet()) {
					sb.append(key);
					sb.append("=");
					sb.append(URLEncoder.encode(data.get(key)));
					sb.append("&");
				}

				String urlParams = sb.toString();

				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				connection.setRequestProperty("Content-Length", ""
						+ String.valueOf(urlParams.getBytes().length));
				connection.setRequestProperty("Content-Language", "en-US");

				DataOutputStream wr = new DataOutputStream(connection
						.getOutputStream());
				wr.writeBytes(urlParams);
				wr.flush();
				wr.close();

				sb.setLength(0);
			}

			rd = new BufferedReader(new InputStreamReader(connection
					.getInputStream()));
			String line = null;
			while ((line = rd.readLine()) != null) {
				sb.append(line + '\n');
			}
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public void setAuthentication(final String user, final String password) {
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password.toCharArray());
			}
		});
	}
}
