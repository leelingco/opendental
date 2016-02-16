//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 7:59:27 PM
//

package OpenDental;

import OpenDental.CreditCardUtils;
import OpenDental.MagstripCardParseException;

public class MagstripCardParser   
{
    private static final char TRACK_SEPARATOR = '?';
    private static final char FIELD_SEPARATOR = '^';
    private String _inputStripeStr = new String();
    private String _track1Data = new String();
    private String _track2Data = new String();
    private String _track3Data = new String();
    private boolean _needsParsing = new boolean();
    private boolean _hasTrack1 = new boolean();
    private boolean _hasTrack2 = new boolean();
    private boolean _hasTrack3 = new boolean();
    private String _accountHolder = new String();
    private String _firstName = new String();
    private String _lastName = new String();
    private String _accountNumber = new String();
    private int _expMonth = new int();
    private int _expYear = new int();
    public MagstripCardParser(String trackString) throws Exception {
        _inputStripeStr = trackString;
        _needsParsing = true;
        parse();
    }

    public boolean getHasTrack1() throws Exception {
        return _hasTrack1;
    }

    public boolean getHasTrack2() throws Exception {
        return _hasTrack2;
    }

    public boolean getHasTrack3() throws Exception {
        return _hasTrack3;
    }

    public String getTrack1() throws Exception {
        return _track1Data;
    }

    public String getTrack2() throws Exception {
        return _track2Data;
    }

    public String getTrack3() throws Exception {
        return _track3Data;
    }

    public String getTrackData() throws Exception {
        return _track1Data + _track2Data + _track3Data;
    }

    public String getAccountName() throws Exception {
        return _accountHolder;
    }

    public String getFirstName() throws Exception {
        return _firstName;
    }

    public String getLastName() throws Exception {
        return _lastName;
    }

    public String getAccountNumber() throws Exception {
        return _accountNumber;
    }

    public int getExpirationMonth() throws Exception {
        return _expMonth;
    }

    public int getExpirationYear() throws Exception {
        return _expYear;
    }

    protected void parse() throws Exception {
        if (!_needsParsing)
        {
            return ;
        }
         
        try
        {
            //Example: Track 1 Data Only
            //%B1234123412341234^CardUser/John^030510100000019301000000877000000?
            //Key off of the presence of "^" but not "="
            //Example: Track 2 Data Only
            //;1234123412341234=0305101193010877?
            //Key off of the presence of "=" but not "^"
            //Determine the presence of special characters
            String[] tracks = _inputStripeStr.Split(new char[]{ TRACK_SEPARATOR }, StringSplitOptions.RemoveEmptyEntries);
            if (tracks.Length > 0)
            {
                _hasTrack1 = true;
                _track1Data = tracks[0];
            }
             
            if (tracks.Length > 1)
            {
                _hasTrack2 = true;
                _track2Data = tracks[1];
            }
             
            if (tracks.Length > 2)
            {
                _hasTrack3 = true;
                _track3Data = tracks[2];
            }
             
            if (_hasTrack1)
            {
                parseTrack1();
            }
             
            if (_hasTrack2)
            {
                parseTrack2();
            }
             
            if (_hasTrack3)
            {
                parseTrack3();
            }
             
        }
        catch (MagstripCardParseException __dummyCatchVar1)
        {
            throw __dummyCatchVar1;
        }
        catch (Exception ex)
        {
            throw new MagstripCardParseException(ex);
        }

        _needsParsing = false;
    }

    private void parseTrack1() throws Exception {
        if (String.IsNullOrEmpty(_track1Data))
        {
            throw new MagstripCardParseException("Track 1 data is empty.");
        }
         
        String[] parts = _track1Data.Split(new char[]{ FIELD_SEPARATOR }, StringSplitOptions.None);
        if (parts.Length != 3)
        {
            throw new MagstripCardParseException("Missing last field separator (^) in track 1 data.");
        }
         
        _accountNumber = CreditCardUtils.StripNonDigits(parts[0]);
        if (!String.IsNullOrEmpty(parts[1]))
        {
            _accountHolder = parts[1].Trim();
        }
         
        if (!String.IsNullOrEmpty(_accountHolder))
        {
            int nameDelim = _accountHolder.IndexOf("/");
            if (nameDelim > -1)
            {
                _lastName = _accountHolder.Substring(0, nameDelim);
                _firstName = _accountHolder.Substring(nameDelim + 1);
            }
             
        }
         
        //date format: YYMM
        String expDate = parts[2].Substring(0, 4);
        _expYear = parseExpireYear(expDate);
        _expMonth = parseExpireMonth(expDate);
    }

    private void parseTrack2() throws Exception {
        if (String.IsNullOrEmpty(_track2Data))
        {
            throw new MagstripCardParseException("Track 2 data is empty.");
        }
         
        if (_track2Data.StartsWith(";"))
        {
            _track2Data = _track2Data.Substring(1);
        }
         
        //may have already parsed this info out if track 1 data present
        if (String.IsNullOrEmpty(_accountNumber) || (_expMonth == 0 || _expYear == 0))
        {
            //Track 2 only cards
            //Ex: ;1234123412341234=0305101193010877?
            int sepIndex = _track2Data.IndexOf('=');
            if (sepIndex < 0)
            {
                throw new MagstripCardParseException("Invalid track 2 data.");
            }
             
            String[] parts = _track2Data.Split(new char[]{ '=' }, StringSplitOptions.RemoveEmptyEntries);
            if (parts.Length != 2)
            {
                throw new MagstripCardParseException("Missing field separator (=) in track 2 data.");
            }
             
            if (String.IsNullOrEmpty(_accountNumber))
            {
                _accountNumber = CreditCardUtils.StripNonDigits(parts[0]);
            }
             
            if (_expMonth == 0 || _expYear == 0)
            {
                //date format: YYMM
                String expDate = parts[1].Substring(0, 4);
                _expYear = parseExpireYear(expDate);
                _expMonth = parseExpireMonth(expDate);
            }
             
        }
         
    }

    private void parseTrack3() throws Exception {
    }

    //not implemented
    private int parseExpireMonth(String s) throws Exception {
        s = CreditCardUtils.stripNonDigits(s);
        if (!validateExpiration(s))
        {
            return 0;
        }
         
        if (s.Length > 4)
        {
            s = s.Substring(0, 4);
        }
         
        return int.Parse(s.Substring(2, 2));
    }

    private int parseExpireYear(String s) throws Exception {
        s = CreditCardUtils.stripNonDigits(s);
        if (!validateExpiration(s))
        {
            return 0;
        }
         
        if (s.Length > 4)
        {
            s = s.Substring(0, 4);
        }
         
        int y = int.Parse(s.Substring(0, 2));
        if (y > 80)
        {
            y += 1900;
        }
        else
        {
            y += 2000;
        } 
        return y;
    }

    private boolean validateExpiration(String s) throws Exception {
        if (String.IsNullOrEmpty(s))
        {
            return false;
        }
         
        if (s.Length < 4)
        {
            return false;
        }
         
        return true;
    }

}


