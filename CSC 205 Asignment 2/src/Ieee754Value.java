
public class Ieee754Value {
	String sign, signifcand;
	String binaryExponent;
	public Ieee754Value(String sign, String binaryExponent,String signifcand) {
		this.sign = sign;
		this.signifcand = signifcand;
		this.binaryExponent = binaryExponent;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSignifcand() {
		return signifcand;
	}
	public void setSignifcand(String signifcand) {
		this.signifcand = signifcand;
	}
	public String getBinaryExponent() {
		return binaryExponent;
	}
	public void setBinaryExponent(String binaryExponent) {
		this.binaryExponent = binaryExponent;
	}
	public String toString() {
		return sign + binaryExponent +signifcand;
	}
	
	
}
