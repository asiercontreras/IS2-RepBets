package businessLogic;

import java.util.Iterator;
import java.util.List;

public class ExtendedIterator<Object> implements Iterator<Object> {
	private List<Object> eventList;
	private int currentIndex;

	public ExtendedIterator(List<Object> events) {
		this.eventList = events;
		this.currentIndex = 0;
	}

	public Object previous() {
		if (hasPrevious()) {
			return eventList.get(--currentIndex);
		}
		return null;
	}

	public boolean hasPrevious() {
		return currentIndex > 0;
	}

	public void goFirst() {
		currentIndex = -1;
	}

	public void goLast() {
		currentIndex = eventList.size();
	}

	public boolean hasNext() {
		return currentIndex < eventList.size() - 1;
	}

	public Object next() {
		if (hasNext()) {
			return eventList.get(++currentIndex);
		}
		return null; 
	}

}
