package edu.uuerp;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.bumptech.glide.Glide;
import edu.uuerp.FacultyListRecyclerAdapter;
import edu.uuerp.databinding.LayoutFacultyItemBinding;
import java.util.ArrayList;

public class FacultyListRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<FacultyMember> facultyList;

    public FacultyListRecyclerAdapter(Context context, ArrayList<FacultyMember> facultyList) {
        this.context = context;
        this.facultyList = facultyList;
    }

    @Override
    public int getItemCount() {
        return 8; // facultyList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder facultyItemViewHolder, int position) {
        if (true) return;
        FacultyItemViewHolder facultyItemView = (FacultyItemViewHolder) facultyItemViewHolder;
        FacultyMember facultyMember = facultyList.get(position);

        facultyItemView.fullName.setText(facultyMember.getFullName());
        facultyItemView.codeName.setText(facultyMember.getCodeName());
        
        Glide.with(facultyItemView.profilePic)
                .load(facultyMember.getProfilePicUrl())
                .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_smile_face_48dp))
                .error(ContextCompat.getDrawable(context, R.drawable.ic_smile_face_48dp))
                .into(facultyItemView.profilePic);

        facultyItemView.itemView.setOnClickListener((view) -> {});
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
        return new FacultyItemViewHolder(
                LayoutFacultyItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false));
    }

    private class FacultyItemViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic;
        TextView fullName, codeName, titleOptional, primaryPhone;

        public FacultyItemViewHolder(LayoutFacultyItemBinding LayoutFacultyItemView) {
            super(LayoutFacultyItemView.getRoot());
            profilePic = LayoutFacultyItemView.profilePic;
            fullName = LayoutFacultyItemView.fullName;
            codeName = LayoutFacultyItemView.codeName;
            titleOptional = LayoutFacultyItemView.titleOptional;
            primaryPhone = LayoutFacultyItemView.primaryPhone;
            
            primaryPhone.setPaintFlags(primaryPhone.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        }
    }
}
