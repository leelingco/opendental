//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;


/**
* Almost the same as the included ToolBarButton, but with a few extra properties.
*/
public class ODToolBarButton  extends System.ComponentModel.Component 
{
    /**
    * Pushed(toggle) and enabled are handled separately
    */
    private OpenDental.UI.ToolBarButtonState state = OpenDental.UI.ToolBarButtonState.Normal;
    private OpenDental.UI.ODToolBarButtonStyle style = OpenDental.UI.ODToolBarButtonStyle.DropDownButton;
    private int imageIndex = new int();
    private Rectangle bounds = new Rectangle();
    private String text = new String();
    private String toolTipText = new String();
    private boolean enabled = true;
    private Menu dropDownMenu = new Menu();
    private Object tag = new Object();
    private boolean pushed = new boolean();
    /**
    * Creates a new ODToolBarButton.
    */
    public ODToolBarButton() throws Exception {
        style = OpenDental.UI.ODToolBarButtonStyle.PushButton;
        state = OpenDental.UI.ToolBarButtonState.Normal;
        imageIndex = -1;
        text = "";
        toolTipText = "";
        tag = "";
    }

    /**
    * Creates a new ODToolBarButton with the given text.
    */
    public ODToolBarButton(String buttonText, int buttonImageIndex, String buttonToolTip, Object buttonTag) throws Exception {
        style = OpenDental.UI.ODToolBarButtonStyle.PushButton;
        state = OpenDental.UI.ToolBarButtonState.Normal;
        imageIndex = buttonImageIndex;
        text = buttonText;
        toolTipText = buttonToolTip;
        tag = buttonTag;
    }

    /**
    * Creates a new separator style ODToolBarButton.
    */
    public ODToolBarButton(OpenDental.UI.ODToolBarButtonStyle buttonStyle) throws Exception {
        style = buttonStyle;
        state = OpenDental.UI.ToolBarButtonState.Normal;
        imageIndex = -1;
        text = "";
        toolTipText = "";
        tag = "";
    }

    /**
    * The bounds of this button.
    */
    public Rectangle getBounds() throws Exception {
        return bounds;
    }

    public void setBounds(Rectangle value) throws Exception {
        bounds = value;
    }

    /**
    * 
    */
    public OpenDental.UI.ODToolBarButtonStyle getStyle() throws Exception {
        return style;
    }

    public void setStyle(OpenDental.UI.ODToolBarButtonStyle value) throws Exception {
        style = value;
    }

    /**
    * 
    */
    public OpenDental.UI.ToolBarButtonState getState() throws Exception {
        return state;
    }

    public void setState(OpenDental.UI.ToolBarButtonState value) throws Exception {
        state = value;
    }

    /**
    * 
    */
    public String getText() throws Exception {
        return text;
    }

    public void setText(String value) throws Exception {
        text = value;
    }

    /**
    * 
    */
    public String getToolTipText() throws Exception {
        return toolTipText;
    }

    public void setToolTipText(String value) throws Exception {
        toolTipText = value;
    }

    /**
    * 
    */
    public int getImageIndex() throws Exception {
        return imageIndex;
    }

    public void setImageIndex(int value) throws Exception {
        imageIndex = value;
    }

    /**
    * 
    */
    public boolean getEnabled() throws Exception {
        return enabled;
    }

    public void setEnabled(boolean value) throws Exception {
        enabled = value;
    }

    /**
    * 
    */
    public Menu getDropDownMenu() throws Exception {
        return dropDownMenu;
    }

    public void setDropDownMenu(Menu value) throws Exception {
        dropDownMenu = value;
    }

    /**
    * Holds extra information about the button, so we can tell which button was clicked.
    */
    public Object getTag() throws Exception {
        return tag;
    }

    public void setTag(Object value) throws Exception {
        tag = value;
    }

    /**
    * Only used if style is ToggleButton.
    */
    public boolean getPushed() throws Exception {
        return pushed;
    }

    public void setPushed(boolean value) throws Exception {
        pushed = value;
    }

}


