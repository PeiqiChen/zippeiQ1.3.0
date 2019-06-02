package algorithm_unit;

import java.io.IOException;
import org.xerial.snappy.Snappy;

public class Zippy {
    public static byte[] compress(byte srcBytes[]) throws IOException {
        return  Snappy.compress(srcBytes);
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        return Snappy.uncompress(bytes);
    }
}