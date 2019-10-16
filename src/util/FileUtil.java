package util;

import java.io.*;
import java.util.List;

/**
 * @Author: Mexinz
 * @Date: 2019/10/16
 */
public class FileUtil {

    public static boolean writeFormulas(List<String> strings) {
        return writeFiles(strings,new File(Constants.exerciseFile));
    }

    public static boolean writeAnswers(List<String> strings) {
        return writeFiles(strings,new File(Constants.answerFile));
    }

    public static boolean writeFiles(List<String> strings, File file) {
        try (OutputStream outputStream = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))){
            for (int i = 1; i <= strings.size() ; i++) {
                writer.write(i + ". " + strings.get(i -1));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
