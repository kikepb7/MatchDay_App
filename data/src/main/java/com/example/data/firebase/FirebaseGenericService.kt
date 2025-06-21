package com.example.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirebaseGenericService<T : Any>(
    private val reference: DatabaseReference,
    private val basePath: String,
    private val clazz: Class<T>
) {

    fun createItem(item: T, key: String? = null): String {
        val ref = key?.let { reference.child(basePath).child(it) }
            ?: reference.child(basePath).push()
        val id = ref.key.orEmpty()

        ref.setValue(item)

        return id
    }

    fun createItemInPath(item: T, dynamicPath: String, key: String? = null): String {
        val ref = key?.let { reference.child(dynamicPath).child(it) }
            ?: reference.child(dynamicPath).push()
        val id = ref.key.orEmpty()

        ref.setValue(item)

        return id
    }

    fun getItemById(id: String): Flow<T?> = reference.child("$basePath/$id").snapshots.map { snapshot ->
            snapshot.getValue(clazz)
    }

    fun getAllItems(): Flow<List<T>> = reference.child(basePath).snapshots.map { snapshot ->
        snapshot.children.mapNotNull { it.getValue(clazz) }
    }

    fun updateItem(id: String, item: T) {
        reference.child("$basePath/$id").setValue(item)
    }

    fun deleteItem(id: String) {
        reference.child("$basePath/$id").removeValue()
    }
}