package testex;

import java.util.Date;

public interface IDateFormatter {

    String getFormattedDate(String timeZone, Date time) throws JokeException;
}
