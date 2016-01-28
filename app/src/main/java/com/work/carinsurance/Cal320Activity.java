package com.work.carinsurance;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Cal320Activity extends Activity{
	
	Spinner sp1,sp2,sp3;
	ArrayList<String> al1;
	List<String> al2 = new ArrayList<String>();
	List<String> al3 = new ArrayList<String>();
	List<String> al4 = new ArrayList<String>();
	Integer[] im = {R.drawable.a8,R.drawable.a16,R.drawable.a20,R.drawable.a21,R.drawable.a30,R.drawable.a33,R.drawable.a36,R.drawable.a50,R.drawable.a52};
	
	public TextView sStart,sEnd,tx1,tx2,tx3,tx4,sMax,sAvg,sMin,sAgecar,sMotor,sGroup,tv5,person,tv6;
	public EditText input,iAgedrive,iFleet,iNcb,iOther,iOd,iTp,iName,iRegis,iMode,iMe,iTppd,iTpbi,iPa,iBail,iDamageX,iPersonNum;
	public ImageView pic;
	public Button bt,print;

	public ReadText rFt = new ReadText("");
	public Formula sentAmount1 = new Formula();
	
	public DecimalFormat formatter = new DecimalFormat("#,###,###,###,###");
	public DecimalFormat formatter1 = new DecimalFormat("#,###,###,###,###.00");
	public DecimalFormat formatter2 = new DecimalFormat("#,###,###,##0,000");
	
	public String amount3x,amount4x,amountmin,amountavg,amountmax,xx1,xx2,xx3,xx4,xx5,xx6,xx7,xx8;
	public String numYear1,numYear2,temp,bandId;
	public String[] bTemp,al11;
	
	public int bmode=0;
	
	public Double avg=0.0,min=0.0,max=0.0,minf=0.0,maxf=0.0,getagef=0.0;
	public Double amount1=0.0,amount2=0.0,amount3=0.0,amount4=0.00;
	public Double amount11=0.0 ,amount21=0.0,amount31=0.0,amount41=0.00;
	public Double rateModeCar=0.0, rateMotorCar=0.0, rateDriverAge=0.0,rateCarAge=0.0, rateCapIsur=0.0,rateGroupCar=0.0, rateF=0.0, rateG=0.0, rateH=0.0;
	
	public static final Double FIX80=0.8;
	public static final Double FIX90=0.9;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cal);
		super.onCreate(savedInstanceState);
		
		//--------------------------------------------------------------------------------------------------------------------------------------------------
		final Dialog dialog = new Dialog(Cal320Activity.this);
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.customdialog);
        dialog.setCancelable(true);
		
		Button btnDialog = (Button) findViewById(R.id.btnDialog);
        btnDialog.setOnClickListener(new Button.OnClickListener() {
            public void onClick (View v) {
                Button btClose = (Button) dialog.findViewById(R.id.btClose);
                btClose.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
        				xx1 = formatter.format(Double.parseDouble(iTppd.getText().toString().replace(",","").trim()));
        				xx2 = formatter.format(Double.parseDouble(iTpbi.getText().toString().replace(",","").trim()));
        				xx3 = formatter.format(Double.parseDouble(iDamageX.getText().toString().replace(",","").trim()));
        				xx4 = formatter.format(Double.parseDouble(iPa.getText().toString().replace(",","").trim()));
        				xx5 = formatter.format(Double.parseDouble(iMe.getText().toString().replace(",","").trim()));
        				xx6 = formatter.format(Double.parseDouble(iBail.getText().toString().replace(",","").trim()));
        				xx7 = formatter.format(Double.parseDouble(iOd.getText().toString().replace(",","").trim()));
        				xx8 = formatter.format(Double.parseDouble(iTp.getText().toString().replace(",","").trim()));
        				
        				iTppd.setText(xx1);
        				iTpbi.setText(xx2);
        				iDamageX.setText(xx3);
        				iPa.setText(xx4);
        				iMe.setText(xx5);
        				iBail.setText(xx6);
        				iOd.setText(xx7);
        				iTp.setText(xx8);
        				sMotor.setText(iPersonNum.getText().toString());
        				sGroup.setText(iMode.getText().toString());
        				bmode = Integer.parseInt(iMode.getText().toString().trim());
        				if(bmode == 320 || bmode == 340)
                            dialog.cancel();
            				else{
            					Toast.makeText(Cal320Activity.this,String.valueOf("กรุณาใส่ ลักษณะการใช้รถ 320 และ 340"),Toast.LENGTH_LONG).show();
            				}
                    }});
                dialog.show();
            }});

		sp1 = (Spinner) findViewById(R.id.spinner1);
		sp2 = (Spinner) findViewById(R.id.spinner2);
		sp3 = (Spinner) findViewById(R.id.spinner3);
		bt = (Button) findViewById(R.id.bt);
		print = (Button) findViewById(R.id.btPrint);
        
        input = (EditText) findViewById(R.id.input);	input.setText("");
		tx1 = (TextView) findViewById(R.id.tx1);		tx1.setText("0");
		tx2 = (TextView) findViewById(R.id.tx2);		tx2.setText("0");
		tx3 = (TextView) findViewById(R.id.tx3);		tx3.setText("0");
		tx4 = (TextView) findViewById(R.id.tx4);		tx4.setText("0");
		sMax = (TextView) findViewById(R.id.tx5);		sMax.setText("0");
		sAvg = (TextView) findViewById(R.id.tx6);		sAvg.setText("0");
		sMin = (TextView) findViewById(R.id.tx7);		sMin.setText("0");
		
		sAgecar = (TextView) findViewById(R.id.sAgecar);	sAgecar.setText("0");
		tv5 = (TextView) findViewById(R.id.tv5);			tv5.setText("น้ำหนัก");
		tv6 = (TextView) findViewById(R.id.tv6);			tv6.setText("ลักษณะรถ");
		
		sMotor = (TextView) findViewById(R.id.sMotor);		sMotor.setText("0");
		sGroup = (TextView) findViewById(R.id.sGroup);		sGroup.setText("0");
		sStart = (TextView) findViewById(R.id.sStart);		sStart.setText("0");
		sEnd = (TextView) findViewById(R.id.sEnd);			sEnd.setText("0");
		
		iTppd = (EditText) dialog.findViewById(R.id.iTppd);			iTppd.setText("2,500,000");
		iTpbi = (EditText) dialog.findViewById(R.id.iTpbi);			iTpbi.setText("500,000");
		iDamageX = (EditText) dialog.findViewById(R.id.iDamage);	iDamageX.setText("10,000,000");
		iPa = (EditText) dialog.findViewById(R.id.iPa);				iPa.setText("100,000");
		iMe = (EditText) dialog.findViewById(R.id.iMe);				iMe.setText("50,000");
		iBail = (EditText) dialog.findViewById(R.id.iBail);			iBail.setText("200,000");
		iOd = (EditText) dialog.findViewById(R.id.iOd);				iOd.setText("0");
		iTp = (EditText) dialog.findViewById(R.id.iTp);				iTp.setText("0");
		
		iName = (EditText) dialog.findViewById(R.id.iName);				iName.setText("");
		iAgedrive = (EditText) dialog.findViewById(R.id.iAgeDriver);	iAgedrive.setText("1");
		iRegis = (EditText) dialog.findViewById(R.id.iRegis);			iRegis.setText("");
		iMode = (EditText) dialog.findViewById(R.id.iMode);				iMode.setText("320");
		iFleet = (EditText) dialog.findViewById(R.id.iFleet);			iFleet.setText("0");
		iNcb = (EditText) dialog.findViewById(R.id.iNcb);				iNcb.setText("20");
		iOther = (EditText) dialog.findViewById(R.id.iOther);			iOther.setText("0");
		person = (TextView) dialog.findViewById(R.id.person);			person.setText("Weight car");
		iPersonNum = (EditText) dialog.findViewById(R.id.iPerson);		iPersonNum.setText("3000");

		input.setFocusableInTouchMode(true);
		iTppd.setFocusableInTouchMode(true);
		iTpbi.setFocusableInTouchMode(true);
		iPa.setFocusableInTouchMode(true);
		iMe.setFocusableInTouchMode(true);
		iBail.setFocusableInTouchMode(true);
		iOd.setFocusableInTouchMode(true);
		iTp.setFocusableInTouchMode(true);
		iName.setFocusableInTouchMode(true);
		iAgedrive.setFocusableInTouchMode(true);
		iRegis.setFocusableInTouchMode(true);
		iMode.setFocusableInTouchMode(true);
		iFleet.setFocusableInTouchMode(true);
		iNcb.setFocusableInTouchMode(true);
		iOther.setFocusableInTouchMode(true);
		iPersonNum.setFocusableInTouchMode(true);
		
		ReadText rFt = new ReadText("");
		al1 = rFt.getBand210(getAssets());
		al11 = al1.toArray(new String[al1.size()]);
		sp1.setAdapter(new MyCustomAdapter(this,R.layout.rowsp, al11, im));
		sp1.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView,View view,final int i1, long l) {
				bandId = String.valueOf(i1 + 1);
				ReadText rFt = new ReadText("");
				al2 = rFt.getModel210(bandId, getAssets());
				ArrayAdapter<String> Ad2 = new ArrayAdapter<String>(Cal320Activity.this,android.R.layout.simple_spinner_item,al2);
				sp2.setAdapter(Ad2);
				sp2.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,View view,final int i2, long l) {
						ReadText rFt = new ReadText("");
						al3 = rFt.getYear(getAssets());
						ArrayAdapter<String> Ad3 = new ArrayAdapter<String>(Cal320Activity.this,android.R.layout.simple_spinner_item,al3);
						sp3.setAdapter(Ad3);
						sp3.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener() {
							public void onItemSelected(AdapterView<?> adapterView,View view,int i3, long l) {
								
								ReadText rFt = new ReadText("");
								al4 = rFt.getModelId210(getAssets(),bandId);
								temp = rFt.getModel1210(getAssets(),bandId,al4.get(i2));
								bTemp = temp.split("\t");
								Calendar c = Calendar.getInstance();
								SimpleDateFormat dm = new SimpleDateFormat("dd-MM");
								SimpleDateFormat yy = new SimpleDateFormat("yyyy");
								String Date = dm.format(c.getTime());
								String Date1 = yy.format(c.getTime());
								numYear1 = String.valueOf(Integer.parseInt(Date1)+543);
								numYear2 = String.valueOf(1 + Integer.parseInt(Date1)+543);
								
								sAgecar.setText(String.valueOf((2556 - Integer.parseInt(al3.get(i3).substring(5)))+1)+" ปี");
								sMotor.setText(iPersonNum.getText().toString());
								sGroup.setText(iMode.getText().toString());
								sStart.setText(Date +"-"+ numYear1);
								sEnd.setText(Date +"-"+ numYear2);
								
								getagef = Double.parseDouble(sAgecar.getText().toString().replace("ปี","").trim())-1;
								min = Double.parseDouble(bTemp[5].replace(",","").trim());
								max = Double.parseDouble(bTemp[6].replace(",","").trim());
								if(min == 0 && max == 0){
									Toast.makeText(Cal320Activity.this,String.valueOf("ไม่มีข้อมูลของรถยี้ห้อนี้"),Toast.LENGTH_LONG).show();
									bt.setEnabled(false);
								}
								else{
									bt.setEnabled(true);
								}
								
								minf = sentAmount1.maxmin210(min, getagef);
								maxf = max;
								
								min=0.0;max=0.0;
								min = minf * FIX80;
								max = maxf * FIX90;
								avg = (min+max)/2;
								amountmin = formatter.format(min);
								amountavg = formatter.format(avg);
								amountmax = formatter.format(max);
								sMax.setText(amountmin);
								sAvg.setText(amountavg);
								sMin.setText(amountmax);
								tx1.setText("0");
								tx2.setText("0");
								tx3.setText("0");
								tx4.setText("0");
						}
							public void onNothingSelected(AdapterView<?> arg0) {Toast.makeText(Cal320Activity.this,String.valueOf("Your Selected Empty"),Toast.LENGTH_LONG).show();}});
				}
					public void onNothingSelected(AdapterView<?> arg0) {Toast.makeText(Cal320Activity.this,String.valueOf("Your Selected Empty"),Toast.LENGTH_LONG).show();}});
		}
			public void onNothingSelected(AdapterView<?> arg0) {Toast.makeText(Cal320Activity.this,String.valueOf("Your Selected Empty"),Toast.LENGTH_LONG).show();}});
	
		bt.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ReadText rFt = new ReadText("");
				Integer tanInput = 0;
				bmode = Integer.parseInt(iMode.getText().toString().trim());
		        if(bmode == 320 || bmode == 340)
		        {
     
				if(input.getText().toString().equals("")){
					tanInput = 50000;
				}
				else{
					tanInput = Integer.parseInt(input.getText().toString().replace(",","").trim());
					if(tanInput != 0){
						String ttinput = formatter.format(tanInput);
						input.setText(ttinput);
					}
				}
				if(tanInput >= min && tanInput <= max){
				rateModeCar = Double.parseDouble(rFt.getModeCar320(getAssets(),iMode.getText().toString()).toString().trim())/100;
				rateMotorCar = Double.parseDouble(rFt.getMotorCar320(getAssets(),sMotor.getText().toString().trim()))/100;
				rateCarAge = Double.parseDouble(rFt.getCarAge320(getAssets(),sAgecar.getText().toString().replace("ปี","").trim()).toString())/100;
				rateCapIsur = Double.parseDouble(rFt.getTypeInsurance320(getAssets(),tanInput.toString(),"1").toString().trim())/100;
				rateH = Double.parseDouble(rFt.getDamageProperty320(getAssets(),iTppd.getText().toString().replace(",","").trim(),"1").toString());
				rateF = Double.parseDouble(rFt.getDamagePerson320(getAssets(),iTpbi.getText().toString().replace(",","").trim(),"1").toString());
				rateG = Double.parseDouble(rFt.getDamageAccient320(getAssets(),iDamageX.getText().toString().replace(",","").trim(),"1").toString());
				//--------------------------------------------------------------------------------------------------------------------------------------------------------
				if(rateCapIsur == 0){
					Toast.makeText(Cal320Activity.this,String.valueOf("คุณใส่ทุนประกันไม่ถูกต้อง"),Toast.LENGTH_LONG).show();
				}
				else{
				Integer in1=0,in2=0;
				in1 = Integer.parseInt(sAgecar.getText().toString().replace("ปี","").trim());
				Double INSURSMODECAR110 = 0.0;
				INSURSMODECAR110 = sentAmount1.amount1320(in1, in2);
				amount1 = (double) Math.ceil(INSURSMODECAR110 * (rateModeCar*rateMotorCar*rateCarAge*rateCapIsur*rateF*rateG*rateH));
				amount2 = sentAmount1.amount2320(in1, amount1,iMode.getText().toString().trim());
				Double odInput=Double.parseDouble(iOd.getText().toString().replace(",","")),tpInput=Double.parseDouble(iTp.getText().toString().replace(",",""));
				Double fleet1 = Double.parseDouble(iFleet.getText().toString());
				Double ncb1 = Double.parseDouble(iNcb.getText().toString());
				Double other1 = Double.parseDouble(iOther.getText().toString());
				amount3 = sentAmount1.amount3210(odInput, tpInput, amount2, fleet1, ncb1, other1);
				amount4 = sentAmount1.amount4(amount3);
				//---------------------------------------------------------------------------------------------------------------------------------------------------------
				Integer tanInt1 = Integer.parseInt(iTppd.getText().toString().replace(",","").trim())*2;
				Integer tanInt2 = Integer.parseInt(iTpbi.getText().toString().replace(",","").trim())*2;
				rateH = Double.parseDouble(rFt.getDamageProperty210(getAssets(),(tanInt1.toString()),"1").toString());
				rateF = Double.parseDouble(rFt.getDamagePerson210(getAssets(),(tanInt2.toString()),"1").toString());
				Double INSURSMODECAR111 = (double) 0;
				if(in1>=1 && in1<=5){
					INSURSMODECAR111 = (double) 14950;
					amount11 = (double) Math.ceil(INSURSMODECAR111 * (rateModeCar*rateMotorCar*rateCarAge*rateCapIsur*rateF*rateG*rateH));
					Double paAmount1 =(double) 600, meAmount1=(double) 135,bailbondAmount1=(double) 500;
					amount21 = amount11 + (paAmount1+meAmount1+bailbondAmount1);
					amount31 = sentAmount1.amount3320(odInput, tpInput, amount21, fleet1, ncb1, other1,in1);
					amount41 = sentAmount1.amount4(amount31);
					String amount31x,amount41x;
					amount31x = formatter.format(amount31);
					amount41x = formatter1.format(amount41);
					tx3.setText(amount31x);
					tx4.setText(amount41x);
				}
				else{
					tx3.setText("0");
					tx4.setText("0");
				}
				//--------------------------------------------------------------------------------------------------------------------------------------------------------
				amount3x = formatter.format(amount3);
				amount4x = formatter1.format(amount4);
				tx1.setText(amount3x);
				tx2.setText(amount4x);
				}
				}
				else{
					tx1.setText("0");
					tx2.setText("0");
					tx3.setText("0");
					tx4.setText("0");
					String amountmin2 = formatter2.format(min);
					String amountmax2 = formatter2.format(max);
					Toast.makeText(Cal320Activity.this,String.valueOf("ใส่ค่าที่อยู่ระหว่าง "+amountmin2+" และ "+amountmax2),Toast.LENGTH_LONG).show();
				}
		        }
		        else{
		        	dialog.show();
		        	Toast.makeText(Cal320Activity.this,String.valueOf("กรุณาใส่ ลักษณะการใช้รถ 320 และ 340"),Toast.LENGTH_LONG).show();
		        }
			}});
		print.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            
            }});
		//---------------**
	}
	public class MyCustomAdapter extends ArrayAdapter<String> {
		public MyCustomAdapter(Context context, int textViewResourceId,String[] objects, Integer[] image) {super(context, textViewResourceId, objects);}
		@Override
		public View getDropDownView(int position, View convertView,ViewGroup parent) {return getCustomView(position, convertView, parent);}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) { return getCustomView(position, convertView, parent);}
		public View getCustomView(int position, View convertView,ViewGroup parent) { 
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.rowsp, parent, false);
			TextView label = (TextView) row.findViewById(R.id.nband);
			label.setText(al11[position]);
			ImageView icon = (ImageView) row.findViewById(R.id.icon);
			icon.setImageResource(im[position]);
			return row;
		}
	}
}