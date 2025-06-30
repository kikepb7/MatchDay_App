package com.example.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseFirestoreGenericService<T : Any>(
    private val firestore: FirebaseFirestore,
    private val collectionPath: String,
    private val clazz: Class<T>
) {

    suspend fun createItem(item: T, key: String? = null): String {
        val ref = key?.let { firestore.collection(collectionPath).document(it) }
            ?: firestore.collection(collectionPath).document()
        ref.set(item).await()
        return ref.id
    }

    suspend fun createItemInPath(item: T, dynamicPath: String, key: String? = null): String {
        val ref = key?.let { firestore.document("$dynamicPath/$it") }
            ?: firestore.collection(dynamicPath).document()
        ref.set(item).await()
        return ref.id
    }

    fun getItemById(id: String): Flow<T?> = callbackFlow {
        val registration = firestore.collection(collectionPath).document(id)
            .addSnapshotListener { snapshot, _ ->
                trySend(snapshot?.toObject(clazz))
            }

        awaitClose { registration.remove() }
    }

    suspend fun getLastItem(id: String): T? {
        return firestore.collection(collectionPath)
            .orderBy(id, Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await().firstOrNull()?.toObject(clazz)
    }

    fun getItemByIdFromCache(id: String): Flow<T?> = flow {
        val snapshot = firestore
            .collection(collectionPath)
            .document(id)
            .get(Source.CACHE)
            .await()

        emit(snapshot.toObject(clazz))
    }

    fun getAllItems(): Flow<List<T>> = callbackFlow {
        val registration = firestore.collection(collectionPath)
            .addSnapshotListener { snapshot, _ ->
                val items = snapshot?.toObjects(clazz).orEmpty()
                trySend(items)
            }

        awaitClose { registration.remove() }
    }

    fun getAllItemsFromCache(): Flow<List<T>> = flow {
        val snapshot = firestore
            .collection(collectionPath)
            .get(Source.CACHE)
            .await()

        val items = snapshot.toObjects(clazz)
        emit(items)
    }

    fun getItemRealtime(id: String): Flow<T?> = callbackFlow {
        val listenerRegistration = firestore.collection(collectionPath)
            .document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                trySend(snapshot?.toObject(clazz))
            }

        awaitClose { listenerRegistration.remove() }
    }

    fun getAllItemsRealtime(): Flow<List<T>> = callbackFlow {
        val listenerRegistration = firestore.collection(collectionPath)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                trySend(snapshot?.toObjects(clazz) ?: emptyList())
            }

        awaitClose { listenerRegistration.remove() }
    }

    suspend fun updateItem(id: String, item: T) {
        firestore.collection(collectionPath).document(id).set(item).await()
    }

    suspend fun updateField(id: String, field: String, value: Any) {
        firestore.collection(collectionPath).document(id).update(field, value).await()
    }

    suspend fun updateFields(id: String, fields: Map<String, Any?>) {
        firestore.collection(collectionPath).document(id).update(fields).await()
    }

    suspend fun deleteItem(id: String) {
        firestore.collection(collectionPath).document(id).delete().await()
    }

    fun getItemsByField(field: String, value: Any): Flow<List<T>> = flow {
        val snapshot = firestore
            .collection(collectionPath)
            .whereEqualTo(field, value)
            .get()
            .await()

        val items = snapshot.toObjects(clazz)

        emit(items)
    }

    fun getItemsFromSubcollection(parentPath: String): Flow<List<T>> = flow {
        try {
            val snapshot = firestore
                .collection(parentPath)
                .get(Source.SERVER)
                .await()

            emit(snapshot.toObjects(clazz))
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    fun queryByFieldEqualTo(field: String, value: Any): Flow<List<T>> = flow {
        val snapshot = firestore.collection(collectionPath)
            .whereEqualTo(field, value)
            .get()
            .await()

        emit(snapshot.toObjects(clazz))
    }

    fun queryByFieldGreaterThan(field: String, value: Any): Flow<List<T>> = flow {
        val snapshot = firestore.collection(collectionPath)
            .whereGreaterThan(field, value)
            .get()
            .await()

        emit(snapshot.toObjects(clazz))
    }

    fun queryByMultipleConditions(conditions: List<Triple<String, String, Any>>): Flow<List<T>> = flow {
        var query: Query = firestore.collection(collectionPath)

        for ((field, op, value) in conditions) {
            query = when (op) {
                "==" -> query.whereEqualTo(field, value)
                ">" -> query.whereGreaterThan(field, value)
                ">=" -> query.whereGreaterThanOrEqualTo(field, value)
                "<" -> query.whereLessThan(field, value)
                "<=" -> query.whereLessThanOrEqualTo(field, value)
                "array-contains" -> query.whereArrayContains(field, value)
                else -> query
            }
        }

        val snapshot = query.get().await()
        emit(snapshot.toObjects(clazz))
    }
}