package org.openintents.plaphoons;

import org.openintents.plaphoons.domain.TalkInfo;
import org.openintents.plaphoons.domain.TalkInfoCollection;
import org.openintents.plaphoons.sample.R;

import android.graphics.Color;

public class SampleTalkCollection {
	static public TalkInfoCollection acceuil = new TalkInfoCollection();
	static public TalkInfoCollection personnes1 = new TalkInfoCollection();
	static public TalkInfoCollection couleurs = new TalkInfoCollection();
	static public TalkInfoCollection boire = new TalkInfoCollection();

	static {
		acceuil.columns = 5;
		acceuil.rows = 3;
		acceuil.setInfos(
				new TalkInfo[] {
						new TalkInfo("Persons", "persons", Color.YELLOW,
								personnes1, R.drawable.personne), //
						new TalkInfo(), //
						new TalkInfo("Colors", "colors",
								Color.BLACK, couleurs, R.drawable.couleurs), //
						new TalkInfo(), //
						new TalkInfo("Drink", "drink", Color.GREEN, boire,
								R.drawable.boire1) }, //


				new TalkInfo[] {
						new TalkInfo("Home", Color.MAGENTA, R.drawable.maison), //
						new TalkInfo("School", "school", Color.MAGENTA, null,
								R.drawable.ecole), //
						new TalkInfo("Swimming", Color.MAGENTA,
								R.drawable.nager1), //
						new TalkInfo("Bag", Color.MAGENTA,
								R.drawable.sac_a_dos), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo("More", Color.BLACK, R.drawable.encore), //
						new TalkInfo("Stop", Color.BLACK, R.drawable.arreter), //
						new TalkInfo("Thank you", Color.BLACK, R.drawable.merci), //
						new TalkInfo(), //
						new TalkInfo("Toilet",
								"I have to go to the toilet",
								Color.BLACK, null, R.drawable.toilette2), //
						new TalkInfo() }

		);

		personnes1.columns = 5;
		personnes1.rows = 3;

		personnes1.setInfos(new TalkInfo[] {
				new TalkInfo("Mam", Color.YELLOW, R.drawable.maman), //
				new TalkInfo("Dad", Color.YELLOW, R.drawable.papa), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo() }, //


				new TalkInfo[] { new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() },

				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

		boire.columns = 5;
		boire.rows = 4;

		boire.setInfos(new TalkInfo[] {
				new TalkInfo("Drink", "I'd like to drink", Color.BLACK, null,
						R.drawable.boire1), //
				new TalkInfo(), //
				new TalkInfo(), //
				new TalkInfo()}, //

				new TalkInfo[] {
						new TalkInfo("Soup", "soup", Color.BLACK, null,
								R.drawable.soupe), //
						new TalkInfo("Water", "water", Color.BLACK, null,
								R.drawable.c), //
						new TalkInfo("Milk", "milk", Color.BLACK, null,
								R.drawable.lait1), //
						new TalkInfo("Sirup", "sirup",
								Color.BLACK, null, R.drawable.koolade), //
						new TalkInfo("Mint", "mint sirup", Color.BLACK,
								null, R.drawable.menthe) //						
						}, //

				new TalkInfo[] {
						new TalkInfo("Strawberry juice", "strawberry juice"), //
						new TalkInfo(), //
						new TalkInfo("Chocolate", " hot chocolate", Color.BLACK, null,
								R.drawable.chocolat_au_lait), //
						new TalkInfo("Orange Juice", "Orange Juice",
								Color.BLACK, null, R.drawable.jus_d_orange), //
						new TalkInfo("Drink Pack", "a drink pack",
								Color.BLACK, null, R.drawable.boite_de_jus)}, //

				new TalkInfo[] {
						
						new TalkInfo("More", "more", Color.BLACK, null,
								R.drawable.encore), //
						new TalkInfo("Stop", "stop", Color.BLACK, null,
								R.drawable.arreter), //
						new TalkInfo("Thank you", "thank you", Color.BLACK, null,
								R.drawable.merci), //
					    new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });	
			


		couleurs.columns = 5;
		couleurs.rows = 3;
		couleurs.setInfos(
				new TalkInfo[] {
						new TalkInfo("Blue", "blue", Color.BLACK, null,
								R.drawable.bleu), //
						new TalkInfo("Red", "red", Color.BLACK, null,
								R.drawable.rouge), //
						new TalkInfo("White", "white", Color.BLACK, null,
								R.drawable.blanc), //
						new TalkInfo(), //
						new TalkInfo() }, //

				new TalkInfo[] {
						new TalkInfo("Rose", "rose", Color.BLACK, null,
								R.drawable.rose), //
						new TalkInfo("Brown", "brown", Color.BLACK, null,
								R.drawable.brun), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo() }, //


				new TalkInfo[] {
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(), //
						new TalkInfo(null, null, Color.BLACK, acceuil,
								R.drawable.retourner) });

	}

}
