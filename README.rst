MARM
----

MARM is a homegrown tool for creating multiple version of resources with minimal effort.


USAGE
=====

The main function of marm is resize a single set of resources with copies at x1, x2 and x4 size. The format for that is:

    java -jar marm_app.jar resize <input directory> <output directory>

The files recognized and processed are inkscape SVG, PNG and HIERO font files. Any other file is copied to the three destination directories as as.


Note that for HIERO conversion you will need a recent nighly version of libgdx...


VARIABLES
=========
MARM assumes the following commands to be available

 * convert
 * inkscape
 * hiero

You can replace these with your own, for example you can do


    java -jar marm_app.jar *inkscape=/opt/inkscape/bin/inkscape* resize indir outdir
