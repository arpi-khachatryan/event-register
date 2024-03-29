import commands.EventCommands;
import manager.EventManager;
import manager.UserManager;
import model.Event;
import model.EventType;
import model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class EventRegister implements EventCommands {
    private static final Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static UserManager userManager = new UserManager();
    private static EventManager eventManager = new EventManager();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            EventCommands.printCommands();
            int command = Integer.parseInt(scanner.nextLine());
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_EVENT:
                    addEvent();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case SHOW_ALL_EVENTS:
                    showAllEvents();
                    break;
                case SHOW_ALL_USERS:
                    showAllUsers();
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }
    }

    private static void showAllUsers() {
        List<User> all = userManager.getAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

    private static void showAllEvents() {
        List<Event> all = eventManager.getAll();
        for (Event event : all) {
            System.out.println(event);
        }
    }

    private static void addUser() {
        showAllEvents();
        System.out.println("Please choose event's id");
        int eventId = Integer.parseInt(scanner.nextLine());
        Event event = eventManager.getById(eventId);
        if (event == null) {
            System.out.println("Please choose correct event ID");
        } else {
            System.out.println("Please input user's name,surname,email");
            String userDataStr = scanner.nextLine();
            String[] userData = userDataStr.split(",");
            User user = User.builder()
                    .name(userData[0])
                    .surname(userData[1])
                    .email(userData[2])
                    .event(event)
                    .build();
            userManager.add(user);
            System.out.println("User added");
        }
    }

    private static void addEvent() {
        System.out.println("Please input evnet's name,place,isOnline,evenType(MOVE,SPORT,GAME),price,date(yyyy-MM-dd HH:mm:ss)");
        String eventDataStr = scanner.nextLine();
        String[] eventData = eventDataStr.split(",");
        Event event = null;
        try {
            event = Event.builder()
                    .name(eventData[0])
                    .place(eventData[1])
                    .isOnline(Boolean.valueOf(eventData[2]))
                    .eventType(EventType.valueOf(eventData[3]))
                    .price(Double.parseDouble(eventData[4]))
                    .eventDate(sdf.parse(eventData[5]))
                    .build();
            eventManager.add(event);
            System.out.println("Event added");
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
    }
}


