//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:27 PM
//

package OpenDental.UI;


public enum ToolBarButtonState
{
    /**
    * There are also pushed and enabled to worry about separately.0.
    */
    Normal,
    /**
    * Mouse is hovering over the button and the mouse button is not pressed.
    */
    Hover,
    /**
    * Mouse was pressed over this button and is still down, even if it has moved off this button or off the toolbar.
    */
    Pressed,
    /**
    * In a dropdown button, only the dropdown portion is pressed. For hover, the entire button acts as one, but for pressing, the dropdown can be pressed separately.
    */
    DropPressed
}

