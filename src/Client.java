import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {

    private static boolean testPosted;
    private static String testGet;

    public static void main(String[] args) {

        String serverHost = "localhost";
        String serverPort = "8080";


        while (true) {
            System.out.println("Enter your command. POST key value, GET key, quit");
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine();
            String[] inputArray = inputString.split(" ");
            try {
                String command = inputArray[0];
                if (command.toLowerCase().equals("quit")) {
                    break;
                }

                String key = inputArray[1];
                String value = "";
                if (command.toLowerCase().equals("post")) {
                    value = inputArray[2];
                    postToServer(serverHost, serverPort, command, key, value);
                } else {
                    getFromServer(serverHost,serverPort,command,key);
                }
            } catch (Exception e) {
                System.out.println("Please Enter a valid command.");
            }
        }

//        getGameList(serverHost, serverPort);
//        claimRoute(serverHost, serverPort);
    }

    public boolean testPost(String serverHost,String serverPort,String command, String key, String value) {
        testPosted = false;
        postToServer(serverHost,serverPort,command,key,value);

        return testPosted;
    }

    public String testGet(String serverHost,String serverPort,String command,String key) {
        testGet = "null";
        getFromServer(serverHost,serverPort,command,key);
        return testGet;
    }


    private static void getFromServer(String serverHost,String serverPort,String command,String key) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/key_value_store/?key="+key);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestProperty("key",key);
            http.setRequestMethod("GET");
            http.setDoOutput(false);	// There is no request body

            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                if (respBody.available() == 0) {
                    System.out.println("No key named " + key + " found.");
                } else {
                    String respData = readString(respBody);
                    testGet = respData;
                    System.out.println(respData);
                }
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void postToServer(String serverHost, String serverPort, String command, String key, String value) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/key_value_store/?key="+key+"&value="+value);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod(command.toUpperCase());
            http.setDoOutput(true);

            http.connect();
            String reqData = key + '\t' + value;

            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (command.toLowerCase().equals("post")) {
                    testPosted = true;
                    System.out.println("Key Value added to Server");
                }
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
