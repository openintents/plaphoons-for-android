package org.openintents.plaphoons;
/*
 * Projecte Fressa a JAVA
 * Tools.java
 * Created on 11 / novembre / 2008, 15:40
 *
 * @author Jordi Lagares Roset "jlagares@xtec.cat - www.lagares.org"
 * amb el suport del Departament d'Educacio de la Generalitat de Catalunya
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details (see the LICENSE file).
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.openintents.plaphoons.sample.R;

public class Tools {
    
  public int ErrorCode;
  
  public Tools() {
  }
    
/**************************************************************/
/*BEGIN TOOLS**************************************************/
/**************************************************************/
  public boolean not(boolean b){
    if (b) {
      return false;
    } else {    
      return true;
    }
  }
  
  public boolean Not(boolean b){
    if (b) {
      return false;
    } else {    
      return true;
    }
  }
  
/*STRINGS******************************************************/
  public String TreureFinalsLinea(String s) {
    int i;
    
    i=0;
    while (i<s.length()) {
      if (s.substring(i,i+1).equals("\n")) {
        s=s.substring(0,i)+s.substring(i+1);
      } else {
        i++;
      }
    }
    i=0;
    while (i<s.length()) {
      if (s.substring(i,i+1).equals("\r")) {
        s=s.substring(0,i)+s.substring(i+1);
      } else {
        i++;
      }
    }
    return(s);
  }

  public String removeSpace(String s) {
    /* aixo no pita per treure espais
    s.trim();
    JOptionPane.showMessageDialog(null,s,"TreureEspais",JOptionPane.INFORMATION_MESSAGE);
    return(s);
     */
    int i;
    
    i=0;
    while (i<s.length()) {
      if (s.substring(i,i+1).equals(" ")) {
        s=s.substring(0,i)+s.substring(i+1);
      } else {
        i++;
      }
    }
    return(s);
  }

  public int Pos(String s1,String s) {
    return s.indexOf(s1)+1;
  }

  public int Length(String s) {
    return s.length(); 
  }
  
  public String Copy(String s,int i,int c) {
    if (i<1) {
      if (i+c-1<0) {  
        return "";  
      } else {
        c=i+c-1;
        i=1;
      }
    }
    if ((i-1)<Length(s)) {
      if ((i+c-1)>Length(s)) {
        return s.substring(i-1,Length(s));
      } else {
        return s.substring(i-1,i+c-1);
      }
    } else {
      return "";  
    }
  }
  
  public String LeftS(String s,int nombre) {
    if (nombre>s.length()) {
      return s;
    } else {
      return s.substring(0,nombre);
    }
  }

  public String RightS(String s,int nombre) {
    if (nombre>s.length()) {
      return s;
    } else {
      return s.substring(s.length()-nombre,s.length());
    }
  }
  
  public String Insert(String s1,String s,int i) {
    return LeftS(s,i-1)+s1+RightS(s,s.length()-i+1);
  }
  
  public String Delete(String s,int i,int c) {
    return LeftS(s,i-1)+RightS(s,s.length()-i-c+1);
  }
  
  public String CanviText(String TextACanviar,String FindText,String ReplaceText)  {
    int P;
  
    P=Pos(FindText,TextACanviar);
    while (P>0) {
      TextACanviar=Delete(TextACanviar,P,Length(FindText));
      TextACanviar=Insert(ReplaceText,TextACanviar,P);
      P=Pos(FindText,TextACanviar);
    }
    return TextACanviar;
  }
  
  public String Arredonir(double x)  {
    String s;
    
    s=String.valueOf(Math.round(x*10000)/10000.);
    s=CanviText(s,",",".");
    return s;
  }

  public String RealToString(double v) {
    String s;  
    //return String.valueOf(v);
    //return Arredonir(v);
    s=Arredonir(v);
    if (RightS(s,2).equals(".0")) {
      s=Delete(s,Length(s)-1,2);  
    }
    return s;
  }

  public String IntToStr(int i) {
    return String.valueOf(i);
  }

  public String LongToStr(long i) {
    return String.valueOf(i);
  }

/*NUMERIC******************************************************/
  public static double Abs(double s) {
    return Math.abs(s);
  }
  
  public static int Abs(int s) {
    return Math.abs(s);
  }
  
  public int Round(double v) {
    return (int)Math.round(v);
  }

  public long RoundLong(double v) {
    return (long)Math.round(v);
  }

  public int StrToInt(String s){
    ErrorCode=0;  
    try  {
      s=CanviText(s,",",".");
      return Round(Double.valueOf(s).doubleValue());
    } catch (Exception e) {
      ErrorCode=1;  
      return 0;
    }    
  }
  
  public long StrToLong(String s){
    ErrorCode=0;  
    try  {
      s=CanviText(s,",",".");
      return RoundLong(Double.valueOf(s).doubleValue());
    } catch (Exception e) {
      ErrorCode=1;  
      return 0;
    }    
  }
  
  public double StrToReal(String s){
    ErrorCode=0;  
    try  {
      s=CanviText(s,",",".");
      return Double.valueOf(s).doubleValue();
    } catch (Exception e) {
      ErrorCode=1;  
      return 0;
    }    
  }
  
  public double StrToFloat(String s){
    ErrorCode=0;  
    try  {
      s=CanviText(s,",",".");
      return Double.valueOf(s).doubleValue();
    } catch (Exception e) {
      ErrorCode=1;  
      return 0;
    }    
  }


/*ARXIUS*******************************************************/
  public static String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');
    if (i > 0 &&  i < s.length() - 1) {
      ext = s.substring(i+1).toLowerCase();
    }
    return ext;
  }

  public static String getExtension(String f) {
    String ext = null;
    String s = f;
    int i = s.lastIndexOf('.');
    if (i > 0 &&  i < s.length() - 1) {
      ext = s.substring(i+1).toLowerCase();
    }
    return ext;
  }

  //http://www.java2s.com/Code/Java/File-Input-Output/CopyfilesusingJavaIOAPI.htm
  public static void FileCopy(String fromFileName, String toFileName) {
    File fromFile = new File(fromFileName);
    File toFile = new File(toFileName);

    if (toFile.exists()) toFile.delete();

    try {
      if (!fromFile.exists())
        throw new IOException("FileCopy: " + "no such source file: "+ fromFileName);
      if (!fromFile.isFile())
        throw new IOException("FileCopy: " + "can't copy directory: "+ fromFileName);
      if (!fromFile.canRead())
        throw new IOException("FileCopy: " + "source file is unreadable: "+ fromFileName);

      if (toFile.isDirectory())
        toFile = new File(toFile, fromFile.getName());

      if (toFile.exists()) {
        if (!toFile.canWrite())
          throw new IOException("FileCopy: "+ "destination file is unwriteable: " + toFileName);
        System.out.print("Overwrite existing file " + toFile.getName()+ "? (Y/N): ");
        System.out.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String response = in.readLine();
        if (!response.equals("Y") && !response.equals("y"))
          throw new IOException("FileCopy: "+ "existing file was not overwritten.");
      } else {
        String parent = toFile.getParent();
        if (parent == null) parent = System.getProperty("user.dir");
        File dir = new File(parent);
        if (!dir.exists())
          throw new IOException("FileCopy: "+ "destination directory doesn't exist: " + parent);
        if (dir.isFile())
          throw new IOException("FileCopy: "+ "destination is not a directory: " + parent);
        if (!dir.canWrite())
          throw new IOException("FileCopy: "+ "destination directory is unwriteable: " + parent);
      }

      FileInputStream from = null;
      FileOutputStream to = null;
      try {
        from = new FileInputStream(fromFile);
        to = new FileOutputStream(toFile);
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = from.read(buffer)) != -1)
          to.write(buffer, 0, bytesRead); // write
      } finally {
        if (from != null)
          try {
            from.close();
          } catch (IOException e) {
            ;
          }
        if (to != null)
          try {
            to.close();
          } catch (IOException e) {
            ;
          }
      }
    } catch ( java.io.IOException e ) {             
    }
  }
  
    
/**************************************************************/
/*END TOOLS****************************************************/
/**************************************************************/

}
