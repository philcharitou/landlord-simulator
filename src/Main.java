import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Landlord player = null;
    private static List<Tenant> availableTenants = new ArrayList<Tenant>();
    private static List<House> availableProperties = new ArrayList<House>();
    private static final Random rand = new Random();
    private static final DecimalFormat df = new DecimalFormat();

    public static void main(String[] args) {
        boolean gameOver = false;
        List<String> roadTypes = new ArrayList<String>();
        List<String> roadNames = new ArrayList<String>();

        Collections.addAll(roadTypes, "Avenue", "Road", "Street", "Lane", "Circle", "Square");
        Collections.addAll(roadNames, "Abbey", "Bingham", "Charles", "Dickens", "Elsepth", "Frederick", "George", "Hood", "Ingrid", "Java");

        for (int i = 0; i < 11; i++) {
            availableProperties.add(new House(rand.nextInt(1200, 3500), (rand.nextInt(100, 1000) + " " + roadNames.get(rand.nextInt(9)) + " " + roadTypes.get(rand.nextInt(6)))));
        }

        for (int i = 0; i < 20; i++) {
            availableTenants.add(new Tenant(roadNames.get(rand.nextInt(9)), roadNames.get(rand.nextInt(9)), rand.nextInt(1200, 3500)));
        }

        System.out.println("\n--- Welcome to LANDLORDIAN, the dystopian landlord simulator. ---\n");
        System.out.println("Fortunately, due to a wonderful set of circumstances, you find yourself as a landlord. Rather than a renter.");
        System.out.println("With great power comes great rent control. Be sure to follow the law or don't get caught!");

        System.out.println("\nLet's start with who you are.. What's your first name?");
        String firstName = scanner.nextLine();
        System.out.println("That's.. an interesting name! What about your surname?");
        String lastName = scanner.nextLine();

        player = new Landlord(firstName, lastName,0);

        // Remove first property and give it to the player
        player.buyProperty(availableProperties.getFirst(), true);
        availableProperties.removeFirst();

        System.out.println("\nIt's a pleasure to have you in existence " + firstName + " " + lastName + ". Let's begin.");
        System.out.println("\nOnce upon a time... On " + rand.nextInt(100, 1000) + " " + lastName + " " + roadTypes.get(rand.nextInt(6)) + "...");
        System.out.println("An incredible non-denominated individual, one truly stunning person of fabulous wealth and class owned a house.");
        System.out.println("Sure, they lived in their parents basement. But with the money they saved, they could begin their venture into..");
        System.out.println("~ Land Ownership ~");
        System.out.println("But would they stop at just one house? Forfeit all potential economic opportunity? No. They would play the game. This game.");

        System.out.println("\nYOUR OBJECTIVE:\tlike all great heroes.. make as much money as possible before you kick the bucket!");
        System.out.println("* For a list of commands, please type \"help\".");

        while (!gameOver) {
            String input = scanner.nextLine();

            switch (input) {
                case "help":
                    getHelpFunctions();
                    break;
                case "bank balance":
                    System.out.println("Bank Balance: " +df.format(player.getBankBalance()));
                    break;
                case "monthly income":
                    System.out.println("Monthly Income: " + player.getMonthlyIncome());
                    break;
                case "available properties":
                    System.out.println("Available Properties for Purchase:\n");
                    System.out.println("Bank Balance: "+df.format(player.getBankBalance()));
                    int i = 0;
                    for (House house : availableProperties) {
                        String address = i++ + ":\t" + house.getAddress();
                        String value = "$ " + df.format(house.getBuildingValue());
                        String format = "%-30s%s%n";
                        System.out.printf(format, address, value);
                    }
                    System.out.println("\nIf you would like to purchase one of these properties, " +
                            "please enter:\npurchase [property]\nWhere [property] is replaced by the address or number " +
                            "of the property you wish to purchase.");

                    propertyLoop("purchase");

                    break;
                case "my properties":
                    System.out.println("Your Properties:\n");
                    player.listProperties();
                    break;
                case "rent properties":
                    System.out.println("Available Properties for Rent:\n");
                    player.listPropertiesWithoutTenants();

                    System.out.println("\nIf you would like to rent one of these properties, " +
                            "please enter:\nrent [property]\nWhere [property] is replaced by the address or number " +
                            "of the property you wish to purchase.");

                    propertyLoop("rent");

                    break;
                case "adjust rent":
                    System.out.println("Your Properties:\n");
                    player.listPropertiesWithRent();

                    System.out.println("\nIf you would like to adjust rent on one of these properties, " +
                            "please enter:\nadjust [property]\nWhere [property] is replaced by the address or number " +
                            "of the property you wish to adjust.");

                    propertyLoop("adjust");

                    break;
                case "insurance fraud":
                    System.out.println("Your Properties:\n");
                    player.listProperties();

                    System.out.println("\nIf you would like to accidentally burn down one of these properties, " +
                            "please enter:\nburn [property]\nWhere [property] is replaced by the address or number " +
                            "of the property you wish to adjust.");

                    propertyLoop("burn");

                    break;
                case "evict tenants":
                    System.out.println("Your Properties:\n");
                    player.listProperties();

                    System.out.println("\nIf you would like to evict tenants from one of these properties, " +
                            "please enter:\nevict [property]\nWhere [property] is replaced by the address or number " +
                            "of the property you wish to adjust.");

                    propertyLoop("evict");

                    break;
                case "pay bills":
                    System.out.println("Your Bills:\n");
                    System.out.println("Bank Balance: "+df.format(player.getBankBalance()));
                    int j = 0;
                    for (Debt debt : player.getOwedMoney()) {
                        String address = j++ + "Months Left:\t" + debt.getMonthsLeft();
                        String value = "$ " + df.format(debt.getAmount());
                        String format = "%-30s%s%n";
                        System.out.printf(format, address, value);
                    }
                    System.out.println("\nIf you would like to consolidate one of these debts, " +
                            "please enter:\npay [debt]\nWhere [debt] is replaced by the number " +
                            "of the debt you wish to pay.");

                    debtLoop();

                    break;
                case "wait":
                    System.out.println("How many months would you like to wait?");
                    int months = parseInt(scanner.nextLine());
                    waitMonths(months);
                    break;
                default:
                    System.out.println("Unrecognized. Please type \"help\" for available commands.");
                    break;
            }

            if (player.getAge() >= 85 * 12) {
                gameOver = true;
                System.out.println("Looks like old age finally caught up with you, let's see how much you can leave to your cat.");
                System.out.println("Bank Balance: "+df.format(player.getBankBalance()));
            }

            if (player.bankBalance < 0) {
                gameOver = true;
                System.out.println("You ran out of money!");
                System.out.println("Bank Balance: "+df.format(player.getBankBalance()));
            }

            if (player.hasExpiredDebt()) {
                gameOver = true;
                System.out.println("You didn't pay your debts!");
                System.out.println("Bank Balance: "+df.format(player.getBankBalance()));
            }
        }

        System.out.println("** GAME OVER, THANK YOU FOR PLAYING **");
    }

    private static void getHelpFunctions() {
        String format = "%-30s%s%n";

        System.out.printf(format, ("\n-\t bank balance"), ("Check your personal bank balance"));
        System.out.printf(format, ("-\t monthly income"), ("Check your monthly income stream from rentals"));
        System.out.printf(format, ("-\t available properties"), ("Lists all available properties for purchase"));
        System.out.printf(format, ("-\t my properties"), ("Lists all properties you own and their occupants"));
        System.out.printf(format, ("-\t rent properties"), ("Lists all available properties for rent"));
        System.out.printf(format, ("-\t adjust rent"), ("Adjust rent on your properties"));
        System.out.printf(format, ("-\t insurance fraud"), ("Commit insurance fraud on your properties (risky business!)"));
        System.out.printf(format, ("-\t evict tenants"), ("Remove pesky tenants for a 'small' legal fee"));
        System.out.printf(format, ("-\t pay bills"), ("Pay any outstanding amounts (i.e. Hospital)"));
        System.out.printf(format, ("-\t wait"), ("Wait to earn money and watch the world go by."));
    }

    private static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    private static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

    private static House findByAddress(Collection<House> listHouse, String address) {
        return listHouse.stream().filter(house -> address.equalsIgnoreCase(house.getAddress())).findFirst().orElse(null);
    }

    private static void propertyLoop(String type) {
        boolean looping = true;
        List<House> properties = new ArrayList<House>();
        House house = null;

        properties = switch (type) {
            case "rent" -> player.getPropertiesWithoutTenants();
            case "purchase" -> availableProperties;
            default -> player.getProperties();
        };

        while (true) {
            String propertyChoice = scanner.nextLine();

            if (Objects.equals(propertyChoice, "exit")) {
                return;
            }

            if(propertyChoice.length() > 4) {
                switch (type) {
                    case "rent":
                        propertyChoice = propertyChoice.substring(4).trim();
                        break;
                    case "purchase":
                        propertyChoice = propertyChoice.substring(8).trim();
                        break;
                    case "adjust":
                        propertyChoice = propertyChoice.substring(6).trim();
                        break;
                    case "evict":
                        propertyChoice = propertyChoice.substring(5).trim();
                        break;
                    case "burn":
                        propertyChoice = propertyChoice.substring(4).trim();
                        break;
                    default:
                        propertyChoice = propertyChoice;
                        break;
                }
            }

            if (isInteger(propertyChoice)) {
                if (parseInt(propertyChoice) >= 0 && parseInt(propertyChoice) < properties.size()) {
                    house = properties.get(parseInt(propertyChoice));
                }
            } else {
                house = findByAddress(properties, propertyChoice);
            }

            if (house == null) {
                System.out.println("There was a problem finding that property, please try again or type \"exit\" to go back.");
            } else {
                if(Objects.equals(type, "rent")) {
                    Tenant availableTenant = getAvailableTenant(house);

                    if (availableTenant != null) {
                        house.rentOut(availableTenant);
                        System.out.println("Congratulations! You are now the proud landlord of " + availableTenant.getFirstName()+" "+availableTenant.getLastName()+". Your properties now include:");
                        player.listProperties();
                        return;
                    } else {
                        System.out.println("Uh Oh! Looks like nobody is interested in renting right now. Maybe you've cornered the market?");
                        return;
                    }

                } else if (Objects.equals(type, "purchase")) {
                    if (player.buyProperty(house, false)) {
                        properties.remove(house);
                        System.out.println("Congratulations! You are now the proud owner of " + house.getAddress() + ". Your properties now include:");
                        player.listProperties();
                        return;
                    } else {
                        System.out.println("Uh Oh! Looks like you don't have enough money...");
                    }
                } else if (Objects.equals(type, "adjust")) {
                    System.out.println("What would you like to adjust the rent to?");
                    double adjustmentAmount = Double.parseDouble(scanner.nextLine());

                    if(!house.getTenants().isEmpty()) {
                        Tenant tenant = house.getTenants().getFirst();

                        if (tenant.getMonthlyIncome() < adjustmentAmount) {
                            boolean decision = false;

                            while (!decision) {
                                System.out.println("Are you sure? Your current tenant would no longer be able to afford this property and would have to leave. (Y Yes, N no)");
                                String choice = scanner.nextLine();
                                if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                                    System.out.println("The rent has been changed!");
                                    house.setMonthlyRent(adjustmentAmount);
                                    house.evict(tenant);
                                    decision = true;
                                } else if (choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")) {
                                    System.out.println("Okay! Would you like to adjust another property?");
                                    decision = true;
                                }
                            }
                        } else {

                            if (rand.nextInt(100) > 80 && adjustmentAmount > house.getMonthlyRent()) {
                                System.out.println("Unfortunately, "+tenant.firstName+" wasn't a fan. Looks like you'll need to find a new tenant!");
                                tenant.setBankBalance(-1);
                                house.evict(tenant);
                            }

                            house.setMonthlyRent(adjustmentAmount);
                            System.out.println("The rent has been changed!");
                        }
                    } else {
                        System.out.println("The rent has been changed!");
                        house.setMonthlyRent(adjustmentAmount);
                    }
                } else if (Objects.equals(type, "evict")) {
                    if (house.isOccupied()) {
                        System.out.println("Congratulations! have successfully un-homed your tenant!");
                        house.evict(house.getTenants().getFirst());
                        return;
                    } else {
                        System.out.println("It seems there is no tenant in this property! Please elect another or type \"exit\"");
                    }
                }  else if (Objects.equals(type, "burn")) {
                    if (house.isOccupied()) {
                        boolean decision = false;

                        while (!decision) {
                            System.out.println("Hm.. It seems this house is currently occupied, are you sure? (Y yes, N no)");
                            String choice = scanner.nextLine();
                            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                                System.out.println("You're not supposed to say yes! Let's evict them first.");
                                decision = true;
                            } else if (choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")) {
                                System.out.println("Phew! Had me worried there for a second..");
                                decision = true;
                            }
                        }

                        return;
                    } else {
                        System.out.println("Let's get this party started!");

                        try {
                            for (int i = 0; i < 3; i++) {
                                System.out.println("Burning ...");
                                Thread.sleep(100);
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    if (rand.nextInt(100) > 50) {
                        System.out.println("Entirely successful! Enjoy the new paycheck.");
                        System.out.println("Old Bank Balance: "+df.format(player.getBankBalance()));
                        player.commitInsuranceFraud(house);
                        System.out.println("New Bank Balance: "+df.format(player.getBankBalance()));
                    } else {
                        System.out.println("Complete failure! You were caught by Big Brother.");
                        System.out.println("Old Bank Balance: "+df.format(player.getBankBalance()));
                        player.loseProperty(house);
                        double bankBalance = player.getBankBalance();
                        player.setBankBalance(bankBalance-200000);
                        System.out.println("New Bank Balance: "+df.format(player.getBankBalance()));

                    }
                }
            }
        }
    }

    private static void debtLoop() {
        boolean looping = true;
        List<Debt> debts = player.getOwedMoney();
        Debt debt = null;

        while (true) {
            String debtChoice = scanner.nextLine();

            if (Objects.equals(debtChoice, "exit")) {
                return;
            }

            debtChoice = debtChoice.substring(3).trim();

            if (parseInt(debtChoice) >= 0 && parseInt(debtChoice) < debts.size()) {
                debt = debts.get(parseInt(debtChoice));
            }

            if (debt == null) {
                System.out.println("There was a problem finding that debt, please try again or type \"exit\" to go back.");
            } else {
                if (player.getBankBalance() >= debt.getAmount()) {
                    player.payDebt(debt);
                } else {
                    System.out.println("Uh Oh! It looks like you can't afford to pay this off right now.");
                    return;
                }
            }
        }
    }

    private static Tenant getAvailableTenant(House house) {
        for (Tenant tenant : availableTenants) {
            if (tenant.getHouse() == null && tenant.getBankBalance() > 0 && tenant.getMonthlyIncome() >= house.getMonthlyRent()) {
                return tenant;
            }
        }
        return null;
    }

    private static void waitMonths(int months) {
        df.setMaximumFractionDigits(2);

        for (int i = 0; i < months; i++) {
            for (Tenant tenant : availableTenants) {
                if (tenant.getBankBalance() > 0) {
                    tenant.getPaid();
                }
            }

            for (House house : availableProperties) {
                house.setBuildingValue(house.getBuildingValue()*1.002);
            }

            for (Debt debt : player.getOwedMoney()) {
                int monthsLeft = debt.getMonthsLeft();
                debt.setMonthsLeft(monthsLeft-1);
            }

            player.chargeRent();
        }
        player.age(months);

        if (player.getAge() > 40) {
            if(rand.nextInt(100) > 80) {
                Debt hospitalBill = new Debt(rand.nextInt(100000, 300000), 12);
                player.healthScare(hospitalBill);

                System.out.println("Uh oh! You've had a health scare. Better pay these medical bills (or else)");
                System.out.println("HOSPITAL BILL: "+hospitalBill.getAmount());
                System.out.println("DUE IN: "+hospitalBill.getMonthsLeft()+" months");
            }
        }

        System.out.println("You are now "+Math.floor((double) player.getAge() /12)+" years old and have $"+df.format(player.getBankBalance())+" in your bank.");
    }
}