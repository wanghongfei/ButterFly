## ButterFly
ButterFly is a command-line arguments parser that supports multiple standards. Java 8 is required.

### Usage
```
	String[] args = buildArgs("-tal -p 8000 -hm -n 100"); // construct array of strings to simulate command-line arguments

	// constrcut the Parser
	// the second parameter of POSIXParser are an array of arguments that you are interested in.
	Parser p = new POSIXParser(args, "-t", "-a", "-l", "-p", "-h", "-m", "-n"); // for POSIX style
	Parser p = new GUNParser(args, "--t", "--verbose"); // for GUN style
		
	// should be true
	boolean parmT = p.containsOption("-t");
	Assert.assertTrue(parmT);
		
	// should be true
	boolean parmA = p.containsOption("-a");
	Assert.assertTrue(parmA);
		
	// should be true
	boolean parmL = p.containsOption("-l");
	Assert.assertTrue(parmL);
		
	// should be '8000'
	String parmP = p.getArgument("-p");
	Assert.assertNotNull(parmP);
	Assert.assertEquals("8000", parmP);
```


### The POSIX style of command-line arguments
The POSIX conventions for command-line arguments is supported and its details are summarized bellow:

* An option is a hyphen followed by a single alphanumeric character, like this: -o.
* An option may require an argument (which must appear immediately after the option); for example, -o argument or -oargument.
* Options that do not require arguments can be grouped after a hyphen, so, for example, -lst is equivalent to -t -l -s.
* Options can appear in any order; thus -lst is equivalent to -tls.
* Options can appear multiple times.
* Options precede other nonoption arguments: -lst nonoption.
* The -- argument terminates options.
* The - option is typically used to represent one of the standard input streams.

### The GUN style of command-line arguments
Based on the POSIX style, GUN style adds double-hyphen arguments like '--verbose'.