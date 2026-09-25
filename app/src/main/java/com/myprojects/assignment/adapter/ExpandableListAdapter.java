package com.myprojects.assignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myprojects.assignment.R;
import com.myprojects.assignment.models.Cabinet;
import com.myprojects.assignment.models.Company;
import com.myprojects.assignment.models.Medicine;
import com.myprojects.assignment.models.Shelf;

import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter<ExpandableListAdapter.ViewHolder> {

    private final Context context;
    private List<Company> companies;

    public void setFilterList(List<Company>filterList){
        this.companies=filterList;
        notifyDataSetChanged();
    }
    public ExpandableListAdapter(Context context, List<Company> companies) {
        this.context = context;
        this.companies = companies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expandable_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Company company = companies.get(position);
        holder.bind(company);
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public void updateData(List<Company> newCompanies) {
        this.companies = newCompanies;
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView companyName;
        private final TextView companyDescription;
        private final RecyclerView cabinetRecyclerView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.company_name);
            companyDescription = itemView.findViewById(R.id.company_description);
            cabinetRecyclerView = itemView.findViewById(R.id.cabinet_recycler_view);
        }

        void bind(Company company) {
            companyName.setText(company.getName());
            companyDescription.setText(company.getDescription());

            CabinetAdapter cabinetAdapter = new CabinetAdapter(company.getCabinets());
            cabinetRecyclerView.setAdapter(cabinetAdapter);
            cabinetRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

    static class CabinetAdapter extends RecyclerView.Adapter<CabinetAdapter.CabinetViewHolder> {
        private final List<Cabinet> cabinets;

        CabinetAdapter(List<Cabinet> cabinets) {
            this.cabinets = cabinets;
        }

        @NonNull
        @Override
        public CabinetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cabinet_list_item, parent, false);
            return new CabinetViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CabinetViewHolder holder, int position) {
            Cabinet cabinet = cabinets.get(position);
            holder.bind(cabinet);
        }

        @Override
        public int getItemCount() {
            return cabinets.size();
        }

        static class CabinetViewHolder extends RecyclerView.ViewHolder {
            private final TextView cabinetName;
            private final TextView cabinetDescription;
            private final RecyclerView shelfRecyclerView;

            CabinetViewHolder(@NonNull View itemView) {
                super(itemView);
                cabinetName = itemView.findViewById(R.id.cabinet_name);
                cabinetDescription = itemView.findViewById(R.id.cabinet_description);
                shelfRecyclerView = itemView.findViewById(R.id.shelf_recycler_view);
            }

            void bind(Cabinet cabinet) {
                cabinetName.setText(cabinet.getName());
                cabinetDescription.setText(cabinet.getDescription());

                ShelfAdapter shelfAdapter = new ShelfAdapter(cabinet.getShelves());
                shelfRecyclerView.setAdapter(shelfAdapter);
                shelfRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            }
        }
    }

    static class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ShelfViewHolder> {
        private final List<Shelf> shelves;

        ShelfAdapter(List<Shelf> shelves) {
            this.shelves = shelves;
        }

        @NonNull
        @Override
        public ShelfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shelf_list_item, parent, false);
            return new ShelfViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ShelfViewHolder holder, int position) {
            Shelf shelf = shelves.get(position);
            holder.bind(shelf);
        }

        @Override
        public int getItemCount() {
            return shelves.size();
        }

        static class ShelfViewHolder extends RecyclerView.ViewHolder {
            private final TextView shelfName;
            private final TextView shelfDescription;
            private final RecyclerView medicineRecyclerView;

            ShelfViewHolder(@NonNull View itemView) {
                super(itemView);
                shelfName = itemView.findViewById(R.id.shelf_name);
                shelfDescription = itemView.findViewById(R.id.shelf_description);
                medicineRecyclerView = itemView.findViewById(R.id.medicine_recycler_view);
            }

            void bind(Shelf shelf) {
                shelfName.setText(shelf.getName());
                shelfDescription.setText(shelf.getDescription());

                MedicineAdapter medicineAdapter = new MedicineAdapter(shelf.getMedicines());
                medicineRecyclerView.setAdapter(medicineAdapter);
                medicineRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            }
        }
    }

    static class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
        private final List<Medicine> medicines;

        MedicineAdapter(List<Medicine> medicines) {
            this.medicines = medicines;
        }

        @NonNull
        @Override
        public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_list_item, parent, false);
            return new MedicineViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
            Medicine medicine = medicines.get(position);
            holder.bind(medicine);
        }

        @Override
        public int getItemCount() {
            return medicines.size();
        }

        static class MedicineViewHolder extends RecyclerView.ViewHolder {
            private final TextView medicineName;
            private final TextView medicineDescription;

            MedicineViewHolder(@NonNull View itemView) {
                super(itemView);
                medicineName = itemView.findViewById(R.id.medicine_name);
                medicineDescription = itemView.findViewById(R.id.medicine_description);
            }

            void bind(Medicine medicine) {
                medicineName.setText(medicine.getName());
                medicineDescription.setText(medicine.getDescription());
            }
        }
    }
}
