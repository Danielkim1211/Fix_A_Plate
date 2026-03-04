package com.example.greenplate.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.greenplate.models.Firebase;
import com.example.greenplate.models.Ingredient;
import com.example.greenplate.models.ShoppingListItem;
import com.example.greenplate.models.User;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;


public class ShoppingListViewModel extends ViewModel {
    private static Firebase firebase = Firebase.getInstance();
    private static User user = FirebaseViewModel.getInstance().getUser();
    private static ArrayList<ShoppingListItem> selectedItems = new ArrayList<>();


    public static void addShoppingListItem(String item, int quantity) {
        for (ShoppingListItem existingItem : user.getShoppingList()) {
            if (existingItem.getName().equalsIgnoreCase(item)) {
                updateShoppingListItemQuantity(item, existingItem.getQuantity() + quantity);
                return;
            }
        }
        boolean found = false;
        ShoppingListItem newShoppingListItem = new ShoppingListItem(item, quantity);

        for (ShoppingListItem i : user.getShoppingList()) {
            if (i.getName().equalsIgnoreCase(newShoppingListItem.getName())) {
                updateShoppingListItemQuantity(item, quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            DatabaseReference ref = firebase.getDatabase().getReference()
                    .child("users").child(user.getUserId()).child("shoppingList").push();
            newShoppingListItem.setId(ref.getKey());
            FirebaseViewModel.getInstance().getUser().addShoppingListItem(newShoppingListItem);
            ref.setValue(newShoppingListItem.toMap());
        }
    }

    public static void updateShoppingListItemQuantity(String name, int quantity) {
        ShoppingListItem existingItem = null;
        for (ShoppingListItem item : user.getShoppingList()) {
            if (item.getName().equalsIgnoreCase(name)) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            existingItem.setQuantity(quantity);
            firebase.getDatabase().getReference().child("users").child(user.getUserId())
                    .child("shoppingList").child(existingItem.getId())
                    .child("quantity").setValue(quantity);
            return;
        } else {
            addShoppingListItem(name, quantity);
        }
        int newQuantity = quantity;
        String itemId = "";

        for (ShoppingListItem item : user.getShoppingList()) {
            if (item.getName().equals(name)) {
                newQuantity += item.getQuantity();
                itemId = item.getId();
            }
        }

        if (newQuantity <= 0) {
            firebase.getDatabase().getReference().child("users").child(user.getUserId())
                    .child("shoppingList").child(itemId).removeValue();
            user.removeShoppingListItem(itemId);
        } else if (itemId.isEmpty()) {
            addShoppingListItem(name, quantity);
        } else {
            firebase.getDatabase().getReference().child("users").child(user.getUserId())
                    .child("shoppingList").child(itemId).child("quantity").setValue(newQuantity);

            for (ShoppingListItem item : user.getShoppingList()) {
                if (item.getId().equals(itemId)) {
                    item.setQuantity(newQuantity);
                    break;
                }
            }
        }
    }

    public static void purchaseItems() {
        Firebase firebase = Firebase.getInstance();
        DatabaseReference databaseReference = firebase.getDatabase().getReference();

        for (ShoppingListItem shoppingItem : selectedItems) {
            Ingredient existingIngredient = user.findIngredient(shoppingItem.getName());

            if (existingIngredient != null) {
                existingIngredient.setQuantity(existingIngredient.getQuantity()
                        + shoppingItem.getQuantity());
                databaseReference.child("pantry").child(existingIngredient.getId())
                        .setValue(existingIngredient.getMap());
            } else {
                Ingredient newIngredient = new Ingredient(shoppingItem.getName(), 0,
                        shoppingItem.getQuantity(), user.getUserId());
                DatabaseReference newIngredientRef = databaseReference.child("pantry").push();
                String newIngredientId = newIngredientRef.getKey();
                newIngredient.setId(newIngredientId);
                newIngredientRef.setValue(newIngredient.getMap());
                user.addIngredient(newIngredient);
            }
            databaseReference.child("users").child(user.getUserId())
                    .child("shoppingList").child(shoppingItem.getId()).removeValue();
            user.removeShoppingListItem(shoppingItem.getId());
        }

        selectedItems.clear();

    }

    public static void selectItem(ShoppingListItem item) {
        if (!selectedItems.contains(item)) {
            selectedItems.add(item);
        }
    }

    public static void unselectItem(ShoppingListItem item) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item);
        }
    }

    public static ArrayList<ShoppingListItem> getSelectedItems() {
        return selectedItems;
    }


}

