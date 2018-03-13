import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import java.util.HashMap;
import java.util.Map;


public class KeyValueHandler implements HttpHandler {

    private HashMap<String, String> keyValueMap = new HashMap<>();
    private String keyName = "key";


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        boolean success = false;
        LocalDatabase localDatabase = new LocalDatabase();
        if (keyValueMap.size() == 0) {
            if (localDatabase.fileIsCreated()) {
                try {
                    keyValueMap = localDatabase.readFile();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        Headers requestHeaders = httpExchange.getRequestHeaders();
        InputStream requestBody = httpExchange.getRequestBody();
        String requestData = readString(requestBody);

        if (httpExchange.getRequestMethod().toLowerCase().equals("post")) {

            String[] requestDataArray = requestData.split("\t");
            String key = requestDataArray[0];
            String value = requestDataArray[1];
            keyValueMap.put(key, value);
            localDatabase.clear();
            localDatabase.saveFile(keyValueMap);

            String responseData = "Name added to database";
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            success = true;

            OutputStream responseBody = httpExchange.getResponseBody();
            writeString(responseData, responseBody);

        } else if (httpExchange.getRequestMethod().toLowerCase().equals("get")) {
            Map<String, String> params = queryToMap(httpExchange.getRequestURI().getQuery());
            String respData = keyValueMap.get(params.get(keyName));

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
            OutputStream respBody = httpExchange.getResponseBody();
            writeString(respData, respBody);
            respBody.close();
            success = true;
        }

        if (!success) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }
    }

    private Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = streamReader.read(buf)) > 0) {
            stringBuilder.append(buf,0,len);
        }
        return stringBuilder.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
