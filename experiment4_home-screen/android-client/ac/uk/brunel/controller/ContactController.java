package ac.uk.brunel.controller;

import android.os.Bundle;
import android.util.Log;

import com.google.gdata.client.*;
import com.google.gdata.client.contacts.*;
import com.google.gdata.data.*;
import com.google.gdata.data.contacts.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.util.*;


import java.io.IOException;
import java.net.URL;

public class ContactController extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.contact);
        super.onCreate(savedInstanceState);

        try {
            Log.i("tmg", "-----> " + IndexController.consumerKey);
            printAllContacts(new ContactsService(IndexController.consumerKey));
        } catch (Exception ex) {
            Log.e("tmg", ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    protected void initComponents() {
        // TODO Auto-generated method stub


    }

    @Override
    protected void initListeners() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void renderView() {
        // TODO Auto-generated method stub


    }
    /*
private static void printContact(ContactEntry contact) {
System.err.println("Id: " + contact.getId());
if (contact.getTitle() != null) {
System.err.println("Contact name: " + contact.getTitle().getPlainText());
} else {
System.err.println("Contact has no name");

}
System.err.println("Last updated: " + contact.getUpdated().toUiString());
if (contact.hasDeleted()) {
System.err.println("Deleted:");
}

ElementHelper.printContact(System.err, contact);

Link photoLink = contact.getLink(
"http://schemas.google.com/contacts/2008/rel#photo", "image/*");
System.err.println("Photo link: " + photoLink.getHref());
String photoEtag = photoLink.getEtag();
System.err.println("  Photo ETag: "
+ (photoEtag != null ? photoEtag : "(No contact photo uploaded)"));
System.err.println("Self link: " + contact.getSelfLink().getHref());
System.err.println("Edit link: " + contact.getEditLink().getHref());
System.err.println("ETag: " + contact.getEtag());
System.err.println("-------------------------------------------\n");
}            */


    //ContactsService myService
    public static void printAllContacts(ContactsService myService)
            throws ServiceException, IOException {
        // Request the feed
        URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/default/full");
        ContactFeed resultFeed = myService.getFeed(feedUrl, ContactFeed.class);
        // Print the results
        Log.i("tmg",resultFeed.getTitle().getPlainText());
        for (int i = 0; i < resultFeed.getEntries().size(); i++) {
            ContactEntry entry = resultFeed.getEntries().get(i);
            if (entry.hasName()) {
                Name name = entry.getName();
                if (name.hasFullName()) {
                    String fullNameToDisplay = name.getFullName().getValue();
                    if (name.getFullName().hasYomi()) {
                        fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
                    }
                    Log.i("tmg","\t\t" + fullNameToDisplay);
                } else {
                    Log.i("tmg","\t\t (no full name found)");
                }
                if (name.hasNamePrefix()) {
                    Log.i("tmg","\t\t" + name.getNamePrefix().getValue());
                } else {
                    Log.i("tmg","\t\t (no name prefix found)");
                }
                if (name.hasGivenName()) {
                    String givenNameToDisplay = name.getGivenName().getValue();
                    if (name.getGivenName().hasYomi()) {
                        givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
                    }
                    Log.i("tmg","\t\t" + givenNameToDisplay);
                } else {
                    Log.i("tmg","\t\t (no given name found)");
                }
                if (name.hasAdditionalName()) {
                    String additionalNameToDisplay = name.getAdditionalName().getValue();
                    if (name.getAdditionalName().hasYomi()) {
                        additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
                    }
                    Log.i("tmg","\t\t" + additionalNameToDisplay);
                } else {
                    Log.i("tmg","\t\t (no additional name found)");
                }
                if (name.hasFamilyName()) {
                    String familyNameToDisplay = name.getFamilyName().getValue();
                    if (name.getFamilyName().hasYomi()) {
                        familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
                    }
                    Log.i("tmg","\t\t" + familyNameToDisplay);
                } else {
                    Log.i("tmg","\t\t (no family name found)");
                }
                if (name.hasNameSuffix()) {
                    Log.i("tmg","\t\t" + name.getNameSuffix().getValue());
                } else {
                    Log.i("tmg","\t\t (no name suffix found)");
                }
            } else {
                Log.i("tmg","\t (no name found)");
            }

            Log.i("tmg","Email addresses:");
            for (Email email : entry.getEmailAddresses()) {
                System.out.print(" " + email.getAddress());
                if (email.getRel() != null) {
                    System.out.print(" rel:" + email.getRel());
                }
                if (email.getLabel() != null) {
                    System.out.print(" label:" + email.getLabel());
                }
                if (email.getPrimary()) {
                    System.out.print(" (primary) ");
                }
                System.out.print("\n");
            }

            Log.i("tmg","IM addresses:");
            for (Im im : entry.getImAddresses()) {
                System.out.print(" " + im.getAddress());
                if (im.getLabel() != null) {
                    System.out.print(" label:" + im.getLabel());
                }
                if (im.getRel() != null) {
                    System.out.print(" rel:" + im.getRel());
                }
                if (im.getProtocol() != null) {
                    System.out.print(" protocol:" + im.getProtocol());
                }
                if (im.getPrimary()) {
                    System.out.print(" (primary) ");
                }
                System.out.print("\n");
            }

            Log.i("tmg","Groups:");
            for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
                String groupHref = group.getHref();
                Log.i("tmg","  Id: " + groupHref);
            }

            Log.i("tmg","Extended Properties:");
            for (ExtendedProperty property : entry.getExtendedProperties()) {
                if (property.getValue() != null) {
                    Log.i("tmg","  " + property.getName() + "(value) = " +
                            property.getValue());
                } else if (property.getXmlBlob() != null) {
                    Log.i("tmg","  " + property.getName() + "(xmlBlob)= " +
                            property.getXmlBlob().getBlob());
                }
            }

            Link photoLink = entry.getContactPhotoLink();
            String photoLinkHref = photoLink.getHref();
            Log.i("tmg","Photo Link: " + photoLinkHref);

            if (photoLink.getEtag() != null) {
                Log.i("tmg","Contact Photo's ETag: " + photoLink.getEtag());
            }

            Log.i("tmg","Contact's ETag: " + entry.getEtag());
        }
    }


}
