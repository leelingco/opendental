//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:35 PM
//

package SparksToothChart;


public enum ToothGroupType
{
    /**
    * 0- This is areas of the tooth that are not included in any fillings at all.
    */
    Enamel,
    /**
    * 1
    */
    Cementum,
    /**
    * 2
    */
    M,
    /**
    * 3
    */
    O,
    /**
    * 4
    */
    D,
    /**
    * 5
    */
    B,
    /**
    * 6
    */
    L,
    /**
    * 7
    */
    F,
    /**
    * 8
    */
    I,
    /**
    * 9. class V. In addition to B or F
    */
    V,
    /**
    * 10. The pulp chamber and post or buildup.  Because our teeth are not yet transparent, these faces need to be translated forward.
    */
    Buildup,
    /**
    * 11. Only defined on some anterior teeth.  The small bit of enamel on the F that is not included in any filling.
    */
    EnamelF,
    /**
    * 12. Only defined on some anterior teeth.  The F portion of the D filling.
    */
    DF,
    /**
    * 13. Only defined on some anterior teeth.  The F portion of the M filling.
    */
    MF,
    /**
    * 14. Only defined on some anterior teeth.  The F portion of the I filling.
    */
    IF,
    /**
    * Only present in the special implant tooth object.
    */
    Implant,
    /**
    * Not used. Just a placeholder
    */
    Canals,
    /**
    * Used where there are unknown materials present such as 'default'.  Always ignore these groups.
    */
    None
}

