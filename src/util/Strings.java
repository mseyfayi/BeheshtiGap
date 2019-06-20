package util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Strings is used to keeping final strings
 */
public class Strings {
    /**
     * The IP is IP of the Server
     */
    public static final String IP = "localhost";

    /**
     * Gets format to the size
     *
     * @param size given size to be formatted
     * @return formatted size
     */
    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String handleLastSeen(Date lastSeen) {
        Date now = new Date();
        Date date = new Date(now.getTime() - lastSeen.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String makeTimeSimple(Date time){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(time);
    }
}