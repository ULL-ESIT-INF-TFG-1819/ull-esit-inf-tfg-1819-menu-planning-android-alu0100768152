package com.example.tfg3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg3.SQLite.AdminSQLiteOpenHelper;

import java.util.List;

public class AdapterListaCompra extends ArrayAdapter<Row> implements View.OnClickListener{
    private LayoutInflater layoutInflater;

    public AdapterListaCompra(Context context, List<Row> objects)
    {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // holder pattern
        Holder holder = null;
        if (convertView == null)
        {
            holder = new Holder();

            convertView = layoutInflater.inflate(R.layout.item_lista, null);
            holder.setTextViewTitle((TextView) convertView.findViewById(R.id.idIngrediente));
            holder.setCheckBox((CheckBox) convertView.findViewById(R.id.idcheck));
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        Row row = getItem(position);
        holder.getTextViewTitle().setText(row.getTitle());
        holder.getCheckBox().setTag(position);
        holder.getCheckBox().setChecked(row.isChecked());
        holder.getCheckBox().setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        CheckBox checkBox = (CheckBox) v;
        int position = (Integer) v.getTag();
        getItem(position).setChecked(checkBox.isChecked());

        String msg = this.getContext().getString(R.string.check_toast,
                position, checkBox.isChecked());
        String[] parts = msg.split("[:]");

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this.getContext(), "datos", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        Cursor fila = BaseDeDatos.rawQuery("select id_plato, ingrediente, gramos from lista_ingredientes where id="+getItem(position).getId(), null);

        int id_plato = 0;
        String ingrediente = "";
        double gramos = 0.0;
        String tiene = "no";

        if(fila.moveToFirst()) {

            id_plato = Integer.parseInt(fila.getString(0));
            ingrediente = fila.getString(1);
            gramos = Double.valueOf(fila.getString(2)).doubleValue();
        }

        if(parts[1].equals("true")){
            tiene="si";
        }

        if(parts[1].equals("false")){
            tiene="no";
        }

        ContentValues registro = new ContentValues();

        registro.put("id", getItem(position).getId());
        registro.put("id_plato", id_plato);
        registro.put("ingrediente",ingrediente);
        registro.put("gramos", gramos);
        registro.put("tiene", tiene);

        BaseDeDatos.update("lista_ingredientes", registro, "id=" + getItem(position).getId(), null);
        BaseDeDatos.close();
        Toast.makeText(this.getContext(),""+getItem(position).getId(), Toast.LENGTH_SHORT).show();
    }

    static class Holder
    {
        TextView textViewTitle;
        CheckBox checkBox;

        public TextView getTextViewTitle()
        {
            return textViewTitle;
        }

        public void setTextViewTitle(TextView textViewTitle)
        {
            this.textViewTitle = textViewTitle;
        }

        public CheckBox getCheckBox()
        {
            return checkBox;
        }
        public void setCheckBox(CheckBox checkBox)
        {
            this.checkBox = checkBox;
        }

    }
}