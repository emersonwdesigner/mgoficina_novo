package com.mgoficina;

import com.example.mgoficina.R;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SobreActivity extends Activity {
 TextView versao;
 LinearLayout info;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre);
		versao = (TextView) findViewById(R.id.versao);
		info = (LinearLayout) findViewById(R.id.sobreInfo);
		
		PackageInfo pInfo;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			String version = pInfo.versionName;
			
			versao.setText(version);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
