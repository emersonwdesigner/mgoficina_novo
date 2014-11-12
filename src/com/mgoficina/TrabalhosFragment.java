package com.mgoficina;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.actionbarsherlock.app.SherlockListFragment;
import com.example.mgoficina.R;

public class TrabalhosFragment extends SherlockListFragment{
    
	TextView trab, icon;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		
		final DataBaseHandler db = new DataBaseHandler(getSherlockActivity());
		 
		String[] from = new String[] {DataBaseHandler.KEY_STATUS_NAME, DataBaseHandler.KEY_STATUS_INFO, DataBaseHandler.KEY_STATUS_ICON, DataBaseHandler.KEY_STATUS_KEY, DataBaseHandler.KEY_STATUS_KEY};
        
		//Toast.makeText(getSherlockActivity(), from[0], Toast.LENGTH_SHORT).show();
        // Ids of views in listview_layout
        int[] to = { R.id.txt,R.id.cur, R.id.flag, R.id.keyStatus, R.id.keys};        
        
        Cursor cursor = db.getAllStatus();
		
		@SuppressWarnings("deprecation")
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.listview_layout, cursor, from, to);
		setListAdapter(adapter);
		adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
			
			   /** Binds the Cursor column defined by the specified index to the specified view */
			   public boolean setViewValue(View view, Cursor cursor, int columnIndex){
			       if(view.getId() == R.id.keys){
			    	   Log.v("aviso",cursor.getString(columnIndex));
			    	   icon = (TextView) view.findViewById(R.id.keys);
			    	   String conta = String.valueOf(db.conta(cursor.getString(columnIndex)));
			    	   String total="";
			    	   if(conta.equals("1")){
			    		total = getString(R.string.item);  
			    	   }else{
			    		total = getString(R.string.itens);     
			    	   }
			    	   icon.setText(" "+conta+" "+ total);
			           
			           return true; //true because the data was bound to the view
			       }
			       return false;
			   }
			});		
		
		return super.onCreateView(inflater, container, savedInstanceState);	
		
	}
	
	 @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onActivityCreated(savedInstanceState);
	 
	        
	        OnItemClickListener listener = new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					TextView param = (TextView) arg1.findViewById(R.id.keyStatus);
					String var1 = param.getText().toString();
					//Toast.makeText( getActivity().getBaseContext()  , "Long Clicked " + var1 , Toast.LENGTH_SHORT).show();
					 
					
					Intent intent = new Intent(getActivity(), ListaTrabalhosActivity.class);
					
					intent.putExtra("STATUS", var1);
					intent.putExtra("TIPO", "os");
	                startActivity(intent);
				}
	        };
	 
	        getListView().setOnItemClickListener(listener);
	 
	    }

}

