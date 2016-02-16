//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:01:29 PM
//

package fyiReporting.RDL;

import CS2JNet.JavaSupport.language.RefSupport;
import CS2JNet.System.StringSupport;
import fyiReporting.RDL.Constant;
import fyiReporting.RDL.ConstantBoolean;
import fyiReporting.RDL.ConstantDateTime;
import fyiReporting.RDL.ConstantDecimal;
import fyiReporting.RDL.ConstantDouble;
import fyiReporting.RDL.ConstantInteger;
import fyiReporting.RDL.ConstantString;
import fyiReporting.RDL.DataSetDefn;
import fyiReporting.RDL.Field;
import fyiReporting.RDL.FunctionAggrAvg;
import fyiReporting.RDL.FunctionAggrCount;
import fyiReporting.RDL.FunctionAggrCountDistinct;
import fyiReporting.RDL.FunctionAggrCountRows;
import fyiReporting.RDL.FunctionAggrFirst;
import fyiReporting.RDL.FunctionAggrLast;
import fyiReporting.RDL.FunctionAggrLevel;
import fyiReporting.RDL.FunctionAggrMax;
import fyiReporting.RDL.FunctionAggrMin;
import fyiReporting.RDL.FunctionAggrNext;
import fyiReporting.RDL.FunctionAggrPrevious;
import fyiReporting.RDL.FunctionAggrRvAvg;
import fyiReporting.RDL.FunctionAggrRvCount;
import fyiReporting.RDL.FunctionAggrRvMax;
import fyiReporting.RDL.FunctionAggrRvMin;
import fyiReporting.RDL.FunctionAggrRvStdev;
import fyiReporting.RDL.FunctionAggrRvStdevp;
import fyiReporting.RDL.FunctionAggrRvSum;
import fyiReporting.RDL.FunctionAggrRvVar;
import fyiReporting.RDL.FunctionAggrRvVarp;
import fyiReporting.RDL.FunctionAggrStdev;
import fyiReporting.RDL.FunctionAggrStdevp;
import fyiReporting.RDL.FunctionAggrSum;
import fyiReporting.RDL.FunctionAggrVar;
import fyiReporting.RDL.FunctionAggrVarp;
import fyiReporting.RDL.FunctionAnd;
import fyiReporting.RDL.FunctionChoose;
import fyiReporting.RDL.FunctionCode;
import fyiReporting.RDL.FunctionCustomInstance;
import fyiReporting.RDL.FunctionCustomStatic;
import fyiReporting.RDL.FunctionDiv;
import fyiReporting.RDL.FunctionDivDecimal;
import fyiReporting.RDL.FunctionExp;
import fyiReporting.RDL.FunctionField;
import fyiReporting.RDL.FunctionFieldCollection;
import fyiReporting.RDL.FunctionFieldIsMissing;
import fyiReporting.RDL.FunctionFormat;
import fyiReporting.RDL.FunctionGlobalCollection;
import fyiReporting.RDL.FunctionIif;
import fyiReporting.RDL.FunctionMinus;
import fyiReporting.RDL.FunctionMinusDecimal;
import fyiReporting.RDL.FunctionModulus;
import fyiReporting.RDL.FunctionMult;
import fyiReporting.RDL.FunctionMultDecimal;
import fyiReporting.RDL.FunctionNot;
import fyiReporting.RDL.FunctionOr;
import fyiReporting.RDL.FunctionParameterCollection;
import fyiReporting.RDL.FunctionPlus;
import fyiReporting.RDL.FunctionPlusDecimal;
import fyiReporting.RDL.FunctionPlusString;
import fyiReporting.RDL.FunctionRelopEQ;
import fyiReporting.RDL.FunctionRelopGT;
import fyiReporting.RDL.FunctionRelopGTE;
import fyiReporting.RDL.FunctionRelopLT;
import fyiReporting.RDL.FunctionRelopLTE;
import fyiReporting.RDL.FunctionRelopNE;
import fyiReporting.RDL.FunctionReportItemCollection;
import fyiReporting.RDL.FunctionReportParameter;
import fyiReporting.RDL.FunctionReportParameterLabel;
import fyiReporting.RDL.FunctionSwitch;
import fyiReporting.RDL.FunctionSystem;
import fyiReporting.RDL.FunctionTextbox;
import fyiReporting.RDL.FunctionUnaryMinus;
import fyiReporting.RDL.FunctionUnaryMinusDecimal;
import fyiReporting.RDL.FunctionUnaryMinusInteger;
import fyiReporting.RDL.FunctionUserCollection;
import fyiReporting.RDL.ICacheData;
import fyiReporting.RDL.Identifier;
import fyiReporting.RDL.IdentifierKey;
import fyiReporting.RDL.IdentifierKeyEnum;
import fyiReporting.RDL.IExpr;
import fyiReporting.RDL.Lexer;
import fyiReporting.RDL.NameLookup;
import fyiReporting.RDL.ParserException;
import fyiReporting.RDL.ReportClass;
import fyiReporting.RDL.ReportParameter;
import fyiReporting.RDL.Textbox;
import fyiReporting.RDL.Token;
import fyiReporting.RDL.TokenList;
import fyiReporting.RDL.TokenTypes;
import fyiReporting.RDL.XmlUtil;

/* ====================================================================
    Copyright (C) 2004-2006  fyiReporting Software, LLC
    This file is part of the fyiReporting RDL project.
	
    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 2.1 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
    For additional information, email info@fyireporting.com or visit
    the website www.fyiReporting.com.
*/
/**
* Language parser.   Recursive descent parser.  Precedence of operators
* is handled by starting with lowest precedence and calling down recursively
* to the highest.
* AND/OR
* NOT
* relational operators, eq, ne, lt, lte, gt, gte
* +, -
* *, /, %
* ^ (exponentiation)
* unary +, -
* parenthesis (...)
* 
* In BNF the language grammar is:
*  {@code 
* Expr: Term ExprRhs
* ExprRhs: PlusMinusOperator Term ExprRhs
* Term: Factor TermRhs
* TermRhs: MultDivOperator Factor TermRhs
* Factor: ( Expr ) | BaseType | - BaseType | - ( Expr )
* BaseType: FuncIdent | NUMBER | QUOTE
* FuncIDent: IDENTIFIER ( [Expr] [, Expr]*) | IDENTIFIER
* PlusMinusOperator: + | -
* MultDivOperator: * | /
* }
*/
public class Parser   
{
    static public long Counter = new long();
    // counter used for unique expression count
    private TokenList tokens;
    private Stack operandStack = new Stack();
    private Stack operatorStack = new Stack();
    private Token curToken = null;
    private NameLookup idLookup = null;
    private List<ICacheData> _DataCache = new List<ICacheData>();
    private boolean _InAggregate = new boolean();
    private List<FunctionField> _FieldResolve = new List<FunctionField>();
    private boolean _NoAggregate = false;
    /**
    * Parse an expression.
    */
    public Parser(List<ICacheData> c) throws Exception {
        _DataCache = c;
    }

    /**
    * Returns a parsed Expression
    * 
    *  @param lu The NameLookUp class used to resolve names.
    *  @param expr The expression to be parsed.
    *  @return An expression that can be run after validation and binding.
    */
    public IExpr parse(NameLookup lu, String expr) throws Exception {
        _InAggregate = false;
        if (!StringSupport.equals(expr.Substring(0, 1), "="))
            return new Constant(expr);
         
        // if 1st char not '='
        //   this is a constant value
        idLookup = lu;
        IExpr e = this.ParseExpr(new StringReader(expr));
        if (e == null)
            // Didn't get an expression?
            e = new Constant(expr);
         
        return e;
    }

    //  then provide a constant
    // marked true when in an aggregate function
    public boolean getNoAggregateFunctions() throws Exception {
        return _NoAggregate;
    }

    public void setNoAggregateFunctions(boolean value) throws Exception {
        _NoAggregate = value;
    }

    /**
    * Returns a parsed DPL instance.
    * 
    *  @param reader The TextReader value to be parsed.
    *  @return A parsed Program instance.
    */
    private IExpr parseExpr(TextReader reader) throws Exception {
        IExpr result = null;
        Lexer lexer = new Lexer(reader);
        tokens = lexer.lex();
        if (tokens.peek().Type == TokenTypes.EQUAL)
        {
            tokens.extract();
            // skip over the equal
            curToken = tokens.extract();
            // set up the first token
            RefSupport<IExpr> refVar___0 = new RefSupport<IExpr>();
            matchExprAndOr(refVar___0);
            result = refVar___0.getValue();
        }
         
        // start with lowest precedence and work up
        if (curToken.Type != TokenTypes.EOF)
            throw new ParserException("End of expression expected." + "  At column " + Convert.ToString(curToken.StartCol));
         
        return result;
    }

    // ExprAndOr:
    private void matchExprAndOr(RefSupport<IExpr> result) throws Exception {
        TokenTypes t = TokenTypes.AND;
        // remember the type
        IExpr lhs;
        RefSupport<IExpr> refVar___1 = new RefSupport<IExpr>();
        matchExprNot(refVar___1);
        lhs = refVar___1.getValue();
        result.setValue(lhs);
        while ((t = curToken.Type) == TokenTypes.AND || t == TokenTypes.OR)
        {
            // in case we get no matches
            curToken = tokens.extract();
            IExpr rhs;
            RefSupport<IExpr> refVar___2 = new RefSupport<IExpr>();
            matchExprNot(refVar___2);
            rhs = refVar___2.getValue();
            boolean bBool = (rhs.getTypeCode() == TypeCode.Boolean && lhs.getTypeCode() == TypeCode.Boolean);
            if (!bBool)
                throw new ParserException("AND/OR operations require both sides to be boolean expressions." + "  At column " + Convert.ToString(curToken.StartCol));
             
            switch(t)
            {
                case AND: 
                    result.setValue(new FunctionAnd(lhs,rhs));
                    break;
                case OR: 
                    result.setValue(new FunctionOr(lhs,rhs));
                    break;
            
            }
            lhs = result.getValue();
        }
    }

    // in case we have more AND/OR s
    private void matchExprNot(RefSupport<IExpr> result) throws Exception {
        TokenTypes t = TokenTypes.AND;
        // remember the type
        t = curToken.Type;
        if (t == TokenTypes.NOT)
        {
            curToken = tokens.extract();
        }
         
        RefSupport<IExpr> refVar___3 = new RefSupport<IExpr>();
        matchExprRelop(refVar___3);
        result.setValue(refVar___3.getValue());
        if (t == TokenTypes.NOT)
        {
            if (result.getValue().getTypeCode() != TypeCode.Boolean)
                throw new ParserException("NOT requires boolean expression." + "  At column " + Convert.ToString(curToken.StartCol));
             
            result.setValue(new FunctionNot(result.getValue()));
        }
         
    }

    // ExprRelop:
    private void matchExprRelop(RefSupport<IExpr> result) throws Exception {
        TokenTypes t = TokenTypes.AND;
        // remember the type
        IExpr lhs;
        RefSupport<IExpr> refVar___4 = new RefSupport<IExpr>();
        matchExprAddSub(refVar___4);
        lhs = refVar___4.getValue();
        result.setValue(lhs);
        while ((t = curToken.Type) == TokenTypes.EQUAL || t == TokenTypes.NOTEQUAL || t == TokenTypes.GREATERTHAN || t == TokenTypes.GREATERTHANOREQUAL || t == TokenTypes.LESSTHAN || t == TokenTypes.LESSTHANOREQUAL)
        {
            // in case we get no matches
            curToken = tokens.extract();
            IExpr rhs;
            RefSupport<IExpr> refVar___5 = new RefSupport<IExpr>();
            matchExprAddSub(refVar___5);
            rhs = refVar___5.getValue();
            switch(t)
            {
                case EQUAL: 
                    result.setValue(new FunctionRelopEQ(lhs,rhs));
                    break;
                case NOTEQUAL: 
                    result.setValue(new FunctionRelopNE(lhs,rhs));
                    break;
                case GREATERTHAN: 
                    result.setValue(new FunctionRelopGT(lhs,rhs));
                    break;
                case GREATERTHANOREQUAL: 
                    result.setValue(new FunctionRelopGTE(lhs,rhs));
                    break;
                case LESSTHAN: 
                    result.setValue(new FunctionRelopLT(lhs,rhs));
                    break;
                case LESSTHANOREQUAL: 
                    result.setValue(new FunctionRelopLTE(lhs,rhs));
                    break;
            
            }
            lhs = result.getValue();
        }
    }

    // in case we continue the loop
    // ExprAddSub: PlusMinusOperator Term ExprRhs
    private void matchExprAddSub(RefSupport<IExpr> result) throws Exception {
        TokenTypes t = TokenTypes.AND;
        // remember the type
        IExpr lhs;
        RefSupport<IExpr> refVar___6 = new RefSupport<IExpr>();
        matchExprMultDiv(refVar___6);
        lhs = refVar___6.getValue();
        result.setValue(lhs);
        while ((t = curToken.Type) == TokenTypes.PLUS || t == TokenTypes.PLUSSTRING || t == TokenTypes.MINUS)
        {
            // in case we get no matches
            curToken = tokens.extract();
            IExpr rhs;
            RefSupport<IExpr> refVar___7 = new RefSupport<IExpr>();
            matchExprMultDiv(refVar___7);
            rhs = refVar___7.getValue();
            TypeCode lt = lhs.getTypeCode();
            TypeCode rt = rhs.getTypeCode();
            boolean bDecimal = (rt == TypeCode.Decimal && lt == TypeCode.Decimal);
            boolean bString = (rt == TypeCode.String || lt == TypeCode.String);
            switch(t)
            {
                case PLUSSTRING: 
                    result.setValue(new FunctionPlusString(lhs,rhs));
                    break;
                case PLUS: 
                    if (bDecimal)
                        result.setValue(new FunctionPlusDecimal(lhs,rhs));
                    else if (bString)
                        result.setValue(new FunctionPlusString(lhs,rhs));
                    else
                        result.setValue(new FunctionPlus(lhs,rhs));  
                    break;
                case MINUS: 
                    if (bDecimal)
                        result.setValue(new FunctionMinusDecimal(lhs,rhs));
                    else if (bString)
                        throw new ParserException("'-' operator works only on numbers." + "  At column " + Convert.ToString(curToken.StartCol));
                    else
                        result.setValue(new FunctionMinus(lhs,rhs));  
                    break;
            
            }
            lhs = result.getValue();
        }
    }

    // in case continue in the loop
    // TermRhs: MultDivOperator Factor TermRhs
    private void matchExprMultDiv(RefSupport<IExpr> result) throws Exception {
        TokenTypes t = TokenTypes.AND;
        // remember the type
        IExpr lhs;
        RefSupport<IExpr> refVar___8 = new RefSupport<IExpr>();
        matchExprExp(refVar___8);
        lhs = refVar___8.getValue();
        result.setValue(lhs);
        while ((t = curToken.Type) == TokenTypes.FORWARDSLASH || t == TokenTypes.STAR || t == TokenTypes.MODULUS)
        {
            // in case we get no matches
            curToken = tokens.extract();
            IExpr rhs;
            RefSupport<IExpr> refVar___9 = new RefSupport<IExpr>();
            matchExprExp(refVar___9);
            rhs = refVar___9.getValue();
            boolean bDecimal = (rhs.getTypeCode() == TypeCode.Decimal && lhs.getTypeCode() == TypeCode.Decimal);
            switch(t)
            {
                case FORWARDSLASH: 
                    if (bDecimal)
                        result.setValue(new FunctionDivDecimal(lhs,rhs));
                    else
                        result.setValue(new FunctionDiv(lhs,rhs)); 
                    break;
                case STAR: 
                    if (bDecimal)
                        result.setValue(new FunctionMultDecimal(lhs,rhs));
                    else
                        result.setValue(new FunctionMult(lhs,rhs)); 
                    break;
                case MODULUS: 
                    result.setValue(new FunctionModulus(lhs,rhs));
                    break;
            
            }
            lhs = result.getValue();
        }
    }

    // in case continue in the loop
    // TermRhs: ExpOperator Factor TermRhs
    private void matchExprExp(RefSupport<IExpr> result) throws Exception {
        IExpr lhs;
        RefSupport<IExpr> refVar___10 = new RefSupport<IExpr>();
        matchExprUnary(refVar___10);
        lhs = refVar___10.getValue();
        if (curToken.Type == TokenTypes.EXP)
        {
            curToken = tokens.extract();
            IExpr rhs;
            RefSupport<IExpr> refVar___11 = new RefSupport<IExpr>();
            matchExprUnary(refVar___11);
            rhs = refVar___11.getValue();
            result.setValue(new FunctionExp(lhs,rhs));
        }
        else
            result.setValue(lhs); 
    }

    private void matchExprUnary(RefSupport<IExpr> result) throws Exception {
        TokenTypes t = TokenTypes.AND;
        // remember the type
        t = curToken.Type;
        if (t == TokenTypes.PLUS || t == TokenTypes.MINUS)
        {
            curToken = tokens.extract();
        }
         
        RefSupport<IExpr> refVar___12 = new RefSupport<IExpr>();
        matchExprParen(refVar___12);
        result.setValue(refVar___12.getValue());
        if (t == TokenTypes.MINUS)
        {
            if (result.getValue().getTypeCode() == TypeCode.Decimal)
                result.setValue(new FunctionUnaryMinusDecimal(result.getValue()));
            else if (result.getValue().getTypeCode() == TypeCode.Int32)
                result.setValue(new FunctionUnaryMinusInteger(result.getValue()));
            else
                result.setValue(new FunctionUnaryMinus(result.getValue()));  
        }
         
    }

    // Factor: ( Expr ) | BaseType | - BaseType | - ( Expr )
    private void matchExprParen(RefSupport<IExpr> result) throws Exception {
        // Match- ( Expr )
        if (curToken.Type == TokenTypes.LPAREN)
        {
            // trying to match ( Expr )
            curToken = tokens.extract();
            RefSupport<IExpr> refVar___13 = new RefSupport<IExpr>();
            matchExprAndOr(refVar___13);
            result.setValue(refVar___13.getValue());
            if (curToken.Type != TokenTypes.RPAREN)
                throw new ParserException("')' expected but not found.  Found '" + curToken.Value + "'  At column " + Convert.ToString(curToken.StartCol));
             
            curToken = tokens.extract();
        }
        else
        {
            RefSupport<IExpr> refVar___14 = new RefSupport<IExpr>();
            matchBaseType(refVar___14);
            result.setValue(refVar___14.getValue());
        } 
    }

    // BaseType: FuncIdent | NUMBER | QUOTE   - note certain types are restricted in expressions
    private void matchBaseType(RefSupport<IExpr> result) throws Exception {
        boolean boolVar___0 = matchFuncIDent(refVar___15);
        if (boolVar___0)
        {
            RefSupport<IExpr> refVar___15 = new RefSupport<IExpr>();
            resVar___1 = ();
            result.setValue(refVar___15.getValue());
            return resVar___1;
        }
         
        switch(curToken.Type)
        {
            case NUMBER: 
                result.setValue(new ConstantDecimal(curToken.Value));
                break;
            case DATETIME: 
                result.setValue(new ConstantDateTime(curToken.Value));
                break;
            case DOUBLE: 
                result.setValue(new ConstantDouble(curToken.Value));
                break;
            case INTEGER: 
                result.setValue(new ConstantInteger(curToken.Value));
                break;
            case QUOTE: 
                result.setValue(new ConstantString(curToken.Value));
                break;
            default: 
                throw new ParserException("Constant or Identifier expected but not found.  Found '" + curToken.Value + "'  At column " + Convert.ToString(curToken.StartCol));
        
        }
        curToken = tokens.extract();
        return ;
    }

    // FuncIDent: IDENTIFIER ( [Expr] [, Expr]*) | IDENTIFIER
    private boolean matchFuncIDent(RefSupport<IExpr> result) throws Exception {
        IExpr e;
        String fullname = new String();
        // will hold the full name
        String method = new String();
        // will hold method name or second part of name
        String firstPart = new String();
        // will hold the collection name
        String thirdPart = new String();
        // will hold third part of name
        boolean bOnePart = new boolean();
        // simple name: no ! or . in name
        result.setValue(null);
        if (curToken.Type != TokenTypes.IDENTIFIER)
            return false;
         
        // Disentangle method calls from collection references
        method = fullname = curToken.Value;
        curToken = tokens.extract();
        // Break the name into parts
        char[] breakChars = new char[]{ '!', '.' };
        int posBreak = method.IndexOfAny(breakChars);
        if (posBreak > 0)
        {
            bOnePart = false;
            firstPart = method.Substring(0, posBreak);
            method = method.Substring(posBreak + 1);
        }
        else
        {
            // rest of expression
            bOnePart = true;
            firstPart = method;
        } 
        posBreak = method.IndexOf('.');
        if (posBreak > 0)
        {
            thirdPart = method.Substring(posBreak + 1);
            // rest of expression
            method = method.Substring(0, posBreak);
        }
        else
            thirdPart = null; 
        if (curToken.Type != TokenTypes.LPAREN)
        {
            System.String __dummyScrutVar5 = firstPart;
            if (__dummyScrutVar5.equals("Fields"))
            {
                Field f = idLookup.lookupField(method);
                if (f == null && !this._InAggregate)
                    throw new ParserException("Field '" + method + "'  not found.");
                 
                if (thirdPart == null || StringSupport.equals(thirdPart, "Value"))
                {
                    if (f == null)
                    {
                        FunctionField ff;
                        result.setValue(ff = new FunctionField(method));
                        this._FieldResolve.Add(ff);
                    }
                    else
                        result.setValue(new FunctionField(f)); 
                }
                else if (StringSupport.equals(thirdPart, "IsMissing"))
                {
                    if (f == null)
                    {
                        FunctionField ff;
                        result.setValue(ff = new FunctionFieldIsMissing(method));
                        this._FieldResolve.Add(ff);
                    }
                    else
                        result.setValue(new FunctionFieldIsMissing(f)); 
                }
                else
                    throw new ParserException("Field '" + method + "'  only supports 'Value' and 'IsMissing' properties.");  
                return true;
            }
            else if (__dummyScrutVar5.equals("Parameters"))
            {
                // see ResolveParametersMethod for resolution of MultiValue parameter function reference
                ReportParameter p = idLookup.lookupParameter(method);
                if (p == null)
                    throw new ParserException("Report parameter '" + method + "'  not found.");
                 
                int ci = thirdPart == null ? -1 : thirdPart.IndexOf(".Count");
                if (ci > 0)
                    thirdPart = thirdPart.Substring(0, ci);
                 
                FunctionReportParameter r;
                if (thirdPart == null || StringSupport.equals(thirdPart, "Value"))
                    r = new FunctionReportParameter(p);
                else if (StringSupport.equals(thirdPart, "Label"))
                    r = new FunctionReportParameterLabel(p);
                else
                    throw new ParserException("Parameter '" + method + "'  only supports 'Value' and 'Label' properties.");  
                if (ci > 0)
                    r.setParameterMethod("Count",null);
                 
                result.setValue(r);
                return true;
            }
            else if (__dummyScrutVar5.equals("ReportItems"))
            {
                Textbox t = idLookup.lookupReportItem(method);
                if (t == null)
                    throw new ParserException("ReportItem '" + method + "'  not found.");
                 
                if (thirdPart != null && !StringSupport.equals(thirdPart, "Value"))
                    throw new ParserException("ReportItem '" + method + "'  only supports 'Value' property.");
                 
                result.setValue(new FunctionTextbox(t,idLookup.getExpressionName()));
                return true;
            }
            else if (__dummyScrutVar5.equals("Globals"))
            {
                e = idLookup.lookupGlobal(method);
                if (e == null)
                    throw new ParserException("Globals '" + method + "'  not found.");
                 
                result.setValue(e);
                return true;
            }
            else if (__dummyScrutVar5.equals("User"))
            {
                e = idLookup.lookupUser(method);
                if (e == null)
                    throw new ParserException("User variable '" + method + "'  not found.");
                 
                result.setValue(e);
                return true;
            }
            else if (__dummyScrutVar5.equals("Recursive"))
            {
                // Only valid for some aggregate functions
                result.setValue(new IdentifierKey(IdentifierKeyEnum.Recursive));
                return true;
            }
            else if (__dummyScrutVar5.equals("Simple"))
            {
                // Only valid for some aggregate functions
                result.setValue(new IdentifierKey(IdentifierKeyEnum.Simple));
                return true;
            }
            else
            {
                if (!bOnePart)
                    throw new ParserException(String.Format("'{0}' is an unknown identifer.", fullname));
                 
                System.String.APPLY __dummyScrutVar6 = method.ToLower();
                // lexer should probably mark these
                if (__dummyScrutVar6.equals("true") || __dummyScrutVar6.equals("false"))
                {
                    result.setValue(new ConstantBoolean(method.ToLower()));
                }
                else
                {
                    // usually this is enum that will be used in an aggregate
                    result.setValue(new Identifier(method));
                } 
                return true;
            }       
        }
         
        // We've got an function reference
        curToken = tokens.extract();
        // get rid of '('
        // Got a function now obtain the arguments
        int argCount = 0;
        boolean isAggregate = isAggregate(method,bOnePart);
        if (_NoAggregate && isAggregate)
            throw new ParserException("Aggregate function '" + method + "' cannot be used within a Grouping expression.");
         
        if (_InAggregate && isAggregate)
            throw new ParserException("Aggregate function '" + method + "' cannot be nested in another aggregate function.");
         
        _InAggregate = isAggregate;
        if (_InAggregate)
            _FieldResolve = new List<FunctionField>();
         
        List<IExpr> largs = new List<IExpr>();
        while (true)
        {
            if (curToken.Type == TokenTypes.RPAREN)
            {
                // We've got our function
                curToken = tokens.extract();
                break;
            }
             
            if (argCount == 0)
            {
            }
            else // don't need to do anything
            if (curToken.Type == TokenTypes.COMMA)
            {
                curToken = tokens.extract();
            }
            else
                throw new ParserException("Invalid function arguments.  Found '" + curToken.Value + "'  At column " + Convert.ToString(curToken.StartCol));  
            RefSupport<IExpr> refVar___16 = new RefSupport<IExpr>();
            matchExprAndOr(refVar___16);
            e = refVar___16.getValue();
            if (e == null)
                throw new ParserException("Expecting ',' or ')'.  Found '" + curToken.Value + "'  At column " + Convert.ToString(curToken.StartCol));
             
            largs.Add(e);
            argCount++;
        }
        if (_InAggregate)
        {
            ResolveFields(method, this._FieldResolve, largs);
            _FieldResolve = null;
            _InAggregate = false;
        }
         
        IExpr[] args = largs.ToArray();
        Object scope = new Object();
        boolean bSimple = new boolean();
        if (!bOnePart)
        {
            result.setValue((StringSupport.equals(firstPart, "Parameters")) ? ResolveParametersMethod(method, thirdPart, args) : ResolveMethodCall(fullname, args));
        }
        else
        {
            // throw exception when fails
            System.String.APPLY __dummyScrutVar7 = method.ToLower();
            if (__dummyScrutVar7.equals("iif"))
            {
                if (args.Length != 3)
                    throw new ParserException("iff function requires 3 arguments." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                //  We allow any type for the first argument; it will get converted to boolean at runtime
                //					if (args[0].GetTypeCode() != TypeCode.Boolean)
                //						throw new ParserException("First argument to iif function must be boolean." + "  At column " + Convert.ToString(curToken.StartCol));
                result.setValue(new FunctionIif(args[0], args[1], args[2]));
            }
            else if (__dummyScrutVar7.equals("choose"))
            {
                if (args.Length <= 2)
                    throw new ParserException("Choose function requires at least 2 arguments." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                System.Array<fyiReporting.RDL.IExpr>.INDEXER.APPLY __dummyScrutVar8 = args[0].GetTypeCode();
                if (__dummyScrutVar8.equals(TypeCode.Double) || __dummyScrutVar8.equals(TypeCode.Single) || __dummyScrutVar8.equals(TypeCode.Int32) || __dummyScrutVar8.equals(TypeCode.Decimal) || __dummyScrutVar8.equals(TypeCode.Int16) || __dummyScrutVar8.equals(TypeCode.Int64))
                {
                }
                else
                {
                    throw new ParserException("First argument to Choose function must be numeric." + "  At column " + Convert.ToString(curToken.StartCol));
                } 
                result.setValue(new FunctionChoose(args));
            }
            else if (__dummyScrutVar7.equals("switch"))
            {
                if (args.Length <= 2)
                    throw new ParserException("Switch function requires at least 2 arguments." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                if (args.Length % 2 != 0)
                    throw new ParserException("Switch function must have an even number of arguments." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                for (int i = 0;i < args.Length;i = i + 2)
                {
                    if (args[i].GetTypeCode() != TypeCode.Boolean)
                        throw new ParserException("Switch function must have a boolean expression every other argument." + "  At column " + Convert.ToString(curToken.StartCol));
                     
                }
                result.setValue(new FunctionSwitch(args));
            }
            else if (__dummyScrutVar7.equals("format"))
            {
                if (args.Length > 2 || args.Length < 1)
                    throw new ParserException("Format function requires 2 arguments." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                if (args.Length == 1)
                {
                    result.setValue(new FunctionFormat(args[0], new ConstantString("")));
                }
                else
                {
                    if (args[1].GetTypeCode() != TypeCode.String)
                        throw new ParserException("Second argument to Format function must be a string." + "  At column " + Convert.ToString(curToken.StartCol));
                     
                    result.setValue(new FunctionFormat(args[0], args[1]));
                } 
            }
            else if (__dummyScrutVar7.equals("fields"))
            {
                if (args.Length != 1)
                    throw new ParserException("Fields collection requires exactly 1 argument." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                result.setValue(new FunctionFieldCollection(idLookup.getFields(), args[0]));
            }
            else if (__dummyScrutVar7.equals("parameters"))
            {
                if (args.Length != 1)
                    throw new ParserException("Parameters collection requires exactly 1 argument." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                result.setValue(new FunctionParameterCollection(idLookup.getParameters(), args[0]));
            }
            else if (__dummyScrutVar7.equals("reportitems"))
            {
                if (args.Length != 1)
                    throw new ParserException("ReportItems collection requires exactly 1 argument." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                result.setValue(new FunctionReportItemCollection(idLookup.getReportItems(), args[0]));
            }
            else if (__dummyScrutVar7.equals("globals"))
            {
                if (args.Length != 1)
                    throw new ParserException("Globals collection requires exactly 1 argument." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                result.setValue(new FunctionGlobalCollection(idLookup.getGlobals(), args[0]));
            }
            else if (__dummyScrutVar7.equals("user"))
            {
                if (args.Length != 1)
                    throw new ParserException("User collection requires exactly 1 argument." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                result.setValue(new FunctionUserCollection(idLookup.getUser(), args[0]));
            }
            else if (__dummyScrutVar7.equals("sum"))
            {
                RefSupport<boolean> refVar___17 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___17);
                bSimple = refVar___17.getValue();
                FunctionAggrSum aggrFS = new FunctionAggrSum(_DataCache, args[0], scope);
                aggrFS.setLevelCheck(bSimple);
                result.setValue(aggrFS);
            }
            else if (__dummyScrutVar7.equals("avg"))
            {
                RefSupport<boolean> refVar___18 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___18);
                bSimple = refVar___18.getValue();
                FunctionAggrAvg aggrFA = new FunctionAggrAvg(_DataCache, args[0], scope);
                aggrFA.setLevelCheck(bSimple);
                result.setValue(aggrFA);
            }
            else if (__dummyScrutVar7.equals("min"))
            {
                RefSupport<boolean> refVar___19 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___19);
                bSimple = refVar___19.getValue();
                FunctionAggrMin aggrFMin = new FunctionAggrMin(_DataCache, args[0], scope);
                aggrFMin.setLevelCheck(bSimple);
                result.setValue(aggrFMin);
            }
            else if (__dummyScrutVar7.equals("max"))
            {
                RefSupport<boolean> refVar___20 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___20);
                bSimple = refVar___20.getValue();
                FunctionAggrMax aggrFMax = new FunctionAggrMax(_DataCache, args[0], scope);
                aggrFMax.setLevelCheck(bSimple);
                result.setValue(aggrFMax);
            }
            else if (__dummyScrutVar7.equals("first"))
            {
                RefSupport<boolean> refVar___21 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___21);
                bSimple = refVar___21.getValue();
                result.setValue(new FunctionAggrFirst(_DataCache, args[0], scope));
            }
            else if (__dummyScrutVar7.equals("last"))
            {
                RefSupport<boolean> refVar___22 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___22);
                bSimple = refVar___22.getValue();
                result.setValue(new FunctionAggrLast(_DataCache, args[0], scope));
            }
            else if (__dummyScrutVar7.equals("next"))
            {
                RefSupport<boolean> refVar___23 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___23);
                bSimple = refVar___23.getValue();
                result.setValue(new FunctionAggrNext(_DataCache, args[0], scope));
            }
            else if (__dummyScrutVar7.equals("previous"))
            {
                RefSupport<boolean> refVar___24 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___24);
                bSimple = refVar___24.getValue();
                result.setValue(new FunctionAggrPrevious(_DataCache, args[0], scope));
            }
            else if (__dummyScrutVar7.equals("level"))
            {
                RefSupport<boolean> refVar___25 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 1, refVar___25);
                bSimple = refVar___25.getValue();
                result.setValue(new FunctionAggrLevel(scope));
            }
            else if (__dummyScrutVar7.equals("count"))
            {
                RefSupport<boolean> refVar___26 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___26);
                bSimple = refVar___26.getValue();
                FunctionAggrCount aggrFC = new FunctionAggrCount(_DataCache, args[0], scope);
                aggrFC.setLevelCheck(bSimple);
                result.setValue(aggrFC);
            }
            else if (__dummyScrutVar7.equals("countrows"))
            {
                RefSupport<boolean> refVar___27 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 1, refVar___27);
                bSimple = refVar___27.getValue();
                FunctionAggrCountRows aggrFCR = new FunctionAggrCountRows(scope);
                aggrFCR.setLevelCheck(bSimple);
                result.setValue(aggrFCR);
            }
            else if (__dummyScrutVar7.equals("countdistinct"))
            {
                RefSupport<boolean> refVar___28 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___28);
                bSimple = refVar___28.getValue();
                FunctionAggrCountDistinct aggrFCD = new FunctionAggrCountDistinct(_DataCache, args[0], scope);
                aggrFCD.setLevelCheck(bSimple);
                result.setValue(aggrFCD);
            }
            else if (__dummyScrutVar7.equals("rownumber"))
            {
                RefSupport<boolean> refVar___29 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 1, refVar___29);
                bSimple = refVar___29.getValue();
                IExpr texpr = new ConstantDouble("0");
                result.setValue(new FunctionAggrRvCount(_DataCache,texpr,scope));
            }
            else if (__dummyScrutVar7.equals("runningvalue"))
            {
                if (args.Length < 2 || args.Length > 3)
                    throw new ParserException("RunningValue takes 2 or 3 arguments." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                String aggrFunc = args[1].EvaluateString(null, null);
                if (aggrFunc == null)
                    throw new ParserException("RunningValue 'Function' argument is invalid." + "  At column " + Convert.ToString(curToken.StartCol));
                 
                RefSupport<boolean> refVar___30 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 3, refVar___30);
                bSimple = refVar___30.getValue();
                System.String.APPLY __dummyScrutVar9 = aggrFunc.ToLower();
                if (__dummyScrutVar9.equals("sum"))
                {
                    result.setValue(new FunctionAggrRvSum(_DataCache, args[0], scope));
                }
                else if (__dummyScrutVar9.equals("avg"))
                {
                    result.setValue(new FunctionAggrRvAvg(_DataCache, args[0], scope));
                }
                else if (__dummyScrutVar9.equals("count"))
                {
                    result.setValue(new FunctionAggrRvCount(_DataCache, args[0], scope));
                }
                else if (__dummyScrutVar9.equals("max"))
                {
                    result.setValue(new FunctionAggrRvMax(_DataCache, args[0], scope));
                }
                else if (__dummyScrutVar9.equals("min"))
                {
                    result.setValue(new FunctionAggrRvMin(_DataCache, args[0], scope));
                }
                else if (__dummyScrutVar9.equals("stdev"))
                {
                    result.setValue(new FunctionAggrRvStdev(_DataCache, args[0], scope));
                }
                else if (__dummyScrutVar9.equals("stdevp"))
                {
                    result.setValue(new FunctionAggrRvStdevp(_DataCache, args[0], scope));
                }
                else if (__dummyScrutVar9.equals("var"))
                {
                    result.setValue(new FunctionAggrRvVar(_DataCache, args[0], scope));
                }
                else if (__dummyScrutVar9.equals("varp"))
                {
                    result.setValue(new FunctionAggrRvVarp(_DataCache, args[0], scope));
                }
                else
                {
                    throw new ParserException("RunningValue function '" + aggrFunc + "' is not supported.  At column " + Convert.ToString(curToken.StartCol));
                }         
            }
            else if (__dummyScrutVar7.equals("stdev"))
            {
                RefSupport<boolean> refVar___31 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___31);
                bSimple = refVar___31.getValue();
                FunctionAggrStdev aggrSDev = new FunctionAggrStdev(_DataCache, args[0], scope);
                aggrSDev.setLevelCheck(bSimple);
                result.setValue(aggrSDev);
            }
            else if (__dummyScrutVar7.equals("stdevp"))
            {
                RefSupport<boolean> refVar___32 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___32);
                bSimple = refVar___32.getValue();
                FunctionAggrStdevp aggrSDevP = new FunctionAggrStdevp(_DataCache, args[0], scope);
                aggrSDevP.setLevelCheck(bSimple);
                result.setValue(aggrSDevP);
            }
            else if (__dummyScrutVar7.equals("var"))
            {
                RefSupport<boolean> refVar___33 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___33);
                bSimple = refVar___33.getValue();
                FunctionAggrVar aggrVar = new FunctionAggrVar(_DataCache, args[0], scope);
                aggrVar.setLevelCheck(bSimple);
                result.setValue(aggrVar);
            }
            else if (__dummyScrutVar7.equals("varp"))
            {
                RefSupport<boolean> refVar___34 = new RefSupport<boolean>();
                scope = ResolveAggrScope(args, 2, refVar___34);
                bSimple = refVar___34.getValue();
                FunctionAggrVarp aggrVarP = new FunctionAggrVarp(_DataCache, args[0], scope);
                aggrVarP.setLevelCheck(bSimple);
                result.setValue(aggrVarP);
            }
            else
            {
                result.setValue(ResolveMethodCall(fullname, args));
            }                           
        } 
        return true;
    }

    // through exception when fails
    private boolean isAggregate(String method, boolean onePart) throws Exception {
        if (!onePart)
            return false;
         
        boolean rc = new boolean();
        System.String.APPLY __dummyScrutVar10 = method.ToLower();
        // this needs to include all aggregate functions
        if (__dummyScrutVar10.equals("sum") || __dummyScrutVar10.equals("avg") || __dummyScrutVar10.equals("min") || __dummyScrutVar10.equals("max") || __dummyScrutVar10.equals("first") || __dummyScrutVar10.equals("last") || __dummyScrutVar10.equals("next") || __dummyScrutVar10.equals("previous") || __dummyScrutVar10.equals("count") || __dummyScrutVar10.equals("countrows") || __dummyScrutVar10.equals("countdistinct") || __dummyScrutVar10.equals("stdev") || __dummyScrutVar10.equals("stdevp") || __dummyScrutVar10.equals("var") || __dummyScrutVar10.equals("varp") || __dummyScrutVar10.equals("rownumber") || __dummyScrutVar10.equals("runningvalue"))
        {
            rc = true;
        }
        else
        {
            rc = false;
        } 
        return rc;
    }

    private void resolveFields(String aggr, List<FunctionField> fargs, List<IExpr> args) throws Exception {
        if (fargs == null || fargs.Count == 0)
            return ;
         
        // get the scope argument offset
        int argOffset = StringSupport.equals(aggr.ToLower(), "countrows") ? 1 : 2;
        DataSetDefn ds;
        if (args.Count == argOffset)
        {
            String dsname = null;
            IExpr e = args[argOffset - 1];
            if (e instanceof ConstantString)
                dsname = e.evaluateString(null,null);
             
            if (dsname == null)
                throw new ParserException(String.Format("{0} function's scope must be a constant.", aggr));
             
            ds = this.idLookup.scopeDataSet(dsname);
            if (ds == null)
                throw new ParserException(String.Format("Scope '{0}' does not reference a known DataSet.", dsname));
             
        }
        else
        {
            ds = this.idLookup.scopeDataSet(null);
            if (ds == null)
                throw new ParserException(String.Format("No scope provided for aggregate function '{0}' but more than one DataSet defined.", aggr));
             
        } 
        for (Object __dummyForeachVar0 : fargs)
        {
            FunctionField f = (FunctionField)__dummyForeachVar0;
            if (f.getFld() != null)
                continue;
             
            Field dsf = ds.getFields().get___idx(f.getName());
            if (dsf == null)
                throw new ParserException(String.Format("Field '{0}' is not in DataSet {1}.", f.getName(), ds.getName().getNm()));
             
            f.setFld(dsf);
        }
        return ;
    }

    private Object resolveAggrScope(IExpr[] args, int indexOfScope, RefSupport<boolean> bSimple) throws Exception {
        Object scope = new Object();
        bSimple.setValue(true);
        if (args.Length >= indexOfScope)
        {
            String n = args[indexOfScope - 1].EvaluateString(null, null);
            if (idLookup.getIsPageScope())
                throw new ParserException(String.Format("Scope '{0}' can't be specified in a Page Header or Footer expression.", n));
             
            scope = idLookup.lookupScope(n);
            if (scope == null)
            {
                Identifier ie = args[indexOfScope - 1] instanceof Identifier ? (Identifier)args[indexOfScope - 1] : (Identifier)null;
                if (ie == null || ie.getIsNothing() == false)
                    throw new ParserException(String.Format("Scope '{0}' is not a known Grouping, DataSet or DataRegion name.", n));
                 
            }
             
            // check for "nothing" identifier
            if (args.Length > indexOfScope)
            {
                // has recursive/simple been specified
                IdentifierKey k = args[indexOfScope] instanceof IdentifierKey ? (IdentifierKey)args[indexOfScope] : (IdentifierKey)null;
                if (k == null)
                    throw new ParserException("Illegal scope identifer specified.  At column " + Convert.ToString(curToken.StartCol));
                 
                if (k.getValue() == IdentifierKeyEnum.Recursive)
                    bSimple.setValue(false);
                 
            }
             
        }
        else if (idLookup.getIsPageScope())
        {
            scope = "pghf";
        }
        else
        {
            // indicates page header or footer
            scope = idLookup.lookupGrouping();
            if (scope == null)
            {
                scope = idLookup.lookupMatrix();
                if (scope == null)
                    scope = idLookup.scopeDataSet(null);
                 
            }
             
        }  
        return scope;
    }

    private IExpr resolveParametersMethod(String pname, String vf, IExpr[] args) throws Exception {
        FunctionReportParameter result;
        ReportParameter p = idLookup.lookupParameter(pname);
        if (p == null)
            throw new ParserException("Report parameter '" + pname + "'  not found.");
         
        String arrayMethod = new String();
        int posBreak = vf.IndexOf('.');
        if (posBreak > 0)
        {
            arrayMethod = vf.Substring(posBreak + 1);
            // rest of expression
            vf = vf.Substring(0, posBreak);
        }
        else
            arrayMethod = null; 
        if (vf == null || StringSupport.equals(vf, "Value"))
            result = new FunctionReportParameter(p);
        else if (StringSupport.equals(vf, "Label"))
            result = new FunctionReportParameterLabel(p);
        else
            throw new ParserException("Parameter '" + pname + "'  only supports 'Value' and 'Label' properties.");  
        result.SetParameterMethod(arrayMethod, args);
        return result;
    }

    private IExpr resolveMethodCall(String fullname, IExpr[] args) throws Exception {
        String cls = new String(), method = new String();
        int idx = fullname.LastIndexOf('.');
        if (idx > 0)
        {
            cls = fullname.Substring(0, idx);
            method = fullname.Substring(idx + 1);
        }
        else
        {
            cls = "";
            method = fullname;
        } 
        // Fill out the argument types
        Type[] argTypes = new Type[args.Length];
        for (int i = 0;i < args.Length;i++)
        {
            argTypes[i] = XmlUtil.GetTypeFromTypeCode(args[i].GetTypeCode());
        }
        // See if this is a function within the Code element
        Type cType = null;
        boolean bCodeFunction = false;
        if (StringSupport.equals(cls, "") || StringSupport.equals(cls.ToLower(), "code"))
        {
            cType = idLookup.getCodeClassType();
            // get the code class type
            if (cType != null)
            {
                if (cType.GetMethod(method, argTypes) == null)
                    cType = null;
                else
                    // try for the method in the instance
                    bCodeFunction = true; 
            }
             
            if (!StringSupport.equals(cls, "") && !bCodeFunction)
                throw new ParserException(String.Format("{0} is not a Code method.  Verify the name of the method and its arguments match an existing code function.", method));
             
        }
         
        // See if this is a function within the instance classes
        ReportClass rc = null;
        if (cType == null)
        {
            rc = idLookup.lookupInstance(cls);
            // is this an instance variable name?
            if (rc == null)
            {
                cType = idLookup.lookupType(cls);
            }
            else
            {
                // no, must be a static class reference
                cType = idLookup.lookupType(rc.getClassName());
            } 
        }
         
        // yes, use the classname of the ReportClass
        String syscls = null;
        if (cType == null)
        {
            // ok try for some of the system functions
            System.String __dummyScrutVar11 = cls;
            if (__dummyScrutVar11.equals("Math"))
            {
                syscls = "System.Math";
            }
            else if (__dummyScrutVar11.equals("String"))
            {
                syscls = "System.String";
            }
            else if (__dummyScrutVar11.equals("Convert"))
            {
                syscls = "System.Convert";
            }
            else if (__dummyScrutVar11.equals("Financial"))
            {
                syscls = "fyiReporting.RDL.Financial";
            }
            else
            {
                syscls = "fyiReporting.RDL.VBFunctions";
            }    
            if (syscls != null)
            {
                cType = Type.GetType(syscls);
            }
             
        }
         
        if (cType == null)
        {
            String err = new String();
            if (cls == null || cls.Length == 0)
                err = String.Format("Function {0} is not known.", method);
            else
                err = String.Format("Class {0} is not known.", cls); 
            throw new ParserException(err);
        }
         
        IExpr result = null;
        //			MethodInfo mInfo = cType.GetMethod(method, BindingFlags.,binder, argTypes, modifiers);
        MethodInfo mInfo = cType.GetMethod(method, argTypes);
        if (mInfo == null)
        {
            String err = new String();
            if (cls == null || cls.Length == 0)
                err = String.Format("Function '{0}' is not known.", method);
            else
                err = String.Format("Function '{0}' of class '{1}' is not known.", method, cls); 
            throw new ParserException(err);
        }
         
        TypeCode tc = Type.GetTypeCode(mInfo.ReturnType);
        if (bCodeFunction)
            result = new FunctionCode(method, args, tc);
        else if (syscls != null)
            result = new FunctionSystem(syscls, method, args, tc);
        else if (rc == null)
            result = new FunctionCustomStatic(idLookup.getCMS(), cls, method, args, tc);
        else
            result = new FunctionCustomInstance(rc, method, args, tc);   
        return result;
    }

}


