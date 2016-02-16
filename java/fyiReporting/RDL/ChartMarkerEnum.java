//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:25 PM
//

package fyiReporting.RDL;


public enum ChartMarkerEnum
{
    // used for line point markers
    // the order reflects the usage order as well (see GetSeriesMarkers in ChartBase)
    Circle,
    Square,
    Triangle,
    Plus,
    X,
    Diamond,
    // TODO: diamond doesn't draw well in small size
    Count,
    // must equal the number of shapes
    None
}

