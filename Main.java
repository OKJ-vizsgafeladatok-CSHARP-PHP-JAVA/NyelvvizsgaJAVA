import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	public static List<Nyelvvizsga> beolvas(String fajlnev) {
		List<Nyelvvizsga> lista = new ArrayList<Nyelvvizsga>();
		try {
			List<String> sorok = Files.readAllLines(Paths.get(fajlnev));
			for (String sor : sorok.subList(1, sorok.size())) {
				String[] split = sor.split(";");
				List<Integer> evek = new ArrayList<Integer>();
				for (int i = 1; i < split.length; i++) {
					evek.add(Integer.parseInt((split[i])));
				}
				Nyelvvizsga o = new Nyelvvizsga(split[0], evek);
				lista.add(o);
			}
		} catch (Exception e) {
			System.out.println("Hiba a fájl beolvasásakor. " + fajlnev);
		}
		return lista;
	}

	public static TreeMap<String, Integer> rendez(Map<String, Integer> lista) {
		TreeMap<String, Integer> rendezett = new TreeMap<String, Integer>(new Comparator<String>() {
			public int compare(String o1, String o2) {
				int compare = lista.get(o2).compareTo(lista.get(o1));
				if (compare == 0) {
					return 1;
				} else {
					return compare;
				}
			}
		});

		rendezett.putAll(lista);
		return rendezett;
	}
	
	public static TreeMap<String,Double> rendezD(Map<String,Double> lista){
		TreeMap<String,Double> rendezett=new TreeMap<String,Double>(new Comparator<String>() {
			public int compare(String o1,String o2) {
				int compare=lista.get(o2).compareTo(lista.get(o1));
				if(compare==0) {
					return 1;
				}else {
					return compare;
				}
			}
		});
		rendezett.putAll(lista);
		return rendezett;
	}

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		List<Nyelvvizsga> a = beolvas("sikeres.csv");
		List<Nyelvvizsga> b = beolvas("sikertelen.csv");

		TreeMap<String, Integer> c = new TreeMap<String, Integer>();
		for (Nyelvvizsga sikeres : a) {
			for (Nyelvvizsga sikertelen : b) {
				if (sikeres.getNyelv().equals(sikertelen.getNyelv())) {
					c.put(sikertelen.getNyelv(), (sikeres.sumAllYear() + sikertelen.sumAllYear()));
				}
			}
		}
		c = rendez(c);
//		c.forEach((k,v)->{System.out.println(k+"->"+v);});
		System.out.println("2. feladat: A legnépszerűbb nyelvek:");
		int szamol = 0;
		for (Entry<String, Integer> o : c.entrySet()) {
			if (szamol < 3) {
				System.out.println("\t" + o.getKey());
			}
			szamol++;
		}
		szamol = 0;
		// 3.feladat:
		System.out.print("3. feladat:");
		int beker = 0;
		do {
			System.out.println("A vizsgálandó év: ");
			beker = sc.nextInt();

		} while ((beker < 2009 || beker > 2017));

		TreeMap<String, Double> d = new TreeMap<String, Double>();
		List<String> nullasok=new ArrayList<String>();
		double arany = 0;
		int s=0;
		int t=0;
		for (int i = 0; i < a.size(); i++) {
			s += a.get(i).getEvek().get(beker - 2009);
			t += b.get(i).getEvek().get(beker - 2009);
			if(s+t==0) {
				arany=0;
				nullasok.add(a.get(i).getNyelv());
			}else {
				arany = (t / ((double) s+t)) * 100;
//				System.out.println();
			}
			d.put(a.get(i).getNyelv(), arany);
			arany = 0;
			s=0;
			t=0;
		}

		d=rendezD(d);
		System.out.println("4.feladat:");
		System.out.println(beker + "-ben " + d.firstKey() + " nyelvből a sikertelen vizsgázók aránya: "
				+ new DecimalFormat("0.00").format(d.firstEntry().getValue()));

		System.out.println("5. feladat: ");
		for (String st : nullasok) {
			System.out.println("\t"+st);
		}
		
		System.out.println("6. feladat: A fájlt létrehoztam. ");
		String fajlba="";
		Double arany2=0.0;
		for (int i = 0; i < a.size(); i++) {
			fajlba+=a.get(i).getNyelv()+";";
			fajlba+=a.get(i).sumAllYear()+b.get(i).sumAllYear()+";";
			arany2=(a.get(i).sumAllYear()/(a.get(i).sumAllYear()+(double)b.get(i).sumAllYear()))*100;
			fajlba+=new DecimalFormat("0.00").format(arany2)+"%\n";
			arany2=0.0;
		}
		
		Files.write(Paths.get("osszesites.csv"),fajlba.getBytes());
	}// end of main
}
