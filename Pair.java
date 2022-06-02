public class Pair<K, V> {
	public K key;
	public V value;

	public Pair(K k, V v) {
		this.key = k;
		this.value = v;
	}

	public String toString() {
		return String.format("(key=%s, value=%s)", key.toString(), value.toString());
	}
}