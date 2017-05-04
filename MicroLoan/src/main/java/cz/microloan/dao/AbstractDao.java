package cz.microloan.dao;

/**
 * @author Jan Tolar
 *
 * Interface for common db operations with generic object 
 */
public interface AbstractDao<T extends Object> {

	/**
	 * Create generic object
	 * @param object
	 */
	void create(T object);

	/**
	 * Update generic object
	 * @param object
	 */
	void update(T object);

	/**
	 * Remove generic object
	 * @param object
	 */
	void remove(T object);

}
