//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:30 PM
//

package OpenDental;

import CS2JNet.System.StringSupport;
import OpenDental.UI.ODGridColumn;
import OpenDentBusiness.Lans;

/**
* Lan is short for language.  Used to translate text to another language.
*/
public class Lan   
{
    //<summary>stub</summary>
    //private static bool itemInserted;
    //strings-----------------------------------------------
    /**
    * Converts a string to the current language.
    */
    public static String g(String classType, String text) throws Exception {
        String retVal = Lans.convertString(classType,text);
        return retVal;
    }

    //if(itemInserted) {
    //	Lans.RefreshCache();
    //}
    /**
    * Converts a string to the current language.
    */
    public static String g(System.Object sender, String text) throws Exception {
        String retVal = Lans.ConvertString(sender.GetType().Name, text);
        return retVal;
    }

    //if(itemInserted) {
    //	Lans.RefreshCache();
    //}
    //menuItems---------------------------------------------
    /**
    * C is for control. Translates the text of this control to another language.
    */
    public static void c(String classType, System.Windows.Forms.MenuItem mi) throws Exception {
        mi.Text = Lans.ConvertString(classType, mi.Text);
    }

    //if(itemInserted) {
    //	Lans.RefreshCache();
    //}
    /**
    * 
    */
    public static void c(System.Windows.Forms.Control sender, System.Windows.Forms.MenuItem mi) throws Exception {
        mi.Text = Lans.ConvertString(sender.GetType().Name, mi.Text);
    }

    //if(itemInserted) {
    //	Lans.RefreshCache();
    //}
    //controls-----------------------------------------------
    /**
    * 
    */
    public static void c(String classType, System.Windows.Forms.Control[] contr) throws Exception {
        for (int i = 0;i < contr.Length;i++)
        {
            contr[i].Text = Lans.ConvertString(classType, contr[i].Text);
        }
    }

    //if(itemInserted) {
    //	Lans.RefreshCache();
    //}
    /**
    * 
    */
    public static void c(System.Windows.Forms.Control sender, System.Windows.Forms.Control[] contr) throws Exception {
        C(sender, contr, false);
    }

    /**
    * 
    */
    public static void c(System.Windows.Forms.Control sender, System.Windows.Forms.Control[] controls, boolean isRecursive) throws Exception {
        for (int i = 0;i < controls.Length;i++)
        {
            if (controls[i].GetType() == OpenDental.UI.ODGrid.class)
            {
                ((OpenDental.UI.ODGrid)controls[i]).setTitle(Lans.convertString(((OpenDental.UI.ODGrid)controls[i]).getTranslationName(),((OpenDental.UI.ODGrid)controls[i]).getTitle()));
                for (Object __dummyForeachVar0 : ((OpenDental.UI.ODGrid)controls[i]).getColumns())
                {
                    ODGridColumn col = (ODGridColumn)__dummyForeachVar0;
                    col.setHeading(Lans.convertString(((OpenDental.UI.ODGrid)controls[i]).getTranslationName(),col.getHeading()));
                }
                continue;
            }
             
            controls[i].Text = Lans.ConvertString(sender.GetType().Name, controls[i].Text);
            if (isRecursive)
            {
                Cchildren(sender.GetType().Name, controls[i]);
            }
             
        }
    }

    /**
    * This is recursive, but a little simpler than Fchildren.
    */
    private static void cchildren(String classType, Control parent) throws Exception {
        for (Object __dummyForeachVar1 : parent.Controls)
        {
            Control contr = (Control)__dummyForeachVar1;
            if (contr.HasChildren)
            {
                cchildren(classType,contr);
            }
             
            if (StringSupport.equals(contr.Text, ""))
            {
                continue;
            }
             
            contr.Text = Lans.ConvertString(classType, contr.Text);
        }
    }

    //forms----------------------------------------------------------------------------------------
    /**
    * F is for Form. Translates the following controls on the entire form: title Text, labels, buttons, groupboxes, checkboxes, radiobuttons.  Can include a list of controls to exclude. Also puts all the correct controls into the All category (OK,Cancel,Close,Delete,etc).
    */
    public static void f(System.Windows.Forms.Form sender) throws Exception {
        F(sender, new System.Windows.Forms.Control[]{  });
    }

    /**
    * F is for Form. Translates the following controls on the entire form: title Text, labels, buttons, groupboxes, checkboxes, radiobuttons.  Can include a list of controls to exclude. Also puts all the correct controls into the All category (OK,Cancel,Close,Delete,etc).
    */
    public static void f(Form sender, Control[] exclusions) throws Exception {
        if (StringSupport.equals(CultureInfo.CurrentCulture.Name, "en-US"))
        {
            return ;
        }
         
        if (CultureInfo.CurrentCulture.TextInfo.IsRightToLeft)
        {
            sender.RightToLeft = RightToLeft.Yes;
            sender.RightToLeftLayout = true;
        }
         
        //first translate the main title Text on the form:
        if (!Contains(exclusions, sender))
        {
            sender.Text = Lans.ConvertString(sender.GetType().Name, sender.Text);
        }
         
        //then launch the recursive function for all child controls
        Fchildren(sender, sender, exclusions);
    }

    //if(itemInserted) {
    //	Lans.RefreshCache();
    //}
    /**
    * Returns true if the contrToFind exists in the supplied contrArray. Used to check the exclusion list of F.
    */
    private static boolean contains(Control[] contrArray, Control contrToFind) throws Exception {
        for (int i = 0;i < contrArray.Length;i++)
        {
            if (contrArray[i] == contrToFind)
            {
                return true;
            }
             
        }
        return false;
    }

    /**
    * Called from F and also recursively. Translates all children of the given control except those in the exclusions list.
    */
    private static void fchildren(Form sender, Control parent, Control[] exclusions) throws Exception {
        for (Object __dummyForeachVar4 : parent.Controls)
        {
            Control contr = (Control)__dummyForeachVar4;
            if (contr.GetType() == OpenDental.UI.ODGrid.class)
            {
                ((OpenDental.UI.ODGrid)contr).setTitle(Lans.convertString(((OpenDental.UI.ODGrid)contr).getTranslationName(),((OpenDental.UI.ODGrid)contr).getTitle()));
                for (Object __dummyForeachVar2 : ((OpenDental.UI.ODGrid)contr).getColumns())
                {
                    ODGridColumn col = (ODGridColumn)__dummyForeachVar2;
                    col.setHeading(Lans.convertString(((OpenDental.UI.ODGrid)contr).getTranslationName(),col.getHeading()));
                }
                continue;
            }
             
            //any controls with children of their own.
            if (contr.HasChildren)
            {
                Fchildren(sender, contr, exclusions);
            }
             
            Type contrType = contr.GetType();
            if (CultureInfo.CurrentCulture.TextInfo.IsRightToLeft)
            {
                if (contrType == GroupBox.class || contrType == Panel.class)
                {
                    for (Object __dummyForeachVar3 : contr.Controls)
                    {
                        Control contrGb = (Control)__dummyForeachVar3;
                        contrGb.Location = new Point(contr.Width - contrGb.Width - contrGb.Left, contrGb.Top);
                    }
                }
                 
            }
             
            //ignore any controls except the types we are interested in
            if (contrType != TextBox.class && contrType != Button.class && contrType != OpenDental.UI.Button.class && contrType != Label.class && contrType != GroupBox.class && contrType != CheckBox.class && contrType != RadioButton.class)
            {
                continue;
            }
             
            if (StringSupport.equals(contr.Text, ""))
            {
                continue;
            }
             
            if (exclusions != null && !Contains(exclusions, contr))
            {
                if (StringSupport.equals(contr.Text, "OK") || StringSupport.equals(contr.Text, "&OK") || StringSupport.equals(contr.Text, "Cancel") || StringSupport.equals(contr.Text, "&Cancel") || StringSupport.equals(contr.Text, "Close") || StringSupport.equals(contr.Text, "&Close") || StringSupport.equals(contr.Text, "Add") || StringSupport.equals(contr.Text, "&Add") || StringSupport.equals(contr.Text, "Delete") || StringSupport.equals(contr.Text, "&Delete") || StringSupport.equals(contr.Text, "Up") || StringSupport.equals(contr.Text, "&Up") || StringSupport.equals(contr.Text, "Down") || StringSupport.equals(contr.Text, "&Down") || StringSupport.equals(contr.Text, "Print") || StringSupport.equals(contr.Text, "&Print"))
                {
                    //|| contr.Text==""
                    contr.Text = Lans.ConvertString("All", contr.Text);
                }
                else
                {
                    contr.Text = Lans.ConvertString(sender.GetType().Name, contr.Text);
                } 
            }
             
        }
    }

}


