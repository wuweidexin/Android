package com.chen.manual;

import com.chen.chaobiao1_1.R;
import com.chen.mainActivity.AllMenuItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 该类是读入各种数据时显示的类
 * 
 * 当读入个各种数据时，通过弹出进度条来显示执行时间
 * @author Administrator
 *
 */
public class ManCopRead extends Activity{

	Button back = null;
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.man_cop_read);
		createView();
	}

	private void createView() {
		RadioButton rb1 = (RadioButton)findViewById(R.id.man_cop_reaMeasureData);
		RadioButton rb2 = (RadioButton)findViewById(R.id.man_cop_reaPrice);
		RadioButton rb3 = (RadioButton)findViewById(R.id.man_cop_reaURL);
		RadioButton rb4 = (RadioButton)findViewById(R.id.man_cop_reaMoney);
		RadioButton rb5 = (RadioButton)findViewById(R.id.man_cop_reaHistoryMeasureData);
		RadioButton rb6 = (RadioButton)findViewById(R.id.man_cop_reaFlowmeter);
		RadioGroup rg = (RadioGroup)findViewById(R.id.man_cop_reaRG);

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				changeView(checkedId);

			}

			private void changeView(int checkedId) {

				CheckBox c1 = (CheckBox)findViewById(R.id.man_cop_reaChoseTime);
				CheckBox c2 = (CheckBox)findViewById(R.id.man_cop_reaChoseNum);
				CheckBox c3 = (CheckBox)findViewById(R.id.man_cop_reaManConcleWat);
				CheckBox c4 = (CheckBox)findViewById(R.id.man_cop_reaChoseUseWay);
				switch(checkedId){

				case R.id.man_cop_reaMeasureData:
					c1.setVisibility(0);
					c2.setVisibility(0);
					c3.setVisibility(0);
					c4.setVisibility(0);
					break;
				case R.id.man_cop_reaPrice:
					c1.setVisibility(8);
					c2.setVisibility(8);
					c3.setVisibility(0);
					c4.setVisibility(0);
					break;
				case R.id.man_cop_reaURL:
					c1.setVisibility(8);
					c2.setVisibility(8);
					c3.setVisibility(0);
					c4.setVisibility(0);
					break;
				case R.id.man_cop_reaMoney:
					c1.setVisibility(8);
					c2.setVisibility(8);
					c3.setVisibility(0);
					c4.setVisibility(0);
					break;
				case R.id.man_cop_reaHistoryMeasureData:
					c1.setVisibility(8);
					c2.setVisibility(8);
					c3.setVisibility(0);
					c4.setVisibility(0);
					break;
				case R.id.man_cop_reaFlowmeter:
					c1.setVisibility(8);
					c2.setVisibility(8);
					c3.setVisibility(0);
					c4.setVisibility(0);
					break;
				}

			}
		});
		
		back = (Button)findViewById(R.id.man_cop_exitR);
		back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ManCopRead.this, ManCopIndex.class);
				startActivity(intent);
			}
		});
	}
}
