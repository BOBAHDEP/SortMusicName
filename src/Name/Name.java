package Name;

import ccl.util.FileUtil;
import org.blinkenlights.jid3.*;
import org.blinkenlights.jid3.v1.*;
import org.blinkenlights.jid3.v2.*;
import Exception.MyException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Name {

    private String fileName;
    private String fileExtension;

    public Name(String name){
        this.fileExtension = getFileExtension(name);
        this.fileName = getFileName(name);
    }

    private  static String getFileExtension(String filename){
        int dotPos = filename.lastIndexOf(".") + 1;
        return filename.substring(dotPos);
    }

    private  static String getFileName(String filename){
        return getLastPartOfName(filename, '.' );
    }

    private static String getLastPartOfName(String filename, char letter){
        int dotPos = filename.lastIndexOf(letter);
        if (dotPos == -1) return filename;
        return filename.substring(0,dotPos);
    }

    public void changeName(){
        fileName  =  changeName(fileName);
    }

    public static String changeName(String previousName){
        String result = previousName;
        result = changeName1(result, '-'); // "03 - asd" - > "asd"
        result = changeName1(result, '.'); // "02. Четверо парней" -> "Четверо парней"
        result = changeName1(result, '_'); // "03_asd" - > "asd"
        result = changeName2(result);      // " asd" -> "asd"
        result = changeName3(result);      // "a_s_d" -> "a s d"
        return result;
    }

    public static String changeName1(String previousName, char symbol){  // "1 'Symbol' Name"  -> " Name"
        int index =  previousName.indexOf(symbol) + 1;
        if (previousName.length()-index < 4){
            return previousName;
        }
        if(index <= 0 || index > 5){
            return previousName;
        }
        for(int i = 0; i < index; i++){
            if((previousName.charAt(i) != symbol) && (previousName.charAt(i) != ' ') && !((previousName.charAt(i) >= '0') && (previousName.charAt(i) <= '9'))){
                 return previousName;
            }
        }
        //1_asd -> asd instead -> sd.
        if((previousName.charAt(index) != ' ') && ((previousName.charAt(index) < '0') || (previousName.charAt(index ) > '9'))){
           return previousName.substring(index);
        }
        return previousName.substring(index + 1);
    }

    public  static String changeName2(String previousName){           // " asd" -> "asd"
        for (int i = 0; i < previousName.length(); i++){
            if (previousName.charAt(i) != ' '){
                return previousName.substring(i);
            }
        }
    return "NaN";
    }

    public  static String changeName3(String previousName){           // "a_s_d" -> "a s d"
        return previousName.replace('_',' ');
    }

    public  static String changeName4(String previousName, String containNamePart) throws MyException{  // "Сплин - Двое не спят.mp3"  -> "Двое не спят.mp3"
        /*if (containNamePart.length() > previousName.length()){
            throw new MyException("Files is null changeName4 "+previousName);
        }*/
        if (!previousName.contains(containNamePart)){
            return previousName;
        } else {
            if (previousName.indexOf(containNamePart) == 0){
                for(int i = containNamePart.length(); i < previousName.length(); i++){
                    if ( (previousName.charAt(i) != ' ') && (previousName.charAt(i) != '-')){
                        return previousName.substring(i);
                    }
                }
            }
        }
        return previousName;
    }

    public static void sortID3Tags(File file){
        try{

            MediaFile oMediaFile = new MP3File(file);
            ID3Tag[] aoID3Tag = oMediaFile.getTags();

            ID3V2_3_0Tag oID3V2_3_0Tag = new ID3V2_3_0Tag();

            for (ID3Tag iaoID3Tag : aoID3Tag){
                if (iaoID3Tag instanceof ID3V2_3_0Tag)
                {
                    oID3V2_3_0Tag = (ID3V2_3_0Tag)iaoID3Tag;
                    if (oID3V2_3_0Tag.getTIT2TextInformationFrame() != null){
                        oID3V2_3_0Tag.setTIT2TextInformationFrame(new TIT2TextInformationID3V2Frame (changeName(oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle())));
                        if (oID3V2_3_0Tag.getTIT2TextInformationFrame().getTitle().contains(".")){
                            oID3V2_3_0Tag.setTIT2TextInformationFrame(null);
                        }
                    }
                    if (oID3V2_3_0Tag.getAlbum() != null){
                        oID3V2_3_0Tag.setAlbum(changeName(oID3V2_3_0Tag.getAlbum()));
                        if (oID3V2_3_0Tag.getAlbum().contains(".")){
                            oID3V2_3_0Tag.setAlbum(file.getParentFile().getName());
                        }
                    } else {
                        oID3V2_3_0Tag.setAlbum(file.getParentFile().getName());
                    }
                    if (oID3V2_3_0Tag.getArtist() != null){
                        oID3V2_3_0Tag.setArtist(changeName(oID3V2_3_0Tag.getArtist()));
                        if (oID3V2_3_0Tag.getArtist().contains(".")){
                            oID3V2_3_0Tag.setAlbum(file.getParentFile().getName());
                        }
                    } else {
                        oID3V2_3_0Tag.setAlbum(file.getParentFile().getName());
                    }
                }
            }
            oMediaFile.setID3Tag(oID3V2_3_0Tag);
            oMediaFile.sync();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void copyFile(String fileNameWay, String newFolderWay){
        String newNewFolderName = newFolderWay;// + getLastPartOfName(fileNameWay, '/');
        try{
            FileInputStream fis = new FileInputStream(fileNameWay);
            FileOutputStream fos = new FileOutputStream(newNewFolderName);
            FileChannel fcin = fis.getChannel();
            FileChannel fcout = fos.getChannel();

            // выполнить копирование файла
            fcin.transferTo(0, fcin.size(), fcout);

            // закрываем
            fcin.close();
            fcout.close();
            fis.close();
            fos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void copyFolder(String nameFrom, String nameTo){
        System.out.println(nameFrom + " " + nameTo);
        if (new File(nameFrom).isDirectory()){
            FileUtil.copyDir(nameFrom, nameTo);
        }else {
            try {
                Files.copy(Paths.get(nameFrom), Paths.get(nameTo + "/" + new File(nameFrom).getName()));
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public static void deleteFoldersInside(String folder) throws MyException{
        File file = new File(folder);
        File[] files = file.listFiles();
        if (files == null){throw new MyException("Files is null");}
        for (File f : files){
            FileUtil.deleteRecursively(f.getAbsolutePath());
        }
    }

    @Override
    public String toString (){
        return fileName + " | " + fileExtension;
    }
}
