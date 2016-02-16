//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:38 PM
//

package UnitTests;

import OpenDentBusiness.Employee;
import OpenDentBusiness.Employees;

public class EmployeeT   
{
    public static Employee createEmployee(String prefix) throws Exception {
        Employee emp = new Employee();
        emp.ClockStatus = "Home";
        emp.FName = prefix + "Bob";
        emp.LName = prefix + "Boberson";
        emp.MiddleI = prefix + "Bobbity";
        Employees.insert(emp);
        return emp;
    }

}


