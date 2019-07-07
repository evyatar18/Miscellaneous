typedef size_t int_t;

struct map_node {
    void* key;
    void* data;
    struct map_node* next;
};

struct hashmap {
    int_t (*hash)(void* key);
    int_t length;
    struct map_node** array;
};

/*
 * finds the data of a given key inside a given map_node linked list
 */
void* mapnode_find_data(struct map_node* node, void* key) {
    while (node) {
        if (node->key == key) {
            return node->data;
        }

        node = node->next;
    }

    return NULL;
}


// if i lay here
// if i just lay here
// would lay with me and just forget the world?

/*
 * clears all records on the hashmap.
 * does not free memory!
 */
void hashmap_clear(struct hashmap* map) {
    int_t index = map->length;

    // need to make sure whether this is cache-friendly
    // if subsequent addresses are loaded to cache, this is really bad
    while (index--) {
        map->array[index] = NULL;
    }
}
