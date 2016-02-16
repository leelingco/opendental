//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:22 PM
//

package fyiReporting.RdlDesign;


/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    The RDL project is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
public class SimpleButton  extends Button 
{
    private Color _Transparency = new Color();
    private boolean bDown = false;
    private boolean bIn = false;
    public SimpleButton(Control parent) throws Exception {
        this.Parent = parent;
        this.setTranparencyColor(Color.White);
        this.BackColor = parent.BackColor;
        this.ForeColor = this.Enabled ? Color.Black : Color.Gray;
        this.MouseDown += new MouseEventHandler(SimpleButton_MouseDown);
        this.MouseUp += new MouseEventHandler(SimpleButton_MouseUp);
        this.MouseEnter += new EventHandler(SimpleButton_MouseEnter);
        this.MouseLeave += new EventHandler(SimpleButton_MouseLeave);
        this.Paint += new PaintEventHandler(this.DrawPanelPaint);
    }

    private void drawPanelPaint(Object sender, System.Windows.Forms.PaintEventArgs e) throws Exception {
        Graphics g = e.Graphics;
        Brush b = null;
        Pen p = null;
        try
        {
            // never want to die in here
            b = new SolidBrush(this.Enabled ? this.BackColor : Color.LightGray);
            g.FillRectangle(b, e.ClipRectangle);
            if (bIn && this.Enabled)
                g.DrawRectangle(Pens.Blue, 0, 0, this.Width - 1, this.Height - 1);
             
            if (this.Image != null)
            {
                int x = (this.Width - this.Image.Width) / 2;
                int y = (this.Height - this.Image.Height) / 2;
                if (bDown && bIn)
                {
                    x += 1;
                    y += 1;
                }
                 
                // Draw Image using the transparency color
                ImageAttributes imageAttr = new ImageAttributes();
                imageAttr.SetColorKey(_Transparency, _Transparency, ColorAdjustType.Default);
                // Image
                // Dest. rect.
                // srcX
                // srcY
                // srcWidth
                // srcHeight
                // srcUnit
                g.DrawImage(this.Image, new Rectangle(x, y, this.Image.Width, this.Image.Height), 0, 0, this.Image.Width, this.Image.Height, GraphicsUnit.Pixel, imageAttr);
            }
            else
            {
                // ImageAttributes
                StringFormat format = new StringFormat(StringFormatFlags.NoWrap);
                g.DrawString(this.Text, this.Font, Brushes.Black, new Rectangle(2, 2, this.Width, this.Height), format);
            } 
        }
        catch (Exception __dummyCatchVar0)
        {
        }
        finally
        {
            // todo draw the error message
            if (b != null)
                b.Dispose();
             
            if (p != null)
                p.Dispose();
             
        }
    }

    public Color getTranparencyColor() throws Exception {
        return this._Transparency;
    }

    public void setTranparencyColor(Color value) throws Exception {
        this._Transparency = value;
    }

    private void simpleButton_MouseDown(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button == MouseButtons.Left)
            bDown = true;
         
    }

    private void simpleButton_MouseUp(Object sender, MouseEventArgs e) throws Exception {
        if (e.Button == MouseButtons.Left)
            bDown = false;
         
    }

    private void simpleButton_MouseEnter(Object sender, EventArgs e) throws Exception {
        bIn = true;
    }

    private void simpleButton_MouseLeave(Object sender, EventArgs e) throws Exception {
        bIn = false;
    }

}


