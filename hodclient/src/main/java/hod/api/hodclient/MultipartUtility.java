package hod.api.hodclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class MultipartUtility {
    private final String mBoundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection mHttpConn;
    private String mCharset;
    private OutputStream mOutputStream;
    private PrintWriter mWriter;

    public MultipartUtility(String requestURL, String charset)
            throws IOException {
        mCharset = charset;

        // creates a unique boundary based on time stamp
        mBoundary = "===" + System.currentTimeMillis() + "===";

        URL url = new URL(requestURL);
        mHttpConn = (HttpURLConnection) url.openConnection();
        mHttpConn.setUseCaches(false);
        mHttpConn.setDoOutput(true);
        mHttpConn.setDoInput(true);
        mHttpConn.setReadTimeout(100000);
        mHttpConn.setConnectTimeout(600000);
        mHttpConn.setRequestMethod("POST");
        mHttpConn.setUseCaches(false);
        mHttpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + mBoundary);
        //mHttpConn.setRequestProperty("User-Agent", "CodeJava Agent");
        mOutputStream = mHttpConn.getOutputStream();
        mWriter = new PrintWriter(new OutputStreamWriter(mOutputStream, charset), true);
    }


    public void addFormField(String name, String value) {
        mWriter.append("--" + mBoundary).append(LINE_FEED);
        mWriter.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
        mWriter.append("Content-Type: text/plain; charset=" + mCharset).append(LINE_FEED);
        mWriter.append(LINE_FEED);

        mWriter.append(value).append(LINE_FEED);
        mWriter.flush();
    }

    public void addFilePart(String fieldName, File uploadFile) throws IOException {
        String fileName = uploadFile.getName();
        mWriter.append("--" + mBoundary).append(LINE_FEED);
        mWriter.append("Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        mWriter.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);

        mWriter.append(LINE_FEED);
        mWriter.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            mOutputStream.write(buffer, 0, bytesRead);
        }
        mOutputStream.flush();
        inputStream.close();

        mWriter.append(LINE_FEED);
        mWriter.flush();
    }

    public void addHeaderField(String name, String value) {
        mWriter.append(name + ": " + value).append(LINE_FEED);
        mWriter.flush();
    }

    public String finish() throws IOException {
        String response = "";
        mWriter.append("--" + mBoundary).append(LINE_FEED);
        mWriter.flush();
        mWriter.close();

        // checks server's status code first
        int status = mHttpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            InputStreamReader isr = new InputStreamReader(mHttpConn.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder builder = new StringBuilder();
            String aux = "";
            while ((aux = reader.readLine()) != null) {
                builder.append(aux);
            }
            response = builder.toString();
            reader.close();
            mHttpConn.disconnect();
        } else if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
            InputStream errorstream = mHttpConn.getErrorStream();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(errorstream));
            try {
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            }catch(Exception ex) {
                throw new IOException(ex.getMessage());
            }
            if (response.length() == 0) {
                String error = String.format("Http error: %d, PostRequest failed.", status);
                throw new IOException(error);
            }
        } else {
            String error = String.format("Http error: %d, PostRequest failed.", status);
            throw new IOException(error);
        }
        return response;
    }
}