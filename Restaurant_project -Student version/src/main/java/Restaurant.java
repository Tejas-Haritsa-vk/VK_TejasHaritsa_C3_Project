import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();
    private List<Item> order = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime current_time = getCurrentTime();
        if (current_time.isAfter(openingTime) && current_time.isBefore(closingTime)) {
            return true;
        }
        return false;
        //DELETE ABOVE STATEMENT AND WRITE CODE HERE
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return menu;
        //DELETE ABOVE RETURN STATEMENT AND WRITE CODE HERE
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    public List<Item> selectitems(List<String> items) {
        for (String item : items) {
            order.add(findItemByName(item));
        }
        return order;
    }

    public List<Item> deselectitems(List<String> items) {
        for (String item : items) {
            order.remove(findItemByName(item));
        }
        return order;
    }

    public int getTotal(List<String> order) throws nullOrderException {
        if (order.isEmpty()) throw new nullOrderException("");
        else{
            int total = 0;
            Item newitem;
            for (String item : order) {
                newitem = findItemByName(item);
                total+=newitem.getPrice();
            }
            return total;
        }
    }

    public List<Item> getOrder() {
        return order;
    }
}
