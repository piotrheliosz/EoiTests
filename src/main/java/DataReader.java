import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {


    public List<String> columnList(String csvFileName) throws IOException {

        String filePath = "C:\\workspace\\EoiTests\\columnsData\\" + csvFileName;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        List<String> columnData = new ArrayList<String>();

        while (reader.ready()) {
            String row = reader.readLine().replace(";", "");
            columnData.add(row);
        }
        return columnData;
    }

}
