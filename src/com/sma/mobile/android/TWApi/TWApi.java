package com.sma.mobile.android.TWApi;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.sma.mobile.android.TWApi.HttpUtils.HttpProtocols;
import com.sma.mobile.android.TWApi.HttpUtils.HttpVerbs;

public class TWApi {

	private static final String SEARCH_BASE = "search.twitter.com";
	private static final String REST_BASE = "twitter.com";

	private Map<String, String> mCredentials;

	public void setCredentials(Map<String, String> mCredentials) {
		this.mCredentials = mCredentials;
	}

	public Map<String, String> getCredentials() {
		return mCredentials;
	}

	public TWApi(String user, String password) {
		mCredentials = new HashMap<String, String>();
		mCredentials.put("user", user);
		mCredentials.put("password", password);
	}

	private static JSONArray doApiRequest(String base, String action,
			boolean authRequired, HttpVerbs method,
			Map<String, String> credentials, Map<String, String> data) {
		JSONArray response = null;
		try {
			response = new JSONArray(new HttpRequest().doRequest(String
					.format("%s://%s/%s.json",
							(authRequired) ? HttpProtocols.http.toString()
									: HttpProtocols.http.toString(), base,
							action), method, authRequired, credentials, data));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}

	// Status methods
//
//	public JSONObject statusesShow(String id) {
//		String action = String.format("/statuses/show/%s", id);
//		return doApiRequest(REST_BASE, action, true, HttpVerbs.GET,
//				getCredentials(), null);
//	}
//
//	public JSONObject statusesUpdate(String status) {
//		String action = "/statuses/update";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("status", status);
//		return doApiRequest(REST_BASE, action, true, HttpVerbs.POST,
//				getCredentials(), params);
//	}
//
//	public JSONObject statusesDestroy(String id) {
//		String action = String.format("/destroy/%s", id);
//		return doApiRequest(REST_BASE, action, true, HttpVerbs.DELETE,
//				getCredentials(), null);
//	}
//
//	// http://twitter.com/statuses/public_timeline.json
//	public JSONObject statusesPublicTimeline() {
//		String action = "/statuses/public_timeline";
//		return doApiRequest(REST_BASE, action, false, HttpVerbs.GET, null, null);
//	}

//	// http://twitter.com/statuses/friends_timeline.json
//	public JSONObject statusesFriendsTimeline() {
//		String action = "/statuses/friends_timeline";
//		return doApiRequest(REST_BASE, action, true, HttpVerbs.GET,
//				getCredentials(), null);
//	}

	// http://twitter.com/statuses/user_timeline.json
	public JSONArray statusesUserTimeline(String id, Map<String, String> data) {
		String action = String.format("/statuses/user_timeline/%s", id);
		return doApiRequest(REST_BASE, action, true, HttpVerbs.GET,
				getCredentials(), data);
	}

//	// http://twitter.com/statuses/mentions.json
//	public JSONObject statusesMentions(Map<String, String> data) {
//		String action = "/statuses/mentions";
//		return doApiRequest(REST_BASE, action, true, HttpVerbs.GET,
//				getCredentials(), data);
//	}
//
//	// Help methods
//
//	public static JSONObject test() throws IOException {
//		String action = "help/test";
//		return doApiRequest(REST_BASE, action, false, HttpVerbs.GET, null, null);
//	}

	// Search methods

}
