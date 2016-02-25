
public class Ieee754Converter {
	String sign, signifcand;
	int exponent;
	String binaryExponent;
	Ieee754Value singleValue;
	Ieee754Value doubleValue;
	
	public Ieee754Converter(String sign, String signifcand, int exponent) {
		this.sign = sign;
		this.signifcand = signifcand;
		this.exponent = exponent;
	}
	public Ieee754Value convertBinaryToSingle(String input){
		if(exponent!=0||Double.parseDouble(input)>0)
		{
			exponent += 127;
		} else if(Double.parseDouble(input)==0){
			exponent = 0;
		}
		
		//System.out.println(exponent);
		binaryExponent = Integer.toString(exponent, 2);
		//System.out.println(binaryExponent);
		if(binaryExponent.length()<8)
		{
			while(binaryExponent.length()<8)
			{
				binaryExponent="0"+binaryExponent;
			}
		} else if (binaryExponent.length()>8)
		{
			binaryExponent = binaryExponent.substring(0, 8);
		}
		int periodPos = signifcand.indexOf(".");
		signifcand=signifcand.substring(periodPos+1);
		if(signifcand.length()<23)
		{
			while(signifcand.length()<23)
			{
				signifcand+="0";
			}
		} else if(signifcand.length()>23)
		{
			signifcand = signifcand.substring(0,23);
		}
		singleValue = new Ieee754Value(sign,binaryExponent,signifcand);
		return singleValue;
	}
	public Ieee754Value convertBinaryToDouble(String input){
		if(exponent!=0||Double.parseDouble(input)>0)
		{
			exponent += 1023;
		} else if(Double.parseDouble(input)==0){
			exponent = 0;
		}
		binaryExponent = Integer.toBinaryString(exponent);
		if(binaryExponent.length()<11)
		{
			while(binaryExponent.length()<11)
			{
				binaryExponent="0"+binaryExponent;
			}
		} else if (binaryExponent.length()>11)
		{
			binaryExponent = binaryExponent.substring(0, 11);
		}
		int periodPos = signifcand.indexOf(".");
		signifcand=signifcand.substring(periodPos+1);
		if(signifcand.length()<52)
		{
			while(signifcand.length()<52)
			{
				signifcand+="0";
			}
		} else if(signifcand.length()>52)
		{
			signifcand = signifcand.substring(0,52);
		}
		doubleValue = new Ieee754Value(sign,binaryExponent,signifcand);
		return doubleValue;
	}
	public double convertSingleToDecimal(Ieee754Value singleBinary)
	{
		int decimalExponent = (Integer.parseInt(singleBinary.getBinaryExponent(), 2)-127);
		String tempSignifcand;
		if(decimalExponent==-127){
			decimalExponent=0;
			tempSignifcand = "0."+singleBinary.getSignifcand();
		}
		else{
			tempSignifcand = "1."+singleBinary.getSignifcand();
		}
		//System.out.println(decimalExponent);
		
		int periodPos = tempSignifcand.indexOf(".");
		char[] signifcandArray = new char[tempSignifcand.length()];
		signifcandArray = tempSignifcand.toCharArray();
		double decimalSignifcand=0;
		//move right
		if(decimalExponent>0)
		{
			while(decimalExponent!=0)
			{
				signifcandArray[periodPos]=signifcandArray[periodPos+1];
				signifcandArray[periodPos+1]='.';
				periodPos++;
				decimalExponent--;
			}
		}else if (decimalExponent<0){
			//move left
			while(decimalExponent!=0)
			{
				signifcandArray[periodPos]=signifcandArray[periodPos-1];
				signifcandArray[periodPos-1]='.';
				periodPos--;
				decimalExponent++;
			}
		}else if (decimalExponent==0){
			
		}
		String outputBinary="";
		for(int i=0;i<tempSignifcand.length();i++)
		{
			outputBinary = outputBinary+signifcandArray[i];
		}
		
		periodPos = outputBinary.indexOf(".");
		//System.out.println(periodPos+"!!!");
		if(periodPos==0){
			outputBinary="0"+outputBinary;
			periodPos++;
		}
		//System.out.println(outputBinary);
		String wholeOutputBinary = outputBinary.substring(0, periodPos);
		String decimalOutputBinary = outputBinary.substring(periodPos+1);
		//System.out.println(wholeOutputBinary);
		//System.out.println(decimalOutputBinary);
		int temp1=Integer.parseInt(wholeOutputBinary,2);
		int temp2=0;
		double temp3 = 0;
		double temp4 = 0;
		for(int i=0, j=-1;i<decimalOutputBinary.length();i++,j--)
		{
			temp2 = Integer.parseInt(Character.toString(decimalOutputBinary.charAt(i)));
			temp3 = temp2*Math.pow(2, j);
			temp4 = temp4+temp3;
		}
		decimalSignifcand=temp1+temp4;
		if(singleBinary.getSign().equals("0")){
			decimalSignifcand *= 1;
		} else if(singleBinary.getSign().equals("1")){
			decimalSignifcand *= -1;
		}
		return decimalSignifcand;
	}
	public double convertDoubleToDecimal(Ieee754Value doubleBinary)
	{
		double decimalExponent = (Integer.parseInt(doubleBinary.getBinaryExponent(), 2)-1023);
		String tempSignifcand;
		if(decimalExponent==-1023)
		{
			decimalExponent=0;
			tempSignifcand = "0."+doubleBinary.getSignifcand();
		}else{
			tempSignifcand = "1."+doubleBinary.getSignifcand();
		}
		
		int periodPos = tempSignifcand.indexOf(".");
		char[] signifcandArray = new char[tempSignifcand.length()];
		signifcandArray = tempSignifcand.toCharArray();
		double decimalSignifcand=0;
		//move right
		if(decimalExponent>0)
		{
			while(decimalExponent!=0)
			{
				signifcandArray[periodPos]=signifcandArray[periodPos+1];
				signifcandArray[periodPos+1]='.';
				periodPos++;
				decimalExponent--;
			}
		}else if (decimalExponent<0){
			//move left
			while(decimalExponent!=0)
			{
				signifcandArray[periodPos]=signifcandArray[periodPos-1];
				signifcandArray[periodPos-1]='.';
				periodPos--;
				decimalExponent++;
			}

		} else if(decimalExponent==0){
			
		}
		String outputBinary="";
		for(int i=0;i<tempSignifcand.length();i++)
		{
			outputBinary = outputBinary+signifcandArray[i];
		}
		
		periodPos = outputBinary.indexOf(".");
		//System.out.println(periodPos+"!!!");
		if(periodPos==0){
			outputBinary="0"+outputBinary;
			periodPos++;
		}
		//System.out.println(outputBinary);
		String wholeOutputBinary = outputBinary.substring(0, periodPos);
		String decimalOutputBinary = outputBinary.substring(periodPos+1);
		//System.out.println(wholeOutputBinary);
		//System.out.println(decimalOutputBinary);
		int temp1=Integer.parseInt(wholeOutputBinary,2);
		int temp2=0;
		double temp3 = 0;
		double temp4 = 0;
		for(int i=0, j=-1;i<decimalOutputBinary.length();i++,j--)
		{
			temp2 = Integer.parseInt(Character.toString(decimalOutputBinary.charAt(i)));
			temp3 = temp2*Math.pow(2, j);
			temp4 = temp4+temp3;
		}
		decimalSignifcand=temp1+temp4;
		if(doubleBinary.getSign().equals("0")){
			decimalSignifcand *= 1;
		} else if(doubleBinary.getSign().equals("1")){
			decimalSignifcand *= -1;
		}
		return decimalSignifcand;
	}
}
