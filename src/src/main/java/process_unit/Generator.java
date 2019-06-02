package process_unit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class Generator {
    String suffix;
    String filePath;
    String fileName;
    public Generator(String filePath,String fileName,String suffix){
        this.filePath=filePath;
        this.fileName=fileName;
        this.suffix=suffix;
    }

    public int encode(byte[] data){
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(this.filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(this.filePath+File.separator+this.fileName+"."+this.suffix);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return 0;



    }
    public int decode(byte[] data){
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(this.filePath);
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在
                dir.mkdirs();
            }
            String[] name=this.fileName.split("\\.");
            StringBuilder origName =new StringBuilder();
            for (int i = 0; i < name.length-1; i++) {
                origName.append(name[i]);
                origName.append(".");
            }
            origName.deleteCharAt(origName.length()-1);
            file = new File(this.filePath+File.separator+origName.toString());
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }


        return 0;
    }
}
