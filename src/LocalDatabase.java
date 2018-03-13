import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class LocalDatabase {

    private String PATH = "/localDatase.txt";
    private Path currentRelativePath = Paths.get("");

    public void saveFile(HashMap<String, String> info) throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PATH))) {
            os.writeObject(info);
        }
    }

    public HashMap<String, String> readFile() throws ClassNotFoundException, IOException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH))) {
            return (HashMap<String, String>) is.readObject();
        }
    }

    public boolean fileIsCreated() {
        String currentPath = currentRelativePath.toAbsolutePath().toString();
        PATH = currentPath+PATH;
        File f = new File(PATH);
        if (f.exists()) {
            return true;
        }
        return false;
    }

    public void clear() {
        if (fileIsCreated()) {
            File f = new File(PATH);
            f.delete();
        }
    }

}
