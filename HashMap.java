
public class HashMap<K,V> {

    private static class Node<K,V>{
        K key;
        V value;
        Node<K,V> next;
        public Node(K key, V value){
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    // I am initializing the fields
    private int capacity;
    // HashMap internally stores the bucket arrays 
    //for storing the key-value pairs. Each bucket can contain a linked list of nodes in case of collisions.
    private Node<K,V>[] buckets;
    private int size;

    // class constructor
    @SuppressWarnings("unchecked")
    public HashMap(){
        this.capacity = 16; // default capacity
        this.buckets = new Node[capacity];
        this.size = 0;
    }

    // Hash Function -> I am converting a key to a valid hash index 
    
    // *   key = "apple"
    //  *   "apple".hashCode() = some large integer (e.g. 93029210)
    //  *   Math.abs(93029210 % 16) = 10  → stored in bucket[10]
    private int hash(K key) {
        return Math.abs(key.hashCode() % capacity);
    }

    // Method add the put method 
    //  * HOW IT WORKS:
    //  *  1. Compute the bucket index using hash(key).
    //  *  2. Walk the linked list at that bucket.
    //  *     - If key already exists → update its value.
    //  *  3. If key not found → add a new Node at the head of the list.
    //  *
    //  * TIME COMPLEXITY: O(1) average
    //  */

    public void put(K key , V value){
        int index = hash(key);
        Node<K,V> head = buckets[index];
        Node<K,V> current = head;
        while(current!=null){
            if(current.key.equals(key)){
                current.value = value; // update the value if key already exists
                return;
            }
            current = current.next;
        }
        // I have not found the key for that , in that case I am adding a new node at the head of the list
        // Thats the reason for inserting it will take 0(n) time complexity
        Node<K,V> newNode = new Node<>(key,value);
        newNode.next = head;
        buckets[index] = newNode;
        size++; 
    }



    // Operation 2: get method which will return the value

    public V get(K key){
        int index = hash(key);
        Node<K,V> current = buckets[index];
        while(current!=null){
            if(current.key.equals(key)){
                return current.value; // return the value if key is found
            }
            current = current.next;
        }
        return null; // return null if key is not found
    }

    // Operation 3 will be contains
    public boolean containsKey(K key){
        return get(key) !=null; // if get returns null, it means the key is not present
    }

    public int size(){
        return size;
    }

    // Operation 4 : remove a key from hashmap 
    /**
     * @param key
     * @return
     */
    public V remove(K key){
       // first I need to find out the key 
       int index = hash(key);
       Node<K,V> current = buckets[index];
       // I have to delete so I need to keep a reference to the previous node as well
       Node<K,V> previous = null;
       while (current != null) {
           if (current.key.equals(key)) {
               if (previous == null) {
                   buckets[index] = current.next;
               }
               else{
                    previous.next = current.next;
               }
                size--;
                return current.value;
           }
           previous= current;
           current = current.next;
       }
        return null;
    }

    public void print(){
        // I need to print the hashmap;
        for(int i=0;i<capacity;i++){
            Node<K,V> current = buckets[i];
            while(current!=null){
                System.out.println(current.key+" : "+current.value);
                current = current.next;
            }
        }
    }


    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("orange", 3);

        System.out.println(map.get("apple")); // Output: 1
        System.out.println(map.get("banana")); // Output: 2
        System.out.println(map.get("grape")); // Output: null

        System.out.println(map.containsKey("orange")); // Output: true
        System.out.println(map.containsKey("grape")); // Output: false
        System.out.println("My Hashmap:");
        map.print();
        map.remove("banana");
        System.out.println("After removing banana:");
        map.print();
    }


}
