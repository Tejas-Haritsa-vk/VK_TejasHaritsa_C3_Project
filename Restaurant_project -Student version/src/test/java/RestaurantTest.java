import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    Restaurant restaurant;
    Restaurant R1 = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    List<String> order;
    List<Item> items;

    @BeforeEach
    public void BeforeEach(){
        restaurant = Mockito.spy(R1);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        order = new ArrayList<>();
        order.add("Sweet corn soup");
        order.add("Vegetable lasagne");

        items = new ArrayList<>();
        Item newItem = new Item("Sweet corn soup", 119);
        items.add(newItem);
        newItem = new Item("Vegetable lasagne", 269);
        items.add(newItem);
    }


    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime currentTime = LocalTime.parse("13:30:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentTime);

        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime currentTime = LocalTime.parse("23:30:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(currentTime);

        assertFalse(restaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void passing_empty_order_should_throw_exception() {
        //order consists of zero items
        assertThrows(nullOrderException.class, () -> restaurant.getTotal(new ArrayList<>()));
    }

    @Test
    public void passing_order_with_only_one_item_should_return_item_order_value() throws nullOrderException {
        //order consists of only one item, "Sweet corn soup" priced at
        order.remove("Vegetable lasagne");
        assertEquals(119, restaurant.getTotal(order));
    }

    @Test
    public void passing_order_with_more_than_one_items_should_return_total_order_value() throws nullOrderException {
        //order consists of two item, "Sweet corn soup" priced at 119 and "Vegetable lasagne" priced at 269
        assertEquals(388, restaurant.getTotal(order));
    }

    @Test
    public void selecting_an_item_from_menu_should_add_item_to_order() {
        //selecting and comparing the returned values
        assertEquals(items.size(), restaurant.selectitems(order).size());
        assertEquals(items.get(0).getName(), restaurant.selectitems(order).get(0).getName());
        assertEquals(items.get(0).getPrice(), restaurant.selectitems(order).get(0).getPrice());
    }

    @Test
    public void deselecting_an_item_from_menu_should_remove_item_from_order() {

        items.remove(1); //removing the "Vegetable lasagne" from the list

        restaurant.selectitems(order); //selecting items before deselecting items
        order.remove("Sweet corn soup");

        //deselecting and comparing the returned values
        assertEquals(items.size(), restaurant.deselectitems(order).size());
        assertEquals(items.get(0).getName(), restaurant.deselectitems(order).get(0).getName());
        assertEquals(items.get(0).getPrice(), restaurant.deselectitems(order).get(0).getPrice());
    }

}