package com.mgoficina;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.example.mgoficina.R;

public class FormBuscaActivity extends SherlockActivity {
	EditText entradaTexto;
	final DataBaseHandler db = new DataBaseHandler(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
		getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.title_activity_form_busca)+"</b></font>"));
		getSupportActionBar().setHomeButtonEnabled(true);
		setContentView(R.layout.activity_form_busca);
		
		entradaTexto = (EditText) findViewById(R.id.editBuscar);
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				entradaTexto.setEnabled(true);
				
				if(checkedId == R.id.radioButton1){
					entradaTexto.setText("");
					entradaTexto.setInputType(InputType.TYPE_CLASS_NUMBER);
					
				}else if(checkedId == R.id.radioButton2){
					entradaTexto.setText("");
					entradaTexto.setInputType(InputType.TYPE_CLASS_TEXT);
				}else if(checkedId == R.id.radioButton3){
					entradaTexto.setText("");
					entradaTexto.setInputType(InputType.TYPE_CLASS_TEXT);
				}
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
			}
		});
	}

	
	public void Buscar(View v){
		
					RadioGroup radioGroupId = (RadioGroup) findViewById(R.id.radioGenderGroup);
					
					int id = radioGroupId.getCheckedRadioButtonId();
					if (id == -1){
						Toast.makeText(getBaseContext(), "Selecione o tipo de pesquisa!", Toast.LENGTH_LONG).show();
					}else{
		        
					int selectedOption = radioGroupId.getCheckedRadioButtonId();
		            RadioButton radioGenderButton = (RadioButton) findViewById(selectedOption);
		            	EditText param = (EditText) findViewById(R.id.editBuscar);
		            	String var1 = param.getText().toString();
		            	
		        		
		            	Intent it = new Intent(FormBuscaActivity.this,ListaTrabalhosActivity.class);
		        		if(radioGenderButton.getText().equals(getString(R.string.os))){
		        			it.putExtra("COLUNA", DataBaseHandler.TABLE_CONTACTS+"."+DataBaseHandler.KEY_NAME);
		        			Log.v("aviso", "OS");
		        		}else if(radioGenderButton.getText().equals(getString(R.string.cliente))){
		        			it.putExtra("COLUNA", DataBaseHandler.TABLE_CLIENTES+"."+DataBaseHandler.KEY_CLIENTE_NAME);
		        			Log.v("aviso",  DataBaseHandler.TABLE_CLIENTES+"."+DataBaseHandler.KEY_CLIENTE_NAME);
		        		}else if(radioGenderButton.getText().equals(getString(R.string.descricao))){ 
		        			it.putExtra("COLUNA", DataBaseHandler.TABLE_CONTACTS+"."+DataBaseHandler.KEY_DESCRICAO);
		        			Log.v("aviso", DataBaseHandler.TABLE_CONTACTS+"."+DataBaseHandler.KEY_DESCRICAO);
		        		}
		        		
		        		it.putExtra("STATUS", var1);
		        		it.putExtra("TIPO", "busca");
		        		it.putExtra("KEY", var1);
		                startActivity(it); 	
					}
		
	}

	 @Override
 	protected void onStop() {
 		// TODO Auto-generated method stub
 		super.onStop();
 		this.finish();
 	}
}
