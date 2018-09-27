package thomasWilliams.RunningCalculator;

public class FormatStringToTime {
	// using public or using no word makes the variable public
	private String StringInCorrectTimeFormat;

	FormatStringToTime(String StringInTimeFormat) {
		double t0 = 0;
		double tss = 0;
		double tmm = 0;
		String stmm;
		String stss;
		String st0;

		if (StringInTimeFormat.indexOf(".") > 0
				&& StringInTimeFormat.indexOf(":") > 0) {
			t0 = Integer.parseInt(StringInTimeFormat.substring(
					StringInTimeFormat.indexOf(".") + 1,
					StringInTimeFormat.length()));
			tss = Integer.parseInt(StringInTimeFormat.substring(
					StringInTimeFormat.indexOf(":") + 1,
					StringInTimeFormat.indexOf(".")));
			tmm = Integer.parseInt(StringInTimeFormat.substring(0,
					StringInTimeFormat.indexOf(":")));
		} else if (StringInTimeFormat.indexOf(".") < 0
				&& StringInTimeFormat.indexOf(":") > 0) {
			t0 = 0;
			tss = Integer.parseInt(StringInTimeFormat.substring(
					StringInTimeFormat.indexOf(":") + 1,
					StringInTimeFormat.length()));
			tmm = Integer.parseInt(StringInTimeFormat.substring(0,
					StringInTimeFormat.indexOf(":")));
		} else if (StringInTimeFormat.indexOf(".") < 0
				&& StringInTimeFormat.indexOf(":") < 0) {
			t0 = 0;
			tss = 0;
			tmm = Integer.parseInt(StringInTimeFormat);
		}
		stmm = Integer.toString((int) tmm);
		stss = Integer.toString((int) tss);
		st0 = Integer.toString((int) t0);

		if (stmm.length() < 2) {
			stmm = "0" + stmm;
		}
		if (stss.length() < 2) {
			stss = "0" + stss;
		}

		StringInCorrectTimeFormat = stmm + ":" + stss + "." + st0;
	}
	public String getStringInCorrectTimeFormat() {
		return StringInCorrectTimeFormat;
	}

}
