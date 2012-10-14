package org.openintents.plaphoons.domain;

public class TalkInfoCollection {
	public int rows = 4;
	public int columns = 6;
	public TalkInfo[][] infos;
	public boolean showTextBox = true;

	public TalkInfoCollection(TalkInfo[]... infos) {
		this.infos = infos;
	}

	public TalkInfoCollection(int rowCount, int columnCount, boolean showTextBox) {
		rows = rowCount;
		columns = columnCount;
		this.showTextBox = showTextBox;
		infos = new TalkInfo[rows][columns];
	}

	public void setInfos(TalkInfo[]... infos) {
		this.infos = infos;
	}

}
