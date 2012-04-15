package com.googlecode.sc2dm.annotation.processor.file;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:25 PM - 11/20/11
 */
public class FileHandlerImpl implements FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandlerImpl.class);
    private final File file;

    public FileHandlerImpl(File file) {
        this.file = file;
    }

    public boolean isValidFile() {
        return file.isFile();
    }

    public List<String> readFile() {
        List<String> text = new ArrayList<String>();
        try {
            text = FileUtils.readLines(file);
        } catch (IOException io) {
            logger.error("Unable to read file: " + file.getAbsolutePath(), io);
        }

        return text;
    }

    public void writeFile(List<String> text) {
        try {
            FileUtils.writeLines(file, text);
        } catch (IOException io) {
            logger.error("Unable to write file: " + file.getAbsolutePath(), io);
        }
    }
}
