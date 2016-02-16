//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum EbenefitCategory
{
    /**
    * The X12 benefit categories.  Used to link the user-defined CovCats to the corresponding X12 category.0- Default.  Applies to all codes.
    */
    None,
    /**
    * 1- X12: 30 and 35. All ADA codes except ortho.  D0000-D7999 and D9000-D9999
    */
    General,
    /**
    * 2- X12: 23. ADA D0000-D0999.  This includes DiagnosticXray.
    */
    Diagnostic,
    /**
    * 3- X12: 24. ADA D4000
    */
    Periodontics,
    /**
    * 4- X12: 25. ADA D2000-D2699, and D2800-D2999.
    */
    Restorative,
    /**
    * 5- X12: 26. ADA D3000
    */
    Endodontics,
    /**
    * 6- X12: 27. ADA D5900-D5999
    */
    MaxillofacialProsth,
    /**
    * 7- X12: 36. Exclusive subcategory of restorative.  D2700-D2799
    */
    Crowns,
    /**
    * 8- X12: 37. ADA range?
    */
    Accident,
    /**
    * 9- X12: 38. ADA D8000-D8999
    */
    Orthodontics,
    /**
    * 10- X12: 39. ADA D5000-D5899 (removable), and D6200-D6899 (fixed)
    */
    Prosthodontics,
    /**
    * 11- X12: 40. ADA D7000
    */
    OralSurgery,
    /**
    * 12- X12: 41. ADA D1000
    */
    RoutinePreventive,
    /**
    * 13- X12: 4. ADA D0200-D0399.  So this is like an optional category which is otherwise considered to be diagnosic.
    */
    DiagnosticXRay,
    /**
    * 14- X12: 28. ADA D9000-D9999
    */
    Adjunctive
}

