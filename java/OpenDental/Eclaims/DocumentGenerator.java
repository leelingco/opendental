//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:58:31 PM
//

package OpenDental.Eclaims;


public class DocumentGenerator   
{
    private abstract static class RenderPrim   
    {
        //Graphical element. Is always as wide as the page.
        public abstract float height(Graphics g) throws Exception ;

        public abstract void render(Graphics g, float Y) throws Exception ;

        public abstract RenderPrim clone() {
            try
                    ;
            catch (RuntimeException __dummyCatchVar0)
            {
                throw __dummyCatchVar0;
            }
            catch (Exception __dummyCatchVar0)
            {
                throw new RuntimeException(__dummyCatchVar0);
            }
        
        }

        /**
        * Returns true if the space taken up by this primitive is at least 1 square pixel.
        */
        public abstract boolean hasDims() throws Exception ;
    
    }

    private static class BlankPrim  extends RenderPrim 
    {
        float height = new float();
        public BlankPrim(float pHeight) throws Exception {
            height = pHeight;
        }

        public float height(Graphics g) throws Exception {
            return height;
        }

        public void render(Graphics g, float Y) throws Exception {
            return ;
        }

        public RenderPrim clone() {
            try
            {
                return new BlankPrim(this.height);
            }
            catch (RuntimeException __dummyCatchVar1)
            {
                throw __dummyCatchVar1;
            }
            catch (Exception __dummyCatchVar1)
            {
                throw new RuntimeException(__dummyCatchVar1);
            }
        
        }

        public boolean hasDims() throws Exception {
            return (height > 0);
        }
    
    }

    private static class PageBreak  extends RenderPrim 
    {
        public float height(Graphics g) throws Exception {
            return 0;
        }

        //Has a height but depends on the final page rendering.
        public void render(Graphics g, float Y) throws Exception {
        }

        public RenderPrim clone() {
            try
            {
                return new PageBreak();
            }
            catch (RuntimeException __dummyCatchVar2)
            {
                throw __dummyCatchVar2;
            }
            catch (Exception __dummyCatchVar2)
            {
                throw new RuntimeException(__dummyCatchVar2);
            }
        
        }

        public boolean hasDims() throws Exception {
            return false;
        }
    
    }

    private static class RenderStr  extends RenderPrim 
    {
        Font font = new Font();
        Pen pen = new Pen();
        Size layout = new Size();
        String text = new String();
        float x = new float();
        float yOffset = new float();
        public RenderStr(Font pFont, Pen pPen, Size pLayout, String pText, float pX, float pYOffset) throws Exception {
            font = pFont;
            pen = pPen;
            layout = pLayout;
            text = pText;
            x = pX;
            yOffset = pYOffset;
        }

        public float height(Graphics g) throws Exception {
            return yOffset + g.MeasureString(text, font, layout).Height;
        }

        public void render(Graphics g, float Y) throws Exception {
            g.DrawString(text, font, pen.Brush, new Rectangle((int)Math.Floor(x), (int)Math.Floor(Y + yOffset), layout.Width, layout.Height));
        }

        public RenderPrim clone() {
            try
            {
                return new RenderStr(this.font,this.pen,this.layout,this.text,this.x,this.yOffset);
            }
            catch (RuntimeException __dummyCatchVar3)
            {
                throw __dummyCatchVar3;
            }
            catch (Exception __dummyCatchVar3)
            {
                throw new RuntimeException(__dummyCatchVar3);
            }
        
        }

        public boolean hasDims() throws Exception {
            return (text.Length > 0);
        }
    
    }

    private static class RenderHLine  extends RenderPrim 
    {
        Pen pen = new Pen();
        float x1 = new float();
        float x2 = new float();
        float yOffset = new float();
        public RenderHLine(Pen pPen, float pX1, float pX2, float pYOffset) throws Exception {
            pen = pPen;
            x1 = pX1;
            x2 = pX2;
            yOffset = pYOffset;
        }

        public float height(Graphics g) throws Exception {
            return yOffset + pen.Width;
        }

        public void render(Graphics g, float Y) throws Exception {
            Y = (float)Math.Ceiling(yOffset + Y);
            g.DrawLine(pen, x1, Y, x2, Y);
        }

        public RenderPrim clone() {
            try
            {
                return new RenderHLine(this.pen,this.x1,this.x2,this.yOffset);
            }
            catch (RuntimeException __dummyCatchVar4)
            {
                throw __dummyCatchVar4;
            }
            catch (Exception __dummyCatchVar4)
            {
                throw new RuntimeException(__dummyCatchVar4);
            }
        
        }

        public boolean hasDims() throws Exception {
            return (pen.Width > 0 && x1 != x2);
        }
    
    }

    private List<RenderPrim> renderContainer = new List<RenderPrim>();
    /**
    * Contains an array of elements/render containers for the whole document.
    */
    private List<List<RenderPrim>> documentContainer = new List<List<RenderPrim>>();
    /**
    * Used to store indentation x-values
    */
    private List<float> xstack = new List<float>();
    /**
    * Current dimensions for each printed page.
    */
    public Rectangle bounds = new Rectangle(0, 0, 0, 0);
    /**
    * Current font.
    */
    public Font standardFont = new Font(FontFamily.GenericMonospace, 10);
    public void printPage(Graphics g, int pageNum) throws Exception {
        int page = 0;
        int numPageElements = 0;
        int offset = 0;
        do
        {
            offset = numPageElements;
            numPageElements = calcNumElementsInWholePage(g,numPageElements);
            page++;
        }
        while (pageNum > page);
        float yWritten = 0;
        for (int i = 0;i < numPageElements;i++)
        {
            if (offset + i < documentContainer.Count)
            {
                List<RenderPrim> renderGroup = documentContainer[offset + i];
                for (Object __dummyForeachVar0 : renderGroup)
                {
                    RenderPrim prim = (RenderPrim)__dummyForeachVar0;
                    prim.Render(g, bounds.Top + yWritten);
                }
                yWritten += CalcElementHeight(g, renderGroup);
            }
             
        }
    }

    public int calcTotalPages(Graphics g) throws Exception {
        int pages = 1;
        int elementsProcessed = calcNumElementsInWholePage(g,0);
        while (documentContainer.Count - elementsProcessed > 0)
        {
            pages++;
            elementsProcessed += calcNumElementsInWholePage(g,elementsProcessed);
        }
        return pages;
    }

    private float calcElementHeight(Graphics g, List<RenderPrim> element) throws Exception {
        float maxy = 0;
        for (Object __dummyForeachVar1 : element)
        {
            RenderPrim prim = (RenderPrim)__dummyForeachVar1;
            maxy = Math.Max(maxy, prim.height(g));
        }
        return maxy;
    }

    private int calcNumElementsInWholePage(Graphics g, int offset) throws Exception {
        float totalY = 0;
        int numElements = 0;
        float maxy = 0;
        int i = new int();
        for (i = offset;i < documentContainer.Count;i++)
        {
            //Page breaks end the current page.
            if (documentContainer[i].Count == 1)
            {
                Type elementType = documentContainer[i][0].GetType();
                if (elementType == PageBreak.class)
                {
                    numElements++;
                    break;
                }
                 
            }
             
            maxy = CalcElementHeight(g, documentContainer[i]);
            if (totalY + maxy < bounds.Height)
            {
                numElements++;
                totalY += maxy;
            }
            else
            {
                break;
            } 
        }
        return ((numElements < 1 && documentContainer.Count - offset > 0) ? 1 : numElements);
    }

    //At least one element
    public SizeF drawField(Graphics g, String fieldName, String value, boolean alwaysShow, float X, float Y) throws Exception {
        return drawField(g,fieldName,value,alwaysShow,X,Y,": ");
    }

    /**
    * Prints the contents of the field if they are non-empty. Also prints the field's name if it is not empty, or if alwaysShowName is true.
    */
    public SizeF drawField(Graphics g, String fieldName, String value, boolean alwaysShow, float X, float Y, String divideStr) throws Exception {
        if (fieldName == null)
        {
            //should never happen
            MessageBox.Show(this.ToString() + ".DrawField: Internal error, attempt to render null field name (Out of memory?)");
            return new SizeF(0, 0);
        }
         
        if (value == null)
        {
            //Allow null to count as empty string.
            value = "";
        }
         
        if (alwaysShow || value.Length > 0)
        {
            SizeF size1 = drawString(g,fieldName + divideStr,X,Y);
            SizeF size2 = drawString(g,value,X + size1.Width,Y);
            return new SizeF(size1.Width + size2.Width, Math.Max(size1.Height, size2.Height));
        }
         
        return new SizeF(0, 0);
    }

    //For most situations when the fields are put in a good place on the output page where the field label fits
    //in the page bounds and the field value has a reasonable amount of horizontal space, the width will simply
    //be the width of the two rendered strings added together. This will be the common case and is the only
    //relevant possibility.
    /**
    * The purpose of this method is to test each string before printing.  It should only print strings that belong on the current page.
    */
    public SizeF drawString(Graphics g, String text, float X, float Y) throws Exception {
        return drawString(g,text,X,Y,standardFont);
    }

    /**
    * The purpose of this method is to test each string before printing.  It should only print strings that belong on the current page.  y gets passed in as if it were all one long page.
    */
    public SizeF drawString(Graphics g, String text, float X, float Y, Font font) throws Exception {
        return drawString(g,text,X,Y,font,-1);
    }

    /**
    * The purpose of this method is to test each string before printing.  It should only print strings that belong on the current page.  y gets passed in as if it were all one long page. Use maxPixelWidth to define a maximum width for the output string, which will be truncated to the nearest character that fits within the amount specified. Use maxPixelWidth=-1 to remove width limit.
    */
    public SizeF drawString(Graphics g, String text, float X, float Y, Font font, int maxPixelWidth) throws Exception {
        if (text == null)
        {
            text = "";
        }
         
        Size renderArea = new Size(Convert.ToInt32(Math.Truncate(bounds.Right - X + 1)), bounds.Height);
        if (maxPixelWidth >= 0)
        {
            renderArea.Width = Math.Min(maxPixelWidth, renderArea.Width);
        }
         
        SizeF size = g.MeasureString(text, font, renderArea);
        renderContainer.Add(new RenderStr(font, Pens.Black, renderArea, text, X, Y));
        return size;
    }

    public SizeF horizontalLine(Graphics g, Pen pen, float x1, float x2, float Y) throws Exception {
        renderContainer.Add(new RenderHLine(pen,x1,x2,Y));
        return new SizeF(x2 - x1, pen.Width);
    }

    /**
    * Pushes/Stores the current global x value onto the front of the stack to be used for indentaion.
    */
    public void pushX(float x) throws Exception {
        xstack.Add(x);
    }

    //Adds to the end of the list.
    /**
    * Pops/Removes the x value on the front of the stack and resets the x value to the next x value in the x stack.
    */
    public float popX() throws Exception {
        if (xstack.Count > 0)
        {
            xstack.RemoveAt(xstack.Count - 1);
        }
         
        return resetX();
    }

    /**
    * Gets the x value on the front of the stack and sets the global x value to the returned value. Does not remove the x value from the x stack.
    */
    public float resetX() throws Exception {
        float x = bounds.Left;
        if (xstack.Count > 0)
        {
            x = xstack[xstack.Count - 1];
        }
         
        return x;
    }

    /**
    * Returns the x-value of the beginning of the left-hand side of the element.
    */
    public float startElement(float extraJumpY, boolean alwaysJump) throws Exception {
        //Get max height of the container elements.
        boolean hasDims = false;
        for (Object __dummyForeachVar2 : renderContainer)
        {
            RenderPrim prim = (RenderPrim)__dummyForeachVar2;
            if (prim.hasDims())
            {
                hasDims = true;
                break;
            }
             
        }
        documentContainer.Add(clonePrims());
        if ((hasDims && extraJumpY > 0) || alwaysJump)
        {
            //This prevents getting odd offsets for text rows near the beginning of a page,
            //or from creating a series of white-space.
            renderContainer.Clear();
            renderContainer.Add(new BlankPrim(extraJumpY));
            documentContainer.Add(clonePrims());
        }
         
        renderContainer.Clear();
        return resetX();
    }

    public float startElement(float extraJumpY) throws Exception {
        return startElement(extraJumpY,false);
    }

    /**
    * Same as StartElement(0).
    */
    public float startElement() throws Exception {
        return StartElement(0);
    }

    /**
    * Inserts a page break at the very end of the document to force proceeding elements to begin at the top of the next page.
    */
    public void nextPage() throws Exception {
        startElement();
        renderContainer.Add(new PageBreak());
        startElement();
    }

    private List<RenderPrim> clonePrims() throws Exception {
        List<RenderPrim> dup = new List<RenderPrim>();
        for (Object __dummyForeachVar3 : renderContainer)
        {
            RenderPrim prim = (RenderPrim)__dummyForeachVar3;
            dup.Add(prim.clone());
        }
        return dup;
    }

}


