package cs477.gmu.lab3_ywaikar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listViewItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewItems = findViewById(R.id.listViewItems);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listViewItems.setAdapter(itemsAdapter);

        //  sum default ToDo items
        items.add("Hi github");
        items.add("Fall 2024");
        items.add("Clean the room");

        Button addItemButton = findViewById(R.id.buttonAddItem);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        Button deleteDoneButton = findViewById(R.id.buttonDeleteDone);
        deleteDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDoneItems();
            }
        });
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                modifyItem(position);
            }
        });
        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                confirmDelete(position);
                return true;
            }
        });
    }
    // for adding
    private void addItem() {
        EditText input = findViewById(R.id.editTextNewItem);
        String itemText = input.getText().toString();
        if (!itemText.isEmpty()) {
            itemsAdapter.add(itemText);
            input.setText(""); // Clear the input field
        }
    }
    //  to confirm and delete
    private void confirmDelete(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    items.remove(position);
                    itemsAdapter.notifyDataSetChanged();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }
    //  to modify the item when clicked
    private void modifyItem(int position) {
        String selectedItem = items.get(position);
        if (!selectedItem.startsWith("Done: ")) {
            String updatedItem = "Done: " + selectedItem;
            items.remove(position);
            items.add(updatedItem);
        }

        else {
            String updatedItem = selectedItem.replace("Done: ", "");
            items.remove(position);
            items.add(0, updatedItem);
        }
        itemsAdapter.notifyDataSetChanged();
    }
    private void deleteDoneItems() {
        // iteraereete backwards to avoid skipping items while removing
        for (int i = items.size() - 1; i >= 0; i--) {
            String item = items.get(i);
            if (item.startsWith("Done: ")) {
                items.remove(i);
            }
        }

        itemsAdapter.notifyDataSetChanged();
    }
}