﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Text;
using System.Security.Cryptography;
using System.Threading;
using WebForms;

namespace MobileWeb {
	public partial class ProcessLogin:System.Web.UI.Page {
		private Util util=new Util();

		protected void Page_Load(object sender,EventArgs e) {
			String username="";
			String password="";
			Message.Text="";
				if(Request.Form["username"]!=null) {
					username=Request.Form["username"].ToString().Trim();
				}
				if(Request.Form["password"]!=null) {
					password=Request.Form["password"].ToString().Trim();
				}
				if(username=="" && password=="") {
					Message.Text="CorrectLogin";
					Session["CustomerNum"]=1486;
					util.SetMobileDbConnection();
				}
				else {
					Message.Text="LoginFailed";
				}
		}
		
		//Dennis: don't see the point in adding a 'salt' for now
		public static string MD5Encrypt(string data) {

			MD5CryptoServiceProvider md5 = new MD5CryptoServiceProvider();

			byte[] result = md5.ComputeHash(Encoding.UTF8.GetBytes(data));

			return Encoding.UTF8.GetString(result);

		}




	}
}