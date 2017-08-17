package ec.gob.stptv.formularioManuelas.controlador.util;


/**
 * Created by lmorales on 14/07/17.
 */
public class Values {
	private String key;
	private String value;

	public Values() {
		super();
	}
	
	
	@Override
	public String toString() {
		return value;
	}

	public Values(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public Values(int key, String value) {
		super();
		this.key = String.valueOf(key);
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
