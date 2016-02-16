//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:32 PM
//

package fyiReporting.RDL;

import fyiReporting.RDL.IStreamGen;
import fyiReporting.RDL.OutputPresentationType;

public class ProcessReport   
{
    fyiReporting.RDL.Report r;
    // report
    IStreamGen _sg;
    public ProcessReport(fyiReporting.RDL.Report rep, IStreamGen sg) throws Exception {
        if (rep.rl.getMaxSeverity() > 4)
            throw new Exception("Report has errors.  Cannot be processed.");
         
        r = rep;
        _sg = sg;
    }

    public ProcessReport(fyiReporting.RDL.Report rep) throws Exception {
        if (rep.rl.getMaxSeverity() > 4)
            throw new Exception("Report has errors.  Cannot be processed.");
         
        r = rep;
        _sg = null;
    }

    // Run the report passing the parameter values and the output
    public void run(IDictionary parms, OutputPresentationType type) throws Exception {
        r.runGetData(parms);
        r.runRender(_sg,type);
        return ;
    }

}


