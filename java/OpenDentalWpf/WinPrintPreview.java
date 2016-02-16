//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package OpenDentalWpf;



/**
* warning! Only use the print preview in debug.  It will crash if your mouse moves into the top toolbar.  Construct,set dlg.Owner=this, set Document, ShowDialog.
*/
public class WinPrintPreview  extends Window 
{

    /**
    * 
    */
    public WinPrintPreview() throws Exception {
        InitializeComponent();
    }

    public IDocumentPaginatorSource getDocument() throws Exception {
        return documentViewer.Document;
    }

    public void setDocument(IDocumentPaginatorSource value) throws Exception {
        documentViewer.Document = value;
    }

}


