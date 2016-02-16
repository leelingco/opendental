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
public class SimpleToggle  extends CheckBox 
{
    private Color _UpColor = new Color();
    private Color _DownColor = new Color();
    private Color _Transparency = new Color();
    private boolean bIn = false;
    public SimpleToggle() throws Exception {
        this.Appearance = Appearance.Button;
        this.setUpColor(Color.LightGray);
        this.setDownColor(Color.Azure);
        this.setTranparencyColor(Color.White);
        this.BackColor = this.Checked ? this.getDownColor() : this.getUpColor();
        this.ForeColor = this.Enabled ? Color.Black : Color.Gray;
        this.Paint += new PaintEventHandler(this.DrawPanelPaint);
        this.MouseEnter += new EventHandler(SimpleToggle_MouseEnter);
        this.MouseLeave += new EventHandler(SimpleToggle_MouseLeave);
    }

    private void drawPanelPaint(Object sender, System.Windows.Forms.PaintEventArgs e) throws Exception {
        Graphics g = e.Graphics;
        Brush b = null;
        Pen p = null;
        try
        {
            // never want to die in here
            b = new SolidBrush(this.BackColor);
            g.FillRectangle(b, e.ClipRectangle);
            if (this.Checked || bIn)
            {
                g.DrawRectangle(Pens.Blue, 0, 0, this.Width - 1, this.Height - 1);
            }
             
            if (this.Image != null)
            {
                int x = (this.Width - this.Image.Width) / 2;
                int y = (this.Height - this.Image.Height) / 2;
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

    public Color getUpColor() throws Exception {
        return this._UpColor;
    }

    public void setUpColor(Color value) throws Exception {
        this._UpColor = value;
    }

    public Color getDownColor() throws Exception {
        return this._DownColor;
    }

    public void setDownColor(Color value) throws Exception {
        this._DownColor = value;
    }

    protected void onCheckedChanged(EventArgs e) throws Exception {
        super.OnCheckedChanged(e);
        // CheckBox OnCheckedChanged method
        this.BackColor = this.Checked ? this.getDownColor() : this.getUpColor();
    }

    protected void onClick(EventArgs e) throws Exception {
        super.OnClick(e);
        // CheckBox OnClick method
        this.BackColor = this.Checked ? this.getDownColor() : this.getUpColor();
    }

    protected void onEnabledChanged(EventArgs e) throws Exception {
        super.OnEnabledChanged(e);
        // CheckBox OnEnabled method
        if (this.Enabled)
        {
            this.ForeColor = Color.Black;
            this.BackColor = this.Checked ? this.getDownColor() : this.getUpColor();
        }
        else
        {
            this.ForeColor = Color.LightGray;
            this.BackColor = Color.LightGray;
        } 
    }

    private void simpleToggle_MouseEnter(Object sender, EventArgs e) throws Exception {
        bIn = true;
    }

    private void simpleToggle_MouseLeave(Object sender, EventArgs e) throws Exception {
        bIn = false;
    }

}


