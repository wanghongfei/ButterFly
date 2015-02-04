package cn.fh.butterfly.test;

import org.junit.Assert;
import org.junit.Test;

import cn.fh.butterfly.GUNParser;
import cn.fh.butterfly.POSIXParser;
import cn.fh.butterfly.Parser;

public class GUNParserTest {
	@Test
	public void test2Args() {
		String[] args = buildArgs("-t 1000 -p 30");

		Parser p = new GUNParser(args, new String[] { "-t", "-p" });

		String time = p.getArgument("-t");
		Assert.assertNotNull(time);
		Assert.assertEquals("1000", time);

		String port = p.getArgument("-p");
		Assert.assertNotNull(port);
		Assert.assertEquals("30", port);
	}

	@Test
	public void test1Arg() {
		String[] args = buildArgs("-t 1000");

		Parser p = new GUNParser(args, new String[] { "-t" });

		String time = p.getArgument("-t");
		Assert.assertNotNull(time);
		Assert.assertEquals("1000", time);

	}

	@Test
	public void test0Arg() {
		Parser p = new GUNParser(new String[0], new String[] { "-t" });

		String time = p.getArgument("-t");
		Assert.assertNull(time);
	}

	@Test
	public void testMultipleArgs() {
		String[] args = buildArgs("-tal");
		Parser p = new GUNParser(args, new String[] { "-t", "-a", "-l" });

		boolean parmT = p.containsOption("-t");
		Assert.assertTrue(parmT);

		boolean parmA = p.containsOption("-a");
		Assert.assertTrue(parmA);

		boolean parmL = p.containsOption("-l");
		Assert.assertTrue(parmL);

		boolean otherParam = p.containsOption("-b");
		Assert.assertFalse(otherParam);
	}

	@Test
	public void testMultipleArgsWith() {
		String[] args = buildArgs("-tal -p 8000 -hm -n 100");
		Parser p = new GUNParser(args, "-t", "-a", "-l", "-p", "-h", "-m", "-n");

		boolean parmT = p.containsOption("-t");
		Assert.assertTrue(parmT);

		boolean parmA = p.containsOption("-a");
		Assert.assertTrue(parmA);

		boolean parmL = p.containsOption("-l");
		Assert.assertTrue(parmL);

		boolean parmM = p.containsOption("-m");
		Assert.assertTrue(parmM);

		boolean parmH = p.containsOption("-h");
		Assert.assertTrue(parmH);

		String parmP = p.getArgument("-p");
		Assert.assertNotNull(parmP);
		Assert.assertEquals("8000", parmP);

		String parmN = p.getArgument("-n");
		Assert.assertNotNull(parmN);
		Assert.assertEquals("100", parmN);

		boolean otherParam = p.containsOption("-b");
		Assert.assertFalse(otherParam);
	}
	
	@Test
	public void testGunFeature() {
		String[] args = buildArgs("-tal --verbose --port 8000");
		Parser p = new GUNParser(args, new String[] { "-t", "-a", "-l", "--verbose", "--port" });

		boolean parmT = p.containsOption("-t");
		Assert.assertTrue(parmT);

		boolean parmA = p.containsOption("-a");
		Assert.assertTrue(parmA);

		boolean parmL = p.containsOption("-l");
		Assert.assertTrue(parmL);

		boolean otherParam = p.containsOption("-b");
		Assert.assertFalse(otherParam);	
		
		boolean parmVerbose = p.containsOption("--verbose");
		Assert.assertTrue(parmVerbose);
		
		String parmPort = p.getArgument("--port");
		Assert.assertNotNull(parmPort);
		Assert.assertEquals("8000", parmPort);
	}

	private String[] buildArgs(String arg) {
		return arg.split(" ");
	}

	private void print(String[] strs) {
		for (String str : strs) {
			System.out.println(str);
		}
	}
}
