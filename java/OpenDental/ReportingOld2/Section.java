//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:24 PM
//

package OpenDental.ReportingOld2;

import OpenDental.ReportingOld2.AreaSectionKind;

/**
* Every ReportObject in a ODReport must be attached to a Section.
*/
public class Section   
{
    /**
    * 
    */
    private String name = new String();
    /**
    * 
    */
    private int height = new int();
    /**
    * Width is usually the entire page unless set differently here.
    */
    private int width = new int();
    /**
    * Specifies which kind, like ReportHeader, or GroupFooter.
    */
    private AreaSectionKind kind = AreaSectionKind.ReportHeader;
    /**
    * 
    */
    public Section(AreaSectionKind myKind, int myHeight) throws Exception {
        kind = myKind;
        //name is not user editable, so:
        switch(kind)
        {
            case ReportHeader: 
                name = "Report Header";
                break;
            case PageHeader: 
                name = "Page Header";
                break;
            case Detail: 
                //case AreaSectionKind.GroupHeader:
                name = "Detail";
                break;
            case PageFooter: 
                //case AreaSectionKind.GroupFooter:
                name = "Page Footer";
                break;
            case ReportFooter: 
                name = "Report Footer";
                break;
        
        }
        height = myHeight;
    }

    /**
    * Not user editable.
    */
    public String getName() throws Exception {
        return name;
    }

    /**
    * 
    */
    public int getHeight() throws Exception {
        return height;
    }

    public void setHeight(int value) throws Exception {
        height = value;
    }

    /**
    * 
    */
    public int getWidth() throws Exception {
        return width;
    }

    public void setWidth(int value) throws Exception {
        width = value;
    }

    /**
    * 
    */
    public AreaSectionKind getKind() throws Exception {
        return kind;
    }

    public void setKind(AreaSectionKind value) throws Exception {
        kind = value;
    }

}


