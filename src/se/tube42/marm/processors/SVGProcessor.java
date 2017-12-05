
package se.tube42.marm.processors;

import java.io.*;
import se.tube42.marm.data.*;
import se.tube42.marm.logic.*;

public class SVGProcessor implements Processor
{

    // fix for an inkscape crazy user-breaking major-major change
    private static int dpi = -1;
    private static int getDPI()
    throws IOException
    {
        if(dpi == -1) {
            dpi = 96;
            try {
                String output = ExecHelper.run("inkscape", "--version");
                String vstr = output.split(" ")[1];
                float vf = Float.parseFloat(vstr);
                if(vf > 0.2 && vf < 0.92 )
                    dpi = 90;
                System.out.println("inkscape version " + vf + " -> using DPI=" + dpi);
            } catch(Exception e) {
                // can't do anything about it
            }

        }
        return dpi;
    }

    public void process(
              String type, int zoom,
              File infile, File outfile)
          throws IOException
    {
        final boolean plain = type.indexOf('p') != -1;
        final boolean keep = type.indexOf('k') != -1;
        final float ratio = keep ? 1f : (float)Math.pow(2, zoom);

        outfile = FileHelper.replaceExtension(outfile, "png");
        final String svg = infile.getPath();
        final String png = outfile.getPath();

        if(plain) {
            ExecHelper.run("inkscape",
                      "--without-gui",
                      "--file="+ svg,
                      "--export-dpi=" + (getDPI() * ratio),
                      "--export-background-opacity=0",
		      "--export-area-page",
                      "--export-png="+png
                      );
        } else {
            ExecHelper.run("inkscape",
                      "--without-gui",
                      "--file="+svg,
		       "--export-dpi=" + (getDPI() * 16),
                      "--export-background-opacity=0",
		      "--export-area-page",
                      "--export-png="+png
                      );

            ExecHelper.run("convert",
                      png,
                      "-transparent",
                      "#fffffe",
                      "-resize",
                      "" + (6.25 * ratio) + "%",
                      "-define",
                      "png:bit-depth=8",
                      "png32:" + png
                      );

        }
    }
}
