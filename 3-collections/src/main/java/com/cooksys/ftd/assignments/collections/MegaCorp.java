package com.cooksys.ftd.assignments.collections;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {

    HashSet<Capitalist> hierarchyParentSet = new HashSet<Capitalist>();
    HashSet<Capitalist> hierarchyChildrenSet = new HashSet<Capitalist>();
    HashSet<Capitalist> hierarchyElementSet = new HashSet<Capitalist>();
    
    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
    	if (has(capitalist)) {
    		return false;
    	}

    	hierarchyElementSet.add(capitalist);

    	if (capitalist instanceof FatCat && !capitalist.hasParent()) {
    		hierarchyParentSet.add(capitalist);
    		return true;
    	} else if (!(capitalist instanceof FatCat) && !capitalist.hasParent()) {
    		return false;
    	} else {
            while (capitalist.hasParent()){
        		hierarchyChildrenSet.add(capitalist);
    	    	capitalist = capitalist.getParent();
    	    }
            hierarchyParentSet.add(capitalist);
    	}
    	return true;
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
        return (hierarchyElementSet.contains(capitalist));
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
        return hierarchyElementSet;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	HashSet<FatCat> newSet = new HashSet<FatCat>();
        for (Capitalist temp : hierarchyParentSet) {
        	FatCat newTemp = ((FatCat) temp);
        	newSet.add(newTemp);
        }
        return newSet;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
        HashSet<Capitalist> childrenSet = new HashSet<Capitalist>();
        
        for (Capitalist children : hierarchyChildrenSet) {
        	if (children.getParent().equals(fatCat)) {
        		childrenSet.add(children);
        	}
        }
        
        return childrenSet;
    }

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	HashMap<FatCat, Set<Capitalist>> hierarchyMap = new HashMap<FatCat, Set<Capitalist>>();
    	for (Capitalist parent : hierarchyParentSet) {
    		HashSet<Capitalist> childrenSet = new HashSet<Capitalist>();
    	    for (Capitalist child : hierarchyChildrenSet) {
    	    	if (child.getParent().equals(parent)) {
    	    		childrenSet.add(child);
    	    	}
    	    }
    	    
    	    FatCat tempParent = (FatCat) parent;
    	    hierarchyMap.put(tempParent, childrenSet);
    	}
    	return hierarchyMap;
    	    
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
    	LinkedList<FatCat> parentChain = new LinkedList<FatCat>();
    	if (!capitalist.hasParent() || !has(capitalist) ) {
    		return parentChain;
    	}
    	
    	while (capitalist.hasParent() && capitalist.getParent() != null) {
    		parentChain.add(capitalist.getParent());
    		capitalist = capitalist.getParent();
    	}
    	
    	return parentChain;
    
    }
}
