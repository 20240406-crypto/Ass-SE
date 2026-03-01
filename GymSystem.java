//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Scanner;

class Member {
    private int id;
    private String name;
    private String phoneNumber;
    private MembershipStatus status;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;
    private LocalDate frozenUntilDate;
    private SubscriptionType subscriptionType;
    private int totalFreezeDaysUsed;
    private int remainingFreezeDays;

    public enum MembershipStatus {
        ACTIVE, FROZEN, INACTIVE
    }

    public enum SubscriptionType {
        ONE_DAY("1 Day", 1, 0),
        ONE_MONTH("1 Month", 30, 7),      // 1 week freeze
        THREE_MONTHS("3 Months", 90, 14),  // 2 weeks freeze
        SIX_MONTHS("6 Months", 180, 30),   // 1 month freeze
        ONE_YEAR("1 Year", 365, 90);       // 90 days freeze

        private final String displayName;
        private final int days;
        private final int freezeDays;

        SubscriptionType(String displayName, int days, int freezeDays) {
            this.displayName = displayName;
            this.days = days;
            this.freezeDays = freezeDays;
        }

        public String getDisplayName() { return displayName; }
        public int getDays() { return days; }
        public int getFreezeDays() { return freezeDays; }
    }

    public Member(int id, String name, String phoneNumber, SubscriptionType subscriptionType) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.subscriptionType = subscriptionType;
        this.status = MembershipStatus.ACTIVE;
        this.membershipStartDate = LocalDate.now();
        this.membershipEndDate = this.membershipStartDate.plusDays(subscriptionType.getDays());
        this.frozenUntilDate = null;
        this.totalFreezeDaysUsed = 0;
        this.remainingFreezeDays = subscriptionType.getFreezeDays();
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public MembershipStatus getStatus() { return status; }
    public LocalDate getMembershipStartDate() { return membershipStartDate; }
    public LocalDate getMembershipEndDate() { return membershipEndDate; }
    public SubscriptionType getSubscriptionType() { return subscriptionType; }
    public int getRemainingFreezeDays() { return remainingFreezeDays; }
    public int getTotalFreezeDaysUsed() { return totalFreezeDaysUsed; }

    public void renewMembership(SubscriptionType newSubscriptionType) {
        this.subscriptionType = newSubscriptionType;
        this.membershipStartDate = LocalDate.now();
        this.membershipEndDate = this.membershipStartDate.plusDays(newSubscriptionType.getDays());
        this.status = MembershipStatus.ACTIVE;
        this.frozenUntilDate = null;
        this.totalFreezeDaysUsed = 0;
        this.remainingFreezeDays = newSubscriptionType.getFreezeDays();
    }

    public boolean freezeMembership(int days) {
        if (status == MembershipStatus.ACTIVE) {
            if (days <= remainingFreezeDays) {
                this.status = MembershipStatus.FROZEN;
                this.frozenUntilDate = LocalDate.now().plusDays(days);
                // Extend membership end date by the frozen period
                this.membershipEndDate = this.membershipEndDate.plusDays(days);
                this.totalFreezeDaysUsed += days;
                this.remainingFreezeDays -= days;
                return true;
            } else {
                System.out.println("Cannot freeze for " + days + " days. Only " + remainingFreezeDays + " freeze days remaining.");
                return false;
            }
        }
        return false;
    }

    public void checkMembershipStatus() {
        // Auto-update status based on dates
        if (status == MembershipStatus.ACTIVE && LocalDate.now().isAfter(membershipEndDate)) {
            status = MembershipStatus.INACTIVE;
        } else if (status == MembershipStatus.FROZEN && frozenUntilDate != null) {
            if (LocalDate.now().isAfter(frozenUntilDate) || LocalDate.now().isEqual(frozenUntilDate)) {
                status = MembershipStatus.ACTIVE;
                frozenUntilDate = null;
            }
        }
    }

    public String getDetailedInfo() {
        checkMembershipStatus();
        StringBuilder info = new StringBuilder();
        info.append("\n╔══════════════════════════════════════╗\n");
        info.append("║         MEMBER INFORMATION           ║\n");
        info.append("╠══════════════════════════════════════╣\n");
        info.append(String.format("║ %-18s: %-15d ║\n", "Member ID", id));
        info.append(String.format("║ %-18s: %-15s ║\n", "Name", name));
        info.append(String.format("║ %-18s: %-15s ║\n", "Phone", phoneNumber));
        info.append(String.format("║ %-18s: %-15s ║\n", "Status", status));
        info.append(String.format("║ %-18s: %-15s ║\n", "Subscription", subscriptionType.getDisplayName()));
        info.append("╠══════════════════════════════════════╣\n");
        info.append(String.format("║ %-18s: %-15s ║\n", "Start Date", membershipStartDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        info.append(String.format("║ %-18s: %-15s ║\n", "End Date", membershipEndDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));

        long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), membershipEndDate);
        String daysRemainingStr = daysRemaining >= 0 ? String.valueOf(daysRemaining) : "Expired";
        info.append(String.format("║ %-18s: %-15s ║\n", "Days Remaining", daysRemainingStr));

        info.append("╠══════════════════════════════════════╣\n");
        info.append(String.format("║ %-18s: %-15d ║\n", "Freeze Days Total", subscriptionType.getFreezeDays()));
        info.append(String.format("║ %-18s: %-15d ║\n", "Freeze Days Used", totalFreezeDaysUsed));
        info.append(String.format("║ %-18s: %-15d ║\n", "Freeze Days Left", remainingFreezeDays));

        if (status == MembershipStatus.FROZEN && frozenUntilDate != null) {
            info.append("╠══════════════════════════════════════╣\n");
            info.append(String.format("║ %-18s: %-15s ║\n", "Frozen Until", frozenUntilDate.format(DateTimeFormatter.ISO_LOCAL_DATE)));
        }

        info.append("╚══════════════════════════════════════╝\n");

        return info.toString();
    }

    public String getBasicInfo() {
        checkMembershipStatus();
        return String.format("ID: %d | Name: %-15s | Status: %-8s | Subscription: %-10s | Freeze Days Left: %d",
                id, name, status, subscriptionType.getDisplayName(), remainingFreezeDays);
    }
}

public class GymSystem {
    private HashMap<Integer, Member> members;
    private Scanner scanner;
    private int nextMemberId;

    public GymSystem() {
        members = new HashMap<>();
        scanner = new Scanner(System.in);
        nextMemberId = 1000;
    }

    public void addNewMember() {
        System.out.println("\n=== Add New Member ===");
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        System.out.println("\nSelect Subscription Type:");
        Member.SubscriptionType[] types = Member.SubscriptionType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.printf("%d. %s (Freeze Days: %d)%n",
                    i + 1, types[i].getDisplayName(), types[i].getFreezeDays());
        }

        System.out.print("Enter choice (1-" + types.length + "): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice >= 1 && choice <= types.length) {
                Member.SubscriptionType selectedType = types[choice - 1];
                Member member = new Member(nextMemberId, name, phone, selectedType);
                members.put(nextMemberId, member);

                System.out.println("\n✅ Member added successfully with ID: " + nextMemberId);
                System.out.println(member.getDetailedInfo());
                nextMemberId++;
            } else {
                System.out.println("Invalid choice. Member not added.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Member not added.");
        }
    }

    public void scanID() {
        System.out.println("\n=== Scan Member ID ===");
        System.out.print("Enter member ID: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());

            if (members.containsKey(id)) {
                Member member = members.get(id);
                System.out.println(member.getDetailedInfo());
            } else {
                System.out.println("❌ Member not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }

    public void renewMembership() {
        System.out.println("\n=== Renew Membership ===");
        System.out.print("Enter member ID: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());

            if (members.containsKey(id)) {
                Member member = members.get(id);

                System.out.println("\nCurrent Member:");
                System.out.println(member.getBasicInfo());

                System.out.println("\nSelect New Subscription Type:");
                Member.SubscriptionType[] types = Member.SubscriptionType.values();
                for (int i = 0; i < types.length; i++) {
                    System.out.printf("%d. %s (Freeze Days: %d)%n",
                            i + 1, types[i].getDisplayName(), types[i].getFreezeDays());
                }

                System.out.print("Enter choice (1-" + types.length + "): ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice >= 1 && choice <= types.length) {
                    Member.SubscriptionType selectedType = types[choice - 1];
                    member.renewMembership(selectedType);
                    System.out.println("\n✅ Membership renewed successfully!");
                    System.out.println(member.getDetailedInfo());
                } else {
                    System.out.println("Invalid choice. Renewal cancelled.");
                }
            } else {
                System.out.println("❌ Member not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter numbers.");
        }
    }

    public void freezeMembership() {
        System.out.println("\n=== Freeze Membership ===");
        System.out.print("Enter member ID: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());

            if (members.containsKey(id)) {
                Member member = members.get(id);

                System.out.println("\nMember Information:");
                System.out.println(member.getBasicInfo());

                if (member.getStatus() == Member.MembershipStatus.ACTIVE) {
                    System.out.print("\nEnter number of days to freeze (Available: " + member.getRemainingFreezeDays() + " days): ");
                    int days = Integer.parseInt(scanner.nextLine());

                    if (member.freezeMembership(days)) {
                        System.out.println("\n✅ Membership frozen successfully for " + days + " days!");
                        System.out.println(member.getDetailedInfo());
                    }
                } else if (member.getStatus() == Member.MembershipStatus.FROZEN) {
                    System.out.println("❌ Membership is already frozen.");
                } else {
                    System.out.println("❌ Cannot freeze inactive membership.");
                }
            } else {
                System.out.println("❌ Member not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter numbers.");
        }
    }

    public void displayMemberInfo() {
        System.out.println("\n=== Display Member Information ===");
        System.out.print("Enter member ID: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());

            if (members.containsKey(id)) {
                Member member = members.get(id);
                System.out.println(member.getDetailedInfo());
            } else {
                System.out.println("❌ Member not found with ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
        }
    }

    public void listAllMembers() {
        System.out.println("\n=== All Members List ===");
        if (members.isEmpty()) {
            System.out.println("No members in the system.");
        } else {
            System.out.println("Total Members: " + members.size());
            System.out.println("-".repeat(70));
            for (Member member : members.values()) {
                System.out.println(member.getBasicInfo());
            }
            System.out.println("-".repeat(70));
        }
    }

    public void displayMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║     GYM MANAGEMENT SYSTEM           ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ 1. Add New Member                    ║");
        System.out.println("║ 2. Scan ID (Check Status)            ║");
        System.out.println("║ 3. Renew Membership                  ║");
        System.out.println("║ 4. Freeze Membership                 ║");
        System.out.println("║ 5. Display Member Info               ║");
        System.out.println("║ 6. List All Members                  ║");
        System.out.println("║ 7. Exit                              ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Enter your choice: ");
    }

    public void run() {
        int choice;
        do {
            displayMenu();
            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addNewMember();
                        break;
                    case 2:
                        scanID();
                        break;
                    case 3:
                        renewMembership();
                        break;
                    case 4:
                        freezeMembership();
                        break;
                    case 5:
                        displayMemberInfo();
                        break;
                    case 6:
                        listAllMembers();
                        break;
                    case 7:
                        System.out.println("\n👋 Thank you for using Gym Management System!");
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                choice = 0;
            }
        } while (choice != 7);

        scanner.close();
    }

    public static void main(String[] args) {
        GymSystem gymSystem = new GymSystem();
        gymSystem.run();
    }
}