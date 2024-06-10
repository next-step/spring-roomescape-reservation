package roomescape.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtils {
	public static void validDateFormat(String date) throws ParseException {
		if(!StringUtils.hasText(date)) {
			throw new IllegalArgumentException("날짜");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		sdf.parse(date);
	}

	public static void validTimeFormat(String time) throws ParseException {
		if(!StringUtils.hasText(time)) {
			throw new IllegalArgumentException("시간");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		sdf.setLenient(false);
		sdf.parse(time);
	}
}
