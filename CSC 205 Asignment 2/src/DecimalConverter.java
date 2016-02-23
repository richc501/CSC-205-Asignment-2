import java.util.Scanner;


public class DecimalConverter {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String decimal;
		String sign = null;
		int numberWhole = 0; 
		System.out.println("Enter decimal Number:");
		decimal = input.nextLine();
		if(Double.parseDouble(decimal)>=0)
		{
			sign="0";
		} else if(Double.parseDouble(decimal)<0)
		{
			sign="1";
		}
		int periodPos = decimal.indexOf(".");
		if(periodPos!=0)
		{
			numberWhole = Integer.parseInt(decimal.substring(0, periodPos));
		} else if(periodPos==0)
		{
			numberWhole = 0;
		}
		double numberDecimal = Double.parseDouble(decimal.substring(periodPos));

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
		Ieee754Value singleBinary = convert.convertBinaryToSingle();
		System.out.println(singleBinary);
		convert = new Ieee754Converter(sign,outputBinary,exponent);
		Ieee754Value doubleBinary = convert.convertBinaryToDouble();
		System.out.println(doubleBinary);
		double singleToDecimal = convert.convertSingleToDecimal(singleBinary);
		System.out.println(singleToDecimal);
		double doubleToDecimal = convert.convertDoubleToDecimal(doubleBinary);
		System.out.println(doubleToDecimal);
		


















			input.close();
	}
}
