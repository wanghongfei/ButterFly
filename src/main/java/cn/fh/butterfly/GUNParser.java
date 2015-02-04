package cn.fh.butterfly;

import java.util.Map;

public class GUNParser extends POSIXParser {

	public GUNParser(String[] args, String... tokens) {
		super(args, tokens);
	}

	@Override
	protected void parseOtherType(String option, String value, Map<String, String> map) {
		if (option.startsWith("--")) {
			map.put(option, value);
		}
	}

	
}
