package com.example.quang.studenthousing.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.quang.studenthousing.R;
import com.example.quang.studenthousing.object.House;
import com.example.quang.studenthousing.services.APIClient;
import com.example.quang.studenthousing.services.DataClient;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridViewHouseUploadedAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<House> arrItem;
    private Dialog dialog;

    public GridViewHouseUploadedAdapter(Context context, int layout, ArrayList<House> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder {
        TextView tvTitle;
        ImageView imView;
        TextView tvPrice;
        TextView tvAcreage;
        ImageView imMore;
    }

    @Override
    public int getCount() {
        return arrItem.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewRow = view;
        if (viewRow == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                viewRow = inflater.inflate(layout, viewGroup, false);
            }

            ViewHolder holder = new ViewHolder();
            if (viewRow != null) {
                holder.tvTitle = viewRow.findViewById(R.id.tvItemTitleRequest);
            }
            if (viewRow != null) {
                holder.tvPrice = viewRow.findViewById(R.id.tvItemPriceRequest);
            }
            if (viewRow != null) {
                holder.tvAcreage = viewRow.findViewById(R.id.tvItemAcreageRequest);
            }
            if (viewRow != null) {
                holder.imView = viewRow.findViewById(R.id.imItemHouseRequest);
            }
            if (viewRow != null) {
                holder.imMore = viewRow.findViewById(R.id.more_request);
            }

            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        House house = arrItem.get(i);
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }
        if (holder != null) {
            holder.tvTitle.setText(house.getTITLE());
        }
        if (holder != null) {
            holder.tvPrice.setText(house.getPRICE() + " " + context.getString(R.string.million));
        }
        if (holder != null) {
            holder.tvAcreage.setText(house.getACREAGE() + " " + context.getString(R.string.meter2));
        }
        if (holder != null) {
            Glide.with(context).load(APIClient.BASE_URL + house.getIMAGE()).into(holder.imView);
        }

        ViewHolder finalHolder = holder;
        holder.imMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, finalHolder.imMore);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.menu_edit_post, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                initDialog(house);
                                break;
                            case R.id.delete:
                                delete(house.getIDHOUSE());
                                break;
                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        return viewRow;
    }

    //xoa bai da dang
    private void delete(int idHouse) {
        DataClient dataClient = APIClient.getData();
        Call<String> callBack = dataClient.deleteHouse(idHouse);
        callBack.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("success")) {
                    ((Activity) context).finish();
                } else if (response.body().equals("fail")) {
                    Toast.makeText(context, R.string.fail, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initDialog(House house) {
        dialog = new Dialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_edit_info);
        dialog.setCancelable(false);

        EditText edtTitle = dialog.findViewById(R.id.edtTitleEdit);
        EditText edtCity = dialog.findViewById(R.id.edtCityEdit);
        EditText edtDistrict = dialog.findViewById(R.id.edtDistrictEdit);
        EditText edtWard = dialog.findViewById(R.id.edtWardEdit);
        EditText edtStreet = dialog.findViewById(R.id.edtStreetEdit);
        RadioButton radObjectMale = dialog.findViewById(R.id.radMaleEdit);
        RadioButton radObjectFemale = dialog.findViewById(R.id.radFemaleEdit);
        RadioButton radObjectBoth = dialog.findViewById(R.id.radBothEdit);
        EditText edtDesc = dialog.findViewById(R.id.edtDescEdit);
        EditText edtPhone = dialog.findViewById(R.id.edtPhoneEdit);
        EditText edtAcreage = dialog.findViewById(R.id.edtAcreageEdit);
        TextView tvAcreage = dialog.findViewById(R.id.tvAcreageEdit);
        EditText edtPrice = dialog.findViewById(R.id.edtPriceEdit);
        TextView tvPrice = dialog.findViewById(R.id.tvPriceEdit);
        EditText edtMaxPeo = dialog.findViewById(R.id.edtMaxpeoEdit);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdateEdit);
        Button btnBack = dialog.findViewById(R.id.btnBackEdit);
        RadioButton radBooked = dialog.findViewById(R.id.radBookedEdit);
        RadioButton radUnBook = dialog.findViewById(R.id.radUnbookEdit);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        edtTitle.setText(house.getTITLE());

        String address = house.getADDRESS();
        String[] arrAddress = address.split(", ");

        String city = arrAddress[arrAddress.length - 1];
        String district = arrAddress[arrAddress.length - 2];
        String ward = arrAddress[arrAddress.length - 3];
        String street = arrAddress[arrAddress.length - 4];

        edtCity.setText(city);
        edtDistrict.setText(district);
        edtWard.setText(ward);
        edtStreet.setText(street);
        edtCity.setEnabled(false);
        edtDistrict.setEnabled(false);
        edtWard.setEnabled(false);

        if (house.getOBJECT() == 1) {
            radObjectMale.setChecked(true);
        } else if (house.getOBJECT() == 0) {
            radObjectFemale.setChecked(true);
        } else {
            radObjectBoth.setChecked(true);
        }

        if (house.getSTATE() == 1) {
            radBooked.setChecked(true);
        } else {
            radUnBook.setChecked(true);
        }

        edtDesc.setText(house.getDESC());
        edtPhone.setText(house.getCONTACT());
        edtAcreage.setText(house.getACREAGE() + "");
        edtPrice.setText(house.getPRICE() + "");
        edtMaxPeo.setText(house.getMAXPEO() + "");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = edtTitle.getText().toString();
                c = edtCity.getText().toString();
                d = edtDistrict.getText().toString();
                w = edtWard.getText().toString();
                s = edtStreet.getText().toString();
                desc = edtDesc.getText().toString();
                phone = edtPhone.getText().toString();
                if (radObjectMale.isChecked()) {
                    object = 1;
                } else if (radObjectFemale.isChecked()) {
                    object = 0;
                } else {
                    object = 2;
                }

                if (radBooked.isChecked()) {
                    state = 1;
                } else {
                    state = 0;
                }

                id = house.getIDHOUSE();

                maxpeo = Integer.parseInt(edtMaxPeo.getText().toString());

                price = Float.parseFloat(edtPrice.getText().toString());
                acreage = Float.parseFloat(edtAcreage.getText().toString());

                if (title.isEmpty() || s.isEmpty() || desc.isEmpty() || phone.isEmpty()
                        || edtMaxPeo.getText().toString().isEmpty() || edtPrice.getText().toString().isEmpty()
                        || edtAcreage.getText().toString().isEmpty()) {
                    Snackbar snackbar = Snackbar
                            .make(edtDesc, R.string.insert_info, Snackbar.LENGTH_LONG);
                    snackbar.show();

                    return;
                }

                GetLatLng getLatLng = new GetLatLng();
                getLatLng.execute(s + ", " + w + ", " + d + ", " + c);

            }
        });


        dialog.show();
    }

}