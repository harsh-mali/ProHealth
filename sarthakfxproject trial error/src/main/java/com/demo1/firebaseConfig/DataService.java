package com.demo1.firebaseConfig;

import com.demo1.nestedCollection.Diet;
import com.demo1.nestedCollection.Workout;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DataService {

    // Firestore instance for database operations
    private static Firestore db;

    // Static block to initialize Firebase when the class is loaded
    static {
        try {
            initializeFirebase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to initialize Firebase with service account credentials, i.e json file
    @SuppressWarnings("deprecation")
    private static void initializeFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("sarthakfxproject trial error\\src\\main\\resources\\java-fx-firebase-store-cbbc3-firebase-adminsdk-d7d9t-38a3c64333.json");

        // Configure Firebase options with the credentials
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            // Uncomment and set the Database URL if needed
            // .setDatabaseUrl("https:")
            .build();

        // Initialize Firebase app with the options
        FirebaseApp.initializeApp(options);
        // Get Firestore instance
        db = FirestoreClient.getFirestore();
    }

    // Method to add data to a specified collection and document
    public void addData(String collection, String document, Map<String, Object> data) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collection).document(document); //access that collection and that document
        ApiFuture<WriteResult> result = docRef.set(data);
        // Block until the write operation is complete
        result.get();
    }

    // Method to retrieve data from a specified collection and document
    public DocumentSnapshot getData(String collection, String document) throws ExecutionException, InterruptedException {
        try {
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            // Block until the read operation is complete and return the document snapshot
            return future.get();
        } catch (Exception e) {
            // Print the stack trace for debugging
            e.printStackTrace();
            // Re-throw the exception or handle it based on your application's needs
            throw e;
        }
    }

    // Method to authenticate a user by comparing the provided password with the stored password
    public boolean authenticateUser(String username, String password) throws ExecutionException, InterruptedException {
        // Retrieve the document for the user with the given username
        DocumentSnapshot document = db.collection("users").document(username).get().get();
        try{
            if (document.exists()) {
                // Get the stored password from the document
                String storedPassword = document.getString("password");
                // Compare the provided password with the stored password
                return password.equals(storedPassword);
            }
        }catch(Exception e){
            System.out.println("enter valid data");
        }
        return false;
    }

    // Add a workout
    public static void addWorkout(String username, Workout workout) throws ExecutionException, InterruptedException {
        CollectionReference workoutsRef = db.collection("users").document(username).collection("workouts");
        ApiFuture<WriteResult> result = workoutsRef.document(workout.getTimestamp()).set(workout);
        System.out.println("Update time : " + result.get().getUpdateTime());
    }

    // Add a diet
    public static void addDiet(String username, Diet diet) throws ExecutionException, InterruptedException {
        CollectionReference dietsRef = db.collection("users").document(username).collection("diets");
        ApiFuture<WriteResult> result = dietsRef.document(diet.getTimestamp()).set(diet);
        System.out.println("Update time : " + result.get().getUpdateTime());
        System.out.println("Diet timestamp : "+diet.getTimestamp());
    }

    public List<DocumentSnapshot> getUserDataInDescendingOrder(String username, String collectionName, String orderByField) 
        throws ExecutionException, InterruptedException {
    List<DocumentSnapshot> documentsList = new ArrayList<>();

    CollectionReference collection = db.collection("users").document(username).collection(collectionName);
    Query query = collection.orderBy(orderByField, Query.Direction.DESCENDING);

    ApiFuture<QuerySnapshot> querySnapshot = query.get();

    for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
        documentsList.add(document);
    }

    return documentsList;
}



}