package com.fire.processor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Util {

    public static void downloadFileForModis(Period format) throws IOException {
        String srcFileName = "";
        String destFileName = "";
        switch (format) {
            case HOUR24:
                srcFileName = getModis24HFileName();
                destFileName = concatFileFormat(Constants.TEMP_STORAGE, Constants.MODIS_24_HR_FILE_NAME);
                break;
            case HOUR48:
                srcFileName = getModis48HFileName();
                destFileName = concatFileFormat(Constants.TEMP_STORAGE, Constants.MODIS_48_HR_FILE_NAME);
                break;
            case DAY7:
                srcFileName = getModis7DFileName();
                destFileName = concatFileFormat(Constants.TEMP_STORAGE, Constants.MODIS_07_D_FILE_NAME);
                break;
        }
        downloadFile(srcFileName, destFileName);
    }

    public static void downloadFileForViirs(Period format) throws IOException {
        String srcFileName = "";
        String destFileName = "";
        switch (format) {
            case HOUR24:
                srcFileName = getViirs24HFileName();
                destFileName = concatFileFormat(Constants.TEMP_STORAGE, Constants.VIIRS_24_HR_FILE_NAME);
                break;
            case HOUR48:
                srcFileName = getViirs48HFileName();
                destFileName = concatFileFormat(Constants.TEMP_STORAGE, Constants.VIIRS_48_HR_FILE_NAME);
                break;
            case DAY7:
                srcFileName = getViirs7DFileName();
                destFileName = concatFileFormat(Constants.TEMP_STORAGE, Constants.VIIRS_07_D_FILE_NAME);
                break;
        }
        downloadFile(srcFileName, destFileName);
    }

    private static void downloadFile(String fileToDownLoad, String locationToSave) throws IOException {
        URL website = new URL(fileToDownLoad);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream stream = new FileOutputStream((locationToSave));
        stream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        stream.close();
        rbc.close();
    }

    private static String concatFileFormat(String part1, String part2) {
        return part1 + part2;
    }

    private static String getModis24HFileName() {
        return concatFileFormat(Constants.SOURCE_URL, Constants.MODIS_24_HR_FILE_NAME);
    }

    private static String getModis48HFileName() {
        return concatFileFormat(Constants.SOURCE_URL, Constants.MODIS_48_HR_FILE_NAME);
    }

    private static String getModis7DFileName() {
        return concatFileFormat(Constants.SOURCE_URL, Constants.MODIS_07_D_FILE_NAME);
    }

    private static String getViirs24HFileName() {
        return concatFileFormat(Constants.SOURCE_URL, Constants.VIIRS_24_HR_FILE_NAME);
    }

    private static String getViirs48HFileName() {
        return concatFileFormat(Constants.SOURCE_URL, Constants.VIIRS_48_HR_FILE_NAME);
    }

    private static String getViirs7DFileName() {
        return concatFileFormat(Constants.SOURCE_URL, Constants.VIIRS_07_D_FILE_NAME);
    }

    public enum Period {
        HOUR24, HOUR48, DAY7
    }
}
