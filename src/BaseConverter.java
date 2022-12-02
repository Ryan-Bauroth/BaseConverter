import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Base convert from any base (2-16) to another base (2-16)
 * @Author Ryfi
 * @Version 11.30.22
 * @Extra Uses files to allow user to select which values document they want to use. The program will open a file system for you and simply select the values file you want to use.
 */
public class BaseConverter{
    private final String DIGITS = "0123456789ABCDEF";
    /**
     * Convert a String num in fromBase to base-10 int.
     * @param num the original number
     * @param fromBase the original from base
     * @return a base-10 int of num base fromBase
     */
    public int strToInt(String num, String fromBase)    {
        int value = 0, exp = 0;
        for(int i = num.length()-1; i >= 0; i--, exp++){
            value += DIGITS.indexOf(num.substring(i, i+1)) * Math.pow(Integer.parseInt(fromBase), exp);
        }
        return value;
    }

    /**
     * Convert an int to any base 2-16 number
     * @param num the original number
     * @param toBase what base you want to convert it to
     * @return a number matching the base of toBase from num
     */
    public String intToStr(int num, int toBase) {
        String toNum = "";
        while(num > 0){
            int pos = num % toBase;
            toNum = DIGITS.substring(pos, pos+1) + toNum;
            num /= toBase;
        }
        return (toNum.equals("")) ? "0" : toNum;
    }

    /**
     * Converts value datafiles to whatever they call for and prints converted data to both a file and console
     */
    public void inputConvertPrintWrite()    {
        Scanner in = null;
        PrintWriter out = null;
        String output = "";
        int runs = 0;
        try{
            //JFile Tings
            File test = new File("datafiles");
            JFileChooser chooser = new JFileChooser(test);
            FileNameExtensionFilter filter = new FileNameExtensionFilter(".dat Files", "dat");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("2nd Page of Google");
            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                in = new Scanner(new File(chooser.getSelectedFile().toURI()));
            }
            out = new PrintWriter(new File("datafiles/converted.dat"));
            while(in.hasNext()){
                runs++;
                String[] Line = in.nextLine().split("\t");
                if(Integer.parseInt(Line[1]) < 2 || Integer.parseInt(Line[1]) > 16){
                    System.out.println("Invalid input base " + Line[1]);
                }
                else if(Integer.parseInt(Line[2]) < 2 || Integer.parseInt(Line[2]) > 16){
                    System.out.println("Invalid output base " + Line[2]);
                }
                else{
                    if(runs != 1){
                        out.println("");
                    }
                    output = intToStr(strToInt(Line[0], Line[1]), Integer.parseInt(Line[2]));
                    //WRITE TO FILE
                    out.print(Line[0] + "\t" + Line[1] + "\t" + output + "\t" + Line[2]);
                    //PRINT
                    System.out.println(Line[0] + " Base " + Line[1] + " = " + output + " base " + Line[2]);
                }
            }
            if(out !=null)
                out.close();
            if(in != null)
                in.close();
        }
        catch(Exception e){System.out.println("Big yikers. Looks like an error occurred. Details here: " + e.toString());}
    }

    /**
     * Main method of BaseConverter class
     * @param args
     */
    public static void main(String[] args) {
        BaseConverter bc = new BaseConverter();
        bc.inputConvertPrintWrite();
    }
}