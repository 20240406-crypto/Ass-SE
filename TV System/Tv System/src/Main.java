import java.util.*;


public class Main {
//    this is the channel it has the number and name of channel
    static class Channel {
        private  int number;
        private  String name;

        public Channel(int number, String name) {
            if (number <= 0) throw new IllegalArgumentException("Channel number must be > 0");
            if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Channel name is required");
            this.number = number;
            this.name = name.trim();
        }

        public int getNumber() { return number; }
        public String getName() { return name; }

        @Override
        public String toString() {
            return number + " - " + name;
        }
    }

    static class TV {
        private  Map<Integer, Channel> channels = new TreeMap<>();
        private Integer current = null;

        public boolean addChannel(int number, String name) {
            if (channels.containsKey(number)) return false;
            channels.put(number, new Channel(number, name));
            if (current == null) current = number;
            return true;
        }

        public boolean deleteChannel(int number) {
            if (!channels.containsKey(number)) return false;
            channels.remove(number);
            if (Objects.equals(current, number)) {
                current = channels.isEmpty() ? null : channels.keySet().iterator().next();
            }
            return true;
        }

        public boolean switchTo(int number) {
            if (!channels.containsKey(number)) return false;
            current = number;
            return true;
        }

        public Channel getCurrent() {
            return current == null ? null : channels.get(current);
        }

        public Collection<Channel> list() {
            return channels.values();
        }
    }

    // Read an integer safely
    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TV tv = new TV();

        while (true) {
            System.out.println("\n==== TV SYSTEM ====");
            Channel cur = tv.getCurrent();
            System.out.println("Current: " + (cur == null ? "(none)" : cur));
            System.out.println("\n");
            System.out.println("1) List channels");
            System.out.println("2) Add channel");
            System.out.println("3) Delete channel");
            System.out.println("4) Switch channel");
            System.out.println("0) Exit");

            int choice = readInt(sc, "Choose: ");

            switch (choice) {
                case 1 -> {
                    if (tv.getCurrent() == null) {
                        System.out.println("empty" );
                    }else {
                        System.out.println("Channels:");
                        for (Channel c : tv.list()) System.out.println(" - " + c);
                    }
                }
                case 2 -> {
                    int num = readInt(sc, "Channel number: ");
                    System.out.print("Channel name: ");
                    String name = sc.nextLine();

                    boolean ok = tv.addChannel(num, name);
                    if (ok) {
                        System.out.println("✅ Added: " + num + " - " + name);
                    } else {
                        System.out.println("❌ This channel number already exists.");
                    }
                }
                case 3 -> {
                    int num = readInt(sc, "Channel number to delete: ");
                    System.out.println(tv.deleteChannel(num) ? "✅ Deleted." : "❌ Channel not found.");
                }
                case 4 -> {
                    int num = readInt(sc, "Channel number to switch to: ");
                    System.out.println(tv.switchTo(num) ? "✅ Switched." : "❌ Channel not found.");
                }
                case 0 -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");
            }
        }
    }
}