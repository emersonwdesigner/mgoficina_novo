package com.mgoficina;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import com.example.mgoficina.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaTrabalhosAdapter extends ArrayAdapter<Contact>{
	Funcoes funcoes = new Funcoes();
Context context;
int layoutResourceId;
ArrayList<Contact> data=new ArrayList<Contact>();
public ListaTrabalhosAdapter(Context context, int layoutResourceId, ArrayList<Contact> data) {
super(context, layoutResourceId, data);
this.layoutResourceId = layoutResourceId;
this.context = context;
this.data = data;
}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
		
	
View row = convertView;

ImageHolder holder = null;
if(row == null)
{
LayoutInflater inflater = ((Activity)context).getLayoutInflater();
row = inflater.inflate(layoutResourceId, parent, false);
holder = new ImageHolder();
holder.numOrdem = (TextView)row.findViewById(R.id.numOrdem);
holder.Descricao = (TextView)row.findViewById(R.id.Descricao);
holder.thumb = (ImageView)row.findViewById(R.id.thumb);
holder.ListaCliente = (TextView)row.findViewById(R.id.ListaCliente);
holder.listaId = (TextView)row.findViewById(R.id.ListaId);
holder.novo = (TextView)row.findViewById(R.id.novo);


row.setTag(holder);
}
else
{
holder = (ImageHolder)row.getTag();
}
Contact picture = data.get(position);
holder.numOrdem.setText("Ordem de servi�o: "+picture ._name+" ");
holder.Descricao.setText(funcoes.limitaLetras(picture ._descricao, 25));
if(picture._cliente == null){

holder.ListaCliente.setText("Cliente: "+ context.getResources().getString(R.string.excluido));
}else{
	holder.ListaCliente.setText(funcoes.limitaLetras("Cliente: "+picture ._cliente, 25));	
}
holder.listaId.setText(picture ._idv);

if(picture._image == null){

	}else{
	        byte[] outImage=picture._image;
	        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
	        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
	        holder.thumb.setImageBitmap(theImage);
	}

if(picture._entrada == 0){
	row.setBackgroundColor(row.getResources().getColor(R.color.verde_agua));
	//row.setBackgroundColor(Color.parseColor("#CFFDD4"));
	holder.novo.setVisibility(View.VISIBLE);
}
/**
if (position % 2 == 0) {
	row.setBackgroundColor(Color.WHITE);
} else {
	row.setBackgroundColor(Color.parseColor("#f3f3f3"));
}
**/
return row;
}
static class ImageHolder
{
ImageView thumb;
TextView listaId, numOrdem, txtTitle, Descricao, ListaCliente, novo;
}


}




