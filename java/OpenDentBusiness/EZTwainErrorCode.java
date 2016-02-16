//
// Translated by CS2J (http://www.cs2j.com): 2/15/2016 8:00:51 PM
//

package OpenDentBusiness;


public enum EZTwainErrorCode
{
    /**
    * EZTwain Error Codes. 43 EZTEC_NO_PDF is because of a missing DLL.0
    */
    EZTEC_NONE,
    /**
    * 1
    */
    EZTEC_START_TRIPLET_ERRS,
    /**
    * 3
    */
    EZTEC_CAP_GET,
    /**
    * 3
    */
    EZTEC_CAP_SET,
    /**
    * 4
    */
    EZTEC_DSM_FAILURE,
    /**
    * 5
    */
    EZTEC_DS_FAILURE,
    /**
    * 6
    */
    EZTEC_XFER_FAILURE,
    /**
    * 7
    */
    EZTEC_END_TRIPLET_ERRS,
    /**
    * 8
    */
    EZTEC_OPEN_DSM,
    /**
    * 9
    */
    EZTEC_OPEN_DEFAULT_DS,
    /**
    * 10
    */
    EZTEC_NOT_STATE_4,
    /**
    * 11
    */
    EZTEC_NULL_HCON,
    /**
    * 12
    */
    EZTEC_BAD_HCON,
    /**
    * 13
    */
    EZTEC_BAD_CONTYPE,
    /**
    * 14
    */
    EZTEC_BAD_ITEMTYPE,
    /**
    * 15
    */
    EZTEC_CAP_GET_EMPTY,
    /**
    * 16
    */
    EZTEC_CAP_SET_EMPTY,
    /**
    * 17
    */
    EZTEC_INVALID_HWND,
    /**
    * 18
    */
    EZTEC_PROXY_WINDOW,
    /**
    * 19
    */
    EZTEC_USER_CANCEL,
    /**
    * 20
    */
    EZTEC_RESOLUTION,
    /**
    * 21
    */
    EZTEC_LICENSE,
    /**
    * 22
    */
    EZTEC_JPEG_DLL,
    /**
    * 23
    */
    EZTEC_SOURCE_EXCEPTION,
    /**
    * 24
    */
    EZTEC_LOAD_DSM,
    /**
    * 25
    */
    EZTEC_NO_SUCH_DS,
    /**
    * 26
    */
    EZTEC_OPEN_DS,
    /**
    * 27
    */
    EZTEC_ENABLE_FAILED,
    /**
    * 28
    */
    EZTEC_BAD_MEMXFER,
    /**
    * 29
    */
    EZTEC_JPEG_GRAY_OR_RGB,
    /**
    * 30
    */
    EZTEC_JPEG_BAD_Q,
    /**
    * 31
    */
    EZTEC_BAD_DIB,
    /**
    * 32
    */
    EZTEC_BAD_FILENAME,
    /**
    * 33
    */
    EZTEC_FILE_NOT_FOUND,
    /**
    * 34
    */
    EZTEC_FILE_ACCESS,
    /**
    * 35
    */
    EZTEC_MEMORY,
    /**
    * 36
    */
    EZTEC_JPEG_ERR,
    /**
    * 37
    */
    EZTEC_JPEG_ERR_REPORTED,
    /**
    * 38
    */
    EZTEC_0_PAGES,
    /**
    * 39
    */
    EZTEC_UNK_WRITE_FF,
    /**
    * 40
    */
    EZTEC_NO_TIFF,
    /**
    * 41
    */
    EZTEC_TIFF_ERR,
    /**
    * 42
    */
    EZTEC_PDF_WRITE_ERR,
    /**
    * 43
    */
    EZTEC_NO_PDF,
    /**
    * 44
    */
    EZTEC_GIFCON,
    /**
    * 45
    */
    EZTEC_FILE_READ_ERR,
    /**
    * 46
    */
    EZTEC_BAD_REGION,
    /**
    * 47
    */
    EZTEC_FILE_WRITE,
    /**
    * 48
    */
    EZTEC_NO_DS_OPEN,
    /**
    * 49
    */
    EZTEC_DCXCON,
    /**
    * 50
    */
    EZTEC_NO_BARCODE,
    /**
    * 51
    */
    EZTEC_UNK_READ_FF,
    /**
    * 52
    */
    EZTEC_DIB_FORMAT,
    /**
    * 53
    */
    EZTEC_PRINT_ERR,
    /**
    * 54
    */
    EZTEC_NO_DCX,
    /**
    * 55
    */
    EZTEC_APP_BAD_CON,
    /**
    * 56
    */
    EZTEC_LIC_KEY,
    /**
    * 57
    */
    EZTEC_INVALID_PARAM,
    /**
    * 58
    */
    EZTEC_INTERNAL,
    /**
    * 59
    */
    EZTEC_LOAD_DLL,
    /**
    * 60
    */
    EZTEC_CURL,
    /**
    * 61
    */
    EZTEC_MULTIPAGE_OPEN,
    /**
    * 62
    */
    EZTEC_BAD_SHUTDOWN,
    /**
    * 63
    */
    EZTEC_DLL_VERSION,
    /**
    * 64
    */
    EZTEC_OCR_ERR,
    /**
    * 65
    */
    EZTEC_ONLY_TO_PDF,
    /**
    * 66
    */
    EZTEC_APP_TITLE,
    /**
    * 67
    */
    EZTEC_PATH_CREATE,
    /**
    * 68
    */
    EZTEC_LATE_LIC,
    /**
    * 69
    */
    EZTEC_PDF_PASSWORD,
    /**
    * 70
    */
    EZTEC_PDF_UNSUPPORTED,
    /**
    * 71
    */
    EZTEC_PDF_BAFFLED,
    /**
    * 72
    */
    EZTEC_PDF_INVALID,
    /**
    * 73
    */
    EZTEC_PDF_COMPRESSION,
    /**
    * 74
    */
    EZTEC_NOT_ENOUGH_PAGES,
    /**
    * 75
    */
    EZTEC_DIB_ARRAY_OVERFLOW,
    /**
    * 76
    */
    EZTEC_DEVICE_PAPERJAM,
    /**
    * 77
    */
    EZTEC_DEVICE_DOUBLEFEED,
    /**
    * 78
    */
    EZTEC_DEVICE_COMM,
    /**
    * 79
    */
    EZTEC_DEVICE_INTERLOCK,
    /**
    * 80
    */
    EZTEC_BAD_DOC,
    /**
    * 81
    */
    EZTEC_OTHER_DS_OPEN
}

