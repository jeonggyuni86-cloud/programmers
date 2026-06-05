import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AccountBookImpl implements AccountBook{
    final private File folder = new File("accountbook");

    AccountBookImpl() {
        if(!folder.exists()) folder.mkdir();
    }
    @Override
    public boolean addAccount(String date, Item item) {
        File file = new File(folder, date + ".txt");
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            try (FileWriter fw = new FileWriter(file, true)) {
                fw.write(item.toString() + "\n");
            }
            return true;
        } catch (Exception e ) {
            return false;
        }
    }

    @Override
    public String[] showDate() {
        final File[] files = getFiles();
        final List<String> dates = new ArrayList<>();
        int idx = 0;
        for(File file : files) {
            String str = file.getName().replace(".txt", "");
            if(str.startsWith(".")) continue;
            System.out.println(++idx + ". " + str);
            dates.add(str);
        }
        return dates.toArray(String[]::new);
    }

    @Override
    public String[] showAccount(String[] dates, int idx) {
        File file = new File(folder, dates[idx - 1] + ".txt");
        final List<String> list = new ArrayList<>();
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            try (Scanner sc = new Scanner(file)) {
                for(int i = 1; sc.hasNextLine(); i++) {
                    String str = sc.nextLine();
                    System.out.println(i + ". " + str);
                    list.add(str);
                }
            }
        } catch (IOException e) {
            return new String[0];
        }
        return list.toArray(String[]::new);
    }

    @Override
    public boolean deleteAccount(String[] dates, int dateIdx, String[] account, int idx) {
        final File file = new File(folder, dates[dateIdx - 1] + ".txt");

        List<String> list = new ArrayList<>(Arrays.asList(account));
        list.remove(idx - 1);

        try (FileWriter fw = new FileWriter(file, false)) {
            for (String str : list) {
                fw.write(str + "\n");
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean deleteFile(String[] dates, int dateIdx) {
        File file = new File(folder,dates[dateIdx - 1] + ".txt");
        return file.delete();
    }

    private File[] getFiles() {
        File[] files = folder.listFiles();
        return files == null ? new File[0] : files;
    }
}
