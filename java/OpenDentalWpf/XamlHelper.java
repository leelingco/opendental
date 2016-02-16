//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:35 PM
//

package OpenDentalWpf;


/**
* Neither of these copy methods work, but leaving this here for later.
*/
public class XamlHelper   
{
}


/*
		public static UIElement DeepCopy(UIElement element){
			string shapestring = XamlWriter.Save(element);   
			StringReader stringReader = new StringReader(shapestring);  
			XmlTextReader xmlTextReader = new XmlTextReader(stringReader);  
			UIElement DeepCopyobject = (UIElement)XamlReader.Load(xmlTextReader);  
			return DeepCopyobject;  
		}
		public static object CopyObject(object obj) { 
			if(obj == null) { 
				return null;
			}
			object result = Activator.CreateInstance(obj.GetType()); 
			foreach(FieldInfo field in obj.GetType().GetFields()) {//could specify some binding flags here. 
				if(field.FieldType.GetInterface("IList",false) == null) { 
					field.SetValue(result,field.GetValue(obj)); 
				} 
				else { 
					IList listObject = (IList)field.GetValue(result); 
					if(listObject != null) { 
						foreach(object item in ((IList)field.GetValue(obj))) { 
							listObject.Add(CopyObject(item)); 
						} 
					} 
				} 
			} 
			return result; 
		}*/