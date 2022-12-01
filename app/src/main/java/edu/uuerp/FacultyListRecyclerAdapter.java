package edu.uuerp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import edu.uuerp.FacultyListRecyclerAdapter;
import edu.uuerp.FacultyMember;
import edu.uuerp.databinding.LayoutFacultyItemBinding;
import java.util.ArrayList;

public class FacultyListRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<FacultyMember> facultyList;

    public FacultyListRecyclerAdapter(Context context, ArrayList<FacultyMember> facultyList) {
        this.context = context;
        this.facultyList = new ArrayList<>();
        this.facultyList.addAll(facultyList);
    }
    
    public void updateData(ArrayList<FacultyMember> newData){
        facultyList.clear();
        facultyList.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return facultyList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder facultyItemViewHolder, int position) {

        FacultyItemViewHolder facultyItemView = (FacultyItemViewHolder) facultyItemViewHolder;
        FacultyMember facultyMember = facultyList.get(position);

        facultyItemView.fullName.setText(facultyMember.getFullName());
        facultyItemView.codeName.setText(facultyMember.getCodeName());
        facultyItemView.primaryPhone.setText(facultyMember.getPrimaryNumber());
        facultyItemView.titleOptional.setText(facultyMember.getTitle());

        facultyItemView.titleOptional.setVisibility(
                facultyMember.hasTitle() ? View.VISIBLE : View.GONE);

        Glide.with(facultyItemView.profilePic)
                .load(facultyMember.getProfilePicUrl())
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_smile_face_48dp))
                .error(ContextCompat.getDrawable(context, R.drawable.ic_smile_face_48dp))
                .into(facultyItemView.profilePic);

        facultyItemView.callPrimaryButton.setOnClickListener(
                (view) -> {
                    context.startActivity(
                            new Intent(
                                    Intent.ACTION_DIAL,
                                    Uri.fromParts("tel", facultyMember.getPrimaryNumber(), null)));
                });

        facultyItemView.itemView.setOnClickListener((view) -> {});
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        return new FacultyItemViewHolder(
                LayoutFacultyItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false));
    }

    private class FacultyItemViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView profilePic;
        TextView fullName, codeName, titleOptional, primaryPhone;
        MaterialButton callPrimaryButton;

        public FacultyItemViewHolder(LayoutFacultyItemBinding LayoutFacultyItemView) {
            super(LayoutFacultyItemView.getRoot());
            profilePic = LayoutFacultyItemView.profilePic;
            fullName = LayoutFacultyItemView.fullName;
            codeName = LayoutFacultyItemView.codeName;
            titleOptional = LayoutFacultyItemView.titleOptional;
            primaryPhone = LayoutFacultyItemView.primaryPhone;
            callPrimaryButton = LayoutFacultyItemView.callPrimaryButton;
        }
    }
}
