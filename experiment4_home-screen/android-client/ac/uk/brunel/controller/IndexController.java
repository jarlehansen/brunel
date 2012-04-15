package ac.uk.brunel.controller;

import ac.uk.brunel.view.TabButton;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class IndexController extends TabActivity {

    private TabHost tabHost;
    private TabHost.TabSpec spec;
    private ArrayList<TabButton> buttonList;

    //start auth attributes
    private String CALLBACKURL = "myapp://mainactivity";
    private CommonsHttpOAuthConsumer httpOauthConsumer;
    private OAuthProvider httpOauthprovider;
    public static String consumerKey = "your key here";
    public static String consumerSecret = "your secret key here";

    // tms
    private CommonsHttpOAuthProvider provider;
    private CommonsHttpOAuthConsumer consumer;

    //end auth attributes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main);
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(null);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        initComponents();
        renderView();
        initListeners();

        authorizeUser();
    }

    /**
     * Opens the browser using signpost jar with application specific
     * consumerkey and consumerSecret.
     */

    private void authorizeUser() {
        try {
            consumer = new CommonsHttpOAuthConsumer("anonymous", "anonymous"); // DefaultOAuthConsumer("anonymous","anonymous");
            // I scope kan vi legge til det vi ¿nsker tilgang til
            String scope = "http://www.google.com/calendar/feeds/brunel.nith%40gmail.com/public/full/"; // http://www.google.com/m8/feeds/"; //"http://www.blogger.com/feeds"; //"http://www.google.com/calendar/feeds/default/allcalendars/full";
            provider = new CommonsHttpOAuthProvider( //DefaultOAuthProvider(
                    "https://www.google.com/accounts/OAuthGetRequestToken?scope="
                            + URLEncoder.encode(scope, "utf-8"),
                    "https://www.google.com/accounts/OAuthGetAccessToken",
                    "https://www.google.com/accounts/OAuthAuthorizeToken?hd=default");

            Log.d("tmg", "Fetching request token...");
            String authUrl = provider.retrieveRequestToken(consumer, CALLBACKURL);// OAuth.OUT_OF_BAND);

            Log.d("tmg", "Request token: " + consumer.getToken());
            Log.d("tmg", "Token secret: " + consumer.getTokenSecret());
            Log.d("tmg", "Now enter:\n" + authUrl + "\n in browser to grant this app authorization");

            this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    //After use authorizes this is the function where we get back callbac with
    //user specific token and secret token. You might want to store this token
    //for future use.

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Uri uri = intent.getData();
        // Check if you got NewIntent event due to Twitter Call back only
        if (uri != null && uri.toString().startsWith(CALLBACKURL)) {

            String verifier = uri.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);

            try {
                // this will populate token and token_secret in consumer
                // httpOauthprovider.retrieveAccessToken(httpOauthConsumer,
                // verifier);
                provider.retrieveAccessToken(consumer, verifier); // httpOauthConsumerWHY
                // NULL
                // HERE????
                // String userKey = httpOauthConsumer.getToken();
                // String userSecret = httpOauthConsumer.getConsumerSecret();

                String userKey = consumer.getToken();
                String userSecret = consumer.getConsumerSecret();
                consumerKey = userKey;
                doCoolStuff();


            } catch (Exception e) {
                Log.d("", e.getMessage());
            }
        }

    }

    private void doCoolStuff() {
        URL url;
        try {
            // contacts
            // url = new URL("http://www.google.com/m8/feeds/calendar/default/owncalendars/full");


            // http://www.google.com/calendar/feeds/brunel.nith%40gmail.com/public/full/
            HttpGet get = new HttpGet("http://www.google.com/calendar/feeds/brunel.nith%40gmail.com/public/full/"); //"http://www.google.com/m8/feeds/contacts/default/full?max-results=1000"); //"http://www.google.com/m8/feeds/contacts/default/full");

            consumer.sign(get);
            HttpClient client = new DefaultHttpClient();

            BasicResponseHandler respHandl = new BasicResponseHandler();
            HttpResponse response = client.execute(get);


            String textanswer = respHandl.handleResponse(response);
            Log.i("tmg", "string lengde: " + textanswer.length());
            Log.i("tmg", textanswer);
            Log.i("tmg", response.getStatusLine().toString());

            Xml.parse(textanswer, new MyXmlHandler());
        } catch (Exception e) {
            Log.e("tmg", e.getMessage(), e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private XMLReader getParser() throws SAXException, ParserConfigurationException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        //xr.setContentHandler(this);
        return xr;
    }

    protected void initComponents() {
        tabHost = getTabHost();
        buttonList = new ArrayList<TabButton>();
    }

    protected void renderView() {

        addTab("apps", "Apps", R.drawable.selector_tab_applications, new Intent().setClass(this, AppDashboardController.class));
        addTab("cal", "Calendar", R.drawable.selector_tab_calendar, new Intent().setClass(this, CalendarController.class));
        addTab("con", "Contacts", R.drawable.selector_tab_contacts, new Intent().setClass(this, ContactController.class));

        tabHost.setCurrentTab(0);
        setFocus(0);
    }

    protected void initListeners() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            public void onTabChanged(String tabId) {
                int index = tabHost.getCurrentTab();
                setFocus(index);

            }
        });
    }


    private void addTab(String tag, String indicator, int resourceId, Intent intent) {
        TabButton tabButton = new TabButton(this, resourceId, indicator);
        buttonList.add(tabButton);
        spec = tabHost.newTabSpec(tag);
        spec.setContent(intent);
        spec.setIndicator(tabButton);
        tabHost.addTab(spec);
    }

    private void setFocus(int index) {

        TabButton tabButton = null;
        for (int i = 0; i < buttonList.size(); i++) {
            tabButton = buttonList.get(i);
            if (i == index) {
                tabButton.setBackground(true);
            } else {
                tabButton.setBackground(false);
            }
        }
    }

}
