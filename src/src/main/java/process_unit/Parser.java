package process_unit;

import algorithm_unit.Deflate;
import algorithm_unit.LZ4;
import algorithm_unit.Zippy;
import process_unit.Generator;
import process_unit.ParameterException;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;


public class Parser {
    Generator generator=null;
    String filePath;
    String fileName;
    String suggest_type;
    int times=2000;//压缩次数
    public Parser(String filePath,String fileName){
        this.filePath=filePath;
        this.fileName=fileName;
    }


    public void parse(int show) throws Exception {

        FileInputStream fis = new FileInputStream(new File(this.filePath+File.separator+this.fileName));
        FileChannel channel = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate((int) channel.size());
        channel.read(bb);
        byte[] beforeBytes = bb.array();

        if(show==1){System.out.println("文件大小：" + beforeBytes.length + " bytes，压缩次数："+times);}

        if(show==1) {System.out.println("Deflate:");}
        long startTime1 = System.currentTimeMillis();
        byte[] afterBytesD = null;
        for (int i = 0; i < times; i++) {
            afterBytesD = Deflate.compress(beforeBytes);
        }
        long endTime1 = System.currentTimeMillis();
        double rateD=(beforeBytes.length-afterBytesD.length)*100/beforeBytes.length;
        if(show==1) {
            System.out.println("压缩率：" + rateD + "%");
            System.out.println("压缩时间：" + (endTime1 - startTime1)
                    + "ms");

            System.out.println("LZ4:");
        }
        startTime1 = System.currentTimeMillis();
        byte[] afterBytesL = null;
        for (int i = 0; i < times; i++) {
            afterBytesL = LZ4.compress(beforeBytes);
        }
        endTime1 = System.currentTimeMillis();
        double rateL=(beforeBytes.length-afterBytesL.length)*100/beforeBytes.length;
        if(show==1) {
            System.out.println("压缩率：" + rateL + "%");
            System.out.println("压缩时间：" + (endTime1 - startTime1)
                    + "ms");

            System.out.println("Snappy:");
        }
        startTime1 = System.currentTimeMillis();
        byte[] afterBytesS = null;
        for (int i = 0; i < times; i++) {
            afterBytesS = Zippy.compress(beforeBytes);
        }
        endTime1 = System.currentTimeMillis();
        double rateS=(beforeBytes.length-afterBytesS.length)*100/beforeBytes.length;
        if(show==1) {
            System.out.println("压缩率：" + rateS + "%");
            System.out.println("压缩时间：" + (endTime1 - startTime1)
                    + "ms");
        }

        //哪个afterbyte最小
        double minRate = (rateD < rateL) ? rateD : rateL;
        minRate = (minRate < rateS) ? minRate : rateS;
        String type;
        if(rateD==minRate){
            this.suggest_type="deflate";
        }else if(rateL==minRate){
            this.suggest_type="lz4";
        }else if(rateS==minRate){
            this.suggest_type="snappy";
        }else{
            this.suggest_type="";
        }
        if(show==1){System.out.println("\n建议用."+this.suggest_type+"格式压缩此文件\n\n");}
    }


    public void comParse(String type,int flagR, int flagT) throws Exception{
        FileInputStream fis = new FileInputStream(new File(this.filePath+File.separator+this.fileName));
        FileChannel channel = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate((int) channel.size());
        channel.read(bb);
        byte[] beforeBytes = bb.array();


        long startTime1=0,endTime1=0;
        startTime1 = System.currentTimeMillis();
        byte[] afterBytes = null;
        if(type!= "deflate" && type!= "lz4"&& type!= "snappy"){
            type=this.suggest_type;
            System.out.println("正在用建议格式."+type+"格式压缩此文件...");
        }else{
            System.out.println("正在用."+type+"格式压缩此文件...");
        }
        switch(type){
            case "deflate":
                startTime1 = System.currentTimeMillis();
                for (int i = 0; i < times; i++) {
                    afterBytes = Deflate.compress(beforeBytes);
                }
                endTime1 = System.currentTimeMillis();
                break;
            case "lz4":
                startTime1 = System.currentTimeMillis();
                for (int i = 0; i < times; i++) {
                    afterBytes = LZ4.compress(beforeBytes);
                }
                endTime1 = System.currentTimeMillis();
                break;
            case "snappy":
                startTime1 = System.currentTimeMillis();
                for (int i = 0; i < times; i++) {
                    afterBytes = Zippy.compress(beforeBytes);
                }
                endTime1 = System.currentTimeMillis();
                break;
            default:
                break;
        }
        int flag=0;
        this.generator=new Generator(this.filePath,this.fileName,type);
        flag=this.generator.encode(afterBytes);
        if(flag==1){
            System.out.println("成功压缩文件"+fileName+"为"+fileName+"."+type);
            double rate=(beforeBytes.length-afterBytes.length)*100/beforeBytes.length;
            if(flagR==1){System.out.println("压缩率："+rate+"%");}
            if(flagT==1){System.out.println("压缩时间：" + (endTime1 - startTime1)
                    + "ms");}

        }else{
            System.out.println("压缩失败，请重新尝试...");
        }

    }

    public void uncomParse(int flagT) throws Exception {


        FileInputStream fis = new FileInputStream(new File(this.filePath+File.separator+this.fileName));
        FileChannel channel = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate((int) channel.size());
        channel.read(bb);
        byte[] dataBytes = bb.array();

        byte[] resultBytes = null;
        long startTime2=0,endTime2=0;

        String[] temp=fileName.split("\\.");
        String type=temp[temp.length-1];
        switch(type){
            case "deflate":
                startTime2 = System.currentTimeMillis();
                for (int i = 0; i < times; i++) {
                    resultBytes = Deflate.uncompress(dataBytes);
                }
                endTime2 = System.currentTimeMillis();
                break;
            case "lz4":
                startTime2 = System.currentTimeMillis();
                for (int i = 0; i < times; i++) {
                    resultBytes = LZ4.uncompress(dataBytes);
                }
                endTime2 = System.currentTimeMillis();
                break;
            case "snappy":
                startTime2 = System.currentTimeMillis();
                for (int i = 0; i < times; i++) {
                    resultBytes = Zippy.uncompress(dataBytes);
                }
                endTime2 = System.currentTimeMillis();
                break;
                default:
                    throw new ParameterException("未知格式文件");
        }
        System.out.println("正在解压"+this.fileName+"...");
        this.generator= new Generator(this.filePath,this.fileName,type);
        int flag =this.generator.decode(resultBytes);
        if(flag==1){
            System.out.println("解压成功\n解压缩后大小：" + resultBytes.length + " bytes");
            if(flagT==1){System.out.println("解压缩次数：" + times + "，时间：" + (endTime2 - startTime2)
                    + "ms");}
        }else{
            throw new ParameterException("解压失败");
        }
    }


}
