import java.util.Scanner;
//1.0
//0.5
//19.5
//-3.75
//0

public class DecimalConverter {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String decimal;
		boolean noPeriod;
		String sign = null;
		int numberWhole = 0; 
		int periodPos = 0;
		System.out.println("Enter decimal Number:");
		decimal = input.nextLine();
		if(Double.parseDouble(decimal)>=0)
		{
			sign="0";
		} else if(Double.parseDouble(decimal)<0)
		{
			sign="1";
			int dashPos = decimal.indexOf('-');
			decimal = decimal.substring(dashPos+1);
		}
		
		if(decimal.contains(".")){
			periodPos = decimal.indexOf(".");
			System.out.println(periodPos);
			noPeriod = false;
		} else {
			noPeriod = true;
		}
		if(periodPos!=0)
		{
			numberWhole = Integer.parseInt(decimal.substring(0, periodPos));
		} else if(periodPos==0)
		{
			numberWhole = 0;
		}
		double numberDecimal = 0;
		if(noPeriod==false){
			numberDecimal = Double.parseDouble(decimal.substring(periodPos));
		}
		

		System.out.println(numberWhole+"  .  "+numberDecimal);
		String binary = Integer.toBinaryString(numberWhole)+".";

		double tempDec = numberDecimal;
		while(tempDec>0)
		{
			double number = tempDec*2;
			if(number>=1)
			{
				binary += "1";
				tempDec = number-1;
			}else{
				binary += "0";
				tempDec = number;
			}
		}
		System.out.println(binary);
		char[] binaryArray = new char[binary.length()];
		if(binary.charAt(0)=='0'){
			binary=binary.substring(periodPos);
		}
		binaryArray=binary.toCharArray();
		int exponent = 0;
		periodPos = binary.indexOf(".");
		//move right
		if(numberWhole==0)
		{
			while(binaryArray[1]!='.')
			{
				binaryArray[periodPos]=binaryArray[periodPos+1];
				binaryArray[periodPos+1]='.';
				periodPos++;
				exponent--;
				System.out.println(binaryArray[periodPos]);
			}
				
			
		}else{
			//move left
			while(binaryArray[1]!='.'){
				binaryArray[periodPos]=binaryArray[periodPos-1];
				binaryArray[periodPos-1]='.';
				periodPos--;
				exponent++;
			}

		}
		String outputBinary="";
		for(int i=0;i<binary.length();i++)
		{
			outputBinary = outputBinary+binaryArray[i];
		}
		System.out.println(outputBinary);
		System.out.println(exponent);
		
		Ieee754Converter convert = new Ieee754Converter(sign,outputBinary,exponent);
		Ieee754Value singleBinary = convert.convertBinaryToSingle(decimal);
		System.out.println(singleBinary);
		convert = new Ieee754Converter(sign,outputBinary,exponent);
		Ieee754Value doubleBinary = convert.convertBinaryToDouble(decimal);
		System.out.println(doubleBinary);
		double singleToDecimal = convert.convertSingleToDecimal(singleBinary);
		System.out.println(singleToDecimal);
		double doubleToDecimal = convert.convertDoubleToDecimal(doubleBinary);
		System.out.println(doubleToDecimal);
		


















			input.close();
	}
}
