//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:31 PM
//

package OpenDental.Eclaims;

import CS2JNet.System.LCC.Disposable;
import CS2JNet.System.StringSupport;
import OpenDental.com.dentalxchange.webservices.Credentials;
import OpenDental.com.dentalxchange.webservices.Request;
import OpenDental.com.dentalxchange.webservices.Response;
import OpenDental.com.dentalxchange.webservices.WebServiceService;
import OpenDental.Eclaims.x837Controller;
import OpenDentBusiness.Clearinghouse;
import OpenDentBusiness.PrefC;
import OpenDentBusiness.PrefName;

/**
* aka ClaimConnect.
*/
public class ClaimConnect   
{
    /**
    * 
    */
    public ClaimConnect() throws Exception {
    }

    /**
    * Returns true if the communications were successful, and false if they failed. If they failed, a rollback will happen automatically by deleting the previously created X12 file. The batchnum is supplied for the possible rollback.
    */
    public static boolean launch(Clearinghouse clearhouse, int batchNum) throws Exception {
        try
        {
            //Step 1: Post authentication request:
            Version myVersion = new Version(Application.ProductVersion);
            HttpWebRequest webReq = new HttpWebRequest();
            WebResponse response = new WebResponse();
            StreamReader readStream = new StreamReader();
            String str = new String();
            String[] responseParams = new String[]();
            String status = "";
            String group = "";
            String userid = "";
            String authid = "";
            String errormsg = "";
            String alertmsg = "";
            String curParam = "";
            String serverName = "https://claimconnect.dentalxchange.com/dci/upload.svl";
            //"https://prelive.dentalxchange.com/dci/upload.svl";
            webReq = (HttpWebRequest)WebRequest.Create(serverName);
            //CONSTANT; signifies that this is an authentication request
            //CONSTANT; file format
            //CONSTANT
            String postData = "Function=Auth" + "&Source=EDI" + "&Username=" + clearhouse.LoginID + "&Password=" + clearhouse.Password + "&UploaderName=OpenDental" + "&UploaderVersion=" + myVersion.Major.ToString() + "." + myVersion.Minor.ToString() + "." + myVersion.Build.ToString();
            //eg 12.3.24
            webReq.KeepAlive = false;
            webReq.Method = "POST";
            webReq.ContentType = "application/x-www-form-urlencoded";
            webReq.ContentLength = postData.Length;
            ASCIIEncoding encoding = new ASCIIEncoding();
            byte[] bytes = encoding.GetBytes(postData);
            Stream streamOut = webReq.GetRequestStream();
            streamOut.Write(bytes, 0, bytes.Length);
            streamOut.Close();
            response = webReq.GetResponse();
            //Process the authentication response:
            readStream = new StreamReader(response.GetResponseStream(), Encoding.ASCII);
            str = readStream.ReadToEnd();
            readStream.Close();
            //MessageBox.Show(str);
            responseParams = str.Split('&');
            for (int i = 0;i < responseParams.Length;i++)
            {
                curParam = GetParam(responseParams[i]);
                System.String __dummyScrutVar0 = curParam;
                if (__dummyScrutVar0.equals("Status"))
                {
                    status = GetParamValue(responseParams[i]);
                }
                else if (__dummyScrutVar0.equals("GROUP"))
                {
                    group = GetParamValue(responseParams[i]);
                }
                else if (__dummyScrutVar0.equals("UserID"))
                {
                    userid = GetParamValue(responseParams[i]);
                }
                else if (__dummyScrutVar0.equals("AuthenticationID"))
                {
                    authid = GetParamValue(responseParams[i]);
                }
                else if (__dummyScrutVar0.equals("ErrorMessage"))
                {
                    errormsg = GetParamValue(responseParams[i]);
                }
                else if (__dummyScrutVar0.equals("AlertMessage"))
                {
                    alertmsg = GetParamValue(responseParams[i]);
                }
                else
                {
                    throw new Exception("Unexpected parameter: " + curParam);
                }      
            }
            //Process response for errors:
            if (!StringSupport.equals(alertmsg, ""))
            {
                MessageBox.Show(alertmsg);
            }
             
            System.String __dummyScrutVar1 = status;
            if (__dummyScrutVar1.equals("0"))
            {
            }
            else //MessageBox.Show("Authentication successful.");
            if (__dummyScrutVar1.equals("1"))
            {
                throw new Exception("Authentication failure.  Please verify your login ID and password by visiting\r\nSetup | Clearinghouses | ClaimConnect.  " + "These values are probably the same as your dentalxchange log in.  Error message: " + errormsg);
            }
            else if (__dummyScrutVar1.equals("2"))
            {
                throw new Exception("Cannot authenticate at this time. " + errormsg);
            }
            else if (__dummyScrutVar1.equals("3"))
            {
                throw new Exception("Invalid authentication request. " + errormsg);
            }
            else if (__dummyScrutVar1.equals("4"))
            {
                throw new Exception("Invalid program version. " + errormsg);
            }
            else if (__dummyScrutVar1.equals("5"))
            {
                throw new Exception("No customer contract. " + errormsg);
            }
                  
            //Step 2: Post upload request:
            String[] fileNames = Directory.GetFiles(clearhouse.ExportPath);
            if (fileNames.Length > 1)
            {
                for (int f = 0;f < fileNames.Length;f++)
                {
                    File.Delete(fileNames[f]);
                }
                throw new ApplicationException("A previous batch submission was found in an incomplete state.  You will need to resubmit your most recent batch as well as this batch.  Also check reports to be certain that all expected claims went through.");
            }
             
            String fileName = fileNames[0];
            String boundary = "------------7d13e425b00d0";
            postData = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"Function\"\r\n" + "\r\n" + "Upload\r\n" + "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"Source\"\r\n" + "\r\n" + "EDI\r\n" + "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"AuthenticationID\"\r\n" + "\r\n" + authid + "\r\n" + "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"File\"; filename=\"" + fileName + "\"\r\n" + "Content-Type: text/plain\r\n" + "\r\n";
            StreamReader sr = new StreamReader(fileName);
            try
            {
                {
                    postData += sr.ReadToEnd() + "\r\n" + "--" + boundary + "--";
                }
            }
            finally
            {
                if (sr != null)
                    Disposable.mkDisposable(sr).dispose();
                 
            }
            //Debug.Write(postData);
            //MessageBox.Show(postData);
            webReq = (HttpWebRequest)WebRequest.Create(serverName);
            webReq.KeepAlive = false;
            webReq.Method = "POST";
            webReq.ContentType = "multipart/form-data; boundary=" + boundary;
            webReq.ContentLength = postData.Length;
            bytes = encoding.GetBytes(postData);
            streamOut = webReq.GetRequestStream();
            streamOut.Write(bytes, 0, bytes.Length);
            streamOut.Close();
            response = webReq.GetResponse();
            //Process the response
            readStream = new StreamReader(response.GetResponseStream(), Encoding.ASCII);
            str = readStream.ReadToEnd();
            readStream.Close();
            errormsg = "";
            status = "";
            str = str.Replace("\r\n", "");
            Debug.Write(str);
            if (str.Length > 300)
            {
                throw new Exception("Unknown lengthy error message received.");
            }
             
            responseParams = str.Split('&');
            for (int i = 0;i < responseParams.Length;i++)
            {
                curParam = GetParam(responseParams[i]);
                System.String __dummyScrutVar2 = curParam;
                if (__dummyScrutVar2.equals("Status"))
                {
                    status = GetParamValue(responseParams[i]);
                }
                else if (__dummyScrutVar2.equals("ErrorMessage"))
                {
                    errormsg = GetParamValue(responseParams[i]);
                }
                else if (__dummyScrutVar2.equals("Filename") || __dummyScrutVar2.equals("Timestamp"))
                {
                }
                else if (__dummyScrutVar2.equals(""))
                {
                }
                else
                {
                    throw new Exception("Unexpected parameter: " + curParam + "*");
                }    
            }
            //errorMessage blank
            System.String __dummyScrutVar3 = status;
            if (__dummyScrutVar3.equals("0"))
            {
                MessageBox.Show("Upload successful.");
            }
            else if (__dummyScrutVar3.equals("1"))
            {
                throw new Exception("Authentication failure.  Please verify your login ID and password by visiting\r\nSetup | Clearinghouses | ClaimConnect.  " + "These values are probably the same as your dentalxchange log in.  Error message: " + errormsg);
            }
            else if (__dummyScrutVar3.equals("2"))
            {
                throw new Exception("Cannot upload at this time. " + errormsg);
            }
               
            //delete the uploaded claim
            File.Delete(fileName);
        }
        catch (Exception e)
        {
            MessageBox.Show(e.Message);
            x837Controller.rollback(clearhouse,batchNum);
            return false;
        }

        return true;
    }

    private static String getParam(String paramAndValue) throws Exception {
        if (StringSupport.equals(paramAndValue, ""))
        {
            return "";
        }
         
        String[] pair = paramAndValue.Split('=');
        return pair[0];
    }

    //if(pair.Length!=2){
    //	throw new Exception("Unexpected parameter from server: "+paramAndValue);
    private static String getParamValue(String paramAndValue) throws Exception {
        if (StringSupport.equals(paramAndValue, ""))
        {
            return "";
        }
         
        String[] pair = paramAndValue.Split('=');
        //if(pair.Length!=2){
        //	throw new Exception("Unexpected parameter from server: "+paramAndValue);
        //}
        if (pair.Length == 1)
        {
            return "";
        }
         
        return pair[1];
    }

    public static String benefits270(Clearinghouse clearhouse, String x12message) throws Exception {
        Credentials cred = new Credentials();
        if (PrefC.getBool(PrefName.CustomizedForPracticeWeb))
        {
            //even though they currently use code from a different part of the program.
            cred.setclient("Practice-Web");
            cred.setserviceID("DCI Web Service ID: 001513");
        }
        else
        {
            cred.setclient("OpenDental");
            cred.setserviceID("DCI Web Service ID: 002778");
        } 
        cred.setversion(Application.ProductVersion);
        cred.setusername(clearhouse.LoginID);
        cred.setpassword(clearhouse.Password);
        Request request = new Request();
        request.setcontent(HttpUtility.HtmlEncode(x12message));
        //get rid of ampersands, etc.
        WebServiceService service = new WebServiceService();
        service.setUrl("https://webservices.dentalxchange.com/dws/services/dciservice.svl");
        //always use production. So I don't forget
        String strResponse = "";
        try
        {
            Response response = service.lookupEligibility(cred,request);
            strResponse = response.getcontent();
        }
        catch (SoapException ex)
        {
            strResponse = "If this is a new customer, this error might be due to an invalid Username or Password.  Servers may need a few hours before ready to accept new user information.\r\n" + "Error message received directly from Claim Connect:  " + ex.Message + "\r\n\r\n" + ex.Detail.InnerText;
        }

        //cleanup response.  Seems to start with \n and 4 spaces.  Ends with trailing \n.
        strResponse = strResponse.TrimStart('\n');
        //strResponse.Replace("\n","");
        strResponse = strResponse.TrimStart(' ');
        return strResponse;
    }

}


//CodeBase.MsgBoxCopyPaste msgbox=new CodeBase.MsgBoxCopyPaste(response.content);
//msgbox.ShowDialog();
/*
			string strRawResponse="";
			string strRawResponseNormal="ISA*00*          *00*          *30*330989922      *29*AA0989922      *030606*0936*U*00401*000013966*0*T*:~GS*HB*330989922*AA0989922*20030606*0936*13966*X*004010X092~ST*271*0001~BHT*0022*11*ASX012145WEB*20030606*0936~HL*1**20*1~NM1*PR*2*ACME INC*****PI*12345~HL*2*1*21*1~NM1*1P*1*PROVLAST*PROVFIRST****SV*5558006~HL*3*2*22*0~TRN*2*100*1330989922~NM1*IL*1*SMITH*JOHN*B***MI*123456789~REF*6P*XYZ123*GROUPNAME~REF*18*2484568*TEST PLAN NAME~N3*29 FREMONT ST*~N4*PEACE*NY*10023~DMG*D8*19570515*M~DTP*307*RD8*19910712-19920525~EB*1*FAM*30~SE*17*0001~GE*1*13966~IEA*1*000013966~";
			string strRawResponseFailureAuth=@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"">
	<soapenv:Body>
		<soapenv:Fault>
			<faultcode>soapenv:Server.userException</faultcode>
			<faultstring>Authentication failed.</faultstring>
			<faultactor/>
			<detail>
				<string>Authentication failed.</string>
			</detail>
		</soapenv:Fault>
	</soapenv:Body>
</soapenv:Envelope>";
			string strRawResponseFailure997=@"<?xml version=""1.0"" encoding=""UTF-8""?>
<soapenv:Envelope xmlns:soapenv=""http://schemas.xmlsoap.org/soap/envelope/"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"" xmlns:xsi=""http://www.w3.org/2001/XMLSchema-instance"">
	<soapenv:Body>
		<soapenv:Fault>
			<faultcode>soapenv:Server.userException</faultcode>
			<faultstring>Malformed document sent. Please insure that the format is correct and all required data is present.</faultstring>
			<faultactor/>
			<detail>
				<string>ISA*00*          *00*          *30*330989922      *30*BB0989922      *030606*1351*U*00401*000014066*0*T*:~GS*FA*330989922**20030606*1351*14066*X*004010~ST*997*0001~AK1*HR*100~AK2*276*0001~AK3*DMG*10**8~AK4*2**8*20041210~AK5*R*5~AK9*R*0*0*0*3~SE*8*0001~GE*1*14066~IEA*1*000014066~</string>
			</detail>
		</soapenv:Fault>
	</soapenv:Body>
</soapenv:Envelope>";
			return strRawResponseNormal;*/
/*
			XmlDocument doc=new XmlDocument();
			doc.LoadXml(strRawResponse);
			//StringReader strReader=new StringReader(strRawResponseNormal);
			//XmlReader xmlReader=XmlReader.Create(strReader);
			//xmlReader...MoveToElement(
			XmlNode node=doc.SelectSingleNode("//lookupEligibilityReturn");
			if(node!=null) {
				return node.InnerText;//271
			}
			node=doc.SelectSingleNode("//detail/string");
			if(node==null) {
				throw new ApplicationException("Returned data not in expected format: "+strRawResponse);
			}
			if(node.InnerText=="Authentication failed.") {
				throw new ApplicationException("Authentication failed.");
			}
			return node.InnerText;//997
			*/
/*
	[System.Web.Services.WebServiceBindingAttribute(Name="MyMathSoap",Namespace="http://www.contoso.com/")]
	public class MyMath:System.Web.Services.Protocols.SoapHttpClientProtocol {
		
		[System.Diagnostics.DebuggerStepThroughAttribute()]
		public MyMath() {
			this.Url = "http://www.contoso.com/math.asmx";
		}
		[System.Diagnostics.DebuggerStepThroughAttribute()]
		[System.Web.Services.Protocols.SoapDocumentMethodAttribute("http://www.contoso.com/Add",RequestNamespace="http://www.contoso.com/",ResponseNamespace="http://www.contoso.com/",Use=System.Web.Services.Description.SoapBindingUse.Literal,ParameterStyle=System.Web.Services.Protocols.SoapParameterStyle.Wrapped)]
		public int Add(int num1,int num2) {
			object[] results = this.Invoke("Add",new object[] {num1,
                        num2});
			return ((int)(results[0]));
		}
		[System.Diagnostics.DebuggerStepThroughAttribute()]
		public System.IAsyncResult BeginAdd(int num1,int num2,System.AsyncCallback callback,object asyncState) {
			return this.BeginInvoke("Add",new object[] {num1,
                        num2},callback,asyncState);
		}
		[System.Diagnostics.DebuggerStepThroughAttribute()]
		public int EndAdd(System.IAsyncResult asyncResult) {
			object[] results = this.EndInvoke(asyncResult);
			return ((int)(results[0]));
		}
	}*/