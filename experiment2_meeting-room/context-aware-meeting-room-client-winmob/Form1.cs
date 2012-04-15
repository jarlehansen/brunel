using System;

using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Net;
using System.IO;
using ProtoBuf;
using System.Net.Sockets;

namespace CAMR_3
{
    public partial class Form1 : Form
    {
        private FileUtil fileUtil;

        public Form1()
        {
            InitializeComponent();
            fileUtil = new FileUtil();
          

        }

        private void button1_Click(object sender, EventArgs e)
        {
            //ConnectToEngine();
            //ConnectToEngineViaTcp();
            connectToEngineStackoverFlow("a", "b", "c");
        }

        private void ConnectToEngineViaTcp()
        {

            //TcpClient client = null;
            TcpClient tcpl = new TcpClient();
            tcpl.Connect("127.0.0.1", 8080);
            //tcpl.Start();
           // client = tcpl.AcceptTcpClient();

           // HttpWebRequest req = (HttpWebRequest)WebRequest.Create("http://localhost:8080/");
          //  req.Method = "POST";
          //  req.GetRequestStream();
            NetworkStream str = tcpl.GetStream();

            ac.uk.brunel.contextaware.Note myNote = new ac.uk.brunel.contextaware.Note();
            myNote.btAddress = "012345678900";

            System.Xml.Serialization.XmlSerializer serial = new System.Xml.Serialization.XmlSerializer(typeof(ac.uk.brunel.contextaware.Note));
            serial.Serialize(str, myNote);
            str.Flush();
            MessageBox.Show("Data Written n wait for Read");

        }

        private void ConnectToEngine()
        {
            try
            {

                //HttpWebRequest req = (HttpWebRequest)WebRequest.Create("http://context-aware-meeting-room.appspot.com/presentationNotes");
                HttpWebRequest req = (HttpWebRequest)WebRequest.Create("http://localhost:8080/");
                req.Method = "POST";
                req.AllowWriteStreamBuffering = true;

                Stream reqStream = req.GetRequestStream();
                //StreamWriter wrtr = new StreamWriter(reqStream);

                ac.uk.brunel.contextaware.Note myNote = new ac.uk.brunel.contextaware.Note();
                myNote.btAddress = "012345678900";
                //wrtr.Write(myNote);

                Serializer.Serialize<ac.uk.brunel.contextaware.Note>(reqStream, myNote);
                //ProtoBuf.




                //mottak må endres

                //   HttpWebRequest req = (HttpWebRequest)WebRequest.Create(downloadUrl);
                //   req.Method = "GET";

                HttpWebResponse resp = (HttpWebResponse)req.GetResponse();

                // Retrieve response stream and wrap in StreamReader
                Stream respStream = resp.GetResponseStream();
                //StreamReader rdr = new StreamReader(respStream);

                ac.uk.brunel.contextaware.Note newNote;
                newNote = Serializer.Deserialize<ac.uk.brunel.contextaware.Note>(respStream);

                string response = "finnished: " + newNote.ToString();

                //string alfa =  "finnished: " + rdr.ToString();

                // loop through response stream reading each line 
                //  and writing to the local file
                // string inLine = rdr.ReadLine();

                //rdr.Close();
                //wrtr.Close();

                textBox1.Text = response;

            }
            catch (Exception ex)
            {

                textBox1.Text = ex.Message;
            }



        }

        private void connectToEngineStackoverFlow(string method, string directory, string data)
        {
            //private string SendData(string method, string directory, string data)
    
        //string page = string.Format("http://{0}/{1}", DeviceAddress, directory);

        ac.uk.brunel.contextaware.Note myNote = new ac.uk.brunel.contextaware.Note();
        myNote.btAddress = "0017833F9DF3"; //win mob sin

        //BHttpWebRequest request = (HttpWebRequest)WebRequest.Create("http://172.16.207.128:8080/"); 
        HttpWebRequest request = (HttpWebRequest)WebRequest.Create("http://context-aware-meeting-room.appspot.com/presentationNotes");
        request.KeepAlive = false;
        request.ProtocolVersion = HttpVersion.Version10;
        request.Method = "POST";
        request.UserAgent = "Windows Mobile";
        request.AllowWriteStreamBuffering = true;


        //ProtoBuf.Serializer.
        // turn our request string into a byte stream
        /*byte[] postBytes;

        if(data != null)
        {
            postBytes = Encoding.UTF8.GetBytes(data);
        }
        else
        {
            postBytes = new byte[0];
        }
         
            


        request.ContentType = "application/x-www-form-urlencoded";
        request.ContentLength = postBytes.Length;*/

        System.IO.Stream requestStream = request.GetRequestStream();

        //Serializer.Serialize<ac.uk.brunel.contextaware.Note>(requestStream, myNote);


           // System.Xml.Serialization.XmlSerializer seria = new System.Xml.Serialization.XmlSerializer(myNote.GetType());
          //  seria.Serialize(requestStream,myNote);

        byte[] postBytes = this.Serialize(myNote);
        // now send it
        requestStream.Write(postBytes, 0, postBytes.Length);
            requestStream.Flush();
            requestStream.Close();
            
          //  textBox1.Text = "i gjennom";

        HttpWebResponse response;

        response = (HttpWebResponse)request.GetResponse();
        textBox1.Text = "i gjennom";
    //    return GetResponseData(response);


        System.IO.Stream responseStream = response.GetResponseStream();
         
            /*int myByte = 0;
            while (myByte != -1)
            {
                myByte = responseStream.ReadByte();
            }
            */
        ac.uk.brunel.contextaware.Note myNoteRec = ProtoBuf.Serializer.Deserialize<ac.uk.brunel.contextaware.Note>(responseStream);
        textBox2.Text = "igjennom 2:  " + myNoteRec.message;

            /*
             *  i første omgang sender du BluetoothAddress
hvis du får tilbake en gyldig meetingid (at den ikke er null eller tom) så sender du også med den i tillegg til btAddress på neste request
             * */


        }

        
        private void mnuExit_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        public byte[] Serialize(ac.uk.brunel.contextaware.Note obj)
        {
            byte[] raw;
            using (MemoryStream memoryStream = new MemoryStream())
            {
                Serializer.Serialize(memoryStream, obj);
                raw = memoryStream.ToArray();
            }

            return raw;
        }

        public ac.uk.brunel.contextaware.Note Deserialize(byte[] serializedType)
        {
            ac.uk.brunel.contextaware.Note obj;
            using (MemoryStream memoryStream = new MemoryStream(serializedType))
            {
                obj = Serializer.Deserialize<ac.uk.brunel.contextaware.Note>(memoryStream);
            }
            return obj;
        }

       

        private void menuItem3_Click(object sender, EventArgs e)
        {
           // FileUtil fileUtil = new FileUtil();
            fileUtil.SaveToFile(textBox2.Text);
            textBox2.Text = "";
        }

        private void menuItem4_Click(object sender, EventArgs e)
        {
           
        }

        private void menuItem5_Click(object sender, EventArgs e)
        {
            
        }

        private void menuItem6_Click(object sender, EventArgs e)
        {
            MeetingHistory mt = new MeetingHistory();
            mt.Show();
        }

        private void mnuPresentation_Click(object sender, EventArgs e)
        {
            //open presentation form
        }

        private void mnuHistory_Click(object sender, EventArgs e)
        {
            //open history form
        }

        private void mnuDeviceInfo_Click(object sender, EventArgs e)
        {
            MessageBox.Show(BluetoothUtil.FindMacAddressInTheHand());
        }

        private void mnuConfig_Click(object sender, EventArgs e)
        {
            //open server config form
        }

       

    }
}