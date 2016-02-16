//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:25 PM
//

package OpenDental.ReportingOld2;


public enum AreaSectionKind
{
    /**
    * The type of section is used in the Section class.  Only ONE of each type is allowed except for the GroupHeader and GroupFooter which are optional and can have one pair for each group.  The order of the sections is locked and user cannot change.Printed at the top of the report.
    */
    ReportHeader,
    /**
    * Printed at the top of each page.
    */
    PageHeader,
    /**
    * Not implemented yet. Will print at the top of a specific group.
    */
    GroupHeader,
    /**
    * This is the data of the report and represents one row of data.  This section gets printed once for each record in the datatable.
    */
    Detail,
    /**
    * Not implemented yet.
    */
    GroupFooter,
    /**
    * Prints at the bottom of each page, including after the reportFooter
    */
    PageFooter,
    /**
    * Prints at the bottom of the report, but before the page footer for the last page.
    */
    ReportFooter
}

