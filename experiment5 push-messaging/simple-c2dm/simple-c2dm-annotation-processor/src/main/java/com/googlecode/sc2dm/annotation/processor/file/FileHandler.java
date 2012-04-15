package com.googlecode.sc2dm.annotation.processor.file;

import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 6:25 PM - 11/20/11
 */
public interface FileHandler {
    public boolean isValidFile();
    public List<String> readFile();
    public void writeFile(List<String> text);
}
