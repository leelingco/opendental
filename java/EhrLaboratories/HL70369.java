//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package EhrLaboratories;


public enum HL70369
{
    //Used in ACK messages
    /**
    * Coding Systems.  OID:2.16.840.1.113883.12.369  Source phinvads.cdc.gov
    * This enum is not stored directly in the DB because of variable enum values, instead it is used to fill controls to allow users to pick from, or type their own.0 - Local general code (where z is an alphanumeric character). Actual value does not contain an underscore, but enumerations cannot start with a number.
    * Source:Locally defined codes for purpose of sender or receiver. Local codes can be identified by L (for backward compatibility) or 99zzz (where z is an alphanumeric character).Category:General CodesStatus:Active
    */
    _99zzz,
    /**
    * 1 - Local general code (where z is an alphanumeric character)
    * Source:Locally defined codes for purpose of sender or receiver. Local codes can be identified by L (for backward compatibility) or 99zzz (where z is an alphanumeric character).Category:General CodesStatus:Active
    */
    L,
    /**
    * 2 - American College of Radiology finding codes
    * Source:Index for Radiological Diagnosis Revised, 3rd Edition 1986, American College of Radiology, Reston, VA.Category:Specific Non-Drug CodeStatus:Active
    */
    ACR,
    /**
    * 3 - Table of HL7 Version 3 ActCode values
    * Source:For use in v2.x systems interoperating with V3 systems.  Identical to the code system 2.16.840.1.113883.5.4 ActCode in the Version 3 vocabulary.Category:General CodesStatus:New
    */
    ACTCODE,
    /**
    * 4 - Used to indicate that the target of the relationship will be a filtered subset of the total related set of targets. Used when there is a need to limit the number of components to the first, the last, the next, the total, the average or some other filtered or calculated subset.
    * Source:V3 coding system.   Download with V3 materials.Category:General CodesStatus:New
    */
    ACTRELSS,
    /**
    * 5 - German Alpha-ID v2006
    * Source:ID of the alphabetical Index ICD-10-GM-2006. Alpha-ID.Category:Status:New
    */
    ALPHAID2006,
    /**
    * 6 - German Alpha-ID v2007
    * Source:ID of the alphabetical Index ICD-10-GM-2007. Alpha-ID.Category:Status:New
    */
    ALPHAID2007,
    /**
    * 7 - German Alpha-ID v2008
    * Source:ID of the alphabetical Index ICD-10-GM-2008. Alpha-ID.Category:Status:New
    */
    ALPHAID2008,
    /**
    * 8 - German Alpha-ID v2009
    * Source:ID of the alphabetical Index ICD-10-GM-2009. Alpha-ID.Category:Status:New
    */
    ALPHAID2009,
    /**
    * 9 - Australian Medicines Terminology (v2)
    * Source:The national terminology to identify medicines used in Australia, using unique codes to deliver unambiguous, accurate and standardised names for both branded (trade) and generic (medicinal) products.Category:Drug codeStatus:New
    */
    AMTv2,
    /**
    * 10 - HL7 set of units of measure actual code is ANS+, but enumerations cannot contain special characters.
    * Source:HL7 set of units of measure based upon ANSI X3.50 - 1986, ISO 2988-83, and US customary units / see chapter 7, section 7.4.2.6.Category:Status:Active
    */
    ANS_,
    /**
    * 11 - WHO Adverse Reaction Terms
    * Source:WHO Collaborating Centre for International Drug Monitoring, Box 26, S-751 03, Uppsala, Sweden.Category:Drug codeStatus:Active
    */
    ART,
    ///<summary>12 - ASTM E1238/ E1467 Universal
    ///<para>Source:American Society for Testing & Materials and CPT4 (see Appendix X1 of Specification E1238 and Appendix X2 of Specification E1467).</para>
    ///<para>Category:Specific Non-Drug Code</para>
    ///<para>Status:Active</para></summary>
    AS4,
    /**
    * 13 - AS4 Neurophysiology Codes
    * Source:ASTM’s diagnostic codes and test result coding/grading systems for clinical neurophysiology. See ASTM Specification E1467, Appendix 2.Category:Specific Non-Drug CodeStatus:Active
    */
    AS4E,
    /**
    * 14 - American Type Culture Collection
    * Source:Reference cultures (microorganisms, tissue cultures, etc.), related biological materials and associated data. American Type Culture Collection, 12301 Parklawn Dr, Rockville MD, 20852. (301) 881-2600.Category:Specific Non-Drug CodeStatus:Active
    */
    ATC,
    /**
    * 15 - CPT-4
    * Source:American Medical Association, P.O. Box 10946, Chicago IL  60610.Category:Specific Non-Drug CodeStatus:Active
    */
    C4,
    /**
    * 16 - CPT-5
    * Source:Not currently being worked on, no proposed release date at this time.  American Medical Association, P.O. Box 10946, Chicago IL  60610.Category:Specific Non-Drug CodeStatus:Obsolete
    */
    C5,
    /**
    * 17 - College of American Pathologists Electronic Cancer Checklist
    * Source:Each code in this system represents a single line in a database template for the College of American Pathologists Electronic Cancer Checklist (CAP eCC).  Each line and its code corresponds to either a question or an answer selection.  The code is in a decimal format of #########.#########, where each "#" is an optional number.  The nine digits to the right of the Ckey decimal point make up a namespace identifier, which is specific to the center that created the database entries for the checklist line items with their unique Ckey values.   The namespace identifier for SNOMED Terminology Solutions at the College of American Pathologists is 1000043.  All Ckey values in the 2008 release use namespace 1000043.  The digits to the left of the decimal point are a locally assigned sequential key for the ChecklistTemplateItems table in the local CAP eCC database.  These codes are used to specify questions and answers selected in a CAP eCC template for transmission in an HL7 message, as defined by the NAACCR Pathology Workgroup and the CDC/NPCR Reporting Pathology Protocols II (RPP II) project. SNOMED Terminology Solutions, College of American Pathologists, 325 Waukegan Road, Northfield, Illinois, 60093, snomedsolutions@cap.orgCategory:Specific, Non-drug codeStatus:New
    */
    CAPECC,
    /**
    * 18 - Chemical abstract codes
    * Source:These include unique codes for each unique chemical, including all generic drugs. The codes do not distinguish among different dosing forms. When multiple equivalent CAS numbers exist, use the first one listed in USAN. USAN 1990 and the USP dictionary of drug names, William M. Heller, Ph.D., Executive Editor, United States Pharmacopeial Convention, Inc., 12601 Twinbrook Parkway, Rockville, MD 20852.Category:Drug codeStatus:Active
    */
    CAS,
    /**
    * 19 - Clinical Care Classification system
    * Source:Clinical Care Classification System (formerly Home Health Care Classification system) codes. The Clinical Care Classification (CCC) consists of two terminologies: CCC of Nursing Diagnoses  and CCC of Nursing Interventions both of which are classified by 21 Care Components. Virginia Saba, EdD, RN; Georgetown University School of Nursing; Washington, DC.Category:Specific Non-Drug CodeStatus:Active
    */
    CCC,
    /**
    * 20 - CDT-2 Codes
    * Source:American Dental Association’s Current Dental Terminology (CDT-2) code. American Dental Association, 211 E. Chicago Avenue,. Chicago, Illinois 60611.Category:Specific Non-Drug CodeStatus:Active
    */
    CD2,
    /**
    * 21 - CDC Analyte Codes
    * Source:Public Health Practice Program Office, Centers for Disease Control and Prevention, 4770 Buford Highway, Atlanta, GA, 30421. Also available via FTP: ftp.cdc.gov/pub/laboratory _info/CLIA and Gopher: gopher.cdc.gov:70/11/laboratory_info/CLIACategory:Specific Non-Drug CodeStatus:Active
    */
    CDCA,
    /**
    * 22 - CDC Emergency Department Acuity
    * Source:Patient Acuity indicates level of care required (Acute, Chronic, Critical)Category:Public Health CodeStatus:New
    */
    CDCEDACUITY,
    /**
    * 23 - CDC Methods/Instruments Codes
    * Source:Public Health Practice Program Office, Centers for Disease Control and Prevention, 4770 Buford Highway, Atlanta, GA, 30421. Also available via FTPCategory:Specific Non-Drug CodeStatus:Active
    */
    CDCM,
    /**
    * 24 - CDC National Healthcare Safety Network Codes
    * Source:A set of patient safety and healthcare personnel safety vocabulary concepts and associated identifiers maintained as a code system by the CDC's National Healthcare Safety Network.Category:General CodesStatus:New
    */
    CDCNHSN,
    /**
    * 25 - CDC BioSense RT observations (Census) - CDC
    * Source:List of BioSense RT observations (Clinical) used in OBX-3 like Temperature, Bloodpressure and Census related observations.Category:Public Health CodeStatus:New
    */
    CDCOBS,
    /**
    * 26 - CDC PHIN Vocabulary Coding System
    * Source:CDC Public Health Information Network Vocabulary Service (PHIN VS) coding system concepts are used when the public health concepts are not available in the Standard Development Organization(SDO) Vocabulary like SNOMED CT, LOINC, ICD-9, etc.. The concepts in this coding system will be mapped to SDO Vocabulary when it is available.Category:Public Health CodeStatus:New
    */
    CDCPHINVS,
    ///<summary>27 - Race & Ethnicity - CDC
    ///<para>Source:The U.S. Centers for Disease Control and Prevention (CDC) has prepared a code set for use in coding race and ethnicity data. This code set is based on current federal standards for classifying data on race and ethnicity, specifically the minimum race and ethnicity categories defined by the U.S. Office of Management and Budget (OMB) and a more detailed set of race and ethnicity categories maintained by the U.S. Bureau of the Census (BC). The main purpose of the code set is to facilitate use of federal standards for classifying data on race and ethnicity when these data are exchanged, stored, retrieved, or analyzed in electronic form. At the same time, the code set can be applied to paper-based record systems to the extent that these systems are used to collect, maintain, and report data on race and ethnicity in accordance with current federal standards.</para>
    ///<para>Category:Demographic Code</para>
    ///<para>Status:New</para></summary>
    CDCREC,
    /**
    * 28 - CDC Surveillance
    * Source:CDC Surveillance Codes. For data unique to specific public health surveillance requirements. Epidemiology Program Office, Centers for Disease Control and Prevention, 1600 Clifton Rd, Atlanta, GA, 30333. (404) 639-3661.Category:Specific Non-Drug CodeStatus:Active
    */
    CDS,
    /**
    * 29 - CEN ECG diagnostic codes (Obsolete)
    * Source:CEN ECG diagnostic codes – (Obsolete, retained for backwards compatibility only.  See the entry for the MDC coding system.)Category:Specific Non-Drug CodeStatus:Obsolete
    */
    CE,
    /**
    * 30 - CLIP
    * Source:Codes for radiology reports.  Simon Leeming, Beth Israel Hospital, Boston MA.Category:Specific Non-Drug CodeStatus:Active
    */
    CLP,
    /**
    * 31 - CPT Modifier Code
    * Source:Available for the AMA at the address listed for CPT above. These codes are found in Appendix A of CPT 2000 Standard Edition. (CPT 2000 Standard Edition, American Medical Association, Chicago, IL).Category:Specific Non-Drug CodeStatus:Active
    */
    CPTM,
    /**
    * 32 - COSTART
    * Source:International coding system for adverse drug reactions. In the USA, maintained by the FDA, Rockville, MD.Category:Drug codeStatus:Active
    */
    CST,
    /**
    * 33 - CDC Vaccine Codes
    * Source:National Immunization Program, Centers for Disease Control and Prevention, 1660 Clifton Road, Atlanta, GA, 30333Category:Drug codeStatus:Active
    */
    CVX,
    /**
    * 34 - DICOM Controlled Terminology
    * Source:Codes defined in DICOM Content Mapping Resource. Digital Imaging and Communications in Medicine (DICOM). NEMA Publication PS-3.16 National Electrical Manufacturers Association (NEMA). Rosslyn, VA, 22209. Available at:Category:Specific Non-Drug CodeStatus:Active
    */
    DCM,
    /**
    * 35 - EUCLIDES
    * Source:Available from Euclides Foundation International nv, Excelsiorlaan 4A, B-1930 Zaventem, Belgium; Phone: 32 2 720 90 60.Category:Specific Non-Drug CodeStatus:Active
    */
    E,
    /**
    * 36 - Euclides  quantity codes
    * Source:Available from Euclides Foundation International nv (see above)Category:Specific Non-Drug CodeStatus:Active
    */
    E5,
    /**
    * 37 - Euclides Lab method codes
    * Source:Available from Euclides Foundation International nv, Excelsiorlaan 4A, B-1930 Zaventem, Belgium; Phone: 32 2 720 90 60.Category:Specific Non-Drug CodeStatus:Active
    */
    E6,
    /**
    * 38 - Euclides Lab equipment codes
    * Source:Available from Euclides Foundation International nv (see above)Category:Specific Non-Drug CodeStatus:Active
    */
    E7,
    /**
    * 39 - Education Level
    * Source:For use in v2.x systems interoperating with V3 systems.  Identical to the code system 2.16.840.1.113883.5.1077 EducationLevel in the Version 3 vocabulary.Category:General CodesStatus:New
    */
    EDLEVEL,
    /**
    * 40 - Entity Code
    * Source:For use in v2.x systems interoperating with V3 systems.  Identical to the code system 2.16.840.1.113883.5.1060 EntityCode in the Version 3 vocabulary.Category:General CodesStatus:New
    */
    ENTITYCODE,
    /**
    * 41 - Entity Handling Code
    * Source:For use in v2.x systems interoperating with V3 systems.  Identical to the code system 2.16.840.1.113883.5.42 EntityHandling in the Version 3 vocabulary.Category:General CodesStatus:New
    */
    ENTITYHDLG,
    /**
    * 42 - Enzyme Codes
    * Source:Enzyme Committee of the International Union of Biochemistry and Molecular Biology. Enzyme Nomenclature: Recommendations on the Nomenclature and Classification of Enzyme-Catalysed Reactions. London: Academic Press, 1992.Category:Specific Non-Drug CodeStatus:Active
    */
    ENZC,
    /**
    * 43 - EPA SRS
    * Source:Subset of EPA SRS listing chemicals that are present in ECOTOX, STORET and TRI.Category:Non-Drug Chemical CodeStatus:New
    */
    EPASRS,
    /**
    * 44 - Unique Ingredient Identifier (UNII)
    * Source:The Unique Ingredient Identifier (UNII) generated from the FDA Substance Registration System (SRS).Category:Drug CodeStatus:New
    */
    FDAUNII,
    /**
    * 45 - First DataBank Drug Codes
    * Source:National Drug Data File. Proprietary product of First DataBank, Inc. (800) 633-3453, or http://www.firstdatabank.com.Category:Drug codeStatus:Active
    */
    FDDC,
    /**
    * 46 - First DataBank Diagnostic Codes
    * Source:Used for drug-diagnosis interaction checking. Proprietary product of First DataBank, Inc. As above for FDDC.Category:Drug codeStatus:Active
    */
    FDDX,
    ///<summary>47 - FDA K10
    ///<para>Source:Dept. of Health & Human Services, Food & Drug Administration, Rockville, MD 20857. (device & analyte process codes).</para>
    ///<para>Category:Specific Non-Drug Code</para>
    ///<para>Status:Active</para></summary>
    FDK,
    /**
    * 48 - FIPS 5-2 (State)
    * Source:Codes for the Identification of the States, the District of Columbia and the Outlying Areas of the United States, and Associated Areas.Category:Demographic CodeStatus:New
    */
    FIPS5_2,
    /**
    * 49 - FIPS 6-4 (County)
    * Source:Federal Information Processing Standard (FIPS) 6-4 provides the names and codes that represent the counties and other entities treated as equivalent legal and/or statistical subdivisions of the 50 States, the District of Columbia, and the possessions and freely associated areas of the United States.Category:Demographic CodeStatus:New
    */
    FIPS6_4,
    /**
    * 50 - G-DRG German DRG Codes v 2004
    * Source:German Handbook for DRGs. The THREE versions, "2004" , "2005" and "2006" are activeCategory:Status:Obsolete
    */
    GDRG2004,
    /**
    * 51 - G-DRG German DRG Codes v 2005
    * Source:German Handbook for DRGs. The THREE versions, "2004" , "2005" and "2006" are activeCategory:Status:Obsolete
    */
    GDRG2005,
    /**
    * 52 - G-DRG German DRG Codes v 2006
    * Source:German Handbook for DRGs. The THREE versions, "2004" , "2005" and "2006" are activeCategory:Status:Active
    */
    GDRG2006,
    /**
    * 53 - G-DRG German DRG Codes v2007
    * Source:German Handbook for DRGs 2007.Category:Status:New
    */
    GDRG2007,
    /**
    * 54 - G-DRG German DRG Codes v2008
    * Source:German Handbook for DRGs 2008.Category:Status:New
    */
    GDRG2008,
    /**
    * 55 - G-DRG German DRG Codes v2008
    * Source:German Handbook for DRGs 2009.Category:Status:New
    */
    GDRG2009,
    /**
    * 56 - German Major Diagnostic Codes v 1004
    * Source:German Major Diagnostic Codes version "2004"Category:Status:Obsolete
    */
    GMDC2004,
    /**
    * 57 - German Major Diagnostic Codes v2005
    * Source:Category:Status:Obsolete
    */
    GMDC2005,
    /**
    * 58 - German Major v2006 Diagnostic Codes
    * Source:Category:Status:Active
    */
    GMDC2006,
    /**
    * 59 - German Major Diagnostic Codes v2007
    * Source:German Major Diagnostic Codes 2007.Category:Status:Active
    */
    GMDC2007,
    /**
    * 60 - German Major Diagnostic Codes v2008
    * Source:German Major Diagnostic Codes v2008.Category:Status:New
    */
    GMDC2008,
    /**
    * 61 - HIBCC
    * Source:Health Industry Business Communications Council, 5110 N. 40th St., Ste 120, Phoenix, AZ 85018.Category:Specific Non-Drug CodeStatus:Active
    */
    HB,
    /**
    * 62 - CMS (formerly HCFA)  Common Procedure Coding System
    * Source:HCPCS: contains codes for medical equipment, injectable drugs, transportation services, and other services not found in CPT4. http://www.cms.hhs.gov/MedHCPCSGenInfo/Category:Specific Non-Drug CodeStatus:Active
    */
    HCPCS,
    /**
    * 63 - Health Care Provider Taxonomy
    * Source:Formerly the responsibility of Workgroup 15 (Provider Information) within ANSI ASC X12N, all maintenance is now done by NUCC (turned over in 2001).   Primary distribution is the responsibility of Washington Publishing Company, through its World Wide Web Site http://www.wpc-edi.com.  Requests for new codes or changes may be  done through the same website.  For further information, NUCC may be contacted at: Stephanie Moncada, NUCC Secretary American Medical Association 515 N. State St. Chicago, IL 60610 Email: stephanie.moncada@ama-assn.orgCategory:Specific Non-Drug CodeStatus:Updated
    */
    HCPT,
    /**
    * 64 - Home Health Care
    * Source:Home Health Care Classification System; Virginia Saba, EdD, RN; Georgetown University School of Nursing; Washington, DC. Superceded by 'CCC' (see above); this entry is retained for backward-compatibility.Category:Specific Non-Drug CodeStatus:Active
    */
    HHC,
    /**
    * 65 - Health Outcomes
    * Source:Health Outcomes Institute codes for outcome variables available (with responses) from Stratis Health (formerly Foundation for Health Care Evaluation and Health Outcomes Institute), 2901 Metro Drive, Suite 400, Bloomington, MN, 55425-1525; (612) 854-3306 (voice); (612) 853-8503 (fax); dziegen@winternet.com. See examples in the Implementation Guide.Category:Specific Non-Drug CodeStatus:Active
    */
    HI,
    /**
    * 66 - HL7 Defined Codes where nnnn is the HL7 table number
    * Source:Health Level Seven where nnnn is the HL7 table number.   Comment pending from INM.Category:General codeStatus:Obsolete
    */
    HL7nnnn,
    /**
    * 67 - Japanese Nationwide Medicine Code
    * Source:Category:Status:Active
    */
    HOT,
    /**
    * 68 - CMS (formerly HCFA )Procedure Codes (HCPCS)
    * Source:Health Care Financing Administration (HCFA) Common Procedure Coding System (HCPCS) including modifiers.[1]Category:Specific Non-Drug CodeStatus:Active
    */
    HPC,
    /**
    * 69 - Healthcare Service Location
    * Source:A comprehensive classification of locations and settings where healthcare services are provided. This code system is based on the NHSN location code system that has been developed over a number of years through CDC's interaction with a variety of healthcare facilities and is intended to serve a variety of reporting needs where coding of healthcare service locations is required.Category:Specific Non-Drug CodeStatus:New
    */
    HSLOC,
    /**
    * 70 - ICD-10
    * Source:World Health Publications, Albany, NY.Category:Specific Non-Drug CodeStatus:Active
    */
    I10,
    /**
    * 71 - International Classification of Diseases, 10th  Revision, Clinical Modification (ICD-10-CM)
    * Source:ICD-10-CM is a clinical modification of the International Statistical Classification of Diseases and Related Health Problems, 10th revision (ICD-10) published by the United States for reporting diagnosis in morbidity settings. Additional information is available at: http://www.cdc.gov/nchs/icd/icd10cm.htm.Category:Specific Non-Drug CodeStatus:Active
    */
    I10C,
    /**
    * 72 - ICD 10 Germany 2004
    * Source:Three code sets exist I10G2004, I10G2005, I10G2006Category:Status:Obsolete
    */
    I10G2004,
    /**
    * 73 - ICD 10 Germany 2005
    * Source:Three code sets exist I10G2004Category:Status:Obsolete
    */
    I10G2005,
    /**
    * 74 - ICD 10 Germany 2006
    * Source:Three code sets exist I10G2004Category:Status:Active
    */
    I10G2006,
    /**
    * 75 - International Classification of Diseases, 10th  Revision, Procedure Coding System (ICD-10-PCS)
    * Source:ICD-10-PCS is a procedure classification published by the United States for classifying procedures performed in hospital inpatient health care settings. Additional information is available at: http://www.cms.gov/Medicare/Coding/ICD10/2013-ICD-10-PCS-GEMs.html.Category:Specific Non-Drug CodeStatus:Active
    */
    I10P,
    /**
    * 76 - ICD9
    * Source:World Health Publications, Albany, NY.Category:Specific Non-Drug CodeStatus:Active
    */
    I9,
    /**
    * 77 - International Classification of Diseases, 9th  Revision, Clinical Modification (ICD-9-CM)
    * Source:ICD-9-CM is a clinical modification of the World Health Organization's 9th Revision, International Classification of Diseases (ICD-9). ICD-9-CM is the official system of assigning codes to diagnoses and procedures associated with healthcare utilization in the United States. Additional information is available at: http://www.cms.hhs.gov/ICD9ProviderDiagnosticCodes/08_ICD10.asp.Category:Specific Non-Drug CodeStatus:Active
    */
    I9C,
    /**
    * 78 - ICD-9CM Diagnosis codes
    * Source:Indicates codes from ICD-9-CM drawn from Volumes 1 and 2, which cover diagnosis codes only.Category:Status:Active
    */
    I9CDX,
    /**
    * 79 - ICD-9CM Procedure codes
    * Source:Indicates codes from ICD-9-CM drawn from Volume 3, which covers procedure codes only.Category:Status:Active
    */
    I9CP,
    /**
    * 80 - ISBT
    * Source:Retained for backward compatibility only as of v 2.5. This code value has been superceded by the individual codes IBTnnnn (where nnnn identifies a specific table in ISBT 128).  Tables commencing with IBT are used in transfusion/transplantation and maintained by ICCBBA, PO Box 11309, San Bernardino, CACategory:Specific Non-Drug CodeStatus:Obsolete
    */
    IBT,
    /**
    * 81 - ISBT 128 Standard transfusion/transplantation data items
    * Source:ISBT 128 Standard data items used in transfusion/transplantation and maintained by ICCBBA, PO Box 11309, San Bernardino, CACategory:Specific Non-Drug CodeStatus:New
    */
    IBT0001,
    /**
    * 82 - ICHPPC-2
    * Source:International Classification of Health Problems in Primary Care, Classification Committee of World Organization of National Colleges, Academies and Academic Associations of General Practitioners (WONCA), 3rd edition. An adaptation of ICD9 intended for use in General Medicine, Oxford University Press.Category:Specific Non-Drug CodeStatus:Active
    */
    IC2,
    /**
    * 83 - ICD-10 Australian modification
    * Source:Category:Status:Active
    */
    ICD10AM,
    /**
    * 84 - ICD-10 Canada
    * Source:Category:Status:Active
    */
    ICD10CA,
    /**
    * 85 - ICD 10 Germany v2007
    * Source:ICD German modification for 2007.Category:Status:Active
    */
    ICD10GM2007,
    /**
    * 86 - ICD 10 Germany v2008
    * Source:ICD German modification for 2008.Category:Status:New
    */
    ICD10GM2008,
    /**
    * 87 - ICD 10 Germany v2009
    * Source:ICD German modification for 2009.Category:Status:New
    */
    ICD10GM2009,
    /**
    * 88 - International Classification of Diseases for Oncology
    * Source:International Classification of Diseases for Oncology, 2nd Edition. World Health Organization: Geneva, Switzerland, 1990. Order from: College of American Pathologists, 325 Waukegan Road, Northfield, IL, 60093-2750. (847) 446-8800.Category:Specific Non-Drug CodeStatus:Active
    */
    ICDO,
    /**
    * 89 - International Classification of Disease for Oncology Second Edition
    * Source:Percy C, VanHolten V, and Muir C, editors. International Classification of Diseases for Oncology. Second Edition. Geneva: World Health Organization; 1990.  The ICD-9 neoplasm structure did not include morphology and there was a growing interest by physicians to establish a coding system for morphology.  ICD-O is used in cancer registries (and other related areas) for coding the topography (site) and morphology of a neoplasm. The topography code uses similar categories as ICD-10 for malignant neoplasms allowing greater specificity for the site of non-malignant neoplasms than in ICD-10.  The topography code consists of an alphabetic character (the letter C) followed by two numeric digits, a decimal point, and a numeric digit. The morphology code consists of a 6-digit numeric code which consists of three parts: histologic type (4-digit), behavior code (1-digit), and grading or differentiation (1-digit). ICD-O-2 is used for tumors diagnosed prior to 2001.Category:Specific Non-Drug CodeStatus:New
    */
    ICDO2,
    /**
    * 90 - International Classification of Disease for Oncology Third Edition
    * Source:Fritz A, Percy C, Jack A, Shanmugaratnam K, Sobin L, Parkin D, et al, editors. International Classification of Diseases for Oncology. Third Edition. Geneva: World Health Organization; 2000. The ICD-9 neoplasm structure did not include morphology and there was a growing interest by physicians to establish a coding system for morphology.  ICD-O is used in cancer registries (and other related areas) for coding the topography (site) and morphology of a neoplasm. The topography code uses similar categories as ICD-10 for malignant neoplasms allowing greater specificity for the site of non-malignant neoplasms than in ICD-10.  The topography code consists of an alphabetic character (the letter C) followed by two numeric digits, a decimal point, and a numeric digit. The morphology code consists of a 6-digit numeric code which consists of three parts: histologic type, behavior code, and grading or differentiation. In the third edition the morphology codes were revised, especially for leukemias and lymphomas. ICD-O-3 is used for tumors diagnosed in 2001 and later.Category:Specific Non-Drug CodeStatus:New
    */
    ICDO3,
    /**
    * 91 - International Classification of Functioning, Disability and Health (ICF)
    * Source:ICF is a classification of those characteristics of health involving functional impairments, activity limitations, or participation restrictions that are often associated with disability. The ICF classification complements the World Health Organization's (WHO) International Classification of Diseases-10th Revision (ICD), which contains information on diagnosis and health condition, but not on functional status. The ICD and ICF constitute the core classifications in the WHO Family of International Classifications (WHO-FIC). Additional information is available at: http://www.cdc.gov/nchs/icd/icf.htm.Category:Specific Non-Drug CodeStatus:Active
    */
    ICF,
    /**
    * 92 - ICCS
    * Source:Commission on Professional and Hospital Activities, 1968 Green Road, Ann Arbor, MI 48105.Category:Specific Non-Drug CodeStatus:Active
    */
    ICS,
    /**
    * 93 - International Classification of Sleep Disorders
    * Source:International Classification of Sleep Disorders Diagnostic and Coding Manual, 1990, available from American Sleep Disorders Association, 604 Second Street SW, Rochester, MN  55902Category:Specific Non-Drug CodeStatus:Active
    */
    ICSD,
    /**
    * 94 - ISO 2955.83 (units of measure) with HL7 extensions. Actual value is ISO+, but enumerations cannot contain special characters.
    * Source:See chapter 7, section 7.4.2.6Category:Status:Active
    */
    ISO_,
    /**
    * 95 - ISO 3166-1 Country Codes
    * Source:International Standards Organization standard 3166 contains 3 parts.  Part 1 contains three tables for codes for countries of the world.  These are 2-character alphabetic, 3-character alphabetic, and numeric codes.Category:DemographicsStatus:New
    */
    ISO3166_1,
    /**
    * 96 - ISO 3166-2 Country subdivisions
    * Source:International Standards Organization standard 3166 contains 3 parts.  Part 2 contains a complete breakdown into a relevant level of administrative subdivisions of all countries listed in ISO 3166-1. The code elements used consist of the alpha-2 code element from ISO 3166-1 followed by a separator and a further string of up to three alphanumeric characters e. g. DK-025 for the Danish county Roskilde.Category:DemographicsStatus:New
    */
    ISO3166_2,
    /**
    * 97 - ISO4217 Currency Codes
    * Source:ISO's currency codes, which are based on the ISO country codes are published in the standard ISO 4217:2008 Codes for the representation of currencies and funds.  This International Standard specifies the structure for a three-letter alphabetic code and an equivalent three-digit numeric code for the representation of currencies and funds. For those currencies having minor units, it also shows the decimal relationship between such units and the currency itself.Category:FinancialStatus:New
    */
    ISO4217,
    /**
    * 98 - ISO 639 Language
    * Source:International Standards Organization codes for the representation of names of languages.  ISO 639 provides two sets of language codes, one as a two-character code set (639-1) and another as a three-character code set (639-2) for the representation of names of languages.  ISO 639-3, Codes for the representation of names of languages - Part 3: Alpha-3 code for comprehensive coverage of languages, is a code list that aims to define three-letter identifiers for all known human languages.Category:DemographicsStatus:New
    */
    ISO639,
    /**
    * 99 - ISO Defined Codes where nnnn is the ISO table number. (deprecated)
    * Source:International Standards Organization tables.  This has been deprecated since the ISO numbered standards are not the same as tables, and there are no "ISO table numbers".  Some standards contains tables of values, and some contain more than one table.  In the future, specific tables of values drawn from ISO standards will have explicit entries here in table 0396.  Use the specific entries for identified tables instead of this one.Category:General codeStatus:Obsolete
    */
    ISOnnnn,
    /**
    * 100 - Integrated Taxonomic Information System
    * Source:This is a taxonomic hierarchy for living organisms.Category:Status:Active
    */
    ITIS,
    /**
    * 101 - IUPAC/IFCC Component Codes
    * Source:Codes used by IUPAC/IFF to identify the component (analyte) measured. Contact Henrik Olesen, as above for IUPP.Category:Specific Non-Drug CodeStatus:Active
    */
    IUPC,
    /**
    * 102 - IUPAC/IFCC Property Codes
    * Source:International Union of Pure and Applied Chemistry/International Federation of Clinical Chemistry. The Silver Book: Compendium of terminology and nomenclature of properties in clinical laboratory sciences. Oxford: Blackwell Scientific Publishers, 1995. Henrik Olesen, M.D., D.M.Sc., Chairperson, Department of Clinical Chemistry, KK76.4.2, Rigshospitalet, University Hospital of Copenhagen, DK-2200, Copenhagen. http://inet.uni-c.dk/~qukb7642/Category:Specific Non-Drug CodeStatus:Active
    */
    IUPP,
    ///<summary>103 - JLAC/JSLM, nationwide laboratory code
    ///<para>Source:Source: Classification &Coding for Clinical Laboratory. Japanese Society of Laboratory Medicine(JSLM, Old:Japan Society of Clinical Pathology). Version 10, 1997. A multiaxial code  including a analyte code (e.g., Rubella = 5f395), identification code (e.g., virus ab IGG=1431), a specimen code (e.g., serum =023) and a method code (e.g., ELISA = 022)</para>
    ///<para>Category:</para>
    ///<para>Status:Active</para></summary>
    JC10,
    /**
    * 104 - Japanese Chemistry
    * Source:Clinical examination classification code. Japan Association of Clinical Pathology. Version 8, 1990. A multiaxial code  including a subject code (e.g., Rubella = 5f395, identification code (e.g., virus ab IGG), a specimen code (e.g., serum =023) and a method code (e.g., ELISA = 022)Category:withdrawnStatus:Active
    */
    JC8,
    /**
    * 105 - Japanese Image Examination Cache
    * Source:Category:Status:Active
    */
    JJ1017,
    /**
    * 106 - LanguaL
    * Source:LanguaL stands for "Langua aLimentaria" or "language of food"  LanguaL is a multilingual thesaural system using facetted classification about food.  Terms reprented in PHIN VADS will be limited to the English language version.Category:Food CodeStatus:New
    */
    LANGUAL,
    /**
    * 107 - Local billing code
    * Source:Local billing codes/names (with extensions if needed).Category:General codeStatus:Active
    */
    LB,
    /**
    * 108 - Logical Observation Identifier Names and Codes (LOINC®)
    * Source:Logical Observation Identifiers Names and Codes (LOINC®) provides a set of universal codes and names for identifying laboratory and other clinical observations. One of the main goals of LOINC is to facilitate the exchange and pooling of results for clinical care, outcomes management, and research. LOINC was initiated by Regenstrief Institute research scientists who continue to develop it with the collaboration of the LOINC Committee. The LOINC table, LOINC codes, and LOINC panels and forms file are copyright © 1995-2011, Regenstrief Institute, Inc. and the LOINC Committee and available at no cost (http://loinc.org) under the license at http://loinc.org/terms-of-use. The laboratory portion of the LOINC database contains the usual clinical laboratory categories of chemistry, hematology, serology, microbiology (including parasitology and virology), toxicology; as well as categories for drugs and the cell counts, antibiotic susceptibilities, and more. The clinical portion of the LOINC database includes entries for vital signs, hemodynamics, intake/output, EKG, obstetric ultrasound, cardiac echo, radiology report titles, pulmonary ventilator management, document and section titles, patient assessment instruments (e.g. Glascow Coma Score, PHQ-9 depression scale, CMS-required patient assessment instruments), and other clinical observations.Category:Specific Non-Drug CodeStatus:Active
    */
    LN,
    /**
    * 109 - Medicaid
    * Source:Medicaid billing codes/names.Category:Specific Non-Drug CodeStatus:Active
    */
    MCD,
    /**
    * 110 - Medicare
    * Source:Medicare billing codes/names.Category:Specific Non-Drug CodeStatus:Active
    */
    MCR,
    /**
    * 111 - Medical Device Communication
    * Source:EN ISO/IEEE 11073-10101 Health informatics – Point-of-care medical device communication - NomenclatureCategory:Specific Non-Drug CodeStatus:Active
    */
    MDC,
    /**
    * 112 - Medispan Diagnostic Codes
    * Source:Codes Used for drug-diagnosis interaction checking. Proprietary product. Hierarchical drug codes for identifying drugs down to manufacturer and pill size. MediSpan, Inc., 8425 Woodfield Crossing Boulevard, Indianapolis, IN 46240. Tel: (800) 428-4495. URL: http://www.medispan.com/Products/index.aspx?cat=1. As above for MGPI.Category:Drug codeStatus:Active
    */
    MDDX,
    /**
    * 113 - Medical Economics Drug Codes
    * Source:Proprietary Codes for identifying drugs. Proprietary product of Medical Economics Data, Inc. (800) 223-0581.Category:Drug codeStatus:Active
    */
    MEDC,
    /**
    * 114 - MIME Media Type IANA
    * Source:Encoding as defined by MIME (Multipurpose Internet Mail Extensions)Category:Specific Non-Drug CodeStatus:Active
    */
    MEDIATYPE,
    /**
    * 115 - Medical Dictionary for Drug Regulatory Affairs (MEDDRA)
    * Source:Patrick Revelle, Director MSSO 12011 Sunset Hills Road, VAR1/7B52 Reston, VA 20190 Patrick.Revelle@ngc.com http://www.meddramsso.com/MSSOWeb/index.htmCategory:Drug codeStatus:Active
    */
    MEDR,
    /**
    * 116 - Medical Economics Diagnostic Codes
    * Source:Used for drug-diagnosis interaction checking. Proprietary product of Medical Economics Data, Inc. (800) 223-0581.Category:Drug codeStatus:Active
    */
    MEDX,
    /**
    * 117 - Medispan GPI
    * Source:Medispan hierarchical drug codes for identifying drugs down to manufacturer and pill size. Proprietary product of MediSpan, Inc., 8425 Woodfield Crossing Boulevard, Indianapolis, IN 46240. Tel: (800) 428-4495.Category:Drug codeStatus:Active
    */
    MGPI,
    /**
    * 118 - CDC Vaccine Manufacturer Codes
    * Source:As above, for CVXCategory:Drug codeStatus:Active
    */
    MVX,
    /**
    * 119 - Industry (NAICS)
    * Source:The North American Industry Classification System (NAICS) consists of a set of six digit codes that classify and categorize industries.  It also organizes the categories on a production/process-oriented basis.  This new, uniform, industry-wide classification system has been designed as the index for statistical reporting of all economic activities of the U.S., Canada, and Mexico. Mapping is available between SIC 1987 and NAICS 2002 codes at U.S. Census Bureau website. Mapping is also available between NAICS 2002 and NAICS 2007 at U.S. Census Bureau websiteCategory:Demographic CodeStatus:New
    */
    NAICS,
    /**
    * 120 - NCPDP code list for data element nnnn [as used in segment sss]
    * Source:NCPDP maintain code list associated with the specified Data Element (nnnn) and Segment (sss).  The Segment portion is optional if there is no specialization of the Data Element codes between segments.  Examples:   NCPDP1131RES = code set defined for NCPDP data element 1131 as used in the RES segment (Code List Qualifier – Response Code)   NCPDP1131STS = code set defined for NCPDP data element 1131 as used in the STS segment (Code List Qualifier – Reject Code)   NCPDP9701 = code set defined for NCPDP data element 9701 (Individual Relationship, Coded).  No specialization to a segment exists for this data element. National Council for Prescription Drug Programs, 924Ø East Raintree Drive, Scottsdale, AZ  8526Ø. Phone: (48Ø) 477-1ØØØ Fax: (48Ø) 767-1Ø42 e-mail: ncpdp@ncpdp.org www.ncpdp.orgCategory:Status:Active
    */
    NCPDPnnnnsss,
    /**
    * 121 - NANDA
    * Source:North American Nursing Diagnosis Association, Philadelphia, PA.Category:Specific Non-Drug CodeStatus:Active
    */
    NDA,
    /**
    * 122 - National drug codes
    * Source:These provide unique codes for each distinct drug, dosing form, manufacturer, and packaging. (Available from the National Drug Code Directory, FDA, Rockville, MD, and other sources.)Category:Drug codeStatus:Active
    */
    NDC,
    /**
    * 123 - NDF-RT (Drug Classification)
    * Source:The National Drug File RT (NDF-RT) is published by the US Veterans' Administration (VA). NDF-RT covers clinical drugs used at the VA. The NCI version of NDF-RT is used by NCI to provide automated terminology access to the Food and Drug Administration (FDA) Structured Product Label (SPL) initiative.Category:Drug CodeStatus:New
    */
    NDFRT,
    /**
    * 124 - Nursing Interventions Classification
    * Source:Iowa Intervention Project, College of Nursing, University of Iowa, Iowa City, IowaCategory:Specific Non-Drug CodeStatus:Active
    */
    NIC,
    /**
    * 125 - Source of Information (Immunization)
    * Source:CDC National Immunization Program's (NIP) defined table to be used in HL7 2.x message RXA-9 for documenting the source of information regarding immunization. E.g. From school, provider,public health agency.Category:Public Health CodeStatus:New
    */
    NIP001,
    /**
    * 126 - Substance refusal reason
    * Source:CDC National Immunization Program's (NIP) defined table to be used in HL7 2.x message RXA-18 for substance refusal reason (reasons for not having vaccination). E.g. Religious exemption, parental decisionCategory:Public Health CodeStatus:New
    */
    NIP002,
    /**
    * 127 - Vaccination - Contraindications, Precautions, and Immunities
    * Source:CDC National Immunization Program's (NIP) defined table for vaccine contraindications and precautions. E.g. Allergy to egg ingestion, thimerosolCategory:Public Health CodeStatus:New
    */
    NIP004,
    /**
    * 128 - Vaccinated at location (facility)
    * Source:CDC National Immunization Program's (NIP) defined table for vaccinated at location (facility). E.g.  Private doctor's office, Public Health ClinicCategory:Public Health CodeStatus:New
    */
    NIP007,
    /**
    * 129 - Vaccine purchased with (Type of funding)
    * Source:CDC National Immunization Program's (NIP) defined table enumerates the type of funds used for purchasing vaccine. E.g. Public funds, Military fundsCategory:Public Health CodeStatus:New
    */
    NIP008,
    /**
    * 130 - Reported adverse event previously
    * Source:CDC National Immunization Program's (NIP) defined table enumerates the authorities to whom the vaccination related adverse events were previously reported. E.g. To health department, To manufacturerCategory:Public Health CodeStatus:New
    */
    NIP009,
    /**
    * 131 - VAERS Report type
    * Source:CDC National Immunization Program's (NIP) defined table enumerates the type of report used in VAERS (Vaccination Adverse Event Reporting System). E.g. Initial, Follow-upCategory:Public Health CodeStatus:New
    */
    NIP010,
    /**
    * 132 - Notifiable Event (Disease/Condition) Code List
    * Source:List of notifiable events, which includes infectious and non-infectious disease or conditions. This list includes events that are notifiable at the state and national level.Category:Public Health CodeStatus:New
    */
    NND,
    /**
    * 133 - National Provider Identifier
    * Source:Health Care Finance Administration, US Dept. of Health and Human Services, 7500 Security Blvd., Baltimore, MD 21244.Category:Specific Non-Drug CodeStatus:Active
    */
    NPI,
    /**
    * 134 - National Uniform Billing Committee Code
    * Source:http://www.nubc.org/Category:Status:Active
    */
    NUBC,
    /**
    * 135 - Flavors of NULL
    * Source:System of coded values for Flavors of Null, as used in HL7 Version 3 standards.  Identical to the HL7 version 3  coding system 2.16.840.1.113883.5.1008 NullFlavorCategory:General CodesStatus:New
    */
    NULLFL,
    /**
    * 136 - German Procedure Codes
    * Source:Source: OPS Operationen- und Prozedurenschlussel. Three versions are active.Category:Status:Obsolete
    */
    O301,
    /**
    * 137 - OPS Germany 2004
    * Source:Source: OPS Operationen- und Prozedurenschlussel. Three versions are activeCategory:Status:Obsolete
    */
    O3012004,
    /**
    * 138 - OPS Germany 2005
    * Source:Source: OPS Operationen- und Prozedurenschlussel. Three versions are activeCategory:Status:Obsolete
    */
    O3012005,
    /**
    * 139 - Ops Germany 2006
    * Source:Source: OPS Operationen- und Prozedurenschlussel. Three versions are activeCategory:Status:Active
    */
    O3012006,
    /**
    * 140 - Observation Method Code
    * Source:For use in v2.x systems interoperating with V3 systems.  Identical to the code system 2.16.840.1.113883.5.84 ObservationMethod in the Version 3 vocabulary.Category:General CodesStatus:New
    */
    OBSMETHOD,
    /**
    * 141 - Omaha System
    * Source:Omaha Visiting Nurse Association, Omaha, NB.Category:Specific Non-Drug CodeStatus:Active
    */
    OHA,
    /**
    * 142 - OPS Germany v2007
    * Source:Source: OPS Operationen- und Prozedurenschlussel 2007.Category:Status:Active
    */
    OPS2007,
    /**
    * 143 - OPS Germany v2008
    * Source:Source: OPS Operationen- und Prozedurenschlussel 2008.Category:Status:New
    */
    OPS2008,
    /**
    * 144 - OPS Germany v2008
    * Source:Source: OPS Operationen- und Prozedurenschlussel 2009.Category:Status:New
    */
    OPS2009,
    /**
    * 145 - CDC Public Health Information Network (PHIN) Question
    * Source:CDC Public Health Questions used in HL7 Message as observation identifiers. These question or observation identifiers are used in CDC's message implementation guides and will be passed in HL7 OBX-3 or Observation.CodeCategory:Public Health CodeStatus:New
    */
    PHINQUESTION,
    /**
    * 146 - CDC PHLIP Lab result codes that are not covered in SNOMED at the time of this implementation
    * Source:APHL CDC co-sponsored PHLIPCategory:Lab CodeStatus:New
    */
    PLR,
    /**
    * 147 - CDC PHLIP Lab test codes, where LOINC concept is too broad or not yet available, especially as needed for ordering and or lab to lab reporting )
    * Source:APHL CDC co-sponsored PHLIPCategory:Lab CodeStatus:New
    */
    PLT,
    /**
    * 148 - POS Codes
    * Source:HCFA Place of Service Codes for Professional Claims (see http://www.cms.hhs.gov/PlaceofServiceCodes/).Category:Specific Non-Drug CodeStatus:Active
    */
    POS,
    /**
    * 149 - Paticipation Mode Code
    * Source:For use in v2.x systems interoperating with V3 systems.  Identical to the code system 2.16.840.1.113883.5.1064 ParticipationMode in the Version 3 vocabulary.Category:General CodesStatus:New
    */
    PRTCPTNMODE,
    /**
    * 150 - Read Classification
    * Source:The Read Clinical Classification of Medicine, Park View Surgery, 26 Leicester Rd., Loughborough LE11 2AG (includes drug procedure and other codes, as well as diagnostic codes).Category:Specific Non-Drug CodeStatus:Active
    */
    RC,
    /**
    * 151 - Used initially for contact roles.
    * Source:For use in v2.x systems interoperating with V3 systems.  Identical to the code system 2.16.840.1.113883.5.111 RoleCode in the Version 3 vocabulary.Category:General CodesStatus:new
    */
    ROLECLASS,
    /**
    * 152 - Participation Mode
    * Source:For use in v2.x systems interoperating with V3 systems.  Identical to the code system 2.16.840.1.113883.5.111 RoleCode in the Version 3 vocabulary.Category:General CodesStatus:New
    */
    ROLECODE,
    /**
    * 153 - Specifies the mode, immediate versus deferred or queued, by which a receiver should communicate its receiver responsibilities.
    * Source:V3 coding system, available in RIM download materials.Category:General CodesStatus:New
    */
    RSPMODE,
    /**
    * 154 - RxNorm
    * Source:RxNorm provides standard names for clinical drugs (active ingredient + strength + dose form) and for dose forms as administered to a patient. It provides links from clinical drugs, both branded and generic, to their active ingredients, drug components (active ingredient + strength), and related brand names. NDCs (National Drug Codes) for specific drug products (where there are often many NDC codes for a single product) are linked to that product in RxNorm. RxNorm links its names to many of the drug vocabularies commonly used in pharmacy management and drug interaction software, including those of First Databank, Micromedex, MediSpan, and Multum. By providing links between these vocabularies, RxNorm can mediate messages between systems not using the same software and vocabulary.RxNorm is one of a suite of designated standards for use in U.S. Federal Government systems for the electronic exchange of clinical health information.Category:Drug CodeStatus:New
    */
    RXNORM,
    /**
    * 155 - SNOMED Clinical Terms
    * Source:SNOMED-CT concept identifier codes. SNOMED International, I325 Waukegan Rd, Northfield, IL, 60093, +1 800-323-4040, mailto:snomed@cap.org  http://www.snomed.orgCategory:Specific Non-Drug CodeStatus:Active
    */
    SCT,
    /**
    * 156 - SNOMED Clinical Terms alphanumeric codes
    * Source:Used to indicate that the code value is the legacy-style SNOMED alphanumeric codes, rather than the concept identifier codes.  SNOMED International, I325 Waukegan Rd, Northfield, IL, 60093, +1 800-323-4040, mailto:snomed@cap.org  http://www.snomed.orgCategory:Status:Active
    */
    SCT2,
    /**
    * 157 - SNOMED- DICOM Microglossary
    * Source:College of American Pathologists, Skokie, IL, 60077-1034. (formerly designated as 99SDM).Category:Specific Non-Drug CodeStatus:Active
    */
    SDM,
    /**
    * 158 - Industry (SIC)
    * Source:Standard Industry Classification - 1987. Use NAICS 2002. This is mainly for mapping and backward compatibility purposes.Category:Demographic CodeStatus:New
    */
    SIC,
    /**
    * 159 - Systemized Nomenclature of Medicine (SNOMED)
    * Source:Systemized Nomenclature of Medicine, 2nd Edition 1984 Vols 1, 2, College of American Pathologists, Skokie, IL.Category:Specific Non-Drug CodeStatus:Active
    */
    SNM,
    /**
    * 160 - SNOMED International
    * Source:SNOMED International, 1993 Vols 1-4, College of American Pathologists, Skokie, IL, 60077-1034.Category:Specific Non-Drug CodeStatus:Active
    */
    SNM3,
    /**
    * 161 - SNOMED topology codes (anatomic sites)
    * Source:College of American Pathologists, 5202 Old Orchard Road, Skokie, IL 60077-1034.Category:Specific Non-Drug CodeStatus:Active
    */
    SNT,
    /**
    * 162 - Occupation (SOC 2000)
    * Source:The 2000 Standard Occupational Classification (SOC) system is used by Federal statistical agencies to classify workers into occupational categories for the purpose of collecting, calculating, or disseminating data.Category:Demographic CodeStatus:New
    */
    SOC,
    /**
    * 163 - Priority (Type) of Visit
    * Source:Source: Official UB-04 Data Specification Manual, published July 2007, by the National Uniform Billing Committee (NUBC), and can be found at http://www.nubc.org. This coding system supersedes UB92 and is effective immediately (July, 2007).Category:Status:Active
    */
    UB04FL14,
    /**
    * 164 - Point of Origin
    * Source:Source: Official UB-04 Data Specification Manual, published July 2007, by the National Uniform Billing Committee (NUBC), and can be found at http://www.nubc.org. This coding system supersedes UB92 and is effective immediately (July, 2007).Category:Status:Active
    */
    UB04FL15,
    /**
    * 165 - Patient Discharge Status
    * Source:Source: Official UB-04 Data Specification Manual, published July 2007, by the National Uniform Billing Committee (NUBC), and can be found at http://www.nubc.org. This coding system supersedes UB92 and is effective immediately (July, 2007).Category:Status:Active
    */
    UB04FL17,
    /**
    * 166 - Occurrence Code
    * Source:Source: Official UB-04 Data Specification Manual, published July 2007, by the National Uniform Billing Committee (NUBC), and can be found at http://www.nubc.org. This coding system supersedes UB92 and is effective immediately (July, 2007).Category:Status:New
    */
    UB04FL31,
    /**
    * 167 - Occurrence Span
    * Source:Source: Official UB-04 Data Specification Manual, published July 2007, by the National Uniform Billing Committee (NUBC), and can be found at http://www.nubc.org. This coding system supersedes UB92 and is effective immediately (July, 2007).Category:Status:New
    */
    UB04FL35,
    /**
    * 168 - Value Code
    * Source:Source: Official UB-04 Data Specification Manual, published July 2007, by the National Uniform Billing Committee (NUBC), and can be found at http://www.nubc.org. This coding system supersedes UB92 and is effective immediately (July, 2007).Category:Status:New
    */
    UB04FL39,
    /**
    * 169 - UCDS
    * Source:Uniform Clinical Data Systems. Ms. Michael McMullan, Office of Peer Review Health Care Finance Administration, The Meadows East Bldg., 6325 Security Blvd., Baltimore, MD 21207; (301) 966 6851.Category:Specific Non-Drug CodeStatus:Active
    */
    UC,
    /**
    * 170 - UCUM code set for units of measure(from Regenstrief)
    * Source:Added by motion of VOCABULARY T.C. 20060308 14-0-3Category:Status:Active
    */
    UCUM,
    /**
    * 171 - MDNS
    * Source:Universal Medical Device Nomenclature System. ECRI, 5200 Butler Pike, Plymouth Meeting, PA  19462 USA. Phone: 215-825-6000, Fax: 215-834-1275.Category:Device codeStatus:Active
    */
    UMD,
    /**
    * 172 - Unified Medical Language
    * Source:National Library of Medicine, 8600 Rockville Pike, Bethesda, MD 20894.Category:Specific Non-Drug CodeStatus:Active
    */
    UML,
    /**
    * 173 - Universal Product Code
    * Source:The Uniform Code Council. 8163 Old Yankee Road, Suite J, Dayton, OH  45458; (513) 435 3070Category:Specific Non-Drug CodeStatus:Active
    */
    UPC,
    /**
    * 174 - UPIN
    * Source:Medicare/CMS 's (formerly HCFA)  universal physician identification numbers, available from Health Care Financing Administration, U.S. Dept. of Health and Human Services, Bureau of Program Operations, 6325 Security Blvd., Meadows East Bldg., Room 300, Baltimore, MD 21207Category:Specific Non-Drug CodeStatus:Active
    */
    UPIN,
    /**
    * 175 - U.S. Board on Geographic Names (USGS - GNIS)
    * Source:List of populated places(City) from U.S. Geological Survey Geographic Name Information System (USGS GNIS)Category:Demographic CodeStatus:New
    */
    USGSGNIS,
    /**
    * 176 - United States Postal Service
    * Source:Two Letter State and Possession Abbreviations are listed in  Publication 28, Postal Addressing Standards which can be obtained from Address Information Products, National Address Information Center, 6060 Primacy Parkway, Suite 101, Memphis, Tennessee  38188-0001 Questions of comments regarding the publication should be addressed to the Office of Address and Customer Information Systems, Customer and Automation Service Department, US Postal Service, 475 Lenfant Plaza SW Rm 7801, Washington, DC  20260-5902Category:Specific Non-Drug CodeStatus:Active
    */
    USPS,
    /**
    * 177 - Clinicians are required to track the Vaccine Information Sheet (VIS) that was shared with the recipient of a vaccination.  This code system contains codes that  identify the document type and the owner of the document.
    * Source:More information can be found at the CDC Immunization Standards page at http://www.cdc.gov/vaccines/programs/iis/default.htm.  Content may be downloaded from PHINVADS at https://phinvads.cdc.gov/vads/ViewCodeSystem.action?id=2.16.840.1.113883.6.304 and at http://www.cdc.gov/phin/activities/vocabulary.html.Category:Specific Non-Drug CodeStatus:active
    */
    VIS,
    /**
    * 178 - WHO record # drug codes (6 digit)
    * Source:World Health organization record number code. A unique sequential number is assigned to each unique single component drug and to each multi-component drug. Eight digits are allotted to each such code, six to identify the active agent, and 2 to identify the salt, of single content drugs. Six digits are assigned to each unique combination of drugs in a dispensing unit. The six digit code is identified by W1, the 8 digit code by W2.Category:Drug codeStatus:Active
    */
    W1,
    /**
    * 179 - WHO record # drug codes (8 digit)
    * Source:World Health organization record number code. A unique sequential number is assigned to each unique single component drug and to each multi-component drug. Eight digits are allotted to each such code, six to identify the active agent, and 2 to identify the salt, of single content drugs. Six digits are assigned to each unique combination of drugs in a dispensing unit. The six digit code is identified by W1, the 8 digit code by W2.Category:Drug codeStatus:Active
    */
    W2,
    /**
    * 180 - WHO record # code with ASTM extension
    * Source:With ASTM extensions (see Implementation Guide), the WHO codes can be used to report serum (and other) levels, patient compliance with drug usage instructions, average daily doses and more (see Appendix X1 the Implementation Guide).Category:Drug codeStatus:Active
    */
    W4,
    /**
    * 181 - WHO ATC
    * Source:WHO’s ATC codes provide a hierarchical classification of drugs by therapeutic class. They are linked to the record number codes listed above.Category:Drug codeStatus:Active
    */
    WC,
    /**
    * 182 - ASC X12 Code List nnnn
    * Source:Code list associated with X12 Data Element nnnn.  Example::     X12DE738 – code set defined for X12 data element 738 (Measurement Qualifier) The Accredited Standards Committee (ASC) X12 www.x12.orgCategory:General CodesStatus:Active
    */
    X12DEnnnn
}

