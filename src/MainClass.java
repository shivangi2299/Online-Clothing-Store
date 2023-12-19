import java.util.Scanner;

import java.util.List;

import java.util.regex.Pattern; 





public class MainClass {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        User currentUser = null;

        UserAuthentication userAuthentication = new UserAuthentication();

        ShoppingCart shoppingCart = new ShoppingCart();

        Catalog catalog = new Catalog();



        // Load products into the catalog

        List<Product> products = ProductLoader.loadProducts();

        catalog.addProducts(products);

        

        while (true) {

            System.out.println("1. Register");

            System.out.println("2. Login");

            System.out.println("3. Browse Products");

            System.out.println("4. Add Product to Cart");

            System.out.println("5. View Cart");

            System.out.println("6. Place Order");

            System.out.println("7. Exit");



            if (currentUser != null) {

                System.out.println("Logged in as: " + currentUser.getUsername());

            }



            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            scanner.nextLine(); // Consume the newline character.



            switch (choice) {

                case 1: // Register

                    System.out.print("Enter username: ");

                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");

                    String password = scanner.nextLine();

                    System.out.print("Enter name: ");

                    String name = scanner.nextLine();

                    System.out.print("Enter email: ");

                    String email = scanner.nextLine();

                 // Validate input

                    if (!isValidUsername(username)) {

                        System.out.println("Username must contain only lowercase letters.");

                    } else if (!isValidPassword(password)) {

                        System.out.println("Password must contain one uppercase letter, one special character, one number, and at least 8 characters.");

                    } else if (!isValidName(name)) {

                        System.out.println("Name must be in the format 'First Last' with the first letter of each word capitalized.");

                    } else if (!isValidEmail(email)) {

                        System.out.println("Email must contain the @ symbol.");

                    } else {

                        userAuthentication.registerUser(username, password, name, email);

                        Logger.log("User registered: " + username);

                        System.out.println("User registered successfully!");

                    }

                    break;

                    

                    

                    

                case 2: // Login

                    if (currentUser != null) {

                        System.out.println("You are already logged in.");

                    } else {

                        System.out.print("Enter username: ");

                        String loginUsername = scanner.nextLine();

                        System.out.print("Enter password: ");

                        String loginPassword = scanner.nextLine();

                        currentUser = userAuthentication.login(loginUsername, loginPassword);



                        if (currentUser != null) {

                            Logger.log("User logged in: " + currentUser.getUsername());

                            System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");

                        } else {

                            Logger.log("Login failed for user: " + loginUsername);

                            System.out.println("Login failed. Please check your credentials.");

                        }

                    }

                    break;

                case 3: // Browse Products (Not implemented in this example)

                    



                    displayProductList(catalog.getAllProducts());

                    break;



                    

                case 4: // Add Product to Cart (Not implemented in this example)

                	if (currentUser != null) {

                        // Placeholder for adding a product to the cart

                        System.out.print("Enter the product name to add to the cart: ");

                        String productName = scanner.nextLine();

                        Product selectedProduct = findProductByName(catalog.getAllProducts(), productName);





                        if (selectedProduct != null) {

                            shoppingCart.addItem(selectedProduct);

                            System.out.println("Product added to the cart.");

                        } else {

                            System.out.println("Product not found in the catalog.");

                        }

                    } else {

                        System.out.println("You need to log in to add products to the cart.");

                    }

                    break;



                 // Inside your main switch statement

                case 5: // View Cart

                    if (currentUser != null) {

                        List<Product> items = shoppingCart.getItems();

                        System.out.println("Cart for " + currentUser.getUsername() + ":");

                        for (Product item : items) {

                            System.out.println(item.getName());

                        }



                        double totalPrice = calculateTotalPrice(shoppingCart);

                        System.out.println("Total Price: $" + totalPrice);

                    } else {

                        System.out.println("You need to log in to view your cart.");

                    }

                    break;



                case 6: // Place Order (Not implemented in this example)

                    if (currentUser != null) {

                        System.out.println("Placing an order...");

                        double totalPrice = calculateTotalPrice(shoppingCart);

                        boolean paymentSuccess = new PaymentProcessor().processPayment(totalPrice, "Credit Card");



                        if (paymentSuccess) {

                            Logger.log("Order placed for user: " + currentUser.getUsername());

                            System.out.println("Order placed successfully.");

                        } else {

                            Logger.log("Payment failed for user: " + currentUser.getUsername());

                            System.out.println("Payment failed. Please try again.");

                        }

                    } else {

                        System.out.println("You need to log in to place an order.");

                    }

                    break;

                case 7: // Exit

                    System.out.println("Exiting the program.");

                    scanner.close();

                    System.exit(0);

                default:

                    System.out.println("Invalid choice. Please try again.");

            }

        }

    }

    

    

    





	// Validation methods

    private static boolean isValidUsername(String username) {

        // Username contains all lowercase letters

        return Pattern.matches("^[a-z]+$", username);

    }



    private static boolean isValidPassword(String password) {

        // Password contains one uppercase, one special character, one number, and all lowercase

        return Pattern.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=.*[0-9])(?=.*[a-z]).{8,}$", password);

    }



    private static boolean isValidName(String name) {

        // Name contains the first name and last name

        return Pattern.matches("^[A-Z][a-z]* [A-Z][a-z]*$", name);

    }



    private static boolean isValidEmail(String email) {

        // Email contains the @ symbol

        return email.contains("@");

    }











    private static void displayProductList(List<Product> productList) {

        System.out.println("Product List:");

        for (int i = 0; i < productList.size(); i++) {

            Product product = productList.get(i);

            System.out.println((i + 1) + ". " + product.getName() + " - $" + product.getPrice());

            System.out.println("   Description: " + product.getDescription());

            System.out.println("   Quantity in Stock: " + product.getQuantityInStock());

        }

    }





// Placeholder method to find a product by name

private static Product findProductByName(List<Product> catalogProducts, String productName) {

    for (Product product : catalogProducts) {

        if (product.getName().equalsIgnoreCase(productName)) {

            return product;

        }

    }

    return null;

}



// Placeholder method to calculate the total price of items in the cart

private static double calculateTotalPrice(ShoppingCart shoppingCart) {

    double total = 0.0;

    for (Product product : shoppingCart.getItems()) {

        total += product.getPrice();

    }

    return total;

}

}