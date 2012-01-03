package org.openintents.plaphoons;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.openintents.plaphoons.domain.TalkInfo;
import org.openintents.plaphoons.domain.TalkInfoCollection;

import android.graphics.Color;

public class PlaFileParser {
	// public void CarregarDades(String NomFitxer, String DirAc, String
	// NomFitxerSol){
	Tools tool = new Tools();

	public TalkInfoCollection parseFile(String filePath,  String encoding) throws IOException {		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding));
		
		TalkInfoCollection tiCollection = parseHeader(br);

		TalkInfo talkInfo;
		String line = readLine(br);
		int row = 0;
		int column = 0;
		while (line != null) {

			talkInfo = parseTalkInfo(line);
			if (talkInfo != null) {
				tiCollection.infos[row][column] = talkInfo;
				column++;
				if (column >= tiCollection.columns){
					column = 0;
					row++;
				}
			}
			

			line = readLine(br);
		}
		return tiCollection;
	}

	public TalkInfoCollection parseHeader(BufferedReader r) throws IOException {

		int columnCount;
		int rowCount;
		int error;

		String FoNe = "NO";
		String FoIt = "NO";
		String FoSu = "NO";
		String FoTa = "NO";

		String s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Pos("PLAPHOONS(C)JordiLagaresRoset-ProjecteFRESSA", s) == 0) {
			error = 2;
			return null;
		}

		// get column count
		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.not(tool.Copy(s, 1, 9).equals("COLUMNES="))) {
			error = 6;
			return null;
		}
		s = tool.Delete(s, 1, 9);
		columnCount = tool.StrToInt(s);
		if ((columnCount < 1) || (columnCount > Global.MaximDeCaselles)) {
			error = 7;
			return null;
		}

		// get row count
		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.not(tool.Copy(s, 1, 6).equals("FILES="))) {
			error = 8;
			return null;
		}
		s = tool.Delete(s, 1, 6);
		rowCount = tool.StrToInt(s);
		if ((rowCount < 1) || (rowCount > Global.MaximDeCaselles)) {
			error = 9;
			return null;
		}

		// get color
		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.not(tool.Copy(s, 1, 6).equals("COLOR="))) {
			error = 10;
			return null;
		}
		s = tool.Delete(s, 1, 6);
		int defaultColor = tool.StrToInt(s);
		if ((defaultColor < 0) || (defaultColor > 7)) {
			error = 11;
			return null;
		}

		// center title?

		//
		// Aqui me salto un tros per acceptar fitxers .pla de versions velles
		//
		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 15).equals("CAPTIONCENTRAT=")) {
			s = tool.Delete(s, 1, 15);
			boolean captionCentrat = tool.Copy(s, 1, 1).equals("S");
		} else {
			error = 12;
			return null;
		}

		// get font familiy
		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 12).equals("FontCharset=")) {
			s = tool.Delete(s, 1, 12);
			int fontCharset = tool.StrToInt(s);
			// FontDialog.Font.Charset:=StrToInt(S);
		} else {
			error = 13;
			return null;
		}

		// get font color
		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 10).equals("FontColor=")) {
			s = tool.Delete(s, 1, 10);
			int fontColor = tool.StrToInt(s);
			// FontDialog.Font.Color:=StrToInt(S);
		} else {
			error = 14;
			return null;
		}

		// get font height
		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 11).equals("FontHeight=")) {
			s = tool.Delete(s, 1, 11);
			int fontHeight = tool.StrToInt(s);
			// FontDialog.Font.Height:=StrToInt(S);
		} else {
			error = 15;
			return null;
		}

		// get font name
		s = readLine(r);
		// s=tool.removeSpace(s);
		if (tool.Copy(s, 1, 12).equals("Font Name = ")) {
			s = tool.Delete(s, 1, 12);
			s = tool.CanviText(s, "\r", "");
			s = tool.CanviText(s, "\n", "");
			String fontName = s;
			// FontDialog.Font.Name:=s;
		} else {
			error = 16;
			return null;
		}

		// get font size
		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 9).equals("FontSize=")) {
			s = tool.Delete(s, 1, 9);
			int fontSize = tool.StrToInt(s);
			// FontDialog.Font.Size:=StrToInt(S);
		} else {
			error = 17;
			return null;
		}

		// font bold?
		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 12).equals("FontNegreta=")) {
			s = tool.Delete(s, 1, 12);
			// FontDialog.Font.Style:=[];

			if (tool.Copy(s, 1, 1).equals("S")) {
				FoNe = "S";
			}
		} else {
			error = 18;
			return null;
		}

		// font italic?
		s = readLine(r);
		s = tool.removeSpace(s);
		// if (Copy(s,1,12).equals("FontIt\u00e0lica=")) {
		if (tool.Copy(s, 1, 6).equals("FontIt")) {
			s = tool.Delete(s, 1, 12);
			if (tool.Copy(s, 1, 1).equals("S")) {
				FoIt = "S";
			}
		} else {
			error = 19;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 16).equals("FontSubrratllat=")) {
			s = tool.Delete(s, 1, 16);
			if (tool.Copy(s, 1, 1).equals("S")) {
				FoSu = "S";
			}
		} else {
			error = 20;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 11).equals("FontTatxat=")) {
			s = tool.Delete(s, 1, 11);
			if (tool.Copy(s, 1, 1).equals("S")) {
				FoTa = "S";
			}
		} else {
			error = 21;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 12).equals("BARRABOTONS=")) {
			s = tool.Delete(s, 1, 12);
			boolean barrabotons = tool.Copy(s, 1, 1).equals("S");

		} else {
			error = 22;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 14).equals("BARRAMISSATGE=")) {
			s = tool.Delete(s, 1, 14);
			if (tool.Copy(s, 1, 1).equals("S")) {
				boolean barramissatage = true;
			}
		} else {
			error = 23;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 9).equals("WORDWRAP=")) {
			s = tool.Delete(s, 1, 9);
			/*
			 * if (tool.Copy(s,1,1).equals("S")) { boolean wordWrap = true;
			 */
		} else {
			error = 24;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 3, 11).equals("MESLLETRES=")) {
			s = tool.Delete(s, 1, 13);

			String NomFitxerInicial = "";

			if (NomFitxerInicial == "") {

				boolean meslLetres = tool.Copy(s, 1, 1).equals("S");

			}
		} else {
			error = 25;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 1, 22).equals("ENVIARFINESTRAEXTERNA=")) {
			s = tool.Delete(s, 1, 22);
			boolean enviaRefineStraExterna = tool.Copy(s, 1, 1).equals("S");
		} else {
			error = 26;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 8, 5).equals("LEFT=")) {
			s = tool.Delete(s, 1, 12);
			int POSICILEFTNou = tool.StrToInt(s);
			// this.setLocation(StrToInt(s),this.getY());
		} else {
			error = 27;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 8, 6).equals("WIDTH=")) {
			s = tool.Delete(s, 1, 13);
			int POSICIOWIDTHNou = tool.StrToInt(s);
			// this.setSize(StrToInt(s),this.getHeight());
		} else {
			error = 28;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 8, 4).equals("TOP=")) {
			s = tool.Delete(s, 1, 11);
			int POSICIOTOPNou = tool.StrToInt(s);
			// this.setLocation(this.getX(),StrToInt(s));
		} else {
			error = 29;
			return null;
		}

		s = readLine(r);
		s = tool.removeSpace(s);
		if (tool.Copy(s, 8, 7).equals("HEIGHT=")) {
			s = tool.Delete(s, 1, 14);
			int POSICIOHEIGHTNou = tool.StrToInt(s);
			// this.setSize(this.getWidth(),StrToInt(s));
		} else {
			error = 30;
			return null;
		}

		return new TalkInfoCollection(rowCount, columnCount);
	}

	private String readLine(BufferedReader r) throws IOException {
		String line = r.readLine();
		while (line != null && line.startsWith("//")) {
			line = r.readLine();
		}
		return line;
	}

	public TalkInfo parseTalkInfo(String s) {
		int error;
		TalkInfo talkInfo = new TalkInfo();

		// get image
		int n = tool.Pos("PICTURE=", s);
		if (n > 0) {
			// s=Delete(s,1,n+8);
			s = tool.Delete(s, n, 9);
		} else {
			error = 30;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			talkInfo.drawablePath = tool.Copy(s, 1, n - 1);
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 31;
			return null;
		}

		// get title
		n = tool.Pos("CAPTION=", s);
		if (n > 0) {
			s = tool.Delete(s, n, 9);
		} else {
			error = 32;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			talkInfo.title = tool.Copy(s, 1, n - 1);
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 32;
			return null;
		}

		// get text to read out
		n = tool.Pos("READ=", s);
		if (n > 0) {
			s = tool.Delete(s, n, 6);
		} else {
			error = 33;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			talkInfo.text = tool.Copy(s, 1, n - 1);
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 33;
			return null;
		}

		// get link
		n = tool.Pos("LINK=", s);
		if (n > 0) {
			s = tool.Delete(s, n, 6);
		} else {
			error = 34;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			talkInfo.childFilename = tool.Copy(s, 1, n - 1);
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 34;
			return null;
		}

		// get color of frame
		n = tool.Pos("MC=", s);
		if (n > 0) {
			s = tool.Delete(s, n, 4);
		} else {
			error = 35;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			int NumeroColorMarcs = tool.StrToInt(tool.Copy(s, 1, n - 1));
			if (NumeroColorMarcs == 0) {
				talkInfo.color = Color.BLACK;
			} else if (NumeroColorMarcs == 1) {
				talkInfo.color = Color.rgb(255, 0, 255);
			} else if (NumeroColorMarcs == 2) {
				talkInfo.color = Color.YELLOW;
			} else if (NumeroColorMarcs == 3) {
				talkInfo.color = Color.GREEN;
			} else if (NumeroColorMarcs == 4) {
				talkInfo.color = Color.RED; // was orange
			} else if (NumeroColorMarcs == 5) {
				talkInfo.color = Color.BLUE;
			} else if (NumeroColorMarcs == 6) {
				talkInfo.color = Color.WHITE;
			}
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 35;
			return null;
		}

		// get color of font
		n = tool.Pos("FCOLOR=", s);
		if (n > 0) {
			s = tool.Delete(s, 1, n + 7);
		} else {
			error = 36;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			// TODO handle font color; not yet supported
			// talkInfo.fontcolor =tool.StrToInt(tool.Copy(s,1,n-1));
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 36;
			return null;
		}

		// get font name
		n = tool.Pos("FNAME=", s);
		if (n > 0) {
			s = tool.Delete(s, 1, n + 6);
		} else {
			error = 37;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			// TODO handle font name; not yet supported
			// talkInfo.fontName=tool.Copy(s,1,n-1);
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 37;
			return null;
		}

		// get font size
		n = tool.Pos("FSIZE=", s);
		if (n > 0) {
			s = tool.Delete(s, 1, n + 6);
		} else {
			error = 38;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			// TODO handle font size; not yet supported
			// talkInfo.fontSize=tool.StrToInt(tool.Copy(s,1,n-1));
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 38;
			return null;
		}

		int fontStyle = 0;
		n = tool.Pos("FNEGRETA=", s);
		if (n > 0) {
			s = tool.Delete(s, 1, n + 9);
		} else {
			error = 39;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			if (tool.Copy(s, 1, 1).equals("S")) {
				// fontStyle += Font.Style.BOLD;
			}
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 39;
			return null;
		}
		n = tool.Pos("FITALICA=", s);
		if (n > 0) {
			s = tool.Delete(s, 1, n + 9);
		} else {
			error = 40;
			return null;
		}
		n = tool.Pos("\"", s);
		if (n > 0) {
			if (tool.Copy(s, 1, 1).equals("S")) {
				// fontStyle += Font.Style.ITALIC;
			}
			s = tool.Delete(s, 1, n + 1);
		} else {
			error = 40;
			return null;
		}
		return talkInfo;
	}
}
