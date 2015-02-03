package cn.fh.butterfly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POSIXParser implements Parser {
	private List<String> argList;
	private List<String> optTokenList;
	private Map<String, String> argMap;
	
	/**
	 * Construct a POSIXParser object.
	 * 
	 * @param args The command-line arguments
	 * @param tokens Arguments that you are interested in. e.g., {"-t", "-s", "-p"}
	 */
	public POSIXParser(String[] args, String... tokens) {
		if (null == args || null == tokens) {
			throw new IllegalArgumentException("Array of arguments and tokens cannot be null");
		}

		this.argList = Arrays.asList(args);
		this.optTokenList = Arrays.asList(tokens);
		
		parse();
	}
	

	/**
	 * Get the argument of an option.
	 * Return null if the argument does not exist.
	 */
	@Override
	public String getArgument(String option) {
		return this.argMap.get(option);
	}

	@Override
	public boolean containsOption(String option) {
		return this.argMap.containsKey(option);
	}
	
	private void parse() {
		Map<String, String> map = new HashMap<>();
		
		int len = this.argList.size();
		int ix = 0;
		while (ix < len) {
			String option = this.argList.get(ix);
			String arg = null;

			int indexIncreasement = 2;
			if (notTheLastElem(ix, len) ) {
				arg = this.argList.get(ix + 1);
				if (!isArgument(arg)) {
					arg = null;
					indexIncreasement = 1;
				}
			}

			ix += indexIncreasement;
			
			parseOption(option, arg, map);
			
		}
		
		
		
/*		for (int ix = 0 ; ix < len ; ix += 2) {
			String option = this.argList.get(ix);
			String arg = null;

			if (notTheLastElem(ix, len) ) {
				arg = this.argList.get(ix + 1);
				if (!isArgument(arg)) {
					arg = null;
				}
			}
			
			System.out.println("option:" + option);
			parseOption(option, arg, map);
		}*/
		
		this.argMap = map;
	}
	
	private boolean isArgument(String token) {
		return !token.startsWith("-");
	}
	
	private boolean notTheLastElem(int curIndex, int len) {
		return curIndex + 1 < len;
	}
	
	private void parseOption(String option, String value, Map<String, String> map) {
		int len = option.length();
		
		if (len > 2) {
			// parse string likes '-tla'
			parseMultipleTokens(option, map);
		} else if (len == 2) {
			// string likes '-t'
			parseSingleToken(option, value, map);

		} else {
			// invalid option
			// do nothing
		}
	}
	
	/**
	 * Parse string likes '-tls'
	 * 
	 * @param token
	 */
	private void parseMultipleTokens(String token, Map<String, String> map) {
		int len = token.length();
		for (int ix = 1 ; ix < len ; ++ix) {
			char ch = token.charAt(ix);
			
			if (isToken(ch)) {
				map.put(String.valueOf("-" + ch), null);
			}
		}
	}
	
	/**
	 * Parse string likes '-t'
	 * @param token
	 */
	private void parseSingleToken(String option, String arg, Map<String, String> map) {
		map.put(option, arg);
	}
	
	
	/**
	 * Determine if an option is valid.
	 * @param targetChar
	 * @return
	 */
	private boolean isToken(char targetChar) {
		return this.optTokenList.stream().anyMatch( (token) -> {
			return token.charAt(1) == targetChar;
		});
	}

}
