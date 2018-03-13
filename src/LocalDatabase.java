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
        System.out.println("CURRENT RELATIVE PATH IS: "+currentPath);;
        PATH = currentPath+PATH;
        File f = new File(PATH);
        if (f.exists()) {
            return true;
        }
        System.out.println("FILE IS "+f.getAbsolutePath());
        return false;
    }

    public void clear() {
        if (fileIsCreated()) {
            File f = new File(PATH);
            f.delete();
        }
    }

//    public static void main(String... args) throws Exception {
//        LocalDatabase xd = new LocalDatabase();
//
//        // Populate and save our HashMap
//        HashMap<String, Integer> users = new HashMap<>();
//        users.put("David Minesote", 11);
//        users.put("Sean Bright", 22);
//        users.put("Tom Overflow", 33);
//
//        xd.saveFile(info);
//
//        // Read our HashMap back into memory and print it out
//        HashMap<String, Integer> restored = xd.readFile();
//
//        System.out.println(restored);
//    }
}
