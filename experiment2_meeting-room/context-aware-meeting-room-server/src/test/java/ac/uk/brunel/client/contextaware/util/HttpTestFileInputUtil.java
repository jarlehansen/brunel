package ac.uk.brunel.client.contextaware.util;

import com.google.protobuf.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Jarle Hansen (hansjar@gmail.com)
 * Date: Feb 15, 2010
 * Time: 8:58:06 PM
 */
public enum HttpTestFileInputUtil {
    ;

    private static final String folderName = "build/temp-files";
    private static final File folder = new File(folderName);
    private static final File testFile = new File(folderName + "/test.txt");

    private static void createTestFile() throws IOException {
        createFolder();
        boolean inFileCreated = testFile.createNewFile();
        System.out.println("Input file created: " + inFileCreated);
    }

    private static void createFolder() {
        boolean folderCreated = folder.mkdirs();
        System.out.println("Folder created: " + folderCreated);
    }

    public static FileInputStream createTestInputStream(final Message protoMessage) throws IOException {
        createTestFile();

        FileOutputStream fileOutput = null;
        try {
            fileOutput = new FileOutputStream(testFile);
            protoMessage.writeTo(fileOutput);
        } catch (IOException io) {
            throw io;
        } finally {
            if (fileOutput != null) {
                fileOutput.close();
            }
        }

        return new FileInputStream(testFile);
    }
}
