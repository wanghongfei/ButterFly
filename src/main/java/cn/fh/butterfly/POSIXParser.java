package cn.fh.butterfly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POSIXParser implements Parser {
	private List<String> argList;
	private List<String> optTokenList;
	private Map<String, String> argMap;
	
	protected enum TokenType {
		/**
		 * string likes '-XXX'
		 */
		SINGLE_HYPHEN,
		/**
		 * string likes 'XXX'
		 */
		NO_HYPHEN,
		OTHER
	}
	
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
	
	/**
	 * traverse every string in argument list and process.
	 */
	private void parse() {
		Map<String, String> map = new HashMap<>();
		
		int len = this.argList.size();
		int ix = 0;
		// traverse argument list
		while (ix < len) {
			// get the current string
			String option = this.argList.get(ix);
			// the next string
			String arg = null;
			
			TokenType type = determineTokenType(option);

			int indexIncreasement = 2;
			if (notTheLastElem(ix, len) ) {
				// next element exists
				// take the next parameter as an argument
				arg = this.argList.get(ix + 1);
				TokenType nextType = determineTokenType(arg);

				// if the next parameter isn't an argument,
				// set arg variable to null
				if (nextType != TokenType.NO_HYPHEN) {
					arg = null;
					indexIncreasement = 1;
				}
			}

			ix += indexIncreasement;
			
			parseOption(option, arg, type, map);
			
		}

		
		this.argMap = map;
	}

	
	private TokenType determineTokenType(String token) {
		if (token.startsWith("-")) {
			if (token.length() > 1) {
				if (token.charAt(1) != '-') {
					return TokenType.SINGLE_HYPHEN;
				} else {
					return TokenType.OTHER;
				}
			}
		}
		
		return TokenType.NO_HYPHEN;
	}
	
	/**
	 * Determine if this is the last element.
	 * @param curIndex
	 * @param len
	 * @return
	 */
	private boolean notTheLastElem(int curIndex, int len) {
		return curIndex + 1 < len;
	}
	
	private boolean isMultipleOptions(String option) {
		return option.length() > 2;
	}
	
	private void parseOption(String option, String value, TokenType type, Map<String, String> map) {
		
		if (type == TokenType.SINGLE_HYPHEN) {
			if (isMultipleOptions(option)) {
				// parse string likes '-tla'
				parseMultipleTokens(option, map);
			} else {
				// string likes '-t'
				parseSingleToken(option, value, map);
			}
		} else {
			// other type of option
			// let the derived class(if any) tackles this option
			parseOtherType(option, value, map);
		}
	}
	
	/**
	 * Derived class can override this method to deal with other type of options.
	 * 
	 * @param option
	 * @param value
	 * @param type
	 * @param map
	 */
	protected void parseOtherType(String option, String value, Map<String, String> map) {
	}
	
	/**
	 * Parse string likes '-tls'
	 * 
	 * @param token
	 */
	private void parseMultipleTokens(String token, Map<String, String> map) {
		int len = token.length();
		
		// start traverse from the first letter
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
