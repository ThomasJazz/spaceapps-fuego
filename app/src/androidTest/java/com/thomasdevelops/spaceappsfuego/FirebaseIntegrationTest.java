package com.thomasdevelops.spaceappsfuego;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thomasdevelops.spaceappsfuego.pojo.FireReport;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static android.support.constraint.Constraints.TAG;

@RunWith(AndroidJUnit4.class)
public class FirebaseIntegrationTest {
    @Test(timeout = 10000)
    public void testCRUD() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        final String testReports = "reports_test";
        final String testReportUser = "testid1";
        final String testReportUserNew = "testId2";
        final FirebaseFirestore db = FirebaseFirestore.getInstance();  // DAO
        final FireReport report = new FireReport(testReportUser, 47.6062, 122.3321);
        // Create test
        db.collection(testReports)
                .add(report)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        // Read test
                        db.collection(testReports)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        Assert.assertEquals(true, task.isSuccessful());
                                        // verify the written data exists
                                        boolean foundTestData = false;
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            FireReport dbReport = document.toObject(FireReport.class);
                                            foundTestData |= dbReport.getCreator().equals(testReportUser);
                                        }
                                        Assert.assertEquals(true, foundTestData);
                                        // Update test
                                        report.setCreator(testReportUserNew);
                                        db.collection(testReports)
                                                .document(documentReference.getId())
                                                .set(report);
                                        // verify record updated
                                        documentReference.get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        Assert.assertEquals(true, task.isSuccessful());
                                                        DocumentSnapshot snapshot = task.getResult();
                                                        FireReport retrievedReport = snapshot.toObject(FireReport.class);
                                                        Assert.assertNotNull(retrievedReport);
                                                        Assert.assertEquals(true, retrievedReport.getCreator().equals(testReportUserNew));
                                                        // Delete test
                                                        documentReference.delete()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        Assert.assertEquals(true, task.isSuccessful());
                                                                        // verify deleted node
                                                                        documentReference.get()
                                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                        Assert.assertEquals(true, task.isSuccessful());
                                                                                        signal.countDown();
                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Assert.fail();
                        signal.countDown();
                    }
                });
        signal.await();
    }
}
