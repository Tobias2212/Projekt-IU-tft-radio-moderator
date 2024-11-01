package de.iu.tftradiomoderator.data.provider

class RadioMemoryProvider<T> {

    private var cache: T? = null

    fun cacheAndRetrieve(data: T): T {
        cache = data
        println("Caching data: $data")
        return data
    }

    fun retrieve(): T? {
        println("Retrieving data from cache: $cache")
        return cache
    }

    fun clean() {
        println("Cache is being cleared.")
        cache = null
    }


}