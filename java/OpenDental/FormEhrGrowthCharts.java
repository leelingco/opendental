//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:41 PM
//

package OpenDental;

import OpenDentBusiness.Patient;
import OpenDentBusiness.Patients;
import OpenDentBusiness.Vitalsign;
import OpenDentBusiness.Vitalsigns;

public class FormEhrGrowthCharts  extends Form 
{

    public long PatNum = new long();
    private Patient pat;
    private static final float border = 50f;
    private Pen thinPen = new Pen(Color.LightGray, 0.5f);
    private Pen thickPen = new Pen(Color.Black, 1.5f);
    private Brush dataBrush = new SolidBrush(Color.CadetBlue);
    private float minYH = new float();
    private float maxYH = new float();
    private float minYW = new float();
    private float maxYW = new float();
    private float minX = new float();
    private float maxX = new float();
    private float pixPerYear = new float();
    private float pixPerInch = new float();
    private float pixPerPound = new float();
    private static final int minAge = 2;
    private static final int maxAge = 20;
    private static final int maxInches = 76;
    private static final int maxPounds = 230;
    private static final int ageRange = maxAge - minAge;
    List<Vitalsign> listVS = new List<Vitalsign>();
    public FormEhrGrowthCharts() throws Exception {
        initializeComponent();
    }

    private void formGrowthCharts_Load(Object sender, EventArgs e) throws Exception {
        listVS = Vitalsigns.refresh(PatNum);
        pat = Patients.getPat(PatNum);
        Text += " - " + pat.Gender.ToString() + " Growth Chart for " + pat.FName + " " + pat.LName + " age " + pat.getAge();
    }

    private void formGrowthCharts_Paint(Object sender, PaintEventArgs e) throws Exception {
        e.Graphics.SmoothingMode = SmoothingMode.HighQuality;
        defineBounds(e.Graphics);
        drawHeightGrid(e.Graphics);
        drawWeightGrid(e.Graphics);
        plotHW(e.Graphics);
    }

    /**
    * Defines the bounds of the graphs to be drawn based on size of form.
    */
    private void defineBounds(Graphics g) throws Exception {
        minYH = border;
        maxYH = ClientSize.Height / 2f - border;
        minYW = ClientSize.Height / 2f + border;
        maxYW = ClientSize.Height - border;
        minX = border + g.MeasureString("230lbs", Font).Width;
        maxX = (ClientSize.Width)-border - g.MeasureString("230lbs", Font).Width;
        pixPerYear = (maxX - minX) / (float)(ageRange);
        pixPerInch = (maxYH - minYH) / (float)maxInches;
        pixPerPound = (maxYW - minYW) / (float)maxPounds;
    }

    /**
    * 
    */
    private void drawHeightGrid(Graphics g) throws Exception {
        float curX = new float();
        float curY = new float();
        SizeF labelPix = new SizeF();
        //draw background
        g.FillRectangle(Brushes.White, minX, minYH, maxX - minX, maxYH - minYH);
        //draw Title
        labelPix = g.MeasureString("Height - (" + pat.Gender.ToString() + ")", Font);
        curX = ((maxX + minX) / 2) - (labelPix.Width / 2);
        curY = minYH - labelPix.Height - 2f;
        g.DrawString("Height - (" + pat.Gender.ToString() + ")", Font, Brushes.Black, curX, curY);
        for (int i = 0;i < ageRange + 1;i++)
        {
            //Draw thin age lines
            curX = minX + (float)i * pixPerYear;
            g.DrawLine(thinPen, curX, minYH, curX, maxYH);
            labelPix = g.MeasureString((i + minAge).ToString() + "y", DefaultFont);
            g.DrawString((i + minAge).ToString() + "y", Font, Brushes.Black, curX - (labelPix.Width / 2f), maxYH + 1f);
        }
        for (int i = 0;i < maxInches + 1;i++)
        {
            //draw all Horizontal Height lines
            curY = maxYH - (float)i * pixPerInch;
            g.DrawLine(thinPen, minX, curY, maxX, curY);
            if (i % 12 == 0 || i == maxInches)
            {
                //bold line and label every 12"
                g.DrawLine(thickPen, minX, curY, maxX, curY);
                g.DrawString(i.ToString() + "in", Font, Brushes.Black, border - 5f, curY - 5f);
                g.DrawString(i.ToString() + "in", Font, Brushes.Black, maxX + 4f, curY - 5f);
            }
             
        }
        for (int i = 0;i < ageRange + 1;i++)
        {
            //draw bold vertical age lines
            curX = minX + (float)i * pixPerYear;
            if ((i % 5) + minAge == 5 || i == 0)
            {
                g.DrawLine(thickPen, curX, minYH, curX, maxYH);
            }
             
        }
    }

    /**
    * 
    */
    private void drawWeightGrid(Graphics g) throws Exception {
        float curX = new float();
        float curY = new float();
        SizeF labelPix = new SizeF();
        //draw background
        g.FillRectangle(Brushes.White, minX, minYW, maxX - minX, maxYW - minYW);
        //draw Title
        labelPix = g.MeasureString("Weight - (" + pat.Gender.ToString() + ")", Font);
        curX = ((maxX + minX) / 2) - (labelPix.Width / 2);
        curY = minYW - labelPix.Height - 2f;
        g.DrawString("Weight - (" + pat.Gender.ToString() + ")", Font, Brushes.Black, curX, curY);
        for (int i = 0;i < ageRange + 1;i++)
        {
            //Draw thin age lines
            curX = minX + (float)i * pixPerYear;
            g.DrawLine(thinPen, curX, minYW, curX, maxYW);
            labelPix = g.MeasureString((i + minAge).ToString() + "y", Font);
            g.DrawString((i + minAge).ToString() + "y", DefaultFont, Brushes.Black, curX - (labelPix.Width / 2f), maxYW + 2f);
        }
        for (int i = 0;i < maxPounds + 1;i++)
        {
            //draw all Horizontal pound lines
            curY = maxYW - (float)i * pixPerPound;
            if (i % 5 == 0)
            {
                //gray line every 5 lbs
                g.DrawLine(thinPen, minX, curY, maxX, curY);
            }
             
            if (i % 20 == 0 || i == maxPounds)
            {
                //bold line and label every 20lbs
                g.DrawLine(thickPen, minX, curY, maxX, curY);
                g.DrawString(i.ToString() + "lbs", DefaultFont, Brushes.Black, border - 5f, curY - 5f);
                g.DrawString(i.ToString() + "lbs", DefaultFont, Brushes.Black, maxX + 4f, curY - 5f);
            }
             
        }
        for (int i = 0;i < ageRange + 1;i++)
        {
            //draw bold vertical age lines
            curX = minX + (float)i * pixPerYear;
            if ((i % 5) + minAge == 5 || i == 0)
            {
                g.DrawLine(thickPen, curX, minYW, curX, maxYW);
            }
             
        }
    }

    /**
    * 
    */
    private void plotHW(Graphics g) throws Exception {
        float curX = new float();
        float curY = new float();
        for (Object __dummyForeachVar0 : listVS)
        {
            Vitalsign vs = (Vitalsign)__dummyForeachVar0;
            TimeSpan ageOfVitals = vs.DateTaken - pat.Birthdate;
            curX = minX + (float)((ageOfVitals.TotalDays / 365) - 2) * pixPerYear;
            //-2 to adjust for age starting at 2yrs old
            curY = maxYH - vs.Height * pixPerInch;
            g.FillEllipse(Brushes.Blue, curX - 2.5f, curY - 2.5f, 5f, 5f);
            curY = maxYW - (float)vs.Weight * pixPerPound;
            g.FillEllipse(Brushes.Teal, curX - 2.5f, curY - 2.5f, 5f, 5f);
        }
    }

    /**
    * Required designer variable.
    */
    private System.ComponentModel.IContainer components = null;
    /**
    * Clean up any resources being used.
    * 
    *  @param disposing true if managed resources should be disposed; otherwise, false.
    */
    protected void dispose(boolean disposing) throws Exception {
        if (disposing && (components != null))
        {
            components.Dispose();
        }
         
        super.Dispose(disposing);
    }

    /**
    * Required method for Designer support - do not modify
    * the contents of this method with the code editor.
    */
    private void initializeComponent() throws Exception {
        this.SuspendLayout();
        //
        // FormGrowthCharts
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(684, 692);
        this.DoubleBuffered = true;
        this.Name = "FormGrowthCharts";
        this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        this.Text = "FormGrowthCharts";
        this.Load += new System.EventHandler(this.FormGrowthCharts_Load);
        this.Paint += new System.Windows.Forms.PaintEventHandler(this.FormGrowthCharts_Paint);
        this.ResumeLayout(false);
    }

}
/* This is a copy of the entire file as of 3/25/11 AM
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
namespace OpenDental {
	public partial class FormGrowthCharts:Form {
		private float patNum;
		private const float border=10f;
		private Pen thinPen=new Pen(Color.Gray,1f);
		private Pen thickPen=new Pen(Color.Black,1f);
		public FormGrowthCharts() {
			InitializeComponent();
		}
		private void FormGrowthCharts_Load(object sender,EventArgs e) {
		}
		private void FormGrowthCharts_Paint(object sender,PaintEventArgs e) {
			//DrawHeightGrid(e.Graphics);
			//DrawWeightGrid(e.Graphics);
			testGraphics(e.Graphics);
			e.Graphics.DrawString(Height.ToString(),DefaultFont,Brushes.Cyan,5,30);
			e.Graphics.DrawString(Width.ToString(),DefaultFont,Brushes.Cyan,30,30);
			//DrawWeightGrid
			//etc...
			//e.Graphics.DrawLine(thinPen,0,0,100,100);
		}
		private void testGraphics(Graphics g){
			Height=200;
			Width=200;
			for(int i=0;i<400;i++){
				g.DrawLine(thinPen,i,0,i,i);
				if(i%10==0){
				g.DrawLine(thickPen,i,0,i,i);					
				}
			}
		}
		///<summary></summary>
		private void DrawHeightGrid(Graphics g) {
			float maxYcoord = (float)(Height-border)/2f;
			float maxXcoord = (float)Width-border;
			float pixPerX = (float)(Width-2f*border)/19f;
			float pixPerY = (float)(maxYcoord-border)/76f;
			float xCur = border;
			float yCur = border;
			
			for(int i=0;i<19;i++) { //Vertical Lines
				xCur=border+(float)i*pixPerX;
				g.DrawLine(thinPen,xCur,border,xCur,maxYcoord);
				g.DrawString((i+2).ToString(),DefaultFont,Brushes.Black,xCur-7f,maxYcoord);
			}
			maxXcoord=xCur;//graphics issue with Width. This ensures the horizontal lines are drawn up to but not past the vertical lines.
			for(int i=0;i<77;i++) {//Horizontal Height Lines
				yCur=maxYcoord-i*pixPerY;
				g.DrawLine(thinPen,border,yCur,maxXcoord,yCur);
				if(i%12==0) {//bold line every 12 inches
					g.DrawLine(thickPen,border,yCur,maxXcoord,yCur);
					g.DrawString(i.ToString(),DefaultFont,Brushes.Black,maxXcoord+4f,yCur-5f);
				}
			}
			for(int i=0;i<19;i++) {//Bold vertical Lines
				xCur=border+(float)i*pixPerX;
				if(i%5==3) {//bold line every 5 years
					g.DrawLine(thickPen,xCur,border,xCur,maxYcoord);
				}
			}
		}
		///<summary></summary>
		private void DrawWeightGrid(Graphics g) {
			float minYcoord = (float)(Height+border)/2f;
			float maxYcoord = (float)(Height-border)*.96f;
			float maxXcoord = (float)Width-border;
			float pixPerX = (float)(Width-2f*border)/19f;
			float pixPerY = (float)(maxYcoord-minYcoord)/231f;
			float xCur = border;
			float yCur = border;
			for(int i=0;i<19;i++) { //Vertical Lines
				xCur=border+(float)i*pixPerX;
				g.DrawLine(thinPen,xCur,minYcoord,xCur,maxYcoord);
				//g.DrawString((i+2).ToString(),DefaultFont,Brushes.Black,xCur-7f,maxYcoord+5f);
			}
			maxXcoord=xCur;//graphics issue with Width. This ensures the horizontal lines are drawn up to but not past the last vertical line.
			for(int i=0;i<231;i++) {//Horizontal Lines
				yCur=maxYcoord-i*pixPerY;
				if(i%2==0) {//lines every inch was too crowded.
					g.DrawLine(thinPen,border,yCur,maxXcoord,yCur);
				}
				if(i%10==0) {//bold line every 10 lbs
					g.DrawLine(thickPen,border,yCur,maxXcoord,yCur);
				}
				if(i%20==0) {//number every 20 lbs
					g.DrawString(i.ToString(),DefaultFont,Brushes.Black,maxXcoord+4f,yCur-5f);
				}
			}
			for(int i=0;i<19;i++) {//Bold vertical Lines
				xCur=border+(float)i*pixPerX;
				if(i%5==3) {//bold line every 5 years
					g.DrawLine(thickPen,xCur,minYcoord,xCur,(float)(Height-border)*.96f);
				}
			}
		}
	}
}
*/

/* This is a copy of the entire file as of 3/25/11 AM
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
namespace OpenDental {
	public partial class FormGrowthCharts:Form {
		private float patNum;
		private const float border=10f;
		private Pen thinPen=new Pen(Color.Gray,1f);
		private Pen thickPen=new Pen(Color.Black,1f);
		public FormGrowthCharts() {
			InitializeComponent();
		}
		private void FormGrowthCharts_Load(object sender,EventArgs e) {
		}
		private void FormGrowthCharts_Paint(object sender,PaintEventArgs e) {
			//DrawHeightGrid(e.Graphics);
			//DrawWeightGrid(e.Graphics);
			testGraphics(e.Graphics);
			e.Graphics.DrawString(Height.ToString(),DefaultFont,Brushes.Cyan,5,30);
			e.Graphics.DrawString(Width.ToString(),DefaultFont,Brushes.Cyan,30,30);
			//DrawWeightGrid
			//etc...
			//e.Graphics.DrawLine(thinPen,0,0,100,100);
		}
		private void testGraphics(Graphics g){
			Height=200;
			Width=200;
			for(int i=0;i<400;i++){
				g.DrawLine(thinPen,i,0,i,i);
				if(i%10==0){
				g.DrawLine(thickPen,i,0,i,i);					
				}
			}
		}
		///<summary></summary>
		private void DrawHeightGrid(Graphics g) {
			float maxYcoord = (float)(Height-border)/2f;
			float maxXcoord = (float)Width-border;
			float pixPerX = (float)(Width-2f*border)/19f;
			float pixPerY = (float)(maxYcoord-border)/76f;
			float xCur = border;
			float yCur = border;
			
			for(int i=0;i<19;i++) { //Vertical Lines
				xCur=border+(float)i*pixPerX;
				g.DrawLine(thinPen,xCur,border,xCur,maxYcoord);
				g.DrawString((i+2).ToString(),DefaultFont,Brushes.Black,xCur-7f,maxYcoord);
			}
			maxXcoord=xCur;//graphics issue with Width. This ensures the horizontal lines are drawn up to but not past the vertical lines.
			for(int i=0;i<77;i++) {//Horizontal Height Lines
				yCur=maxYcoord-i*pixPerY;
				g.DrawLine(thinPen,border,yCur,maxXcoord,yCur);
				if(i%12==0) {//bold line every 12 inches
					g.DrawLine(thickPen,border,yCur,maxXcoord,yCur);
					g.DrawString(i.ToString(),DefaultFont,Brushes.Black,maxXcoord+4f,yCur-5f);
				}
			}
			for(int i=0;i<19;i++) {//Bold vertical Lines
				xCur=border+(float)i*pixPerX;
				if(i%5==3) {//bold line every 5 years
					g.DrawLine(thickPen,xCur,border,xCur,maxYcoord);
				}
			}
		}
		///<summary></summary>
		private void DrawWeightGrid(Graphics g) {
			float minYcoord = (float)(Height+border)/2f;
			float maxYcoord = (float)(Height-border)*.96f;
			float maxXcoord = (float)Width-border;
			float pixPerX = (float)(Width-2f*border)/19f;
			float pixPerY = (float)(maxYcoord-minYcoord)/231f;
			float xCur = border;
			float yCur = border;
			for(int i=0;i<19;i++) { //Vertical Lines
				xCur=border+(float)i*pixPerX;
				g.DrawLine(thinPen,xCur,minYcoord,xCur,maxYcoord);
				//g.DrawString((i+2).ToString(),DefaultFont,Brushes.Black,xCur-7f,maxYcoord+5f);
			}
			maxXcoord=xCur;//graphics issue with Width. This ensures the horizontal lines are drawn up to but not past the last vertical line.
			for(int i=0;i<231;i++) {//Horizontal Lines
				yCur=maxYcoord-i*pixPerY;
				if(i%2==0) {//lines every inch was too crowded.
					g.DrawLine(thinPen,border,yCur,maxXcoord,yCur);
				}
				if(i%10==0) {//bold line every 10 lbs
					g.DrawLine(thickPen,border,yCur,maxXcoord,yCur);
				}
				if(i%20==0) {//number every 20 lbs
					g.DrawString(i.ToString(),DefaultFont,Brushes.Black,maxXcoord+4f,yCur-5f);
				}
			}
			for(int i=0;i<19;i++) {//Bold vertical Lines
				xCur=border+(float)i*pixPerX;
				if(i%5==3) {//bold line every 5 years
					g.DrawLine(thickPen,xCur,minYcoord,xCur,(float)(Height-border)*.96f);
				}
			}
		}
	}
}
*/