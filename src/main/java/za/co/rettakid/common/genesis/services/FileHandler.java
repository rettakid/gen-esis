package za.co.rettakid.common.genesis.services;

import java.io.*;

/**
 * Created by lwazi8 on 26/08/2015.
 */
public class FileHandler {

    private final static String CHARSET = "UTF-8";

    public static String getFileText(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),CHARSET));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                stringBuilder.append(currentLine);
                stringBuilder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void saveFile(String content,String filePath) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(filePath);
        out.write(content);
        out.close();
    }

}
