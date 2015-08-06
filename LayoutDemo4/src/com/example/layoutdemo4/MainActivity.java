package com.example.layoutdemo4;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */

	private Boolean m_hiddened = false;
	private LinearLayout mLayout;
	private ScrollView scrollv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLayout = (LinearLayout)this.findViewById(R.id.LinearLayout);
		scrollv = (ScrollView)this.findViewById(R.id.ScrollView);
		Button button = (Button)this.findViewById(R.id.Button);
		button.setOnClickListener(mClickListener);
	}
	
	private Button.OnClickListener mClickListener = new Button.OnClickListener(){

		@Override
		public void onClick(View view) {
			TextView tv = new TextView(null);
			tv.setText("ScrollViewҲ��һ��Layout���֣����������ڲ���������ʾ���µ�ʱ��" +
					"���ִ�ֱ��������Ҫע����ǲ�����ScrollView�зŶ�����");
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			mLayout.addView(tv, params);
		}

	};


}
