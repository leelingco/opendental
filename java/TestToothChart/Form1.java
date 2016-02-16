//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:43 PM
//

package TestToothChart;

import OpenDentBusiness.ToothInitial;
import OpenDentBusiness.ToothInitialType;
import OpenDentBusiness.ToothNumberingNomenclature;
import TestToothChart.FormFullScreen;
import TestToothChart.FormImageViewer;
import TestToothChart.Form1;

public class Form1  extends Form 
{

    public Form1() throws Exception {
        initializeComponent();
        toothChart2D.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        toothChartOpenGL.setPreferredPixelFormatNumber(7);
        toothChartOpenGL.setDrawMode(OpenDentBusiness.DrawingMode.OpenGL);
        toothChartDirectX.setDeviceFormat(new SparksToothChart.ToothChartDirectX.DirectXDeviceFormat("0;Hardware;32;D16;R5G6B5;FourSamples"));
        toothChartDirectX.setDrawMode(OpenDentBusiness.DrawingMode.DirectX);
    }

    private void form1_Load(Object sender, EventArgs e) throws Exception {
    }

    private void butReset_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
    }

    private void butAllPrimary_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //
        toothChart2D.setPrimary("1");
        toothChart2D.setPrimary("2");
        toothChart2D.setPrimary("3");
        toothChart2D.setPrimary("4");
        toothChart2D.setPrimary("5");
        toothChart2D.setPrimary("6");
        toothChart2D.setPrimary("7");
        toothChart2D.setPrimary("8");
        toothChart2D.setPrimary("9");
        toothChart2D.setPrimary("10");
        toothChart2D.setPrimary("11");
        toothChart2D.setPrimary("12");
        toothChart2D.setPrimary("13");
        toothChart2D.setPrimary("14");
        toothChart2D.setPrimary("15");
        toothChart2D.setPrimary("16");
        toothChart2D.setPrimary("17");
        toothChart2D.setPrimary("18");
        toothChart2D.setPrimary("19");
        toothChart2D.setPrimary("20");
        toothChart2D.setPrimary("21");
        toothChart2D.setPrimary("22");
        toothChart2D.setPrimary("23");
        toothChart2D.setPrimary("24");
        toothChart2D.setPrimary("25");
        toothChart2D.setPrimary("26");
        toothChart2D.setPrimary("27");
        toothChart2D.setPrimary("28");
        toothChart2D.setPrimary("29");
        toothChart2D.setPrimary("30");
        toothChart2D.setPrimary("31");
        toothChart2D.setPrimary("32");
        //
        toothChartOpenGL.setPrimary("1");
        toothChartOpenGL.setPrimary("2");
        toothChartOpenGL.setPrimary("3");
        toothChartOpenGL.setPrimary("4");
        toothChartOpenGL.setPrimary("5");
        toothChartOpenGL.setPrimary("6");
        toothChartOpenGL.setPrimary("7");
        toothChartOpenGL.setPrimary("8");
        toothChartOpenGL.setPrimary("9");
        toothChartOpenGL.setPrimary("10");
        toothChartOpenGL.setPrimary("11");
        toothChartOpenGL.setPrimary("12");
        toothChartOpenGL.setPrimary("13");
        toothChartOpenGL.setPrimary("14");
        toothChartOpenGL.setPrimary("15");
        toothChartOpenGL.setPrimary("16");
        toothChartOpenGL.setPrimary("17");
        toothChartOpenGL.setPrimary("18");
        toothChartOpenGL.setPrimary("19");
        toothChartOpenGL.setPrimary("20");
        toothChartOpenGL.setPrimary("21");
        toothChartOpenGL.setPrimary("22");
        toothChartOpenGL.setPrimary("23");
        toothChartOpenGL.setPrimary("24");
        toothChartOpenGL.setPrimary("25");
        toothChartOpenGL.setPrimary("26");
        toothChartOpenGL.setPrimary("27");
        toothChartOpenGL.setPrimary("28");
        toothChartOpenGL.setPrimary("29");
        toothChartOpenGL.setPrimary("30");
        toothChartOpenGL.setPrimary("31");
        toothChartOpenGL.setPrimary("32");
        //
        toothChartDirectX.setPrimary("1");
        toothChartDirectX.setPrimary("2");
        toothChartDirectX.setPrimary("3");
        toothChartDirectX.setPrimary("4");
        toothChartDirectX.setPrimary("5");
        toothChartDirectX.setPrimary("6");
        toothChartDirectX.setPrimary("7");
        toothChartDirectX.setPrimary("8");
        toothChartDirectX.setPrimary("9");
        toothChartDirectX.setPrimary("10");
        toothChartDirectX.setPrimary("11");
        toothChartDirectX.setPrimary("12");
        toothChartDirectX.setPrimary("13");
        toothChartDirectX.setPrimary("14");
        toothChartDirectX.setPrimary("15");
        toothChartDirectX.setPrimary("16");
        toothChartDirectX.setPrimary("17");
        toothChartDirectX.setPrimary("18");
        toothChartDirectX.setPrimary("19");
        toothChartDirectX.setPrimary("20");
        toothChartDirectX.setPrimary("21");
        toothChartDirectX.setPrimary("22");
        toothChartDirectX.setPrimary("23");
        toothChartDirectX.setPrimary("24");
        toothChartDirectX.setPrimary("25");
        toothChartDirectX.setPrimary("26");
        toothChartDirectX.setPrimary("27");
        toothChartDirectX.setPrimary("28");
        toothChartDirectX.setPrimary("29");
        toothChartDirectX.setPrimary("30");
        toothChartDirectX.setPrimary("31");
        toothChartDirectX.setPrimary("32");
    }

    private void butMixed_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //
        toothChart2D.setPrimary("1");
        toothChart2D.setPrimary("2");
        toothChart2D.setPrimary("4");
        toothChart2D.setPrimary("5");
        toothChart2D.setPrimary("6");
        toothChart2D.setPrimary("11");
        toothChart2D.setPrimary("12");
        toothChart2D.setPrimary("13");
        toothChart2D.setPrimary("15");
        toothChart2D.setPrimary("16");
        toothChart2D.setPrimary("17");
        toothChart2D.setPrimary("18");
        toothChart2D.setPrimary("20");
        toothChart2D.setPrimary("21");
        toothChart2D.setPrimary("22");
        toothChart2D.setPrimary("27");
        toothChart2D.setPrimary("28");
        toothChart2D.setPrimary("29");
        toothChart2D.setPrimary("31");
        toothChart2D.setPrimary("32");
        //
        toothChartOpenGL.setPrimary("1");
        toothChartOpenGL.setPrimary("2");
        toothChartOpenGL.setPrimary("4");
        toothChartOpenGL.setPrimary("5");
        toothChartOpenGL.setPrimary("6");
        toothChartOpenGL.setPrimary("11");
        toothChartOpenGL.setPrimary("12");
        toothChartOpenGL.setPrimary("13");
        toothChartOpenGL.setPrimary("15");
        toothChartOpenGL.setPrimary("16");
        toothChartOpenGL.setPrimary("17");
        toothChartOpenGL.setPrimary("18");
        toothChartOpenGL.setPrimary("20");
        toothChartOpenGL.setPrimary("21");
        toothChartOpenGL.setPrimary("22");
        toothChartOpenGL.setPrimary("27");
        toothChartOpenGL.setPrimary("28");
        toothChartOpenGL.setPrimary("29");
        toothChartOpenGL.setPrimary("31");
        toothChartOpenGL.setPrimary("32");
        //
        toothChartDirectX.setPrimary("1");
        toothChartDirectX.setPrimary("2");
        toothChartDirectX.setPrimary("4");
        toothChartDirectX.setPrimary("5");
        toothChartDirectX.setPrimary("6");
        toothChartDirectX.setPrimary("11");
        toothChartDirectX.setPrimary("12");
        toothChartDirectX.setPrimary("13");
        toothChartDirectX.setPrimary("15");
        toothChartDirectX.setPrimary("16");
        toothChartDirectX.setPrimary("17");
        toothChartDirectX.setPrimary("18");
        toothChartDirectX.setPrimary("20");
        toothChartDirectX.setPrimary("21");
        toothChartDirectX.setPrimary("22");
        toothChartDirectX.setPrimary("27");
        toothChartDirectX.setPrimary("28");
        toothChartDirectX.setPrimary("29");
        toothChartDirectX.setPrimary("31");
        toothChartDirectX.setPrimary("32");
    }

    private void panelColorBackgroundGray_Click(Object sender, EventArgs e) throws Exception {
        toothChartOpenGL.setColorBackground(panelColorBackgroundGray.BackColor);
        toothChartDirectX.setColorBackground(panelColorBackgroundGray.BackColor);
    }

    private void panelColorBackgroundLtGray_Click(Object sender, EventArgs e) throws Exception {
        toothChartOpenGL.setColorBackground(panelColorBackgroundLtGray.BackColor);
        toothChartDirectX.setColorBackground(panelColorBackgroundLtGray.BackColor);
    }

    private void panelColorBackgroundBlack_Click(Object sender, EventArgs e) throws Exception {
        toothChartOpenGL.setColorBackground(panelColorBackgroundBlack.BackColor);
        toothChartDirectX.setColorBackground(panelColorBackgroundBlack.BackColor);
    }

    private void panelColorBackgroundWhite_Click(Object sender, EventArgs e) throws Exception {
        toothChartOpenGL.setColorBackground(panelColorBackgroundWhite.BackColor);
        toothChartDirectX.setColorBackground(panelColorBackgroundWhite.BackColor);
    }

    private void panelColorBackgroundBlue_Click(Object sender, EventArgs e) throws Exception {
        toothChartOpenGL.setColorBackground(panelColorBackgroundBlue.BackColor);
        toothChartDirectX.setColorBackground(panelColorBackgroundBlue.BackColor);
    }

    private void panelColorTextGray_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorText(panelColorTextGray.BackColor);
        toothChartOpenGL.setColorText(panelColorTextGray.BackColor);
        toothChartDirectX.setColorText(panelColorTextGray.BackColor);
    }

    private void panelColorTextBlack_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorText(panelColorTextBlack.BackColor);
        toothChartOpenGL.setColorText(panelColorTextBlack.BackColor);
        toothChartDirectX.setColorText(panelColorTextBlack.BackColor);
    }

    private void panelColorTextWhite_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorText(panelColorTextWhite.BackColor);
        toothChartOpenGL.setColorText(panelColorTextWhite.BackColor);
        toothChartDirectX.setColorText(panelColorTextWhite.BackColor);
    }

    private void panelColorTextHighlightGray_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorTextHighlight(panelColorTextHighlightGray.BackColor);
        toothChartOpenGL.setColorTextHighlight(panelColorTextHighlightGray.BackColor);
        toothChartDirectX.setColorTextHighlight(panelColorTextHighlightGray.BackColor);
    }

    private void panelColorTextHighlightBlack_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorTextHighlight(panelColorTextHighlightBlack.BackColor);
        toothChartOpenGL.setColorTextHighlight(panelColorTextHighlightBlack.BackColor);
        toothChartDirectX.setColorTextHighlight(panelColorTextHighlightBlack.BackColor);
    }

    private void panelColorTextHighlightWhite_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorTextHighlight(panelColorTextHighlightWhite.BackColor);
        toothChartOpenGL.setColorTextHighlight(panelColorTextHighlightWhite.BackColor);
        toothChartDirectX.setColorTextHighlight(panelColorTextHighlightWhite.BackColor);
    }

    private void panelColorTextHighlightRed_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorTextHighlight(panelColorTextHighlightRed.BackColor);
        toothChartOpenGL.setColorTextHighlight(panelColorTextHighlightRed.BackColor);
        toothChartDirectX.setColorTextHighlight(panelColorTextHighlightRed.BackColor);
    }

    private void panelColorBackHighlightGray_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorBackHighlight(panelColorBackHighlightGray.BackColor);
        toothChartOpenGL.setColorBackHighlight(panelColorBackHighlightGray.BackColor);
        toothChartDirectX.setColorBackHighlight(panelColorBackHighlightGray.BackColor);
    }

    private void panelColorBackHighlightBlack_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorBackHighlight(panelColorBackHighlightBlack.BackColor);
        toothChartOpenGL.setColorBackHighlight(panelColorBackHighlightBlack.BackColor);
        toothChartDirectX.setColorBackHighlight(panelColorBackHighlightBlack.BackColor);
    }

    private void panelColorBackHighlightWhite_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorBackHighlight(panelColorBackHighlightWhite.BackColor);
        toothChartOpenGL.setColorBackHighlight(panelColorBackHighlightWhite.BackColor);
        toothChartDirectX.setColorBackHighlight(panelColorBackHighlightWhite.BackColor);
    }

    private void panelColorBackHighlightBlue_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setColorBackHighlight(panelColorBackHighlightBlue.BackColor);
        toothChartOpenGL.setColorBackHighlight(panelColorBackHighlightBlue.BackColor);
        toothChartDirectX.setColorBackHighlight(panelColorBackHighlightBlue.BackColor);
    }

    private void butSizeNormal_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.Size = new Size(410, 307);
        toothChartOpenGL.Size = new Size(410, 307);
        toothChartDirectX.Size = new Size(410, 307);
        toothChart2D.Location = new Point(8, 28);
        toothChartOpenGL.Location = new Point(424, 28);
        toothChartDirectX.Location = new Point(840, 28);
    }

    private void butSizeTall_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.Size = new Size(250, 307);
        toothChartOpenGL.Size = new Size(250, 307);
        toothChartDirectX.Size = new Size(250, 307);
    }

    private void butSizeWide_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.Size = new Size(410, 190);
        toothChartOpenGL.Size = new Size(410, 190);
        toothChartDirectX.Size = new Size(410, 190);
    }

    private void butVeryTall_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.Size = new Size(295, 700);
        toothChartOpenGL.Size = new Size(295, 700);
        toothChartDirectX.Size = new Size(295, 700);
        toothChart2D.Location = new Point(200, 28);
        toothChartOpenGL.Location = new Point(500, 28);
        toothChartDirectX.Location = new Point(800, 28);
    }

    private void butVeryWide_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.Size = new Size(1200, 200);
        toothChartOpenGL.Size = new Size(1200, 200);
        toothChartDirectX.Size = new Size(1200, 200);
        toothChart2D.Location = new Point(5, 5);
        toothChartOpenGL.Location = new Point(5, 210);
        toothChartDirectX.Location = new Point(5, 415);
    }

    private void butMissing_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //toothChart2D.  pointless
        toothChartOpenGL.setMissing("1");
        toothChartOpenGL.setMissing("3");
        toothChartOpenGL.setMissing("5");
        toothChartOpenGL.setMissing("7");
        toothChartOpenGL.setMissing("9");
        toothChartOpenGL.setMissing("11");
        toothChartOpenGL.setMissing("13");
        toothChartOpenGL.setMissing("15");
        toothChartOpenGL.setMissing("17");
        toothChartOpenGL.setMissing("19");
        toothChartOpenGL.setMissing("21");
        toothChartOpenGL.setMissing("23");
        toothChartOpenGL.setMissing("25");
        toothChartOpenGL.setMissing("27");
        toothChartOpenGL.setMissing("29");
        toothChartOpenGL.setMissing("31");
        //
        toothChartDirectX.setMissing("1");
        toothChartDirectX.setMissing("3");
        toothChartDirectX.setMissing("5");
        toothChartDirectX.setMissing("7");
        toothChartDirectX.setMissing("9");
        toothChartDirectX.setMissing("11");
        toothChartDirectX.setMissing("13");
        toothChartDirectX.setMissing("15");
        toothChartDirectX.setMissing("17");
        toothChartDirectX.setMissing("19");
        toothChartDirectX.setMissing("21");
        toothChartDirectX.setMissing("23");
        toothChartDirectX.setMissing("25");
        toothChartDirectX.setMissing("27");
        toothChartDirectX.setMissing("29");
        toothChartDirectX.setMissing("31");
    }

    private void butHidden_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //
        toothChart2D.setHidden("4");
        toothChart2D.setHidden("5");
        toothChart2D.setHidden("12");
        toothChart2D.setHidden("13");
        toothChart2D.setHidden("20");
        toothChart2D.setHidden("21");
        toothChart2D.setHidden("28");
        toothChart2D.setHidden("29");
        //
        toothChartOpenGL.setHidden("4");
        toothChartOpenGL.setHidden("5");
        toothChartOpenGL.setHidden("12");
        toothChartOpenGL.setHidden("13");
        toothChartOpenGL.setHidden("20");
        toothChartOpenGL.setHidden("21");
        toothChartOpenGL.setHidden("28");
        toothChartOpenGL.setHidden("29");
        //
        toothChartDirectX.setHidden("4");
        toothChartDirectX.setHidden("5");
        toothChartDirectX.setHidden("12");
        toothChartDirectX.setHidden("13");
        toothChartDirectX.setHidden("20");
        toothChartDirectX.setHidden("21");
        toothChartDirectX.setHidden("28");
        toothChartDirectX.setHidden("29");
    }

    private void butMissingHiddenComplex_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //
        toothChart2D.setPrimary("4");
        toothChart2D.setPrimary("5");
        toothChart2D.setPrimary("6");
        toothChart2D.setPrimary("7");
        toothChart2D.setPrimary("8");
        toothChart2D.setPrimary("9");
        toothChart2D.setPrimary("10");
        toothChart2D.setPrimary("11");
        toothChart2D.setPrimary("12");
        toothChart2D.setPrimary("13");
        toothChart2D.setPrimary("14");
        toothChart2D.setPrimary("15");
        toothChart2D.setPrimary("16");
        toothChart2D.setMissing("4");
        toothChart2D.setMissing("B");
        toothChart2D.setMissing("6");
        toothChart2D.setMissing("D");
        toothChart2D.setHidden("G");
        toothChart2D.setHidden("11");
        toothChart2D.setHidden("I");
        toothChart2D.setHidden("13");
        toothChart2D.setHidden("14");
        //bottom
        toothChart2D.setPrimary("29");
        toothChart2D.setPrimary("28");
        toothChart2D.setPrimary("27");
        toothChart2D.setPrimary("26");
        toothChart2D.setPrimary("25");
        toothChart2D.setPrimary("24");
        toothChart2D.setPrimary("23");
        toothChart2D.setPrimary("22");
        toothChart2D.setPrimary("21");
        toothChart2D.setPrimary("20");
        toothChart2D.setPrimary("19");
        toothChart2D.setPrimary("18");
        toothChart2D.setPrimary("17");
        toothChart2D.setMissing("29");
        toothChart2D.setMissing("S");
        toothChart2D.setMissing("27");
        toothChart2D.setMissing("Q");
        toothChart2D.setHidden("N");
        toothChart2D.setHidden("22");
        toothChart2D.setHidden("L");
        toothChart2D.setHidden("20");
        toothChart2D.setHidden("19");
        //
        toothChartOpenGL.setPrimary("4");
        toothChartOpenGL.setPrimary("5");
        toothChartOpenGL.setPrimary("6");
        toothChartOpenGL.setPrimary("7");
        toothChartOpenGL.setPrimary("8");
        toothChartOpenGL.setPrimary("9");
        toothChartOpenGL.setPrimary("10");
        toothChartOpenGL.setPrimary("11");
        toothChartOpenGL.setPrimary("12");
        toothChartOpenGL.setPrimary("13");
        toothChartOpenGL.setPrimary("14");
        toothChartOpenGL.setPrimary("15");
        toothChartOpenGL.setPrimary("16");
        toothChartOpenGL.setMissing("4");
        toothChartOpenGL.setMissing("B");
        toothChartOpenGL.setMissing("6");
        toothChartOpenGL.setMissing("D");
        toothChartOpenGL.setHidden("G");
        toothChartOpenGL.setHidden("11");
        toothChartOpenGL.setHidden("I");
        toothChartOpenGL.setHidden("13");
        toothChartOpenGL.setHidden("14");
        //mirror image on the bottom:
        toothChartOpenGL.setPrimary("29");
        toothChartOpenGL.setPrimary("28");
        toothChartOpenGL.setPrimary("27");
        toothChartOpenGL.setPrimary("26");
        toothChartOpenGL.setPrimary("25");
        toothChartOpenGL.setPrimary("24");
        toothChartOpenGL.setPrimary("23");
        toothChartOpenGL.setPrimary("22");
        toothChartOpenGL.setPrimary("21");
        toothChartOpenGL.setPrimary("20");
        toothChartOpenGL.setPrimary("19");
        toothChartOpenGL.setPrimary("18");
        toothChartOpenGL.setPrimary("17");
        toothChartOpenGL.setMissing("29");
        toothChartOpenGL.setMissing("S");
        toothChartOpenGL.setMissing("27");
        toothChartOpenGL.setMissing("Q");
        toothChartOpenGL.setHidden("N");
        toothChartOpenGL.setHidden("22");
        toothChartOpenGL.setHidden("L");
        toothChartOpenGL.setHidden("20");
        toothChartOpenGL.setHidden("19");
        //
        toothChartDirectX.setPrimary("4");
        toothChartDirectX.setPrimary("5");
        toothChartDirectX.setPrimary("6");
        toothChartDirectX.setPrimary("7");
        toothChartDirectX.setPrimary("8");
        toothChartDirectX.setPrimary("9");
        toothChartDirectX.setPrimary("10");
        toothChartDirectX.setPrimary("11");
        toothChartDirectX.setPrimary("12");
        toothChartDirectX.setPrimary("13");
        toothChartDirectX.setPrimary("14");
        toothChartDirectX.setPrimary("15");
        toothChartDirectX.setPrimary("16");
        toothChartDirectX.setMissing("4");
        toothChartDirectX.setMissing("B");
        toothChartDirectX.setMissing("6");
        toothChartDirectX.setMissing("D");
        toothChartDirectX.setHidden("G");
        toothChartDirectX.setHidden("11");
        toothChartDirectX.setHidden("I");
        toothChartDirectX.setHidden("13");
        toothChartDirectX.setHidden("14");
        //bottom
        toothChartDirectX.setPrimary("29");
        toothChartDirectX.setPrimary("28");
        toothChartDirectX.setPrimary("27");
        toothChartDirectX.setPrimary("26");
        toothChartDirectX.setPrimary("25");
        toothChartDirectX.setPrimary("24");
        toothChartDirectX.setPrimary("23");
        toothChartDirectX.setPrimary("22");
        toothChartDirectX.setPrimary("21");
        toothChartDirectX.setPrimary("20");
        toothChartDirectX.setPrimary("19");
        toothChartDirectX.setPrimary("18");
        toothChartDirectX.setPrimary("17");
        toothChartDirectX.setMissing("29");
        toothChartDirectX.setMissing("S");
        toothChartDirectX.setMissing("27");
        toothChartDirectX.setMissing("Q");
        toothChartDirectX.setHidden("N");
        toothChartDirectX.setHidden("22");
        toothChartDirectX.setHidden("L");
        toothChartDirectX.setHidden("20");
        toothChartDirectX.setHidden("19");
    }

    private void butWatches_Click(Object sender, EventArgs e) throws Exception {
        for (int i = 1;i <= 32;i++)
        {
            //toothChart2D.ResetTeeth();
            //toothChartOpenGL.ResetTeeth();
            //toothChartDirectX.ResetTeeth();
            String toothNum = i.ToString();
            toothChart2D.SetWatch(toothNum, Color.Red);
            toothChartOpenGL.SetWatch(toothNum, Color.Red);
            toothChartDirectX.SetWatch(toothNum, Color.Red);
        }
    }

    private void butFillings_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //
        toothChart2D.SetCrown("1", Color.DarkGreen);
        toothChart2D.SetSurfaceColors("3", "MOD", Color.DarkRed);
        toothChart2D.SetSurfaceColors("4", "V", Color.Green);
        toothChart2D.SetSurfaceColors("5", "B", Color.Green);
        toothChart2D.SetSurfaceColors("6", "FIL", Color.DarkRed);
        toothChart2D.setPrimary("11");
        toothChart2D.setMissing("11");
        toothChart2D.SetSurfaceColors("H", "MOD", Color.DarkRed);
        //some invalid surfaces
        toothChart2D.setPrimary("12");
        toothChart2D.SetSurfaceColors("I", "MOD", Color.DarkRed);
        toothChart2D.SetSurfaceColors("J", "O", Color.DarkRed);
        //should not show
        toothChart2D.setHidden("14");
        toothChart2D.SetSurfaceColors("14", "MOD", Color.DarkRed);
        //should not show
        toothChart2D.SetCrown("15", Color.DarkRed);
        toothChart2D.SetCrown("17", Color.DarkBlue);
        toothChart2D.SetCrown("18", Color.DarkBlue);
        toothChart2D.SetCrown("19", Color.DarkBlue);
        toothChart2D.SetCrown("20", Color.LightBlue);
        toothChart2D.SetCrown("21", Color.LightBlue);
        toothChart2D.SetCrown("22", Color.LightBlue);
        toothChart2D.SetCrown("31", Color.White);
        toothChart2D.SetCrown("32", Color.Black);
        //
        toothChartOpenGL.SetCrown("1", Color.DarkGreen);
        toothChartOpenGL.SetSurfaceColors("3", "MOD", Color.DarkRed);
        toothChartOpenGL.SetSurfaceColors("4", "V", Color.Green);
        toothChartOpenGL.SetSurfaceColors("5", "B", Color.Green);
        toothChartOpenGL.SetSurfaceColors("6", "FIL", Color.DarkRed);
        toothChartOpenGL.setPrimary("11");
        toothChartOpenGL.setMissing("11");
        toothChartOpenGL.SetSurfaceColors("H", "MOD", Color.DarkRed);
        //some invalid surfaces
        toothChartOpenGL.setPrimary("12");
        toothChartOpenGL.SetSurfaceColors("I", "MOD", Color.DarkRed);
        toothChartOpenGL.SetSurfaceColors("J", "O", Color.DarkRed);
        //should not show
        toothChartOpenGL.setHidden("14");
        toothChartOpenGL.SetSurfaceColors("14", "MOD", Color.DarkRed);
        //should not show
        toothChartOpenGL.SetCrown("15", Color.DarkRed);
        toothChartOpenGL.SetCrown("17", Color.DarkBlue);
        toothChartOpenGL.SetCrown("18", Color.DarkBlue);
        toothChartOpenGL.SetCrown("19", Color.DarkBlue);
        toothChartOpenGL.SetCrown("20", Color.LightBlue);
        toothChartOpenGL.SetCrown("21", Color.LightBlue);
        toothChartOpenGL.SetCrown("22", Color.LightBlue);
        toothChartOpenGL.SetCrown("31", Color.White);
        toothChartOpenGL.SetCrown("32", Color.Black);
        //
        toothChartDirectX.SetCrown("1", Color.DarkGreen);
        toothChartDirectX.SetSurfaceColors("3", "MOD", Color.DarkRed);
        toothChartDirectX.SetSurfaceColors("4", "V", Color.Green);
        toothChartDirectX.SetSurfaceColors("5", "B", Color.Green);
        toothChartDirectX.SetSurfaceColors("6", "FIL", Color.DarkRed);
        toothChartDirectX.setPrimary("11");
        toothChartDirectX.setMissing("11");
        toothChartDirectX.SetSurfaceColors("H", "MOD", Color.DarkRed);
        //some invalid surfaces
        toothChartDirectX.setPrimary("12");
        toothChartDirectX.SetSurfaceColors("I", "MOD", Color.DarkRed);
        toothChartDirectX.SetSurfaceColors("J", "O", Color.DarkRed);
        //should not show
        toothChartDirectX.setHidden("14");
        toothChartDirectX.SetSurfaceColors("14", "MOD", Color.DarkRed);
        //should not show
        toothChartDirectX.SetCrown("15", Color.DarkRed);
        toothChartDirectX.SetCrown("17", Color.DarkBlue);
        toothChartDirectX.SetCrown("18", Color.DarkBlue);
        toothChartDirectX.SetCrown("19", Color.DarkBlue);
        toothChartDirectX.SetCrown("20", Color.LightBlue);
        toothChartDirectX.SetCrown("21", Color.LightBlue);
        toothChartDirectX.SetCrown("22", Color.LightBlue);
        toothChartDirectX.SetCrown("31", Color.White);
        toothChartDirectX.SetCrown("32", Color.Black);
    }

    private void butMouse_Click(Object sender, EventArgs e) throws Exception {
    }

    //toothChartOpenGL.
    private void butRCT_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        String tooth_id = new String();
        for (int i = 1;i <= 32;i++)
        {
            tooth_id = i.ToString();
            toothChart2D.SetRCT(tooth_id, Color.DarkRed);
            toothChart2D.SetBU(tooth_id, Color.DarkRed);
            toothChartOpenGL.SetRCT(tooth_id, Color.DarkRed);
            toothChartOpenGL.SetBU(tooth_id, Color.DarkRed);
            toothChartDirectX.SetRCT(tooth_id, Color.DarkRed);
            toothChartDirectX.SetBU(tooth_id, Color.DarkRed);
        }
    }

    private void butPrimaryBU_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        String tooth_id = new String();
        for (int i = 1;i <= 32;i++)
        {
            tooth_id = i.ToString();
            toothChart2D.setPrimary(tooth_id);
            toothChartOpenGL.setPrimary(tooth_id);
            toothChartDirectX.setPrimary(tooth_id);
        }
        toothChart2D.SetBU("A", Color.DarkRed);
        toothChart2D.SetBU("B", Color.DarkRed);
        toothChart2D.SetBU("C", Color.DarkRed);
        toothChart2D.SetBU("D", Color.DarkRed);
        toothChart2D.SetBU("E", Color.DarkRed);
        toothChart2D.SetBU("F", Color.DarkRed);
        toothChart2D.SetBU("G", Color.DarkRed);
        toothChart2D.SetBU("H", Color.DarkRed);
        toothChart2D.SetBU("I", Color.DarkRed);
        toothChart2D.SetBU("J", Color.DarkRed);
        toothChart2D.SetBU("K", Color.DarkRed);
        toothChart2D.SetBU("L", Color.DarkRed);
        toothChart2D.SetBU("M", Color.DarkRed);
        toothChart2D.SetBU("N", Color.DarkRed);
        toothChart2D.SetBU("O", Color.DarkRed);
        toothChart2D.SetBU("P", Color.DarkRed);
        toothChart2D.SetBU("Q", Color.DarkRed);
        toothChart2D.SetBU("R", Color.DarkRed);
        toothChart2D.SetBU("S", Color.DarkRed);
        toothChart2D.SetBU("T", Color.DarkRed);
        //
        toothChartOpenGL.SetBU("A", Color.DarkRed);
        toothChartOpenGL.SetBU("B", Color.DarkRed);
        toothChartOpenGL.SetBU("C", Color.DarkRed);
        toothChartOpenGL.SetBU("D", Color.DarkRed);
        toothChartOpenGL.SetBU("E", Color.DarkRed);
        toothChartOpenGL.SetBU("F", Color.DarkRed);
        toothChartOpenGL.SetBU("G", Color.DarkRed);
        toothChartOpenGL.SetBU("H", Color.DarkRed);
        toothChartOpenGL.SetBU("I", Color.DarkRed);
        toothChartOpenGL.SetBU("J", Color.DarkRed);
        toothChartOpenGL.SetBU("K", Color.DarkRed);
        toothChartOpenGL.SetBU("L", Color.DarkRed);
        toothChartOpenGL.SetBU("M", Color.DarkRed);
        toothChartOpenGL.SetBU("N", Color.DarkRed);
        toothChartOpenGL.SetBU("O", Color.DarkRed);
        toothChartOpenGL.SetBU("P", Color.DarkRed);
        toothChartOpenGL.SetBU("Q", Color.DarkRed);
        toothChartOpenGL.SetBU("R", Color.DarkRed);
        toothChartOpenGL.SetBU("S", Color.DarkRed);
        toothChartOpenGL.SetBU("T", Color.DarkRed);
        //
        toothChartDirectX.SetBU("A", Color.DarkRed);
        toothChartDirectX.SetBU("B", Color.DarkRed);
        toothChartDirectX.SetBU("C", Color.DarkRed);
        toothChartDirectX.SetBU("D", Color.DarkRed);
        toothChartDirectX.SetBU("E", Color.DarkRed);
        toothChartDirectX.SetBU("F", Color.DarkRed);
        toothChartDirectX.SetBU("G", Color.DarkRed);
        toothChartDirectX.SetBU("H", Color.DarkRed);
        toothChartDirectX.SetBU("I", Color.DarkRed);
        toothChartDirectX.SetBU("J", Color.DarkRed);
        toothChartDirectX.SetBU("K", Color.DarkRed);
        toothChartDirectX.SetBU("L", Color.DarkRed);
        toothChartDirectX.SetBU("M", Color.DarkRed);
        toothChartDirectX.SetBU("N", Color.DarkRed);
        toothChartDirectX.SetBU("O", Color.DarkRed);
        toothChartDirectX.SetBU("P", Color.DarkRed);
        toothChartDirectX.SetBU("Q", Color.DarkRed);
        toothChartDirectX.SetBU("R", Color.DarkRed);
        toothChartDirectX.SetBU("S", Color.DarkRed);
        toothChartDirectX.SetBU("T", Color.DarkRed);
    }

    private void butBigX_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        String tooth_id = new String();
        for (int i = 1;i <= 32;i++)
        {
            tooth_id = i.ToString();
            toothChart2D.SetBigX(tooth_id, Color.DarkRed);
            toothChartOpenGL.SetBigX(tooth_id, Color.DarkRed);
            toothChartDirectX.SetBigX(tooth_id, Color.DarkRed);
        }
    }

    private void butBridges_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //
        toothChart2D.setMissing("3");
        toothChart2D.SetCrown("2", Color.SteelBlue);
        toothChart2D.SetPontic("3", Color.SteelBlue);
        toothChart2D.SetCrown("4", Color.SteelBlue);
        toothChart2D.setMissing("8");
        toothChart2D.setMissing("9");
        toothChart2D.SetCrown("7", Color.SteelBlue);
        toothChart2D.SetPontic("8", Color.SteelBlue);
        toothChart2D.SetPontic("9", Color.SteelBlue);
        toothChart2D.SetCrown("10", Color.SteelBlue);
        toothChart2D.setMissing("19");
        toothChart2D.SetCrown("18", Color.SteelBlue);
        toothChart2D.SetPontic("19", Color.SteelBlue);
        toothChart2D.SetCrown("20", Color.SteelBlue);
        toothChart2D.setMissing("26");
        toothChart2D.setMissing("27");
        toothChart2D.SetCrown("25", Color.SteelBlue);
        toothChart2D.SetPontic("26", Color.SteelBlue);
        toothChart2D.SetPontic("27", Color.SteelBlue);
        toothChart2D.SetCrown("28", Color.SteelBlue);
        //
        toothChartOpenGL.setMissing("3");
        toothChartOpenGL.SetCrown("2", Color.SteelBlue);
        toothChartOpenGL.SetPontic("3", Color.SteelBlue);
        toothChartOpenGL.SetCrown("4", Color.SteelBlue);
        toothChartOpenGL.setMissing("8");
        toothChartOpenGL.setMissing("9");
        toothChartOpenGL.SetCrown("7", Color.SteelBlue);
        toothChartOpenGL.SetPontic("8", Color.SteelBlue);
        toothChartOpenGL.SetPontic("9", Color.SteelBlue);
        toothChartOpenGL.SetCrown("10", Color.SteelBlue);
        toothChartOpenGL.setMissing("19");
        toothChartOpenGL.SetCrown("18", Color.SteelBlue);
        toothChartOpenGL.SetPontic("19", Color.SteelBlue);
        toothChartOpenGL.SetCrown("20", Color.SteelBlue);
        toothChartOpenGL.setMissing("26");
        toothChartOpenGL.setMissing("27");
        toothChartOpenGL.SetCrown("25", Color.SteelBlue);
        toothChartOpenGL.SetPontic("26", Color.SteelBlue);
        toothChartOpenGL.SetPontic("27", Color.SteelBlue);
        toothChartOpenGL.SetCrown("28", Color.SteelBlue);
        //
        toothChartDirectX.setMissing("3");
        toothChartDirectX.SetCrown("2", Color.SteelBlue);
        toothChartDirectX.SetPontic("3", Color.SteelBlue);
        toothChartDirectX.SetCrown("4", Color.SteelBlue);
        toothChartDirectX.setMissing("8");
        toothChartDirectX.setMissing("9");
        toothChartDirectX.SetCrown("7", Color.SteelBlue);
        toothChartDirectX.SetPontic("8", Color.SteelBlue);
        toothChartDirectX.SetPontic("9", Color.SteelBlue);
        toothChartDirectX.SetCrown("10", Color.SteelBlue);
        toothChartDirectX.setMissing("19");
        toothChartDirectX.SetCrown("18", Color.SteelBlue);
        toothChartDirectX.SetPontic("19", Color.SteelBlue);
        toothChartDirectX.SetCrown("20", Color.SteelBlue);
        toothChartDirectX.setMissing("26");
        toothChartDirectX.setMissing("27");
        toothChartDirectX.SetCrown("25", Color.SteelBlue);
        toothChartDirectX.SetPontic("26", Color.SteelBlue);
        toothChartDirectX.SetPontic("27", Color.SteelBlue);
        toothChartDirectX.SetCrown("28", Color.SteelBlue);
    }

    private void butImplants_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //
        toothChart2D.setMissing("3");
        toothChart2D.SetImplant("3", Color.Silver);
        toothChart2D.SetCrown("3", Color.SteelBlue);
        toothChart2D.setMissing("7");
        toothChart2D.setMissing("8");
        toothChart2D.setMissing("9");
        toothChart2D.setMissing("10");
        toothChart2D.SetImplant("7", Color.Silver);
        toothChart2D.SetImplant("10", Color.Silver);
        toothChart2D.SetCrown("7", Color.SteelBlue);
        toothChart2D.SetPontic("8", Color.SteelBlue);
        toothChart2D.SetPontic("9", Color.SteelBlue);
        toothChart2D.SetCrown("10", Color.SteelBlue);
        toothChart2D.setMissing("19");
        toothChart2D.setMissing("20");
        toothChart2D.SetImplant("19", Color.Silver);
        toothChart2D.SetImplant("20", Color.Silver);
        toothChart2D.SetCrown("19", Color.SteelBlue);
        toothChart2D.SetCrown("20", Color.SteelBlue);
        //
        toothChartOpenGL.setMissing("3");
        toothChartOpenGL.SetImplant("3", Color.Silver);
        toothChartOpenGL.SetCrown("3", Color.SteelBlue);
        toothChartOpenGL.setMissing("7");
        toothChartOpenGL.setMissing("8");
        toothChartOpenGL.setMissing("9");
        toothChartOpenGL.setMissing("10");
        toothChartOpenGL.SetImplant("7", Color.Silver);
        toothChartOpenGL.SetImplant("10", Color.Silver);
        toothChartOpenGL.SetCrown("7", Color.SteelBlue);
        toothChartOpenGL.SetPontic("8", Color.SteelBlue);
        toothChartOpenGL.SetPontic("9", Color.SteelBlue);
        toothChartOpenGL.SetCrown("10", Color.SteelBlue);
        toothChartOpenGL.setMissing("19");
        toothChartOpenGL.setMissing("20");
        toothChartOpenGL.SetImplant("19", Color.Silver);
        toothChartOpenGL.SetImplant("20", Color.Silver);
        toothChartOpenGL.SetCrown("19", Color.SteelBlue);
        toothChartOpenGL.SetCrown("20", Color.SteelBlue);
        //
        toothChartDirectX.setMissing("3");
        toothChartDirectX.SetImplant("3", Color.Silver);
        toothChartDirectX.SetCrown("3", Color.SteelBlue);
        toothChartDirectX.setMissing("7");
        toothChartDirectX.setMissing("8");
        toothChartDirectX.setMissing("9");
        toothChartDirectX.setMissing("10");
        toothChartDirectX.SetImplant("7", Color.Silver);
        toothChartDirectX.SetImplant("10", Color.Silver);
        toothChartDirectX.SetCrown("7", Color.SteelBlue);
        toothChartDirectX.SetPontic("8", Color.SteelBlue);
        toothChartDirectX.SetPontic("9", Color.SteelBlue);
        toothChartDirectX.SetCrown("10", Color.SteelBlue);
        toothChartDirectX.setMissing("19");
        toothChartDirectX.setMissing("20");
        toothChartDirectX.SetImplant("19", Color.Silver);
        toothChartDirectX.SetImplant("20", Color.Silver);
        toothChartDirectX.SetCrown("19", Color.SteelBlue);
        toothChartDirectX.SetCrown("20", Color.SteelBlue);
    }

    private void butSealants_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //
        toothChart2D.SetSealant("2", Color.Red);
        toothChart2D.SetSealant("3", Color.Red);
        toothChart2D.SetSealant("4", Color.Red);
        toothChart2D.SetSealant("13", Color.Red);
        toothChart2D.SetSealant("14", Color.Red);
        toothChart2D.SetSealant("15", Color.Red);
        toothChart2D.SetSealant("18", Color.Red);
        toothChart2D.SetSealant("19", Color.Red);
        toothChart2D.SetSealant("20", Color.Red);
        toothChart2D.SetSealant("29", Color.Red);
        toothChart2D.SetSealant("30", Color.Red);
        toothChart2D.SetSealant("31", Color.Red);
        //
        toothChartOpenGL.SetSealant("2", Color.Red);
        toothChartOpenGL.SetSealant("3", Color.Red);
        toothChartOpenGL.SetSealant("4", Color.Red);
        toothChartOpenGL.SetSealant("13", Color.Red);
        toothChartOpenGL.SetSealant("14", Color.Red);
        toothChartOpenGL.SetSealant("15", Color.Red);
        toothChartOpenGL.SetSealant("18", Color.Red);
        toothChartOpenGL.SetSealant("19", Color.Red);
        toothChartOpenGL.SetSealant("20", Color.Red);
        toothChartOpenGL.SetSealant("29", Color.Red);
        toothChartOpenGL.SetSealant("30", Color.Red);
        toothChartOpenGL.SetSealant("31", Color.Red);
        //
        toothChartDirectX.SetSealant("2", Color.Red);
        toothChartDirectX.SetSealant("3", Color.Red);
        toothChartDirectX.SetSealant("4", Color.Red);
        toothChartDirectX.SetSealant("13", Color.Red);
        toothChartDirectX.SetSealant("14", Color.Red);
        toothChartDirectX.SetSealant("15", Color.Red);
        toothChartDirectX.SetSealant("18", Color.Red);
        toothChartDirectX.SetSealant("19", Color.Red);
        toothChartDirectX.SetSealant("20", Color.Red);
        toothChartDirectX.SetSealant("29", Color.Red);
        toothChartDirectX.SetSealant("30", Color.Red);
        toothChartDirectX.SetSealant("31", Color.Red);
    }

    private void butVeneers_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.resetTeeth();
        toothChartOpenGL.resetTeeth();
        toothChartDirectX.resetTeeth();
        //
        toothChart2D.SetVeneer("4", Color.DarkRed);
        toothChart2D.SetVeneer("5", Color.DarkRed);
        toothChart2D.SetVeneer("6", Color.DarkRed);
        toothChart2D.SetVeneer("7", Color.DarkRed);
        toothChart2D.SetVeneer("8", Color.DarkRed);
        toothChart2D.SetVeneer("9", Color.DarkRed);
        toothChart2D.SetVeneer("10", Color.DarkRed);
        toothChart2D.SetVeneer("11", Color.DarkRed);
        toothChart2D.SetVeneer("12", Color.DarkRed);
        toothChart2D.SetVeneer("13", Color.DarkRed);
        toothChart2D.SetVeneer("20", Color.DarkRed);
        toothChart2D.SetVeneer("21", Color.DarkRed);
        toothChart2D.SetVeneer("22", Color.DarkRed);
        toothChart2D.SetVeneer("23", Color.DarkRed);
        toothChart2D.SetVeneer("24", Color.DarkRed);
        toothChart2D.SetVeneer("25", Color.DarkRed);
        toothChart2D.SetVeneer("26", Color.DarkRed);
        toothChart2D.SetVeneer("27", Color.DarkRed);
        toothChart2D.SetVeneer("28", Color.DarkRed);
        toothChart2D.SetVeneer("29", Color.DarkRed);
        //
        toothChartOpenGL.SetVeneer("4", Color.DarkRed);
        toothChartOpenGL.SetVeneer("5", Color.DarkRed);
        toothChartOpenGL.SetVeneer("6", Color.DarkRed);
        toothChartOpenGL.SetVeneer("7", Color.DarkRed);
        toothChartOpenGL.SetVeneer("8", Color.DarkRed);
        toothChartOpenGL.SetVeneer("9", Color.DarkRed);
        toothChartOpenGL.SetVeneer("10", Color.DarkRed);
        toothChartOpenGL.SetVeneer("11", Color.DarkRed);
        toothChartOpenGL.SetVeneer("12", Color.DarkRed);
        toothChartOpenGL.SetVeneer("13", Color.DarkRed);
        toothChartOpenGL.SetVeneer("20", Color.DarkRed);
        toothChartOpenGL.SetVeneer("21", Color.DarkRed);
        toothChartOpenGL.SetVeneer("22", Color.DarkRed);
        toothChartOpenGL.SetVeneer("23", Color.DarkRed);
        toothChartOpenGL.SetVeneer("24", Color.DarkRed);
        toothChartOpenGL.SetVeneer("25", Color.DarkRed);
        toothChartOpenGL.SetVeneer("26", Color.DarkRed);
        toothChartOpenGL.SetVeneer("27", Color.DarkRed);
        toothChartOpenGL.SetVeneer("28", Color.DarkRed);
        toothChartOpenGL.SetVeneer("29", Color.DarkRed);
        //
        toothChartDirectX.SetVeneer("4", Color.DarkRed);
        toothChartDirectX.SetVeneer("5", Color.DarkRed);
        toothChartDirectX.SetVeneer("6", Color.DarkRed);
        toothChartDirectX.SetVeneer("7", Color.DarkRed);
        toothChartDirectX.SetVeneer("8", Color.DarkRed);
        toothChartDirectX.SetVeneer("9", Color.DarkRed);
        toothChartDirectX.SetVeneer("10", Color.DarkRed);
        toothChartDirectX.SetVeneer("11", Color.DarkRed);
        toothChartDirectX.SetVeneer("12", Color.DarkRed);
        toothChartDirectX.SetVeneer("13", Color.DarkRed);
        toothChartDirectX.SetVeneer("20", Color.DarkRed);
        toothChartDirectX.SetVeneer("21", Color.DarkRed);
        toothChartDirectX.SetVeneer("22", Color.DarkRed);
        toothChartDirectX.SetVeneer("23", Color.DarkRed);
        toothChartDirectX.SetVeneer("24", Color.DarkRed);
        toothChartDirectX.SetVeneer("25", Color.DarkRed);
        toothChartDirectX.SetVeneer("26", Color.DarkRed);
        toothChartDirectX.SetVeneer("27", Color.DarkRed);
        toothChartDirectX.SetVeneer("28", Color.DarkRed);
        toothChartDirectX.SetVeneer("29", Color.DarkRed);
    }

    private void radioPointer_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setCursorTool(SparksToothChart.CursorTool.Pointer);
        toothChartOpenGL.setCursorTool(SparksToothChart.CursorTool.Pointer);
        toothChartDirectX.setCursorTool(SparksToothChart.CursorTool.Pointer);
    }

    private void radioPen_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setCursorTool(SparksToothChart.CursorTool.Pen);
        toothChartOpenGL.setCursorTool(SparksToothChart.CursorTool.Pen);
        toothChartDirectX.setCursorTool(SparksToothChart.CursorTool.Pen);
    }

    private void radioEraser_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setCursorTool(SparksToothChart.CursorTool.Eraser);
        toothChartOpenGL.setCursorTool(SparksToothChart.CursorTool.Eraser);
        toothChartDirectX.setCursorTool(SparksToothChart.CursorTool.Eraser);
    }

    private void radioColorChanger_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setCursorTool(SparksToothChart.CursorTool.ColorChanger);
        toothChartOpenGL.setCursorTool(SparksToothChart.CursorTool.ColorChanger);
        toothChartDirectX.setCursorTool(SparksToothChart.CursorTool.ColorChanger);
    }

    private void panelColorDrawBlack_Click(Object sender, EventArgs e) throws Exception {
        panelColorDraw.BackColor = panelColorDrawBlack.BackColor;
        toothChart2D.setColorDrawing(panelColorDraw.BackColor);
        toothChartOpenGL.setColorDrawing(panelColorDraw.BackColor);
        toothChartDirectX.setColorDrawing(panelColorDraw.BackColor);
    }

    private void panelColorDrawRed_Click(Object sender, EventArgs e) throws Exception {
        panelColorDraw.BackColor = panelColorDrawRed.BackColor;
        toothChart2D.setColorDrawing(panelColorDraw.BackColor);
        toothChartOpenGL.setColorDrawing(panelColorDraw.BackColor);
        toothChartDirectX.setColorDrawing(panelColorDraw.BackColor);
    }

    private void panelColorDrawBlue_Click(Object sender, EventArgs e) throws Exception {
        panelColorDraw.BackColor = panelColorDrawBlue.BackColor;
        toothChart2D.setColorDrawing(panelColorDraw.BackColor);
        toothChartOpenGL.setColorDrawing(panelColorDraw.BackColor);
        toothChartDirectX.setColorDrawing(panelColorDraw.BackColor);
    }

    private void panelColorDrawGreen_Click(Object sender, EventArgs e) throws Exception {
        panelColorDraw.BackColor = panelColorDrawGreen.BackColor;
        toothChart2D.setColorDrawing(panelColorDraw.BackColor);
        toothChartOpenGL.setColorDrawing(panelColorDraw.BackColor);
        toothChartDirectX.setColorDrawing(panelColorDraw.BackColor);
    }

    private void panelColorDrawWhite_Click(Object sender, EventArgs e) throws Exception {
        panelColorDraw.BackColor = panelColorDrawWhite.BackColor;
        toothChart2D.setColorDrawing(panelColorDraw.BackColor);
        toothChartOpenGL.setColorDrawing(panelColorDraw.BackColor);
        toothChartDirectX.setColorDrawing(panelColorDraw.BackColor);
    }

    private void butColorDrawOther_Click(Object sender, EventArgs e) throws Exception {
        ColorDialog cd = new ColorDialog();
        cd.Color = panelColorDraw.BackColor;
        if (cd.ShowDialog() != DialogResult.OK)
        {
            return ;
        }
         
        panelColorDraw.BackColor = cd.Color;
        toothChart2D.setColorDrawing(panelColorDraw.BackColor);
        toothChartOpenGL.setColorDrawing(panelColorDraw.BackColor);
        toothChartDirectX.setColorDrawing(panelColorDraw.BackColor);
    }

    private void butShowDrawing_Click(Object sender, EventArgs e) throws Exception {
        ToothInitial ti = new ToothInitial();
        ti.ColorDraw = Color.Blue;
        ti.DrawingSegment = "30,30;70,30;70,70;50,80;50,90;30,70;30,30";
        ti.InitialType = ToothInitialType.Drawing;
        toothChart2D.addDrawingSegment(ti);
        toothChartOpenGL.addDrawingSegment(ti);
        toothChartDirectX.addDrawingSegment(ti);
    }

    private void butGetBitmap2D_Click(Object sender, EventArgs e) throws Exception {
        textScreenshotBox.Visible = true;
        Application.DoEvents();
        FormImageViewer form = new FormImageViewer();
        form.Bmp = toothChart2D.getBitmap();
        form.Show();
        textScreenshotBox.Visible = false;
    }

    private void butGetBitmapOpenGL_Click(Object sender, EventArgs e) throws Exception {
        textScreenshotBox.Visible = true;
        Application.DoEvents();
        FormImageViewer form = new FormImageViewer();
        form.Bmp = toothChartOpenGL.getBitmap();
        form.Show();
        textScreenshotBox.Visible = false;
    }

    private void butGetBitmapDirectX_Click(Object sender, EventArgs e) throws Exception {
        textScreenshotBox.Visible = true;
        Application.DoEvents();
        FormImageViewer form = new FormImageViewer();
        form.Bmp = toothChartDirectX.getBitmap();
        form.Show();
        textScreenshotBox.Visible = false;
    }

    private void butFullscreen2D_Click(Object sender, EventArgs e) throws Exception {
        FormFullScreen form = new FormFullScreen();
        form.toothChartForBig.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        form.toothChartForBig.setTcData(toothChartOpenGL.getTcData().copy());
        form.ShowDialog();
    }

    private void butFullscreenOpenGL_Click(Object sender, EventArgs e) throws Exception {
        FormFullScreen form = new FormFullScreen();
        form.toothChartForBig.setDrawMode(OpenDentBusiness.DrawingMode.OpenGL);
        form.toothChartForBig.setTcData(toothChartOpenGL.getTcData().copy());
        form.ShowDialog();
    }

    private void butFullscreenDirectX_Click(Object sender, EventArgs e) throws Exception {
        FormFullScreen form = new FormFullScreen();
        form.toothChartForBig.setDrawMode(OpenDentBusiness.DrawingMode.DirectX);
        form.toothChartForBig.setTcData(toothChartDirectX.getTcData().copy());
        form.ShowDialog();
    }

    private void butUniversal_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setToothNumberingNomenclature(ToothNumberingNomenclature.Universal);
        toothChartOpenGL.setToothNumberingNomenclature(ToothNumberingNomenclature.Universal);
        toothChartDirectX.setToothNumberingNomenclature(ToothNumberingNomenclature.Universal);
    }

    private void butFDI_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setToothNumberingNomenclature(ToothNumberingNomenclature.FDI);
        toothChartOpenGL.setToothNumberingNomenclature(ToothNumberingNomenclature.FDI);
        toothChartDirectX.setToothNumberingNomenclature(ToothNumberingNomenclature.FDI);
    }

    private void butHaderup_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setToothNumberingNomenclature(ToothNumberingNomenclature.Haderup);
        toothChartOpenGL.setToothNumberingNomenclature(ToothNumberingNomenclature.Haderup);
        toothChartDirectX.setToothNumberingNomenclature(ToothNumberingNomenclature.Haderup);
    }

    private void butPalmer_Click(Object sender, EventArgs e) throws Exception {
        toothChart2D.setToothNumberingNomenclature(ToothNumberingNomenclature.Palmer);
        toothChartOpenGL.setToothNumberingNomenclature(ToothNumberingNomenclature.Palmer);
        toothChartDirectX.setToothNumberingNomenclature(ToothNumberingNomenclature.Palmer);
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
        System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(Form1.class);
        SparksToothChart.ToothChartData toothChartData1 = new SparksToothChart.ToothChartData();
        SparksToothChart.ToothChartData toothChartData2 = new SparksToothChart.ToothChartData();
        SparksToothChart.ToothChartData toothChartData3 = new SparksToothChart.ToothChartData();
        this.label1 = new System.Windows.Forms.Label();
        this.label2 = new System.Windows.Forms.Label();
        this.label3 = new System.Windows.Forms.Label();
        this.butReset = new System.Windows.Forms.Button();
        this.butAllPrimary = new System.Windows.Forms.Button();
        this.butMixed = new System.Windows.Forms.Button();
        this.groupBox1 = new System.Windows.Forms.GroupBox();
        this.panelColorBackgroundLtGray = new System.Windows.Forms.Panel();
        this.panelColorBackgroundBlue = new System.Windows.Forms.Panel();
        this.panelColorBackgroundWhite = new System.Windows.Forms.Panel();
        this.panelColorBackgroundBlack = new System.Windows.Forms.Panel();
        this.panelColorBackgroundGray = new System.Windows.Forms.Panel();
        this.groupBox2 = new System.Windows.Forms.GroupBox();
        this.panelColorTextWhite = new System.Windows.Forms.Panel();
        this.panelColorTextBlack = new System.Windows.Forms.Panel();
        this.panelColorTextGray = new System.Windows.Forms.Panel();
        this.groupBox3 = new System.Windows.Forms.GroupBox();
        this.panelColorTextHighlightRed = new System.Windows.Forms.Panel();
        this.panelColorTextHighlightWhite = new System.Windows.Forms.Panel();
        this.panelColorTextHighlightBlack = new System.Windows.Forms.Panel();
        this.panelColorTextHighlightGray = new System.Windows.Forms.Panel();
        this.groupBox4 = new System.Windows.Forms.GroupBox();
        this.panelColorBackHighlightBlue = new System.Windows.Forms.Panel();
        this.panelColorBackHighlightWhite = new System.Windows.Forms.Panel();
        this.panelColorBackHighlightBlack = new System.Windows.Forms.Panel();
        this.panelColorBackHighlightGray = new System.Windows.Forms.Panel();
        this.groupBox5 = new System.Windows.Forms.GroupBox();
        this.butVeryWide = new System.Windows.Forms.Button();
        this.butVeryTall = new System.Windows.Forms.Button();
        this.butSizeWide = new System.Windows.Forms.Button();
        this.butSizeTall = new System.Windows.Forms.Button();
        this.butSizeNormal = new System.Windows.Forms.Button();
        this.butMissing = new System.Windows.Forms.Button();
        this.butHidden = new System.Windows.Forms.Button();
        this.butMissingHiddenComplex = new System.Windows.Forms.Button();
        this.groupBox6 = new System.Windows.Forms.GroupBox();
        this.groupBox7 = new System.Windows.Forms.GroupBox();
        this.butPrimaryBU = new System.Windows.Forms.Button();
        this.butVeneers = new System.Windows.Forms.Button();
        this.butSealants = new System.Windows.Forms.Button();
        this.butImplants = new System.Windows.Forms.Button();
        this.butBridges = new System.Windows.Forms.Button();
        this.butBigX = new System.Windows.Forms.Button();
        this.butRCT = new System.Windows.Forms.Button();
        this.butFillings = new System.Windows.Forms.Button();
        this.label4 = new System.Windows.Forms.Label();
        this.groupBox8 = new System.Windows.Forms.GroupBox();
        this.groupBox9 = new System.Windows.Forms.GroupBox();
        this.label6 = new System.Windows.Forms.Label();
        this.butShowDrawing = new System.Windows.Forms.Button();
        this.groupBox10 = new System.Windows.Forms.GroupBox();
        this.butColorDrawOther = new System.Windows.Forms.Button();
        this.panelColorDrawWhite = new System.Windows.Forms.Panel();
        this.panelColorDrawBlue = new System.Windows.Forms.Panel();
        this.panelColorDrawGreen = new System.Windows.Forms.Panel();
        this.panelColorDrawBlack = new System.Windows.Forms.Panel();
        this.panelColorDrawRed = new System.Windows.Forms.Panel();
        this.panelColorDraw = new System.Windows.Forms.Panel();
        this.radioColorChanger = new System.Windows.Forms.RadioButton();
        this.radioEraser = new System.Windows.Forms.RadioButton();
        this.radioPen = new System.Windows.Forms.RadioButton();
        this.radioPointer = new System.Windows.Forms.RadioButton();
        this.groupBox11 = new System.Windows.Forms.GroupBox();
        this.label7 = new System.Windows.Forms.Label();
        this.butGetBitmapOpenGL = new System.Windows.Forms.Button();
        this.butGetBitmapDirectX = new System.Windows.Forms.Button();
        this.butGetBitmap2D = new System.Windows.Forms.Button();
        this.textScreenshotBox = new System.Windows.Forms.TextBox();
        this.toothChartDirectX = new SparksToothChart.ToothChartWrapper();
        this.toothChartOpenGL = new SparksToothChart.ToothChartWrapper();
        this.toothChart2D = new SparksToothChart.ToothChartWrapper();
        this.groupBox12 = new System.Windows.Forms.GroupBox();
        this.butFullscreenDirectX = new System.Windows.Forms.Button();
        this.butFullscreenOpenGL = new System.Windows.Forms.Button();
        this.butFullscreen2D = new System.Windows.Forms.Button();
        this.groupBox13 = new System.Windows.Forms.GroupBox();
        this.butPalmer = new System.Windows.Forms.Button();
        this.butFDI = new System.Windows.Forms.Button();
        this.butHaderup = new System.Windows.Forms.Button();
        this.butUniversal = new System.Windows.Forms.Button();
        this.groupBox14 = new System.Windows.Forms.GroupBox();
        this.butWatches = new System.Windows.Forms.Button();
        this.groupBox1.SuspendLayout();
        this.groupBox2.SuspendLayout();
        this.groupBox3.SuspendLayout();
        this.groupBox4.SuspendLayout();
        this.groupBox5.SuspendLayout();
        this.groupBox6.SuspendLayout();
        this.groupBox7.SuspendLayout();
        this.groupBox8.SuspendLayout();
        this.groupBox9.SuspendLayout();
        this.groupBox10.SuspendLayout();
        this.groupBox11.SuspendLayout();
        this.groupBox12.SuspendLayout();
        this.groupBox13.SuspendLayout();
        this.groupBox14.SuspendLayout();
        this.SuspendLayout();
        //
        // label1
        //
        this.label1.Location = new System.Drawing.Point(167, 5);
        this.label1.Name = "label1";
        this.label1.Size = new System.Drawing.Size(100, 17);
        this.label1.TabIndex = 198;
        this.label1.Text = "2D";
        this.label1.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // label2
        //
        this.label2.Location = new System.Drawing.Point(580, 5);
        this.label2.Name = "label2";
        this.label2.Size = new System.Drawing.Size(100, 17);
        this.label2.TabIndex = 199;
        this.label2.Text = "OpenGL";
        this.label2.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // label3
        //
        this.label3.Location = new System.Drawing.Point(996, 5);
        this.label3.Name = "label3";
        this.label3.Size = new System.Drawing.Size(100, 17);
        this.label3.TabIndex = 200;
        this.label3.Text = "DirectX";
        this.label3.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
        //
        // butReset
        //
        this.butReset.Location = new System.Drawing.Point(8, 361);
        this.butReset.Name = "butReset";
        this.butReset.Size = new System.Drawing.Size(75, 23);
        this.butReset.TabIndex = 201;
        this.butReset.Text = "Reset";
        this.butReset.UseVisualStyleBackColor = true;
        this.butReset.Click += new System.EventHandler(this.butReset_Click);
        //
        // butAllPrimary
        //
        this.butAllPrimary.Location = new System.Drawing.Point(8, 412);
        this.butAllPrimary.Name = "butAllPrimary";
        this.butAllPrimary.Size = new System.Drawing.Size(75, 23);
        this.butAllPrimary.TabIndex = 202;
        this.butAllPrimary.Text = "All Primary";
        this.butAllPrimary.UseVisualStyleBackColor = true;
        this.butAllPrimary.Click += new System.EventHandler(this.butAllPrimary_Click);
        //
        // butMixed
        //
        this.butMixed.Location = new System.Drawing.Point(8, 437);
        this.butMixed.Name = "butMixed";
        this.butMixed.Size = new System.Drawing.Size(75, 23);
        this.butMixed.TabIndex = 203;
        this.butMixed.Text = "Mixed";
        this.butMixed.UseVisualStyleBackColor = true;
        this.butMixed.Click += new System.EventHandler(this.butMixed_Click);
        //
        // groupBox1
        //
        this.groupBox1.Controls.Add(this.panelColorBackgroundLtGray);
        this.groupBox1.Controls.Add(this.panelColorBackgroundBlue);
        this.groupBox1.Controls.Add(this.panelColorBackgroundWhite);
        this.groupBox1.Controls.Add(this.panelColorBackgroundBlack);
        this.groupBox1.Controls.Add(this.panelColorBackgroundGray);
        this.groupBox1.Location = new System.Drawing.Point(170, 357);
        this.groupBox1.Name = "groupBox1";
        this.groupBox1.Size = new System.Drawing.Size(174, 47);
        this.groupBox1.TabIndex = 204;
        this.groupBox1.TabStop = false;
        this.groupBox1.Text = "ColorBackground";
        //
        // panelColorBackgroundLtGray
        //
        this.panelColorBackgroundLtGray.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(224)))), ((int)(((byte)(224)))), ((int)(((byte)(224)))));
        this.panelColorBackgroundLtGray.Location = new System.Drawing.Point(38, 18);
        this.panelColorBackgroundLtGray.Name = "panelColorBackgroundLtGray";
        this.panelColorBackgroundLtGray.Size = new System.Drawing.Size(23, 21);
        this.panelColorBackgroundLtGray.TabIndex = 209;
        this.panelColorBackgroundLtGray.Click += new System.EventHandler(this.panelColorBackgroundLtGray_Click);
        //
        // panelColorBackgroundBlue
        //
        this.panelColorBackgroundBlue.BackColor = System.Drawing.Color.CornflowerBlue;
        this.panelColorBackgroundBlue.Location = new System.Drawing.Point(125, 18);
        this.panelColorBackgroundBlue.Name = "panelColorBackgroundBlue";
        this.panelColorBackgroundBlue.Size = new System.Drawing.Size(23, 21);
        this.panelColorBackgroundBlue.TabIndex = 208;
        this.panelColorBackgroundBlue.Click += new System.EventHandler(this.panelColorBackgroundBlue_Click);
        //
        // panelColorBackgroundWhite
        //
        this.panelColorBackgroundWhite.BackColor = System.Drawing.Color.White;
        this.panelColorBackgroundWhite.Location = new System.Drawing.Point(96, 18);
        this.panelColorBackgroundWhite.Name = "panelColorBackgroundWhite";
        this.panelColorBackgroundWhite.Size = new System.Drawing.Size(23, 21);
        this.panelColorBackgroundWhite.TabIndex = 207;
        this.panelColorBackgroundWhite.Click += new System.EventHandler(this.panelColorBackgroundWhite_Click);
        //
        // panelColorBackgroundBlack
        //
        this.panelColorBackgroundBlack.BackColor = System.Drawing.Color.Black;
        this.panelColorBackgroundBlack.Location = new System.Drawing.Point(67, 18);
        this.panelColorBackgroundBlack.Name = "panelColorBackgroundBlack";
        this.panelColorBackgroundBlack.Size = new System.Drawing.Size(23, 21);
        this.panelColorBackgroundBlack.TabIndex = 206;
        this.panelColorBackgroundBlack.Click += new System.EventHandler(this.panelColorBackgroundBlack_Click);
        //
        // panelColorBackgroundGray
        //
        this.panelColorBackgroundGray.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152)))));
        this.panelColorBackgroundGray.Location = new System.Drawing.Point(9, 18);
        this.panelColorBackgroundGray.Name = "panelColorBackgroundGray";
        this.panelColorBackgroundGray.Size = new System.Drawing.Size(23, 21);
        this.panelColorBackgroundGray.TabIndex = 205;
        this.panelColorBackgroundGray.Click += new System.EventHandler(this.panelColorBackgroundGray_Click);
        //
        // groupBox2
        //
        this.groupBox2.Controls.Add(this.panelColorTextWhite);
        this.groupBox2.Controls.Add(this.panelColorTextBlack);
        this.groupBox2.Controls.Add(this.panelColorTextGray);
        this.groupBox2.Location = new System.Drawing.Point(170, 410);
        this.groupBox2.Name = "groupBox2";
        this.groupBox2.Size = new System.Drawing.Size(129, 47);
        this.groupBox2.TabIndex = 209;
        this.groupBox2.TabStop = false;
        this.groupBox2.Text = "ColorText";
        //
        // panelColorTextWhite
        //
        this.panelColorTextWhite.BackColor = System.Drawing.Color.White;
        this.panelColorTextWhite.Location = new System.Drawing.Point(67, 18);
        this.panelColorTextWhite.Name = "panelColorTextWhite";
        this.panelColorTextWhite.Size = new System.Drawing.Size(23, 21);
        this.panelColorTextWhite.TabIndex = 207;
        this.panelColorTextWhite.Click += new System.EventHandler(this.panelColorTextWhite_Click);
        //
        // panelColorTextBlack
        //
        this.panelColorTextBlack.BackColor = System.Drawing.Color.Black;
        this.panelColorTextBlack.Location = new System.Drawing.Point(38, 18);
        this.panelColorTextBlack.Name = "panelColorTextBlack";
        this.panelColorTextBlack.Size = new System.Drawing.Size(23, 21);
        this.panelColorTextBlack.TabIndex = 206;
        this.panelColorTextBlack.Click += new System.EventHandler(this.panelColorTextBlack_Click);
        //
        // panelColorTextGray
        //
        this.panelColorTextGray.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152)))));
        this.panelColorTextGray.Location = new System.Drawing.Point(9, 18);
        this.panelColorTextGray.Name = "panelColorTextGray";
        this.panelColorTextGray.Size = new System.Drawing.Size(23, 21);
        this.panelColorTextGray.TabIndex = 205;
        this.panelColorTextGray.Click += new System.EventHandler(this.panelColorTextGray_Click);
        //
        // groupBox3
        //
        this.groupBox3.Controls.Add(this.panelColorTextHighlightRed);
        this.groupBox3.Controls.Add(this.panelColorTextHighlightWhite);
        this.groupBox3.Controls.Add(this.panelColorTextHighlightBlack);
        this.groupBox3.Controls.Add(this.panelColorTextHighlightGray);
        this.groupBox3.Location = new System.Drawing.Point(170, 463);
        this.groupBox3.Name = "groupBox3";
        this.groupBox3.Size = new System.Drawing.Size(129, 47);
        this.groupBox3.TabIndex = 210;
        this.groupBox3.TabStop = false;
        this.groupBox3.Text = "ColorTextHighlight";
        //
        // panelColorTextHighlightRed
        //
        this.panelColorTextHighlightRed.BackColor = System.Drawing.Color.Red;
        this.panelColorTextHighlightRed.Location = new System.Drawing.Point(96, 18);
        this.panelColorTextHighlightRed.Name = "panelColorTextHighlightRed";
        this.panelColorTextHighlightRed.Size = new System.Drawing.Size(23, 21);
        this.panelColorTextHighlightRed.TabIndex = 208;
        this.panelColorTextHighlightRed.Click += new System.EventHandler(this.panelColorTextHighlightRed_Click);
        //
        // panelColorTextHighlightWhite
        //
        this.panelColorTextHighlightWhite.BackColor = System.Drawing.Color.White;
        this.panelColorTextHighlightWhite.Location = new System.Drawing.Point(67, 18);
        this.panelColorTextHighlightWhite.Name = "panelColorTextHighlightWhite";
        this.panelColorTextHighlightWhite.Size = new System.Drawing.Size(23, 21);
        this.panelColorTextHighlightWhite.TabIndex = 207;
        this.panelColorTextHighlightWhite.Click += new System.EventHandler(this.panelColorTextHighlightWhite_Click);
        //
        // panelColorTextHighlightBlack
        //
        this.panelColorTextHighlightBlack.BackColor = System.Drawing.Color.Black;
        this.panelColorTextHighlightBlack.Location = new System.Drawing.Point(38, 18);
        this.panelColorTextHighlightBlack.Name = "panelColorTextHighlightBlack";
        this.panelColorTextHighlightBlack.Size = new System.Drawing.Size(23, 21);
        this.panelColorTextHighlightBlack.TabIndex = 206;
        this.panelColorTextHighlightBlack.Click += new System.EventHandler(this.panelColorTextHighlightBlack_Click);
        //
        // panelColorTextHighlightGray
        //
        this.panelColorTextHighlightGray.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152)))));
        this.panelColorTextHighlightGray.Location = new System.Drawing.Point(9, 18);
        this.panelColorTextHighlightGray.Name = "panelColorTextHighlightGray";
        this.panelColorTextHighlightGray.Size = new System.Drawing.Size(23, 21);
        this.panelColorTextHighlightGray.TabIndex = 205;
        this.panelColorTextHighlightGray.Click += new System.EventHandler(this.panelColorTextHighlightGray_Click);
        //
        // groupBox4
        //
        this.groupBox4.Controls.Add(this.panelColorBackHighlightBlue);
        this.groupBox4.Controls.Add(this.panelColorBackHighlightWhite);
        this.groupBox4.Controls.Add(this.panelColorBackHighlightBlack);
        this.groupBox4.Controls.Add(this.panelColorBackHighlightGray);
        this.groupBox4.Location = new System.Drawing.Point(170, 516);
        this.groupBox4.Name = "groupBox4";
        this.groupBox4.Size = new System.Drawing.Size(129, 47);
        this.groupBox4.TabIndex = 211;
        this.groupBox4.TabStop = false;
        this.groupBox4.Text = "ColorBackHighlight";
        //
        // panelColorBackHighlightBlue
        //
        this.panelColorBackHighlightBlue.BackColor = System.Drawing.Color.CornflowerBlue;
        this.panelColorBackHighlightBlue.Location = new System.Drawing.Point(96, 18);
        this.panelColorBackHighlightBlue.Name = "panelColorBackHighlightBlue";
        this.panelColorBackHighlightBlue.Size = new System.Drawing.Size(23, 21);
        this.panelColorBackHighlightBlue.TabIndex = 208;
        this.panelColorBackHighlightBlue.Click += new System.EventHandler(this.panelColorBackHighlightBlue_Click);
        //
        // panelColorBackHighlightWhite
        //
        this.panelColorBackHighlightWhite.BackColor = System.Drawing.Color.White;
        this.panelColorBackHighlightWhite.Location = new System.Drawing.Point(67, 18);
        this.panelColorBackHighlightWhite.Name = "panelColorBackHighlightWhite";
        this.panelColorBackHighlightWhite.Size = new System.Drawing.Size(23, 21);
        this.panelColorBackHighlightWhite.TabIndex = 207;
        this.panelColorBackHighlightWhite.Click += new System.EventHandler(this.panelColorBackHighlightWhite_Click);
        //
        // panelColorBackHighlightBlack
        //
        this.panelColorBackHighlightBlack.BackColor = System.Drawing.Color.Black;
        this.panelColorBackHighlightBlack.Location = new System.Drawing.Point(38, 18);
        this.panelColorBackHighlightBlack.Name = "panelColorBackHighlightBlack";
        this.panelColorBackHighlightBlack.Size = new System.Drawing.Size(23, 21);
        this.panelColorBackHighlightBlack.TabIndex = 206;
        this.panelColorBackHighlightBlack.Click += new System.EventHandler(this.panelColorBackHighlightBlack_Click);
        //
        // panelColorBackHighlightGray
        //
        this.panelColorBackHighlightGray.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152)))));
        this.panelColorBackHighlightGray.Location = new System.Drawing.Point(9, 18);
        this.panelColorBackHighlightGray.Name = "panelColorBackHighlightGray";
        this.panelColorBackHighlightGray.Size = new System.Drawing.Size(23, 21);
        this.panelColorBackHighlightGray.TabIndex = 205;
        this.panelColorBackHighlightGray.Click += new System.EventHandler(this.panelColorBackHighlightGray_Click);
        //
        // groupBox5
        //
        this.groupBox5.Controls.Add(this.butVeryWide);
        this.groupBox5.Controls.Add(this.butVeryTall);
        this.groupBox5.Controls.Add(this.butSizeWide);
        this.groupBox5.Controls.Add(this.butSizeTall);
        this.groupBox5.Controls.Add(this.butSizeNormal);
        this.groupBox5.Location = new System.Drawing.Point(12, 662);
        this.groupBox5.Name = "groupBox5";
        this.groupBox5.Size = new System.Drawing.Size(233, 70);
        this.groupBox5.TabIndex = 212;
        this.groupBox5.TabStop = false;
        this.groupBox5.Text = "Resize and Scale";
        //
        // butVeryWide
        //
        this.butVeryWide.Location = new System.Drawing.Point(158, 41);
        this.butVeryWide.Name = "butVeryWide";
        this.butVeryWide.Size = new System.Drawing.Size(69, 23);
        this.butVeryWide.TabIndex = 208;
        this.butVeryWide.Text = "Very Wide";
        this.butVeryWide.UseVisualStyleBackColor = true;
        this.butVeryWide.Click += new System.EventHandler(this.butVeryWide_Click);
        //
        // butVeryTall
        //
        this.butVeryTall.Location = new System.Drawing.Point(87, 41);
        this.butVeryTall.Name = "butVeryTall";
        this.butVeryTall.Size = new System.Drawing.Size(69, 23);
        this.butVeryTall.TabIndex = 207;
        this.butVeryTall.Text = "Very Tall";
        this.butVeryTall.UseVisualStyleBackColor = true;
        this.butVeryTall.Click += new System.EventHandler(this.butVeryTall_Click);
        //
        // butSizeWide
        //
        this.butSizeWide.Location = new System.Drawing.Point(158, 16);
        this.butSizeWide.Name = "butSizeWide";
        this.butSizeWide.Size = new System.Drawing.Size(69, 23);
        this.butSizeWide.TabIndex = 206;
        this.butSizeWide.Text = "Wide";
        this.butSizeWide.UseVisualStyleBackColor = true;
        this.butSizeWide.Click += new System.EventHandler(this.butSizeWide_Click);
        //
        // butSizeTall
        //
        this.butSizeTall.Location = new System.Drawing.Point(87, 16);
        this.butSizeTall.Name = "butSizeTall";
        this.butSizeTall.Size = new System.Drawing.Size(69, 23);
        this.butSizeTall.TabIndex = 205;
        this.butSizeTall.Text = "Tall";
        this.butSizeTall.UseVisualStyleBackColor = true;
        this.butSizeTall.Click += new System.EventHandler(this.butSizeTall_Click);
        //
        // butSizeNormal
        //
        this.butSizeNormal.Location = new System.Drawing.Point(9, 16);
        this.butSizeNormal.Name = "butSizeNormal";
        this.butSizeNormal.Size = new System.Drawing.Size(75, 23);
        this.butSizeNormal.TabIndex = 204;
        this.butSizeNormal.Text = "Normal";
        this.butSizeNormal.UseVisualStyleBackColor = true;
        this.butSizeNormal.Click += new System.EventHandler(this.butSizeNormal_Click);
        //
        // butMissing
        //
        this.butMissing.Location = new System.Drawing.Point(10, 18);
        this.butMissing.Name = "butMissing";
        this.butMissing.Size = new System.Drawing.Size(75, 23);
        this.butMissing.TabIndex = 207;
        this.butMissing.Text = "Missing";
        this.butMissing.UseVisualStyleBackColor = true;
        this.butMissing.Click += new System.EventHandler(this.butMissing_Click);
        //
        // butHidden
        //
        this.butHidden.Location = new System.Drawing.Point(10, 43);
        this.butHidden.Name = "butHidden";
        this.butHidden.Size = new System.Drawing.Size(75, 23);
        this.butHidden.TabIndex = 213;
        this.butHidden.Text = "Hidden";
        this.butHidden.UseVisualStyleBackColor = true;
        this.butHidden.Click += new System.EventHandler(this.butHidden_Click);
        //
        // butMissingHiddenComplex
        //
        this.butMissingHiddenComplex.Location = new System.Drawing.Point(10, 68);
        this.butMissingHiddenComplex.Name = "butMissingHiddenComplex";
        this.butMissingHiddenComplex.Size = new System.Drawing.Size(75, 23);
        this.butMissingHiddenComplex.TabIndex = 214;
        this.butMissingHiddenComplex.Text = "Complex";
        this.butMissingHiddenComplex.UseVisualStyleBackColor = true;
        this.butMissingHiddenComplex.Click += new System.EventHandler(this.butMissingHiddenComplex_Click);
        //
        // groupBox6
        //
        this.groupBox6.Controls.Add(this.butHidden);
        this.groupBox6.Controls.Add(this.butMissingHiddenComplex);
        this.groupBox6.Controls.Add(this.butMissing);
        this.groupBox6.Location = new System.Drawing.Point(536, 361);
        this.groupBox6.Name = "groupBox6";
        this.groupBox6.Size = new System.Drawing.Size(118, 99);
        this.groupBox6.TabIndex = 215;
        this.groupBox6.TabStop = false;
        this.groupBox6.Text = "Missing and Hidden";
        //
        // groupBox7
        //
        this.groupBox7.Controls.Add(this.butPrimaryBU);
        this.groupBox7.Controls.Add(this.butVeneers);
        this.groupBox7.Controls.Add(this.butSealants);
        this.groupBox7.Controls.Add(this.butImplants);
        this.groupBox7.Controls.Add(this.butBridges);
        this.groupBox7.Controls.Add(this.butBigX);
        this.groupBox7.Controls.Add(this.butRCT);
        this.groupBox7.Controls.Add(this.butFillings);
        this.groupBox7.Location = new System.Drawing.Point(680, 361);
        this.groupBox7.Name = "groupBox7";
        this.groupBox7.Size = new System.Drawing.Size(118, 221);
        this.groupBox7.TabIndex = 216;
        this.groupBox7.TabStop = false;
        this.groupBox7.Text = "Restorations";
        //
        // butPrimaryBU
        //
        this.butPrimaryBU.Location = new System.Drawing.Point(10, 68);
        this.butPrimaryBU.Name = "butPrimaryBU";
        this.butPrimaryBU.Size = new System.Drawing.Size(88, 23);
        this.butPrimaryBU.TabIndex = 214;
        this.butPrimaryBU.Text = "Primary BU";
        this.butPrimaryBU.UseVisualStyleBackColor = true;
        this.butPrimaryBU.Click += new System.EventHandler(this.butPrimaryBU_Click);
        //
        // butVeneers
        //
        this.butVeneers.Location = new System.Drawing.Point(10, 193);
        this.butVeneers.Name = "butVeneers";
        this.butVeneers.Size = new System.Drawing.Size(88, 23);
        this.butVeneers.TabIndex = 213;
        this.butVeneers.Text = "Veneers";
        this.butVeneers.UseVisualStyleBackColor = true;
        this.butVeneers.Click += new System.EventHandler(this.butVeneers_Click);
        //
        // butSealants
        //
        this.butSealants.Location = new System.Drawing.Point(10, 168);
        this.butSealants.Name = "butSealants";
        this.butSealants.Size = new System.Drawing.Size(88, 23);
        this.butSealants.TabIndex = 212;
        this.butSealants.Text = "Sealants";
        this.butSealants.UseVisualStyleBackColor = true;
        this.butSealants.Click += new System.EventHandler(this.butSealants_Click);
        //
        // butImplants
        //
        this.butImplants.Location = new System.Drawing.Point(10, 143);
        this.butImplants.Name = "butImplants";
        this.butImplants.Size = new System.Drawing.Size(88, 23);
        this.butImplants.TabIndex = 211;
        this.butImplants.Text = "Implants";
        this.butImplants.UseVisualStyleBackColor = true;
        this.butImplants.Click += new System.EventHandler(this.butImplants_Click);
        //
        // butBridges
        //
        this.butBridges.Location = new System.Drawing.Point(10, 118);
        this.butBridges.Name = "butBridges";
        this.butBridges.Size = new System.Drawing.Size(88, 23);
        this.butBridges.TabIndex = 210;
        this.butBridges.Text = "Bridges";
        this.butBridges.UseVisualStyleBackColor = true;
        this.butBridges.Click += new System.EventHandler(this.butBridges_Click);
        //
        // butBigX
        //
        this.butBigX.Location = new System.Drawing.Point(10, 93);
        this.butBigX.Name = "butBigX";
        this.butBigX.Size = new System.Drawing.Size(88, 23);
        this.butBigX.TabIndex = 209;
        this.butBigX.Text = "Big X";
        this.butBigX.UseVisualStyleBackColor = true;
        this.butBigX.Click += new System.EventHandler(this.butBigX_Click);
        //
        // butRCT
        //
        this.butRCT.Location = new System.Drawing.Point(10, 43);
        this.butRCT.Name = "butRCT";
        this.butRCT.Size = new System.Drawing.Size(88, 23);
        this.butRCT.TabIndex = 208;
        this.butRCT.Text = "RCT/BU";
        this.butRCT.UseVisualStyleBackColor = true;
        this.butRCT.Click += new System.EventHandler(this.butRCT_Click);
        //
        // butFillings
        //
        this.butFillings.Location = new System.Drawing.Point(10, 18);
        this.butFillings.Name = "butFillings";
        this.butFillings.Size = new System.Drawing.Size(88, 23);
        this.butFillings.TabIndex = 207;
        this.butFillings.Text = "Fillings/Crns";
        this.butFillings.UseVisualStyleBackColor = true;
        this.butFillings.Click += new System.EventHandler(this.butFillings_Click);
        //
        // label4
        //
        this.label4.Location = new System.Drawing.Point(8, 17);
        this.label4.Name = "label4";
        this.label4.Size = new System.Drawing.Size(199, 100);
        this.label4.TabIndex = 217;
        this.label4.Text = resources.GetString("label4.Text");
        //
        // groupBox8
        //
        this.groupBox8.Controls.Add(this.label4);
        this.groupBox8.Location = new System.Drawing.Point(840, 361);
        this.groupBox8.Name = "groupBox8";
        this.groupBox8.Size = new System.Drawing.Size(222, 120);
        this.groupBox8.TabIndex = 218;
        this.groupBox8.TabStop = false;
        this.groupBox8.Text = "Mouse clicks";
        //
        // groupBox9
        //
        this.groupBox9.Controls.Add(this.label6);
        this.groupBox9.Controls.Add(this.butShowDrawing);
        this.groupBox9.Controls.Add(this.groupBox10);
        this.groupBox9.Controls.Add(this.panelColorDraw);
        this.groupBox9.Controls.Add(this.radioColorChanger);
        this.groupBox9.Controls.Add(this.radioEraser);
        this.groupBox9.Controls.Add(this.radioPen);
        this.groupBox9.Controls.Add(this.radioPointer);
        this.groupBox9.Location = new System.Drawing.Point(840, 510);
        this.groupBox9.Name = "groupBox9";
        this.groupBox9.Size = new System.Drawing.Size(288, 200);
        this.groupBox9.TabIndex = 219;
        this.groupBox9.TabStop = false;
        this.groupBox9.Text = "Drawing";
        //
        // label6
        //
        this.label6.Location = new System.Drawing.Point(11, 152);
        this.label6.Name = "label6";
        this.label6.Size = new System.Drawing.Size(271, 43);
        this.label6.TabIndex = 214;
        this.label6.Text = "There is not a test for the SegmentDrawn event which causes saving to the databas" + "e.  That will be tested in the full application.  Must implement.";
        //
        // butShowDrawing
        //
        this.butShowDrawing.Location = new System.Drawing.Point(9, 126);
        this.butShowDrawing.Name = "butShowDrawing";
        this.butShowDrawing.Size = new System.Drawing.Size(100, 23);
        this.butShowDrawing.TabIndex = 213;
        this.butShowDrawing.Text = "Show a Drawing";
        this.butShowDrawing.UseVisualStyleBackColor = true;
        this.butShowDrawing.Click += new System.EventHandler(this.butShowDrawing_Click);
        //
        // groupBox10
        //
        this.groupBox10.Controls.Add(this.butColorDrawOther);
        this.groupBox10.Controls.Add(this.panelColorDrawWhite);
        this.groupBox10.Controls.Add(this.panelColorDrawBlue);
        this.groupBox10.Controls.Add(this.panelColorDrawGreen);
        this.groupBox10.Controls.Add(this.panelColorDrawBlack);
        this.groupBox10.Controls.Add(this.panelColorDrawRed);
        this.groupBox10.Location = new System.Drawing.Point(134, 51);
        this.groupBox10.Name = "groupBox10";
        this.groupBox10.Size = new System.Drawing.Size(147, 73);
        this.groupBox10.TabIndex = 11;
        this.groupBox10.TabStop = false;
        this.groupBox10.Text = "Set Color";
        //
        // butColorDrawOther
        //
        this.butColorDrawOther.Location = new System.Drawing.Point(6, 45);
        this.butColorDrawOther.Name = "butColorDrawOther";
        this.butColorDrawOther.Size = new System.Drawing.Size(67, 23);
        this.butColorDrawOther.TabIndex = 213;
        this.butColorDrawOther.Text = "Other";
        this.butColorDrawOther.UseVisualStyleBackColor = true;
        this.butColorDrawOther.Click += new System.EventHandler(this.butColorDrawOther_Click);
        //
        // panelColorDrawWhite
        //
        this.panelColorDrawWhite.BackColor = System.Drawing.Color.White;
        this.panelColorDrawWhite.Location = new System.Drawing.Point(118, 17);
        this.panelColorDrawWhite.Name = "panelColorDrawWhite";
        this.panelColorDrawWhite.Size = new System.Drawing.Size(22, 22);
        this.panelColorDrawWhite.TabIndex = 10;
        this.panelColorDrawWhite.Click += new System.EventHandler(this.panelColorDrawWhite_Click);
        //
        // panelColorDrawBlue
        //
        this.panelColorDrawBlue.BackColor = System.Drawing.Color.RoyalBlue;
        this.panelColorDrawBlue.Location = new System.Drawing.Point(62, 17);
        this.panelColorDrawBlue.Name = "panelColorDrawBlue";
        this.panelColorDrawBlue.Size = new System.Drawing.Size(22, 22);
        this.panelColorDrawBlue.TabIndex = 9;
        this.panelColorDrawBlue.Click += new System.EventHandler(this.panelColorDrawBlue_Click);
        //
        // panelColorDrawGreen
        //
        this.panelColorDrawGreen.BackColor = System.Drawing.Color.DarkGreen;
        this.panelColorDrawGreen.Location = new System.Drawing.Point(90, 17);
        this.panelColorDrawGreen.Name = "panelColorDrawGreen";
        this.panelColorDrawGreen.Size = new System.Drawing.Size(22, 22);
        this.panelColorDrawGreen.TabIndex = 7;
        this.panelColorDrawGreen.Click += new System.EventHandler(this.panelColorDrawGreen_Click);
        //
        // panelColorDrawBlack
        //
        this.panelColorDrawBlack.BackColor = System.Drawing.Color.Black;
        this.panelColorDrawBlack.Location = new System.Drawing.Point(6, 17);
        this.panelColorDrawBlack.Name = "panelColorDrawBlack";
        this.panelColorDrawBlack.Size = new System.Drawing.Size(22, 22);
        this.panelColorDrawBlack.TabIndex = 6;
        this.panelColorDrawBlack.Click += new System.EventHandler(this.panelColorDrawBlack_Click);
        //
        // panelColorDrawRed
        //
        this.panelColorDrawRed.BackColor = System.Drawing.Color.DarkRed;
        this.panelColorDrawRed.Location = new System.Drawing.Point(34, 17);
        this.panelColorDrawRed.Name = "panelColorDrawRed";
        this.panelColorDrawRed.Size = new System.Drawing.Size(22, 22);
        this.panelColorDrawRed.TabIndex = 4;
        this.panelColorDrawRed.Click += new System.EventHandler(this.panelColorDrawRed_Click);
        //
        // panelColorDraw
        //
        this.panelColorDraw.BackColor = System.Drawing.Color.Black;
        this.panelColorDraw.Location = new System.Drawing.Point(10, 95);
        this.panelColorDraw.Name = "panelColorDraw";
        this.panelColorDraw.Size = new System.Drawing.Size(22, 22);
        this.panelColorDraw.TabIndex = 10;
        //
        // radioColorChanger
        //
        this.radioColorChanger.Location = new System.Drawing.Point(10, 72);
        this.radioColorChanger.Name = "radioColorChanger";
        this.radioColorChanger.Size = new System.Drawing.Size(122, 17);
        this.radioColorChanger.TabIndex = 9;
        this.radioColorChanger.TabStop = true;
        this.radioColorChanger.Text = "Color Changer";
        this.radioColorChanger.UseVisualStyleBackColor = true;
        this.radioColorChanger.Click += new System.EventHandler(this.radioColorChanger_Click);
        //
        // radioEraser
        //
        this.radioEraser.Location = new System.Drawing.Point(10, 53);
        this.radioEraser.Name = "radioEraser";
        this.radioEraser.Size = new System.Drawing.Size(122, 17);
        this.radioEraser.TabIndex = 8;
        this.radioEraser.TabStop = true;
        this.radioEraser.Text = "Eraser";
        this.radioEraser.UseVisualStyleBackColor = true;
        this.radioEraser.Click += new System.EventHandler(this.radioEraser_Click);
        //
        // radioPen
        //
        this.radioPen.Location = new System.Drawing.Point(10, 34);
        this.radioPen.Name = "radioPen";
        this.radioPen.Size = new System.Drawing.Size(122, 17);
        this.radioPen.TabIndex = 7;
        this.radioPen.TabStop = true;
        this.radioPen.Text = "Pen";
        this.radioPen.UseVisualStyleBackColor = true;
        this.radioPen.Click += new System.EventHandler(this.radioPen_Click);
        //
        // radioPointer
        //
        this.radioPointer.Checked = true;
        this.radioPointer.Location = new System.Drawing.Point(10, 15);
        this.radioPointer.Name = "radioPointer";
        this.radioPointer.Size = new System.Drawing.Size(122, 17);
        this.radioPointer.TabIndex = 6;
        this.radioPointer.TabStop = true;
        this.radioPointer.Text = "Pointer";
        this.radioPointer.UseVisualStyleBackColor = true;
        this.radioPointer.Click += new System.EventHandler(this.radioPointer_Click);
        //
        // groupBox11
        //
        this.groupBox11.Controls.Add(this.label7);
        this.groupBox11.Controls.Add(this.butGetBitmapOpenGL);
        this.groupBox11.Controls.Add(this.butGetBitmapDirectX);
        this.groupBox11.Controls.Add(this.butGetBitmap2D);
        this.groupBox11.Location = new System.Drawing.Point(536, 616);
        this.groupBox11.Name = "groupBox11";
        this.groupBox11.Size = new System.Drawing.Size(214, 99);
        this.groupBox11.TabIndex = 220;
        this.groupBox11.TabStop = false;
        this.groupBox11.Text = "GetBitmap";
        //
        // label7
        //
        this.label7.Location = new System.Drawing.Point(91, 20);
        this.label7.Name = "label7";
        this.label7.Size = new System.Drawing.Size(117, 71);
        this.label7.TabIndex = 215;
        this.label7.Text = "Since screen capture is unreliable, a warning to that effect shows if a screencap" + "ture is used.";
        //
        // butGetBitmapOpenGL
        //
        this.butGetBitmapOpenGL.Location = new System.Drawing.Point(10, 43);
        this.butGetBitmapOpenGL.Name = "butGetBitmapOpenGL";
        this.butGetBitmapOpenGL.Size = new System.Drawing.Size(75, 23);
        this.butGetBitmapOpenGL.TabIndex = 213;
        this.butGetBitmapOpenGL.Text = "OpenGL";
        this.butGetBitmapOpenGL.UseVisualStyleBackColor = true;
        this.butGetBitmapOpenGL.Click += new System.EventHandler(this.butGetBitmapOpenGL_Click);
        //
        // butGetBitmapDirectX
        //
        this.butGetBitmapDirectX.Location = new System.Drawing.Point(10, 68);
        this.butGetBitmapDirectX.Name = "butGetBitmapDirectX";
        this.butGetBitmapDirectX.Size = new System.Drawing.Size(75, 23);
        this.butGetBitmapDirectX.TabIndex = 214;
        this.butGetBitmapDirectX.Text = "DirectX";
        this.butGetBitmapDirectX.UseVisualStyleBackColor = true;
        this.butGetBitmapDirectX.Click += new System.EventHandler(this.butGetBitmapDirectX_Click);
        //
        // butGetBitmap2D
        //
        this.butGetBitmap2D.Location = new System.Drawing.Point(10, 18);
        this.butGetBitmap2D.Name = "butGetBitmap2D";
        this.butGetBitmap2D.Size = new System.Drawing.Size(75, 23);
        this.butGetBitmap2D.TabIndex = 207;
        this.butGetBitmap2D.Text = "2D";
        this.butGetBitmap2D.UseVisualStyleBackColor = true;
        this.butGetBitmap2D.Click += new System.EventHandler(this.butGetBitmap2D_Click);
        //
        // textScreenshotBox
        //
        this.textScreenshotBox.Location = new System.Drawing.Point(2, 191);
        this.textScreenshotBox.Name = "textScreenshotBox";
        this.textScreenshotBox.Size = new System.Drawing.Size(1257, 20);
        this.textScreenshotBox.TabIndex = 221;
        this.textScreenshotBox.Text = resources.GetString("textScreenshotBox.Text");
        this.textScreenshotBox.Visible = false;
        //
        // toothChartDirectX
        //
        this.toothChartDirectX.setAutoFinish(false);
        this.toothChartDirectX.setColorBackground(System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152))))));
        this.toothChartDirectX.Cursor = System.Windows.Forms.Cursors.Default;
        this.toothChartDirectX.setCursorTool(SparksToothChart.CursorTool.Pointer);
        this.toothChartDirectX.setDeviceFormat(null);
        this.toothChartDirectX.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        this.toothChartDirectX.Location = new System.Drawing.Point(840, 28);
        this.toothChartDirectX.Name = "toothChartDirectX";
        this.toothChartDirectX.setPerioMode(false);
        this.toothChartDirectX.setPreferredPixelFormatNumber(0);
        this.toothChartDirectX.Size = new System.Drawing.Size(410, 307);
        this.toothChartDirectX.TabIndex = 197;
        toothChartData1.setSizeControl(new System.Drawing.Size(410, 307));
        this.toothChartDirectX.setTcData(toothChartData1);
        this.toothChartDirectX.setUseHardware(false);
        //
        // toothChartOpenGL
        //
        this.toothChartOpenGL.setAutoFinish(false);
        this.toothChartOpenGL.setColorBackground(System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152))))));
        this.toothChartOpenGL.Cursor = System.Windows.Forms.Cursors.Default;
        this.toothChartOpenGL.setCursorTool(SparksToothChart.CursorTool.Pointer);
        this.toothChartOpenGL.setDeviceFormat(null);
        this.toothChartOpenGL.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        this.toothChartOpenGL.Location = new System.Drawing.Point(424, 28);
        this.toothChartOpenGL.Name = "toothChartOpenGL";
        this.toothChartOpenGL.setPerioMode(false);
        this.toothChartOpenGL.setPreferredPixelFormatNumber(0);
        this.toothChartOpenGL.Size = new System.Drawing.Size(410, 307);
        this.toothChartOpenGL.TabIndex = 196;
        toothChartData2.setSizeControl(new System.Drawing.Size(410, 307));
        this.toothChartOpenGL.setTcData(toothChartData2);
        this.toothChartOpenGL.setUseHardware(false);
        //
        // toothChart2D
        //
        this.toothChart2D.setAutoFinish(false);
        this.toothChart2D.setColorBackground(System.Drawing.Color.FromArgb(((int)(((byte)(150)))), ((int)(((byte)(145)))), ((int)(((byte)(152))))));
        this.toothChart2D.Cursor = System.Windows.Forms.Cursors.Default;
        this.toothChart2D.setCursorTool(SparksToothChart.CursorTool.Pointer);
        this.toothChart2D.setDeviceFormat(null);
        this.toothChart2D.setDrawMode(OpenDentBusiness.DrawingMode.Simple2D);
        this.toothChart2D.Location = new System.Drawing.Point(8, 28);
        this.toothChart2D.Name = "toothChart2D";
        this.toothChart2D.setPerioMode(false);
        this.toothChart2D.setPreferredPixelFormatNumber(0);
        this.toothChart2D.Size = new System.Drawing.Size(410, 307);
        this.toothChart2D.TabIndex = 195;
        toothChartData3.setSizeControl(new System.Drawing.Size(410, 307));
        this.toothChart2D.setTcData(toothChartData3);
        this.toothChart2D.setUseHardware(false);
        //
        // groupBox12
        //
        this.groupBox12.Controls.Add(this.butFullscreenDirectX);
        this.groupBox12.Controls.Add(this.butFullscreenOpenGL);
        this.groupBox12.Controls.Add(this.butFullscreen2D);
        this.groupBox12.Location = new System.Drawing.Point(401, 616);
        this.groupBox12.Name = "groupBox12";
        this.groupBox12.Size = new System.Drawing.Size(120, 99);
        this.groupBox12.TabIndex = 213;
        this.groupBox12.TabStop = false;
        this.groupBox12.Text = "Fullscreen";
        //
        // butFullscreenDirectX
        //
        this.butFullscreenDirectX.Location = new System.Drawing.Point(9, 68);
        this.butFullscreenDirectX.Name = "butFullscreenDirectX";
        this.butFullscreenDirectX.Size = new System.Drawing.Size(75, 23);
        this.butFullscreenDirectX.TabIndex = 206;
        this.butFullscreenDirectX.Text = "DirectX";
        this.butFullscreenDirectX.UseVisualStyleBackColor = true;
        this.butFullscreenDirectX.Click += new System.EventHandler(this.butFullscreenDirectX_Click);
        //
        // butFullscreenOpenGL
        //
        this.butFullscreenOpenGL.Location = new System.Drawing.Point(9, 43);
        this.butFullscreenOpenGL.Name = "butFullscreenOpenGL";
        this.butFullscreenOpenGL.Size = new System.Drawing.Size(75, 23);
        this.butFullscreenOpenGL.TabIndex = 205;
        this.butFullscreenOpenGL.Text = "OpenGL";
        this.butFullscreenOpenGL.UseVisualStyleBackColor = true;
        this.butFullscreenOpenGL.Click += new System.EventHandler(this.butFullscreenOpenGL_Click);
        //
        // butFullscreen2D
        //
        this.butFullscreen2D.Location = new System.Drawing.Point(9, 18);
        this.butFullscreen2D.Name = "butFullscreen2D";
        this.butFullscreen2D.Size = new System.Drawing.Size(75, 23);
        this.butFullscreen2D.TabIndex = 204;
        this.butFullscreen2D.Text = "2D";
        this.butFullscreen2D.UseVisualStyleBackColor = true;
        this.butFullscreen2D.Click += new System.EventHandler(this.butFullscreen2D_Click);
        //
        // groupBox13
        //
        this.groupBox13.Controls.Add(this.butPalmer);
        this.groupBox13.Controls.Add(this.butFDI);
        this.groupBox13.Controls.Add(this.butHaderup);
        this.groupBox13.Controls.Add(this.butUniversal);
        this.groupBox13.Location = new System.Drawing.Point(401, 361);
        this.groupBox13.Name = "groupBox13";
        this.groupBox13.Size = new System.Drawing.Size(118, 123);
        this.groupBox13.TabIndex = 222;
        this.groupBox13.TabStop = false;
        this.groupBox13.Text = "Tooth Numbers";
        //
        // butPalmer
        //
        this.butPalmer.Location = new System.Drawing.Point(10, 93);
        this.butPalmer.Name = "butPalmer";
        this.butPalmer.Size = new System.Drawing.Size(75, 23);
        this.butPalmer.TabIndex = 215;
        this.butPalmer.Text = "Palmer";
        this.butPalmer.UseVisualStyleBackColor = true;
        this.butPalmer.Click += new System.EventHandler(this.butPalmer_Click);
        //
        // butFDI
        //
        this.butFDI.Location = new System.Drawing.Point(10, 43);
        this.butFDI.Name = "butFDI";
        this.butFDI.Size = new System.Drawing.Size(75, 23);
        this.butFDI.TabIndex = 213;
        this.butFDI.Text = "FDI";
        this.butFDI.UseVisualStyleBackColor = true;
        this.butFDI.Click += new System.EventHandler(this.butFDI_Click);
        //
        // butHaderup
        //
        this.butHaderup.Location = new System.Drawing.Point(10, 68);
        this.butHaderup.Name = "butHaderup";
        this.butHaderup.Size = new System.Drawing.Size(75, 23);
        this.butHaderup.TabIndex = 214;
        this.butHaderup.Text = "Haderup";
        this.butHaderup.UseVisualStyleBackColor = true;
        this.butHaderup.Click += new System.EventHandler(this.butHaderup_Click);
        //
        // butUniversal
        //
        this.butUniversal.Location = new System.Drawing.Point(10, 18);
        this.butUniversal.Name = "butUniversal";
        this.butUniversal.Size = new System.Drawing.Size(75, 23);
        this.butUniversal.TabIndex = 207;
        this.butUniversal.Text = "Universal";
        this.butUniversal.UseVisualStyleBackColor = true;
        this.butUniversal.Click += new System.EventHandler(this.butUniversal_Click);
        //
        // groupBox14
        //
        this.groupBox14.Controls.Add(this.butWatches);
        this.groupBox14.Location = new System.Drawing.Point(536, 466);
        this.groupBox14.Name = "groupBox14";
        this.groupBox14.Size = new System.Drawing.Size(118, 44);
        this.groupBox14.TabIndex = 223;
        this.groupBox14.TabStop = false;
        this.groupBox14.Text = "Conditions";
        //
        // butWatches
        //
        this.butWatches.Location = new System.Drawing.Point(10, 18);
        this.butWatches.Name = "butWatches";
        this.butWatches.Size = new System.Drawing.Size(75, 23);
        this.butWatches.TabIndex = 207;
        this.butWatches.Text = "Watches";
        this.butWatches.UseVisualStyleBackColor = true;
        this.butWatches.Click += new System.EventHandler(this.butWatches_Click);
        //
        // Form1
        //
        this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        this.ClientSize = new System.Drawing.Size(1258, 737);
        this.Controls.Add(this.groupBox14);
        this.Controls.Add(this.groupBox5);
        this.Controls.Add(this.textScreenshotBox);
        this.Controls.Add(this.toothChartDirectX);
        this.Controls.Add(this.toothChartOpenGL);
        this.Controls.Add(this.toothChart2D);
        this.Controls.Add(this.groupBox11);
        this.Controls.Add(this.groupBox9);
        this.Controls.Add(this.groupBox8);
        this.Controls.Add(this.groupBox7);
        this.Controls.Add(this.groupBox6);
        this.Controls.Add(this.groupBox4);
        this.Controls.Add(this.groupBox3);
        this.Controls.Add(this.groupBox2);
        this.Controls.Add(this.groupBox1);
        this.Controls.Add(this.butMixed);
        this.Controls.Add(this.butAllPrimary);
        this.Controls.Add(this.butReset);
        this.Controls.Add(this.label3);
        this.Controls.Add(this.label2);
        this.Controls.Add(this.label1);
        this.Controls.Add(this.groupBox12);
        this.Controls.Add(this.groupBox13);
        this.Name = "Form1";
        this.Text = "Form1";
        this.Load += new System.EventHandler(this.Form1_Load);
        this.groupBox1.ResumeLayout(false);
        this.groupBox2.ResumeLayout(false);
        this.groupBox3.ResumeLayout(false);
        this.groupBox4.ResumeLayout(false);
        this.groupBox5.ResumeLayout(false);
        this.groupBox6.ResumeLayout(false);
        this.groupBox7.ResumeLayout(false);
        this.groupBox8.ResumeLayout(false);
        this.groupBox9.ResumeLayout(false);
        this.groupBox10.ResumeLayout(false);
        this.groupBox11.ResumeLayout(false);
        this.groupBox12.ResumeLayout(false);
        this.groupBox13.ResumeLayout(false);
        this.groupBox14.ResumeLayout(false);
        this.ResumeLayout(false);
        this.PerformLayout();
    }

    private SparksToothChart.ToothChartWrapper toothChart2D;
    private SparksToothChart.ToothChartWrapper toothChartOpenGL;
    private SparksToothChart.ToothChartWrapper toothChartDirectX;
    private System.Windows.Forms.Label label1 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label2 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Label label3 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butReset = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butAllPrimary = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butMixed = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox1 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Panel panelColorBackgroundGray = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorBackgroundBlue = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorBackgroundWhite = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorBackgroundBlack = new System.Windows.Forms.Panel();
    private System.Windows.Forms.GroupBox groupBox2 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Panel panelColorTextWhite = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorTextBlack = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorTextGray = new System.Windows.Forms.Panel();
    private System.Windows.Forms.GroupBox groupBox3 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Panel panelColorTextHighlightRed = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorTextHighlightWhite = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorTextHighlightBlack = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorTextHighlightGray = new System.Windows.Forms.Panel();
    private System.Windows.Forms.GroupBox groupBox4 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Panel panelColorBackHighlightBlue = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorBackHighlightWhite = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorBackHighlightBlack = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorBackHighlightGray = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorBackgroundLtGray = new System.Windows.Forms.Panel();
    private System.Windows.Forms.GroupBox groupBox5 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butSizeWide = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSizeTall = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSizeNormal = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butMissing = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butHidden = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butMissingHiddenComplex = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox6 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.GroupBox groupBox7 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butFillings = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label4 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox8 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butRCT = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butBigX = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butBridges = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butImplants = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butSealants = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox9 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.RadioButton radioColorChanger = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioEraser = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioPen = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.RadioButton radioPointer = new System.Windows.Forms.RadioButton();
    private System.Windows.Forms.Panel panelColorDraw = new System.Windows.Forms.Panel();
    private System.Windows.Forms.GroupBox groupBox10 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Panel panelColorDrawWhite = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorDrawBlue = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorDrawGreen = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorDrawBlack = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Panel panelColorDrawRed = new System.Windows.Forms.Panel();
    private System.Windows.Forms.Button butColorDrawOther = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butShowDrawing = new System.Windows.Forms.Button();
    private System.Windows.Forms.Label label6 = new System.Windows.Forms.Label();
    private System.Windows.Forms.GroupBox groupBox11 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butGetBitmapOpenGL = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butGetBitmapDirectX = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butGetBitmap2D = new System.Windows.Forms.Button();
    private System.Windows.Forms.TextBox textScreenshotBox = new System.Windows.Forms.TextBox();
    private System.Windows.Forms.Label label7 = new System.Windows.Forms.Label();
    private System.Windows.Forms.Button butVeneers = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butPrimaryBU = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butVeryWide = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butVeryTall = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox12 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butFullscreenDirectX = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butFullscreenOpenGL = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butFullscreen2D = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox13 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butFDI = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butHaderup = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butUniversal = new System.Windows.Forms.Button();
    private System.Windows.Forms.Button butPalmer = new System.Windows.Forms.Button();
    private System.Windows.Forms.GroupBox groupBox14 = new System.Windows.Forms.GroupBox();
    private System.Windows.Forms.Button butWatches = new System.Windows.Forms.Button();
}


