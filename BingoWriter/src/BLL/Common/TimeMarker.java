package BLL.Common;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeMarker {
    public String GetCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return sdf.format(now);
    }

    public long GetDateTimeMillisMinus(DateTime dtStart, DateTime dtEnd) {
        return dtEnd.getMillis() - dtStart.getMillis();
    }

    public List<SimpleDateFormat> GetDateTimeFormat()
    {
        List<SimpleDateFormat> sdfList = new ArrayList<>();
        sdfList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        sdfList.add(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        sdfList.add(new SimpleDateFormat("yyyy-MM-dd HH"));
        sdfList.add(new SimpleDateFormat("MM-dd-yyyy HH:mm:ss"));
        sdfList.add(new SimpleDateFormat("MM-dd-yyyy HH:mm"));
        sdfList.add(new SimpleDateFormat("MM-dd-yyyy HH"));
        sdfList.add(new SimpleDateFormat("MM-dd-yyyy"));
        sdfList.add(new SimpleDateFormat("MM-yyyy"));
        return sdfList;
    }
}
