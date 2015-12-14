package com.echo.framework.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PermMetaInfo extends BaseDomain {
	private static final long serialVersionUID = 6357004070110948453L;

	private Set<String> allowRegexSet = new HashSet<String>();
	private Set<String> denyRegexSet = new HashSet<String>();
	private Map<String, Set<String>> allowRegexSet4Rest = new HashMap<String, Set<String>>();
	private Map<String, Set<String>> denyRegexSet4Rest = new HashMap<String, Set<String>>();

	public PermMetaInfo(String[] allowRegexList, String[] denyRegexList,
			Map<String, String[]> allowRegexMap4Rest,
			Map<String, String[]> denyRegexMap4Rest) {
		for (String regex : allowRegexList) {
			this.allowRegexSet.add(regex);
		}

		for (String regex : denyRegexList) {
			this.denyRegexSet.add(regex);
		}

		for (String regex : allowRegexMap4Rest.keySet()) {
			Set<String> methodSet = new HashSet<String>();

			for (String method : allowRegexMap4Rest.get(regex)) {
				methodSet.add(method);
			}

			allowRegexSet4Rest.put(regex, methodSet);
		}

		for (String regex : denyRegexMap4Rest.keySet()) {
			Set<String> methodSet = new HashSet<String>();

			for (String method : denyRegexMap4Rest.get(regex)) {
				methodSet.add(method);
			}

			denyRegexSet4Rest.put(regex, methodSet);
		}
	}

	public Set<String> getAllowRegexSet() {
		return allowRegexSet;
	}

	public Set<String> getDenyRegexSet() {
		return denyRegexSet;
	}

	public Map<String, Set<String>> getAllowRegexSet4Rest() {
		return allowRegexSet4Rest;
	}

	public Map<String, Set<String>> getDenyRegexSet4Rest() {
		return denyRegexSet4Rest;
	}
}
