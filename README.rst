MARM
====

MARM is a homegrown tool for creating multiple version of resources with minimal effort.


For example, assume you want to have an asset in x1 and x2 sizes.
You could do this manually but marm simplifies the process for you.


Building
--------


To build run::

   ant dist

This will create marm_app.jar. To simplify its use you may create an alias::

   alias marm="java -jar marm_app.jar"

Usage
-----

The two main operation modes are resize and pack::

    marm [OPTIONS] resize <dir-in> <dir-out>
    marm [OPTIONS] pack <dir-in> <dir-out> <atlas name>

The files recognized and processed in <dir-in> are inkscape SVG, PNG and HIERO font files. Any other file is copied to the three destination directories as as.

Valid options are::

   name=value          # set variable
   -<n>                # request a particular size (n = 1, 2, 4 or 8)
   -v                  # be verbose

If you don't specify any sizes then the tool will assume x1, x2 and x4.


Variables
~~~~~~~~~

MARM assumes the following commands to be available

 * convert
 * inkscape
 * hiero

You can replace these with your own, for example you can do::

    marm *inkscape=/opt/inkscape/bin/inkscape* resize indir outdir


Conversion rules
~~~~~~~~~~~~~~~~

The last part of the source filename defines how it will be converted.

* *Source rules:* Normally given A.svg marm will create 1/A.png, 2/A.png and 4/A.png. However given A_14.png and A_2.png marm will use the former to create 1/A.png and 4/A.png and the latter to create 2/A.png.
* *Keep file:* A file named A_k.svg will not be resized (e.g. 1/A.png and 4/A.png will be of the same size).
* *Plain files:* A file named A_p.svg will not be processed to remove alpha-blend pixels.


You can combine multiple rules. For example A_12pk.svg is a valid input file.
