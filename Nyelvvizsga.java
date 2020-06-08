import java.util.List;

public class Nyelvvizsga {
	
	private String nyelv;
	private List<Integer> evek;
	public Nyelvvizsga(String nyelv, List<Integer> evek) {
		super();
		this.nyelv = nyelv;
		this.evek = evek;
	}
	public String getNyelv() {
		return nyelv;
	}
	public void setNyelv(String nyelv) {
		this.nyelv = nyelv;
	}
	public List<Integer> getEvek() {
		return evek;
	}
	public void setEvek(List<Integer> evek) {
		this.evek = evek;
	}
	@Override
	public String toString() {
		return "Nyelvvizsga [nyelv=" + nyelv + ", evek=" + evek + "]";
	}
	public int sumAllYear() {
		int sum=0;
		for (Integer integer : evek) {
			sum+=integer;
		}
		return sum;
	}
}
