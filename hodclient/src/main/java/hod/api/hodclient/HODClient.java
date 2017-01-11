package hod.api.hodclient;

/**
 * Created by vuv on 10/10/2015.
 */
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.MimeTypeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;


public class HODClient {
    private class ErrorCode {
        public static final int TIMEOUT = 1600;
        public static final int IN_PROGRESS = 1610;
        public static final int QUEUED = 1620;
        public static final int NONSTANDARD_RESPONSE = 1630;
        public static final int INVALID_PARAM = 1640;
        public static final int INVALID_HOD_RESPONSE = 1650;
        public static final int UNKNOWN_ERROR = 1660;
        public static final int HTTP_ERROR = 1670;
        public static final int CONNECTION_ERROR = 1680;
        public static final int IO_ERROR = 1690;
        public static final int HOD_CLIENT_BUSY = 1700;
    }
    private String mApiKey = "";
    private String mHodBase = "https://api.havenondemand.com/1/api/";
    private String mHodJobResult = "https://api.havenondemand.com/1/job/result/";
    private String mHodJobStatus = "https://api.havenondemand.com/1/job/status/";
    private String mHodCombineAsync = "async/executecombination";
    private String mHodCombineSync = "sync/executecombination";
    private boolean getJobID = true;
    private String mVersion = "v1";
    private boolean isBusy = false;
    private boolean isError = false;
    IHODClientCallback mCallback;

    private enum HTTP_METHOD  { GET, POST };
    private HTTP_METHOD httpMethod = HTTP_METHOD.GET;

    public HODClient(String apiKey, String version, IHODClientCallback callback) {
        this.mApiKey = apiKey;
        this.mVersion = version;
        mCallback = callback;
        initializeHTTP();
    }
    public HODClient(String apiKey, IHODClientCallback callback) {
        this.mApiKey = apiKey;
        this.mVersion = "v1";
        mCallback = callback;
        initializeHTTP();;
    }
    public void SetVersion(String version) {
        this.mVersion = version;
    }
    public void SetAPIKey(String apiKey) {
        this.mApiKey = apiKey;
    }
    private void initializeHTTP() {

    }
    public void GetJobResult(String jobID) {
        if (!isBusy) {
            isBusy = true;
            httpMethod = HTTP_METHOD.GET;
            String queryStr = String.format("%s%s?apikey=%s", mHodJobResult, jobID, mApiKey);
            getJobID = false;
            new MakeAsyncActivitiesTask().execute(null, queryStr, "");
        } else {
            mCallback.onErrorOccurred("Busy. Previous operation is in progress");
        }
    }
    public void GetJobStatus(String jobID) {
        if (!isBusy) {
            isBusy = true;
            httpMethod = HTTP_METHOD.GET;
            String queryStr = String.format("%s%s?apikey=%s", mHodJobStatus, jobID, mApiKey);
            getJobID = false;
            new MakeAsyncActivitiesTask().execute(null, queryStr, "");
        } else {
            mCallback.onErrorOccurred("Busy. Previous operation is in progress");
        }
    }
    public void GetRequest(Map<String,Object> params, String hodApp, Boolean async) {
        if (!isBusy) {
            isBusy = true;
            httpMethod = HTTP_METHOD.GET;
            String endPoint = mHodBase;
            if (async) {
                endPoint += String.format("async/%s/%s", hodApp, mVersion);
            } else {
                endPoint += String.format("sync/%s/%s", hodApp, mVersion);
            }
            getJobID = async;
            new MakeAsyncActivitiesTask().execute(params, endPoint, "standalone");
        } else {
            mCallback.onErrorOccurred("Busy. Previous operation is in progress");
        }
    }
    public void GetRequest(Map<String,Object> params, String hodApp, String version, Boolean async) {
        if (!isBusy) {
            isBusy = true;
            httpMethod = HTTP_METHOD.GET;
            String endPoint = mHodBase;
            if (async) {
                endPoint += String.format("async/%s/%s", hodApp, version);
            } else {
                endPoint += String.format("sync/%s/%s", hodApp, version);
            }
            getJobID = async;
            new MakeAsyncActivitiesTask().execute(params, endPoint, "standalone");
        } else {
            mCallback.onErrorOccurred("Busy. Previous operation is in progress");
        }
    }
    public void PostRequest(Map<String,Object> params, String hodApp, Boolean async) {
        if (!isBusy) {
            isBusy = true;
            httpMethod = HTTP_METHOD.POST;
            String endPoint = mHodBase;
            if (async) {
                endPoint += String.format("async/%s/%s", hodApp, mVersion);
            } else {
                endPoint += String.format("sync/%s/%s", hodApp, mVersion);
            }
            getJobID = async;
            new MakeAsyncActivitiesTask().execute(params, endPoint, "standalone");
        } else {
            mCallback.onErrorOccurred("Busy. Previous operation is in progress");
        }
    }
    public void PostRequest(Map<String,Object> params, String hodApp, String version, Boolean async) {
        if (!isBusy) {
            isBusy = true;
            httpMethod = HTTP_METHOD.POST;
            String endPoint = mHodBase;
            if (async) {
                endPoint += String.format("async/%s/%s", hodApp, version);
            } else {
                endPoint += String.format("sync/%s/%s", hodApp, version);
            }
            getJobID = async;
            new MakeAsyncActivitiesTask().execute(params, endPoint, "standalone");
        } else {
            mCallback.onErrorOccurred("Busy. Previous operation is in progress");
        }
    }
    public void GetRequestCombination(Map<String,Object> params, String hodApp, Boolean async) {
        if (!isBusy) {
            isBusy = true;
            String queryStr = mHodBase;
            if (async) {
                queryStr += String.format("%s/%s?apikey=%s", mHodCombineAsync, this.mVersion, this.mApiKey);
            } else {
                queryStr += String.format("%s/%s?apikey=%s", mHodCombineSync, this.mVersion, this.mApiKey);
            }
            getJobID = async;
            queryStr += "&combination=" + hodApp;

            httpMethod = HTTP_METHOD.GET;
            new MakeAsyncActivitiesTask().execute(params, queryStr, "combination");
        } else {
            mCallback.onErrorOccurred("Busy. Previous operation is in progress");
        }
    }
    public void PostRequestCombination(Map<String,Object> params, String hodApp, Boolean async) {
        if (!isBusy) {
            isBusy = true;
            String endPoint = mHodBase;
            if (async) {
                endPoint += String.format("%s/%s?", mHodCombineAsync, this.mVersion);
            } else {
                endPoint += String.format("%s/%s?", mHodCombineSync, this.mVersion);
            }
            getJobID = async;
            httpMethod = HTTP_METHOD.POST;
            new MakeAsyncActivitiesTask().execute(params, endPoint, hodApp);
        } else {
            mCallback.onErrorOccurred("Busy. Previous operation is in progress");
        }
    }
    private String createGetRequest(String query, Map<String,Object> params)
    {
        String url = String.format("%s?apikey=%s", query, mApiKey);
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                String key = e.getKey();
                if (key.equals("file")) {
                    isError = true;
                    return createErrorJSONString(ErrorCode.INVALID_PARAM, "File upload must be used with PostRequest method.", "");
                }
                Object val = e.getValue();
                String type = val.getClass().getName();
                if (type.equals("java.util.ArrayList")) {
                    for (String m : (ArrayList<String>) val) {
                        try {
                            String value = URLEncoder.encode(m, "utf-8");
                            url += String.format("&%s=%s", key, value);
                        } catch (UnsupportedEncodingException ex) {
                            isError = true;
                            return createErrorJSONString(ErrorCode.UNKNOWN_ERROR, ex.getMessage(), "");
                        }
                    }
                } else {
                    try {
                        String value = URLEncoder.encode(val.toString(), "utf-8");
                        url += String.format("&%s=%s", key, value);
                    } catch (UnsupportedEncodingException ex) {
                        isError = true;
                        return createErrorJSONString(ErrorCode.UNKNOWN_ERROR, ex.getMessage(), "");
                        }
                }
            }
        }
        return url;
    }
    private String createGetCombinationRequest(String query, Map<String,Object> params)
    {
        String queryStr = query;
        if (params != null) {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                String key = e.getKey();
                Object val = e.getValue();
                if (key.equals("file")) {
                    isError = true;
                    return createErrorJSONString(ErrorCode.INVALID_PARAM, "File upload must be used with PostRequest method.", "");
                } else {
                    try {
                        String jsonVal = (String) val;
                        if (isJSON(jsonVal)) {
                            String value = URLEncoder.encode(jsonVal, "utf-8");
                            queryStr += String.format("&parameters={\"name\":\"%s\",\"value\":%s}", key, value);
                        }else {
                            String value = URLEncoder.encode(jsonVal, "utf-8");
                            queryStr += String.format("&parameters={\"name\":\"%s\",\"value\":\"%s\"}", key, value);
                        }
                    } catch (UnsupportedEncodingException ex) {
                        isError = true;
                        return createErrorJSONString(ErrorCode.UNKNOWN_ERROR, ex.getMessage(), "");
                    }
                }
            }
        }
        return queryStr;
    }
    private String createPostRequest(MultipartUtility multipart, Map<String,Object> params)
    {
        try {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                String key = e.getKey();
                Object val = e.getValue();
                String objType = val.getClass().getName();
                if (objType.equals("java.util.ArrayList")) {
                    if (key.equals("file")) {
                        for (String m : (ArrayList<String>) val) {
                            File pFile = new File(m);
                            if (pFile.exists()) {
                                multipart.addFilePart("file", pFile);

                            } else {
                                isError = true;
                                return createErrorJSONString(ErrorCode.INVALID_PARAM, "File not found", "");
                            }
                        }
                    } else {
                        for (String m : (ArrayList<String>) val) {
                            String encodedParam = URLEncoder.encode(m, "UTF-8");
                            multipart.addFormField(key, encodedParam);
                        }
                    }
                } else {
                    if (key.equals("file")) {
                        String fileFullName = val.toString();
                        File pFile = new File(fileFullName);
                        if (pFile.exists()) {
                            multipart.addFilePart("file", pFile);
                        } else {
                            isError = true;
                            return createErrorJSONString(ErrorCode.INVALID_PARAM, "File not found", "");
                        }
                    } else {

                        if (key.equals("text")) {
                            multipart.addFormField(key, (String) val);
                        } else {
                            multipart.addFormField(key, (String) val);
                        }
                    }
                }
            }
        } catch (IOException ex)
        {
            isError = true;
            return createErrorJSONString(ErrorCode.UNKNOWN_ERROR, ex.getMessage(), "");
        }
        return "";
    }
    private String createPostCombinationRequest(MultipartUtility multipart, Map<String,Object> params)
    {
        try {
            for (Map.Entry<String, Object> e : params.entrySet()) {
                String key = e.getKey();
                Object val = e.getValue();
                String objType = val.getClass().getName();
                if (key.equals("file")) {
                    if (objType.equals("java.util.ArrayList")) { // multiple files in arrayList
                        for (Object obj : (ArrayList<Object>) val) {
                            Map<String,String> file = (Map<String,String>) obj;
                            for (Map.Entry<String, String> f : file.entrySet()) {
                                String fn = f.getKey();
                                String name = f.getValue();
                                File pFile = new File(name);
                                if (pFile.exists()) {
                                    multipart.addFormField("file_parameters", fn);
                                    multipart.addFilePart(key, pFile);

                                } else {
                                    isError = true;
                                    return createErrorJSONString(ErrorCode.INVALID_PARAM, "File not found", "");
                                }
                            }
                        }
                    } else { // single file
                        Map<String,String> file = (Map<String,String>) val;
                        for (Map.Entry<String, String> f : file.entrySet()) {
                            String fn = f.getKey();
                            String name = f.getValue();
                            File pFile = new File(name);
                            if (pFile.exists()) {
                                multipart.addFormField("file_parameters", fn);
                                multipart.addFilePart(key, pFile);

                            } else {
                                isError = true;
                                return createErrorJSONString(ErrorCode.INVALID_PARAM, "File not found", "");
                            }
                        }
                    }
                } else {
                    String jsonVal = (String) val;
                    if (isJSON(jsonVal))
                        jsonVal = String.format("{\"name\":\"%s\",\"value\":%s}", key, jsonVal);
                    else
                        jsonVal = String.format("{\"name\":\"%s\",\"value\":\"%s\"}", key, jsonVal);
                    multipart.addFormField("parameters", jsonVal);
                }
            }
        } catch (Exception ex)
        {
            isError = true;
            return createErrorJSONString(ErrorCode.UNKNOWN_ERROR, ex.getMessage(), "");
        }
        return "";
    }
    private Boolean isJSON(String str) {
        Boolean ret = true;
        try {
            new JSONObject(str);
        } catch (JSONException ex) {
            try {
                new JSONArray(str);
            } catch (JSONException ex1) {
                ret = false;
            }
        }
        return ret;
    }
    private String createErrorJSONString(int code, String reason, String detail) {
        return String.format("{\"error\":%d,\"reason\":\"%s\",\"detail\":\"%s\"}", code, reason, detail);
    }
    private void ParseResponse(String response) {
        isBusy = false;
        if (getJobID)
            mCallback.requestCompletedWithJobID(response);
        else
            mCallback.requestCompletedWithContent(response);
    }
    private void ParseError(String error) {
        isBusy = false;
        mCallback.onErrorOccurred(error);
    }
    class MakeAsyncActivitiesTask extends AsyncTask<Object, String, String> {
        @Override
        protected String doInBackground(Object... params) {
            isError = false;
            String url = "";
            URL connectUrl;
            if (httpMethod == HTTP_METHOD.GET) {
                url = (String) params[1];
                if (params[0] != null) {
                    Map<String, Object> map = (Map) params[0];
                    if (params[2].equals("standalone"))
                        url = createGetRequest(url, map);
                    else
                        url = createGetCombinationRequest(url, map);

                    if (isError)
                        return url;
                }
                HttpURLConnection urlConnection = null;
                try {
                    connectUrl = new URL(url);
                    urlConnection = (HttpURLConnection) connectUrl.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    InputStreamReader isr = new InputStreamReader(in);
                    String response = "";
                    int status = urlConnection.getResponseCode();
                    if (status == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(isr);
                        StringBuilder builder = new StringBuilder();
                        String aux = "";
                        while ((aux = reader.readLine()) != null) {
                            builder.append(aux);
                        }
                        response = builder.toString();
                        reader.close();
                        return response;
                    } else if (status == HttpURLConnection.HTTP_BAD_REQUEST){
                        isError = true;
                        return createErrorJSONString(status, "HTTP error. SendRequest failed. Please try again", "");
                    } else {
                        // return error status
                        isError = true;
                        return createErrorJSONString(status, "HTTP error. SendRequest failed. Please try again", "");
                    }
                } catch (IOException e1) {
                    InputStream errorstream = urlConnection.getErrorStream();
                    String response="";
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(errorstream));
                    try {
                        while ((line = br.readLine()) != null) {
                            response += line;
                        }
                    }catch(Exception ex) {
                        return createErrorJSONString(ErrorCode.UNKNOWN_ERROR, ex.getMessage(), "");
                    }
                    isError = true;
                    if (response.length() > 0)
                        return response;
                    else
                        return createErrorJSONString(ErrorCode.UNKNOWN_ERROR, e1.getMessage(), "");
                } finally {
                    urlConnection.disconnect();
                }
            } else if (httpMethod == HTTP_METHOD.POST) {
                try {
                    url = (String) params[1];
                    String charset = "UTF-8";
                    MultipartUtility multipart = new MultipartUtility(url, charset);
                    String error = "";
                    Map<String, Object> map = (Map) params[0];
                    multipart.addFormField("apikey", mApiKey);

                    if (params[2].equals("standalone")) {
                        error = createPostRequest(multipart, map);
                    } else {
                        multipart.addFormField("combination", (String) params[2]);
                        error = createPostCombinationRequest(multipart, map);
                    }
                    if (isError){
                        return error;
                    }
                    String response = multipart.finish(); // response from server.
                    if (response != null)
                        return response;
                    else {
                        isError = true;
                        return createErrorJSONString(ErrorCode.UNKNOWN_ERROR, "Unknown server error", "");
                    }
                } catch (IOException ex) {
                    isError = true;
                    return createErrorJSONString(ErrorCode.UNKNOWN_ERROR, ex.getMessage(), "");
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... unsued) {

        }

        @Override
        protected void onPostExecute(String sResponse) {
            if (isError) {
                ParseError(sResponse);
            } else {
                ParseResponse(sResponse);
            }
        }
    }

    private static String getMimeType(String url) {
        String extension = url.substring(url.lastIndexOf("."));
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extension);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
        return mimeType;
    }
    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}