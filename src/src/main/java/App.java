import process_unit.ParameterException;
import process_unit.Parser;

import java.io.File;

public class App {
    public static String[] apart(String wholePath){
        //index0存储路径 index1存储文件名
        String[] arr=new String[2];
        String[] path=wholePath.split("/");
        arr[1] = path[path.length-1];
        if(path.length==1){
            arr[0]=".";
        }else{
            StringBuilder sb =new StringBuilder();
            for (int i = 0; i < path.length-1; i++) {
                sb.append(path[i]+ File.separator);
            }
            sb.deleteCharAt(sb.length()-1);
            arr[0]=sb.toString();
        }

        return arr;

    }
    public static void main(String[] args) throws ParameterException{
        String opt1= args[0];//压缩解压缩 -c -u
        String wholePath= args[args.length-1];//路径


        String opt2= null;//压缩格式，可选
        String opt3= null;//压缩选项 可选
        String opt4= null;//压缩选项 可选



        String filePath=App.apart(wholePath)[0];
        String fileName=App.apart(wholePath)[1];

        Parser parser=new process_unit.Parser(filePath,fileName);

//        System.out.println(args.length);


        if(opt1.equals("-c")){
            try{
                if(args.length==2){
                    parser.parse(0);
                    parser.comParse("whateverULikeIDontCare",0,0);
                }
                if(args.length==3) {
                    parser.parse(0);
                    opt2=args[1];
                    switch (opt2) {
                        case "-d":
                            parser.comParse("deflate",0,0);
                            break;
                        case "-l":
                            parser.comParse("lz4",0,0);
                            break;
                        case "-s":
                            parser.comParse("snappy",0,0);
                            break;
                        case "-r":
                            parser.comParse("whateverULikeIDontCare",1,0);
                            break;
                        case "-t":
                            parser.comParse("whateverULikeIDontCare",0,1);
                            break;
                        default:
                            break;

                    }
                }else if(args.length==4){
                    parser.parse(0);
                    opt2=args[1];
                    opt3=args[2];
                    switch (opt2) {
                        case "-d":
                            if(opt3.equals("-r")){parser.comParse("deflate", 1, 0);}
                            else if(opt3.equals("-t")){parser.comParse("deflate", 0, 1);}
                            break;
                        case "-l":
                            if(opt3.equals("-r")){parser.comParse("lz4", 1, 0);}
                            else if(opt3.equals("-t")){parser.comParse("lz4", 0, 1);}
                            break;
                        case "-s":
                            if(opt3.equals("-r")){parser.comParse("snappy", 1, 0);}
                            else if(opt3.equals("-t")){parser.comParse("snappy", 0, 1);}
                            break;
                        case "-r":
                            parser.comParse("whateverULikeIDontCare",1,1);
                        default:
                            break;
                    }
                }else if(args.length==5){
                    parser.parse(0);
                    opt2=args[1];
                    switch (opt2) {
                        case "-d":
                            parser.comParse("deflate", 1, 1);
                            break;
                        case "-l":
                            parser.comParse("lz4", 1, 1);
                            break;
                        case "-s":
                            parser.comParse("snappy", 1, 1);
                            break;
                        default:
                            break;
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(opt1.equals("-u")){
            try{
                if(args.length==2){
                    parser.uncomParse(0);
                }
                else if(args.length==3){
                    parser.uncomParse(1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(opt1.equals("-p")){
            try {
                parser.parse(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else{
            throw new ParameterException("请选择正确的操作");
        }
    }
}
