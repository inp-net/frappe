package fr.inpt.frappe;

public class utils {
	public static int parseYearTier(Object yearTierObj) {
		int yearTier = 0;

		if (yearTierObj != null) {
			if (yearTierObj instanceof String) {
				yearTier = Integer.parseInt((String) yearTierObj);
			} else if (yearTierObj instanceof Number) {
				yearTier = ((Number) yearTierObj).intValue();
			} else if (yearTierObj instanceof char[]) {
				yearTier = Integer.parseInt(new String((char[]) yearTierObj));
			} else {
				throw new IllegalArgumentException(
						"Unexpected type for yearTier: " + yearTierObj.getClass());
			}
		}

		return yearTier;
	}
}
