## ButterFly
ButterFly is a command-line arguments parser that supports multiple standards.

### The POSIX Conventions
The POSIX conventions for command-line arguments is supported and its details are summarized bellow:

* An option is a hyphen followed by a single alphanumeric character, like this: -o.
* An option may require an argument (which must appear immediately after the option); for example, -o argument or -oargument.
* Options that do not require arguments can be grouped after a hyphen, so, for example, -lst is equivalent to -t -l -s.
* Options can appear in any order; thus -lst is equivalent to -tls.
* Options can appear multiple times.
* Options precede other nonoption arguments: -lst nonoption.
* The -- argument terminates options.
* The - option is typically used to represent one of the standard input streams.