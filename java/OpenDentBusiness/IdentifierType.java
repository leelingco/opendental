//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:10 PM
//

package OpenDentBusiness;


public enum IdentifierType
{
    /**
    * Stored as string. Sorted and displayed in the order they are present in this enum.  Root should always be first.Will most likely be the root of all other OIDs.  Represents the organization.
    */
    Root,
    /**
    * FK to EhrLab.EhrLabNum.  root+".1"
    */
    LabOrder,
    /**
    * FK to Patient.PatNum.  root+".2"
    */
    Patient,
    /**
    * FK to Provider.ProvNum.  root+".3"
    */
    Provider,
    /**
    * This will be the root for all CQM reported items, like encounters, procedures, problems, etc.  root+".4"  The extension will be abbreviated name concatenated with the primary key of the object.  Examples: pat5231 or medpat197432 or proc231782 or notperf38291.  This is only used for generating QRDA documents and requires that the encounter, procedure, etc. is uniquely identified in the reports.  The root+".4" makes it unique to this office, the abbreviated name plus primary key makes it unique within the office.
    */
    CqmItem
}

