package com.andymur.pg.java.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class OldCollectionsRunner {

	public static void main(String[] args) {

		Map<String, CompanyStatus> statuses = new HashMap<>();

		statuses.put("efg", CompanyStatus.of(5, true));
		statuses.put("cde", CompanyStatus.of(3, false));
		statuses.put("abc", CompanyStatus.of(10, true));

		List<String> companyNames = new ArrayList<>(statuses.keySet());
		Collections.sort(companyNames);

		List<String> result = new ArrayList<>(companyNames.size());

		for (String companyName: companyNames) {
			if (statuses.get(companyName).status) {
				result.add(getTime(companyName, statuses.get(companyName)));
			}
		}

		Optional<String> a = Optional.ofNullable("");


		System.out.println(result);
	}

	static String getStatusAndTime(final String companyName, final CompanyStatus companyStatus) {
		if (companyStatus.status) {
			return companyName.concat(":open:" + companyStatus.timeTakenToOpenCompany);
		} else {
			return companyName.concat(":closed:-1");
		}
	}

	static String getTime(final String companyName, final CompanyStatus companyStatus) {
		if (companyStatus.status) {
			return companyName.concat(":" + companyStatus.timeTakenToOpenCompany);
		} else {
			return companyName.concat(":-1");
		}
	}

	static class CompanyStatus {
		private final boolean status;
		private final int timeTakenToOpenCompany;

		private CompanyStatus(final boolean status, final int timeTakenToOpenCompany) {
			this.status = status;
			this.timeTakenToOpenCompany = timeTakenToOpenCompany;
		}

		static CompanyStatus of(int timeTakenToOpenCompany, boolean status) {
			return new CompanyStatus(status, timeTakenToOpenCompany);
		}
	}
}
