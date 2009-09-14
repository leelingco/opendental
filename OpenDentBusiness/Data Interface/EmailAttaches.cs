using System;
using System.Collections;
using System.Data;
using System.Reflection;

namespace OpenDentBusiness{
	///<summary></summary>
	public class EmailAttaches{

		public static long Insert(EmailAttach attach) {
			if(RemotingClient.RemotingRole==RemotingRole.ClientWeb) {
				attach.EmailAttachNum=Meth.GetInt(MethodBase.GetCurrentMethod(),attach);
				return attach.EmailAttachNum;
			}
			if(PrefC.RandomKeys) {
				attach.EmailAttachNum=ReplicationServers.GetKey("emailattach","EmailAttachNum");
			}
			string command= "INSERT INTO emailattach (";
			if(PrefC.RandomKeys) {
				command+="EmailAttachNum,";
			}
			command+="EmailMessageNum, DisplayedFileName, ActualFileName) VALUES(";
			if(PrefC.RandomKeys) {
				command+="'"+POut.PLong(attach.EmailAttachNum)+"', ";
			}
			command+=
				 "'"+POut.PLong(attach.EmailMessageNum)+"', "
				+"'"+POut.PString(attach.DisplayedFileName)+"', "
				+"'"+POut.PString(attach.ActualFileName)+"')";
			if(PrefC.RandomKeys) {
				Db.NonQ(command);
			}
			else {
				attach.EmailAttachNum=Db.NonQ(command,true);
			}
			return attach.EmailAttachNum;
		}


	}

	


}









