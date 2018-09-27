package thomasWilliams.RunningCalculator;

public class MillisecondsToString {
	private String ConvertedMilliseconds;

	MillisecondsToString(double dblMilliseconds) {
		double t0 = 0;
		double tss = 0;
		double tmm = 0;
		double thh = 0;

		String sthh;
		String stmm;
		String stss;
		String st0;

		thh = (int) (dblMilliseconds * 24);
		tmm = (int) ((dblMilliseconds - (thh / 24)) * 60 * 24);
		tss = (int) ((dblMilliseconds - ((thh / 24) + (tmm / (60 * 24)))) * 60 * 60 * 24);
		t0 = (int) ((dblMilliseconds - ((thh / 24) + (tmm / (60 * 24)) + (tss / (60 * 60 * 24)))) * 10 * 60 * 60 * 24);

		sthh = Integer.toString((int) thh);
		stmm = Integer.toString((int) tmm);
		stss = Integer.toString((int) tss);
		st0 = Integer.toString((int) t0);

		if (dblMilliseconds > (99.0 / 24) + (59.0 / (60 * 24)) + (59.0 / (60 * 60 * 24)) + (9.0 / (10 * 60 * 60 * 24))) {
			ConvertedMilliseconds = "99:59:59.9";
		} else {
			while (sthh.length() < 2) {
				sthh = "0" + sthh;
			}
			while (stmm.length() < 2) {
				stmm = "0" + stmm;
			}
			while (stss.length() < 2) {
				stss = "0" + stss;
			}

			ConvertedMilliseconds = sthh + ":" + stmm + ":" + stss + "." + st0;
		}
		System.out.println(dblMilliseconds);
		System.out.println(sthh + " | " + stmm + " | " + stss + " | " + st0);
	}

	public String getConvertedString() {
		return ConvertedMilliseconds;
	}
}