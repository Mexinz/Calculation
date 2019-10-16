package util;

import java.io.*;
import java.util.ArrayList;
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

    public static List<String> readFiles(String fileUrl) {
        File file = new File(fileUrl);
        if (file.exists()) {
            List<String> formulas = new ArrayList<>();
            try (InputStream inputStream = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
                String str;
                while((str=reader.readLine()) != null){
                    formulas.add(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return formulas;
        } else {
            return null;
        }
    }

    public static boolean writeGrades(List<Integer> corrects, List<Integer> wrongs) {
        File file = new File(Constants.gradeFile);
        try (OutputStream outputStream = new FileOutputStream(file);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))){
                writer.write("Correct: " + corrects.size() + corrects.toString());
                writer.newLine();
                writer.write("Wrong: " + wrongs.size() + wrongs.toString());
            }
         catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
