package ar.com.santander.pantallas;

public class Screen {
	
	public Start start;
	public Search search;
	public Item item;
	
	public Screen() {
		
		start = new Start();
		search = new Search();
		item = new Item();
		 
	}

}
