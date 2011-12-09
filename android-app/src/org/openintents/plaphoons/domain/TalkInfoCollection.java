package org.openintents.plaphoons.domain;


public class TalkInfoCollection {
	public int rows = 4;
	public int columns = 6;
	public TalkInfo[][] infos;


	public TalkInfoCollection(TalkInfo[]... infos) {
		this.infos = infos;
	}


	public TalkInfoCollection(int rowCount, int columnCount) {
		rows = rowCount;
		columns = columnCount;
		infos = new TalkInfo[rows][columns];
	}


	public void setInfos(TalkInfo[] ...infos ) {
		this.infos = infos;		
	}


}
