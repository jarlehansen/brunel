package ac.uk.brunel.controller;

import android.util.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jarle Hansen (jarle@jarlehansen.net)
 * Created: 2:43 PM - 12/12/10
 */
public class MyXmlHandler extends DefaultHandler {
    private final List<String> stringList = new ArrayList<String>();

    public void startDocument() throws SAXException {
        
    }

    public void endDocument() throws SAXException {
        for(String s : stringList) {
            Log.i("tmg", s);
        }
        
        Log.i("tmg", "Length = " + stringList.size());
    }

    @Override
    public void startElement(String s, String s1, String s2, Attributes attributes) throws SAXException {
    }

    @Override
    public void endElement(String s, String s1, String s2) throws SAXException {
    }

    @Override
    public void characters(char[] chars, int i, int i1) throws SAXException {
        stringList.add(new String(chars));
    }


}
