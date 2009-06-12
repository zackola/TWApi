package com.sma.mobile.android.TWApi;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sma.mobile.android.TWApi.HttpUtils.HttpProtocols;
import com.sma.mobile.android.TWApi.HttpUtils.HttpVerbs;

public class TWApi {

	private static final String TAG = "TWApi";

	private static final String SEARCH_BASE = "search.twitter.com";
	private static final String REST_BASE = "twitter.com";

	private Map<String, String> mCredentials;

	public TWApi(String user, String password) {
		mCredentials = new HashMap<String, String>();
		mCredentials.put("user", user);
		mCredentials.put("password", password);
	}

	private static JSONArray doApiRequestArray(String base, String action,
			boolean authRequired, HttpVerbs method,
			Map<String, String> credentials, Map<String, String> data) {
		JSONArray response = null;
		try {
			HttpRequest request = new HttpRequest();
			response = new JSONArray(request.doRequest(String.format(
					"%s://%s/%s.json", (authRequired) ? HttpProtocols.http
							.toString() : HttpProtocols.http.toString(), base,
					action), method, authRequired, credentials, data));
		} catch (JSONException e) {
			Log.e(TAG, e.toString());
		}
		return response;
	}

	private static JSONObject doApiRequestObject(String base, String action,
			boolean authRequired, HttpVerbs method,
			Map<String, String> credentials, Map<String, String> data) {
		JSONObject response = null;
		try {
			HttpRequest request = new HttpRequest();
			response = new JSONObject(request.doRequest(String.format(
					"%s://%s/%s.json", (authRequired) ? HttpProtocols.http
							.toString() : HttpProtocols.http.toString(), base,
					action), method, authRequired, credentials, data));
		} catch (JSONException e) {
			Log.e(TAG, e.toString());
		}
		return response;
	}

	// Status methods

	// http://twitter.com/statuses/show/id.json
	public JSONObject statusesShow(String id) {
		String action = String.format("/statuses/show/%s", id);
		return doApiRequestObject(REST_BASE, action, true, HttpVerbs.GET,
				mCredentials, null);
	}

	// http://twitter.com/statuses/update.json
	@Untested
	public JSONObject statusesUpdate(String status) {
		String action = "/statuses/update";
		Map<String, String> params = new HashMap<String, String>();
		params.put("status", status);
		return doApiRequestObject(REST_BASE, action, true, HttpVerbs.POST,
				mCredentials, params);
	}

	// http://twitter.com/statuses/destroy/123.json
	@Untested
	public JSONObject statusesDestroy(String id) {
		String action = String.format("/statuses/destroy/%s", id);
		return doApiRequestObject(REST_BASE, action, true, HttpVerbs.DELETE,
				mCredentials, null);
	}

	// http://twitter.com/statuses/public_timeline.json
	@Untested
	public JSONArray statusesPublicTimeline() {
		String action = "/statuses/public_timeline";
		return doApiRequestArray(REST_BASE, action, false, HttpVerbs.GET, null,
				null);
	}

	// http://twitter.com/statuses/friends_timeline.json
	public JSONArray statusesFriendsTimeline() {
		String action = "/statuses/friends_timeline";
		return doApiRequestArray(REST_BASE, action, true, HttpVerbs.GET,
				mCredentials, null);
	}

	// http://twitter.com/statuses/user_timeline.json
	@Untested
	public JSONArray statusesUserTimeline(String id, Map<String, String> data) {
		String action = String.format("/statuses/user_timeline/%s", id);
		return doApiRequestArray(REST_BASE, action, true, HttpVerbs.GET,
				mCredentials, data);
	}

	// http://twitter.com/statuses/mentions.json
	@Untested
	public JSONArray statusesMentions(Map<String, String> data) {
		String action = "/statuses/mentions";
		return doApiRequestArray(REST_BASE, action, true, HttpVerbs.GET,
				mCredentials, data);
	}

	// Help methods
	
	public static JSONObject test() {
		String action = "help/test";
		return doApiRequestObject(REST_BASE, action, false, HttpVerbs.GET,
				null, null);
	}

	// Search methods
	// Soon to come

}
