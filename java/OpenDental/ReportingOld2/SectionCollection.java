//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.ReportingOld2;

import CS2JNet.System.StringSupport;
import OpenDental.ReportingOld2.Section;

/**
* Strongly typed collection of Sections.
*/
public class SectionCollection  extends CollectionBase 
{
    /*
    		///<summary>Returns the Section with the given index.</summary>
    		public Section this[int index]{
          get{
    				return((Section)List[index]);
          }
          //set{
    			//	List[index]=value;
          //}
    		}*/
    /**
    * Returns the Section with the given name.
    */
    public Section get___idx(String name) throws Exception {
        for (Object __dummyForeachVar0 : List)
        {
            Section section = (Section)__dummyForeachVar0;
            if (StringSupport.equals(section.getName(), name))
                return section;
             
        }
        return null;
    }

    //set{
    //	List[index]=value;
    //}
    /**
    * Adds the specified section to the collection, but not to the end.  Instead, it inserts it exactly where it belongs based on the type.  The order cannot be changed by the user. Returns the index at which the section has been added, or -1 if not allowed because it already exists.
    */
    public int add(Section value) throws Exception {
        //if(value.Kind==AreaSectionKind.GroupHeader);
        //if(value.Kind==AreaSectionKind.GroupFooter);
        if (List.Count == 0)
        {
            List.Add(value);
            return 0;
        }
         
        for (int i = 0;i < List.Count;i++)
        {
            //we are trying to find the item to insert before
            if (i == List.Count - 1)
            {
                //if last item in list, then only option is to add to end of list
                List.Insert(i, value);
                return i;
            }
             
            if (((Enum)value.getKind()).ordinal() < ((Enum)((Section)List[i]).getKind()).ordinal())
            {
                List.Insert(i, value);
                return i;
            }
             
        }
        return -1;
    }

    /**
    * 
    */
    public int indexOf(Section value) throws Exception {
        return (List.IndexOf(value));
    }

    /**
    * 
    */
    public int indexOf(String name) throws Exception {
        for (Object __dummyForeachVar1 : List)
        {
            Section section = (Section)__dummyForeachVar1;
            if (StringSupport.equals(section.getName(), name))
                return indexOf(section);
             
        }
        return -1;
    }

    /**
    * 
    */
    public boolean contains(String name) throws Exception {
        for (Object __dummyForeachVar2 : List)
        {
            Section section = (Section)__dummyForeachVar2;
            if (StringSupport.equals(section.getName(), name))
                return true;
             
        }
        return false;
    }

}


//<summary></summary>
//public void Insert(int index,Section value){
//	List.Insert(index,value);
//}
/*
		///<summary>Returns the first Section index of the given kind. Since only one of each kind is allowed, this will reliably return the section of interest.</summary>
		public int GetIndexOfKind(AreaSectionKind kind){
			foreach(Section section in List){
				if(section.Kind==kind)
					return IndexOf(section);
			}
			return -1;
		}*/
/*
		///<summary>Returns the first Section of the given kind. Since only one of each kind is allowed, this will reliably return the section of interest.</summary>
		public Section GetOfKind(AreaSectionKind kind){
			foreach(Section section in List){
				if(section.Kind==kind)
					return section;
			}
			return null;
		}*/