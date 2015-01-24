package org.openintents.plaphoons;

import org.openintents.plaphoons.domain.TalkInfo;
import org.openintents.plaphoons.domain.TalkInfoCollection;
import org.openintents.plaphoons.sample.R;

import android.graphics.Color;

public class SampleTalkCollectionPhone {
	static public TalkInfoCollection acceuil = new TalkInfoCollection();
	static public TalkInfoCollection personnes1 = new TalkInfoCollection();
	static public TalkInfoCollection couleurs = new TalkInfoCollection();
	static public TalkInfoCollection boire = new TalkInfoCollection();
	static public TalkInfoCollection activites = new TalkInfoCollection();

	static {
		acceuil.columns = 2;
		acceuil.rows = 3;
		acceuil.setInfos(
				new TalkInfo[] {
						new TalkInfo("Persons", "persons", Color.YELLOW,
								personnes1, R.drawable.personne), //
						new TalkInfo("Colors", "colors", Color.BLACK, couleurs,
								R.drawable.couleurs) }, //

				new TalkInfo[] {
						new TalkInfo(),//
						new TalkInfo("Drink", "drink", Color.GREEN, boire,
								R.drawable.boire1),//
				},

				new TalkInfo[] {
						new TalkInfo("Thank you", Color.BLACK, R.drawable.merci), //						
						new TalkInfo("Toilet", "I have to go to the toilet",
								Color.BLACK, null, R.drawable.toilette2), //
						}

		);

		personnes1.columns = 3;
		personnes1.rows = 3;

		personnes1.setInfos(new TalkInfo[] {
				new TalkInfo("Mam", "with mam", Color.YELLOW, null, R.drawable.maman), //
				new TalkInfo("Dad", "with dad", Color.YELLOW, null, R.drawable.papa), //								
				new TalkInfo() }, //

				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //						
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //						
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });	

				
		boire.columns = 3;
		boire.rows = 3;

		boire.setInfos(
				new TalkInfo[] {
						new TalkInfo("Drink", "I'd like to drink", Color.BLACK,
								null, R.drawable.boire1), //
						new TalkInfo("Strawberry juice", "strawberry juice"), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo("I'd like", "I'd like", Color.BLUE, null,
								R.drawable.je_veux) }, //

				new TalkInfo[] {
						new TalkInfo("Soup", "soup", Color.BLACK, null,
								R.drawable.soupe), //
						new TalkInfo("Water", "water", Color.BLACK, null,
								R.drawable.c), //
						new TalkInfo("Milk", "milk", Color.BLACK, null,
								R.drawable.lait1), //
						new TalkInfo("Sirup", "sirup", Color.BLACK, null,
								R.drawable.koolade), //
						new TalkInfo("Mint", "mint sirup", Color.BLACK, null,
								R.drawable.menthe) //
				}, //

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo("Chocolate", " hot chocolate",
								Color.BLACK, null, R.drawable.chocolat_au_lait), //
						new TalkInfo("Orange Juice", "Orange Juice",
								Color.BLACK, null, R.drawable.jus_d_orange), //
						new TalkInfo("Drink Pack", "drink pack", Color.BLACK,
								null, R.drawable.boite_de_jus) }, //

				new TalkInfo[] {

						new TalkInfo("More", "more", Color.BLACK, null,
								R.drawable.encore), //
						new TalkInfo("Stop", "stop", Color.BLACK, null,
								R.drawable.arreter), //
						new TalkInfo("Thank you", "thank you", Color.BLACK,
								null, R.drawable.merci), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

		couleurs.columns = 3;
		couleurs.rows = 2;
		couleurs.setInfos(
				new TalkInfo[] {
						new TalkInfo("Blue", "blue", Color.BLACK, null,
								R.drawable.bleu), //
						new TalkInfo("Red", "red", Color.BLACK, null,
								R.drawable.rouge), //
						new TalkInfo("White", "white", Color.BLACK, null,
								R.drawable.blanc)}, //

				new TalkInfo[] {
						new TalkInfo("Rose", "rose", Color.BLACK, null,
								R.drawable.rose), //
						new TalkInfo("Brown", "brown", Color.BLACK, null,
								R.drawable.brun),			
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

	}

}
