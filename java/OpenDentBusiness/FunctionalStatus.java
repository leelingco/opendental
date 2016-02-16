//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:09 PM
//

package OpenDentBusiness;


public enum FunctionalStatus
{
    /**
    * Used in EHR to export patient functional and cognitive statuses on CCD documents.0 - Default value.  If not using EHR, then each diseasedef will use this value.
    */
    Problem,
    /**
    * 1 - This clinical statement contains details of an evaluation or assessment of a patient’s cognitive status. The evaluation may include assessment of a patient's mood, memory, and ability to make decisions. The statement will include, if present, supporting caregivers, non-medical devices, and the time period for which the evaluation and assessment were performed.
    */
    CognitiveResult,
    /**
    * 2 - A cognitive status problem observation is a clinical statement that describes a patient's cognitive condition, findings or symptoms. Examples of cognitive problem observations are inability to recall, amnesia, dementia, and aggressive behavior. A cognitive problem observation is a finding or medical condition. This is different from a cognitive result observation, which is a response to a question that provides insight to the patient's cognitive status. It reflects findings that provide information about a medical condition, while a result observation reflects responses to questions in a cognitive test or those that provide information about a person's judgement, comprehension ability, and response speed.
    */
    CognitiveProblem,
    /**
    * 3 - This clinical statement represents details of an evaluation or assessment of a patient’s functional status. The evaluation may include assessment of a patient's  language, vision, hearing, activities of daily living, behavior, general function, mobility and self-care status. The statement will include, if present, supporting caregivers, non-medical devices, and the time period for which the evaluation and assessment were performed.
    */
    FunctionalResult,
    /**
    * 4 - A functional status problem observation is a clinical statement that represents a patient’s functional perfomance and ability.
    */
    FunctionalProblem
}

