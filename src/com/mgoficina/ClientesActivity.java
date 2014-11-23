package com.mgoficina;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.example.mgoficina.R;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ClientesActivity extends SherlockActivity {
String trab;
DataBaseHandler db = new DataBaseHandler(this);
ContentValues values = new ContentValues();
String[] from;
int[] to;
Cursor cursor;
ListView list;
TextView idCliente, idClienteAnt, nomeAnt, telefoneAnt, enderecoAnt, emailAnt, telefoneTroca, idClienteOs;
EditText editarNome, editarTelefone, editarEndereco, editarEmail;
final Context context = this;
Funcoes funcoes = new Funcoes();
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.main);

getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#ffffff\"><b>"+getString(R.string.clientes)+"</b></font>"));
getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#007ca5")));
getSupportActionBar().setSubtitle(getString(R.string.toque_segure));
getSupportActionBar().setDisplayHomeAsUpEnabled(true);


from = new String[] {
		DataBaseHandler.KEY_CLIENTE_NAME, 
		DataBaseHandler.KEY_CLIENTE_TELEFONE, 
		DataBaseHandler.KEY_CLIENTE_ENDERECO, 
		DataBaseHandler.KEY_CLIENTE_EMAIL, 
		DataBaseHandler.KEY_CLIENTE_ID,
		DataBaseHandler.KEY_CLIENTE_NAME,
		DataBaseHandler.KEY_CLIENTE_TELEFONE, 
		DataBaseHandler.KEY_CLIENTE_ENDERECO,
		DataBaseHandler.KEY_CLIENTE_ID_OS};

// Ids of views in listview_layout
to = new int[] { R.id.txt,R.id.txtTelefone, R.id.txtEndereco, R.id.txtEmail, R.id.keyId, R.id.editarNome, R.id.editarTelefone, R.id.editarEndereco, R.id.keyIdOs};        

cursor = db.getAllClientes();

@SuppressWarnings("deprecation")
SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.listview_clientes, cursor, from, to);
list = (ListView) findViewById(R.id.list);
list.setAdapter(adapter);
registerForContextMenu(list);

adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
	
	   /** Binds the Cursor column defined by the specified index to the specified view */
	   public boolean setViewValue(View view, Cursor cursor, int columnIndex){
		   
		   telefoneTroca = (TextView) view.findViewById(R.id.txtTelefone);
	       if(view.getId() == R.id.txtTelefone){
	    	   
	    	   String telefone = cursor.getString(columnIndex);
	    	   telefoneTroca.setText(getString(R.string.telefone)+": "+funcoes.telefoneFormat(telefone));
	    	   
	           return true; 
	   }
		return false;
	   }    
	       
	});		

}

public boolean onCreateOptionsMenu(Menu menu) {
	
    SubMenu sub = menu.addSubMenu(R.string.novo_cliente).setIcon(R.drawable.ic_action_add_person );
    
    sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    
    
    return true;
    
}


public boolean onOptionsItemSelected(MenuItem item) {
	if(item.getTitle().equals(getString(R.string.novo_cliente))){
		
		LayoutInflater li = LayoutInflater.from(context);
		final View promptsView = li.inflate(R.layout.editar_clientes, null);
		
		idCliente 		= (TextView) promptsView.findViewById(R.id.keyidCliente);
		editarNome 		= (EditText) promptsView.findViewById(R.id.editarNome);
		editarTelefone 	= (EditText) promptsView.findViewById(R.id.editarTelefone);
		editarEndereco 	= (EditText) promptsView.findViewById(R.id.editarEndereco);
		editarEmail 	= (EditText) promptsView.findViewById(R.id.editarEmail);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		alertDialogBuilder.setView(promptsView);
		
		alertDialogBuilder.setTitle(getString(R.string.novo_cliente))
			.setCancelable(false)
			.setPositiveButton(getString(R.string.salvar),
			  new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,int id) {
					
					String nome 	= editarNome.getText().toString();
					String endereco = editarEndereco.getText().toString();
					String email 	= editarEmail.getText().toString();
					int telefone = 0;
					if(!editarTelefone.getText().toString().equals("")){
			        	try {
			        		telefone = Integer.parseInt(editarTelefone.getText().toString());
			        	} catch(NumberFormatException nfe) {
			        	} 
			    	}
					
					//db.criaCliente(funcoes.removerAcentos(nome), telefone, funcoes.removerAcentos(endereco), 0);
					values.put(DataBaseHandler.KEY_CLIENTE_ID_OS, funcoes.gerarIdCliente());
					values.put(DataBaseHandler.KEY_CLIENTE_NAME, funcoes.removerAcentos(nome));
					values.put(DataBaseHandler.KEY_CLIENTE_TELEFONE, telefone);
					values.put(DataBaseHandler.KEY_CLIENTE_ENDERECO, funcoes.removerAcentos(endereco));
					values.put(DataBaseHandler.KEY_CLIENTE_EMAIL, funcoes.removerAcentos(email));
					values.put(DataBaseHandler.KEY_CLIENTE_EXPORTA, 0);
					values.put(DataBaseHandler.KEY_CLIENTE_DELETADO, 0);

					db.Insert(DataBaseHandler.TABLE_CLIENTES, values);
					Toast.makeText(getBaseContext(), getString(R.string.cliente_inserido), Toast.LENGTH_LONG).show();
					startActivity(new Intent(ClientesActivity.this,ClientesActivity.class));
					
			    }
			  })
			.setNegativeButton("Cancelar",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		return true;
	}else{
	
	Intent it = new Intent(ClientesActivity.this, MainActivity.class);
	startActivity(it);  
        return true;
	}
}
	
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    android.view.MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.clientes, menu);
    Log.v("aviso", "0");
}

public boolean onContextItemSelected(android.view.MenuItem item) {
	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	idClienteAnt= (TextView) info.targetView.findViewById(R.id.keyId);
	idClienteOs = (TextView) info.targetView.findViewById(R.id.keyIdOs);
	nomeAnt 	= (TextView) info.targetView.findViewById(R.id.txt);
	telefoneAnt = (TextView) info.targetView.findViewById(R.id.txtTelefone);
	enderecoAnt = (TextView) info.targetView.findViewById(R.id.txtEndereco);
	emailAnt 	= (TextView) info.targetView.findViewById(R.id.txtEmail);
	
	
	Log.v("aviso", "1");
    switch (item.getItemId()) {
    case R.id.edit:

    	LayoutInflater li = LayoutInflater.from(context);
		final View promptsView = li.inflate(R.layout.editar_clientes, null);
		
		idCliente 		= (TextView) promptsView.findViewById(R.id.keyidCliente);
		editarNome 		= (EditText) promptsView.findViewById(R.id.editarNome);
		editarTelefone 	= (EditText) promptsView.findViewById(R.id.editarTelefone);
		editarEndereco 	= (EditText) promptsView.findViewById(R.id.editarEndereco);
		editarEmail 	= (EditText) promptsView.findViewById(R.id.editarEmail);
		
		String telFormatado = telefoneAnt.getText().toString().replace(" ", "").replace(":", "").replace("-", "").substring(8);

		idCliente.setText(idClienteAnt.getText().toString());
		editarNome.setText(nomeAnt.getText().toString());
		editarTelefone.setText(telFormatado);
		editarEndereco.setText(enderecoAnt.getText().toString());
		editarEmail.setText(emailAnt.getText().toString());
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		alertDialogBuilder.setView(promptsView);
		
		alertDialogBuilder.setTitle(getString(R.string.editar)).setIcon(R.drawable.ic_action_edit)
			.setCancelable(false)
			.setPositiveButton(getString(R.string.salvar),
			  new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog,int id) {
					
					String idno 	= idCliente.getText().toString();
					String nome 	= editarNome.getText().toString();
					String telefone = editarTelefone.getText().toString();
					String endereco = editarEndereco.getText().toString();
					String email 	= editarEmail.getText().toString();
					
					//db.editarCliente(idno, funcoes.removerAcentos(nome), telefone, funcoes.removerAcentos(endereco));
					
					values.put(DataBaseHandler.KEY_CLIENTE_NAME, funcoes.removerAcentos(nome));
					values.put(DataBaseHandler.KEY_CLIENTE_TELEFONE, telefone);
					values.put(DataBaseHandler.KEY_CLIENTE_ENDERECO, funcoes.removerAcentos(endereco));
					values.put(DataBaseHandler.KEY_CLIENTE_EMAIL, funcoes.removerAcentos(email));
					

					db.Update(DataBaseHandler.TABLE_CLIENTES, values, "_id=?", new String[] {idno});
					Toast.makeText(getBaseContext(), getString(R.string.iten_editado), Toast.LENGTH_LONG).show();
					startActivity(new Intent(ClientesActivity.this,ClientesActivity.class));
					
			    }
			  })
			.setNegativeButton("Cancelar",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
    	
        return true;
    case R.id.delete:
    	Log.v("aviso", idClienteOs.getText().toString());
    	//db.deleteCliente(idClienteAnt.getText().toString());
    	
    	values.put(DataBaseHandler.KEY_CLIENTE_DELETADO, 1);
		db.Update(DataBaseHandler.TABLE_CLIENTES, values, "_id=?", new String[] {idClienteAnt.getText().toString()});
		
		
    	db.removeClienteOs(idClienteOs.getText().toString());
    	Toast.makeText(getBaseContext(), getString(R.string.cliente_removido), Toast.LENGTH_LONG).show();
        startActivity(new Intent(ClientesActivity.this,ClientesActivity.class));
        return true;
    }
    return true;
}



@Override
protected void onStop() {
	// TODO Auto-generated method stub
	super.onStop();
	this.finish();
}
}
