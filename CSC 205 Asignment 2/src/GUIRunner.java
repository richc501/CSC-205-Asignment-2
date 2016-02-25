import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
//1.0
//0.5
//19.5
//-3.75
//0
public class GUIRunner extends Application{
	Ieee754Converter toSinglePercision;
	Ieee754Converter toDoublePercision;
	Ieee754Converter convert;
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage primaryStage) throws Exception {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		primaryStage.setTitle("IEEE 754 Converter");
		Label inputDecimal1 = new Label("Input Decimal:");
		Label inputDecimal2 = new Label("Input Decimal:");
		Label inputSingle = new Label("Input Single:");
		Label inputDouble = new Label("Input Double:");
		final TextField userTextField1 = new TextField();
		final TextField userTextField2 = new TextField();
		final TextField userTextField3 = new TextField();
		final TextField userTextField4 = new TextField();
		Button convertSingleBtn = new Button("Convert To Single");
		Button convertDoubleBtn = new Button("Convert To Double");
		Button convertDecimalBtn1 = new Button("Convert From Single to Decimal");
		Button convertDecimalBtn2 = new Button("Convert From Double to Decimal");
		final Label outputSingle = new Label("OUTPUT Single");
		final Label outputDouble = new Label("OUTPUT Double");
		final Label outputDecimal1 = new Label("OUTPUT Decimal 1");
		final Label outputDecimal2 = new Label("OUTPUT Decimal 2");
		//Converts Decimal to Single
		grid.add(inputDecimal1, 0,0);
		grid.add(userTextField1,1,0);
		grid.add(convertSingleBtn, 2, 0);
		grid.add(outputSingle,3,0);
		//Converts Decimal to Double
		grid.add(inputDecimal2, 0,1);
		grid.add(userTextField2,1,1);
		grid.add(convertDoubleBtn, 2, 1);
		grid.add(outputDouble,3,1);
		//Converts Single to Decimal
		grid.add(inputSingle, 0, 2);
		grid.add(userTextField3, 1,2);
		grid.add(convertDecimalBtn1, 2, 2);
		grid.add(outputDecimal1, 3, 2);
		//Converts Double to Decimal
		grid.add(inputDouble, 0, 3);
		grid.add(userTextField4, 1, 3);
		grid.add(convertDecimalBtn2, 2, 3);
		grid.add(outputDecimal2, 3, 3);
		
		convertSingleBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent convert) {
				String inputDecimal = userTextField1.getText();
				//System.out.println(inputDecimal);
				toSinglePercision = decimalConverter(inputDecimal);
				String output = toSinglePercision.convertBinaryToSingle(inputDecimal).toString();
				outputSingle.setText(output);
				System.out.println(output);
			}
			
		});
		convertDoubleBtn.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent convert) {
				String inputDecimal = userTextField2.getText();
				//System.out.println(inputDecimal);
				toDoublePercision = decimalConverter(inputDecimal);
				String output = toDoublePercision.convertBinaryToDouble(inputDecimal).toString();
				outputDouble.setText(output);
				System.out.println(output);
				
			}
			
		});
		convertDecimalBtn1.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent convert) {
				String inputSingle = userTextField3.getText();
				String sign = inputSingle.substring(0,1);
				String exponent = inputSingle.substring(1,9);
				String significand = inputSingle.substring(9);
				//System.out.println(sign+" "+exponent+" "+significand);
				Ieee754Value toDecimal = new Ieee754Value(sign,exponent,significand);
				double output = toSinglePercision.convertSingleToDecimal(toDecimal);
				outputDecimal1.setText(Double.toString(output));
			}
			
		});
		convertDecimalBtn2.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent convert) {
				String inputSingle = userTextField4.getText();
				String sign = inputSingle.substring(0,1);
				String exponent = inputSingle.substring(1,12);
				String significand = inputSingle.substring(12);
				//System.out.println(sign+" "+exponent+" "+significand);
				Ieee754Value toDecimal = new Ieee754Value(sign,exponent,significand);
				double output = toSinglePercision.convertDoubleToDecimal(toDecimal);
				outputDecimal2.setText(Double.toString(output));
			}
			
		});
		primaryStage.setScene(new Scene(grid, 1000, 200));
		primaryStage.setResizable(true);
		primaryStage.show();
	}
	public Ieee754Converter decimalConverter(String decimal){
		int periodPos=0;
		boolean noPeriod;
		String sign = null;
		int numberWhole = 0;
		if(Double.parseDouble(decimal)>=0)
		{
			sign="0";
		} else if(Double.parseDouble(decimal)<0)
		{
			sign="1";
			int dashPos = decimal.indexOf('-');
			decimal = decimal.substring(dashPos+1);
			//System.out.println(decimal);
		}
		//periodPos = decimal.indexOf(".");
		if(decimal.contains(".")){
			periodPos = decimal.indexOf(".");
			//System.out.println(periodPos);
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
		double numberDecimal=0;
		if(noPeriod==false)
		{
			numberDecimal = Double.parseDouble(decimal.substring(periodPos));
		}
		//double numberDecimal = Double.parseDouble(decimal.substring(periodPos));

		//System.out.println(numberWhole+"  .  "+numberDecimal);
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
		//System.out.println(binary);
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
		//System.out.println(outputBinary);
		//System.out.println(exponent);
		Ieee754Converter convert = new Ieee754Converter(sign,outputBinary,exponent);
		return convert;
	}

}
