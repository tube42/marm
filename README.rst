MARM
====

MARM is a simple tool for processing assets for different device resolutions.

Building
--------


To build run::

   ant dist

This will create marm_app.jar. To simplify its use you may create an alias::

   alias marm="java -jar `pwd`/marm_app.jar"

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


Conversion rules
~~~~~~~~~~~~~~~~

The conversion rules are any part of the filename between the last '_' and the file extension.
For example the file *A.svg* with the conversion rules *2*, *4* and *p* is renamed to *A_24p.svg*.


The rules decide how each file will be processed:

* *Plain files:* p specifies that the file will not be processed for removing alpha-blended pixels.
* *Keep file:* k specifies that the file will not be scaled.
* *Scale rules:* 1/2/4/8 specifies the target scales.

For example the file A.svg will be converted to A.png and scaled to x1, x2 and x4. The result will be stored in 1/A.png, 2/A.png and 4/A.png
However, if we were instead given A_12p.svg and A_4.svg the former would have been used to create 1/A.png and 2/A.png without
alpha processing while the latter would have been used to create 4/A.png.


If a size is missing, marm will try to guess a compatible source.


Variables
~~~~~~~~~

MARM assumes the following commands to be available

 * convert
 * inkscape
 * hiero

You can replace these with your own, for example you can do::

    marm inkscape="/opt/inkscape/bin/inkscape --export-background=red" resize indir outdir
