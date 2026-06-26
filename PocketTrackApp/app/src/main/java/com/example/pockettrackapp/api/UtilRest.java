package com.example.pockettrackapp.api;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UtilRest {

    private static Map<String,String> headers = new HashMap<>();

    public static void addHeader(String key, String value){
        headers.put(key,value);
    }

    public static void clearHeaders(){
        headers.clear();
    }
    public interface OnResponseListener {
        void onSuccess(Response r);
        void onError(Response r);
    }

    public enum QueryType {
        GET, POST, PUT, PATCH ,DELETE
    }

    public static class Response {
        public int responseCode = -1;
        public String content = null;
        public String exception = null;
    }

    private static class Request {
        public QueryType type;
        public String url = null;
        public OnResponseListener callback = null;
        public String data = null;
    }

    public static void runQuery(QueryType type, String strUrl, String data, OnResponseListener listener) {
        Request request = new Request();
        request.type = type;
        request.url = strUrl;
        request.callback = listener;
        request.data = data;
        new PrvDownloadTask().execute(request);
    }

    public static void runQuery(QueryType type, String strUrl, OnResponseListener listener) {
        runQuery(type, strUrl, null, listener);
    }

    private static class PrvDownloadTask extends AsyncTask<Request, Void, Response> {
        private Request mRequest = null;

        @Override
        protected Response doInBackground(Request... params) {
            HttpURLConnection http = null;
            Response response = new Response();

            try {
                mRequest = params[0];
                URL url = new URL(mRequest.url);
                http = (HttpURLConnection)url.openConnection();
                http.setRequestProperty("Accept", "application/json");
                http.setRequestProperty("Content-Type", "application/json; charset=utf-8");

                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    http.setRequestProperty(entry.getKey(), entry.getValue());
                }

                Log.e("HEADERS", "Headers enviados: " + headers.toString());


                switch (mRequest.type) {
                    case GET:   http.setRequestMethod("GET");   break;
                    case POST:  http.setRequestMethod("POST");  break;
                    case PUT:   http.setRequestMethod("PUT");   break;
                    case PATCH: http.setRequestMethod("PATCH"); break;
                    case DELETE:  http.setRequestMethod("DELETE");  break;
                }
                Log.d(getClass().getName(), "Request " + mRequest.url);
                Log.d(getClass().getName(), " - Type " + http.getRequestMethod());

                if(mRequest.type == QueryType.POST || mRequest.type == QueryType.PUT || mRequest.type == QueryType.PATCH) {
                    http.setDoOutput(true);
                    PrintWriter writer = new PrintWriter(http.getOutputStream());
                    writer.print( mRequest.data );
                    writer.flush();
                    Log.d(getClass().getName(), " - Output data: " + mRequest.data);
                }

                response.responseCode = http.getResponseCode();
                Log.d(getClass().getName(), " - Response code " + response.responseCode);

                if( http.getResponseCode() == HttpURLConnection.HTTP_OK || response.responseCode == 201)
                    response.content = prv_readStream(http.getInputStream());
                else
                    response.content = prv_readStream(http.getErrorStream());
                Log.d(getClass().getName(), " - Response content " + response.content);
            }
            catch(Exception e) {
                e.printStackTrace();
                if( response.responseCode == -1 )
                    response.responseCode = 500;
                response.exception = e.getLocalizedMessage();
            }
            finally {
                if(http != null)
                    http.disconnect();
            }

            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            if(response.responseCode == HttpURLConnection.HTTP_OK || response.responseCode == 201)
                mRequest.callback.onSuccess( response );
            else {
                if(response.exception == null)
                    response.exception = "Wrong server response";
                mRequest.callback.onError( response );
            }
        }

        private String prv_readStream(InputStream in) {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = null;

            try {
                reader = new BufferedReader( new InputStreamReader( in, "UTF-8" ) );
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
            finally {
                if( reader != null ) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }
    }
}
