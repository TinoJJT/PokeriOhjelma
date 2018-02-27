package pokeri;

import java.util.Arrays;

public class PokeriPeli {
	/*Korttipakkaan tulee jokaisen kortin ohjelma-arvo, josta myˆhemmin jaetaan kortit kolmeen eri k‰teen
	 *Ohelmassani arvo 0 vastaa korttia Pata2, ja siit‰ ylˆsp‰in menn‰‰n Pata10:iin, ja sitten Pata J, Q, K ja A
	 *PataA (arvo 12) j‰lk‰inen arvo on Risti2 josta jatketaan samalla logiikalla eteenp‰in.
	 *Ristin j‰lkeen Ruutu ja lopuksi hertta. HerttaA ohjelma-arvo on siis 51.
	 */
	int[] korttiPakka = new int[52];
	int[] kasi1 = new int[5];
	int[] kasi2 = new int[5];
	int[] kasi3 = new int[5];
	
	public PokeriPeli() {
		sekoitaPakka();
		jaaKortit();
		tulostaKadet();
	}
	
	// T‰m‰ metodi antaa korttiPakka taulukolle jokaiiseen lokeroon oman satunnais luvun v‰lilt‰ 0-51
	private void sekoitaPakka(){
		//kaytetyKortit taulukolla seurataan mitk‰ kortit on jo laitettu pakkaan.
		boolean[] kaytetytKortit = new boolean[52];
		int satunnaisluku;
		for(int i = 52; i > 0; i--) {
			int kortti = 0;
			
			//T‰ll‰ while komennolla hyp‰t‰‰n k‰ytettyjen korttien yli.
			while(kaytetytKortit[kortti]==true) kortti++;
			/*Satunnais luvuksi maksimissaan niin iso luku kuin valitsemattomia kortteja j‰ljell‰
			 *T‰ll‰ tavalla taataan ett‰ jokaisella kortilla on yht‰ iso mahdollisuus tulla seuraavaksi.
			 */
			satunnaisluku = (int) (Math.random()*i);
			
			//Kone valitsee kortin j‰ljell‰ ovista korteista
			for(int j = 0; j < satunnaisluku; j++) {
				kortti++;
				while(kaytetytKortit[kortti]==true) kortti++;
			}
			
			//Satunnainen kortti asetetaan pakan pohjalle, ja merkit‰‰n ett‰ t‰m‰ arvo on nyt k‰ytetty.
			korttiPakka[i-1] = kortti;
			kaytetytKortit[kortti] = true;
		}
	}
	
	//Jakaa kortit pakasta k‰siin standardi korttipeli jako perusteella.
	private void jaaKortit() {
		for(int i = 0; i < 5; i++) {
			kasi1[i] = korttiPakka[0+(i*3)];
			kasi2[i] = korttiPakka[1+(i*3)];
			kasi3[i] = korttiPakka[2+(i*3)];
		}
	}
	
	//Tulostaa k‰sien kortit sek‰ mahdollisen pokerik‰den.
	private void tulostaKadet() {
		System.out.println("Ensimm‰isen k‰den kortit ovat:");
		for(int i = 0; i < 5; i++) {
			System.out.print(tunnistaKortti(kasi1[i]) + " ");
		}
		System.out.println("\n" + tunnistaKasi(kasi1) + "\n");
		
		System.out.println("Toisen k‰den kortit ovat:");
		for(int i = 0; i < 5; i++) {
			System.out.print(tunnistaKortti(kasi2[i]) + " ");
		}
		System.out.println("\n" + tunnistaKasi(kasi2) + "\n");
		
		System.out.println("Kolmannen k‰den kortit ovat:");
		for(int i = 0; i < 5; i++) {
			System.out.print(tunnistaKortti(kasi3[i]) + " ");
		}
		System.out.println("\n" + tunnistaKasi(kasi3) + "\n");
	}
	
	//Muuttaa kortin ohjelma-arvon kirjalliseksi arvoksi alussa annetun s‰‰nnˆn mukaisesti.
	private String tunnistaKortti(int kortinArvo) {
		String kortti = "";
		int kortinMaa = kortinArvo/13;
		if(kortinMaa == 0) kortti += "Pata";
		else if(kortinMaa == 1) kortti += "Risti";
		else if(kortinMaa == 2) kortti += "Ruutu";
		else if(kortinMaa == 3) kortti += "Hertta";
		
		int kortinNumero = kortinArvo%13;
		if(kortinNumero < 9) kortti += kortinNumero+2;
		else if(kortinNumero == 9) kortti += "J";
		else if(kortinNumero == 10) kortti += "Q";
		else if(kortinNumero == 11) kortti += "K";
		else if(kortinNumero == 12) kortti += "A";
		
		return kortti;
	}
	
	//K‰y l‰pi mahdolliset pokerik‰det. Aluksi kaksi paria sill‰ se est‰‰ muiden pokerik‰sien mahdollisuuden.
	private String tunnistaKasi(int[] kasi){
		if(tunnistaKaksiParia(kasi)) return "Kaksi paria";
		else if(tunnistaSuora(kasi) && tunnistaVari(kasi)) return "V‰risuora";
		else if(tunnistaSuora(kasi)) return "Suora";
		else if(tunnistaVari(kasi)) return "V‰ri";
		
		return "";
	}
	
	
	private boolean tunnistaKaksiParia(int[] kasi) {
		//Cloonataan annettu k‰si tilap‰iseen k‰teen, jotta ei muuteta alkuper‰isen k‰den arvoja.
		int[] temp = kasi.clone();
		int pareja = 0;
		
		//T‰m‰ toiminto saa eri maiden kortit n‰ytt‰m‰‰n samalta.
		for(int i = 0; i < 5; i++) {
			temp[i] = temp[i]%13;
		}
		//J‰rjestet‰‰n k‰si jotta mahdolliset parit ovat vierekk‰in
		Arrays.sort(temp);
		
		//Laskee monta paria k‰dess‰ on
		for(int i = 0; i < 4; i++) {
			if(temp[i] == temp[i+1]) {
				pareja++;
				//T‰ss‰ i++ jotta kolmosia ei tunnistetaisi kahdeksi pariksi
				i++;
			}
		}
		if(pareja == 2) return true;
		else return false;
	}
	
	private boolean tunnistaSuora(int[] kasi) {
		int[] temp = kasi.clone();
		
		//T‰m‰ toiminto saa eri maiden kortit n‰ytt‰m‰‰n samalta.
		for(int i = 0; i < 5; i++) {
			temp[i] = temp[i]%13;
		}
		
		//Suora helpompi tarkistaa jos kortit on j‰rjestyksess‰
		Arrays.sort(temp);
		for(int i = 0; i < 4; i++) {
			if(temp[i+1] - temp[i] != 1) return false;
		}
		return true;
	}
	
	private boolean tunnistaVari(int[] kasi) {
		int[] temp = kasi.clone();
		Arrays.sort(temp);
		
		/*J‰tt‰‰ j‰ljelle vain kortin maan. Jos kortti jolla on isoin ohjelma arvo ja
		 * kortti jolla on pienin ohjelma-arvo on samaa maata, niin kaikki v‰liss‰kin on
		 */
		if(temp[4]/13 == temp[0]/13) return true;
		else return false;
	}
}
