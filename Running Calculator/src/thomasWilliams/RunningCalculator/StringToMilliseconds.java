package thomasWilliams.RunningCalculator;

public class StringToMilliseconds {
	// using public or using no word makes the variable public
	private double millisecs;

	StringToMilliseconds(String StringInTimeFormat) {
		// 00:00:00.0
		double t0 = 0;
		double tss = 0;
		double tmm = 0;
		double thh = 0;

		thh = Integer.parseInt(StringInTimeFormat.substring(0, 2));
		tmm = Integer.parseInt(StringInTimeFormat.substring(3, 5));
		tss = Integer.parseInt(StringInTimeFormat.substring(6, 8));
		t0 = Integer.parseInt(StringInTimeFormat.substring(9, StringInTimeFormat.length()));

		t0 = t0 / (10 * 60 * 60 * 24);
		tss = tss / (60 * 60 * 24);
		tmm = tmm / (60 * 24);
		thh = thh / (24);

		millisecs = t0 + tss + tmm + thh;
	}

	public double getMillisecs(String StringConvertedToMilliseconds) {
		// String StringInTimeFormat = this.StringInTimeFormat;
		System.out.println(StringConvertedToMilliseconds);
		System.out.println(millisecs);
		return millisecs;
	}
}
