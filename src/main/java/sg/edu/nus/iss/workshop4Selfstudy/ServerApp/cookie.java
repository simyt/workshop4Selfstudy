package sg.edu.nus.iss.workshop4Selfstudy.ServerApp; //pacakge is the hierarchy of files

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class cookie {
    public static String getRandomCookie(String cookieFilePath) {
        String randomCookie = "No cookie for you"; //when nothing is in the cookie file
        List<String> cookies = new LinkedList<>();
        
        try {
            cookies = getDataFromCookieFile(cookieFilePath);

            Random r = new Random();
            if (cookies.size() > 0 ) {
                int randIdx = r.nextInt(cookies.size());
                randomCookie = cookies.get(randIdx);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return randomCookie;
    }

    //create a list to hold all the cookies
    private static List<String> getDataFromCookieFile (String cookieFilePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("cookieFilePath"));
        List<String> cookies = new LinkedList<>();
        String line;
        while ((line = br.readLine()) != null) {
            cookies.add(line);
        }
        return cookies;
    }
}
