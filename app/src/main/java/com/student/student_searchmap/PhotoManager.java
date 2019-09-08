package com.student.student_searchmap;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import net.bither.util.CompressTools;
import net.bither.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import static net.bither.util.FileUtil.renameFile;
import static net.bither.util.FileUtil.getReadableFileSize;

public class PhotoManager {
    private static final String TAG = "PhotoManager";
    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    public static  File newFile;
    public static  Uri uri =null;
    public static void compression(Context context ,File filePath){
        CompressTools.getInstance(context).compressToFile(filePath, new CompressTools.OnCompressListener()
        {
            @Override
            public void onStart()
            {

            }
            @Override
            public void onFail(String error)
            {
                Log.d(TAG, "onFail: "+error.toString());

            }
            @Override
            public void onSuccess(File file)
            {
                Log.d(TAG, "onSuccess: ");
                Log.d(TAG, "onSuccess: "+file.getPath().toString());
                Log.d(TAG, "onSuccess: "+"Size : %s"+getReadableFileSize(file.length()));

                            }
        });
    }
    public static File getTempFile(Context context, Uri uri) throws IOException
    {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        String fileName = FileUtil.getFileName(context, uri);
        String[] splitName = FileUtil.splitFileName(fileName);
        File tempFile = File.createTempFile(splitName[0], splitName[1]);
        tempFile = renameFile(tempFile, fileName);
        tempFile.deleteOnExit();
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(tempFile);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        if (inputStream != null)
        {
            copy(inputStream, out);
            inputStream.close();
        }

        if (out != null)
        {
            out.close();
        }
        return tempFile;
    }


    static int copy(InputStream input, OutputStream output) throws IOException
    {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE)
        {
            return -1;
        }
        return (int) count;
    }

    static long copyLarge(InputStream input, OutputStream output) throws IOException
    {
        return copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
    }


    static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException
    {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer)))
        {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static Uri getFilePath(String path){


        return   Uri.fromFile(new File(path));
    }
}
