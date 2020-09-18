package org.infosystema.peakcoin.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.PersistenceException;

import org.infosystema.peakcoin.beans.FilterExample;
import org.infosystema.peakcoin.beans.SortEnum;
import org.infosystema.peakcoin.domain.PersistentEntity;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public interface GenericService<T extends PersistentEntity<ID>, ID extends Serializable>  {
	
	/**
     * @param entity
     * @return
     */
    T persist(T entity) throws PersistenceException;
 
    /**
     * @param entity
     */
    void remove(T entity);
    
    /**
     * @param entity
     */
    T merge(T entity);
    
    /**
     * @param refresh
     */
    void refresh(T entity);
    
    /**
     * @return
     */
    long countAll();
    
    /**
     * @param id
     * @param lock
     * @return
     */
    T findById(ID id, boolean lock);
    
    /**
     * @param id
     * @param fields fields that include
     * @return
     */
	T getByIdWithFields(ID id, String[] fields);
	
	/**
	 * 
	 * @param id
	 * @param graphs
	 * @return
	 */
	T getByIdWithGraphs(ID id, String[] graphs);
	
	/***
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	Long countByProperty(String property, Object value);
	
	/***
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	List<T> findByProperty(String property, Object value);
	
	/***
	 * 
	 * @param property
	 * @param value
	 * @param fields
	 * @return
	 */
	List<T> findByPropertyWithFields(String property, Object value, String[] fields);
	
	/***
	 * 
	 * @param property
	 * @param value
	 * @param fields
	 * @return
	 */
	List<T> fingByPropertyWithGraphs(String property, Object value, String[] graphs);
	
    /**
     * @return
     */
    List<T> findAll();
    
    /**
     * @param firstResult
     * @param maxResults
     * @return
     */
    List<T> findEntries(int firstResult, int maxResults);
    
    /**
     * @param firstResult
     * @param maxResults
     * @param sort
     * @return
     */
    List<T> findEntries(int firstResult, int maxResults, String sortProperty, SortEnum sort);
    
    /**
     * 
     * @param firstResult
     * @param maxResults
     * @param sortProperty
     * @param sort
     * @param fetchProperties
     * @return
     */
    List<T> findEntries(int firstResult, int maxResults, String sortProperty, SortEnum sort, String[] graphs);
    
    /**
     * 
     * @param list
     * @return
     */
    Long countByExample(List<FilterExample> list);
    
    
    /**
     * 
     * @param property
     * @param list
     * @return
     */

    <U> U  sumByExample(String property, Class<U> class1, List<FilterExample> list);
    
    /**
     * 
     * @param from
     * @param to
     * @param sortEnum
     * @param list
     * @param sortProperty
     * @return
     */
    List<T> findByExample(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty);
    
    /**
     * 
     * @param from
     * @param to
     * @param sortEnum
     * @param list
     * @param sortProperty
     * @return
     */
    List<T> findByExample(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty, String[] fields);
    
    /**
     * 
     * @param from
     * @param to
     * @param sortEnum
     * @param list
     * @param sortProperty
     * @param graphs
     * @return
     */
    List<T> findByExampleWithGraphs(int from, int to, SortEnum sortEnum, List<FilterExample> list, String sortProperty, String[] graphs);

    /**
     * 
     * @return
     */
	Integer maxPosition();
    
    /**
     * 
     * @param entity
     * @param modifiedEntity
     * @param notCheck
     * @return
     */
    List<String> getModifiedFields(T entity, T modifiedEntity, List<String> notCheck);

    /**
     * 
     * @param from
     * @param to
     * @param list
     * @return
     */
	List<T> findByExample(int from, int to, List<FilterExample> list);
}
