package cn.fh.butterfly;


public interface Parser {
	String getArgument(String option);
	boolean containsOption(String option);
}
