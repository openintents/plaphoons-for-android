package org.openintents.plaphoons;

import org.openintents.plaphoons.domain.TalkInfo;
import org.openintents.plaphoons.domain.TalkInfoCollection;
import org.openintents.plaphoons.sample.R;

import android.graphics.Color;

public class SampleTalkCollection {
	static public TalkInfoCollection acceuil = new TalkInfoCollection();
	static public TalkInfoCollection personnes1 = new TalkInfoCollection();
	static public TalkInfoCollection meteo = new TalkInfoCollection();
	static public TalkInfoCollection couleurs = new TalkInfoCollection();
	static public TalkInfoCollection personnes2 = new TalkInfoCollection();
	static public TalkInfoCollection classe = new TalkInfoCollection();
	static public TalkInfoCollection vetements = new TalkInfoCollection();
	static public TalkInfoCollection manger = new TalkInfoCollection();
	static public TalkInfoCollection boire = new TalkInfoCollection();
	static public TalkInfoCollection jouer = new TalkInfoCollection();
	static public TalkInfoCollection therapeutes = new TalkInfoCollection();

	static public final TalkInfoCollection back = new TalkInfoCollection();

	static {
		acceuil.setInfos(
				new TalkInfo[] {
						new TalkInfo("Personnes", "personnes", Color.YELLOW,
								personnes1, R.drawable.personne), //
						new TalkInfo(), //
						new TalkInfo("Les Couleurs", "les couleurs",
								Color.BLACK, couleurs, R.drawable.couleurs), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo("Boire", "boire", Color.GREEN, boire,
								R.drawable.boire1) }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() }, //

				new TalkInfo[] {
						new TalkInfo("Maison", Color.MAGENTA, R.drawable.maison), //
						new TalkInfo("Ecole", "école", Color.MAGENTA, null,
								R.drawable.ecole), //
						new TalkInfo("Piscine", Color.MAGENTA,
								R.drawable.nager1), //
						new TalkInfo("Cartable", Color.MAGENTA,
								R.drawable.sac_a_dos), //
						new TalkInfo("Cahier de Communication", Color.MAGENTA,
								R.drawable.tableau_de_communication), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo("Encore", Color.BLACK, R.drawable.encore), //
						new TalkInfo("Stop", Color.BLACK, R.drawable.arreter), //
						new TalkInfo("Merci", Color.BLACK, R.drawable.merci), //
						new TalkInfo("Casque", Color.BLACK, R.drawable.casque), //
						new TalkInfo("Toilette",
								"Je voudrais aller à  la toilette",
								Color.BLACK, null, R.drawable.toilette2), //
						new TalkInfo() }

		);

		personnes1.setInfos(new TalkInfo[] {
				new TalkInfo("Maman", Color.YELLOW, R.drawable.maman), //
				new TalkInfo("Papa", Color.YELLOW, R.drawable.papa), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

		boire.setInfos(new TalkInfo[] {
				new TalkInfo("Boire", "Je voudrais boire", Color.BLACK, null,
						R.drawable.boire1), //
				new TalkInfo("Jus à  la Fraise", "un jus Ã  la Fraise"), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo("Je veux", "je veux", Color.BLUE, null,
						R.drawable.je_veux) }, //

				new TalkInfo[] {
						new TalkInfo("Soupe", "de la Soupe", Color.BLACK, null,
								R.drawable.soupe), //
						new TalkInfo("Eau", "de l'Eaux", Color.BLACK, null,
								R.drawable.c), //
						new TalkInfo("Lait", "du lait", Color.BLACK, null,
								R.drawable.lait1), //
						new TalkInfo("Grenadine", "de la Grenadine",
								Color.BLACK, null, R.drawable.koolade), //
						new TalkInfo("Menthe", "de la Menthe", Color.BLACK,
								null, R.drawable.menthe), //
						new TalkInfo() }, //

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo("Cacao", " Du Cacao", Color.BLACK, null,
								R.drawable.chocolat_au_lait), //
						new TalkInfo("Jus d'Orange", "Du Jus d'Orange",
								Color.BLACK, null, R.drawable.jus_d_orange), //
						new TalkInfo("Boite de Jus", "un petit jus",
								Color.BLACK, null, R.drawable.boite_de_jus), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo("Encore", "encore", Color.BLACK, null,
								R.drawable.encore), //
						new TalkInfo("Stop", "stop", Color.BLACK, null,
								R.drawable.arreter), //
						new TalkInfo("Merci", "merci", Color.BLACK, null,
								R.drawable.merci), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

		vetements.setInfos(new TalkInfo[] { new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

		manger.setInfos(new TalkInfo[] { new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

		jouer.setInfos(new TalkInfo[] { new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

		meteo.setInfos(new TalkInfo[] { new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

		couleurs.setInfos(
				new TalkInfo[] {
						new TalkInfo("Bleu", "bleu", Color.BLACK, null,
								R.drawable.bleu), //
						new TalkInfo("Rouge", "rouge", Color.BLACK, null,
								R.drawable.rouge), //
						new TalkInfo("Blanc", "blanc", Color.BLACK, null,
								R.drawable.blanc), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() }, //

				new TalkInfo[] {
						new TalkInfo("Rose", "rose", Color.BLACK, null,
								R.drawable.rose), //
						new TalkInfo("Brun", "brun", Color.BLACK, null,
								R.drawable.brun), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

	}

}
